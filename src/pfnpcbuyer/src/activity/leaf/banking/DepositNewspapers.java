package pfnpcbuyer.src.activity.leaf.banking;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Sleep;
import pfnpcbuyer.src.data.NPCBuyerConfig;

public class DepositNewspapers extends Leaf {

    NPCBuyerConfig nbc = NPCBuyerConfig.getInstance();

    @Override
    public boolean isValid() {
        return BankLocation.VARROCK_EAST.getArea(1).contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        if (!Bank.isOpen()) {
            if (Bank.open()) {
                Sleep.sleepUntil(Bank::isOpen, 2000, 50);
            }
        }

        if (Bank.isOpen()) {
            nbc.setStatus("Banking");
            if (Inventory.contains("Newspaper")) {
                if (Bank.depositAll("Newspaper")) {
                    Sleep.sleepUntil(() -> !Inventory.contains("Newspaper"), 3000, 50);
                    Bank.close();
                }
            }
        }
        return 600;
    }
}
