package pfitemlooter.src.activity.leaf.banking;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Sleep;
import pfitemlooter.src.data.ItemLooterConfig;

public class DepositFishFood extends Leaf {
     ItemLooterConfig c = ItemLooterConfig.getInstance();

    @Override
    public boolean isValid() {
        return BankLocation.DRAYNOR.getArea(1).contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        if (!Bank.isOpen()) {
            if (Bank.open()) {
                Sleep.sleepUntil(Bank::isOpen, 2000, 50);
            }
        }

        if (Bank.isOpen()) {
            c.setStatus("Banking");
            if (Inventory.contains("Fish food")) {
                if (Bank.depositAll("Fish food")) {
                    Sleep.sleepUntil(() -> !Inventory.contains("Fish food"), 3000, 50);
                    Bank.close();
                }
            }
        }
        return 600;
    }
}
