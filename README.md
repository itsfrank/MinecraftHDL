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
When synthesized through Minecraft HDL if produces this circuit
