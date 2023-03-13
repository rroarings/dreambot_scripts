package pfitemlooter.src.activity.leaf.looting;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.GameObject;
import pfitemlooter.src.data.ItemLooterConfig;

import java.util.Arrays;
import java.util.Random;

import static org.dreambot.api.utilities.Sleep.sleepUntil;

public class TakeCauldronItems extends Leaf {

    ItemLooterConfig c = ItemLooterConfig.getInstance();

    private static final int OPTION_INDEX = 1;
    private static final int MORE_OPTIONS_INDEX = 4;

    private boolean takeEggNext = false;

    @Override
    public boolean isValid() {
        GameObject cauldron = GameObjects.closest(go -> go.getName().equals("Spooky cauldron") && go.hasAction("Rummage"));
        return !Inventory.isFull() && cauldron != null && cauldron.distance() <= 10;
    }

    @Override
    public int onLoop() {
        GameObject cauldron = GameObjects.closest(go -> go.getName().equals("Spooky cauldron") && go.hasAction("Rummage"));

        if (cauldron != null) {
            if (!Dialogues.inDialogue()) {
                if (cauldron.interact("Rummage")) {
                    c.setStatus("Rummaging through cauldron");
                    sleepUntil(Dialogues::inDialogue, 4000, 100);
                }
            }
        }

        if (Dialogues.canContinue()) {
            c.setStatus("Continuing through dialogue");
            Dialogues.continueDialogue();
            return 100;
        }

        if (Dialogues.inDialogue() && !takeEggNext) {
            c.setStatus("Taking smelly socks");
            String[] options = Dialogues.getOptions();
            if (options != null) {
                for (String option : options) {
                    if (option.equals("Something smelly.")) {
                        Dialogues.clickOption(OPTION_INDEX);
                        sleepUntil(() -> Inventory.contains("Smelly sock"), 5000);
                        takeEggNext = true;
                        break;
                    }
                }
            }
        }

        if (Dialogues.inDialogue() && takeEggNext) {
            c.setStatus("Taking spooky eggs");
            String[] options = Dialogues.getOptions();
            if (options != null) {
                for (String option : options) {
                    if (!option.equals("Something spooky.")) {
                        Dialogues.clickOption(MORE_OPTIONS_INDEX);
                    } else {
                        Dialogues.chooseOption(OPTION_INDEX);
                        sleepUntil(() -> Inventory.contains("Spooky egg"), 5000);
                        takeEggNext = false;
                        break;
                    }
                }
            }
        }

        return 400;
    }
}

