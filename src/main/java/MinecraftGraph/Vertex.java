package MinecraftGraph;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class Vertex {
	protected VertexType type;
	private ArrayList<Vertex> neighbors=new ArrayList<>();
	
	
	
	
	public VertexType getType(){
		return this.type;
	}
	


	
	
	protected void addNeighbor(Vertex v){
		this.neighbors.add(v);
	}
	public ArrayList<Vertex> getNeighbors(){
		return this.neighbors;
	}
	
	protected void removeNeighbor(Vertex v){
		neighbors.remove(v);
	}
	

	
}
