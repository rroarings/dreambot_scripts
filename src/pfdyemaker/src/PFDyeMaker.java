package pfdyemaker.src;

import org.dreambot.api.Client;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManager;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.frameworks.treebranch.TreeScript;
import org.dreambot.api.utilities.Timer;
import pfdyemaker.src.data.DyeMakerConfig;
import pfdyemaker.src.paint.PaintHandler;
import pfdyemaker.src.ui.Frame;

import javax.swing.*;
import java.awt.*;

@ScriptManifest(category = Category.MONEYMAKING, name = "PF Dye Maker", author = "pharaoh", version = 1.7, image = "https://i.imgur.com/zQ1Vwvt.png")
public class PFDyeMaker extends TreeScript {

    private Timer timer;
    private long startTime;
    private PaintHandler paintHandler;
    private Frame gui;

    public Timer getTimer() {
        return timer;
    }

    private void initTree() {
        addBranches(gui.getSelectedBranch().getActionBranch());
    }

    @Override
    public void onStart() {
        timer = new Timer();
        paintHandler = new PaintHandler(this);
        Client.getCanvas().addMouseListener(paintHandler);
        SwingUtilities.invokeLater(() -> {
            gui = new Frame(); // Initialize the gui field
            gui.setVisible(true);
        });
        startTime = System.currentTimeMillis();
        timer.start();
    }

    @Override
    public void onExit() {
        if (gui != null) {
            if (gui.isVisible()) {
                gui.dispose();
            }
        }
    }

    @Override
    public void onPause() {
        if (timer != null) {
            timer.pause();
        }
    }

    @Override
    public int onLoop() {
       if (gui != null) {
           if (gui.isStartLoop()) {
               initTree();
           }
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
