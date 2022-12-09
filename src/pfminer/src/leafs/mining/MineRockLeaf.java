package pfminer.src.leafs.mining;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.GameObject;
import pMiner.src.PMiner;
import pMiner.src.data.Config;
import pfminer.src.data.MinerConfig;
import pfminer.src.framework.Leaf;

public class MineRockLeaf extends Leaf {

    @Override
    public boolean isValid() {
        return !Inventory.isFull() && MinerConfig.QUARRY.contains(Players.getLocal()) && !Players.getLocal().isAnimating();
    }

    @Override
    public int onLoop() {
        GameObject rock = getClosestRock();

        if (Dialogues.inDialogue()) {
            Dialogues.continueDialogue();
        }

        if (MinerConfig.QUARRY.contains(Players.getLocal()) && !Inventory.isFull()) {
            if (rock == null) return Calculations.random(500, 1000);

            if (rock.interact("Mine")) {
                MinerConfig.status = "Mining rock";
                Sleep.sleepUntil(this::isMining, 2000);
            }
        }
        return Calculations.random(600, 800);
    }

    private GameObject getClosestRock() {
        return GameObjects.closest(object -> object.getID() == 11364 && object.hasAction("Mine"));
    }

    private boolean isMining() {
        return Players.getLocal().isAnimating();
    }
}
