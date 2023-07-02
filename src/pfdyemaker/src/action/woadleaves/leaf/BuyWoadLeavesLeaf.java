package pfdyemaker.src.action.woadleaves.leaf;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.NPC;
import pfdyemaker.src.data.DyeMakerConfig;

public class BuyWoadLeavesLeaf extends Leaf {

    DyeMakerConfig config = DyeMakerConfig.getDyeMakerConfig();

    @Override
    public boolean isValid() {
        return Inventory.contains("Coins")
                && Inventory.count("Coins") >= 20
                && !Inventory.isFull();
    }

    @Override
    public int onLoop() {
        NPC WYSON = NPCs.closest(npc -> npc.getName().contains("Wyson the gardener") && npc.isClickable());
        final String[] DIALOGUE_OPTIONS = {"Yes please, I need woad leaves.", "How about 20 coins?"};

        if (!Dialogues.inDialogue()) {
            if (WYSON != null) {
                if (WYSON.interact("Talk-to")) {
                    config.setStatus("Talking to Wyson");
                    Sleep.sleepUntil(Dialogues::inDialogue, 6000, 100);
                }
            }

        }

        if (Dialogues.inDialogue()) {
            if (Dialogues.canContinue()) {
                Dialogues.spaceToContinue();
            }

            if (Dialogues.getOptions() != null) {
                Dialogues.chooseFirstOptionContaining(DIALOGUE_OPTIONS);
            }
        }

        config.pricedItem.update();
        return 300;
    }
}
