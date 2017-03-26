package MinecraftGraph;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class Vertex {
	
	protected VertexType type;
	protected int bits_n;
	private int counter=0;
	private ArrayList<Vertex> next=new ArrayList<>();
	private ArrayList<Vertex> before=new ArrayList<>();
	
	
	
	
	public VertexType getType(){
		return this.type;
	}
	
	public boolean canAdd(){
		if(bits_n==counter){
			return false;
		}
		else{
			return true;
		}
	}
	
	
	public abstract String getID();

	
	
	public void addToNext(Vertex v){
		counter++;
		this.next.add(v);
	}
	public ArrayList<Vertex> getNext(){
		return this.next;
	}
	
	public void removeNext(Vertex v){
		next.remove(v);
	}
	
	public void addToBefore(Vertex v){
		this.before.add(v);
	}
	public ArrayList<Vertex> getBefore(){
		return this.before;
	}
	
	public void removeBefore(Vertex v){
		before.remove(v);
	}
	

	
}
