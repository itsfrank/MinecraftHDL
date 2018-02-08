package minecrafthdl;

import minecrafthdl.synthesis.CircuitTest;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;

/**
 * Created by Francis on 10/28/2016.
 */
public class Utils {

    public static IProperty getPropertyByName(Block block, String name){
        for (IProperty prop : block.getBlockState().getProperties()){
            if (prop.getName().equals(name)){
                return prop;
            }
        }
        return null;
    }

    public static void printProperties(Block block){
        for (IProperty prop : block.getBlockState().getProperties()){
            System.out.println(prop.getName());
            System.out.println(prop.getAllowedValues());

        }
    }

    public static void printCircuit(CircuitTest circuit){
        for (int y = 0; y < circuit.getSizeY(); y++) {
            for (int x = 0; x < circuit.getSizeX(); x++) {
                for (int z = 0; z < circuit.getSizeZ(); z++) {
                    System.out.print(circuit.getState(x, y, z));
                }
                System.out.print("\n");
            }
            System.out.print("\n\n");
        }
    }

}
