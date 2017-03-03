package minecrafthdl;

import MinecraftGraph.*;

/**
 * Created by Francis on 11/27/2016.
 */
public class Demo {

    public static Graph create4bitmuxgraph(){
        Graph mux = new Graph();



        Vertex i = new In_output(1, VertexType.INPUT, "i");
        Vertex j = new In_output(1, VertexType.INPUT, "j");

        mux.addVertex(i);
        mux.addVertex(j);

        Vertex not_1 = new Function(1, VertexType.FUNCTION, FunctionType.INV, 1);
        Vertex not_2 = new Function(1, VertexType.FUNCTION, FunctionType.INV, 1);

        mux.addVertex(not_1);
        mux.addVertex(not_2);

        Vertex a = new In_output(1, VertexType.INPUT, "a");
        Vertex b = new In_output(1, VertexType.INPUT, "b");
        Vertex c = new In_output(1, VertexType.INPUT, "c");
        Vertex d = new In_output(1, VertexType.INPUT, "d");

        mux.addVertex(a);
        mux.addVertex(b);
        mux.addVertex(c);
        mux.addVertex(d);

        Vertex and_1 = new Function(1, VertexType.FUNCTION, FunctionType.AND, 3);
        Vertex and_2 = new Function(1, VertexType.FUNCTION, FunctionType.AND, 3);
        Vertex and_3 = new Function(1, VertexType.FUNCTION, FunctionType.AND, 3);
        Vertex and_4 = new Function(1, VertexType.FUNCTION, FunctionType.AND, 3);
        Vertex or_1 = new Function(1, VertexType.FUNCTION, FunctionType.OR, 4);

        mux.addVertex(and_1);
        mux.addVertex(and_2);
        mux.addVertex(and_3);
        mux.addVertex(and_4);
        mux.addVertex(or_1);

        Vertex o = new In_output(1, VertexType.OUTPUT, "o");

        mux.addVertex(o);


        mux.addEdge(i, not_1);
        mux.addEdge(j, not_2);

        mux.addEdge(i, and_2);
        mux.addEdge(i, and_4);
        mux.addEdge(not_1, and_1);
        mux.addEdge(not_1, and_3);

        mux.addEdge(j, and_3);
        mux.addEdge(j, and_4);
        mux.addEdge(not_2, and_1);
        mux.addEdge(not_2, and_2);

        mux.addEdge(a, and_1);
        mux.addEdge(b, and_2);
        mux.addEdge(c, and_3);
        mux.addEdge(d, and_4);

        mux.addEdge(and_1, or_1);
        mux.addEdge(and_2, or_1);
        mux.addEdge(and_3, or_1);
        mux.addEdge(and_4, or_1);

        mux.addEdge(or_1, o);

        return mux;
    }

}
