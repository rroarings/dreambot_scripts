package pfropebuyer.src.leafs.banking;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.utilities.Sleep;
import pfropebuyer.src.data.RopeBuyerConfig;
import pfropebuyer.src.framework.Leaf;

public class ShouldBankRopesLeaf extends Leaf {

    @Override
    public boolean isValid() {
        return Inventory.contains("Rope") && Inventory.isFull() && BankLocation.DRAYNOR.getArea(1).contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        if (!Bank.isOpen()) {
            RopeBuyerConfig.status = "Opening bank";
            Bank.open();
            Sleep.sleepUntil(Bank::isOpen, 2000);
        }

        if (Bank.isOpen()) {
            RopeBuyerConfig.status = "Deposit items";
            Bank.depositAll(item -> item.getName().contains("Rope"));
            Sleep.sleepUntil(() -> !Inventory.contains("Rope"), 2000);
            Bank.close();
        }
        return 1000;
    }
}
