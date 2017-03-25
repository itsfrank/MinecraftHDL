package minecrafthdl.synthesis.routing;

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

    public static final char[] EXTENDED = { 0x00C7, 0x00FC, 0x00E9, 0x00E2,
            0x00E4, 0x00E0, 0x00E5, 0x00E7, 0x00EA, 0x00EB, 0x00E8, 0x00EF,
            0x00EE, 0x00EC, 0x00C4, 0x00C5, 0x00C9, 0x00E6, 0x00C6, 0x00F4,
            0x00F6, 0x00F2, 0x00FB, 0x00F9, 0x00FF, 0x00D6, 0x00DC, 0x00A2,
            0x00A3, 0x00A5, 0x20A7, 0x0192, 0x00E1, 0x00ED, 0x00F3, 0x00FA,
            0x00F1, 0x00D1, 0x00AA, 0x00BA, 0x00BF, 0x2310, 0x00AC, 0x00BD,
            0x00BC, 0x00A1, 0x00AB, 0x00BB, 0x2591, 0x2592, 0x2593, 0x2502,
            0x2524, 0x2561, 0x2562, 0x2556, 0x2555, 0x2563, 0x2551, 0x2557,
            0x255D, 0x255C, 0x255B, 0x2510, 0x2514, 0x2534, 0x252C, 0x251C,
            0x2500, 0x253C, 0x255E, 0x255F, 0x255A, 0x2554, 0x2569, 0x2566,
            0x2560, 0x2550, 0x256C, 0x2567, 0x2568, 0x2564, 0x2565, 0x2559,
            0x2558, 0x2552, 0x2553, 0x256B, 0x256A, 0x2518, 0x250C, 0x2588,
            0x2584, 0x258C, 0x2590, 0x2580, 0x03B1, 0x00DF, 0x0393, 0x03C0,
            0x03A3, 0x03C3, 0x00B5, 0x03C4, 0x03A6, 0x0398, 0x03A9, 0x03B4,
            0x221E, 0x03C6, 0x03B5, 0x2229, 0x2261, 0x00B1, 0x2265, 0x2264,
            0x2320, 0x2321, 0x00F7, 0x2248, 0x00B0, 0x2219, 0x00B7, 0x221A,
            0x207F, 0x00B2, 0x25A0, 0x00A0 };

    public static final char getAscii(int code) {
        if (code >= 0x80 && code <= 0xFF) {
            return EXTENDED[code - 0x7F];
        }
        return (char) code;
    }

}
