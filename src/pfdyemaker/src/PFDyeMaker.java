package pfdyemaker.src;

import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Timer;
import pfdyemaker.src.data.DyeMakerConfig;
import pfdyemaker.src.framework.Tree;
import pfdyemaker.src.ui.DyeMakerUI;
import pfdyemaker.src.util.API;
import pfdyemaker.src.util.QuantityFormatter;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;

@ScriptManifest(category = Category.MONEYMAKING, name = "PF Dye Maker", author = "pharaoh", version = 1.2, image = "https://i.imgur.com/zQ1Vwvt.png")
public class PFDyeMaker extends AbstractScript implements MouseListener {

    private boolean hidePaint = false;
    private final Rectangle PAINT_BUTTON = new Rectangle(475, 458, 40, 18);
    private long startTime;
    private Timer timer;
    private Image image;
    private final Tree tree = new Tree();
    private final RenderingHints aa = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    private final DyeMakerConfig config = DyeMakerConfig.getDyeMakerConfig();

    private void initTree() {
        tree.addBranches(DyeMakerUI.getSelectedItem().getActivityBranch());
    }

    @Override
    public void onStart() {
        timer = new Timer();
        SwingUtilities.invokeLater(() -> new DyeMakerUI().setVisible(true));
        startTime = System.currentTimeMillis();

        try {
            image = ImageIO.read(new URL("https://i.imgur.com/vHCUz94.png"));
        } catch (IOException e) {
            log("Failed to load image");
        }
    }

    @Override
    public int onLoop() {
        if (DyeMakerUI.isStartLoop()) {
            initTree();
        }
        if (config.getPricedItem() != null) {
            config.getPricedItem().update();
        }
        return this.tree.onLoop();
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

        if (!hidePaint) {
            if (DyeMakerUI.isStartLoop()) {
                if (API.currentLeaf != null) {
                    if (image != null) g.drawImage(image, 0, 0, null);

                    graphics2D.setColor(opaqueBlack);
                    graphics2D.fillRoundRect(5, 342, 510, 134, 2, 2);

                    // transparent click box to show/hide the paint
                    graphics2D.setColor(opaqueOrange);
                    graphics2D.draw(PAINT_BUTTON);

                    graphics2D.setColor(Color.ORANGE);
                    graphics2D.drawString(getManifest().name(), 380, 473);

                    graphics2D.setColor(Color.WHITE);
                    graphics2D.drawString("Time: " + Timer.formatTime(runTime), x, 360);
                    graphics2D.drawString("Status: " + config.getStatus(), x, 380);
                    graphics2D.drawString("v " + getVersion(), 483, 473);

                    if (config.getPricedItem() != null) {
                        String text = config.getDyeToMake() != null ? "Dyes" : "Items";
                        int itemsPH = (int) (config.getPricedItem().getAmount() / ((System.currentTimeMillis() - startTime) / 3600000.0D));

                        config.setProfit(config.getPricedItem().getAmount() * config.getPricedItem().getPrice());
                        graphics2D.drawString(text + ": " + config.getPricedItem().getAmount() + " (" + itemsPH + ")", x, 400);
                        graphics2D.drawString("Gold: " + QuantityFormatter.formatNumber(config.getProfit()) + " (" + QuantityFormatter.formatNumber(timer.getHourlyRate(config.getProfit())) + ")", x, 420);
                    }
                }
            }
        } else {
            graphics2D.setColor(Color.RED);
            graphics2D.drawString("Paint hidden", PAINT_BUTTON.x - 45, PAINT_BUTTON.y + 14);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (PAINT_BUTTON.contains(e.getPoint()))
            hidePaint = !hidePaint;
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
