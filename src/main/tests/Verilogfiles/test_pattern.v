module test(in, clk, out);

input in, clk;
output out;
reg out;

parameter ONE=2'b00, TWO=2'b01, THREE=2'b10;
reg [1:0] state;
wire [1:0] next_state;

assign next_state=fsm_state(state, in);

function [1:0] fsm_state;
	
	input [1:0] state;
	input in;
	case(state) 
		ONE: if(in==1'b1) begin
			fsm_state=ONE;
			end
			else begin
				fsm_state=TWO;
				end
		TWO: if (in==1'b1) begin
				fsm_state=ONE;
				end
				else begin
				fsm_state=THREE;
				end
		THREE: if(in==1'b1) begin
					fsm_state=THREE;
					end
					else begin
					fsm_state=ONE;
					end
		default: fsm_state=ONE;		//shoud never be accessed
		endcase
	endfunction
//define states

always @(posedge clk)
begin
state<=next_state;
end

	
always @(posedge clk)
begin

case(state)
ONE: out<=#1 1'b0;
TWO: out<=#1 1'b0;
THREE: out<=#1 1'b1;
default: out<=#1 1'b0;

endcase

end


		
endmodule