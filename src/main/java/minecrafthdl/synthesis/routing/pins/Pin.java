package minecrafthdl.synthesis.routing.pins;

/**
 * Created by Francis on 2/22/2017.
 */
public class Pin {
    int x;
    int net = -1;
    public boolean top;

    public Pin(int x, boolean top){
        this.x = x;
        this.top = top;
    }

    public void setNet(int net_id, boolean out_net){
        if (!out_net && this.net != -1) {
            throw new RuntimeException("Should not assign pin to two different nets");
        }

        this.net = net_id;
    }

    public int xPos(){
        return this.x;
    }

    public int netID(){
        return this.net;
    }

    public boolean empty(){
        return false;
    }

    public boolean hasNet() {
        return this.netID() != -1;
    }
}
