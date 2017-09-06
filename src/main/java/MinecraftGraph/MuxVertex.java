package MinecraftGraph;

/**
 * Created by Francis O'Brien - 4/4/2017 - 17:12
 */

public class MuxVertex extends Function {

    public Vertex s_vertex, a_vertex, b_vertex;
    public int s_net_num, a_net_num, b_net_num = -1; //a (s = 0); b (s = 1)

    public MuxVertex(int i, FunctionType f_t, int x) {
        super(i, f_t, x);
    }

    @Override
    public String getID() {
        return String.valueOf(id);
    }

    @Override
    public void handleRelay(Vertex v, Vertex relay) {
        super.handleRelay(v, relay);
        if (v == a_vertex) this.a_vertex = relay;
        else if (v == b_vertex) this.b_vertex = relay;
        else if (v == s_vertex) this.s_vertex = relay;
    }
}
