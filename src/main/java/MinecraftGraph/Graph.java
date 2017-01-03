package MinecraftGraph;

import java.util.ArrayList;

public class Graph {
	
	//only input vertices
	private ArrayList<Vertex> vertices;
	
	public Graph(){
		vertices=new ArrayList<>();
	}
	
	
	public void addVertex(Vertex v){
			vertices.add(v);

		
	}
	
	
	public void addEdge(Vertex v1, Vertex v2){
		
		v1.addToNext(v2);
		v2.addToBefore(v1);
	
		
	}
	
	
	public void removeEdge(Vertex v1, Vertex v2){
		v1.removeNext(v2);
		v2.removeBefore(v1);
	}
	
	public void removeVertex(Vertex v){
		//remove v from all vertices neighbors
		
		for(Vertex ver: vertices){
			ver.removeNext(v);
			ver.removeBefore(v);
		}
		//remove v from vertices list
		vertices.remove(v);
		
	}
	
	public ArrayList<Vertex> getVertices(){
		
		return vertices;
		
	}
}
