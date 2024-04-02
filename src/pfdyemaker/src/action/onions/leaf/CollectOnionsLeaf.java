package pfdyemaker.src.action.onions.leaf;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.GameObject;
import pfdyemaker.src.data.DyeMakerConfig;

public class CollectOnionsLeaf extends Leaf {

    DyeMakerConfig config = DyeMakerConfig.getDyeMakerConfig();
    private int previousOnionCount = 0;

    @Override
    public boolean isValid() {
        return !Inventory.isFull() && config.ONION_AREA.contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        GameObject onion = GameObjects.closest(gameObject -> gameObject.getName().contains("Onion") && gameObject.hasAction("Pick"));
        if (onion != null && onion.interact("Pick")) {
            config.setStatus("Picking onion");
            Logger.log("(onions) picking onion...");
            waitForOnionInInventory();
        }
        return 600;
    }

    private void waitForOnionInInventory() {
        Timer timer = new Timer();
        while (timer.elapsed() < 3000) {
            int currentOnionCount = Inventory.count("Onion");
            if (currentOnionCount > previousOnionCount) {
                config.setStatus("Picking wait");
                Logger.log("(onions) waited for onion in inventory, current count: " + currentOnionCount);
                previousOnionCount = currentOnionCount;
                return;
            }
            Sleep.sleep(600); // Adjust the sleep time as needed
        }
        Logger.log("(onions) timed out waiting for onion in inventory");
    }
}
