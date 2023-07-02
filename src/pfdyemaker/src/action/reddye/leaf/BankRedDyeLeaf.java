package pfdyemaker.src.action.reddye.leaf;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.login.LoginUtility;
import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.api.script.ScriptManager;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import pfdyemaker.src.data.DyeMakerConfig;

public class BankRedDyeLeaf extends Leaf {

    DyeMakerConfig config = DyeMakerConfig.getDyeMakerConfig();

    @Override
    public boolean isValid() {
        return BankLocation.DRAYNOR.getArea(1).contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        if (!Bank.isOpen()) {
            config.setStatus("Opening bank");
            Bank.open();
            Sleep.sleepUntil(Bank::isOpen, 2000, 800);
        }

        if (Bank.isOpen()) {
            config.setStatus("Depositing dye");
            if (Inventory.contains(item -> item.getName().equals("Red dye"))) {
                Bank.depositAllExcept(item -> item.getName().equals("Coins") && item.isValid());
                Sleep.sleepUntil(() -> Inventory.onlyContains(item -> item.getName().equals("Coins") && item.isValid()), 2000, 800);
            }

        }

        if (Bank.isOpen() && Bank.contains(config.getDyeIngredient()) && !Inventory.contains(config.getDyeIngredient())) {
            if (Bank.count(config.getDyeIngredient()) >= 3 && Inventory.contains("Coins") && Inventory.count("Coins") >= 100) {
                config.setStatus("Withdrawing " + config.getDyeIngredient());
                Bank.withdrawAll(config.getDyeIngredient());
                Sleep.sleepUntil(() -> Inventory.contains(config.getDyeIngredient()), 4000, 800);
            } else {
               if (safeToLog()) {
                   Tabs.logout();
                   Logger.log("script manager -> out of " + config.getDyeIngredient() + " or Coins. - stopping script");
                   ScriptManager.getScriptManager().stop();
               }
            }
        }
        return 600;
    }

    private boolean safeToLog() {
        return !Players.getLocal().isInCombat();
    }
}