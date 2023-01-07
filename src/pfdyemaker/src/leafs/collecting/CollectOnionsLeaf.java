package pfdyemaker.src.leafs.collecting;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.GameObject;
import pfdyemaker.src.data.DyeMakerConfig;
import pfdyemaker.src.framework.Leaf;

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
            DyeMakerConfig.status = "Picking onions";
            Sleep.sleepUntil(() -> !Players.getLocal().isAnimating(), 900);
        }
        return 600;
    }
}
