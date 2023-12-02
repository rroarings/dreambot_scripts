package pfcows.src.action.branch.traversal;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.frameworks.treebranch.Branch;

public class WalkLumbridgeBankBranch extends Branch {
    @Override
    public boolean isValid() {
        if (Inventory.isFull() && !BankLocation.LUMBRIDGE.getArea(3).contains(Players.getLocal())) {
            return true;
        }
        return false;
    }
}
