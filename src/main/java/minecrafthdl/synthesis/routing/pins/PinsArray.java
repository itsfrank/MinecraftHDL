package minecrafthdl.synthesis.routing.pins;

import minecrafthdl.MHDLException;

import java.util.ArrayList;

/**
 * Created by Francis O'Brien - 3/4/2017 - 5:37 AM
 */

public class PinsArray {
    ArrayList<PinPair> pairs;

    public PinsArray() {
        pairs = new ArrayList<PinPair>();
    }


    public void addPin(Pin p, boolean top) {
        int index = p.x / 2;
        int difference = index - this.pairs.size();

        if (difference >= 0){
            for (int i = 0; i < difference; i++){
                this.pairs.add(new PinPair(this.pairs.size() * 2));
            }
            PinPair new_pair = new PinPair(this.pairs.size() * 2);
            if(top) new_pair.top = p;
            else new_pair.bot = p;

            this.pairs.add(new_pair);
        } else {
            PinPair pair = this.pairs.get(index);

            if(top){
                if (pair.top.empty()) pair.top = p;
                else throw new MHDLException("Attempting to overwrite pin in pair");
            } else {
                if (pair.bot.empty()) pair.bot = p;
                else throw new MHDLException("Attempting to overwrite pin in pair");
            }
        }
    }

    public int addEmptyPair(){
        int x = this.pairs.size() * 2;
        this.pairs.add(new PinPair(x));
        return x;
    }

    public PinPair getPair(int index){
        return this.pairs.get(index);
    }
    
    public ArrayList<PinPair> getPairs(){
        return this.pairs;
    }
}
