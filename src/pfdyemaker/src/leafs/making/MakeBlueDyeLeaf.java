package pfdyemaker.src.leafs.making;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.ScriptManager;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.NPC;
import pfdyemaker.src.data.DyeMakerConfig;
import pfdyemaker.src.framework.Leaf;

public class MakeBlueDyeLeaf extends Leaf {

    DyeMakerConfig config = DyeMakerConfig.getDyeMakerConfig();

    @Override
    public boolean isValid() {
        return Inventory.contains("Coins")
                && Inventory.count("Coins") > 100
                && Inventory.contains(config.WOAD_LEAVES)
                && Inventory.count(config.WOAD_LEAVES) >= 2
                && !Inventory.isFull()
                && config.AGGIES_HOUSE.contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        NPC AGGIE = NPCs.closest(npc -> npc.getName().equals("Aggie") && npc.isClickable());
        if (config.AGGIES_HOUSE.contains(Players.getLocal())) {
            if (!Dialogues.inDialogue()) {
                if (Inventory.interact(config.WOAD_LEAVES, "Use")) {
                    DyeMakerConfig.status = "Selecting item";
                    Sleep.sleepUntil(Inventory::isItemSelected, 2000);
                }
                if (Inventory.isItemSelected()) {
                    DyeMakerConfig.status = "Interact with Aggie";
                    if (Inventory.get(config.WOAD_LEAVES).useOn(AGGIE)) {
                        Sleep.sleepUntil(Dialogues::inDialogue, 2000);
                    }
                }
            }

            if (Dialogues.inDialogue()) {
                DyeMakerConfig.status = "Dialogue skip";
                Dialogues.spaceToContinue();
                return 600;
            }
        }

        //todo update block
        if (Inventory.count("Woad leaf") == 0) {
            Walking.walk(BankLocation.getNearest());
            Sleep.sleepUntil(() -> BankLocation.getNearest().getArea(1).contains(Players.getLocal()), 4000);
            Logger.log("Exiting script - Out of woad leafs");
            ScriptManager.getScriptManager().stop();
        }
        return 1000;
    }
}
