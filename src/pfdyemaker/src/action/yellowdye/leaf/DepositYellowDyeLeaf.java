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
import pfdyemaker.src.util.PotionHandler;

public class DepositYellowDyeLeaf extends Leaf {

    DyeMakerConfig config = DyeMakerConfig.getDyeMakerConfig();

    @Override
    public boolean isValid() {
        return Inventory.contains(item -> item.getName().equals("Yellow dye"))
                && Inventory.count(config.ONION) <= 1;
    }

    @Override
    public int onLoop() {
        if (!BankLocation.DRAYNOR.getArea(3).contains(Players.getLocal())) {
            if (Walking.shouldWalk()) {
                Walking.walk(BankLocation.DRAYNOR.getCenter());
                config.setStatus("Walking to Draynor bank");
                Logger.log("(yellowdye) (deposit) walking to draynor bank");
                Sleep.sleepUntil(() -> BankLocation.DRAYNOR.getArea(3).contains(Players.getLocal()), 3000, 600);
            }
        }
       if (!Bank.isOpen()) {
           if (Bank.open()) {
               PotionHandler.handleEnergyPotion();
               if (Bank.depositAllExcept(item -> item.getName().equals("Coins") && item.isValid())) {
                   config.setStatus("Depositing dye");
                   Logger.log("(yellowdye) (deposit) Deposited dye");
                   Sleep.sleepUntil(() -> Inventory.onlyContains(item -> item.getName().equals("Coins") && item.isValid()), 6000, 600);
               }
           }
       }
        return 600;
    }
}
