package pfdyemaker.src.action.yellowdye.leaf;

import org.dreambot.api.input.Mouse;
import org.dreambot.api.methods.Calculations;
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

public class MakeYellowDyeLeaf extends Leaf {

    DyeMakerConfig config = DyeMakerConfig.getDyeMakerConfig();

    @Override
    public boolean isValid() {
        return Inventory.contains("Coins")
                && Inventory.contains(config.ONION)
                && Inventory.count(config.ONION) >= 2
                && config.AGGIES_HOUSE.contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        NPC AGGIE = NPCs.closest(npc -> npc.getName().equals("Aggie") && npc.isClickable());

        if (Dialogues.inDialogue()) {
            config.setStatus("Skipping dialogue");
            Dialogues.spaceToContinue();
            return Calculations.random(0,3);
        }

        if (config.AGGIES_HOUSE.contains(Players.getLocal())) {
            if (!Dialogues.inDialogue()) {
                if (Inventory.interact(config.ONION, "Use")) {
                    config.setStatus("Selecting item");
                    Sleep.sleepUntil(Inventory::isItemSelected, 2000, 600);
                    Logger.log("(yellowdye) item selected: index " + Inventory.getSelectedItemIndex());
                }
                if (Inventory.isItemSelected()) {
                    if (AGGIE.interact("Use")) {
                        Logger.log("(yellowdye) use onion on aggie");
                        Sleep.sleepUntil(Dialogues::inDialogue, 2000, 600);
                    }
                }
            }
        }

        if (Dialogues.inDialogue() && Inventory.contains(config.ONION)) {
            Item[] onion = Inventory.all(item -> item.getName().equals(config.ONION) && isValid()).toArray(new Item[0]);
            for (int i = 0; i < onion.length; i++) {
                if ((i + 1) % 3 == 0) {
                    Mouse.move(onion[i].getDestination());
                    break;
                }
            }
            return Calculations.random(0,5);
        }
        return 800;
    }
}
