module test(a, b);

input [3:0]a;
output [1:0]b;
assign b[0]=a[0]&a[2];
assign b[1]=a[1] | a[3];

endmodule