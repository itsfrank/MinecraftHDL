package GraphBuilder.json_representations;

import java.util.HashMap;

/**
 * Created by Francis O'Brien - 4/3/2017 - 19:47
 */

public class Module {

    public HashMap<String, JPort> ports;
    public HashMap<String, JCell> cells;

    public void print() {
        System.out.println("PORTS:");
        for (String p : ports.keySet()){
            System.out.println("\t" + p);
            ports.get(p).print(2);
        }

        System.out.println("Cells:");
        for (String c : cells.keySet()){
            System.out.println("\t" + c);
            cells.get(c).print(2);
        }
    }

    public void postInit(){
        for (JCell c : cells.values()) c.posInit();
    }
}
