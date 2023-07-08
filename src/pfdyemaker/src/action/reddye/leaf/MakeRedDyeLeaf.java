package pfdyemaker.src.action.reddye.leaf;

import org.dreambot.api.input.Mouse;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.ScriptManager;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.items.Item;
import pfdyemaker.src.data.DyeMakerConfig;

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
        Item redberry = Inventory.get(item -> item.getName().equals("Redberries"));

        if (Dialogues.inDialogue()) {
            config.setStatus("In dialogue");
            Dialogues.spaceToContinue();
            return 600;
        }

        if (config.AGGIES_HOUSE.contains(Players.getLocal())) {
            if (!Dialogues.inDialogue()) {
                if (Inventory.interact(config.REDBERRIES, "Use")) {
                    config.setStatus("Selecting item");
                    Sleep.sleepUntil(Inventory::isItemSelected, 4000, 800);
                }
                if (Inventory.isItemSelected()) {
                    if (AGGIE.interact("Use")) {
                        Sleep.sleepUntil(Dialogues::inDialogue, 4000, 200);
                    }
                }
            }

            if (!redberry.isValid()) return 600;
            if (Inventory.contains(redberry)) {
                Item[] redberryItems = Inventory.all(item -> item.getName().equals("Redberries") && isValid()).toArray(new Item[0]);
                for (int i = 0; i < redberryItems.length; i++) {
                    if ((i + 1) % 4 == 0) {
                        Mouse.move(redberryItems[i].getDestination());
                        break;
                    }
                }
            }
        }
        return 1000;
    }
}
