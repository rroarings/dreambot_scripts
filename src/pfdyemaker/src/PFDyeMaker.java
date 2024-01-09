package pfdyemaker.src;

import org.dreambot.api.Client;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.frameworks.treebranch.TreeScript;
import pfdyemaker.src.data.DyeMakerConfig;
import pfdyemaker.src.paint.PaintHandler;
import pfdyemaker.src.ui.Frame;

import javax.swing.*;
import java.awt.*;

@ScriptManifest(category = Category.MONEYMAKING, name = "PF Dye Maker", author = "pharaoh", version = 1.6, image = "https://i.imgur.com/zQ1Vwvt.png")
public class PFDyeMaker extends TreeScript {

    private long startTime;
    private PaintHandler paintHandler;

    public long getStartTime() {
        return startTime;
    }

    private void initTree() {
        addBranches(Frame.getSelectedItem().getActionBranch());
    }

    @Override
    public void onStart() {
        paintHandler = new PaintHandler(this);
        Client.getCanvas().addMouseListener(paintHandler);
        SwingUtilities.invokeLater(() -> new Frame().setVisible(true));
        startTime = System.currentTimeMillis();
    }

    @Override
    public int onLoop() {
        if (Frame.isStartLoop()) {
            if (DyeMakerConfig.isUseEnergyPotions()) {
                Walking.setRunThreshold(20);
            }
            initTree();
        }
        if (DyeMakerConfig.getDyeMakerConfig().getPricedItem() != null) {
            DyeMakerConfig.getDyeMakerConfig().getPricedItem().update();
        }
        return this.getRoot().onLoop();
    }

    @Override
    public void onPaint(Graphics g) {
        paintHandler.paint(g);
    }

}
