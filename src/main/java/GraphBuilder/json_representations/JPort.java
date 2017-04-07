package GraphBuilder.json_representations;

import java.util.ArrayList;

/**
 * Created by Francis O'Brien - 4/3/2017 - 19:39
 */

public class JPort extends Node {
    public String name;
    public String direction;
    public ArrayList<Integer> bits;

    public JPort(String name, String direction, ArrayList<Integer> bits) {
        this.name = name;
        this.direction = direction;
        this.bits = bits;
    }

    @Override
    public ArrayList<Integer> getNets() {
        return null;
    }

    public void print(int tabs) {
        System.out.println(JsonFile.tabs(tabs) + "direction: " + direction);
        System.out.println(JsonFile.tabs(tabs) + "bits:");
        for(int i : bits){
            System.out.println(JsonFile.tabs(tabs + 1) + i);

        }

    }

}
