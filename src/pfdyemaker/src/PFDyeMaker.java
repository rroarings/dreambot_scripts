package pfdyemaker.src;

import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Timer;
import pfdyemaker.src.data.DyeMakerConfig;
import pfdyemaker.src.framework.Tree;
import pfdyemaker.src.ui.DyeMakerUI;
import pfdyemaker.src.util.API;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

@ScriptManifest(category = Category.MONEYMAKING, name = "PF Dye Maker", author = "pharaoh", version = 0.2)
public class PFDyeMaker extends AbstractScript {

    private long startTime;
    private Image image;
    private final Tree tree = new Tree();
    private final RenderingHints aa = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    DyeMakerConfig config = DyeMakerConfig.getDyeMakerConfig();

    private void initTree() {
       tree.addBranches(
               DyeMakerUI.getSelectedItem().getActivityBranch());
    }

    @Override
    public void onStart() {
        SwingUtilities.invokeLater(() -> new DyeMakerUI().setVisible(true));
        startTime = System.currentTimeMillis();

        try {
            image = ImageIO.read(new URL("https://i.imgur.com/mVFXE56.png"));
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
        config.setProfit(config.getIngredientsCollected() * config.getIngredientPrice());
        long runTime = System.currentTimeMillis() - startTime;

        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHints(aa);

        if (image != null) {
            g.drawImage(image, 0, 145, null);
        }

        int alpha = 100;
        Color opaqueBlack = new Color(0, 0, 0, alpha);

        graphics2D.setColor(opaqueBlack);
        graphics2D.fillRoundRect(5, 224, 314, 119, 8 , 8);

        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(new Font("Tahoma", Font.PLAIN, 15));

        graphics2D.drawString("Time: " + Timer.formatTime(runTime), 10, 245);
        graphics2D.drawString("Status: " + config.getStatus(), 10, 265);
        graphics2D.drawString("v " + getVersion(), 283, 338);

        graphics2D.setFont(new Font("Tahoma", Font.BOLD, 16));
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString(getManifest().name(), 108, 196);
    }
}
