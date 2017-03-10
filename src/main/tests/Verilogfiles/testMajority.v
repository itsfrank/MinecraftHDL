module testMajority(a, b, c, d);
input a, b, c;
output d;

assign d=(a&c)|(a&b)|(b&c);


endmodule