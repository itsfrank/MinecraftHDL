module multiplexer4_1 ( a ,b ,c ,d ,x ,y ,dout );

output dout ;

input a ;
input b ;
input c ;
input d ;
input x ;
input y ;

assign dout = (a & (~x) & (~y)) |
     (b & (~x) & (y)) | 
     (c & x & (~y)) |
     (d & x & y);

endmodule
