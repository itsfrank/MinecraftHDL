package minecrafthdl;

import minecrafthdl.block.ModBlocks;
import minecrafthdl.gui.MinecraftHDLGuiHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

/**
 * Created by Francis on 10/5/2016.
 */
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent e) {
        ModBlocks.createBlocks();
    }

    public void init(FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(MinecraftHDL.instance, new MinecraftHDLGuiHandler());
    }

    public void postInit(FMLPostInitializationEvent e) {

    }
}
