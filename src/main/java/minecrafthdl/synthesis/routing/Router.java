package minecrafthdl.synthesis.routing;

import minecrafthdl.synthesis.Circuit;

import java.util.ArrayList;

/**
 * Created by Francis on 2/22/2017.
 */
public class Router {

    public static ArrayList<Pin> createInputPins(Circuit circuit, int offset) {
        int num_inputs = circuit.getSizeX()/2;

        ArrayList<Pin> pins = new ArrayList<>();

        for(int i = 0; i < num_inputs; i++){
            pins.add(new Pin((2 * i) + offset, false));
        }

        return pins;
    }

}
