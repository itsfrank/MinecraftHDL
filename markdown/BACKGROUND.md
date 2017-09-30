# Background
### Basic Digital Design
Computer chips today can have well over 2 billion transistors on them, that means that drawing digital circuits by hand when designing them is completely impractical and basically impossible. This is why we use computer-aided-design (CAD).  
Specificall, hardware description languages (HDL). There are two main HDLs, Verilog and VHDL; both have their strengths and waeknesses and both are used in industry. For this project we selected Verilog because the open-source synthesis suite Yosys works with Verilog.

When digital chips are designed, this is a very brief overview of the process:
1. Write how the circuid should behave using HDL
2. Synthesize the HDL file and get a blueprint for the circuit
3. Use the blueprint to "print" ths circuit in silicon. 

After step 3, you will be left with a silicon chip that behaves as described in the original HDL file.

### Verilog
If you are interested in learning more about verilog, here are some good places to start:


* http://www.asic-world.com/verilog/veritut.html
* http://www.asic-world.com/verilog/verilog_one_day.html
* http://vol.verilog.com/

