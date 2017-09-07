package minecrafthdl.synthesis.routing;

import minecrafthdl.MHDLException;
import minecrafthdl.synthesis.routing.pins.Pin;

import java.util.ArrayList;

/**
 * Created by Francis O'Brien - 3/3/2017 - 6:18 AM
 */

public class Net {

    public static int num_nets = 0;

    int id;
    int track = -1;

    private ArrayList<Pin> pins = new ArrayList<Pin>();
    Pin top_pin;

    int x_min = Integer.MAX_VALUE, x_max = -1;


    boolean outpath = false;
    Pin out_pin;
    Net out_partner;

    public Net(){
        this.id = Net.num_nets;
        Net.num_nets++;
    }

    public Net(int x_min, int x_max){
        this.id = Net.num_nets;
        Net.num_nets++;
        this.x_min = x_min;
        this.x_max = x_max;
    }

    public void addPin(Pin p, boolean dogleg){
        if (pins.contains(p)) return;
        p.setNet(this.id, dogleg);
        this.pins.add(p);
        if (p.top) this.top_pin = p;

        if (p.xPos() < x_min) this.x_min = p.xPos();
        if (p.xPos() > x_max) this.x_max = p.xPos();
    }

    public boolean hasHorizontalConflict(Net other){
        if (this.x_max < other.x_min || this.x_min > other.x_max) return false;
        return true;
    }

    public void setTrack(int track){
        if (this.hasTrack()) throw new MHDLException("Net already has a track");
        this.track = track;
    }

    public void setOutNet(Pin out_pin, Net original){
        this.pins.add(out_pin);
        this.outpath = true;
        this.out_pin = out_pin;
        this.top_pin = out_pin;
        out_pin.setNet(this.id, true);

        this.out_partner = original;
        original.out_partner = this;
        this.out_partner.pins.remove(out_pin);
    }

    public int AssignOutColX(int out_col_x){
        this.x_max = out_col_x;
        this.x_min = out_pin.xPos();
        this.out_partner.x_max = out_col_x;
        return out_col_x;
    }

    public boolean isOutpath(){
        return outpath;
    }

    public boolean hasTrack(){
        return track != -1;
    }

    public int getId(){
        return this.id;
    }

    public ArrayList<Pin> getpins() {
        return pins;
    }

    public void newXMax(int x_max) {
        this.x_max = x_max;
    }

    public int trackZ(){
        return (this.track == 0) ? 2 : (this.track * 3) + 2;
    }
}
