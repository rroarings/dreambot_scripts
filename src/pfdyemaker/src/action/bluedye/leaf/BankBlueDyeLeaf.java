package pfdyemaker.src.action.bluedye.leaf;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.ScriptManager;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import pfdyemaker.src.data.DyeMakerConfig;

public class BankBlueDyeLeaf extends Leaf {

    @Override
    public boolean isValid() {
        return BankLocation.DRAYNOR.getArea(1).contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        if (!Bank.isOpen()) {
            if (Bank.open()) {
                DyeMakerConfig.dyeConfig().setStatus("Opening bank");
                Sleep.sleepUntil(Bank::isOpen, 4000, 600);
            }
        }

        if (Bank.isOpen()) {
            if (Bank.depositAllItems()) {
                DyeMakerConfig.dyeConfig().setStatus("Depositing all items");
                Sleep.sleepUntil(Inventory::isEmpty, 4000, 600);
            }
            if (Inventory.isEmpty()) {
                if (!Inventory.contains(item -> item.getName().equals("Coins"))) {
                    withdrawCoins();
                }
            }
        }



        /*if (Bank.isOpen() && Bank.contains(DyeMakerConfig.dyeConfig().getDyeIngredient()) && !Inventory.contains(DyeMakerConfig.dyeConfig().getDyeIngredient())) {
            if (Bank.contains(item -> item.getName().equals("Coins")) && Bank.count(item -> item.getName().equals("Coins")) >= minGoldToWithdraw && Bank.contains(DyeMakerConfig.dyeConfig().getDyeIngredient()) ) {
                DyeMakerConfig.dyeConfig().setStatus("Withdrawing coins");

                Sleep.sleepUntil(() -> Inventory.contains(DyeMakerConfig.dyeConfig().getDyeIngredient()), 4000, 800);
            }
        }*/

        if (Bank.isOpen() && !Bank.contains(DyeMakerConfig.dyeConfig().getDyeIngredient())) {
            if (Bank.close()) {
                Sleep.sleepUntil(() -> !Bank.isOpen(), 4000, 600);
            }
            if (!Bank.isOpen() && !Inventory.contains(DyeMakerConfig.dyeConfig().getDyeIngredient())) {
                DyeMakerConfig.dyeConfig().setStatus("Logging out");
                Logger.log("script manager -> stopping script");
                Logger.log("stop reason -> Out of dye ingredient: "  + DyeMakerConfig.dyeConfig().getDyeIngredient());
                ScriptManager.getScriptManager().stop();
            }
        }

        return 1000;
    }

    private int calculateMinGoldToWithdraw() {
        // Get the amount of woad leaves in the inventory
        int woadLeafCount = DyeMakerConfig.dyeConfig().getPricedItem().getAmount();

        // Define the cost of making a blue dye (2 woad leaves and 5 coins)
        int woadLeavesPerDye = 2;
        int coinsPerDye = 5;

        // Calculate the number of dyes that can be made with the available woad leaves
        int dyesCanBeMade = woadLeafCount / woadLeavesPerDye;

        // Calculate the total gold required to make the dyes
        int totalGoldRequired = dyesCanBeMade * coinsPerDye;

        // Return the total gold required
        return totalGoldRequired;
    }

    private void withdrawCoins() {
        int minGoldToWithdraw = calculateMinGoldToWithdraw();
        if (Inventory.count(item -> item.getName().equals("Coins")) == minGoldToWithdraw) {
            if (Bank.isOpen()) {
                Logger.log("(dyes) (bankBlueDye) bank is open");
                if (Bank.contains(item -> item.getName().equals("Coins")) && Bank.count(item -> item.getName().equals("Coins")) >= 30) {
                    if (Bank.withdraw(item -> item.getName().equals("Coins"), minGoldToWithdraw)) {
                        Sleep.sleepUntil(() -> Inventory.count(item -> item.getName().equals("Coins")) == minGoldToWithdraw, 4000, 600);
                        Logger.log("(dyes) (bankBlueDye) withdrew " + minGoldToWithdraw + " gold");

                    }
                } else {
                    Logger.log("(dyes) (bankBlueDye) not enough gold to continue [ stop2 - less than 30 gold remains ]");
                    //ScriptManager.
                }
            } else Bank.open();
        }
    }

}
