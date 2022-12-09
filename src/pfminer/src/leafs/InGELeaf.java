package pfminer.src.leafs;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import pfminer.src.framework.Leaf;

public class InGELeaf extends Leaf {

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public int onLoop() {
        Logger.log("Arrived at destination of GE!");
        Sleep.sleepTick();
        return Calculations.random(500,1000);
    }
}
