package pfcows.src.paint;

import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.SkillTracker;
import org.dreambot.api.utilities.Timer;
import pfcows.src.PFCows;
import pfcows.src.data.CowsConfig;
import pfcows.src.util.QuantityFormatter;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class PaintHandler implements MouseListener {

    private final Rectangle PAINT_BUTTON = new Rectangle(430, 458, 80, 17);
    private final PFCows script;
    private int startingAttackExp;
    private int startingStrengthExp;
    private boolean hidePaint = false;

    private static final int MAX_PROGRESS_BAR_WIDTH = 100; // You can adjust this value as needed
    private static final int X_OFFSET = 12;
    private static final int X_OFFSET_2X = 230;
    private static final int X_OFFSET_3X = 370;
    private static final int Y_OFFSET = 360;

    private static final Color TRANSPARENT_BLACK = new Color(0, 0, 0, 127);
    private static final Color OPAQUE_TEAL = new Color(42, 248, 121, 191);
    private static final Color OPAQUE_RED = new Color(248, 42, 42, 191);
    private static final Color OPAQUE_BLUE = new Color(29, 135, 255, 191);

    public PaintHandler(PFCows script) {
        this.script = script;
        this.startingAttackExp = script.getStartingAttackExp();
        this.startingStrengthExp = script.getStartingStrengthExp();
    }

    public void paint(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;

        PaintUtils.setAntiAliasingEnabled(g, true);
        graphics2D.setFont(new Font("Arial", Font.PLAIN, 12));
        graphics2D.setColor(Color.WHITE);
        drawChatBoxBackground(graphics2D);
        PaintUtils.drawText(g, Color.WHITE, script.getManifest().name() + " v" + script.getVersion(), PAINT_BUTTON.x - 115, PAINT_BUTTON.y + 13);
        drawTransparentButton(graphics2D);

        if (!hidePaint) {
            drawMainInfo(graphics2D);
        } else {
            graphics2D.drawString("Show Paint", PAINT_BUTTON.x + 10, PAINT_BUTTON.y + 13);
            drawDebugInfo(graphics2D);
        }
    }


    private void drawInteractingModel(Graphics2D g2d) {
        // Draw interacting model here...
    }

    private void drawChatBoxBackground(Graphics2D g2d) {
        g2d.setColor(TRANSPARENT_BLACK);
        g2d.fillRoundRect(5, 342, 510, 134, 2, 2);
    }

    private void drawTransparentButton(Graphics2D g2d) {
        g2d.setColor(Color.YELLOW);
        g2d.draw(PAINT_BUTTON);
    }

    private void drawMainInfo(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        List<String> textList = new ArrayList<>();
        textList.add(Timer.formatTime(System.currentTimeMillis() - script.getStartTime()));
        textList.add("Status: " + (CowsConfig.getCowsConfig().getStatus() == null ? "Idle" : CowsConfig.getCowsConfig().getStatus()));
        if (CowsConfig.getCowsConfig().getPricedItem() != null) {
            int profit = CowsConfig.getCowsConfig().getPricedItem().getAmount() * CowsConfig.getCowsConfig().getPricedItem().getPrice();
            CowsConfig.getCowsConfig().setProfit(profit);
            textList.add(CowsConfig.getCowsConfig().getPricedItem().getName() + " ["+ CowsConfig.getCowsConfig().getPricedItem().getPrice() + "] - " + CowsConfig.getCowsConfig().getPricedItem().getAmount());
            textList.add("Gold: " + QuantityFormatter.formatNumber(CowsConfig.getCowsConfig().getProfit()));
        }

        // Draw the text list on the graphics context
        PaintUtils.drawTextList(g2d, X_OFFSET, 360, textList);
        g2d.drawString("Hide Paint", PAINT_BUTTON.x + 12, PAINT_BUTTON.y + 13);
        drawSkillsColumn1(g2d);
        drawSkillsColumn2(g2d);
    }

    private void drawDebugInfo(Graphics2D g2d) {
        g2d.setColor(Color.YELLOW);
        List<String> textList = new ArrayList<>();
        textList.add("Debug Paint Active");
        textList.add("Branch: " + script.getCurrentBranchName());
        textList.add("Leaf: " + script.getCurrentLeafName());
        textList.add("Status: " + (CowsConfig.getCowsConfig().getStatus() == null ? "Idle" : CowsConfig.getCowsConfig().getStatus()));

        // Draw the text list on the graphics context
        PaintUtils.drawTextList(g2d, X_OFFSET, 360, textList);
        g2d.drawString("Paint hidden", PAINT_BUTTON.x + 6, PAINT_BUTTON.y + 13);
    }

    // HP + ATK
    private void drawSkillsColumn1(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);

        int progressBarHeight = 8; // Set the height of the progress bar

        long maxAttackExp = SkillTracker.getGainedExperience(Skill.HITPOINTS) - startingAttackExp;
        long maxStrengthExp = SkillTracker.getGainedExperience(Skill.ATTACK) - startingStrengthExp;

        int attackProgressBarWidth = (int) (Math.min(maxAttackExp, MAX_PROGRESS_BAR_WIDTH) * 1.0) / 75;
        int strengthProgressBarWidth = (int) (Math.min(maxStrengthExp, MAX_PROGRESS_BAR_WIDTH) * 1.0 / 75);

        List<String> skillList = new ArrayList<>();
        skillList.add("Hitpoints" + " [+" + SkillTracker.getGainedLevels(Skill.HITPOINTS) + "]");
        skillList.add(QuantityFormatter.formatNumber(SkillTracker.getGainedExperience(Skill.HITPOINTS)) + " [" + QuantityFormatter.formatNumber(SkillTracker.getGainedExperiencePerHour(Skill.HITPOINTS)) + "]");

        if (maxAttackExp > 0) {
            drawProgressBarInList(g2d, X_OFFSET_2X, Y_OFFSET - 5, attackProgressBarWidth, progressBarHeight, skillList, 2);
        } else {
            skillList.add(""); // Leave a blank line if there's no experience gained
        }

        skillList.add(""); // Blank line to maintain alignment

        skillList.add("Attack" + " [+" + SkillTracker.getGainedLevels(Skill.ATTACK) + "]");
        skillList.add(QuantityFormatter.formatNumber(SkillTracker.getGainedExperience(Skill.ATTACK)) + " [" + QuantityFormatter.formatNumber(SkillTracker.getGainedExperiencePerHour(Skill.ATTACK)) + "]");

        if (maxStrengthExp > 0) {
            drawProgressBarInList(g2d, X_OFFSET_2X, Y_OFFSET - 5, strengthProgressBarWidth, progressBarHeight, skillList, 6);
        } else {
            skillList.add(""); // Leave a blank line if there's no experience gained
        }

        PaintUtils.drawTextList(g2d, X_OFFSET_2X, Y_OFFSET, skillList);
    }

    // STR + DEF
    private void drawSkillsColumn2(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);

        int progressBarHeight = 8; // Set the height of the progress bar

        long maxAttackExp = SkillTracker.getGainedExperience(Skill.STRENGTH) - startingAttackExp;
        long maxStrengthExp = SkillTracker.getGainedExperience(Skill.DEFENCE) - startingStrengthExp;

        int attackProgressBarWidth = (int) (Math.min(maxAttackExp, MAX_PROGRESS_BAR_WIDTH) * 1.0) / 75;
        int strengthProgressBarWidth = (int) (Math.min(maxStrengthExp, MAX_PROGRESS_BAR_WIDTH) * 1.0 / 75);

        List<String> skillList = new ArrayList<>();
        skillList.add("Strength" + " [+" + SkillTracker.getGainedLevels(Skill.STRENGTH) + "]");
        skillList.add(QuantityFormatter.formatNumber(SkillTracker.getGainedExperience(Skill.STRENGTH)) + " [" + QuantityFormatter.formatNumber(SkillTracker.getGainedExperiencePerHour(Skill.STRENGTH)) + "]");

        if (maxAttackExp > 0) {
            drawProgressBarInList(g2d, X_OFFSET_2X, Y_OFFSET - 5, attackProgressBarWidth, progressBarHeight, skillList, 2);
        } else {
            skillList.add(""); // Leave a blank line if there's no experience gained
        }

        skillList.add(""); // Blank line to maintain alignment

        skillList.add("Defence" + " [+" + SkillTracker.getGainedLevels(Skill.DEFENCE) + "]");
        skillList.add(QuantityFormatter.formatNumber(SkillTracker.getGainedExperience(Skill.DEFENCE)) + " [" + QuantityFormatter.formatNumber(SkillTracker.getGainedExperiencePerHour(Skill.DEFENCE)) + "]");

        if (maxStrengthExp > 0) {
            drawProgressBarInList(g2d, X_OFFSET_2X, Y_OFFSET - 5, strengthProgressBarWidth, progressBarHeight, skillList, 6);
        } else {
            skillList.add(""); // Leave a blank line if there's no experience gained
        }

        PaintUtils.drawTextList(g2d, X_OFFSET_3X, Y_OFFSET, skillList);
    }

    private void drawProgressBarInList(Graphics2D g2d, int x, int y, int width, int height, List<String> list, int index) {
        int yOffset = y + index * 16;
        g2d.fillRect(x, yOffset, width, height);
        list.add(index, " "); // Add an empty string to maintain alignment
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (PAINT_BUTTON.contains(e.getPoint())) {
            hidePaint = !hidePaint;
        }
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
