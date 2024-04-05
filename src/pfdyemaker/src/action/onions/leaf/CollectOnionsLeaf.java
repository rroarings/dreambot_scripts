package pfdyemaker.src.action.onions.leaf;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.GameObject;
import pfdyemaker.src.data.DyeMakerConfig;

public class CollectOnionsLeaf extends Leaf {

    DyeMakerConfig config = DyeMakerConfig.getDyeMakerConfig();

    @Override
    public boolean isValid() {
        return !Inventory.isFull() && config.ONION_AREA.contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        GameObject ONION = GameObjects.closest(gameObject -> gameObject.getName().contains("Onion") && gameObject.hasAction("Pick"));

        if (ONION == null) {
            config.setStatus("Waiting for onion to spawn");
            return 600;
        }

        if (Inventory.contains("Onion seed")) {
            config.setStatus("Dropping onion seed");
            Inventory.interact("Onion seed", "Drop");
            Sleep.sleepUntil(() -> !Inventory.contains("Onion seed"), 600);
        }

        int bCount = Inventory.count(config.ONION);
        if (ONION.interact("Pick")) {
            config.setStatus("Picking onion");
            Sleep.sleepUntil(() -> Inventory.count(config.ONION) > bCount, 5000, 600);
            config.pricedItem.update();
        }
        return 600;
    }
}
