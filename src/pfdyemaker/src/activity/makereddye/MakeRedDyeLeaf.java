package pfdyemaker.src.activity.makereddye;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.ScriptManager;
import org.dreambot.api.utilities.Logger;
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
        //config.getPricedItem().update();

        if (Dialogues.inDialogue()) {
            config.setStatus("Skipping dialogue");
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
        }

        if (!Inventory.contains(config.REDBERRIES)) {
            Walking.walk(BankLocation.getNearest());
            Sleep.sleepUntil(() -> BankLocation.getNearest().getArea(1).contains(Players.getLocal()), 4000, 800);
            Logger.log("Exiting script - Out of redberries");
            ScriptManager.getScriptManager().stop();
        }


        return 1000;
    }
}