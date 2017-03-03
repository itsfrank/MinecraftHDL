package minecrafthdl;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = MinecraftHDL.MODID, name = MinecraftHDL.MODNAME, version = MinecraftHDL.VERSION)
public class MinecraftHDL
{
    public static final String MODID = "minecrafthdl";
    public static final String MODNAME = "Minecraft HDL";
    public static final String VERSION = "1.0";

    @SidedProxy(clientSide="minecrafthdl.ClientProxy", serverSide="minecrafthdl.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static MinecraftHDL instance = new MinecraftHDL();

    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        proxy.preInit(e);
    }

    @EventHandler
    public void init(FMLInitializationEvent e)
    {
        proxy.init(e);
        // some example code
        System.out.println("DIRT BLOCK >> "+Blocks.DIRT.getUnlocalizedName());
        MinecraftForge.EVENT_BUS.register(this);
        GameRegistry.addShapelessRecipe(new ItemStack(Items.DIAMOND, 32), Blocks.DIRT);

    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }

//    @SubscribeEvent
//    public void onPlayerTick(TickEvent.PlayerTickEvent tick)
//    {
//        FMLLog.getLogger().log(Level.INFO, "Player Tick");
//    }
}
