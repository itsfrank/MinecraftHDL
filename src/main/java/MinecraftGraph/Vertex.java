package MinecraftGraph;

import java.util.ArrayList;

public abstract class Vertex {
	protected VertexType type;
	private ArrayList<Vertex> next = new ArrayList<>();
	private ArrayList<Vertex> prev = new ArrayList<>();

	
	
	
	public VertexType getType(){
		return this.type;
	}
	


	
	
	protected void addNext(Vertex v){
		this.next.add(v);
	}
	public ArrayList<Vertex> getNext(){
		return this.next;
	}
	protected void addPrev(Vertex v){
		this.prev.add(v);
	}
	public ArrayList<Vertex> getPrev(){
		return this.prev;
	}
	
	protected void removeNeighbor(Vertex v){
        if (next.contains(v)) {
            next.remove(v);
            v.getPrev().remove(this);
        } else if(prev.contains(v)) {
            prev.remove(v);
            v.next.remove(v);
        }
	}
	

	
}
