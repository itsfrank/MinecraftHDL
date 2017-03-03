package minecrafthdl.synthesis;

import minecrafthdl.Demo;
import minecrafthdl.Utils;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;

/**
 * Created by Francis on 11/12/2016.
 */
public class LogicGates {


    public static void main(String[] args) {
        IntermediateCircuit ic = new IntermediateCircuit();
        ic.loadGraph(Demo.create4bitmuxgraph());
        ic.printLayers();
    }

    public static Circuit IO(){
        Circuit gate = new Circuit(2, 1, 1);
        gate.setBlock(0, 0, 0, Blocks.WOOL.getDefaultState());
        return gate;
    }

    public static Circuit NOT(){
        Circuit gate = new Circuit(2, 1, 4);
        gate.setBlock(0, 0, 0, Blocks.WOOL.getDefaultState());
        gate.setBlock(0, 0, 1, Blocks.REDSTONE_TORCH.getDefaultState().withProperty(Utils.getPropertyByName(Blocks.REDSTONE_TORCH, "facing"), EnumFacing.SOUTH));
        gate.setBlock(0, 0, 2, Blocks.REDSTONE_WIRE.getDefaultState());
        gate.setBlock(0, 0, 3, Blocks.WOOL.getDefaultState());
        return gate;
    }

    public static Circuit AND(int inputs) {
        if (inputs == 0) throw new RuntimeException("Gate cannot have 0 inputs");
        int width;
        if (inputs == 1) width = 1;
        else width = inputs * 2;

        Circuit gate = new Circuit(width, 2, 5);

        gate.setBlock(0, 0, 2, Blocks.REDSTONE_TORCH.getDefaultState().withProperty(Utils.getPropertyByName(Blocks.REDSTONE_TORCH, "facing"), EnumFacing.SOUTH));
        gate.setBlock(0, 0, 3, Blocks.REDSTONE_WIRE.getDefaultState());
        gate.setBlock(0, 0, 4, Blocks.WOOL.getDefaultState());

        for (int i = 0; i < width - 1; i+=2) {
            gate.setBlock(i, 0, 0, Blocks.WOOL.getDefaultState());
            gate.setBlock(i, 0, 1, Blocks.WOOL.getDefaultState());
            gate.setBlock(i, 1, 0, Blocks.REDSTONE_TORCH.getDefaultState());
            gate.setBlock(i, 1, 1, Blocks.REDSTONE_WIRE.getDefaultState());
            if (i != width - 2) {
                gate.setBlock(i + 1, 0, 1, Blocks.WOOL.getDefaultState());
                if (i == 14) {
                    gate.setBlock(i + 1, 1, 1, Blocks.UNPOWERED_REPEATER.getDefaultState().withProperty(Utils.getPropertyByName(Blocks.UNPOWERED_REPEATER, "facing"), EnumFacing.EAST));
                } else {
                    gate.setBlock(i + 1, 1, 1, Blocks.REDSTONE_WIRE.getDefaultState());
                }
            }
        }

        return gate;
    }



    public static Circuit OR(int inputs) {
        if (inputs == 0) throw new RuntimeException("Gate cannot have 0 inputs");
        int width;
        if (inputs == 1) width = 1;
        else width = inputs * 2;

        Circuit gate = new Circuit(width, 2, 5);

        gate.setBlock(0, 0, 3, Blocks.REDSTONE_WIRE.getDefaultState());
        gate.setBlock(0, 0, 4, Blocks.WOOL.getDefaultState());

        for (int i = 0; i < width - 1; i+=2) {
            gate.setBlock(i, 0, 0, Blocks.WOOL.getDefaultState());
            gate.setBlock(i, 0, 1, Blocks.UNPOWERED_REPEATER.getDefaultState().withProperty(Utils.getPropertyByName(Blocks.UNPOWERED_REPEATER, "facing"), EnumFacing.NORTH));
            gate.setBlock(i, 0, 2, Blocks.REDSTONE_WIRE.getDefaultState());
            if (i != width - 2) {
                if (i == 14) {
                    gate.setBlock(i + 1, 0, 2, Blocks.UNPOWERED_REPEATER.getDefaultState().withProperty(Utils.getPropertyByName(Blocks.UNPOWERED_REPEATER, "facing"), EnumFacing.EAST));
                } else {
                    gate.setBlock(i + 1, 0, 2, Blocks.REDSTONE_WIRE.getDefaultState());
                }
            }
        }
        return gate;
    }

}
