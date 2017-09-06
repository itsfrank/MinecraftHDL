//test multiple different gates

module test(a, b, c, d, e, f, g, out);
	input a, b, c, d, e, f, g;
	output out;
	wire x, y, z;
	assign x=a&b&c;
	assign y=x|d;
	assign z=x&g;
	assign out=z|y|f&a;


endmodule