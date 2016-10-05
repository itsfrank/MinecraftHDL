package com.example.examplemod;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.Level;

@Mod(modid = MinecraftHDL.MODID, name = MinecraftHDL.MODNAME, version = MinecraftHDL.VERSION)
public class MinecraftHDL
{
    public static final String MODID = "MinecraftHDL";
    public static final String MODNAME = "Minecraft HDL";
    public static final String VERSION = "1.0";

    @Mod.Instance
    public static MinecraftHDL instance = new MinecraftHDL();

    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        System.out.println("DIRT BLOCK >> "+Blocks.DIRT.getUnlocalizedName());
        MinecraftForge.EVENT_BUS.register(this);
        GameRegistry.addShapelessRecipe(new ItemStack(Items.DIAMOND, 32), Blocks.DIRT);

    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {
    }

//    @SubscribeEvent
//    public void onPlayerTick(TickEvent.PlayerTickEvent tick)
//    {
//        FMLLog.getLogger().log(Level.INFO, "Player Tick");
//    }
}
