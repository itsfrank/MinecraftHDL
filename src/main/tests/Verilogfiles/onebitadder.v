module veriglLearn(
	input a, input b,
	input cin,
	output cout,
	output sum);
	
	
	wire x, y;
	//adder xor gates
	xor G1(sum, a, b, cin);
	and G2(x, a, b);
	and G3(y, sum, cin);
	or G4(cout, x, y);

	
	
	
	

	
endmodule