//test multiple different gates

module test(a, b, c, d, out);
	input a, b, c, d;
	output out;
	wire x, y, z, w;
	assign x=a&b;
	assign y=c&d;
	assign z=x&y;
	assign w=x&y;

	assign out=z&w;


endmodule