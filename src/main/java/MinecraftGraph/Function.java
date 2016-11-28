package MinecraftGraph;

public class Function extends Vertex {
	private FunctionType func_type;
	private int inputs;
	private int connectedInputs=0;
	
	public Function(VertexType v_t, FunctionType f_t, int x){
		super.type=v_t;
		this.func_type=f_t;
		this.inputs=x;
	}
	
	
	public FunctionType getFunc_Type(){
			return this.func_type;
		
	}
	
	protected boolean canAdd(){
		if(inputs==connectedInputs){
			return false;
		}
		else{
			return true;
		}
	}
	
	@Override
	protected void addNeighbor(Vertex v){
		connectedInputs++;
		super.addNeighbor(v);
	}
	
}
