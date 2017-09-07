[comment]: Images
[mux4_short]: https://github.com/itsFrank/MinecraftHDL/blob/pre_release/screenshots/mux4_short.png?raw=true


# Minecraft HDL

Minecraft HDL is a digital synthesis flow for minecraft redstone circuits. It is an attempt to use industry standard design tools and methods to generate digital cicuits with redstone.

### Example:

This file `multiplexer4_1.v` is a 6 input - 1 output circuit that selects one of the first 4 inputs (a, b, c, d) as the output based on the value of the last 2 inputs (x, y)

```verilog
module multiplexer4_1 ( a ,b ,c ,d ,x ,y ,dout );

output dout ;
input a, b, c, d, x, y;

assign dout = (a & (~x) & (~y)) |
     (b & (~x) & (y)) | 
     (c & x & (~y)) |
     (d & x & y);
endmodule
```
When synthesized through Minecraft HDL if produces this circuit:
![4to1mux][mux4_short]
With the 6 input on the right and the output on the left
---
# Quick Links
[Screenshots & Sample Circuits]()
[Getting Started - Installing and Using MinecraftHDL]()
[Background Theory - Digital Design & Verilog]()
[How MinecraftHDL Works - Methods, Algorithms, Tools]()
[Acknowledgments & Licensing]()
---
# About
MinecraftHDL was the final undergraduate design project made by three students in the Electrical Computer & Software Engineering Department at McGill University. 
It is by no means bug-free or even complete; It produces objectively inferior circuits to 'hand-made' redstone designs, and is not intended to me used in modded survival.
MinecraftHDL is an educational tool to illustrate on a macro-scopic scale how microelectronic digital circuits are designed and produced. It is a great way to introduce younger audiences to the world of digital design and can also be used to illustrate the difference between software and hardware design to undergraduate engineers taking their first RTL class.

