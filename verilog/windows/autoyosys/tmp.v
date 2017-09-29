module sevenseg (
    input I1, I2,
    output S1, S2, S3, S4, S5, S6, S7
);

assign s1 = ~I2 || I1;
assign s2 = 1;
assign s3 = ~I1 || I2;
assign s4 = ~I2 || I1;
assign s5 = ~I2;
assign s6 = ~I1 && ~I2;
assign s7 = I1;

endmodule
