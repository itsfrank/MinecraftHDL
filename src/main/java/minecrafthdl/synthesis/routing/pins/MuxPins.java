package minecrafthdl.synthesis.routing.pins;

import MinecraftGraph.*;
import minecrafthdl.MHDLException;
import minecrafthdl.synthesis.Gate;

/**
 * Created by Francis O'Brien - 4/7/2017 - 5:28
 */

public class MuxPins extends GatePins {
    MuxVertex mux_v;

    boolean a_requested = false;
    boolean b_requested = false;
    boolean s_requested = false;

    public MuxPins(Gate g, Vertex v, int offset, boolean top) {
        super(g, v, offset, top);
        if (v.type == VertexType.FUNCTION && ((Function)v).func_type == FunctionType.MUX){
            this.mux_v = (MuxVertex)v;
        } else {
            throw new MHDLException("CANT MAKE MUX PINS WITH NON_MUX VERTEX");
        }
    }

    @Override
    public Pin getNextPin(Vertex v){
        this.next_free_input_pin += 1;
        if (v == this.mux_v.a_vertex) {
            if (!a_requested){
                this.a_requested = true;
                return this.pins.get(0);
            }
            else throw  new RuntimeException("A PIN ALREADY REQUESTED");
        }
        else if (v == this.mux_v.b_vertex) {
            if (!b_requested){
                this.b_requested = true;
                return this.pins.get(2);
            }
            else throw  new RuntimeException("B PIN ALREADY REQUESTED");
        }
        else if (v == this.mux_v.s_vertex) {
            if (!s_requested){
                this.s_requested = true;
                return this.pins.get(1);
            }
            else throw  new RuntimeException("S PIN ALREADY REQUESTED");
        }
        else {
            throw  new RuntimeException("INPUT PIN MUST BE REQUESTED BY A VALID VERTEX");
        }
    }
}
