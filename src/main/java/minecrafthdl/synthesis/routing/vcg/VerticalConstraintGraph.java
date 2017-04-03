package minecrafthdl.synthesis.routing.vcg;

import javafx.util.Pair;
import minecrafthdl.synthesis.routing.Net;
import minecrafthdl.synthesis.routing.pins.PinPair;
import minecrafthdl.synthesis.routing.pins.PinsArray;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Francis O'Brien - 3/4/2017 - 9:29 AM
 */

public class VerticalConstraintGraph {

    int num_nets_routed = 0;

    ArrayList<Pair<Integer, Integer>> edges_done = new ArrayList<>();

    public int[] getEdgeIDList(int id) {
        Node n = nodes.get(id);

        int[] vc_ids = new int[n.edges.size()];

        for (int i = 0; i < n.edges.size(); i++){
            vc_ids[i] = n.edges.get(i).to.net_id;
        }

        return vc_ids;
    }

    private static class Node {
        public boolean routed = false;
        public ArrayList<Edge> edges = new ArrayList<>();

        int net_id;

        public Node(int net_id){
            this.net_id = net_id;
        }
    }

    private static class Edge {
        public Node to;

        public Edge(Node to){
            this.to = to;
        }
    }

    private HashMap<Integer, Node> nodes = new HashMap<>();

    public VerticalConstraintGraph(PinsArray pin_pairs, HashMap<Integer, Net> nets) {
        ArrayList<PinPair> original_pairs = new ArrayList<>(pin_pairs.getPairs());
        for(PinPair pair : original_pairs){
            if (!pair.top.empty()){
                if(!this.nodes.containsKey(pair.top.netID())) this.nodes.put(pair.top.netID(), new Node(pair.top.netID()));
            }

            if (!pair.bot.empty()){
                if(!this.nodes.containsKey(pair.bot.netID())) this.nodes.put(pair.bot.netID(), new Node(pair.bot.netID()));
            }

            if (!pair.top.empty() && !pair.bot.empty() && pair.top.netID() != pair.bot.netID()) {

                if(edgeDone(pair.top.netID(), pair.bot.netID())) continue;

                if (!cycle(this.nodes.get(pair.top.netID()), pair.bot.netID())){
                    this.nodes.get(pair.bot.netID()).edges.add(new Edge(this.nodes.get(pair.top.netID())));
                    this.edges_done.add(new Pair<>(pair.top.netID(), pair.bot.netID()));
                } else {
                    Net out_net = new Net();
                    Net out_partner = nets.get(pair.top.netID());

                    out_net.setOutNet(pair.top, out_partner);

                    int x_max = out_net.AssignOutColX(pin_pairs.addEmptyPair());

                    out_partner.newXMax(x_max);

                    nets.put(out_net.getId(), out_net);

                    Node out_node = new Node(out_net.getId());
                    this.nodes.put(out_net.getId(), out_node);
                    this.nodes.get(pair.bot.netID()).edges.add(new Edge(out_node));
                    this.edges_done.add(new Pair<>(pair.top.netID(), pair.bot.netID()));
                }

            }
        }
        System.out.println();
    }

    public boolean edgeDone(int top, int bot){
        for (Pair<Integer, Integer> pair : this.edges_done){
            if (pair.getKey() == top && pair.getValue() == bot) return true;
        }

        return false;
    }

    public static boolean cycle(Node n, int cycle_id){
        if (cycle_id == 1){
            System.out.println("");

        }
        if (n.net_id == cycle_id) return true;
        for (Edge e : n.edges){
            if (cycle(e.to, cycle_id)){
                return  true;
            }
        }
        return false;
    }


    public boolean canRoute(int netID){
        Node n = this.nodes.get(netID);

        if (n.routed) return false;

        for (Edge e : n.edges){
            if(!e.to.routed) return false;
        }
        return true;
    }

    public void routed(int netID){
        if (this.nodes.get(netID).routed) throw new RuntimeException("Routed same net twice");

        this.nodes.get(netID).routed = true;
        this.num_nets_routed++;
    }

    public boolean done(){
        return this.nodes.values().size() == this.num_nets_routed;
    }

}
