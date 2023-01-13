package pfdyemaker.src.activity.makeyellowdye;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.NPC;
import pfdyemaker.src.data.DyeMakerConfig;
import pfdyemaker.src.framework.Leaf;

public class MakeYellowDyeLeaf extends Leaf {

    DyeMakerConfig config = DyeMakerConfig.getDyeMakerConfig();

    @Override
    public boolean isValid() {
        return Inventory.contains("Coins")
                && Inventory.count("Coins") > 100
                && Inventory.contains(config.ONION)
                && Inventory.count(config.ONION) >= 2
                && config.AGGIES_HOUSE.contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        NPC AGGIE = NPCs.closest(npc -> npc.getName().equals("Aggie") && npc.isClickable());
        if (config.AGGIES_HOUSE.contains(Players.getLocal())) {
            if (!Dialogues.inDialogue()) {
                if (Inventory.interact(config.ONION, "Use")) {
                    config.setStatus("Selecting item");
                    Sleep.sleepUntil(Inventory::isItemSelected, 2000, 600);
                }
                if (Inventory.isItemSelected()) {
                    config.setStatus("Interact with Aggie");
                    if (Inventory.get(config.ONION).useOn(AGGIE)) {
                        Sleep.sleepUntil(Dialogues::inDialogue, 1000, 600);
                    }
                }
            }

            if (Dialogues.inDialogue()) {
                config.setStatus("Dialogue skip");
                Dialogues.spaceToContinue();
                return 600;
            }
        }
        return 800;
    }
}
