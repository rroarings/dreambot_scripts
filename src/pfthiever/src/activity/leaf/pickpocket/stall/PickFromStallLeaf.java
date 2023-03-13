package pfthiever.src.activity.leaf.pickpocket.stall;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.methods.world.Worlds;
import org.dreambot.api.methods.worldhopper.WorldHopper;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.items.Item;
import pfthiever.src.data.Thieving;

public class PickFromStallLeaf extends Leaf {

    private final Tile stallTile = new Tile(1795, 3607, 0);

    @Override
    public boolean isValid() {
        return !Inventory.isFull() && !Players.getLocal().isAnimating() && !Players.getLocal().isInCombat();
    }

    @Override
    public int onLoop() {
        GameObject b_stall = GameObjects.closest(obj -> obj.getName().equals(Thieving.FRUIT_STALL.getName()));

        if (Players.all().size() > 1) {
            int cw = Worlds.getCurrentWorld();
            World hop2world = Worlds.getRandomWorld(world -> world.isMembers() && world.isNormal() && !world.isPVP() && world.getMinimumLevel() < 1);
            WorldHopper.hopWorld(hop2world);
            Sleep.sleepUntil(() -> Worlds.getMyWorld().getWorld() != cw, 6000, 100);
        }

        for (int i = 0; i < 28; i++) {
            Item item = Inventory.getItemInSlot(i);
            if (item != null) {
                for (String name : Thieving.FRUIT_STALL.getDropItems()) {
                    if (name != null && name.equals(item.getName())) {
                        Inventory.slotInteract(i, "Drop");
                        Sleep.sleep(200, 400);
                        break;
                    }
                }
            }
        }

        if (b_stall != null) {
            if (!Players.getLocal().isAnimating()) {
                if (Inventory.isEmpty()) {
                    if (b_stall.interact(Thieving.FRUIT_STALL.getAction())) {
                        Sleep.sleepUntil(() -> (long) Inventory.all().size() > 0, 5000, 100);
                    }
                }
            }
        }
        return 600;
    }
}
