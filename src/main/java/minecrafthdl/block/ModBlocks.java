package minecrafthdl.block;

import minecrafthdl.block.blocks.Synthesizer;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Francis on 10/5/2016.
 */
public final class ModBlocks {

    public static Block tutorialBlock;

    public static void createBlocks() {
        GameRegistry.registerBlock(tutorialBlock = new Synthesizer("tutorial_block"), "tutorial_block");
    }
}
