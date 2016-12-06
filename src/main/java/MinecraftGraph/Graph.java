package MinecraftGraph;

import java.util.ArrayList;

public class Graph {
	
	//only input vertices
	private ArrayList<Vertex> vertices;
	
	public Graph(){

		this.vertices = new ArrayList<Vertex>();
	}
	
	
	public void addVertex(Vertex v){
			vertices.add(v);
	}
	
	
	public void addEdge(Vertex v1, Vertex v2){
		if(v2.getType()==VertexType.FUNCTION){
			Function f=(Function)v2;
			
			if(f.canAdd()){
				v1.addNext(v2);
                v2.addPrev(v1);
			}
		}else{
			v1.addNext(v2);
            v2.addPrev(v1);
		}
	}
	
	
	public void removeEdge(Vertex v1, Vertex v2){
		v1.removeNeighbor(v2);
	}
	
	public void removeVertex(Vertex v){
		//remove v from all vertices neighbors
		
		for(Vertex ver: vertices){
			ver.removeNeighbor(v);
		}
		//remove v from vertices list
		vertices.remove(v);
		
	}
	
	public ArrayList<Vertex> getVertices(){
		
		return vertices;
		
	}
}
