package pfitemlooter.src.ui;

import org.dreambot.api.utilities.Logger;
import pfitemlooter.src.data.ItemLooterConfig;
import pfitemlooter.src.util.ActivityBranch;
import pfitemlooter.src.util.PricedItem;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ItemLooterUI extends JFrame {

    ItemLooterConfig nbc = ItemLooterConfig.getInstance();

    private static ActivityBranch selectedBranch;
    private static boolean startLoop = false;
    private JComboBox<ActivityBranch> npcComboBox;
    private JButton startButton;

    public ItemLooterUI() {
        super("Choose Item");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(300, 100);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Choose item to loot:");
        npcComboBox = new JComboBox<>();
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
        if (npcComboBox.getSelectedItem().equals(ActivityBranch.LOOT_FISH_FOOD)) {
            nbc.pricedItem = new PricedItem("Fish food", true);
        }

        selectedBranch = (ActivityBranch) npcComboBox.getSelectedItem();
        startLoop = true;
        Logger.log("selected activity: " + selectedBranch);
        Logger.log("script started: " + startLoop);
        this.dispose();
    }
}
