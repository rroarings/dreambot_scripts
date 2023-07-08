package pfdyemaker.src.action.bluedye.leaf;

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

        if (Dialogues.inDialogue()) {
            config.setStatus("Skipping dialogue");
            Dialogues.spaceToContinue();
            return 600;
        }

        if (config.AGGIES_HOUSE.contains(Players.getLocal())) {
            if (!Dialogues.inDialogue()) {
                if (Inventory.interact(config.WOAD_LEAVES, "Use")) {
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

        if (Dialogues.inDialogue() && Inventory.contains(config.WOAD_LEAVES)) {
            Item woad_leaves = Inventory.get(item -> item.getName().equals(config.WOAD_LEAVES) && isValid());
            Mouse.move(woad_leaves.getDestination());
        }
        return 1000;
    }
}
