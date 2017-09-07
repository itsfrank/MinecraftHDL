---
# WARNING

Do not add this mod to an existing world without making a backup, when circuits are generated, the entire volume that the circuit will take up is deleted and there is no way to know the size of the generated circuit before having generated it once.

This mod has the potential to destroy massive chunks of your world! It is intended to be used in creative, superflat worlds that are dedicated specifically to this mod.

---
# I - Installing

** If you already have miecraft skip #1, if you already have forge skip #2

1. Get and install Minecraft, launch the launcher at least one (to initialize the Minecraft directory)

2. Download the minecraft forge installer for minecraft 1.10.2 here:   
    https://files.minecraftforge.net/maven/net/minecraftforge/forge/index_1.10.2.html  
    Under "Download Recommended"  
    **Windows**: Get the `Windows Installer`  
    **Mac OSX**: Get the `Installer` JAR  
    Run the installer and select the "Minecraft Client" option when prompted.  

3. Download the MinecraftHDL release zip from here:  
    https://github.com/itsFrank/MinecraftHDL/releases/tag/0.0.1  
    Extract it somewhere (remember where!)  
    Then copy the JAR in the "mods" folder where minecraft forge is installed.  
    It should be in:  
    **Windows**: `C:\Users\YOUR_USERNAME\AppData\Roaming\.minecraft`  
    **Mac OSX**: `~/Library/Application Support/Minecraft`  

    If it's not there, you can see where that folder is located by:  
    launching the Minecraft launcher >  Clicking the menu hamburger button at the top right >   launch options > forge  
    On that page, in the "game directory" box is where forge is installed, there is a green arrow to open the directory in the explorer/finder  
    Copy the MinecraftHDL JAR in the mods directory in there  

4. *** **Mac OSX Only** *** Install yosys (yosys is an open source verilog synthesis tool)  
    Open a terminal window: Press CMD + Space and type `Terminal` 

    Install Homebrew (skip if already installed):  
    `$ /usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"` 
    
    Install yosys  
    `$ brew install yosys`  
    
    To make sure it's installed properly, in your terminal run "$ yosys" you should see license announcement and now be in the yosys shell. type "exit" to quit.  


You are now ready to use MinecraftHDL, launch a new creative world, and scroll to the bottom of the `misc` tab, there should be a new block called the Synthesizer.

# II - Using MinecraftHDL

### Synthesizing your Verilog files

1. Go back to where you extracted the `MinecraftHDL_*.zip` file  
    Copy the verilog files you want to generate in the `verilog/<your OS>` folder

2. Synthesize the file:  
**Windows**:  
    There are 2 ways to do this in windows  
    *Simple Way*:  
    1. rename your file to `myfile.v`  
    2. double click synth.bat  
    3. the generated output is the `myfile.v.json` file  

    *Slightly More Advanced Way:*  
    1. Open a the command prompt  
    2. Cd to the `verilog/windows` folder  
    3. run the command `> synth.bat <verilog_file_name>.v`  
    4. the generated output is the `<verilog_file_name>.v.json` file  

    **Mac OSX**:  
    There is only one way to do this in mac  
    1. Open a terminal window and CD into the `verilog/mac` folder where you copied your verilog file
    2. Run `$ ./synth.sh <verilog_file_name.v>`
    3. the generated output is the `<verilog_file_name>.v.json` file
    
    
 *** If the above methods did not work, see the `Manual Yosys Synthesis` section at the bottom of this document 


### Using the synthesized JSON files in Minecraft

First Launch Minecraft and create a new world in creative mode with the "superflat" world option and allow cheats: ON

In your inventory, go to the "misc" panel and scroll to the bottom, select the last block (should be our synthesizer block), place it and right-click it  
It should give you a chat message saying that it created the "verilog_designs" folder, use "esc" to close the synthesizer's GUI


Alt-tab out of minecraft and copy, the JSON you generated with yosys to the "verilog_designs" folder (it's in the same parent directory as the "mods" folder from earlier)


Default Paths:  
**Windows**: `C:\Users\YOUR_USERNAME\AppData\Roaming\.minecraft\verilog_designs`  
**Mac OSX**: `~/Library/Application Support/Minecraft/verilog_designs`


Back in minecraft, right click the synthesizer again, select your file and click generate.  
Then either use a button or a lever, and place it on the side of the synthesizer block by shift-right-clicking on the block with the button/lever  
Right-click the button or lever and your circuit should appear instantly.

*** If the button stays stuck in the "pressed" position, or if nothing at all happens, most likely an error occured when loading or generating the circuit. Check the minecraft console for more details. Your verilog might be 100% valid, however there are some limitations of MinecraftHDL which are outlined [here]()

# More Advanced Setup

Its kind of a pain to constantly copy the generated JSON file. you can make it easier by editing `synth.bat` or `synth.sh` (depending on your operating system) to copy the generated JSON to the `verilog_designs` folder.


**Windows**  
Change this line: `copy autoyosys\tmp.json %1.v.json`  
To this: `copy autoyosys\tmp.json path\to\verilog_designs\%1.v.json`

**Mac OSX**  
Change this line: `cp ./autoyosys/tmp.json ./$1.json`  
To this: `cp ./autoyosys/tmp.json /path/to/verilog_designs/%1.v.json`


# Manual Yosys Synthesis

If you want to generate the JSON without my scripts you can do the following:


Make sure yosys is installed, on mac follow the homebrew instructions above, on windows download it [here](http://www.clifford.at/yosys/download.html)


Then run yosys:  
**Windows**: double-click on yosys.exe  
**Mac OSX**: Just run `$ yosys` in your terminal


Once in the yosys shell, I use these command to generate the JSON netlist:
```
> read_verilog path/to/verilog/file.v
> hierarchy -check
> proc; opt; fsm; opt; memory; opt
> techmap; opt
> json -o path/where/JSON/will/be/generated.json
```