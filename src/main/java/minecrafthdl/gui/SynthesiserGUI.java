package minecrafthdl.gui;

import minecrafthdl.block.blocks.Synthesizer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Francis on 3/25/2017.
 */
public class SynthesiserGUI extends GuiScreen {

    GuiButton synthesize_button, up_button, down_button;
    int synth_b_id = 0;
    int up_b_id = 1;
    int down_b_id = 2;

    String file_directory = "./verilog_designs";

    ArrayList<String> file_names = new ArrayList<>();
    int selected_file = -1;


    int window_width = 256;
    int window_height = 256;

    int window_left, window_top, filebox_left, filebox_top, filebox_right, filebox_bottom;

    int line_height = 10;
    int padding = 2;
    int total_height = line_height + (2 * padding);

    int start_file_index = 0;

    int block_x, block_y, block_z;
    World world;


    public SynthesiserGUI(World world, int x, int y, int z) {
        super();

        this.world = world;
        this.block_x = x;
        this.block_y = y;
        this.block_z = z;
    }


    @Override
    public void initGui() {


        this.window_left = centerObjectTL(this.window_width, this.width);
        this.window_top = centerObjectTL(this.window_height, this.height);

        this.filebox_left = window_left + 12;
        this.filebox_right = window_left + 150;
        this.filebox_top = window_top + 25;
        this.filebox_bottom = window_top + 130;

        this.buttonList.add(this.synthesize_button = new GuiButton(this.synth_b_id, this.width / 2 - 50, this.height / 2 + 52, 100, 20, "Generate Design"));
        this.buttonList.add(this.up_button = new GuiButton(this.up_b_id , this.filebox_right + 1, this.filebox_top - 1, 20, 20, "▲"));
        this.buttonList.add(this.down_button = new GuiButton(this.down_b_id, this.filebox_right + 1, this.filebox_bottom - 19, 20, 20, "▼"));


        System.out.println("Win L: " + this.window_left + "\tWin T: " + this.window_top);

        this.synthesize_button.enabled = false;
        this.file_names = this.readFileNames();
    }

    private ArrayList<String> readFileNames(){
        ArrayList<String> files = new ArrayList<>();
        File folder = new File(file_directory);

        if (folder == null) {
            folder.mkdir();
        }

        for (File f : folder.listFiles()){
            if (f.getName().toLowerCase().endsWith(".json")) {
                files.add(f.getName());
            }
        }

        return files;
    }

    private int centerObjectTL(int obj_dimension, int scrn_dimension){
        return (scrn_dimension / 2) - (obj_dimension / 3);
    }

    private int centerObjectBR(int obj_dimension, int scrn_dimension){
        return (scrn_dimension / 2) + (obj_dimension / 2);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(new ResourceLocation("minecrafthdl:textures/gui/synthesiser.png"));
        this.drawTexturedModalRect(centerObjectTL(this.window_width, this.width), centerObjectTL(this.window_height, this.height), 0, 0, this.window_width, this.window_height);

        this.fontRendererObj.drawString(
                "Synthesiser",
                (this.width / 2) - (this.fontRendererObj.getStringWidth("Synthesiser") / 2),
                (this.height / 2) - 75,
                0
        );

        this.drawFileNames();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void drawFileNames(){
        int current_height = this.filebox_top;
        int files_shown = 0;
        for (int i = this.start_file_index; i < this.file_names.size(); i++) {
            if (files_shown == 7) break;
            else  files_shown++;
            String file_name = this.file_names.get(i);
            int max_width = this.filebox_right - this.filebox_left - (2 * this.padding);
            if (this.fontRendererObj.getStringWidth(file_name) > max_width) {
                file_name = this.fontRendererObj.trimStringToWidth(file_name, max_width - this.fontRendererObj.getStringWidth("...")) + "...";
            }

            if (this.selected_file == i){

                this.drawGradientRect(
                        this.filebox_left,
                        current_height,
                        this.filebox_right,
                        current_height + this.total_height,
                        0xFFFFFFFF, 0xFFFFFFFF
                );

                current_height += this.padding;

                this.fontRendererObj.drawString(
                        file_name,
                        this.filebox_left + this.padding,
                        current_height,
                        0x00000000
                );

                current_height += this.line_height + this.padding;

            } else {
                current_height += this.padding;

                this.fontRendererObj.drawString(
                        file_name,
                        this.filebox_left + this.padding,
                        current_height,
                        0xFFFFFFFF
                );

                current_height += this.line_height + this.padding;
            }
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException{
        if(mouseX >= this.filebox_left && mouseX <= this.filebox_right && mouseY >= this.filebox_top && mouseY <= this.filebox_bottom) {
            int index = (mouseY - this.filebox_top + (this.start_file_index * this.line_height)) / this.total_height;
            if (index < this.file_names.size()){
                this.selected_file = index;
                this.synthesize_button.enabled = true;
            } else {
                this.selected_file = -1;
                this.synthesize_button.enabled = false;
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        System.out.println("Hi");
        if (button == this.synthesize_button) {
            if (this.selected_file < 0) {
                this.mc.displayGuiScreen(null);
                if (this.mc.currentScreen == null)
                    this.mc.setIngameFocus();
            }

            Synthesizer.file_to_gen = this.file_directory + "/" + this.file_names.get(this.selected_file);

            this.mc.displayGuiScreen(null);
            if (this.mc.currentScreen == null)
                this.mc.setIngameFocus();
        }
        if (button == this.up_button){
            if (this.start_file_index > 0) this.start_file_index-= 1;
        }
        if (button == this.down_button){
            if (this.start_file_index + 6 < this.file_names.size() - 1) this.start_file_index += 1;
        }



        super.actionPerformed(button);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }


}
