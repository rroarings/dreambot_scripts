package pfdyemaker.src.leafs.banking;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.interactive.Players;
import pfdyemaker.src.framework.Leaf;

public class DepositYellowDyeLeaf extends Leaf {

    @Override
    public boolean isValid() {
        return Inventory.contains(" dye") && !BankLocation.DRAYNOR.getArea(1).contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        return 0;
    }
}
