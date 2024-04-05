package pfdyemaker.src.action.yellowdye.leaf;

import org.dreambot.api.input.Mouse;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.items.Item;
import pfdyemaker.src.data.DyeMakerConfig;

public class MakeYellowDyeLeaf extends Leaf {

    @Override
    public boolean isValid() {
        return Inventory.contains("Coins")
                && Inventory.count("Coins") > 100
                && Inventory.contains(DyeMakerConfig.dyeConfig().getOnion())
                && Inventory.count(DyeMakerConfig.dyeConfig().getOnion()) >= 2
                && DyeMakerConfig.dyeConfig().getAggiesHouse().contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        NPC aggie = NPCs.closest(npc -> npc.getName().equals("Aggie") && npc.isClickable());

        if (Dialogues.inDialogue()) {
            DyeMakerConfig.dyeConfig().setStatus("Skipping dialogue");
            Dialogues.spaceToContinue();
        }

        if (DyeMakerConfig.dyeConfig().getAggiesHouse().contains(Players.getLocal())) {
            if (!Dialogues.inDialogue()) {
                if (Inventory.interact(DyeMakerConfig.dyeConfig().getOnion(), "Use")) {
                    DyeMakerConfig.dyeConfig().setStatus("Selecting onion");
                    Sleep.sleepUntil(Inventory::isItemSelected, 4000, 600);
                }
                if (Inventory.isItemSelected()) {
                    if (aggie.interact("Use")) {
                        Sleep.sleepUntil(Dialogues::inDialogue, 4000, 600);
                    }
                }
            }
        }

        if (Dialogues.inDialogue() && Inventory.contains(DyeMakerConfig.dyeConfig().getOnion())) {
            Item[] onion = Inventory.all(item -> item.getName().equals(DyeMakerConfig.dyeConfig().getOnion()) && isValid()).toArray(new Item[0]);
            for (int i = 0; i < onion.length; i++) {
                if ((i + 1) % 3 == 0) {
                    Mouse.move(onion[i].getDestination());
                    break;
                }
            }
        }
        return 600;
    }
}
