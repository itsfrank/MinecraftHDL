package GraphBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
	private static ArrayList<String> ports=new ArrayList<>();
	private static ArrayList<String> cells=new ArrayList<>();
	private static ArrayList<String> net_names=new ArrayList<>();
	
	
	public static Graph buildGraph(String path){
		
		String jsonObj=readJson(new File(path));
		
		Gson gson=new com.google.gson.Gson();
		com.google.gson.JsonObject result=gson.fromJson(jsonObj, JsonElement.class).getAsJsonObject();
		
		JsonObject modules=result.get(keywords[0]).getAsJsonObject();
		JsonObject ports_module=modules.get(keywords[1]).getAsJsonObject();
		JsonObject cells_module=modules.get(keywords[2]).getAsJsonObject();
		JsonObject netnames_module=modules.get(keywords[3]).getAsJsonObject();
		
		ArrayList<Vertex> inputs=new ArrayList<>();
		ArrayList<Vertex> outputs=new ArrayList<>();
		ArrayList<Vertex> gates=new ArrayList<>();

		
		//bits to be added 
		//once bits field is added
		for(String x:ports){
			JsonObject port=ports_module.get(x).getAsJsonObject();
			Vertex v_port;
			String direction=port.get("direction").getAsString();
			
			if(direction.equals("input")){
				v_port=new In_output(VertexType.INPUT, x);
				inputs.add(v_port);
			}else{
				v_port=new In_output(VertexType.OUTPUT, x);
				outputs.add(v_port);
			}	
			
		}
		/*for(Vertex v: inputs){
			System.out.println(v.getName()+"  and "+v.getType());
		}
		for(Vertex v: outputs){
			System.out.println(v.getName()+"and "+v.getType());
		}*/
		
		for(String g:cells){
			JsonObject gate=cells_module.get(g).getAsJsonObject();
			String g_name=gate.get("type").getAsString();
			Function v_gate;
			switch (g_name){
				case "AND":
					v_gate=new Function(VertexType.FUNCTION, FunctionType.AND, num_of_inputs(gate));
					break;
				case "OR":
					v_gate=new Function(VertexType.FUNCTION, FunctionType.OR, num_of_inputs(gate));
					break;
				case "NOR":
					v_gate=new Function(VertexType.FUNCTION, FunctionType.NOR, num_of_inputs(gate));
					break;
				case "NAND":
					v_gate=new Function(VertexType.FUNCTION, FunctionType.NAND, num_of_inputs(gate));
					break;
				case "XOR":
					v_gate=new Function(VertexType.FUNCTION, FunctionType.XOR, num_of_inputs(gate));
					break;
				case "XNOR":
					v_gate=new Function(VertexType.FUNCTION, FunctionType.XNOR, num_of_inputs(gate));
					break;
				default:
					v_gate=new Function(VertexType.FUNCTION, FunctionType.INV, num_of_inputs(gate));
					break;
			
				
			}
			
			gates.add(v_gate);
			
		}
		Graph graph=new Graph();
		for(Vertex v: inputs){
			graph.addVertex(v);
			for(Vertex c: gates)
				graph.addEdge(v, c);

		}
		for(Vertex v: gates){
			graph.addVertex(v);
			for(Vertex c: outputs)
				graph.addEdge(v, c);
			

		}for(Vertex v: outputs){
			graph.addVertex(v);

		}
		
		return graph;
		
	}
	
	
	
	private static String readJson(File file){
		
		StringBuilder sb=new StringBuilder();
		FileReader reader;
		BufferedReader b_r;
		String jsonObj="";
		
		int count_line=0;
		LineType k_w=LineType.ELSE;
		String pattern = "[:{\"\\s]";
		
		try {
			reader = new FileReader(file);
			b_r=new BufferedReader(reader);
			
			 //read line
			String line=b_r.readLine();
			
			 while(line!=null){
				if(!line.contains("creator") && count_line!=3){
					sb.append(line);
					
					
					if(line.contains("ports")){
				
						k_w=LineType.PORT;
					}
					else if(line.contains("cells")){
						k_w=LineType.CELL;
					}
					else if(line.contains("netnames")){
						k_w=LineType.NET_NAME;
					}
					else{
						if(k_w==LineType.PORT){
							if(line.contains("{")){
								ports.add(line.replaceAll(pattern, ""));
							}
						}else if(k_w==LineType.CELL){
							if(line.contains("{")&& !line.contains("parameters")&&!line.contains("attributes")&&!line.contains("connections")&&!line.contains("port_directions")){
								cells.add(line.replaceAll(pattern, ""));
							}
						}
						else if(k_w==LineType.NET_NAME){
							if(line.contains("{") && !line.contains("attributes")){
								net_names.add(line.replaceAll(pattern, ""));

								}
													
							}
						
			
					}
					

		
				}
				count_line++;
				sb.append(System.lineSeparator());
				line=b_r.readLine();

			
			
			 }
			 jsonObj=sb.toString();
				
			 b_r.close();
			 

		
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException f) {
			f.printStackTrace();
		}
	
		System.out.println(cells);
		return jsonObj.substring(0, jsonObj.length()-3);
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
		Graph g=buildGraph("C://Users/Omar/Desktop/or.json");
		
	
	}

}

enum LineType{
	PORT, CELL, NET_NAME, ELSE;
	
}
