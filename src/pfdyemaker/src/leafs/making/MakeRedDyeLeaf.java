package pfdyemaker.src.leafs.making;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.NPC;
import pfdyemaker.src.data.DyeMakerConfig;
import pfdyemaker.src.framework.Leaf;


public class MakeRedDyeLeaf extends Leaf {

    DyeMakerConfig config = DyeMakerConfig.getDyeMakerConfig();

    @Override
    public boolean isValid() {
        return Inventory.contains("Coins")
                && Inventory.count("Coins") > 100
                && Inventory.contains(config.REDBERRIES)
                && Inventory.count(config.REDBERRIES) >= 3
                && config.AGGIES_HOUSE.contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        NPC AGGIE = NPCs.closest(npc -> npc.getName().equals("Aggie") && npc.isClickable());
        if (config.AGGIES_HOUSE.contains(Players.getLocal())) {
            if (!Dialogues.inDialogue()) {
                if (Inventory.interact(config.REDBERRIES, "Use")) {
                    DyeMakerConfig.status = "Selecting item";
                    Sleep.sleepUntil(() -> Inventory.isItemSelected(), 2000);
                }
                if (Inventory.isItemSelected()) {
                    DyeMakerConfig.status = "Interact with Aggie";
                    if (Inventory.get(config.REDBERRIES).useOn(AGGIE)) {
                        Sleep.sleepUntil(() -> Dialogues.inDialogue(), 2000);
                    }
                }
            }
            if (Dialogues.inDialogue()) {
                DyeMakerConfig.status = "Dialogue skips";
                Dialogues.spaceToContinue();
                return 600;
            }
        }
        return 1000;
    }
}
