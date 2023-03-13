package pfnpcbuyer.src.activity.leaf.buying;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.wrappers.interactive.NPC;
import pfnpcbuyer.src.data.NPCBuyerConfig;

import static org.dreambot.api.utilities.Sleep.sleepUntil;

public class PurchaseNewspaper extends Leaf {

    NPCBuyerConfig nbc = NPCBuyerConfig.getInstance();

    @Override
    public boolean isValid() {
        return Inventory.contains("Coins") && Inventory.count("Coins") >= 1400 && !Inventory.isFull();
    }

    @Override
    public int onLoop() {
        NPC benny = NPCs.closest("Benny");

        // Talk to Karim and buy kebabs
        if (benny != null) {
            if (!Dialogues.inDialogue()) {
                if (benny.interact("Talk-to")) {
                    nbc.setStatus("Talking to Benny");
                    sleepUntil(Dialogues::canContinue, Calculations.random(4000, 6000));
                }
            }
        }

        if (Dialogues.inDialogue()) {
            nbc.setStatus("Talking to Benny");
            //Logger.log("In dialogue boi");
            if (Dialogues.canContinue()){
                Dialogues.continueDialogue();
                return 100;
            }
            if (Dialogues.getOptions() != null) {
                Dialogues.typeOption(1);
                nbc.getPricedItem().update();
                return 100;
            }
        }
        else {
            Logger.log("Not in dialog");
        }
        return 300;
    }
}
