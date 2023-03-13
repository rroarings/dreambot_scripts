package pfnpcbuyer.src;

import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.frameworks.treebranch.TreeScript;
import org.dreambot.api.utilities.Timer;
import pfnpcbuyer.src.data.NPCBuyerConfig;
import pfnpcbuyer.src.ui.NPCBuyerUI;
import pfnpcbuyer.src.util.QuantityFormatter;

import javax.swing.*;
import java.awt.*;

@ScriptManifest(category = Category.MISC, name = "PF NPC Buyer", author = "pharaoh", version = 1.0)
public class PFNPCBuyer extends TreeScript {

    NPCBuyerConfig nbc = NPCBuyerConfig.getInstance();

    private boolean hidePaint = false;
    private final Rectangle PAINT_BUTTON = new Rectangle(475, 458, 40, 18);
    private final RenderingHints aa = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    private long startTime;
    private Timer timer;

    private void initTree() {
        addBranches(NPCBuyerUI.getSelectedItem().getActivityBranch());
    }

    @Override
    public void onStart() {
        timer = new Timer();
        SwingUtilities.invokeLater(() -> new NPCBuyerUI().setVisible(true));

    }

    @Override
    public int onLoop() {
        if (NPCBuyerUI.isStartLoop()) {
            initTree();
        }
        if (nbc.getPricedItem() != null) {
            nbc.getPricedItem().update();
        }
        return getRoot().onLoop();
    }


    @Override
    public void onExit() {

    }

    @Override
    public void onPaint(Graphics g) {
        long runTime = System.currentTimeMillis() - startTime;
        int alpha = 127;
        int x = 12;

        Graphics2D graphics2D = (Graphics2D) g;
        Color opaqueBlack = new Color(0, 0, 0, alpha);

        graphics2D.setRenderingHints(aa);
        graphics2D.setFont(new Font("Bahnschrift", Font.PLAIN, 14));

        if (NPCBuyerUI.isStartLoop()) {
            if (getCurrentLeafName() != null) {

                graphics2D.setColor(opaqueBlack);
                graphics2D.fillRoundRect(5, 342, 510, 134, 2, 2);

                graphics2D.setColor(Color.ORANGE);
                graphics2D.draw(PAINT_BUTTON);
                graphics2D.drawString(getManifest().name(), 380, 473);

                graphics2D.setColor(Color.WHITE);
                graphics2D.drawString("Time: " + Timer.formatTime(timer.elapsed()), x, 360);
                graphics2D.drawString("Status: " + nbc.getStatus(), x, 380);
                graphics2D.drawString("v 1.0", 480, 473);

                if (nbc.getPricedItem() != null) {
                    nbc.setProfit(nbc.getPricedItem().getAmount() * nbc.getPricedItem().getPrice());
                    graphics2D.drawString("Items: " + nbc.getPricedItem().getAmount() + " (" + QuantityFormatter.formatNumber(timer.getHourlyRate(nbc.getPricedItem().getAmount())) + ")", x, 400);
                    if (getCurrentBranchName().contains("kebab")) {
                        graphics2D.drawString("Gold: " + QuantityFormatter.formatNumber(nbc.getProfit()) + " (" + QuantityFormatter.formatNumber(timer.getHourlyRate(nbc.getProfit())) + ")", x, 420);
                    }
                }
            }
        }

    }
}
