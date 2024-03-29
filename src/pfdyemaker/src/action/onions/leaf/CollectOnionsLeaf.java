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

        if (ONION == null) return 600;

        if (ONION.interact("Pick")) {
            config.setStatus("Picking onion");
            Sleep.sleepUntil(() -> !Players.getLocal().isAnimating(), 900, 1000);
            config.pricedItem.update();
        }

        if (Inventory.contains("Onion seed")) {
            config.setStatus("Dropping onion seed");
            Inventory.interact("Onion seed", "Drop");
            Sleep.sleepUntil(() -> !Inventory.contains("Onion seed"), 600);
        }
        return 600;
    }
}
