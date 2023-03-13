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

public class PurchaseKebab extends Leaf {

    NPCBuyerConfig nbc = NPCBuyerConfig.getInstance();

    @Override
    public boolean isValid() {
        return Inventory.contains("Coins") && Inventory.count("Coins") > 28 && !Inventory.isFull();
    }

    @Override
    public int onLoop() {
        NPC karim = NPCs.closest("Karim");

        // Talk to Karim and buy kebabs
        if (karim != null) {
            if (!Dialogues.inDialogue()) {
                if (karim.interact("Talk-to")) {
                    nbc.setStatus("Talking to Karim");
                    sleepUntil(Dialogues::canContinue, Calculations.random(4000, 6000));
                }
            }
        }

        if (Dialogues.inDialogue()) {
            nbc.setStatus("Talking to Karim");
            //Logger.log("In dialogue boi");
            if (Dialogues.canContinue()){
                Dialogues.continueDialogue();
                return(Calculations.random(200, 500));
            }
            if (Dialogues.getOptions() != null) {
                Dialogues.typeOption(2);
                nbc.getPricedItem().update();
                return(Calculations.random(100, 400));
            }
        }
        else {
            Logger.log("Not in dialog");
        }
        return 600;
    }
}


