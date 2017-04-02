module mux(a, b, sel, out );

input a,b, sel;
output out;

always @(a, b, sel)

if (sel) begin
	out<=a;
end
else begin
	out<=b;
end


endmodule

