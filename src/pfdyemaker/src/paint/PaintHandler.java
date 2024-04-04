package pfdyemaker.src.paint;

import org.dreambot.api.utilities.Timer;
import pfdyemaker.src.PFDyeMaker;
import pfdyemaker.src.data.DyeMakerConfig;
import pfdyemaker.src.util.PricedItem;
import pfdyemaker.src.util.QuantityFormatter;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class PaintHandler implements MouseListener {

    private final PFDyeMaker script;
    private boolean hidePaint = false;
    private static final int X_OFFSET = 12;
    private static final Rectangle PAINT_BUTTON = new Rectangle(430, 458, 80, 17);
    private static final Color TRANSPARENT_BLACK = new Color(0, 0, 0, 127);

    public PaintHandler(PFDyeMaker script) {
        this.script = script;
    }

    public void paint(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;

        PaintUtils.setAntiAliasingEnabled(g, true);
        graphics2D.setFont(new Font("Arial", Font.PLAIN, 12));
        graphics2D.setColor(Color.WHITE);
        drawChatBoxBackground(graphics2D);
        PaintUtils.drawText(g, Color.WHITE, script.getManifest().name() + " v" + script.getVersion(), PAINT_BUTTON.x - 115, PAINT_BUTTON.y + 13);
        drawHidePaintButton(graphics2D);

        if (!hidePaint) {
            drawMainInfo(graphics2D);
        } else { graphics2D.drawString("Show Paint", PAINT_BUTTON.x + 10, PAINT_BUTTON.y + 13); }
    }

    private void drawChatBoxBackground(Graphics2D g2d) {
        g2d.setColor(TRANSPARENT_BLACK);
        g2d.fillRoundRect(5, 342, 510, 134, 2, 2);
    }

    private void drawHidePaintButton(Graphics2D g2d) {
        g2d.setColor(Color.YELLOW);
        g2d.draw(PAINT_BUTTON);
    }

    private void drawDebugPaintButton(Graphics2D g2d) {
        if (debugPaint) {
            g2d.setColor(PaintUtils.BLUE_LIGHT);
            g2d.fill(DEBUG_PAINT_BUTTON);
            g2d.setColor(Color.BLACK);
            g2d.drawString("Show Debug", DEBUG_PAINT_BUTTON.x + 5, DEBUG_PAINT_BUTTON.y + 13);
        } else {
            g2d.setColor(Color.WHITE);
            g2d.drawString("Hide Debug", DEBUG_PAINT_BUTTON.x + 7, DEBUG_PAINT_BUTTON.y + 13);
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

        // Draw the text list on the graphics context
        PaintUtils.drawTextList(g2d, X_OFFSET, 360, textList);
        g2d.drawString("Hide Paint", PAINT_BUTTON.x + 12, PAINT_BUTTON.y + 13);
    }

    private void drawDebugInfo(Graphics2D g2d) {
        g2d.setColor(Color.YELLOW);
        java.util.List<String> textList = new ArrayList<>();
        textList.add("Debug Paint Active");
        textList.add("Branch: " + script.getCurrentBranchName());
        textList.add("Leaf: " + script.getCurrentLeafName());
        textList.add("Status: " + (DyeMakerConfig.getDyeMakerConfig().getStatus() == null ? "Idle" : DyeMakerConfig.getDyeMakerConfig().getStatus()));

        // Draw the text list on the graphics context
        PaintUtils.drawTextList(g2d, X_OFFSET, 360, textList);
        g2d.drawString("Paint hidden", PAINT_BUTTON.x + 6, PAINT_BUTTON.y + 13);
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
