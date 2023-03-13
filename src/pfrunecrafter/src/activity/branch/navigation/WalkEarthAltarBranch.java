package pfrunecrafter.src.activity.branch.navigation;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.equipment.Equipment;
import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.api.script.frameworks.treebranch.Branch;

public class WalkEarthAltarBranch extends Branch {

    @Override
    public boolean isValid() {
        return Equipment.getItemInSlot(EquipmentSlot.HAT).getName().contains(" tiara") && Inventory.contains("Pure essence");
    }
}
