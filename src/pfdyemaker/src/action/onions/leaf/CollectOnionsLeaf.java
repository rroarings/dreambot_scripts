package pfdyemaker.src.action.onions.leaf;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.GameObject;
import pfdyemaker.src.data.DyeMakerConfig;

public class CollectOnionsLeaf extends Leaf {

    @Override
    public boolean isValid() {
        return !Inventory.isFull() && DyeMakerConfig.dyeConfig().getOnionArea().contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        GameObject onion = GameObjects.closest(gameObject -> gameObject.getName().equals(DyeMakerConfig.dyeConfig().getOnion()) && gameObject.hasAction("Pick"));

        if (onion == null) {
            DyeMakerConfig.dyeConfig().setStatus("Waiting for onion to spawn");
            return 600;
        }

        if (Inventory.contains("Onion seed")) {
            DyeMakerConfig.dyeConfig().setStatus("Dropping onion seed");
            Inventory.interact("Onion seed", "Drop");
            Sleep.sleepUntil(() -> !Inventory.contains("Onion seed"), 600);
        }

        int bCount = Inventory.count(DyeMakerConfig.dyeConfig().getOnion());
        if (onion.interact("Pick")) {
            DyeMakerConfig.dyeConfig().setStatus("Picking onion");
            Sleep.sleepUntil(() -> Inventory.count(DyeMakerConfig.dyeConfig().getOnion()) > bCount, 5000, 600);
            DyeMakerConfig.dyeConfig().getPricedItem().update();
        }
        return 600;
    }
}
