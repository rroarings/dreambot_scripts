package pfitemlooter.src;

import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.frameworks.treebranch.TreeScript;
import org.dreambot.api.script.listener.ChatListener;
import org.dreambot.api.utilities.InventoryMonitor;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.widgets.message.Message;
import pfitemlooter.src.data.ItemLooterConfig;
import pfitemlooter.src.ui.ItemLooterUI;
import pfitemlooter.src.util.PricedItem;

import javax.swing.*;
import java.awt.*;

@ScriptManifest(category = Category.MISC, name = "PF Item Looter", author = "pharaoh", version = 1.0)
public class PFItemLooter extends TreeScript implements ChatListener {

    ItemLooterConfig c = ItemLooterConfig.getInstance();
    InventoryMonitor monitor = new InventoryMonitor();

    private final Rectangle PAINT_BUTTON = new Rectangle(475, 458, 40, 18);
    private final RenderingHints aa = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    private Timer timer;

    private void initTree() {
        addBranches(ItemLooterUI.getSelectedItem().getActivityBranch());
    }

    @Override
    public void onStart() {
        SwingUtilities.invokeLater(() -> new ItemLooterUI().setVisible(true));
        timer = new Timer();
        c.setAlive(true);

        for (PricedItem item : c.pricedItems) {
            log("Item: " + item.getName() + ", Price: " + item.getPrice());
        }
    }

    @Override
    public int onLoop() {

        if (ItemLooterUI.isStartLoop()) {
            initTree();
        }
        if (c.getPricedItem() != null) {
            c.getPricedItem().update();
        }

        monitor.update();
        monitor.getChanges();
        return getRoot().onLoop();
    }

    @Override
    public void onPaint(Graphics g) {
        int x = 12;

        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHints(aa);

        if (getCurrentLeafName() != null) {
            graphics2D.setFont(new Font("Arial", Font.BOLD, 14));
            graphics2D.setColor(Color.ORANGE);
            graphics2D.drawString(getManifest().name(), x, 240);
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString("Time: " + Timer.formatTime(timer.elapsed()), x, 260);
            graphics2D.drawString("Status: " + c.getStatus(), x, 280);
        }
    }

    @Override
    public void onMessage(Message message) {
        if (message != null) {
            if (message.getMessage().contains("Oh dear, you are dead!")) {
                c.setAlive(false);
                log("death reset: true");
            }
        }
    }

}
