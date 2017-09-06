package minecrafthdl.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/**
 * Created by Francis on 3/25/2017.
 */
public class MinecraftHDLGuiHandler implements IGuiHandler {

    public static final int SYNTHESISER_GUI_ID = 0;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == SYNTHESISER_GUI_ID)
            return new SynthesiserGUI(world, x, y, z);
        return null;
    }

}
