[1bfa]: https://github.com/itsFrank/MinecraftHDL/blob/master/screenshots/1bit_fa.png?raw=true 
[1bfa_i]: https://github.com/itsFrank/MinecraftHDL/blob/master/screenshots/1bit_fa_input.png?raw=true
[1bfa_o]: https://github.com/itsFrank/MinecraftHDL/blob/master/screenshots/1bit_fa_output.png?raw=true
[lfu]: https://github.com/itsFrank/MinecraftHDL/blob/master/screenshots/lfu.png?raw=true 
[7seg]: https://github.com/itsFrank/MinecraftHDL/blob/master/screenshots/2bit_7seg.png?raw=true 
[7seg_gif]: https://github.com/itsFrank/MinecraftHDL/blob/master/screenshots/7seg.gif?raw=true
# Sample Circuits and Screenshots

### 1Bit Full Adder
This circuit calculates the result of adding two bits together as well as a carried value form a previous addition.
Chaining multiple of these adders together by connecting the Cout and Cin of subsequent adders is how simple multiple bit adders work.
```verilog
module fulladder
(
    input x, y, cin,
    output A, cout
);
 
assign {cout,A} =  cin + y + x;
 
endmodule
```
**Minecraft Circuit**
![1bit_full_adder][1bfa] 
 Sample Input  
 ![1bit_full_adder_input][1bfa_i]
 Sample Output  
  ![1bit_full_adder_output][1bfa_o]  
 In this case we are calculating '1' + '1' with no carry in. The result is the binary number "10" however since out output is only 1 bit, the output A is actually '0' and the carry out Cout is set to '1'.


---


### Logical Funtioning Unit
This circuit is an "all-in-one" 1-bit boolean calculator. It has 2 control signals that select the operation we want to do and 2 inputs. The output is the result of the operation selected by C1 and C2 using the inputs a and b.
```verilog
module lfu (
    input C1, C2, a, b,
    output o
);

always @(C1, C2, a, b)
begin
  if (C1 == 0 && C2 == 0)
    o = a & b;
  else if (C1 == 0 && C2 == 1)
    o = a | b;
  else if (C1 == 1 && C2 == 0)
    o = a ^ b;
  else
    o = 0;
end

endmodule
```
The control values correspond to the folowing operations:
* '0', '0' : a AND b
* '0', '1' : a OR b,
* '1', '0' : a XOR b,
* '1', '1' : Nothing, output is set to 0

**Minecraft Circuit**
![lfu][lfu] 


 ---


### 2Bit 7-Segment Display Decoder
This circuit controls the state of the 7 segments of a 7-segment display. For this example we only decode a 2Bit number (that means we can display numbers 0, 1, 2, and 3); We chose 2Bits because a full 0-9 decoder would have been too large. However MinecraftHDL does generate it properly.
```verilog
module sevenseg (
    input I1, I2,
    output S1, S2, S3, S4, S5, S6, S7
);

assign S1 = ~I2 | I1;
assign S2 = 1;
assign S3 = ~I1 | I2;
assign S4 = ~I2 | I1;
assign S5 = ~I2;
assign S6 = ~I1 & ~I2;
assign S7 = I1;

endmodule
```
**Minecraft Circuit**
![7seg][7seg] 
Here, the display was built and wired to the outputs by hand, however all the logic and circuit were generated from the verilog above.

**Gif of the display in action!**

![7seg][7seg_gif]
 
