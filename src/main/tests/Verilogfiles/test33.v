//test when can not merge

module test(a, b, c, d, out1, out2);
	input a, b, c, d;
	output out1, out2;
	wire x;
	assign x=a&b;
	assign out1=x&c;
	assign out2=x|d;

endmodule