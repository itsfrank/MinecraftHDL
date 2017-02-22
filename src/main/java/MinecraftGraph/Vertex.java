package MinecraftGraph;

import java.util.ArrayList;

public abstract class Vertex {
	
	protected VertexType type;

	protected int bits_n;
	private int counter=0;
	private ArrayList<Vertex> next=new ArrayList<>();
	private ArrayList<Vertex> before=new ArrayList<>();
	
	
	private ArrayList<Vertex> prev = new ArrayList<>();

	
	
	
	public VertexType getType(){
		return this.type;
	}
	
	protected boolean canAdd(){
		if(bits_n==counter){
			return false;
		}
		else{
			return true;
		}
	}
	
	
	public abstract String getID();

	

	protected void addToNext(Vertex v){
		counter++;
    this.next.add(v);
	}

	protected void addNext(Vertex v){
		this.next.add(v);
	}
  
	public ArrayList<Vertex> getNext(){
		return this.next;
	}

	
	protected void removeNext(Vertex v){
		next.remove(v);
	}
	
	protected void addToBefore(Vertex v){
		this.before.add(v);
	}
	public ArrayList<Vertex> getBefore(){
		return this.before;
	}
	
	protected void removeBefore(Vertex v){
		before.remove(v);
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
