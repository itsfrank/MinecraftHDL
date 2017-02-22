package MinecraftGraph;

public class Function extends Vertex {
	private FunctionType func_type;
	private int id;
	
	public Function(int i, VertexType v_t, FunctionType f_t, int x){
		
		super.type=v_t;
		super.bits_n=x;
		id=i;
		this.func_type=f_t;
	}
	
	
	public FunctionType getFunc_Type(){
			return this.func_type;
		
	}

	public int get_num_inputs(){
		return inputs;
	}

	
	
	
	@Override
	protected void addToNext(Vertex v){
		super.addToNext(v);
	}
	
	@Override 
	protected void addToBefore(Vertex v){
		super.addToBefore(v);
	}
	
	@Override
	public String getID(){
		return String.valueOf(id);
  }

	protected void addNext(Vertex v){
		connectedInputs++;
		super.addNext(v);
	}
	
}
