package pfdyemaker.src.action.yellowdye.leaf;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import pfdyemaker.src.data.DyeMakerConfig;
import pfdyemaker.src.ui.Frame;
import pfdyemaker.src.util.ActionBranch;

public class YellowDyeStartLeaf extends Leaf {

    DyeMakerConfig config = DyeMakerConfig.getDyeMakerConfig();
    private boolean checksComplete = false;

    @Override
    public boolean isValid() {
        ActionBranch branch = Frame.getSelectedItem();
        return branch == ActionBranch.MAKE_YELLOW_DYE && !checksComplete;
    }

    @Override
    public int onLoop() {
        if (!Inventory.isEmpty() || Inventory.isEmpty()) {
            if (!BankLocation.DRAYNOR.getArea(3).contains(Players.getLocal())) {
                if (Walking.shouldWalk()) {
                    config.setStatus("Walking to Draynor bank");
                    Logger.log("(yellowdye) walking to draynor bank");
                    if (Walking.walk(BankLocation.DRAYNOR.getCenter())) {
                        Sleep.sleepUntil(() -> BankLocation.DRAYNOR.getArea(3).contains(Players.getLocal()), 4000, 600);
                    }
                }
            } else if (Bank.open()) {
                config.setStatus("Opening Draynor bank");
                Logger.log("(yellowdye) opening draynor bank");
                Sleep.sleepUntil(Bank::isOpen, 3000, 600);
                if (Bank.isOpen()) {

                    if (!Inventory.isEmpty() && Bank.depositAllItems()) {
                        config.setStatus("Depositing inventory");
                        Logger.log("(yellowdye) items in inventory - depositing inventory ");
                        Sleep.sleepUntil(Inventory::isEmpty, 3000, 600);
                    }

                    if (Inventory.isEmpty() && Bank.withdraw(item -> item.getName().equals("Coins"), config.getGoldToWithdraw())) {
                        config.setStatus("Withdrawing coins");
                        Logger.log("(yellowdye) inventory empty - withdrawing coins");
                        Sleep.sleepUntil(() -> Inventory.contains("Coins"), 3000, 600);
                        checksComplete = true;
                        Logger.log("(yellowdye) checks complete 1");
                    }
                }
            }
        } else if (Inventory.contains(item -> item.getName().contains("Coins"))) {
            checksComplete = true;
            Logger.log("(yellowdye) checks complete 2");
        }
        return 600;
    }
}