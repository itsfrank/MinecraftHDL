package minecrafthdl.synthesis.routing.pins;

/**
 * Created by Francis O'Brien - 3/4/2017 - 6:36 AM
 */

public class PinPair {
    public Pin top, bot;

    public PinPair(int x){
        this.top = new EmptyPin(x, true);
        this.bot = new EmptyPin(x, false);
    }

}
