package GraphBuilder.json_representations;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Francis O'Brien - 4/3/2017 - 19:39
 */

public class JCell extends Node{

    public String type;
    public HashMap<String, String> port_directions;
    public HashMap<String, ArrayList<Integer>> connections;

    public HashMap<String, JPort> ports = new HashMap<String, JPort>();

    @Override
    public ArrayList<Integer> getNets() {
        return null;
    }

    public void posInit(){
        for (String p : port_directions.keySet()){
            ports.put(p, new JPort(p, port_directions.get(p), connections.get(p)));
        }


    }

    public int numInputs(){
        int conns = 0;
        for (String dir: port_directions.values()){
            if (dir.equals("input")) conns += 1;
        }
        return conns;
    }

    public void print(int tabs) {
        System.out.println(JsonFile.tabs(tabs) + "type: " + type);
        System.out.println(JsonFile.tabs(tabs) + "Ports: " + type);

        for (String p : ports.keySet()){
            System.out.println(JsonFile.tabs(tabs + 1) + p);
            ports.get(p).print(tabs + 1);
        }
    }
}
