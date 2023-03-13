package pfrunecrafter.src.activity.leaf.navigation;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.equipment.Equipment;
import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Sleep;

public class WalkEarthAltarLeaf extends Leaf {

    private final Area area = new Area(3303, 3472, 3311, 3465);

    @Override
    public boolean isValid() {
        return Equipment.getItemInSlot(EquipmentSlot.HAT).getName().contains(" tiara") && Inventory.contains("Pure essence");
    }

    @Override
    public int onLoop() {
        if (!area.contains(Players.getLocal())) {
            if (Walking.shouldWalk()) {
                Walking.walk(area.getCenter());
                Sleep.sleepUntil(() -> area.contains(Players.getLocal()), 6000, 100);
            }
        }
        return 900;
    }
}
