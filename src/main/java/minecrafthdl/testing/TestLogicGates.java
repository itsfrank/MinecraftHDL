package minecrafthdl.testing;

import minecrafthdl.Demo;
import minecrafthdl.synthesis.Gate;
import minecrafthdl.synthesis.IntermediateCircuit;
import minecrafthdl.synthesis.LogicGates;

/**
 * Created by Francis O'Brien - 3/4/2017 - 2:35 AM
 */

public class TestLogicGates extends LogicGates {



    public static void main(String[] args) {
        IntermediateCircuit ic = new IntermediateCircuit();
        ic.loadGraph(Demo.create4bitmuxgraph());
        ic.printLayers();
    }

    public static Gate IO(){
        TestGate gate = new TestGate(1, 1, 1, 1, 1, 0, 0, new int[]{0});
        gate.setBlock(0, 0, 0, "x");
        return gate;
    }

    public static Gate NOT(){
        TestGate gate = new TestGate(1, 1, 5, 1, 1, 0, 0, new int[]{0});
        gate.setBlock(0, 0, 0, "x");
        gate.setBlock(0, 0, 1, "i");
        gate.setBlock(0, 0, 2, "*");
        gate.setBlock(0, 0, 3, "*");
        gate.setBlock(0, 0, 4, "x");
        return gate;
    }

    public static Gate RELAY(){
        TestGate gate = new TestGate(1, 1, 5, 1, 1, 0, 0, new int[]{0});
        gate.setBlock(0, 0, 0, "x");
        gate.setBlock(0, 0, 1, "*");
        gate.setBlock(0, 0, 2, ">");
        gate.setBlock(0, 0, 3, "*");
        gate.setBlock(0, 0, 4, "x");
        return gate;
    }

    public static Gate AND(int inputs) {
        if (inputs == 0) throw new RuntimeException("Gate cannot have 0 inputs");
        int width;
        if (inputs == 1) width = 1;

        else width = (inputs * 2) - 1;

        TestGate gate = new TestGate(width, 2, 5, inputs, 1, 1, 0, new int[]{0});

        gate.setBlock(0, 0, 2, "i");
        gate.setBlock(0, 0, 3, "*");
        gate.setBlock(0, 0, 4, "x");

        for (int i = 0; i < width; i+=2) {
            gate.setBlock(i, 0, 0, "x");
            gate.setBlock(i, 0, 1, "x");
            gate.setBlock(i, 1, 0, "i");
            gate.setBlock(i, 1, 1, "*");

            if (i != width - 1) {
                gate.setBlock(i + 1, 0, 1, "x");
                if (i == 14) {
                    gate.setBlock(i + 1, 1, 1, ">");
                } else {
                    gate.setBlock(i + 1, 1, 1, "*");
                }
            }
        }

        return gate;
    }



    public static Gate OR(int inputs) {
        if (inputs == 0) throw new RuntimeException("Gate cannot have 0 inputs");
        int width;
        if (inputs == 1) width = 1;
        else width = (inputs * 2) - 1;

        TestGate gate = new TestGate(width, 2, 5, inputs, 1, 1, 0, new int[]{0});

        gate.setBlock(0, 0, 3, "*");
        gate.setBlock(0, 0, 4, "x");

        for (int i = 0; i < width; i+=2) {
            gate.setBlock(i, 0, 0, "x");
            gate.setBlock(i, 0, 1, ">");
            gate.setBlock(i, 0, 2, "*");
            if (i != width - 1) {
                if (i == 14) {
                    gate.setBlock(i + 1, 0, 2, ">");
                } else {
                    gate.setBlock(i + 1, 0, 2, "*");
                }
            }
        }
        return gate;
    }


}
