package pfminer.src.leafs;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import pfminer.src.framework.Leaf;

public class GoToGELeaf extends Leaf {

    @Override
    public boolean isValid() {
        return !BankLocation.GRAND_EXCHANGE.getArea(7).contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        if(Walking.shouldWalk(6)) Walking.walk(BankLocation.GRAND_EXCHANGE);
        return Calculations.random(500,1000);
    }
}
