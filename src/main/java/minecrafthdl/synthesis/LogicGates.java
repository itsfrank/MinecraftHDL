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

    //A(0,0,3), B(2,0,3), S(5,0,1), Z(2,0,-1)
    public static Circuit MUX21(){
        Circuit gate = new Circuit(5, 2, 3);
        gate.setBlock(0, 0, 2, Blocks.REDSTONE_WIRE.getDefaultState());
        gate.setBlock(0, 1, 1, Blocks.REDSTONE_WIRE.getDefaultState());
        gate.setBlock(0, 0, 1, Blocks.WOOL.getDefaultState());
        gate.setBlock(1, 0, 1, Blocks.REDSTONE_WIRE.getDefaultState());
        gate.setBlock(2, 1, 1, Blocks.WOOL.getDefaultState());
        gate.setBlock(2, 0, 1, Blocks.REDSTONE_WIRE.getDefaultState());
        gate.setBlock(2, 0, 0, Blocks.REDSTONE_WIRE.getDefaultState());
        gate.setBlock(2, 1, 2, Blocks.REDSTONE_WIRE.getDefaultState());
        gate.setBlock(2, 0, 2, Blocks.WOOL.getDefaultState());
        gate.setBlock(3, 1, 1, Blocks.STICKY_PISTON.getDefaultState().withProperty(Utils.getPropertyByName(Blocks.STICKY_PISTON, "facing"), EnumFacing.WEST));
        gate.setBlock(3, 0, 1, Blocks.WOOL.getDefaultState());
        gate.setBlock(4, 0, 1, Blocks.REDSTONE_WIRE.getDefaultState());
        return gate;
    }

    //A(0,0,6), B(2,0,6), C(4,0,6), D(6,0,6), S1(10,0,4), S2(10,0,1), Z(6,0,-1)
    public static Circuit MUX41(){
        Circuit gate = new Circuit(10, 4, 6);
        gate.setBlock(0, 0, 5, Blocks.REDSTONE_WIRE.getDefaultState());
        gate.setBlock(2, 0, 5, Blocks.WOOL.getDefaultState());
        gate.setBlock(2, 1, 5, Blocks.REDSTONE_WIRE.getDefaultState());
        gate.setBlock(4, 0, 5, Blocks.REDSTONE_WIRE.getDefaultState());
        gate.setBlock(6, 0, 5, Blocks.WOOL.getDefaultState());
        gate.setBlock(6, 1, 5, Blocks.REDSTONE_WIRE.getDefaultState());
        gate.setBlock(0, 0, 4, Blocks.WOOL.getDefaultState());
        gate.setBlock(0, 1, 4, Blocks.REDSTONE_WIRE.getDefaultState());
        gate.setBlock(1, 0, 4, Blocks.REDSTONE_WIRE.getDefaultState());
        gate.setBlock(2, 0, 4, Blocks.REDSTONE_WIRE.getDefaultState());
        gate.setBlock(2, 1, 4, Blocks.WOOL.getDefaultState());
        gate.setBlock(3, 1, 4, Blocks.STICKY_PISTON.getDefaultState().withProperty(Utils.getPropertyByName(Blocks.STICKY_PISTON, "facing"), EnumFacing.WEST));
        gate.setBlock(3, 2, 4, Blocks.WOOL.getDefaultState());
        gate.setBlock(3, 3, 4, Blocks.REDSTONE_WIRE.getDefaultState());
        gate.setBlock(4, 0, 4, Blocks.WOOL.getDefaultState());
        gate.setBlock(4, 1, 4, Blocks.REDSTONE_WIRE.getDefaultState());
        gate.setBlock(4, 2, 4, Blocks.WOOL.getDefaultState());
        gate.setBlock(4, 3, 4, Blocks.REDSTONE_WIRE.getDefaultState());
        gate.setBlock(5, 0, 4, Blocks.REDSTONE_WIRE.getDefaultState());
        gate.setBlock(5, 2, 4, Blocks.WOOL.getDefaultState());
        gate.setBlock(5, 3, 4, Blocks.REDSTONE_WIRE.getDefaultState());
        gate.setBlock(6, 0, 4, Blocks.REDSTONE_WIRE.getDefaultState());
        gate.setBlock(6, 1, 4, Blocks.WOOL.getDefaultState());
        gate.setBlock(6, 2, 4, Blocks.WOOL.getDefaultState());
        gate.setBlock(6, 3, 4, Blocks.REDSTONE_WIRE.getDefaultState());
        gate.setBlock(7, 1, 4, Blocks.STICKY_PISTON.getDefaultState().withProperty(Utils.getPropertyByName(Blocks.STICKY_PISTON, "facing"), EnumFacing.WEST));
        gate.setBlock(7, 2, 4, Blocks.WOOL.getDefaultState());
        gate.setBlock(7, 3, 4, Blocks.REDSTONE_WIRE.getDefaultState());
        gate.setBlock(8, 1, 4, Blocks.WOOL.getDefaultState());
        gate.setBlock(8, 2, 4, Blocks.REDSTONE_WIRE.getDefaultState());
        gate.setBlock(9, 0, 4, Blocks.WOOL.getDefaultState());
        gate.setBlock(9, 1, 4, Blocks.REDSTONE_WIRE.getDefaultState());
        gate.setBlock(2, 0, 3, Blocks.REDSTONE_WIRE.getDefaultState());
        gate.setBlock(6, 0, 3, Blocks.REDSTONE_WIRE.getDefaultState());
        gate.setBlock(2, 0, 2, Blocks.REDSTONE_WIRE.getDefaultState());
        gate.setBlock(6, 0, 2, Blocks.WOOL.getDefaultState());
        gate.setBlock(6, 1, 2, Blocks.REDSTONE_WIRE.getDefaultState());
        gate.setBlock(2, 0, 1, Blocks.REDSTONE_WIRE.getDefaultState());
        gate.setBlock(3, 0, 1, Blocks.REDSTONE_WIRE.getDefaultState());
        gate.setBlock(4, 0, 1, Blocks.WOOL.getDefaultState());
        gate.setBlock(4, 1, 1, Blocks.REDSTONE_WIRE.getDefaultState());
        gate.setBlock(5, 0, 1, Blocks.REDSTONE_WIRE.getDefaultState());
        gate.setBlock(6, 0, 1, Blocks.REDSTONE_WIRE.getDefaultState());
        gate.setBlock(6, 1, 1, Blocks.WOOL.getDefaultState());
        gate.setBlock(7, 1, 1, Blocks.STICKY_PISTON.getDefaultState().withProperty(Utils.getPropertyByName(Blocks.STICKY_PISTON, "facing"), EnumFacing.WEST));
        gate.setBlock(8, 0, 1, Blocks.WOOL.getDefaultState());
        gate.setBlock(8, 1, 1, Blocks.REDSTONE_WIRE.getDefaultState());
        gate.setBlock(9, 0, 1, Blocks.REDSTONE_WIRE.getDefaultState());
        gate.setBlock(6, 0, 0, Blocks.REDSTONE_WIRE.getDefaultState());
        return gate;
    }

    //A(0,0,4), B(2,0,4), Z(3,0,-1)
    public static Circuit XOR(){
        Circuit gate = new Circuit(4, 1, 4);
        gate.setBlock(0, 0, 3, Blocks.REDSTONE_WIRE.getDefaultState());
        gate.setBlock(0, 0, 2, Blocks.REDSTONE_WIRE.getDefaultState());
        gate.setBlock(0, 0, 1, Blocks.WOOL.getDefaultState());
        gate.setBlock(0, 0, 0, Blocks.UNLIT_REDSTONE_TORCH.getDefaultState().withProperty(Utils.getPropertyByName(Blocks.REDSTONE_TORCH, "facing"), EnumFacing.NORTH));
        gate.setBlock(1, 0, 1, Blocks.UNPOWERED_REPEATER.getDefaultState().withProperty(Utils.getPropertyByName(Blocks.UNPOWERED_REPEATER, "facing"), EnumFacing.WEST));
        gate.setBlock(1, 0, 0, Blocks.UNPOWERED_REPEATER.getDefaultState().withProperty(Utils.getPropertyByName(Blocks.UNPOWERED_REPEATER, "facing"), EnumFacing.WEST));
        gate.setBlock(2, 0, 3, Blocks.REDSTONE_WIRE.getDefaultState());
        gate.setBlock(2, 0, 2, Blocks.STICKY_PISTON.getDefaultState().withProperty(Utils.getPropertyByName(Blocks.STICKY_PISTON, "facing"), EnumFacing.NORTH));
        gate.setBlock(2, 0, 1, Blocks.WOOL.getDefaultState());
        gate.setBlock(3, 0, 1, Blocks.REDSTONE_WIRE.getDefaultState());
        gate.setBlock(3, 0, 0, Blocks.REDSTONE_WIRE.getDefaultState());
        return gate;
    }

    //D(0,0,2), E(2,0,2), Q(4,0,0
    public static Circuit DFF(){
        Circuit gate = new Circuit(4, 1, 2);
        gate.setBlock(0, 0, 1, Blocks.REDSTONE_WIRE.getDefaultState());
        gate.setBlock(0, 0, 0, Blocks.REDSTONE_WIRE.getDefaultState());
        gate.setBlock(1, 0, 0, Blocks.REDSTONE_WIRE.getDefaultState());
        gate.setBlock(2, 0, 1, Blocks.UNPOWERED_REPEATER.getDefaultState().withProperty(Utils.getPropertyByName(Blocks.UNPOWERED_REPEATER, "facing"), EnumFacing.SOUTH));
        gate.setBlock(2, 0, 0, Blocks.UNPOWERED_REPEATER.getDefaultState().withProperty(Utils.getPropertyByName(Blocks.UNPOWERED_REPEATER, "facing"), EnumFacing.WEST));
        gate.setBlock(3, 0, 0, Blocks.REDSTONE_WIRE.getDefaultState());
        return gate;
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
