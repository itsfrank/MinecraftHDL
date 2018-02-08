package minecrafthdl.synthesis.routing;

import MinecraftGraph.Function;
import MinecraftGraph.FunctionType;
import MinecraftGraph.Vertex;
import MinecraftGraph.VertexType;
import minecrafthdl.synthesis.Gate;
import minecrafthdl.synthesis.routing.pins.*;
import minecrafthdl.synthesis.routing.vcg.VerticalConstraintGraph;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Francis on 2/22/2017.
 */
public class Router {

    public static Channel route(ArrayList<Vertex> top_vertices, ArrayList<Gate> top_gates, ArrayList<Vertex> bottom_vertices, ArrayList<Gate> bottom_gates, int gate_spacing){
        ArrayList<Pin> top_pins, bottom_pins = new ArrayList<Pin>();



        return null;
    }

    public static class PinInitRtn {
        public HashMap<Vertex, GatePins> pin_map;
        public PinsArray pins_array;
    }


    public static PinInitRtn initializePins(ArrayList<Vertex> top_vertices, ArrayList<Gate> top_gates, ArrayList<Vertex> bottom_vertices, ArrayList<Gate> bottom_gates, int gate_spacing){
        PinInitRtn rtn = new PinInitRtn();

        //Pin map init
        HashMap<Vertex, GatePins> pin_map = new HashMap<Vertex, GatePins>();

        if(top_gates.size() != top_vertices.size()) throw  new RuntimeException("Top vertices and gates must be same size");
        if(bottom_gates.size() != bottom_vertices.size()) throw  new RuntimeException("Bottom vertices and gates must be same size");

        //top pins
        int top_offset = 0;
        for (int i = 0; i < top_vertices.size(); i++){
            GatePins gate_pins = new GatePins(top_gates.get(i), top_vertices.get(i), top_offset, true);
            pin_map.put(top_vertices.get(i), gate_pins);

            top_offset += top_gates.get(i).getSizeX() + gate_spacing;
        }

        //bottom pins
        int bottom_offset = 0;
        for (int i = 0; i < bottom_vertices.size(); i++){
            Vertex v = bottom_vertices.get(i);

            GatePins gate_pins = null;
            if (v.type == VertexType.FUNCTION && ((Function)v).func_type == FunctionType.MUX){
                gate_pins = new MuxPins(bottom_gates.get(i), bottom_vertices.get(i), bottom_offset, false);
            } else {
                gate_pins = new GatePins(bottom_gates.get(i), bottom_vertices.get(i), bottom_offset, false);
            }
            pin_map.put(bottom_vertices.get(i), gate_pins);

            bottom_offset += bottom_gates.get(i).getSizeX() + gate_spacing;
        }

        rtn.pin_map = pin_map;

        //Pins array init
        PinsArray pins_array = new PinsArray();

        //top pins
        for (Vertex v : top_vertices){
            for (Pin p : pin_map.get(v).getPins()){
                pins_array.addPin(p, true);
            }
        }

        //bottom pins
        for (Vertex v : bottom_vertices){
            for (Pin p : pin_map.get(v).getPins()){
                pins_array.addPin(p, false);
            }
        }

        rtn.pins_array = pins_array;

        return rtn;
    }

    public static HashMap<Integer, Net> initializeNets(ArrayList<Vertex> top_vertices, ArrayList<Vertex> bottom_vertices, HashMap<Vertex, GatePins> pin_map) {
        HashMap<Integer, Net> nets = new HashMap<Integer, Net>();

        for (Vertex v : top_vertices){
            GatePins gate = pin_map.get(v);

            while (gate.hasNextPin()){
                Pin next_pin = gate.getNextPin(v);
                if (next_pin.empty() || next_pin.hasNet()) continue;

                Net net = new Net();
                nets.put(net.id, net);
                net.addPin(next_pin, false);

                for (Vertex next_vertex : v.getNext()){
                    net.addPin(pin_map.get(next_vertex).getNextPin(v), false);
                }
            }
        }

        for (Vertex v : bottom_vertices){
            GatePins gate = pin_map.get(v);

            while (gate.hasNextPin()){
                Pin next_pin = gate.getNextPin(v);
                if (next_pin.empty() || next_pin.hasNet()) continue;

                Net net = new Net();
                nets.put(net.id, net);
                net.addPin(next_pin, false);

                for (Vertex next_vertex : v.getNext()){
                    if (v.removed) continue;
                    try {
                        Pin np = pin_map.get(next_vertex).getNextPin(v);
                        net.addPin(pin_map.get(next_vertex).getNextPin(v), false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return nets;
    }

    public static Channel placeNets(HashMap<Integer, Net> nets, PinsArray pin_pairs){
        VerticalConstraintGraph vcg = new VerticalConstraintGraph(pin_pairs, nets);

        Channel channel = new Channel();
        channel.pinsArray = pin_pairs;

        while (!vcg.done()){
            for (PinPair pair : pin_pairs.getPairs()){

                if (!pair.top.empty() && vcg.canRoute(pair.top.netID())){
                    channel.findAvailableTrack(nets.get(pair.top.netID()), vcg);
                    vcg.routed(pair.top.netID());
                    break;
                }

                if (!pair.bot.empty() && vcg.canRoute(pair.bot.netID())){
                    channel.findAvailableTrack(nets.get(pair.bot.netID()), vcg);
                    vcg.routed(pair.bot.netID());
                    break;
                }
            }
        }

        return channel;

    }

    public static void printPins(HashMap<Vertex, GatePins> pin_map, ArrayList<Vertex> top_vertices, ArrayList<Vertex> bottom_vertices){
        String str = "";
        for (Vertex v : top_vertices){
            str += pin_map.get(v).printPins() + "|";
        }

        System.out.println(str + "\n");

        str = "";

        for (Vertex v : bottom_vertices){
            str += pin_map.get(v).printPins() + "|";
        }

        System.out.println(str + "\n");
    }
}
