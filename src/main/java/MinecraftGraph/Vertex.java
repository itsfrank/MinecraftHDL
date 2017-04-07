package MinecraftGraph;

import java.util.ArrayList;

public abstract class Vertex {
	
	public VertexType type;
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


	public void print(){
		if (this.type == VertexType.FUNCTION){
			System.out.println(((Function) this).func_type);
		} else {
			System.out.println(this.type);
		}

		for (Vertex v : this.next){
			if (v.type == VertexType.FUNCTION){
				System.out.println("\t:" + ((Function) v).func_type);
			} else {
				System.out.println("\t" + v.type);
			}
		}
	};

	public void handleRelay(Vertex v, Vertex relay) {

	}
}
