package pfcows.src.action.branch.combat;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.items.Item;
import pfcows.src.data.CowsConfig;

public class EatFood extends Leaf {


    @Override
    public boolean isValid() {
        Item i = Inventory.get(item -> item.hasAction("Eat"));
        return Skills.getBoostedLevel(Skill.HITPOINTS) <= 4;
    }


    @Override
    public int onLoop() {
        //Logger.log("eat food task");
        Item i = Inventory.get(item -> item.hasAction("Eat"));
        if (i != null) {
            if (Inventory.contains(i) && Skills.getBoostedLevel(Skill.HITPOINTS) <= 4) {
                if (Inventory.interact(i)) {
                    CowsConfig.getCowsConfig().setStatus("Eating " + i.getName());
                    Sleep.sleepTicks(2);
                    return 300;
                }
            }
        }
        return 300;
    }
}
