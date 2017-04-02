//module counter
// bits simple counter
//with enable and reset

module counter(
out,
enable, 
reset, 
clk
);
output [7:0] out;
input enable, reset, clk;
reg [7:0] out;

always @(posedge clk)
if (reset) begin
	out<=8'b0;
end
else if (enable) begin
	out<=out+1;	
end


endmodule