package minecrafthdl.synthesis;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

/**
 * Created by Francis on 10/28/2016.
 */
public class CircuitTest {
    ArrayList<ArrayList<ArrayList<String>>> blocks;

    public CircuitTest(int sizeX, int sizeY, int sizeZ){
        this.blocks = new ArrayList<ArrayList<ArrayList<String>>>();
        for (int x = 0; x < sizeX; x++) {
            this.blocks.add(new ArrayList<ArrayList<String>>());
            for (int y = 0; y < sizeY; y++) {
                this.blocks.get(x).add(new ArrayList<String>());
                for (int z = 0; z < sizeZ; z++) {
                    this.blocks.get(x).get(y).add(".");
                }
            }
        }
    }

    public void setBlock(int x, int y, int z, String blockstate) {
        this.blocks.get(x).get(y).set(z, blockstate);
    }

    public int getSizeX() {
        return this.blocks.size();
    }

    public int getSizeY() {
        return this.blocks.get(0).size();
    }

    public int getSizeZ() {
        return this.blocks.get(0).get(0).size();
    }

    public String getState(int x, int y, int z){
        return this.blocks.get(x).get(y).get(z);
    }
}
