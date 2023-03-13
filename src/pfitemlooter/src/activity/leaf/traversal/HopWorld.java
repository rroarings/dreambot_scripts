package pfitemlooter.src.activity.leaf.traversal;

import org.dreambot.api.Client;
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

public class HopWorld extends Leaf {
    ItemLooterConfig c = ItemLooterConfig.getInstance();

    @Override
    public boolean isValid() {
        GroundItem ff = GroundItems.closest(groundItem -> groundItem.isOnScreen() && groundItem.getName().equals("Fish food"));
        Area area = new Area(3107, 3357, 3110, 3354, 1);
        return area.contains(Players.getLocal()) && !ff.exists() && !Inventory.isFull();
    }

    @Override
    public int onLoop() {
        World rf2p = Worlds.getRandomWorld(world -> world.isF2P() && world.isNormal() && world.getMinimumLevel() <= Players.getLocal().getLevel());
        World rp2p = Worlds.getRandomWorld(world -> world.isMembers() && world.isNormal() && world.getMinimumLevel() <= Players.getLocal().getLevel());

        if (Client.isMembers()) {
            c.setStatus("Hopping worlds");
            WorldHopper.hopWorld(rp2p);
            Sleep.sleepUntil(Client::isLoggedIn, 5000, 50);
        } else {
            c.setStatus("Hopping worlds");
            WorldHopper.hopWorld(rf2p);
            Sleep.sleepUntil(Client::isLoggedIn, 5000, 50);
        }
        return 600;
    }
}
