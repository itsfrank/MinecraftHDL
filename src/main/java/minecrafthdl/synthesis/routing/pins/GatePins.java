package minecrafthdl.synthesis.routing.pins;

import MinecraftGraph.Vertex;
import minecrafthdl.MHDLException;
import minecrafthdl.synthesis.Gate;

import java.util.ArrayList;

/**
 * Created by Francis O'Brien - 3/3/2017 - 9:43 AM
 */

public class GatePins {

    private final Vertex vertex;
    protected ArrayList<Pin> pins = new ArrayList<Pin>();

    protected int next_free_input_pin = 0;
    protected int next_free_output_pin = 0;

    protected int gate_width;
    protected int offset;
    protected boolean top;

    public GatePins(Gate g, Vertex v, int offset, boolean top){
        this.vertex = v;
        this.offset = offset;
        this.top = top;
        this.gate_width = g.getSizeX();

        if (top){
            for(int i = 0; i < g.num_outputs; i++){
                pins.add(new Pin(offset + (i * (1 + g.output_spacing)), true));
            }
        } else {
            for(int i = 0; i < g.num_inputs; i++){
                pins.add(new Pin(offset + (i * (1 + g.input_spacing)), false));
            }
        }
    }

    public ArrayList<Pin> getPins(){
        return this.pins;
    }

    public boolean hasNet(int net_id){
        for (Pin p : this.pins) {
            if (p.netID() == net_id) return true;
        }
        return false;
    }

    public Pin getNextPin(Vertex v){
        if (next_free_input_pin == this.pins.size()) {
            throw new MHDLException("To many input pins requested from gate");
        }

        this.next_free_input_pin += 1;
        return pins.get(this.next_free_input_pin - 1);
    }

    public boolean hasNextPin(){
        return next_free_input_pin < this.pins.size();
    }

    public String printPins(){
        String str = "";
        for (int i = 0; i < gate_width; i++) {
            str += ".";
        }

        for(Pin p : pins){
            if(p.x - offset == 0) str = 'x' + str.substring(p.x  - offset + 1);
            else if (p.x - offset == gate_width - 1) str = str.substring(0, p.x  - offset) + 'x';
            else str = str.substring(0, p.x  - offset) + 'x' + str.substring(p.x  - offset + 1);
        }

        return str;
    }
}
