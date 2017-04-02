package minecrafthdl.block.blocks;

import minecrafthdl.Demo;
import minecrafthdl.block.BasicBlock;
import minecrafthdl.synthesis.IntermediateCircuit;
import minecrafthdl.synthesis.LogicGates;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.io.IOException;

/**
 * Created by Francis on 10/28/2016.
 */
public class Synthesizer extends BasicBlock {

    public static final PropertyBool TRIGGERED = PropertyBool.create("triggered");

    public Synthesizer(String unlocalizedName) {
        super(unlocalizedName);
        this.setDefaultState(this.blockState.getBaseState().withProperty(TRIGGERED, false));
        System.out.println("hello");
    }



    @SuppressWarnings("deprecation")
    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn) {
        if(!worldIn.isRemote) {
            if(!state.getValue(TRIGGERED)){
                if (worldIn.getRedstonePower(pos.north(), EnumFacing.NORTH) > 0) {
                    //Negative Z is receiving power
                    worldIn.setBlockState(pos, state.withProperty(TRIGGERED, true));
                    IntermediateCircuit ic = new IntermediateCircuit();
                    ic.loadGraph(Demo.create4bitmuxgraph());
                    ic.buildGates();
                    ic.genCircuit().placeInWorld(worldIn, pos.north(), EnumFacing.NORTH);

                }else if (worldIn.getRedstonePower(pos.east(), EnumFacing.EAST) > 0) {
                    //Negative X is receiving power
                    worldIn.setBlockState(pos, state.withProperty(TRIGGERED, true));
                }else if (worldIn.getRedstonePower(pos.south(), EnumFacing.SOUTH) > 0) {
                    //Positive Z is receiving power
                    worldIn.setBlockState(pos, state.withProperty(TRIGGERED, true));
                    LogicGates.RELAY().placeInWorld(worldIn, pos, EnumFacing.SOUTH);
                }else if (worldIn.getRedstonePower(pos.west(), EnumFacing.WEST) > 0) {
                    //Positive X is receiving power
                }else if (worldIn.getRedstonePower(pos.up(), EnumFacing.UP) > 0) {
                    //Positive Y is receiving power

//                    System.out.println(System.getProperty("user.dir"));
                    String[] command = {"cmd.exe","/C","test.bat"};
                    ProcessBuilder pb = new ProcessBuilder(command);
                    pb.redirectError(ProcessBuilder.Redirect.INHERIT);
                    pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
                    try {
                        pb.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }else if (worldIn.getRedstonePower(pos.down(), EnumFacing.DOWN) > 0) {
                    //Negative Y is receiving power
                } else {
                    worldIn.setBlockState(pos, state.withProperty(TRIGGERED, false));
                }
            } else {
                if (!worldIn.isBlockPowered(pos)) {
                    worldIn.setBlockState(pos, state.withProperty(TRIGGERED, false));
                }
            }

            worldIn.notifyNeighborsOfStateChange(pos, this);
        }
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, TRIGGERED);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(TRIGGERED, (meta) > 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(TRIGGERED) ? 1 : 0;
    }
}
