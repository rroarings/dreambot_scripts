package pfdyemaker.src.action.bluedye.leaf;

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

public class MakeBlueDyeLeaf extends Leaf {

    @Override
    public boolean isValid() {
        return Inventory.contains("Coins")
                && Inventory.count("Coins") > 100
                && Inventory.contains(DyeMakerConfig.dyeConfig().getWoadLeaves())
                && Inventory.count(DyeMakerConfig.dyeConfig().getWoadLeaves()) >= 2
                && !Inventory.isFull()
                && DyeMakerConfig.dyeConfig().getAggiesHouse().contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        NPC aggie = NPCs.closest(npc -> npc.getName().equals("Aggie") && npc.isClickable());

        if (Dialogues.inDialogue()) {
            DyeMakerConfig.dyeConfig().setStatus("Skipping dialogue");
            Dialogues.spaceToContinue();
            return 600;
        }

        if (DyeMakerConfig.dyeConfig().getRedberryArea().contains(Players.getLocal())) {
            if (!Dialogues.inDialogue()) {
                if (Inventory.interact(DyeMakerConfig.dyeConfig().getWoadLeaves(), "Use")) {
                    DyeMakerConfig.dyeConfig().setStatus("Selecting item");
                    Sleep.sleepUntil(Inventory::isItemSelected, 4000, 800);
                }
                if (Inventory.isItemSelected()) {
                    if (aggie.interact("Use")) {
                        Sleep.sleepUntil(Dialogues::inDialogue, 4000, 200);
                    }
                }
            }
        }

        if (Dialogues.inDialogue() && Inventory.contains(DyeMakerConfig.dyeConfig().getWoadLeaves())) {
            Item woadLeaves = Inventory.get(item -> item.getName().equals(DyeMakerConfig.dyeConfig().getWoadLeaves()) && isValid());
            Mouse.move(woadLeaves.getDestination());
        }
        return 1000;
    }
}
