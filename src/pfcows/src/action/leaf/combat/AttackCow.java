package pfcows.src.action.leaf.combat;

import org.dreambot.api.input.Mouse;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.item.GroundItems;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.interactive.Player;
import org.dreambot.api.wrappers.items.GroundItem;
import org.dreambot.api.wrappers.items.Item;
import pfcows.src.data.CowsConfig;
import pfcows.src.data.Location;

public class AttackCow extends Leaf {

    @Override
    public boolean isValid() {
        Player player = Players.getLocal();
        if (Skills.getBoostedLevel(Skill.HITPOINTS) >= 5 && !player.isInCombat()) {
            return true;
        }
        return false;
    }

    @Override
    public int onLoop() {
        NPC mob = NPCs.closest(npc -> npc.getName().contains("Cow") && npc.distance() <= 10 && !npc.isInCombat() && Location.FALADOR_COWS.getArea().contains(npc));

        eat();
        Logger.log("eat 0");

        if (Dialogues.inDialogue() || Dialogues.canContinue()) {
            if (Dialogues.spaceToContinue()) {
                Mouse.move(mob);
            }
        }

        if (!Players.getLocal().isInCombat()) {
            if (Dialogues.inDialogue()) {
                Dialogues.spaceToContinue();
            }

            GroundItem phatLoot = GroundItems.closest(groundItem -> groundItem.getName().contains("Cowhide") && groundItem.isOnScreen() && groundItem.distance() <= 3);
            if (phatLoot != null && !Inventory.isFull()) {
                if (phatLoot.interact("Take")) {
                    CowsConfig.getCowsConfig().setStatus("Taking " + phatLoot);
                    Logger.log("loot interaction - take item");
                    Sleep.sleepUntil(() -> !phatLoot.exists(), 4000);
                    Logger.log("loot interaction - item picked up or stolen");
                }
                return 800;
            } else Logger.log("loot interaction fail - item gone or inventory full");

            if (mob != null && mob.interact("Attack")) {
                CowsConfig.getCowsConfig().setStatus("Attacking " + mob.getName());
                Sleep.sleepUntil(() -> phatLoot != null, 4000, 500);
                return 800;
            }
            eat();
            Logger.log("eating 1");
            return 600;
        }
        eat();
        Logger.log("eating 2");
        return 600;
    }

    private void eat() {
        Item i = Inventory.get(item -> item.hasAction("Eat"));
        if (Skills.getBoostedLevel(Skill.HITPOINTS) <= 4) {
            if (i != null) {
                if (Inventory.contains(i)) {
                    if (Inventory.interact(i)) {
                        CowsConfig.getCowsConfig().setStatus("Eating " + i.getName());
                        Logger.log("eating");
                        Sleep.sleepTicks(1);
                    }
                }
            }
        }
    }
}