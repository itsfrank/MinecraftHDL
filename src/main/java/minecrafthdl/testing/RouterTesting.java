package minecrafthdl.testing;

import minecrafthdl.Demo;
import minecrafthdl.synthesis.Circuit;
import minecrafthdl.synthesis.IntermediateCircuit;

/**
 * Created by Francis O'Brien - 3/4/2017 - 1:54 AM
 */

public class RouterTesting {

    public static void main(String[] args){
        Circuit.TEST = true;
        IntermediateCircuit ic = new IntermediateCircuit();
        ic.loadGraph(Demo.create4bitmuxgraph());
        ic.buildGates();
        ic.printLayers();
        ic.routeChannels();
        ic.channels.get(1).genChannelCircuit();
    }



}
