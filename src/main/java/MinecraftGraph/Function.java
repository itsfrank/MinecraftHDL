package MinecraftGraph;

public class Function extends Vertex {
	public FunctionType func_type;
	protected int id;
	
	public Function(int i, FunctionType f_t, int x){
		
		super.type=VertexType.FUNCTION;
		super.bits_n=x;
		id=i;
		this.func_type=f_t;
	}
	
	
	public FunctionType getFunc_Type(){
			return this.func_type;
		
	}
	
	
	
	@Override
	public void addToNext(Vertex v){
		super.addToNext(v);
	}
	
	@Override 
	public void addToBefore(Vertex v){
		super.addToBefore(v);
	}

	@Override
	public String getID(){
		return String.valueOf(id);
		
	}

	public int get_num_inputs() {
		return this.bits_n;
	}
}
