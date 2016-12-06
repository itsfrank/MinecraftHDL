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
        Circuit gate = new Circuit(1, 1, 1);
        gate.setBlock(0, 0, 0, Blocks.WOOL.getDefaultState());
        return gate;
    }

    public static Circuit NOT(){
        Circuit gate = new Circuit(1, 1, 4);
        gate.setBlock(0, 0, 0, Blocks.WOOL.getDefaultState());
        gate.setBlock(0, 0, 1, Blocks.REDSTONE_TORCH.getDefaultState().withProperty(Utils.getPropertyByName(Blocks.REDSTONE_TORCH, "facing"), EnumFacing.SOUTH));
        gate.setBlock(0, 0, 2, Blocks.REDSTONE_WIRE.getDefaultState());
        gate.setBlock(0, 0, 3, Blocks.WOOL.getDefaultState());
        return gate;
    }

    public static Circuit AND(int inputs) {
        if (inputs == 0) throw new RuntimeException("Gate cannot have 0 inputs");
        boolean even = (inputs % 2) == 0;
        int width;
        if (even){
            width = 3 + (2 * (inputs - 2));
        } else {
            width = 1 + (2 * (inputs - 1));
        }
        Circuit gate = new Circuit(width, 2, 5);

        int half_width = (width - 1) / 2;

        for (int i = 0; i < half_width; i+=2) {
            gate.setBlock(i, 0, 0, Blocks.WOOL.getDefaultState());
            gate.setBlock(i, 0, 1, Blocks.WOOL.getDefaultState());
            gate.setBlock(i, 1, 0, Blocks.REDSTONE_TORCH.getDefaultState());
            gate.setBlock(i, 1, 1, Blocks.REDSTONE_WIRE.getDefaultState());
            gate.setBlock(i + 1, 0, 1, Blocks.WOOL.getDefaultState());
            gate.setBlock(i + 1, 1, 1, Blocks.REDSTONE_WIRE.getDefaultState());
        }

        gate.setBlock(half_width, 0, 2, Blocks.REDSTONE_TORCH.getDefaultState().withProperty(Utils.getPropertyByName(Blocks.REDSTONE_TORCH, "facing"), EnumFacing.SOUTH));
        gate.setBlock(half_width, 0, 3, Blocks.REDSTONE_WIRE.getDefaultState());
        gate.setBlock(half_width, 0, 4, Blocks.WOOL.getDefaultState());

        for (int i = even ? half_width : half_width - 1; i < (half_width * 2) + 1; i+=2) {
            gate.setBlock(i + 1, 0, 0, Blocks.WOOL.getDefaultState());
            gate.setBlock(i + 1, 0, 1, Blocks.WOOL.getDefaultState());
            gate.setBlock(i + 1, 1, 0, Blocks.REDSTONE_TORCH.getDefaultState());
            gate.setBlock(i + 1, 1, 1, Blocks.REDSTONE_WIRE.getDefaultState());
            gate.setBlock(i, 0, 1, Blocks.WOOL.getDefaultState());
            gate.setBlock(i, 1, 1, Blocks.REDSTONE_WIRE.getDefaultState());
        }

        return gate;
    }

    public static Circuit OR(int inputs) {
        if (inputs == 0) throw new RuntimeException("Gate cannot have 0 inputs");
        boolean even = (inputs % 2) == 0;
        int width;
        if (even){
            width = 3 + (2 * (inputs - 2));
        } else {
            width = 1 + (2 * (inputs - 1));
        }
        Circuit gate = new Circuit(width, 2, 5);

        int half_width = (width - 1) / 2;

        for (int i = 0; i < half_width; i+=2) {
            gate.setBlock(i, 0, 0, Blocks.WOOL.getDefaultState());
            gate.setBlock(i, 0, 1, Blocks.UNPOWERED_REPEATER.getDefaultState().withProperty(Utils.getPropertyByName(Blocks.UNPOWERED_REPEATER, "facing"), EnumFacing.NORTH));
            gate.setBlock(i, 0, 2, Blocks.REDSTONE_WIRE.getDefaultState());
            gate.setBlock(i + 1, 0, 2, Blocks.REDSTONE_WIRE.getDefaultState());
        }

        gate.setBlock(half_width, 0, 3, Blocks.REDSTONE_WIRE.getDefaultState());
        gate.setBlock(half_width, 0, 4, Blocks.WOOL.getDefaultState());

        for (int i = even ? half_width : half_width - 1; i < (half_width * 2) + 1; i+=2) {
            gate.setBlock(i + 1, 0, 0, Blocks.WOOL.getDefaultState());
            gate.setBlock(i + 1, 0, 1, Blocks.UNPOWERED_REPEATER.getDefaultState().withProperty(Utils.getPropertyByName(Blocks.UNPOWERED_REPEATER, "facing"), EnumFacing.NORTH));
            gate.setBlock(i + 1, 0, 2, Blocks.REDSTONE_WIRE.getDefaultState());
            gate.setBlock(i, 0, 2, Blocks.REDSTONE_WIRE.getDefaultState());
        }

        return gate;
    }


    public static CircuitTest genAndGateTest(int inputs) {
        if (inputs == 0) throw new RuntimeException("Gate cannot have 0 inputs");
        boolean even = (inputs % 2) == 0;
        int width;
        if (even){
            width = 3 + (2 * (inputs - 2));
        } else {
            width = 1 + (2 * (inputs - 1));
        }
        CircuitTest gate = new CircuitTest(width, 2, 5);

        int half_width = (width - 1) / 2;

        for (int i = 0; i < half_width; i+=2) {
            gate.setBlock(i, 0, 0, "#");
            gate.setBlock(i, 0, 1, "#");
            gate.setBlock(i, 1, 0, "i");
            gate.setBlock(i, 1, 1, "*");
            gate.setBlock(i + 1, 0, 1, "#");
            gate.setBlock(i + 1, 1, 1, "*");
        }

        gate.setBlock(half_width, 0, 2, "i");
        gate.setBlock(half_width, 0, 3, "*");
        gate.setBlock(half_width, 0, 4, "#");

        for (int i = even ? half_width : half_width - 1; i < (half_width * 2) + 1; i+=2) {
            gate.setBlock(i + 1, 0, 0, "#");
            gate.setBlock(i + 1, 0, 1, "#");
            gate.setBlock(i + 1, 1, 0, "i");
            gate.setBlock(i + 1, 1, 1, "*");
            gate.setBlock(i, 0, 1, "#");
            gate.setBlock(i, 1, 1, "*");
        }

        return gate;
    }

}
