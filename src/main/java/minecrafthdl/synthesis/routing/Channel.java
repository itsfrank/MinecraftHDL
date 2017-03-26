package minecrafthdl.synthesis.routing;

import minecrafthdl.synthesis.Circuit;
import minecrafthdl.synthesis.routing.pins.Pin;
import minecrafthdl.synthesis.routing.pins.PinPair;
import minecrafthdl.synthesis.routing.pins.PinsArray;

import java.util.ArrayList;

/**
 * Created by Francis O'Brien - 3/3/2017 - 8:37 AM
 */

public class Channel {
    public PinsArray pinsArray;
    public ArrayList<ArrayList<Net>> tracks = new ArrayList<>();

    public void findAvailableTrack(Net net){
        for(int i = 0; i < this.tracks.size(); i++){
            boolean hasConflict = false;
            for (Net n : this.tracks.get(i)){
                hasConflict = n.hasHorizontalConflict(net);
                if (hasConflict) break;
            }
            if (!hasConflict){
                this.tracks.get(i).add(net);
                net.setTrack(i);
                return;
            }
        }
        ArrayList<Net> new_track = new ArrayList<>();
        new_track.add(net);
        this.tracks.add(new_track);
        net.setTrack(this.tracks.size() - 1);
    }

    public Circuit genChannelCircuit(){
        int length = 2 + (3 * this.tracks.size());
        int width = this.pinsArray.getPairs().get(this.pinsArray.getPairs().size() - 1).top.xPos();
        int height = 3;

        Circuit circuit = new Circuit(width, length, height);

        for(Net n : this.t)

        for (PinPair pp : this.pinsArray.getPairs()){
            System.out.println(pp.top.xPos());
        }

        return null;
    }

    public void printChannel(){
        int width = pinsArray.getPairs().size();
        int height = tracks.size() + 2;

        String[][] chars = new String[width][height];

        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                chars[x][y] = ".";
            }
        }

        for (int x = 0; x < width; x++){
            PinPair pair = pinsArray.getPair(x);

            chars[x][0] = Integer.toString(pair.top.netID()).charAt(0) + "";
            chars[x][height - 1] = Integer.toString(pair.bot.netID()).charAt(0) + "";
            if (pair.top.empty()) chars[x][0] = " ";
            if (pair.bot.empty()) chars[x][height - 1] = " ";
        }


        for (ArrayList<Net> t : tracks){
            for (Net n: t){
                for (Pin p : n.getpins()){
                    if (p.top) {
                        for(int i = 1; i <= n.track + 1; i++){
                            chars[p.xPos()/2][i] = "│";
                        }
                    } else {
                        for(int i = n.track + 1; i < height - 1; i++){
                            chars[p.xPos()/2][i] = "│";
                        }
                    }
                }
            }
        }

        for (ArrayList<Net> t : tracks) {
            for (Net n : t) {
                if (n.isOutpath()){
                    for (int y = n.track; y <= n.out_partner.track; y++){
                        chars[n.x_max / 2][y + 1] = "|";
                    }
                }
            }
        }

        for (ArrayList<Net> t : tracks){
            for (Net n: t){
                for (int i = n.x_min/2; i <= n.x_max/2; i++){
                    if(n.x_max == n.x_min) continue;
                    chars[i][n.track + 1] = "─";
                }
            }
        }


        for (ArrayList<Net> t : tracks){
            for (Net n: t){

                if(n.isOutpath()){
                    chars[n.x_max / 2][n.track+1] = "┐";
                    chars[n.x_max / 2][n.out_partner.track+1] = "┘";
                }

                for (Pin p : n.getpins()){
                    int x = p.xPos() / 2;

                    boolean up = false;
                    boolean down = false;
                    boolean left = n.x_min / 2 < x;
                    boolean right = n.x_max / 2 > x;

                    if (p.top){
                        up = true;
                        for (Pin p2 : n.getpins()){
                            if (p2 != p && p.xPos() == p2.xPos()) down = true;
                        }
                    } else {
                        down = true;
                        for (Pin p2 : n.getpins()){
                            if (p2 != p && p.xPos() == p2.xPos()) up = true;
                        }
                    }

                    if (up){
                        if (down){
                            if (left){
                                if (right) chars[x][n.track + 1] = "┼";
                                else chars[x][n.track + 1] = "┤";
                            } else {
                                if (right) chars[x][n.track + 1] = "├";
                                //else chars[x][n.track + 1] = "│";
                            }
                        } else {
                            if (left){
                                if (right) chars[x][n.track + 1] = "┴";
                                else chars[x][n.track + 1] = "┘";
                            } else {
                                if (right) chars[x][n.track + 1] = "└";
                                else chars[x][n.track + 1] = "?";
                            }
                        }
                    } else {
                        if (down){
                            if (left){
                                if (right) chars[x][n.track + 1] = "┬";
                                else chars[x][n.track + 1] = "┐";
                            } else {
                                if (right) chars[x][n.track + 1] = "┌";
                                else chars[x][n.track + 1] = "?";
                            }
                        } else {
                            if (left){
                                //if (right) chars[x][n.track + 1] = "─";
                                //else chars[x][n.track + 1] = "?";
                            } else {
                                if (right) chars[x][n.track + 1] = "?";
                                else chars[x][n.track + 1] = "?";
                            }
                        }
                    }
                }
            }
        }

        for (int y = 0; y < height; y++){
            String row = "";
            for (int x = 0; x < width; x++){
                row += chars[x][y];
                //row += " ";
            }
            System.out.println(row);
        }

    }

}
