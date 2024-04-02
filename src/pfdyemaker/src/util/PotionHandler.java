package pfdyemaker.src.util;

import org.dreambot.api.Client;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.methods.world.Worlds;
import org.dreambot.api.methods.worldhopper.WorldHopper;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import pfdyemaker.src.data.DyeMakerConfig;

public class PotionHandler {

    private static final String ENERGY_POTION_NAME = "Energy potion(";
    private static final String VIAL_NAME = "Vial";
    private static final int ENERGY_THRESHOLD = 50;

    public static void handleEnergyPotion() {
        if (shouldWithdrawEnergyPotion()) {
            withdrawEnergyPotion();
        } else if (shouldDrinkEnergyPotion()) {
            drinkEnergyPotion();
        } else if (shouldDepositVial()) {
            depositVial();
        }
    }

    private static boolean shouldWithdrawEnergyPotion() {
        return DyeMakerConfig.getDyeMakerConfig().isUseEnergyPotions()
                && !Inventory.contains(item -> item.getName().contains(ENERGY_POTION_NAME))
                && Bank.isOpen()
                && Bank.contains(item -> item.getName().contains(ENERGY_POTION_NAME));
    }

    private static boolean shouldDrinkEnergyPotion() {
        return DyeMakerConfig.getDyeMakerConfig().isUseEnergyPotions()
                && Walking.getRunEnergy() <= ENERGY_THRESHOLD
                && Inventory.contains(item -> item.getName().contains(ENERGY_POTION_NAME));
    }

    private static boolean shouldDepositVial() {
        return DyeMakerConfig.getDyeMakerConfig().isUseEnergyPotions()
                && Bank.isOpen()
                && Inventory.contains(VIAL_NAME);
    }

    private static void withdrawEnergyPotion() {
        DyeMakerConfig config = DyeMakerConfig.getDyeMakerConfig();
        config.setStatus("Withdrawing energy potion");
        Logger.log("potionhandler - withdrew energy potion ");
        Bank.withdraw(item -> item.getName().contains(ENERGY_POTION_NAME), 1);
        Bank.close();
    }

    private static void drinkEnergyPotion() {
        Logger.log("pre-potion energy: " + Walking.getRunEnergy());
        Inventory.interact(item -> item.getName().contains(ENERGY_POTION_NAME), "Drink");
        Logger.log("potionhandler - potion sip ");
        Logger.log("post-potion energy: " + Walking.getRunEnergy());
        Sleep.sleep(Calculations.random(100, 300));
    }

    private static void depositVial() {
        DyeMakerConfig config = DyeMakerConfig.getDyeMakerConfig();
        config.setStatus("Depositing vials");
        Logger.log("potionhandler - deposit vials ");
        Bank.depositAll(VIAL_NAME);
        Sleep.sleepUntil(Inventory::isEmpty, 3000, 600);
    }

    public static void worldHop() {
        DyeMakerConfig config = DyeMakerConfig.getDyeMakerConfig();
        World cWorld = Worlds.getCurrent();
        World worldToHop = Worlds.getRandomWorld(world -> world != null && world.isF2P() && world.isNormal() && world.getMinimumLevel() <= Players.getLocal().getLevel());
        int delay = Calculations.random(config.getWorldHopDelayMin(), config.getWorldHopDelayMax());
        Sleep.sleepTicks(delay);
        config.setStatus("Hopping worlds");
        WorldHopper.hopWorld(worldToHop);
        Sleep.sleepUntil(() -> Worlds.getCurrent() != cWorld || Client.isLoggedIn(), 8000, 600);
    }
}

