module threebadder
(
 input x1, x2, x3,
 input y1, y2, y3,
 
 output o1, o2, o3, o4
 );

assign {o4, o3, o2, o1} =  {x3,x2,x1} * {y3,y2,y1};
 
endmodule