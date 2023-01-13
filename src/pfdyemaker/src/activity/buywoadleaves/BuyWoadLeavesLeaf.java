package pfdyemaker.src.activity.buywoadleaves;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.NPC;
import pfdyemaker.src.data.DyeMakerConfig;
import pfdyemaker.src.framework.Leaf;

public class BuyWoadLeavesLeaf extends Leaf {

    DyeMakerConfig config = DyeMakerConfig.getDyeMakerConfig();

    @Override
    public boolean isValid() {
        return Inventory.contains("Coins")
                && Inventory.count("Coins") > 100
                && !Inventory.isFull()
                && config.WYSON_AREA.contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        NPC WYSON = NPCs.closest(npc -> npc.getName().contains("Wyson the gardener") && npc.isClickable());
        final String[] DIALOGUE_OPTIONS = {"Yes please, I need woad leaves.", "How about 20 coins?"};

        if (!Dialogues.inDialogue()) {
            if (WYSON != null) {
                if (WYSON.interact("Talk-to")) {
                    config.setStatus("Talking to Wyson");
                    Sleep.sleepUntil(Dialogues::inDialogue, 600, 300);
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
        return 100;
    }
}
