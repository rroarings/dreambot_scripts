package pfchaosdruids.src.activity.looting.leaf;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.item.GroundItems;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.items.GroundItem;
import pfchaosdruids.src.util.PricedItem;

import java.util.ArrayList;
import java.util.List;

public class LootItemLeaf extends Leaf {

    List<PricedItem> lootTrack = new ArrayList<>();
    Area druidArea = new Area(new Tile(2560, 3358, 0), new Tile(2564, 3354, 0));
    Condition attacking = () -> Players.getLocal().isInCombat();
    GroundItem loot = GroundItems.closest(groundItem -> groundItem.exists() && groundItem.getName().contains(" rune"));

    @Override
    public boolean isValid() {


        if (loot != null && !Players.getLocal().isInCombat()) {
            return true;
        }
        return false;
    }

    @Override
    public int onLoop() {
        if (!Players.getLocal().isInCombat()) {
            if (!Players.getLocal().isAnimating()) {
                if (loot != null && !Inventory.isFull()) {
                    int is = Inventory.size();
                    if (loot.interact("Take")) {
                        Logger.log("taking loot: " + loot);
                        Sleep.sleepUntil(() -> Inventory.size() > is, 5000, 100);
                    }
                }
            }
        }
        return 600;
    }
}
