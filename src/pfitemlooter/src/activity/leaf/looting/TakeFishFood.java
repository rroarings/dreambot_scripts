package pfitemlooter.src.activity.leaf.looting;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.item.GroundItems;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.methods.world.Worlds;
import org.dreambot.api.methods.worldhopper.WorldHopper;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.items.GroundItem;
import pfitemlooter.src.data.ItemLooterConfig;

public class TakeFishFood extends Leaf {

    ItemLooterConfig c = ItemLooterConfig.getInstance();

    @Override
    public boolean isValid() {
        Area area = new Area(3107, 3360, 3110, 3354, 1);
        return area.contains(Players.getLocal()) && !Inventory.isFull();
    }

    @Override
    public int onLoop() {
        GroundItem ff = GroundItems.closest(groundItem -> groundItem.isOnScreen() && groundItem.getName().equals("Fish food"));
        c.getPricedItem().update();

        if (ff != null) {
            if (ff.interact("Take")) {
                c.setStatus("Taking fish food");
                Sleep.sleepUntil(() -> false, 2000, 50);
            }
            return 100;
        } else c.setStatus("Waiting for food to spawn");

        if (worldHopIfPlayerNearby()) {
            c.setStatus("Hopping worlds");
            return 1000;
        }
        return 600;
    }

    private boolean worldHopIfPlayerNearby() {
        if (Players.all(player -> !player.equals(Players.getLocal())).size() > 0) {
            World world = Worlds.getRandomWorld(w -> w.isNormal() && w.getMinimumLevel() <= Players.getLocal().getLevel() && w.isF2P());
            if (world != null) {
                c.setStatus("Hopping worlds");
                return WorldHopper.hopWorld(world);
            }
        }
        return false;
    }
}
