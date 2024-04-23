package pfdyemaker.src.action.reddye.leaf;

import org.dreambot.api.input.Mouse;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.items.Item;
import pfdyemaker.src.data.DyeMakerConfig;

public class MakeRedDyeLeaf extends Leaf {

    @Override
    public boolean isValid() {
        return Inventory.contains("Coins")
                && Inventory.count("Coins") >= 5
                && Inventory.contains(DyeMakerConfig.dyeConfig().getRedberries())
                && Inventory.count(DyeMakerConfig.dyeConfig().getRedberries()) >= 3
                && DyeMakerConfig.dyeConfig().getAggiesHouse().contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        NPC aggie = NPCs.closest(npc -> npc.getName().equals("Aggie") && npc.isClickable());
        GameObject door = GameObjects.closest(gameObject -> gameObject.getID() == 1535);

        if (DyeMakerConfig.dyeConfig().getAggiesHouse().contains(door)) {
            if (door.hasAction("Open")) {
                if (door.interact("Open")) {
                    Logger.log("(dyemaker) open Aggie's door");
                    Sleep.sleepUntil(() -> !door.hasAction("Open"), 5000, 600);
                }
            }
        }

        if (Dialogues.inDialogue()) {
            if (Dialogues.continueDialogue()) {
                DyeMakerConfig.dyeConfig().setStatus("Talking to Aggie");
            }
        }

        if (DyeMakerConfig.dyeConfig().getAggiesHouse().contains(Players.getLocal())) {
            if (!Dialogues.inDialogue()) {
                if (Inventory.interact(DyeMakerConfig.dyeConfig().getRedberries(), "Use")) {
                    DyeMakerConfig.dyeConfig().setStatus("Selecting redberry");
                    Sleep.sleepUntil(Inventory::isItemSelected, 5000, 600);
                }
                if (Inventory.isItemSelected()) {
                    if (aggie.interact("Use")) {
                        Item[] redBerries = Inventory.all(item -> item.getName().equals("Redberries")).toArray(new Item[0]);
                        int redBerryCount = 0;
                        for (Item redBerry : redBerries) {
                            redBerryCount++;
                            if (redBerryCount % 4 == 0) {
                                Mouse.move(redBerry.getDestination());
                                break;
                            }
                        }
                        DyeMakerConfig.dyeConfig().setStatus("Using redberry on Aggie");
                        Sleep.sleepUntil(Dialogues::inDialogue, 5000, 600);
                    }
                }
            }
        }
        return 600;
    }
}
