package pfcows.src.action.branch.bank;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.frameworks.treebranch.Branch;

public class DepositLootBranch extends Branch {

    @Override
    public boolean isValid() {
        return Inventory.isFull() && BankLocation.LUMBRIDGE.getArea(3).contains(Players.getLocal()) || Bank.isOpen();
    }
}
