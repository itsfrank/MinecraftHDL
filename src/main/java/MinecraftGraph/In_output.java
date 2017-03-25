package MinecraftGraph;

import java.util.ArrayList;

public class In_output extends Vertex{
	private String name;
	
	public In_output(int num, VertexType v_t, String n){
		
		super.bits_n=num;
		super.type=v_t;
		this.name=n;

	}
	
	@Override
	public  String getID(){
			return name;
		
	}
	


}
