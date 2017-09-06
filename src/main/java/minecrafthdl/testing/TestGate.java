package minecrafthdl.testing;

import minecrafthdl.synthesis.Gate;

import java.util.ArrayList;

/**
 * Created by Francis O'Brien - 3/4/2017 - 2:31 AM
 */

public class TestGate extends Gate {

    private ArrayList<ArrayList<ArrayList<String>>> sblocks;

    public TestGate(int sizeX, int sizeY, int sizeZ, int num_inputs, int num_outputs, int input_spacing, int output_spacing, int[] output_lines) {
        super(sizeX, sizeY, sizeZ, num_inputs, num_outputs, input_spacing, output_spacing, output_lines);

        this.sblocks = new ArrayList<ArrayList<ArrayList<String>>>();
        for (int x = 0; x < sizeX; x++) {
            this.sblocks.add(new ArrayList<ArrayList<String>>());
            for (int y = 0; y < sizeY; y++) {
                this.sblocks.get(x).add(new ArrayList<String>());
                for (int z = 0; z < sizeZ; z++) {
                    this.sblocks.get(x).get(y).add(".");
                }
            }
        }
    }

    public void setBlock(int x, int y, int z, String blockstate) {
        this.sblocks.get(x).get(y).set(z, blockstate);
    }

    public void printGate(){
        for(int y = 0; y < this.getSizeX(); y++) {
            for (int z = 0; z < this.getSizeX(); z++) {
                for(int x = 0; x < this.getSizeX(); x++){
                    System.out.print(this.sblocks.get(x).get(y).get(z));
                }
                System.out.print("\n");
            }
            System.out.print("\n");
        }
    }
}
