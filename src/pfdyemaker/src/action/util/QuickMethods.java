package pfdyemaker.src.action.util;

import org.dreambot.api.Client;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.methods.world.Worlds;
import org.dreambot.api.methods.worldhopper.WorldHopper;
import org.dreambot.api.utilities.Sleep;
import pfdyemaker.src.data.DyeMakerConfig;

public class QuickMethods {

    public static void withdrawEnergyPotions() {
        if (DyeMakerConfig.isUseEnergyPotions()) {
            DyeMakerConfig.getDyeMakerConfig().setStatus("Withdrawing potions");
            if (Bank.contains(item -> item.getName().contains("Energy potion("))) {
                Bank.withdraw(item -> item.getName().contains("Energy potion("), 3);
                Bank.close();
            }
        }
    }

    public static void drinkEnergyPotion() {
        int currentEnergy = Walking.getRunEnergy();
        if (DyeMakerConfig.isUseEnergyPotions()) {
            if (!Bank.isOpen()) {
                if (currentEnergy <= Walking.getRunThreshold() && Inventory.contains(item -> item.getName().contains("Energy potion("))) {
                    Inventory.interact(item -> item.getName().contains("Energy potion("), "Drink");
                    Sleep.sleepUntil(() -> Walking.getRunEnergy() >= 40, 3000, 600);
                }
            }
        }
    }

    public static void worldHop() {
        World cWorld = Worlds.getCurrent();
        World worldToHop = Worlds.getRandomWorld(world -> world != null && world.isF2P() && world.isNormal() && world.getMinimumLevel() <= Players.getLocal().getLevel());
        int delay = Calculations.random(DyeMakerConfig.getWorldHopDelayMin(), DyeMakerConfig.getWorldHopDelayMax());
        Sleep.sleep(delay);
        DyeMakerConfig.getDyeMakerConfig().setStatus("Hopping worlds, delay: " + delay);
        WorldHopper.hopWorld(worldToHop);
        Sleep.sleepUntil(() -> Worlds.getCurrent() != cWorld || Client.isLoggedIn(), 8000, 300);
    }
}
