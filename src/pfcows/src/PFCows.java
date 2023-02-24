package pfcows.src;

import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.SkillTracker;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.frameworks.treebranch.TreeScript;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.Model;
import pfcows.src.action.branch.combat.EatFood;
import pfcows.src.action.branch.combat.EnterCombat;
import pfcows.src.action.leaf.combat.AttackCow;
import pfcows.src.data.CowsConfig;
import pfcows.src.util.PaintUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ScriptManifest(category = Category.COMBAT, name = "PF AIO Cows", author = "pharaoh", version = 1.1)
public class PFCows extends TreeScript {

    CowsConfig cowsConfig = CowsConfig.getInstance();

    private boolean hidePaint = false;
    private final Rectangle PAINT_BUTTON = new Rectangle(475, 458, 40, 18);
    private final RenderingHints aa = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    private long startTime;
    private int cowhides = 0;
    private Timer timer;

    // Define the bounds and position of the progress bar
    private static final Rectangle PROGRESS_BAR_BOUNDS = new Rectangle(10, 10, 150, 15);

    @Override
    public void onStart() {
        startTime = System.currentTimeMillis();
        //addBranches(new EnterCombat().addLeaves(new EatFood(), new AttackCow()));
        SkillTracker.start(Skill.ATTACK, Skill.HITPOINTS, Skill.STRENGTH, Skill.DEFENCE, Skill.RANGED, Skill.MAGIC);

    }


    // todo update paint
    @Override
    public void onPaint(Graphics g) {
        long gainedXPATK = SkillTracker.getGainedExperience(Skill.ATTACK);
        long gainedXPSTR = SkillTracker.getGainedExperience(Skill.STRENGTH);
        long gainedXPDEF = SkillTracker.getGainedExperience(Skill.DEFENCE);
        long gainedXPHP = SkillTracker.getGainedExperience(Skill.HITPOINTS);
        long runTime = System.currentTimeMillis() - startTime;
        int alpha = 127;
        int x = 12;
        int col2x = 190;
        int col3x = 340;

        Graphics2D graphics2D = (Graphics2D) g;
        Color opaqueBlack = new Color(0, 0, 0, alpha);
        Color opaqueOrange = new Color(255, 165, 0, alpha);
        Color opaqueTeal = new Color(42, 248, 121, 191);
        Color opaqueRed = new Color(248, 42, 42, 191);
        Color opaqueBlue = new Color(29, 135, 255, 191);

        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics2D.setFont(new Font("Arial", Font.PLAIN, 12));

        //if (getCurrentBranchName() != null) {
        // Paint the interacting character
        if (Players.getLocal().getInteractingCharacter() != null) {
            Model interacting = Players.getLocal().getInteractingCharacter().getModel();
            //graphics2D.drawString("Attacking " + Players.getLocal().getInteractingCharacter().getName(), x, 400);
            if (interacting != null) {
                g.setColor(opaqueTeal);
                graphics2D.drawPolygon((Polygon) interacting.getHullBounds());
                graphics2D.fillPolygon((Polygon) interacting.getHullBounds());
            } else log("no tile to paint");
        }

        // Chatbox background
        graphics2D.setColor(opaqueBlack);
        graphics2D.fillRoundRect(5, 342, 510, 134, 2, 2);

        // Transparent click box to show/hide the paint
        graphics2D.setColor(Color.ORANGE);
        graphics2D.draw(PAINT_BUTTON);

        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString("v " + getVersion(), 483, 471);
        graphics2D.drawString(getManifest().name(), 390, 471);

            /*graphics2D.drawString("Branch: " + getCurrentBranchName(), x, 360);
            graphics2D.drawString("Leaf: " + getCurrentLeafName(), x, 380);

            if (cowsConfig.getStatus() == null) {
                graphics2D.drawString("IDLE", x, 420);
            } else graphics2D.drawString("Status: " + cowsConfig.getStatus(), x, 420);

            graphics2D.drawString("v " + getVersion(), 483, 472);

            // Paint the interacting character
            if (Players.getLocal().getInteractingCharacter() != null) {
                Model interacting = Players.getLocal().getInteractingCharacter().getModel();
                graphics2D.drawString("Attacking " + Players.getLocal().getInteractingCharacter().getName(), x, 400);
                if (interacting != null) {
                    g.setColor(opaqueTeal);
                    graphics2D.drawPolygon((Polygon) interacting.getHullBounds());
                    graphics2D.fillPolygon((Polygon) interacting.getHullBounds());
                } else log("no tile to paint");
            }

            long gainedXP = SkillTracker.getGainedExperience(Skill.ATTACK);

            // Draw the progress of the bar
            g.setColor(opaqueRed);
            g.fillRect(x, 448, Math.min(Math.toIntExact(gainedXP), 50), PROGRESS_BAR_BOUNDS.height);

            // Draw the percentage as text over the progress bar
            g.setColor(Color.WHITE);
            int stringX = PROGRESS_BAR_BOUNDS.x;
            g.drawString("Attack", x, 440);
            g.drawString(String.valueOf(SkillTracker.getGainedExperience(Skill.ATTACK)), stringX + 20, 460);
            g.drawLine(x, 468, x + 200, 468);

            PaintUtils.drawText(g, "API API API API API API AP  API API API API API ", 50, 50, Color.GREEN);  // Draws "API" at position (50, 50) with green color
            String[] textList = {"First line", "Second line", "Third line"};
            PaintUtils.drawTextList(g, 100, 20, textList);  // Draws the text list with x=10 and y=20*/

        // Create a list of strings
        List<String> textList = new ArrayList<>();
        textList.add("Branch: " + getCurrentBranchName());
        textList.add("Leaf: " + getCurrentLeafName());
        if (cowsConfig.getStatus() == null) {
            textList.add("Status: Idle");
        } else if (cowsConfig.getStatus() != null) {
            textList.add("Status: " + cowsConfig.getStatus());
        }
        textList.add("Items: todo");
        textList.add("Gold: todo");

        List<String> textList2 = new ArrayList<>();
        textList2.add("Attack");
        textList2.add("");
        textList2.add("");
        textList2.add("Strength");

        List<String> textList3 = new ArrayList<>();
        textList3.add("Defence");
        textList3.add("");
        textList3.add("");
        textList3.add("Hitpoints");

        // Draw line


        g.setColor(Color.WHITE);
        // Call the custom method to draw the list of strings
        PaintUtils.drawTextList(g, x, 360, textList);
        PaintUtils.drawTextList(g, col2x, 360, textList2);
        PaintUtils.drawTextList(g, col3x, 360, textList3);

        // Draw the progress of the attack bar
        g.setColor(opaqueRed);
        int barWidth2 = Math.min(Math.toIntExact(gainedXPATK) / 10, 200);
        g.fillRect(col2x, 370, barWidth2, PROGRESS_BAR_BOUNDS.height);

        // Draw line
        g.setColor(Color.WHITE);
        g.drawLine(col2x, 390, col2x + 100, 390);

        // Draw the progress of the strength bar
        g.setColor(opaqueTeal);
        int barWidth = Math.min(Math.toIntExact(gainedXPSTR) / 10, 200);
        g.fillRect(col2x, 417, barWidth, PROGRESS_BAR_BOUNDS.height);

        // Draw line
        g.setColor(Color.WHITE);
        g.drawLine(col2x, 437, col2x + 100, 437);

        // Draw the progress of the defence bar
        g.setColor(opaqueBlue);
        int barWidth3 = Math.min(Math.toIntExact(gainedXPDEF) / 10, 200);
        g.fillRect(col3x, 370, barWidth3, PROGRESS_BAR_BOUNDS.height);

        // Draw line
        g.setColor(Color.WHITE);
        g.drawLine(col3x, 390, col3x + 100, 390);

        // Draw the progress of the defence bar
        g.setColor(Color.WHITE);
        int barWidth4 = Math.min(Math.toIntExact(gainedXPHP) / 10, 200);
        g.fillRect(col3x, 417, barWidth4, PROGRESS_BAR_BOUNDS.height);

        // Draw line
        g.setColor(Color.WHITE);
        g.drawLine(col3x, 437, col3x + 100, 437);


    }

}
