package pfropebuyer.src.branches.navigation;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.interactive.Players;
import pfropebuyer.src.framework.Branch;

public class WalkToDraynorBankBranch extends Branch {

    @Override
    public boolean isValid() {
        return Inventory.contains("Rope") && Inventory.isFull() && !BankLocation.DRAYNOR.getArea(1).contains(Players.getLocal());
    }
}
