package pMiner.src.tasks;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;
import pMiner.src.PMiner;

public class MineTask extends TaskNode {

    private Area area = new Area(2199, 2787, 2183, 2802);
    private int iron_ore = 11364;

    @Override
    public boolean accept() {
        // If our inventory isn't full and we're not mining, we should start
        return !Inventory.isFull() && !Players.getLocal().isAnimating();
    }

    @Override
    public int execute() {
        GameObject rock = getClosestRock();

        if (Dialogues.inDialogue()) {
            Dialogues.continueDialogue();
        }

        if (!area.contains(Players.getLocal())) {
            if (Walking.walk(area.getRandomTile())) {
                log("[info] walking to mine");
                PMiner.status = "walking to mine";
                Sleep.sleepUntil(() -> area.contains(Players.getLocal()), 5000);
            }
        }

        // If there aren't any available rocks near us, we should just wait until one's available
        if (area.contains(Players.getLocal()) && !Inventory.isFull()) {
            if (rock == null) return Calculations.random(500, 1000);

            if (rock.interact("Mine")) { // If we successfully click on the rock
                PMiner.status = "mining rock";
                Sleep.sleepUntil(this::isMining, 2500); // Wait until we're mining, with a max wait time of 2,500ms (2.5 seconds)

            }
        }

        return Calculations.random(500, 1000);
    }

    /**
     * Finds the closest acceptable rock that we can mine
     *
     * @return The closest GameObject that has the name 'Rocks', with the action 'Mine', and non-null model colors
     */
    private GameObject getClosestRock() {
        return GameObjects.closest(object -> object.getID() == iron_ore && object.hasAction("Mine") && object.getModelColors() != null);
    }

    /**
     * For part 1, we'll consider our player doing any animation as mining
     * This will be improved/changed in a future part
     *
     * @return true if the player is mining, otherwise false
     */
    private boolean isMining() {
        return Players.getLocal().isAnimating();
    }

}
