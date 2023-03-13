package pfdriftnets.src.location.fossilisland;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.widget.helpers.ItemProcessing;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.GameObject;
import pfdriftnets.src.data.DriftNetsConfig;
import pfdriftnets.src.framework.Leaf;

public class SpinNetsLeaf extends Leaf {

     DriftNetsConfig config = DriftNetsConfig.getDriftNetsConfig();
    private final Tile loomTile = new Tile(3741, 3805,0);


    @Override
    public boolean isValid() {
        return Inventory.contains("Jute fibre") && Inventory.count("Jute fibre") >= 2;
    }

    @Override
    public int onLoop() {
        GameObject loom = GameObjects.closest(gameObject -> gameObject.getName().contains("Loom") && gameObject.exists());

        if (loom == null) return 800;

        if (!Players.getLocal().isAnimating() && Inventory.count("Jute fibre") >= 2) {
            if (loom.interact("Weave")) {
                DriftNetsConfig.setStatus("Loom interact");
                Sleep.sleepUntil(ItemProcessing::isOpen, 3000);
            }
        }

        if (ItemProcessing.isOpen()) {
            if (ItemProcessing.makeAll("Drift net")) {
                DriftNetsConfig.setStatus("Making drift nets");
                Sleep.sleepUntil(() -> !Inventory.contains("Jute fibre"), 2000, 800);
            }
        }
        return 1000;
    }
}
