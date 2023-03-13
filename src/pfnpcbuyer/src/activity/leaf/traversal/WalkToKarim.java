package pfnpcbuyer.src.activity.leaf.traversal;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Sleep;
import pfnpcbuyer.src.data.NPCBuyerConfig;

public class WalkToKarim extends Leaf {

    NPCBuyerConfig nbc = NPCBuyerConfig.getInstance();

    @Override
    public boolean isValid() {
        final Area karimArea = new Area(3270, 3183, 3278, 3179);
        return Inventory.contains("Coins") && Inventory.count("Coins") > 28 && !Inventory.isFull() && !karimArea.contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        final Area karimArea = new Area(3270, 3183, 3278, 3179);
        if (Walking.shouldWalk()) {
            if (Walking.walk(karimArea.getCenter())) {
                nbc.setStatus("Walking to Karim");
                Sleep.sleepUntil(() -> karimArea.contains(Players.getLocal()), 4000, 50);
            }
        }
        return 600;
    }
}
