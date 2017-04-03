package minecrafthdl.synthesis.routing;

import minecrafthdl.Utils;
import minecrafthdl.synthesis.Circuit;
import minecrafthdl.synthesis.routing.pins.Pin;
import minecrafthdl.synthesis.routing.pins.PinPair;
import minecrafthdl.synthesis.routing.pins.PinsArray;
import minecrafthdl.synthesis.routing.vcg.VerticalConstraintGraph;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;

import java.util.ArrayList;

/**
 * Created by Francis O'Brien - 3/3/2017 - 8:37 AM
 */

public class Channel {
    public PinsArray pinsArray;
    public ArrayList<ArrayList<Net>> tracks = new ArrayList<>();

    public void findAvailableTrack(Net net, VerticalConstraintGraph vcg){
        int highest_track = 0;

        for (int vc_id : vcg.getEdgeIDList(net.id)){
            for (ArrayList<Net> track : this.tracks){
                for (Net n : track){
                    if (n.id == vc_id && n.track >= highest_track) highest_track = n.track + 1;
                }
            }
        }

        for(int i = highest_track; i < this.tracks.size(); i++){
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
        int height = 3;
        int width = 0;

        for (ArrayList<Net> track : this.tracks){
            for (Net n : track){
                if (n.x_max > width) width = n.x_max;
            }
        }

        width += 1;

        Circuit circuit = new Circuit(width, height, length);

        ArrayList<Net> nets_done = new ArrayList<>();

        for(ArrayList<Net> track : this.tracks){
            for (Net n : track){
                if (nets_done.contains(n)) continue;
                this.placeTrack(circuit, n.track, n.x_min, n.x_max, n.getpins());
                nets_done.add(n);

                if (n.isOutpath()){
                    circuit.setBlock(n.x_max, 0, n.trackZ() + 1, Blocks.WOOL.getDefaultState());
                    circuit.setBlock(n.x_max, 1, n.trackZ() + 1, Blocks.REDSTONE_WIRE.getDefaultState());
                }

                if (n.out_partner != null && !n.isOutpath()){
                    circuit.setBlock(n.x_max, 0, n.trackZ() - 1, Blocks.WOOL.getDefaultState());
                    circuit.setBlock(n.x_max, 1, n.trackZ() - 1, Blocks.REDSTONE_WIRE.getDefaultState());
                }

                this.wireColumns(circuit, n);
                this.repeatNets(circuit, n);
            }
        }



        return circuit;
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

    public void placeTrack(Circuit channel, int track_number, int xmin, int xmax, ArrayList<Pin> pins){
        int z_min = 1 + (3 * track_number);
        int z_track = z_min + 1;

        for (int x = xmin; x <= xmax; x++){
            channel.setBlock(x, 1, z_track, Blocks.WOOL.getDefaultState());
            channel.setBlock(x, 2, z_track, Blocks.REDSTONE_WIRE.getDefaultState());
        }

        for (Pin p : pins){
            if (p.top) {
                channel.setBlock(p.xPos(), 0, z_min, Blocks.WOOL.getDefaultState());
                channel.setBlock(p.xPos(), 1, z_min, Blocks.REDSTONE_WIRE.getDefaultState());
            } else {
                channel.setBlock(p.xPos(), 0, z_track + 1, Blocks.WOOL.getDefaultState());
                channel.setBlock(p.xPos(), 1, z_track + 1, Blocks.REDSTONE_WIRE.getDefaultState());
            }
        }
    }

    public void wireColumns(Circuit channel, Net n) {
        for (Pin p : n.getpins()){
            if (p.top) {
                for (int z = 0; z < n.trackZ() - 1; z++){
                    channel.setBlock(p.xPos(), 0, z, Blocks.REDSTONE_WIRE.getDefaultState());
                }
            } else {
                for (int z = n.trackZ() + 2; z < this.sizeZ(); z++){
                    channel.setBlock(p.xPos(), 0, z, Blocks.REDSTONE_WIRE.getDefaultState());
                }
            }
        }

        if (n.isOutpath()){
            for (int z = n.trackZ() + 2; z < n.out_partner.trackZ() - 1; z++){
                channel.setBlock(n.x_max, 0, z, Blocks.REDSTONE_WIRE.getDefaultState());
            }
        }
    }

    public void repeatNets(Circuit channel, Net n) {

        for (Pin p : n.getpins()) {
            if (p.top) {
                if (n.trackZ() > 10) {
                    for (int z = n.trackZ() - 10; z > -1; z -= 10){
                        channel.setBlock(p.xPos(), 0, z, Blocks.UNPOWERED_REPEATER.getDefaultState().withProperty(Utils.getPropertyByName(Blocks.UNPOWERED_REPEATER, "facing"), EnumFacing.NORTH));
                    }
                }

                if (p.xPos() > n.x_min) {
                    channel.setBlock(p.xPos() - 1, 2, n.trackZ(), Blocks.UNPOWERED_REPEATER.getDefaultState().withProperty(Utils.getPropertyByName(Blocks.UNPOWERED_REPEATER, "facing"), EnumFacing.EAST));
                }

                if (p.xPos() < n.x_max) {
                    channel.setBlock(p.xPos() + 1, 2, n.trackZ(), Blocks.UNPOWERED_REPEATER.getDefaultState().withProperty(Utils.getPropertyByName(Blocks.UNPOWERED_REPEATER, "facing"), EnumFacing.WEST));
                }
            } else {
                if (channel.getSizeZ() - n.trackZ() > 10) {
                    for (int z = n.trackZ() + 3; z < channel.getSizeZ(); z += 10){
                        channel.setBlock(p.xPos(), 0, z, Blocks.UNPOWERED_REPEATER.getDefaultState().withProperty(Utils.getPropertyByName(Blocks.UNPOWERED_REPEATER, "facing"), EnumFacing.NORTH));
                    }
                }

                if (p.xPos() > n.x_min) {
                    if (p.xPos() > n.top_pin.xPos()) channel.setBlock(p.xPos() - 1, 2, n.trackZ(), Blocks.UNPOWERED_REPEATER.getDefaultState().withProperty(Utils.getPropertyByName(Blocks.UNPOWERED_REPEATER, "facing"), EnumFacing.WEST));
                }

                if (p.xPos() < n.x_max) {
                    if (p.xPos() < n.top_pin.xPos()) channel.setBlock(p.xPos() + 1, 2, n.trackZ(), Blocks.UNPOWERED_REPEATER.getDefaultState().withProperty(Utils.getPropertyByName(Blocks.UNPOWERED_REPEATER, "facing"), EnumFacing.EAST));
                }
            }
        }

        if (n.x_max - n.x_min > 19) {
            for (int x = n.x_min + 2; x < n.x_max - 2; x += 14) {
                for (Pin p : n.getpins()){
                    if (x == p.xPos()) x-= 1;
                }

                if (x < n.top_pin.xPos()) channel.setBlock(x, 2, n.trackZ(), Blocks.UNPOWERED_REPEATER.getDefaultState().withProperty(Utils.getPropertyByName(Blocks.UNPOWERED_REPEATER, "facing"), EnumFacing.EAST));
                if (x > n.top_pin.xPos()) channel.setBlock(x, 2, n.trackZ(), Blocks.UNPOWERED_REPEATER.getDefaultState().withProperty(Utils.getPropertyByName(Blocks.UNPOWERED_REPEATER, "facing"), EnumFacing.WEST));
            }
        }

        if (n.isOutpath()){
            if (n.out_partner.trackZ() - n.trackZ() > 14) {
                for (int z = n.trackZ() + 3; z < n.out_partner.trackZ() - 2; z += 13){
                    channel.setBlock(n.x_max, 0, z, Blocks.UNPOWERED_REPEATER.getDefaultState().withProperty(Utils.getPropertyByName(Blocks.UNPOWERED_REPEATER, "facing"), EnumFacing.NORTH));
                }
            }

            channel.setBlock(n.x_max - 1, 2, n.trackZ(), Blocks.UNPOWERED_REPEATER.getDefaultState().withProperty(Utils.getPropertyByName(Blocks.UNPOWERED_REPEATER, "facing"), EnumFacing.WEST));
        }

        if (!n.isOutpath() && n.out_partner != null){
            channel.setBlock(n.x_max - 1, 2, n.trackZ(), Blocks.UNPOWERED_REPEATER.getDefaultState().withProperty(Utils.getPropertyByName(Blocks.UNPOWERED_REPEATER, "facing"), EnumFacing.EAST));

        }
    }

    public int sizeX(){
        int x_max = 0;

        for (ArrayList<Net> t : this.tracks){
            for (Net n : t){
                if (n.x_max > x_max) x_max = n.x_max;
            }
        }
        return x_max;
    }

    public int sizeZ(){
        return 2 + (this.tracks.size() * 3);
    }

}
