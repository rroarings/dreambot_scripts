package pfdyemaker.src.paint;

import org.dreambot.api.utilities.Timer;
import pfdyemaker.src.PFDyeMaker;
import pfdyemaker.src.data.DyeMakerConfig;
import pfdyemaker.src.util.QuantityFormatter;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class PaintHandler implements MouseListener {

    private final PFDyeMaker script;
    private boolean hidePaint = false;
    private boolean debugPaint = false;
    private static final int X_OFFSET = 12;
    private static final Rectangle PAINT_BUTTON = new Rectangle(320, 458, 80, 15);
    private static final Rectangle DEBUG_PAINT_BUTTON = new Rectangle(PAINT_BUTTON.x + 100, 458, 80, 15);
    private static final Color TRANSPARENT_BLACK = new Color(0, 0, 0, 172);

    public PaintHandler(PFDyeMaker script) {
        this.script = script;
    }

    public void paint(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        PaintUtils.setAntiAliasingEnabled(g, true);
        graphics2D.setColor(PaintUtils.OFF_WHITE);
        graphics2D.setFont(new Font("Arial", Font.PLAIN, 12));
        drawHidePaintButton(graphics2D);

        if (!hidePaint) {
            drawChatBoxBackground(graphics2D);

            if (!debugPaint) {
                drawMainPaint(graphics2D);
                drawScriptTitle(graphics2D);
            } else {
                drawDebugPaint(graphics2D);
            }
        }
        drawHidePaintButton(graphics2D);
        drawDebugPaintButton(graphics2D);
    }

    private void drawChatBoxBackground(Graphics2D g2d) {
        g2d.setColor(TRANSPARENT_BLACK);
        g2d.fillRoundRect(5, 342, 506, 134, 2, 2);
        g2d.setColor(PaintUtils.YELLOW_LIGHT);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawRoundRect(5, 342, 506, 134, 2, 2);
    }

    private void drawScriptTitle(Graphics2D g2d) {
        if (!hidePaint) {
            g2d.setColor(new Color(66, 66, 66, 198));
            g2d.fillRect(6, 459, 503, 17);
            PaintUtils.drawText(g2d, PaintUtils.OFF_WHITE, script.getManifest().name() + " v"+script.getManifest().version(), X_OFFSET, 470);
        }
    }

    private void drawHidePaintButton(Graphics2D g2d) {
        if (hidePaint) {
            g2d.setColor(Color.YELLOW);
            g2d.fill(PAINT_BUTTON);
            g2d.setColor(Color.BLACK);
            g2d.drawString("Show Paint", PAINT_BUTTON.x + 9, PAINT_BUTTON.y + 13);
        } else {
            g2d.setColor(PaintUtils.OFF_WHITE);
            g2d.drawString("Hide Paint", PAINT_BUTTON.x + 11, PAINT_BUTTON.y + 13);
        }
        g2d.setColor(Color.YELLOW);
        g2d.setStroke(new BasicStroke(1));
        g2d.draw(PAINT_BUTTON);
    }

    private void drawDebugPaintButton(Graphics2D g2d) {
        if (debugPaint) {
            g2d.setColor(PaintUtils.BLUE_LIGHT);
            g2d.fill(DEBUG_PAINT_BUTTON);
            g2d.setColor(Color.BLACK);
            g2d.drawString("Hide Debug", DEBUG_PAINT_BUTTON.x + 8, DEBUG_PAINT_BUTTON.y + 13);
        } else {
            g2d.setColor(Color.WHITE);
            g2d.drawString("Show Debug", DEBUG_PAINT_BUTTON.x + 5, DEBUG_PAINT_BUTTON.y + 13);
        }
        g2d.setColor(Color.YELLOW);
        g2d.setStroke(new BasicStroke(1));
        g2d.draw(DEBUG_PAINT_BUTTON);
    }

    private void drawMainPaint(Graphics2D g2d) {
        java.util.List<String> textList = new ArrayList<>();
        textList.add(Timer.formatTime(System.currentTimeMillis() - script.getStartTime()));
        textList.add("Status: " + (DyeMakerConfig.getDyeMakerConfig().getStatus() == null ? "Idle" : DyeMakerConfig.getDyeMakerConfig().getStatus()));
        if (DyeMakerConfig.getDyeMakerConfig().getPricedItem() != null) {
            int profit = DyeMakerConfig.getDyeMakerConfig().getPricedItem().getAmount() * DyeMakerConfig.getDyeMakerConfig().getPricedItem().getPrice();
            DyeMakerConfig.getDyeMakerConfig().setProfit(profit);
            textList.add("Item: " + DyeMakerConfig.getDyeMakerConfig().getPricedItem().getName() + ", " + DyeMakerConfig.getDyeMakerConfig().getPricedItem().getAmount());
            textList.add("Gold: " + QuantityFormatter.formatNumber(DyeMakerConfig.getDyeMakerConfig().getProfit()));
        }
        PaintUtils.drawTextList(g2d, X_OFFSET, 360, textList);
    }

    private void drawDebugPaint(Graphics2D g2d) {
        g2d.setColor(Color.YELLOW);
        java.util.List<String> textList = new ArrayList<>();
        textList.add("Debug Paint Active");
        textList.add(Timer.formatTime(System.currentTimeMillis() - script.getStartTime()));
        textList.add("Branch: " + script.getCurrentBranchName());
        textList.add("Leaf: " + script.getCurrentLeafName());
        textList.add("Status: " + (DyeMakerConfig.getDyeMakerConfig().getStatus() == null ? "Idle" : DyeMakerConfig.getDyeMakerConfig().getStatus()));
        textList.add("Use Energy potions: " + DyeMakerConfig.isUseEnergyPotions());
        if (DyeMakerConfig.getDyeMakerConfig().getPricedItem() != null) {
            int profit = DyeMakerConfig.getDyeMakerConfig().getPricedItem().getAmount() * DyeMakerConfig.getDyeMakerConfig().getPricedItem().getPrice();
            DyeMakerConfig.getDyeMakerConfig().setProfit(profit);
            textList.add("Item: " + DyeMakerConfig.getDyeMakerConfig().getPricedItem().getName() + ", " + DyeMakerConfig.getDyeMakerConfig().getPricedItem().getAmount());
            textList.add("Gold: " + QuantityFormatter.formatNumber(DyeMakerConfig.getDyeMakerConfig().getProfit()));
        }
        PaintUtils.drawTextList(g2d, X_OFFSET, 360, textList);
    }

    public void toggleDebugPaint() {
        debugPaint = !debugPaint;
        if (!debugPaint) {
            hidePaint = false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (PAINT_BUTTON.contains(e.getPoint())) {
            hidePaint = !hidePaint;
        }
        if (DEBUG_PAINT_BUTTON.contains(e.getPoint())) {
            toggleDebugPaint();
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
