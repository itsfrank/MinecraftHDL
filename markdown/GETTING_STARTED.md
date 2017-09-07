If you want to try it here are the steps to setting up:
1. get and install Minecraft, launch the launcher at least one (to initialize the Minecraft directory)

2. Download the minecraft forge installer for minecraft 1.10.2 here: 
https://files.minecraftforge.net/maven/net/minecraftforge/forge/index_1.10.2.html
Under "Download Recommended" get the "Installer"
It will download a JAR that you will run and select the "Minecraft Client" option when prompted.

3. Download the MinecraftHDL pre-release zip from here:
https://github.com/itsFrank/MinecraftHDL/releases/tag/0.0.1
Extract it somewhere
Then copy the JAR in the "mods" folder where minecraft forge is installed.
It should be in "~/Library/Application Support/Minecraft"
If not, you can see where that folder is located by launching the Minecraft launcher >  Clicking the menu hamburger button at the top right >   launch options > forge
On that page, in the "game directory" box is where forge is installed, on windows there is a green arrow to open the directory in the explorer/finder
Copy the JAR in the mods directory in there

4. Install yosys (using the terminal):
1. Install Homebrew (skip if already installed)
   $ /usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
Or maybe $ ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)" < /dev/null 2> /dev/null

2. Install yosys
$ brew install yosys

3. To make sure it's installed properly, in your terminal run "$ yosys" you should see a sort of announcement and now be in the yosys shell. type "exit" to quit.

5. Grab a verilog file and copy it into the "verilog/<your OS>" folder (I'm assuming you're on mac)

6. Open a terminal window and CD into the "verilog/mac" folder where you copied your verilog file
Run $ ./synth.sh <yourVerilogFile.v>
This should generate yourVerilogFile.v.json in the same directory
*** If it doesn't work, rename your file to tmp.v copy it into the autoyosys directory. Don't CD into autoyosys and run $yosys -s ./autoyosys/auto.ysy
        this will generate a file called tmp.json inside autoyosys which is equivalent to yourVerilogFile.v.json

7. Launch Minecraft and create a new world in creative mode with the "superflat" world option and allow cheats: ON

8. In your inventory, go to the "misc" panel and scroll to the bottom, select the last block (should be our synthesizer block), place it and right-click it
It should give you a chat message that it created the "verilog_designs" folder, use "esc" to close the synthesizer's GUI
Alt-tab out of minecraft and copy, the JSON you generated with yosys to the "verilog_designs" folder (it's in the same parent directory as the "mods" folder from earlier)
Back in minecraft, right click the synthesizer again, select your file and click generate.
Then either use a button or a lever, and place it on the side of the synthesizer block by shift-right-clicking on the block with the button/lever
Right-click the button or lever and your circuit should appear.
