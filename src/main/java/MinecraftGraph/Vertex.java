package MinecraftGraph;

import java.util.ArrayList;

public abstract class Vertex {
	
	public VertexType type;
	public int bits_n;
	public boolean removed = false;
	private int counter=0;
	private ArrayList<Vertex> next=new ArrayList<Vertex>();
	private ArrayList<Vertex> before=new ArrayList<Vertex>();
	
	
	
	
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

	public void merge(Vertex v) {
		this.before.addAll(v.getBefore());
		for (Vertex bb : v.before) {
			bb.next.remove(v);
			bb.next.add(this);
		}
		this.before.remove(v);
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
