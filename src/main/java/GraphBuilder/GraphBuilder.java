package GraphBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import MinecraftGraph.Function;
import MinecraftGraph.FunctionType;
import MinecraftGraph.Graph;
import MinecraftGraph.In_output;
import MinecraftGraph.Vertex;
import MinecraftGraph.VertexType;


//main class
//json file->javaObject->Vertex->graph
public class GraphBuilder {
	private static final String[] keywords={"modules", "ports", "cells", "netnames"};
	private static ArrayList<String> ports_names=new ArrayList<>();
	private static ArrayList<String> cells_names=new ArrayList<>();
	private static ArrayList<String> net_names=new ArrayList<>();
	private static ArrayList<Port> ports=new ArrayList<>();
	private static ArrayList<Cell> cells=new ArrayList<>();
	
	private static ArrayList<In_output> inputs=new ArrayList<>();
	private static ArrayList<In_output> outputs=new ArrayList<>();
	private static ArrayList<Function> gates=new ArrayList<>();

	
	
	

	
	private static Graph buildGraph(String path){
		
		File file=new File(path);
		//Three string builder for the three json blocks: cells, ports, netnames
		StringBuilder sb1=new StringBuilder();
		StringBuilder sb2=new StringBuilder();
		StringBuilder sb3=new StringBuilder();

		FileReader reader;
		BufferedReader b_r;
		
		String portsBlock="";
		String cellsBlock="";
		String netnamesBlock="";
	
		String pattern = "[:{\"\\s]";
		
		try {
			reader = new FileReader(file);
			b_r=new BufferedReader(reader);
			
			 //skip first four lines
			b_r.readLine();
			b_r.readLine();
			b_r.readLine();
			b_r.readLine();
			
			int port_braces=1;
			String line=b_r.readLine();
			
			sb1.append("{");
			
			while(port_braces!=0){
				sb1.append(line);
				line=b_r.readLine();
				
				if(line.contains("{")){
					ports_names.add(line.replaceAll(pattern, ""));
					port_braces++;
					}
				else if (line.contains("}")){port_braces--;}
			}
			sb1.append("}");
			sb1.append("}");
			portsBlock=sb1.toString();
			
			//read gates block in the json file
			int cells_braces=1;
			line=b_r.readLine();
			sb2.append("{");
			
			while(cells_braces!= 0){
				
				sb2.append(line);
				line=b_r.readLine();
				
				if(line.contains("{")){
					cells_braces++;
					if(cells_braces==2){
						cells_names.add(line.replace(": {", "").replaceAll("[\"\\s]", ""));
						}
					}
				else if (line.contains("}")){cells_braces--;}
				
				
				
				

			}
			
			sb2.append("}");
			sb2.append("}");
			cellsBlock=sb2.toString();
			
			//read netnames block from json file
			
			//read gates block in the json file
			int netnames_braces=1;
			line=b_r.readLine();
			sb3.append("{");
			while(netnames_braces!= 0){
				
				sb3.append(line);
				line=b_r.readLine();
				
				if(line.contains("{")){
					netnames_braces++;
					
					//json syntax makes the required object with { number 2
					if(netnames_braces==2){
						net_names.add(line.replaceAll(pattern, ""));

						}
					}
				else if (line.contains("}")){netnames_braces--;}
				
				

			}
			
			sb3.append("}");
			sb3.append("}");
			netnamesBlock=sb3.toString();

		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException f) {
			f.printStackTrace();
		}
		
		//create jsononjects
		Gson gson= new com.google.gson.Gson();
		JsonObject ports_obj=gson.fromJson(portsBlock, JsonElement.class).getAsJsonObject().get("ports").getAsJsonObject();
		
		//read all ports names and create port objects
		
		for(String s: ports_names){
			JsonObject js=ports_obj.get(s).getAsJsonObject();
			Port port=new Port(s, js.get("direction").getAsString(), js.get("bits").getAsJsonArray());
			ports.add(port);
		
			
		}
		
		JsonObject cells_obj=gson.fromJson(cellsBlock, JsonElement.class).getAsJsonObject().get("cells").getAsJsonObject();
		
		int j=1;	//count to assign ids to gates
		for(String c: cells_names){
			
			JsonObject js_c=cells_obj.get(c).getAsJsonObject();
			JsonObject param_obj=js_c.get("parameters").getAsJsonObject();
			JsonObject conn_obj=js_c.get("connections").getAsJsonObject();
			JsonObject dir_obj=js_c.get("port_directions").getAsJsonObject();


			
			String[] param=param_obj.toString().replaceAll("[{]", "").split(",");
			String[] port_dir=conn_obj.toString().replaceAll("[{]", "").split(",");
			String[] conn=dir_obj.toString().replaceAll("[{]", "").split(",");
			ArrayList<Connection> conn_list=new ArrayList<>();
			
			for(int k=0; k<port_dir.length;k++){
				
				String holder=port_dir[k].split(":")[0].replaceAll("[\"]", "");
				String dir=dir_obj.get(holder).getAsString();
				JsonArray n=conn_obj.get(holder).getAsJsonArray();
				
				conn_list.add(new Connection(dir, n));

				
				
			}
			
			Cell cell=new Cell(j, js_c.get("hide_name").getAsInt(),js_c.get("type").getAsString(), js_c.get("attributes").getAsJsonObject().get("src").getAsString(),conn_list );
			
			cells.add(cell);
			j++;
		}
		
		Graph graph=new Graph();

		//add inputs to graph vertices
		for(Port p: ports){
			if(p.direction.equals("input")){
				In_output in=new In_output(p.bits.size(), VertexType.INPUT, p.name);
				inputs.add(in);
				graph.addVertex(in);
			}
			else{
				outputs.add(new In_output(p.bits.size(), VertexType.OUTPUT, p.name));
			}
			
			
		}
		//add all cells 
		for(Cell c:cells){
			
			Function v=new Function(c.id, VertexType.FUNCTION, resolveType(c.type), c.inputs.size());
			gates.add(v);
			graph.addVertex(v);
		}
		
		
		for(In_output v:outputs){
			graph.addVertex(v);
		}
		
		
		//resolve connections
		for(Port port:ports){
			
			for(Port toCom:ports){
				if(!port.equals(toCom)){
					int conn_count=areConnected(port.bits, toCom.bits);
					if(port.direction.equals("input")&&toCom.direction.equals("output")&&conn_count>0){
						for(int h=0; h<conn_count;h++)
							graph.addEdge(getVertex(graph, port), getVertex(graph, toCom));
					}else if(port.direction.equals("output")&&toCom.direction.equals("input")&&conn_count>0){
						for(int h=0; h<conn_count;h++)
							graph.addEdge(getVertex(graph, toCom), getVertex(graph, port));
					
					}
				}
				
			}
				
		}
		
		//add cells  edges
		for(Cell cell:cells){
			for(Port port:ports){
				int c1=areConnected(port.bits, cell.inputs);
				if(port.direction.equals("input")&& c1>0){
					for(int h=0; h<c1;h++)

						graph.addEdge(getVertex(graph, port), getVertex(graph, cell));
				}
				
				int c2=areConnected(port.bits, cell.outputs);

				if(port.direction.equals("output")&&c2>0){
					
					for(int h=0; h<c2;h++)
						graph.addEdge(getVertex(graph, cell), getVertex(graph, port));
				}	
				
			}
			
			for(Cell c:cells){
				
				if(!c.equals(cell)){
					if(areConnected(cell.outputs, c.inputs)>0)
						graph.addEdge(getVertex(graph, cell), getVertex(graph, c));
				}
				
			}	
			
		}
			
		
		return graph;
	}
	
	
	//getting vertices
	
	private static Vertex getVertex(Graph g, Port p){
	
		for(Vertex v:g.getVertices()){
			
			if(v.getID().equals(p.name)){
				return v;
			}
			
		}
		return null;
		
	}
	
	private static Vertex getVertex(Graph g, Cell p){
		
		for(Vertex v:g.getVertices()){
			
			if(v.getID().equals(String.valueOf(p.id))){
				return v;
			}
			
		}
		return null;
		
	}
	
	//checks if signals, gates are connected
	private static int areConnected(ArrayList<Integer> arr1, ArrayList<Integer> arr2){
		
		int count=0; 
		
		for(Integer x: arr1){
			for(Integer y: arr2){
				if(x==y){
					count++;
				}
					
			}
			
		}
		return count;
		
	
	}
	

	
	
	private static int num_of_inputs(JsonObject j_o){
		int num=0;
		char[] connection=j_o.get("connections").toString().toCharArray();
		for(char c: connection){
			if(c==':') num++;
		}
		return num-1;
	}
	
	
	
	
	//to be removed later
	public static void main(String[] args){
		Graph g=buildGraph("C://Users/Omar Ba mashmos/Documents/Design Project/yosys-win32-mxebin-0.6/tests/testMajority.json");
		
		//for testing purposes
		
		for(Vertex v:g.getVertices()){
			System.out.println(v.getID());
			
			for(Vertex c:v.getNext()){
				if(c.getType()==VertexType.INPUT||c.getType()==VertexType.OUTPUT)
					System.out.println("--->"+c.getID());
				else{
				
					Function gh=(Function)c;
					System.out.println("--->"+gh.getFunc_Type().toString()+", "+gh.getID());
				}
			}
			
		}
		
		
	
		
	}
	
	private static FunctionType resolveType(String type){
		
		//make sure that all string included
		
		if(type.contains("and")||type.contains("AND")){
			return FunctionType.AND;
			
		}else if(type.contains("or")||type.contains("OR")){
			return FunctionType.OR;
			
		}else if(type.contains("XOR")||type.contains("xor")){
			return FunctionType.XOR;
			
		}else if(type.contains("XNOR")||type.contains("xnor")){
			return FunctionType.XNOR;
			
		}else if(type.contains("INV")||type.contains("inv")){
			return FunctionType.INV;
			
		}else if(type.contains("nor")||type.contains("NOR")){
			return FunctionType.NOR;
			
		}else{
			return FunctionType.NAND;
		}
		
		
		
	}

}


class Port{
	String name;
	String direction;
	ArrayList<Integer> bits=new ArrayList<>();
	
	public Port(String n, String d, JsonArray b){
		name=n;
		direction=d;
		
		for(int i=0; i<b.size(); i++){
			bits.add(b.getAsInt());
			
			}
		
		}	
		
	}
	
	class Cell{
		int id;
		int hide_name;
		String type;
		String attr_src;
		ArrayList<Connection> connections=new ArrayList<>();
		
		ArrayList<Integer> inputs=new ArrayList<Integer>();
		ArrayList<Integer> outputs=new ArrayList<>();
		
		public Cell(int i, int h, String t, String src, ArrayList<Connection> cns){
			id=i;
			hide_name=h;
			type=t;
			attr_src=src;
			connections=cns;
			
			
			for(Connection c:connections){
				if(c.direction.equals("input")){
					for(int j=0; j<c.IDs.length; j++){
						inputs.add(c.IDs[j]);
					}
				}
				else{
					for(int j=0; j<c.IDs.length; j++){
						outputs.add(c.IDs[j]);
					}
				}
					
			}			
		
			
		}
	
		
	}
	
	class Connection{
		
		String direction;
		int IDs[];
		
		public Connection(String d, JsonArray arr){
			direction=d;
			IDs= new int[arr.size()];
			for(int j=0; j<arr.size(); j++){
				IDs[j]=arr.get(j).getAsInt();
				
			}
			
			
		}
		
	}
	







