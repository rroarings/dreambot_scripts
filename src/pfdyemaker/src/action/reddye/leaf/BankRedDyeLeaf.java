package pfdyemaker.src.action.reddye.leaf;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.api.script.ScriptManager;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import pfdyemaker.src.data.DyeMakerConfig;

public class BankRedDyeLeaf extends Leaf {

    @Override
    public boolean isValid() {
        return BankLocation.DRAYNOR.getArea(3).contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        if (!Bank.isOpen()) {
            if (Bank.open()) {
                Logger.log("(dyemaker) (bankRedDye) opening bank");
                DyeMakerConfig.dyeConfig().setStatus("Opening bank");
                Sleep.sleepUntil(Bank::isOpen, 5000, 600);
            }
        }

        if (Bank.isOpen()) {
            Logger.log("(dyemaker) (bankRedDye) bank is open");
            if (!Inventory.isEmpty()) {
                DyeMakerConfig.dyeConfig().setStatus("Depositing all items");
                if (Bank.depositAllItems()) {
                    Sleep.sleepUntil(Inventory::isEmpty, 5000, 600);
                    Logger.log("(dyemaker) (bankRedDye) deposit all");
                }
            }

            if (Bank.contains(item -> item.getName().equals("Coins")) && Bank.get(item -> item.getName().equals("Coins")).getAmount() >= goldToWithdraw()) {
                DyeMakerConfig.dyeConfig().setStatus("Withdrawing coins");
                if (Bank.withdraw(item -> item.getName().equals("Coins"), goldToWithdraw())) {
                    Sleep.sleepUntil(() -> Inventory.contains(item -> item.getName().equals("Coins")), 5000, 600);
                    Logger.log("(dyemaker) (bankRedDye) withdrew coins");
                }
            }

            if (Bank.contains(DyeMakerConfig.dyeConfig().getDyeIngredient()) && !Inventory.contains(DyeMakerConfig.dyeConfig().getDyeIngredient())) {
                DyeMakerConfig.dyeConfig().setStatus("Withdrawing " + DyeMakerConfig.dyeConfig().getDyeIngredient());
                if (Bank.withdrawAll(DyeMakerConfig.dyeConfig().getDyeIngredient())) {
                    Sleep.sleepUntil(() -> Inventory.contains(DyeMakerConfig.dyeConfig().getDyeIngredient()), 5000, 600);
                    Logger.log("(dyemaker) (bankRedDye) withdrew redberries");
                }
            }

            if (!Inventory.contains(DyeMakerConfig.dyeConfig().getDyeIngredient())) {
                DyeMakerConfig.dyeConfig().setStatus("Logging out");
                Logger.log("(dyemaker) script manager: stopping script");
                Logger.log("(dyemaker) [ stop 4 ] -> out of dye ingredient: " + DyeMakerConfig.dyeConfig().getDyeIngredient());
                ScriptManager.getScriptManager().stop();
            }
        }
        return 600;
    }

    private int goldToWithdraw() {
        return 45;
    }

}