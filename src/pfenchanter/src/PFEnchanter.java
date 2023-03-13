package pfenchanter.src;

import org.dreambot.api.Client;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.SkillTracker;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Timer;
import pfdyemaker.src.util.QuantityFormatter;
import pfenchanter.src.data.EnchanterConfig;
import pfenchanter.src.framework.Tree;
import pfenchanter.src.ui.EnchanterGUI;
import pfenchanter.src.util.API;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

@ScriptManifest(category = Category.MAGIC, name = "PF Enchanter", author = "pharaoh", version = 1.0, image = "https://i.imgur.com/GvEjLsn.png")
public class PFEnchanter extends AbstractScript implements MouseListener {


    private boolean hidePaint = false;
    private final Rectangle PAINT_BUTTON = new Rectangle(475, 458, 40, 18);

    EnchanterConfig ec = EnchanterConfig.getInstance();

    private long startTime;
    private Image image;
    private Timer timer;
    private final Tree tree = new Tree();
    private final RenderingHints aa = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    private void initTree() {
        tree.addBranches(EnchanterGUI.getSelectedItem().getActivityBranch());
    }

    @Override
    public void onStart() {
        timer = new Timer();
        //getRandomManager().disableSolver(RandomEvent.LOGIN);
        SwingUtilities.invokeLater(() -> new EnchanterGUI(ec).setVisible(true));
        startTime = System.currentTimeMillis();
        SkillTracker.start(Skill.MAGIC);

        try {
            image = ImageIO.read(new URL("https://i.imgur.com/vHCUz94.png"));
        } catch (IOException e) {
            log("Failed to load image");
        }
    }

    @Override
    public int onLoop() {
        if (EnchanterGUI.isStartLoop()) {
            initTree();
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
            if (EnchanterGUI.isStartLoop()) {
                if (API.currentLeaf != null) {
                    if (image != null) g.drawImage(image, 0, 0, null);

                    graphics2D.setColor(opaqueBlack);
                    graphics2D.fillRoundRect(5, 342, 510, 134, 2, 2);

                    graphics2D.setColor(opaqueOrange);
                    graphics2D.draw(PAINT_BUTTON);

                    graphics2D.setColor(Color.ORANGE);
                    graphics2D.drawString(getManifest().name(), 380, 473);

                    graphics2D.setColor(Color.WHITE);
                    graphics2D.drawString("Time: " + Timer.formatTime(runTime), x, 360);
                    graphics2D.drawString("Status: " + ec.getStatus(), x, 380);
                    graphics2D.drawString("Experience: " + SkillTracker.getGainedExperience(Skill.MAGIC), x, 460);
                    graphics2D.drawString("v " + getVersion(), 483, 473);

                    if (ec.getPricedItem() != null) {
                        int itemsPH = (int) (ec.getPricedItem().getAmount() / ((System.currentTimeMillis() - startTime) / 3600000.0D));
                        ec.setProfit(ec.getPricedItem().getAmount() * ec.getPricedItem().getPrice());
                        graphics2D.drawString("Items: " + ec.getPricedItem().getAmount() + " (" + itemsPH + ")", x, 400);
                        graphics2D.drawString("Gold: " + QuantityFormatter.formatNumber(ec.getProfit()) + " (" + QuantityFormatter.formatNumber(timer.getHourlyRate(ec.getProfit())) + ")", x, 420);
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

    @Override
    public void onExit() {
        screenshot();
    }

    /**
     * Takes a screenshot and writes the image as PNG to the dreambot folder
     * inside of a folder as the name of the script
     */
    public void screenshot() {
        File file = new File(getManifest().name());
        BufferedImage image = Client.getCanvasImage();
        try {
            if (!file.exists() || !file.isDirectory()) {
                log("Creating script folder");
                file.mkdir();
            }
            log("Saving screenshot...");

            // Repaint
            onPaint(image.getGraphics());

            ImageIO.write(image, "png",
                    new File(String.format("%s/%s.png", getManifest().name(), System.currentTimeMillis())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

