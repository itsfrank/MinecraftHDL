# Background
### Basic Digital Design
Computer chips today can have well over 2 billion transistors on them, that means that drawing digital circuits by hand when designing them is completely impractical and basically impossible. This is why we use computer-aided-design (CAD).  
Specifically, hardware description languages (HDL). There are two main HDLs, Verilog and VHDL; both have their strengths and waeknesses and both are used in industry. For this project we selected Verilog because the open-source synthesis suite Yosys works with Verilog.

When digital chips are designed, this is a very brief overview of the process:
1. Write how the circuid should behave using HDL
2. Synthesize the HDL file and get a blueprint for the circuit
3. Use the blueprint to "print" ths circuit in silicon. 

After step 3, you will be left with a silicon chip that behaves as described in the original HDL file.

### Digital Design
If you want to get into digital design and computer hardware, Tutorials Point has a great tutorial series that takes you from the beginning: counting in binary. All the way to more complex subjects like actual CPU architecture.

* https://www.tutorialspoint.com/computer_logical_organization/index.htm

(don't be affraid of the "Audience" and "Prerequisite" sections of the overview page, if you're building redstone circuits in minecraft, you've got all the necessary background info for this tutorial)

### Verilog
If you are interested in learning more about verilog, here are some good places to start:


* http://www.asic-world.com/verilog/veritut.html
* http://vol.verilog.com/


### Channel Routing
In MinecraftHDL we use a method called channel routing to connect the different layers of logic gates. Here is a great lecture on the subject:

www.yzuda.org/slides/20080711.ppt


### Yosys
Yosys is the tool we use to transform a verilog file into a netlist of gates, it is open source and supports almost the entirety of Verilog:

http://www.clifford.at/yosys/


