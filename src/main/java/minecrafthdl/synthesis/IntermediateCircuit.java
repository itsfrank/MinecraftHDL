package minecrafthdl.synthesis;

import MinecraftGraph.*;
import org.lwjgl.Sys;

import java.util.ArrayList;

/**
 * Created by Francis on 11/28/2016.
 */
public class IntermediateCircuit {

    public ArrayList<ArrayList<Vertex>> layers = new ArrayList<>();

    public void loadGraph(Graph graph) {
        ArrayList<Vertex> finished = new ArrayList<>();
        ArrayList<Vertex> in_process = new ArrayList<>();
        ArrayList<Vertex> process_done = new ArrayList<>();
        ArrayList<Vertex> to_process = new ArrayList<>();

        for (Vertex v : graph.getVertices()) {
            if (v.getType() == VertexType.INPUT) {
                in_process.add(v);
            }
        }

        int layer = 0;

        while (in_process.size() > 0){
            layers.add(new ArrayList<Vertex>());
            for (Vertex v : in_process){
                boolean valid = true;
                for (Vertex p : v.getPrev()){
                    if (!finished.contains(p)) {
                        valid = false;
                        break;
                    }
                }

                if (valid){
                    layers.get(layer).add(v);
                    process_done.add(v);

                    for (Vertex n : v.getNext()){
                        to_process.add(n);
                    }
                }
            }
            for (Vertex v : process_done){
                in_process.remove(v);
                finished.add(v);
            }
            for (Vertex v : to_process){
                if (!in_process.contains(v)) in_process.add(v);
            }
            process_done.clear();
            to_process.clear();
            layer++;
        }
    }

    public void printLayers(){
        for (ArrayList<Vertex> layer : layers){
            for (Vertex v : layer) {
                if (v.getType() == VertexType.INPUT){
                    System.out.print("I, ");
                }
                if (v.getType() == VertexType.FUNCTION){
                    System.out.print(((Function) v).getFunc_Type() + ", ");
                }
                if (v.getType() == VertexType.OUTPUT){
                    System.out.print("O, ");
                }
            }
            System.out.print("\n");
        }
    }

    public Circuit genCircuit(){
        if (layers.size() == 0) return null;

        ArrayList<ArrayList<Circuit>> layers = new ArrayList<>();

        for (ArrayList<Vertex> v_layer : this.layers){
            ArrayList<Circuit> this_layer = new ArrayList<>();
            for (Vertex v : v_layer){
                if (v.getType() == VertexType.FUNCTION){
                    this_layer.add(genGate(((Function) v).getFunc_Type(), ((Function) v).get_num_inputs()));
                } else {
                    this_layer.add(LogicGates.IO());
                }
            }
            layers.add(this_layer);
        }

        int size_x = 0;
        int size_y = 0;
        int size_z = layers.size() == 0 ? 0 : (layers.size() - 1) * 5;

        int[] layers_size_z = new int[layers.size()];

        for (ArrayList<Circuit> layer : layers){
            int this_size_x = layer.size() == 0 ? 0 : layer.size() - 1;
            int this_size_y = 0;
            int this_size_z = 0;

            for (Circuit c : layer){
                this_size_x += c.getSizeX();
                if (c.getSizeY() > this_size_y) this_size_y = c.getSizeY();
                if (c.getSizeZ() > this_size_z) this_size_z = c.getSizeZ();
            }

            if (this_size_x > size_x) size_x = this_size_x;
            if (this_size_y > size_y) size_y = this_size_y;
            size_z += this_size_z;

            layers_size_z[layers.indexOf(layer)] = this_size_z;
        }

        Circuit circuit = new Circuit(size_x, size_y, size_z);

        int z_offset = 0;
        for (int i = 0; i < layers.size(); i++) {
            int x_offset = 0;
            for (Circuit c : layers.get(i)){
                circuit.insertCircuit(x_offset, 0, z_offset, c);
                x_offset += 1 + c.getSizeX();
            }
            z_offset += 5 + layers_size_z[i];
        }

        return circuit;
    }

    private static Circuit genGate(FunctionType func_type, int num_inputs) {
        if (func_type == FunctionType.AND) {
            return LogicGates.AND(num_inputs);
        } else if ( func_type == FunctionType.OR){
            return LogicGates.OR(num_inputs);
        } else if ( func_type == FunctionType.INV){
            return LogicGates.NOT();
        }
        else throw new RuntimeException("NO SUCH GATE AVAILABLE");
    }


}
