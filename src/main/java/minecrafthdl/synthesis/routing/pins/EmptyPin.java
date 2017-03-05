package minecrafthdl.synthesis.routing.pins;

/**
 * Created by Francis O'Brien - 3/4/2017 - 5:41 AM
 */

public class EmptyPin extends Pin {
    public EmptyPin(int x, boolean top) {
        super(x, top);
    }

    public void setNet(int net_id, boolean out_net){
        throw new RuntimeException("Cant set net of empty pin");
    }

    public boolean empty(){
        return true;
    }
}
