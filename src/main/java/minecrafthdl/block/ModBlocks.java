package minecrafthdl.block;

import minecrafthdl.block.blocks.Synthesizer;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Francis on 10/5/2016.
 */
public final class ModBlocks {

    public static Block synthesizer;

    public static void createBlocks() {
        GameRegistry.registerBlock(synthesizer = new Synthesizer("synthesizer"), "synthesizer");
    }
}
