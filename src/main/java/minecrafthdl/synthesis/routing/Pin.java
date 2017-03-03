package minecrafthdl.synthesis.routing;

import java.util.ArrayList;

/**
 * Created by Francis on 2/22/2017.
 */
public class Pin {
    int x;
    boolean output;
    ArrayList<Pin> connections;

    public Pin(int x, boolean output){
        this.x = x;
        this.output = output;
        if (output) this.connections = new ArrayList<>();
    }
}
