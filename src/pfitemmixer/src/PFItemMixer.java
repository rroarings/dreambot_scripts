package pfitemmixer.src;

import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.SkillTracker;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.frameworks.treebranch.TreeScript;
import org.dreambot.api.utilities.Timer;
import pfitemmixer.src.activity.branch.banking.BankItemsBranch;
import pfitemmixer.src.activity.branch.mixing.MixItemBranch;
import pfitemmixer.src.activity.leaf.banking.BankItemsLeaf;
import pfitemmixer.src.activity.leaf.mixing.MixItemLeaf;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

@ScriptManifest(category = Category.MISC, name = "PF Item Mixer", author = "pharaoh", version = 1.0)
public class PFItemMixer extends TreeScript implements MouseListener {

    public static long farmingXP;
    Tile startTile;

    private boolean hidePaint = false;
    private final Rectangle PAINT_BUTTON = new Rectangle(475, 458, 40, 18);
    private final RenderingHints aa = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    private long startTime;


    @Override
    public void onStart() {
        startTime = System.currentTimeMillis();
        SkillTracker.start(Skill.FARMING);
        startTile = Players.getLocal().getTile();

        addBranches(new MixItemBranch().addLeaves(new MixItemLeaf()),
                new BankItemsBranch().addLeaves(new BankItemsLeaf()));
        log(getCurrentBranchName());
        log(getCurrentLeafName());
        log("start");

        /*String[] thievingItems = {"Lockpick", "Dodgy necklace", "Thieving cape", "Ardougne cloak", "Black Ibis outfit"};

        ArrayList<String> pickedItems = new ArrayList<String>();

        // randomly pick 3 items from the thievingItems array
        Random rand = new Random();
        for (int i = 0; i < 3; i++) {
            int randomIndex = rand.nextInt(thievingItems.length);
            String pickedItem = thievingItems[randomIndex];
            pickedItems.add(pickedItem);
        }

        log("You picked the following items:");
        for (String item : pickedItems) {
            log("- " + item);
        }*/
    }

    @Override
    public void onPaint(Graphics g) {
        long runTime = System.currentTimeMillis() - startTime;
        int alpha = 127;
        int x = 12;

        Graphics2D graphics2D = (Graphics2D) g;
        Color opaqueBlack = new Color(0, 0, 0, alpha);
        Color opaqueOrange = new Color(255, 165, 0, alpha);

        graphics2D.setRenderingHints(aa);
        graphics2D.setFont(new Font("Bahnschrift", Font.PLAIN, 14));

        if (getCurrentBranchName() != null) {

            graphics2D.setColor(opaqueBlack);
            graphics2D.fillRoundRect(5, 342, 510, 134, 2, 2);

            // transparent click box to show/hide the paint
            graphics2D.setColor(opaqueOrange);
            graphics2D.draw(PAINT_BUTTON);

            graphics2D.setColor(Color.ORANGE);
            graphics2D.drawString(getManifest().name(), PAINT_BUTTON.x - 120, 473);

            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString(getCurrentBranchName(), x, 360);
            graphics2D.drawString(getCurrentLeafName(), x, 380);
            graphics2D.drawString(Timer.formatTime(runTime), x, 400);
            graphics2D.drawString("Experience: " + SkillTracker.getGainedExperience(Skill.FARMING), x, 420);

            graphics2D.drawString("v " + getVersion(), 483, 473);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
