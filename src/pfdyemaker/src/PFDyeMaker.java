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
    private PaintHandler paintHandler;
    private Frame gui;

    public Timer getTimer() {
        return timer;
    }

    private void initTree() {
        addBranches(gui.getSelectedItem().getActionBranch());
    }

    @Override
    public void onStart() {
        timer = new Timer();
        paintHandler = new PaintHandler(this);
        Client.getCanvas().addMouseListener(paintHandler);
        SwingUtilities.invokeLater(() -> new Frame().setVisible(true));
        timer.start();
        DyeMakerConfig.dyeConfig().setStatus("Waiting on script configuration");
    }

    @Override
    public int onLoop() {
        if (gui != null) {
            if (gui.isVisible()) {
                gui.dispose();
            }
            initTree();
            if (DyeMakerConfig.dyeConfig().getPricedItem() != null) {
                DyeMakerConfig.dyeConfig().getPricedItem().update();
            }
        }
        return this.getRoot().onLoop();
    }

    @Override
    public void onPause() {
        if (ScriptManager.getScriptManager().isPaused()) {
            timer.pause();
        } else if (!ScriptManager.getScriptManager().isPaused()) {
            timer.resume();
        }
    }

    @Override
    public void onExit() {
        Client.getCanvas().removeMouseListener(paintHandler);
    }

    @Override
    public void onPaint(Graphics g) {
        paintHandler.paint(g);
    }

}
