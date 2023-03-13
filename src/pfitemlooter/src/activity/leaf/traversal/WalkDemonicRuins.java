package pfitemlooter.src.activity.leaf.traversal;

import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import pfitemlooter.src.data.ItemLooterConfig;

public class WalkDemonicRuins extends Leaf {

    ItemLooterConfig c = ItemLooterConfig.getInstance();

    @Override
    public boolean isValid() {
        Area area = new Area(3276, 3894, 3297, 3877);
        return !area.contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        Area area = new Area(3276, 3894, 3297, 3877);
        if (!area.contains(Players.getLocal())) {
            if (Walking.shouldWalk()) {
                if (Walking.walk(area.getCenter())) {
                    c.setStatus("Walking to Demonic Ruins");
                    Walking.setObstacleSleeping(true);
                    //Walking.toggleRun();
                }
            }
        }
        return 600;
    }
}
