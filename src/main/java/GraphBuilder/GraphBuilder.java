package GraphBuilder;

import GraphBuilder.json_representations.JCell;
import GraphBuilder.json_representations.JPort;
import GraphBuilder.json_representations.JsonFile;
import GraphBuilder.json_representations.Module;
import MinecraftGraph.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


//main class
//json file->javaObject->Vertex->graph
public class GraphBuilder {
	private static ArrayList<String> ports_names=new ArrayList<>();
	private static ArrayList<String> cells_names=new ArrayList<>();
	private static ArrayList<Port> ports=new ArrayList<>();
	private static ArrayList<Cell> cells=new ArrayList<>();
	
	private static ArrayList<In_output> inputs=new ArrayList<>();
	private static ArrayList<In_output> outputs=new ArrayList<>();
	private static ArrayList<Function> gates=new ArrayList<>();

	static int test_i = 1;

	static int high_low_nets = Integer.MAX_VALUE;
	static int cell_ids = 0;
	static HashMap<Integer, Vertex> from_net = new HashMap<>();
	static HashMap<Integer, ArrayList<Vertex>> to_net = new HashMap<>();

	public static int putInToNet(int i, Vertex v, Graph g){
		ArrayList<Vertex> l = to_net.get(i);

		if (i == 0) {
			high_low_nets--;
			Function f = new Function(cell_ids++, FunctionType.LOW, 0);
			from_net.put(high_low_nets, f);
			to_net.put(high_low_nets, new ArrayList<>());
			to_net.get(high_low_nets).add(v);
			g.addVertex(f);
			return high_low_nets;
		} else if (i == 1){
			high_low_nets--;
			Function f = new Function(cell_ids++, FunctionType.HIGH, 0);
			from_net.put(high_low_nets, f);
			to_net.put(high_low_nets, new ArrayList<>());
			to_net.get(high_low_nets).add(v);
			g.addVertex(f);
			return high_low_nets;
		} else {
			if (l == null) to_net.put(i, new ArrayList<>());
			to_net.get(i).add(v);
			return i;
		}

	}

	public static int putInFromNet(int i, Vertex v){
		Vertex vr = from_net.get(i);

		if (vr != null) throw new RuntimeException("TWO OUTPUTS ON SAME NET");
		from_net.put(i, v);
		return i;
	}

	public static Graph buildGraph(String path){
		high_low_nets = Integer.MAX_VALUE;

		Gson gson= new com.google.gson.Gson();
		JsonFile jf = null;
		try {
			FileReader fr = new FileReader(path);
			JsonReader jreader = new JsonReader(fr);
			jreader.setLenient(true);
			jf = gson.fromJson(jreader, JsonFile.class);
			fr.close();
			jreader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		jf.postInit();

		Graph g = new Graph();

		Module m = jf.modules.values().iterator().next();

		from_net = new HashMap<>();
		to_net = new HashMap<>();

		for (String p_name : m.ports.keySet()){
			JPort p = m.ports.get(p_name);

			In_output io;

			if (p.direction.equals("input")){

				io = new In_output(p.bits.size(), VertexType.INPUT, p_name);

				for (int i : p.bits){
					putInFromNet(i, io);
				}

			} else {

				io = new In_output(p.bits.size(), VertexType.OUTPUT, p_name);

				for (int i : p.bits){
					putInToNet(i, io, g);
				}

			}

			g.addVertex(io);
		}

		cell_ids = 0;

		for (String c_name : m.cells.keySet()){
			JCell c = m.cells.get(c_name);

			FunctionType f_type = resolveType(c.type);

			Function f;

			if (f_type == FunctionType.MUX){
				f = new MuxVertex(cell_ids++, f_type, c.numInputs());
			} else {
				f = new Function(cell_ids++, f_type, c.numInputs());
			}

			for (String conn_name : c.connections.keySet()){
				String direction = c.port_directions.get(conn_name);
				ArrayList<Integer> conn_nets = c.connections.get(conn_name);

				int conn_net = -1;

				if (direction.equals("input")){
					for (int i : conn_nets){
						conn_net = putInToNet(i, f, g);
					}
				} else {
					for (int i : conn_nets){
						conn_net = putInFromNet(i, f);
					}
				}

				if (f_type == FunctionType.MUX){
					if (conn_name.equals("S")){
						((MuxVertex) f).s_net_num = conn_net;
					} else if (conn_name.equals(("A"))) {
						((MuxVertex) f).a_net_num = conn_net;
					} else if (conn_name.equals(("B"))) {
						((MuxVertex) f).b_net_num = conn_net;
					}
				}
			}

			g.addVertex(f);
		}

		for (int i : to_net.keySet()){
			for (Vertex v : to_net.get(i)){
				Vertex from = from_net.get(i);
				if (from == null){
					throw new RuntimeException("NET HAS NO FROM VERTEX");
				}

				if (v.type == VertexType.FUNCTION){
					Function f = ((Function) v);
					if (((Function) v).func_type == FunctionType.MUX){
						MuxVertex mux = ((MuxVertex) f);

						if (i == mux.a_net_num){
							mux.a_vertex = from;
						} else if (i == mux.b_net_num){
							mux.b_vertex = from;
						} else if (i == mux.s_net_num){
							mux.s_vertex = from;
						}
					}
				}

				g.addEdge(from, v);
			}
		}

		return g;
	}


	public static Graph buildxGraph(String path){

		ArrayList<String> ports_names=new ArrayList<>();
		ArrayList<String> cells_names=new ArrayList<>();
		ArrayList<Port> ports=new ArrayList<>();
		ArrayList<Cell> cells=new ArrayList<>();

		ArrayList<In_output> inputs=new ArrayList<>();
		ArrayList<In_output> outputs=new ArrayList<>();
		ArrayList<Function> gates=new ArrayList<>();

		test_i = 1;
		System.out.println(test_i++); //1


		//create jsononjects
		Gson gson= new com.google.gson.Gson();
		JsonFile jf = null;
		try {
			FileReader fr = new FileReader(path);
			JsonReader jreader = new JsonReader(fr);
			jreader.setLenient(true);
			jf = gson.fromJson(jreader, JsonFile.class);
			fr.close();
			jreader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(test_i++); //2


		jf.postInit();

		Module m = jf.modules.values().iterator().next();

		if (m == null) return null;

		//read all ports names and create port objects

		for(String s: m.ports.keySet()){
			JPort p = m.ports.get(s);
			Port port=new Port(s, p.direction, p.bits);
			ports.add(port);


		}

		System.out.println(test_i++); //3


		int j=1;	//count to assign ids to gates

		for(String s: m.cells.keySet()){

			JCell c = m.cells.get(s);

			ArrayList<Connection> conn_list = new ArrayList<>();

			for(JPort p : c.ports.values()){

				conn_list.add(new Connection(p.direction, p.bits, p.name));



			}



			Cell cell=new Cell(j, c.type, conn_list );

			cells.add(cell);
			j++;
		}

		System.out.println(test_i++); //4

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

		System.out.println(test_i++); //5


		//add all cells
		for(Cell c:cells){

			Function v = null;

			FunctionType type = resolveType((c.type));
			if (type == FunctionType.MUX){
				Connection c_sel = c.getConn("S");

				if (c_sel == null) {
					throw new RuntimeException("MUX MUST HAVE S INPUT");
				}

				v = new MuxVertex(c.id, type, c.inputs.size());
			} else {
				v=new Function(c.id, resolveType(c.type), c.inputs.size());
			}

			gates.add(v);
			graph.addVertex(v);
		}

		System.out.println(test_i++); //6


		for(In_output v:outputs){
			graph.addVertex(v);
		}

		System.out.println(test_i++); //7


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

		System.out.println(test_i++); //8


		//add cells  edges
		for(Cell cell:cells){

			for(Port port:ports){

				int c1=areConnected(port.bits, cell.inputs);
				if(port.direction.equals("input")&& c1>0){
					for(int h=0; h<c1;h++){

						graph.addEdge(getVertex(graph, port), getVertex(graph, cell));

					}

				}

				int c2=areConnected(port.bits, cell.outputs);

				if(port.direction.equals("output")&&c2>0){

					for(int h=0; h<c2;h++){
						graph.addEdge(getVertex(graph, cell), getVertex(graph, port));

					}
				}

			}

			for(Cell c:cells){

				if(!c.equals(cell)){
					if(areConnected(cell.outputs, c.inputs)>0)
						graph.addEdge(getVertex(graph, cell), getVertex(graph, c));
				}

			}

		}


		System.out.println(test_i++); //9

		optimizeGraph(graph);
		System.out.println(test_i++); //10

		return graph;
	}

//	public static Graph buildxGraph(String path){
//
//		File file=new File(path);
//		//Three string builder for the three json blocks: cells, ports, netnames
//		StringBuilder sb1=new StringBuilder();
//		StringBuilder sb2=new StringBuilder();
//		StringBuilder sb3=new StringBuilder();
//
//		FileReader reader;
//		BufferedReader b_r;
//
//		String portsBlock="";
//		String cellsBlock="";
//		String netnamesBlock="";
//
//		String pattern = "[:{\"\\s]";
//
//		try {
//			reader = new FileReader(file);
//			b_r=new BufferedReader(reader);
//
//			 //skip first four lines
//			b_r.readLine();
//			b_r.readLine();
//			b_r.readLine();
//			b_r.readLine();
//
//			int port_braces=1;
//			String line=b_r.readLine();
//
//			sb1.append("{");
//
//			while(port_braces!=0){
//				sb1.append(line);
//				line=b_r.readLine();
//
//				if(line.contains("{")){
//					ports_names.add(line.replaceAll(pattern, ""));
//					port_braces++;
//					}
//				else if (line.contains("}")){port_braces--;}
//			}
//			sb1.append("}");
//			sb1.append("}");
//			portsBlock=sb1.toString();
//
//			//read gates block in the json file
//			int cells_braces=1;
//			line=b_r.readLine();
//			sb2.append("{");
//
//			while(cells_braces!= 0){
//
//				sb2.append(line);
//				line=b_r.readLine();
//
//				if(line.contains("{")){
//					cells_braces++;
//					if(cells_braces==2){
//						cells_names.add(line.replace(": {", "").replaceAll("[\"\\s]", ""));
//						}
//					}
//				else if (line.contains("}")){cells_braces--;}
//
//
//
//			}
//
//			sb2.append("}");
//			sb2.append("}");
//			cellsBlock=sb2.toString();
//
//
//
//		}catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException f) {
//			f.printStackTrace();
//		}
//
//		//create jsononjects
//		Gson gson= new com.google.gson.Gson();
//		System.out.println(portsBlock);
//
//		JsonObject ports_obj=gson.fromJson(portsBlock, JsonElement.class).getAsJsonObject().get("ports").getAsJsonObject();
//
//
//
//		//read all ports names and create port objects
//
//		for(String s: ports_names){
//			JsonObject js=ports_obj.get(s).getAsJsonObject();
//			Port port=new Port(s, js.get("direction").getAsString(), js.get("bits").getAsJsonArray());
//			ports.add(port);
//
//
//		}
//
//		JsonObject cells_obj=gson.fromJson(cellsBlock, JsonElement.class).getAsJsonObject().get("cells").getAsJsonObject();
//
//
//		int j=1;	//count to assign ids to gates
//
//		for(String c: cells_names){
//
//			JsonObject js_c=cells_obj.get(c).getAsJsonObject();
//			JsonObject param_obj=js_c.get("parameters").getAsJsonObject();
//			JsonObject conn_obj=js_c.get("connections").getAsJsonObject();
//			JsonObject dir_obj=js_c.get("port_directions").getAsJsonObject();
//
//
//			String[] param=param_obj.toString().replaceAll("[{]", "").split(",");
//			String[] port_dir=conn_obj.toString().replaceAll("[{]", "").split(",");
//			String[] conn=dir_obj.toString().replaceAll("[{]", "").split(",");
//			ArrayList<Connection> conn_list=new ArrayList<>();
//
//			for(int k=0; k<port_dir.length;k++){
//
//				String holder=port_dir[k].split(":")[0].replaceAll("[\"]", "");
//				String dir=dir_obj.get(holder).getAsString();
//				JsonArray n=conn_obj.get(holder).getAsJsonArray();
//
//				conn_list.add(new Connection(dir, n));
//
//
//
//			}
//
//
//
//			Cell cell=new Cell(j,js_c.get("type").getAsString(),conn_list );
//
//			cells.add(cell);
//			j++;
//		}
//
//		Graph graph=new Graph();
//
//		//add inputs to graph vertices
//		for(Port p: ports){
//			if(p.direction.equals("input")){
//				In_output in=new In_output(p.bits.size(), VertexType.INPUT, p.name);
//				inputs.add(in);
//				graph.addVertex(in);
//			}
//			else{
//				outputs.add(new In_output(p.bits.size(), VertexType.OUTPUT, p.name));
//			}
//
//
//		}
//		//add all cells
//		for(Cell c:cells){
//
//			Function v=new Function(c.id, VertexType.FUNCTION, resolveType(c.type), c.inputs.size());
//			gates.add(v);
//			graph.addVertex(v);
//		}
//
//
//		for(In_output v:outputs){
//			graph.addVertex(v);
//		}
//
//
//		//resolve connections
//		for(Port port:ports){
//
//			for(Port toCom:ports){
//				if(!port.equals(toCom)){
//					int conn_count=areConnected(port.bits, toCom.bits);
//					if(port.direction.equals("input")&&toCom.direction.equals("output")&&conn_count>0){
//						for(int h=0; h<conn_count;h++)
//							graph.addEdge(getVertex(graph, port), getVertex(graph, toCom));
//					}else if(port.direction.equals("output")&&toCom.direction.equals("input")&&conn_count>0){
//						for(int h=0; h<conn_count;h++)
//							graph.addEdge(getVertex(graph, toCom), getVertex(graph, port));
//
//					}
//				}
//
//			}
//
//		}
//
//		//add cells  edges
//		for(Cell cell:cells){
//			for(Port port:ports){
//				int c1=areConnected(port.bits, cell.inputs);
//				if(port.direction.equals("input")&& c1>0){
//					for(int h=0; h<c1;h++)
//
//						graph.addEdge(getVertex(graph, port), getVertex(graph, cell));
//				}
//
//				int c2=areConnected(port.bits, cell.outputs);
//
//				if(port.direction.equals("output")&&c2>0){
//
//					for(int h=0; h<c2;h++)
//						graph.addEdge(getVertex(graph, cell), getVertex(graph, port));
//				}
//
//			}
//
//			for(Cell c:cells){
//
//				if(!c.equals(cell)){
//					if(areConnected(cell.outputs, c.inputs)>0)
//						graph.addEdge(getVertex(graph, cell), getVertex(graph, c));
//				}
//
//			}
//
//		}
//
//		optimizeGraph(graph);
//		return graph;
//	}
//
	private static void optimizeGraph(Graph graph){
		//iterate through all the nodes of the graph
		//if or or and gate check outputs
		//if all outputs are of the same type
		//remove lower level and reconnect its inputs with the higher level
		//got back
		ArrayList<Vertex> verToRemove=new ArrayList<>();
		for(Vertex v: graph.getVertices()){
			System.out.println("vertex for");

			//check if vertex is a gate
			if(v.getType()==VertexType.FUNCTION){
				//check if gate type is and, or
				Function f=(Function)v;
				
				FunctionType f_t=f.getFunc_Type();
				if(f_t==FunctionType.AND||f_t==FunctionType.OR){
					if(canMerge(f)){
						for(Vertex s:f.getNext()){
							System.out.println("vertex inner for");

							graph.mergeVertices(f, s);
							verToRemove.add(f);
						}

					}
					
				}	
			}		
			
		}
		
		for(Vertex t:verToRemove){
			System.out.println("vertex inner 2 for");

			graph.removeVertex(t);
		}
	}

	private static boolean canMerge(Function v){
		for(Vertex x:v.getNext()){
			if(x.getType()!=VertexType.FUNCTION){
				return false;
				
			}
			Function f=(Function)x;
			if(f.getFunc_Type()!=v.getFunc_Type()){
				return false;
			}	
		}
		
		return true;
		
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
	private static int areConnected(ArrayList<Integer> bits, ArrayList<Integer> inputs2){
		
		int count=0; 
		
		for(Integer x: bits){
			for(Integer y: inputs2){
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
	
	
	
	
	private static FunctionType resolveType(String type){
		
		//make sure that all string included
		
		if(type.contains("and")||type.contains("AND")){
			return FunctionType.AND;
			
		}else if(type.contains("MUX")||type.contains("mux")){
			return FunctionType.MUX;
		}else if(type.contains("XOR")||type.contains("xor")){
			return FunctionType.XOR;

		}else if(type.contains("or")||type.contains("OR")){
			return FunctionType.OR;
			
		}else if(type.contains("dff_p")||type.contains("DFF_P")){
			return FunctionType.D_FLIPFLOP;
			
		}else{
			return FunctionType.INV;
		}
		
		
		
	}

}


	class Port{
		String name;
		String direction;
		ArrayList<Integer> bits=new ArrayList<>();

		public Port(String n, String d, ArrayList<Integer> b){
			name=n;
			direction=d;
			bits=b;
		}

	}

	class Cell{
		int id;
		String type;
		ArrayList<Connection> connections=new ArrayList<>();

		ArrayList<Integer> inputs=new ArrayList<Integer>();
		ArrayList<Integer> outputs=new ArrayList<>();

		public Cell(int i, String t, ArrayList<Connection> cns){
			id=i;
			type=t;
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

		public Connection getConn(String name){
			for (Connection c : this.connections){
				if (c.name.equals(name)) return c;
			}
			return null;
		}


	}

	class Connection{
		String name;
		String direction;
		int IDs[];

		public Connection(String d, ArrayList<Integer> arr, String name){
			this.name = name;
			direction=d;
			IDs= new int[arr.size()];
			for(int j=0; j<arr.size(); j++){
				IDs[j]=arr.get(j);

			}


		}

	}

//
//class Port{
//	String name;
//	String direction;
//	ArrayList<Integer> bits=new ArrayList<>();
//
//	public Port(String n, String d, JsonArray b){
//		name=n;
//		direction=d;
//
//
//		for(int i=0; i<b.size(); i++){
//			bits.add(b.get(i).getAsInt());
//		}
//
//	}
//
//}
//
//class Cell{
//	int id;
//	String type;
//	ArrayList<Connection> connections=new ArrayList<>();
//
//	ArrayList<Integer> inputs=new ArrayList<Integer>();
//	ArrayList<Integer> outputs=new ArrayList<>();
//
//	public Cell(int i, String t, ArrayList<Connection> cns){
//		id=i;
//		type=t;
//		connections=cns;
//
//
//		for(Connection c:connections){
//			if(c.direction.equals("input")){
//				for(int j=0; j<c.IDs.length; j++){
//					inputs.add(c.IDs[j]);
//				}
//			}
//			else{
//				for(int j=0; j<c.IDs.length; j++){
//					outputs.add(c.IDs[j]);
//				}
//			}
//
//		}
//
//
//	}
//
//
//}
//
//class Connection{
//
//	String direction;
//	int IDs[];
//
//	public Connection(String d, JsonArray arr){
//		direction=d;
//		IDs= new int[arr.size()];
//		for(int j=0; j<arr.size(); j++){
//			IDs[j]=arr.get(j).getAsInt();
//
//		}
//
//
//	}
//
//}
//








