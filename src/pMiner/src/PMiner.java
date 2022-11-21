package pMiner.src;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.SkillTracker;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.listener.ChatListener;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.widgets.message.Message;

import java.awt.*;

@ScriptManifest(name = "ph IronMiner", description = "Mines at the Isle of Souls", author = "ovlack",
        version = 1.4, category = Category.MINING, image = "")
public class PMiner extends AbstractScript implements ChatListener {

    public static String status = "";

    private final Area soulsBankArea = new Area(2210, 2860, 2214, 2857);
    private Area area = new Area(2199, 2787, 2183, 2802);
    private int iron_ore = 11364;
    private int oreMined;

    @Override
    public void onStart() {
        // Start DreamBot's skill tracker for the mining skill, so we can later see how much experience we've gained
        SkillTracker.start(Skill.MINING);

    }

    @Override
    public int onLoop() {
        if (!Inventory.isFull()) {
            if (area.contains(Players.getLocal())) {
                chopTree();
            } else {
                if (Walking.walk(area.getRandomTile())) {
                    //log("[info] walking to mine");
                    PMiner.status = "walking to mine";
                    Sleep.sleepUntil(() -> area.contains(Players.getLocal()), 5000);
                }
            }
        }

        if (Inventory.isFull()) {
            if (soulsBankArea.contains(Players.getLocal())) {
                bank();
            } else {
                if (Walking.walk(soulsBankArea.getRandomTile())) {
                    PMiner.status = "walking to bank";
                    //log("[info] walking to bank");
                    Sleep.sleepUntil(() -> soulsBankArea.contains(Players.getLocal()), 5000);
                }
            }
        }
        return 600;
    }

    @Override
    public void onPaint(Graphics g) {
        String experienceGainedText = String.format(
                "Mining Experience: %d (%d per hour)", // The paint's text format. '%d' will be replaced with the next two arguments.
                SkillTracker.getGainedExperience(Skill.MINING),
                SkillTracker.getGainedExperiencePerHour(Skill.MINING)
        );

        // Now we'll draw the text on the canvas at (5, 35). (0, 0) is the top left of the canvas.
        g.drawString(experienceGainedText, 10, 35);
        g.drawString(status, 10, 55);
        g.drawString("ores: " + String.valueOf(oreMined), 10, 75);
        g.drawString("money: " + oreMined * 155, 10, 95);
    }

    @Override
    public void onMessage(Message message) {
        if (message.getMessage().contains("You manage to mine some iron")) {
            oreMined++;
        }
    }

    private void chopTree() {
        GameObject tree = getClosestRock();
        if (tree != null && tree.interact("Mine")) {
            int countLog = Inventory.count("Iron ore");
            Sleep.sleepUntil(() -> Inventory.count("Iron ore") > countLog, 12000);
        }
    }

    private void bank() {
        if (soulsBankArea.contains(Players.getLocal())) {
            if (!Bank.isOpen()) {
                Bank.open();
                log("[info] open bank");
                PMiner.status = "open bank";
                Sleep.sleepUntil(Bank::isOpen, 2500);
            }
        }

        if (Bank.isOpen()) {
            Bank.depositAllExcept(item -> item.getName().contains(" pickaxe"));
            log("[info] deposit items");
            PMiner.status = "deposit items";
            Sleep.sleepUntil(() -> !Inventory.contains(" ore"), 2000);
            Bank.close();
        }
    }

    private GameObject getClosestRock() {
        return GameObjects.closest(object -> object.getID() == iron_ore && object.hasAction("Mine") && object.getModelColors() != null);
    }
}
