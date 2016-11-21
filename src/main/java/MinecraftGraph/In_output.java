package MinecraftGraph;

import java.util.ArrayList;

public class In_output extends Vertex{
	private String name;
	
	public In_output(VertexType v_t, String n){
		
		super.type=v_t;
		this.name=n;

	}
	public  String getName(){
			return name;
		
	}
	


}
