package pfnpcbuyer.src.ui;

import org.dreambot.api.methods.grandexchange.LivePrices;
import org.dreambot.api.utilities.Logger;
import pfnpcbuyer.src.data.NPCBuyerConfig;
import pfnpcbuyer.src.util.ActivityBranch;
import pfnpcbuyer.src.util.PricedItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class NPCBuyerUI extends JFrame {

    NPCBuyerConfig nbc = NPCBuyerConfig.getInstance();

    private static ActivityBranch selectedBranch;
    private static boolean startLoop = false;
    private JComboBox<ActivityBranch> npcComboBox;
    private JButton startButton;

    public NPCBuyerUI() {
        super("Choose NPC");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(300, 100);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Choose NPC:");
        npcComboBox = new JComboBox<ActivityBranch>();
        for (ActivityBranch activityBranch : ActivityBranch.values()) {
            npcComboBox.addItem(activityBranch);
        }
        startButton = new JButton("Start");

        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.add(label);
        panel.add(npcComboBox);
        add(panel, BorderLayout.CENTER);
        add(startButton, BorderLayout.SOUTH);
        startButton.addActionListener(this::startBtn);
        setVisible(true);
    }

    public static boolean isStartLoop() {
        return startLoop;
    }

    public static ActivityBranch getSelectedItem() {
        return selectedBranch;
    }

    private void startBtn(ActionEvent e) {
        if (npcComboBox.getSelectedItem().equals(ActivityBranch.BUY_NEWSPAPERS)) {
            nbc.setItemToBuy("Newspaper");
            nbc.pricedItem = new PricedItem("Newspaper", false);
        } else if (npcComboBox.getSelectedItem().equals(ActivityBranch.BUY_KEBABS)) {
            nbc.setItemToBuy("Kebab");
            nbc.pricedItem = new PricedItem("Kebab", true);
        }

        selectedBranch = (ActivityBranch) npcComboBox.getSelectedItem();
        startLoop = true;
        Logger.log("selected activity: " + selectedBranch);
        Logger.log("script started: " + startLoop);
        this.dispose();
    }

}
