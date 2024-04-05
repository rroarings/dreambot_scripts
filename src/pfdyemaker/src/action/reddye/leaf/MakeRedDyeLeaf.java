package pfdyemaker.src.action.reddye.leaf;

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

public class MakeRedDyeLeaf extends Leaf {

    @Override
    public boolean isValid() {
        return Inventory.contains("Coins")
                && Inventory.count("Coins") > 100
                && Inventory.contains(DyeMakerConfig.dyeConfig().getRedberries())
                && Inventory.count(DyeMakerConfig.dyeConfig().getRedberries()) >= 3
                && DyeMakerConfig.dyeConfig().getAggiesHouse().contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        NPC aggie = NPCs.closest(npc -> npc.getName().equals("Aggie") && npc.isClickable());
        Item redberry = Inventory.get(item -> item.getName().equals("Redberries"));

        if (Dialogues.inDialogue()) {
            DyeMakerConfig.dyeConfig().setStatus("In dialogue");
            Dialogues.spaceToContinue();
            return 600;
        }

        if (DyeMakerConfig.dyeConfig().getAggiesHouse().contains(Players.getLocal())) {
            if (!Dialogues.inDialogue()) {
                if (Inventory.interact(DyeMakerConfig.dyeConfig().getRedberries(), "Use")) {
                    DyeMakerConfig.dyeConfig().setStatus("Selecting item");
                    Sleep.sleepUntil(Inventory::isItemSelected, 4000, 600);
                }
                if (Inventory.isItemSelected()) {
                    if (aggie.interact("Use")) {
                        Sleep.sleepUntil(Dialogues::inDialogue, 4000, 600);
                    }
                }
            }

            if (Dialogues.inDialogue() && Inventory.contains(redberry)) {
                Item[] redberryItems = Inventory.all(item -> item.getName().equals(DyeMakerConfig.dyeConfig().getRedberries()) && isValid()).toArray(new Item[0]);
                for (int i = 0; i < redberryItems.length; i++) {
                    if ((i + 1) % 4 == 0) {
                        Mouse.move(redberryItems[i].getDestination());
                        break;
                    }
                }
            }
        }
        return 600;
    }
}
