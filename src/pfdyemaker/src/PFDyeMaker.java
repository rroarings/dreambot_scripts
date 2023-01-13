package pfdyemaker.src;

import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.listener.ChatListener;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.widgets.message.Message;
import pfdyemaker.src.framework.Tree;
import pfdyemaker.src.ui.DyeMakerUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

import static pfdyemaker.src.data.DyeMakerConfig.getDyeMakerConfig;

@ScriptManifest(category = Category.MONEYMAKING, name = "PF Dye Maker", author = "pharaoh", version = 1.1, image = "https://i.imgur.com/zQ1Vwvt.png")
public class PFDyeMaker extends AbstractScript implements ChatListener {

    private long startTime;
    private Image image;
    private final Tree tree = new Tree();
    private final RenderingHints aa = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    private void initTree() { tree.addBranches(DyeMakerUI.getSelectedItem().getActivityBranch()); }

    @Override
    public void onStart() {
        SwingUtilities.invokeLater(() -> new DyeMakerUI().setVisible(true));
        startTime = System.currentTimeMillis();

        try {
            image = ImageIO.read(new URL("https://i.imgur.com/ISw4eOO.png"));
        } catch (IOException e) {
            log("Failed to load image");
        }
    }

    @Override
    public int onLoop() {
        if (DyeMakerUI.isStartLoop()) {
            initTree();
        }
        return this.tree.onLoop();
    }

    @Override
    public void onPaint(Graphics g) {
        long runTime = System.currentTimeMillis() - startTime;
        int itemsPH = (int) (getDyeMakerConfig().getIngredientsCollected() / ((System.currentTimeMillis() - startTime) / 3600000.0D));
        int pPH = (int) (getDyeMakerConfig().getProfit() / ((System.currentTimeMillis() - startTime) / 3600000.0D));
        int alpha = 127;
        int x = 12;

        Graphics2D graphics2D = (Graphics2D) g;
        Color opaqueBlack = new Color(0, 0, 0, alpha);

        getDyeMakerConfig().setProfit(getDyeMakerConfig().getIngredientsCollected() * getDyeMakerConfig().getIngredientPrice());
        graphics2D.setRenderingHints(aa);
        graphics2D.setFont(new Font("Bahnschrift", Font.PLAIN, 14));

        if (image != null) {
            g.drawImage(image, 0, 0, null);
        }

        graphics2D.setColor(opaqueBlack);
        graphics2D.fillRoundRect(5, 347, 324, 124, 8, 8);

        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString(getManifest().name(), 127, 322);

        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString("Time: " + Timer.formatTime(runTime), x, 360);
        graphics2D.drawString("Status: " + getDyeMakerConfig().getStatus(), x, 380);
        graphics2D.drawString("Dyes: " + getDyeMakerConfig().getDyesMade(), x, 400);
        graphics2D.drawString("Items: " + getDyeMakerConfig().getIngredientsCollected() + " (" + itemsPH + ")", x, 420);
        graphics2D.drawString("Gold: " + getDyeMakerConfig().getProfit() + " (" + pPH + ")", x, 440);
        graphics2D.drawString("v " + getVersion(), 294, 471);
    }

    @Override
    public void onMessage(Message message) {
        if (message != null && message.getMessage().contains("You pick an onion")) {
            getDyeMakerConfig().ingredientsCollected++;
        }
    }
}
