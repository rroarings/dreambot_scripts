package pfdriftnets.src.ui;

import org.dreambot.api.utilities.Logger;
import pfdriftnets.src.data.DriftNetsConfig;
import pfdriftnets.src.util.ActivityBranch;
import pharaohsfortune.util.UIColors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DriftNetsUI implements ActionListener {

    DriftNetsConfig config = DriftNetsConfig.getDriftNetsConfig();

    private static ActivityBranch selectedBranch;
    private static boolean startLoop = false;

    private JFrame frame;
    private JPanel panel;
    private JLabel locationLabel;

    private JComboBox locationBox;
    private JButton startBtn;

    public DriftNetsUI() {

        frame = new JFrame();

        locationLabel = new JLabel("Location:");
        locationLabel.setForeground(UIColors.TEXT_COLOR);

        locationBox = new JComboBox();
        locationBox.setBackground(UIColors.COMBOBOX_COLOR);

        for (ActivityBranch activityBranch : ActivityBranch.values()) {
            locationBox.addItem(activityBranch);
        }

        startBtn = new JButton("Start");
        startBtn.setBackground(UIColors.BUTTON_COLOUR);
        startBtn.addActionListener(this);

        panel = new JPanel();
        panel.setLayout(new FlowLayout(4));

        panel.add(locationLabel);
        panel.add(locationBox);
        panel.add(startBtn);

        frame.setLocationRelativeTo(null);
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle("PF Drift Nets");
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        selectedBranch = (ActivityBranch) locationBox.getSelectedItem();
        startLoop = true;

        Logger.log("gui -> Location: " + selectedBranch);
        frame.dispose();
    }

    public static boolean isStartLoop() {
        return startLoop;
    }

    public static ActivityBranch getSelectedItem() {
        return selectedBranch;
    }
}
