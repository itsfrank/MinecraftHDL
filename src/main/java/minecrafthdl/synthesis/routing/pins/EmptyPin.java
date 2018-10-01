package minecrafthdl.synthesis.routing.pins;

import minecrafthdl.MHDLException;

/**
 * Created by Francis O'Brien - 3/4/2017 - 5:41 AM
 */

public class EmptyPin extends Pin {
    public EmptyPin(int x, boolean top) {
        super(x, top, null);
    }

    public void setNet(int net_id, boolean out_net){
        throw new MHDLException("Cant set net of empty pin");
    }

    public boolean empty(){
        return true;
    }
}
