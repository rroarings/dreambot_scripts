package pfchaosdruids.src.activity.combat.leaf;

import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.NPC;

public class AttackDruidLeaf extends Leaf {

    Area druidArea = new Area(new Tile(2560, 3358, 0), new Tile(2564, 3354, 0));
    Condition attacking = () -> Players.getLocal().isInCombat();

    @Override
    public boolean isValid() {
        return druidArea.contains(Players.getLocal()) && !Players.getLocal().isInCombat();
    }

    @Override
    public int onLoop() {
        NPC cdruid = NPCs.closest(npc ->  npc.getName().contains("Chaos druid") && npc.canAttack() && !npc.isHealthBarVisible());

        if (cdruid != null) {
            if (!Players.getLocal().isInCombat()) {
                if (cdruid.interact("Attack")) {
                    Sleep.sleepUntil(attacking, 6000, 50);
                }
            }
        }
        return 600;
    }
}
