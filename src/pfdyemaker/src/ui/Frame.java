package pfdyemaker.src.ui;

import org.dreambot.api.methods.grandexchange.LivePrices;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.utilities.Logger;
import pfdyemaker.src.data.DyeMakerConfig;
import pfdyemaker.src.util.ActionBranch;
import pfdyemaker.src.util.PricedItem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Frame extends JFrame {

    private ActionBranch selectedBranch;
    private boolean startLoop = false;

    private final JSpinner minHopDelaySpinner;
    private final JSpinner maxHopDelaySpinner;
    private final JSpinner minGoldDelaySpinner;
    private final JSpinner maxGoldDelaySpinner;
    public JComboBox<ActionBranch> actionBranchComboBox;
    public JCheckBox nrgPotionCheckBox;
    public JCheckBox worldHopCheckBox;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Frame frame = new Frame();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public boolean isEnergyPotions() {
        return nrgPotionCheckBox.isSelected();
    }

    private void exitBtn(ActionEvent e) {
        dispose();
        ScriptManager.getScriptManager().stop();
    }

    private void startBtn(ActionEvent e) {
        if (actionBranchComboBox.getSelectedItem().equals(ActionBranch.BUY_WOAD_LEAFS)) {
            DyeMakerConfig.getDyeMakerConfig().setDyeIngredient(DyeMakerConfig.getDyeMakerConfig().WOAD_LEAVES);
            DyeMakerConfig.getDyeMakerConfig().setDyeToMake(null);
            DyeMakerConfig.getDyeMakerConfig().setIngredientPrice(LivePrices.get(DyeMakerConfig.getDyeMakerConfig().WOAD_LEAVES));
            DyeMakerConfig.getDyeMakerConfig().pricedItem = new PricedItem("Woad leaf", true);
        } else if (actionBranchComboBox.getSelectedItem().equals(ActionBranch.MAKE_BLUE_DYE)) {
            DyeMakerConfig.getDyeMakerConfig().setDyeIngredient(DyeMakerConfig.getDyeMakerConfig().WOAD_LEAVES);
            DyeMakerConfig.getDyeMakerConfig().setDyeToMake("Blue dye");
            DyeMakerConfig.getDyeMakerConfig().setIngredientPrice(LivePrices.get(DyeMakerConfig.getDyeMakerConfig().WOAD_LEAVES));
            DyeMakerConfig.getDyeMakerConfig().pricedItem = new PricedItem("Blue dye", true);
        } else if (actionBranchComboBox.getSelectedItem().equals(ActionBranch.COLLECT_REDBERRIES)) {
            DyeMakerConfig.getDyeMakerConfig().setDyeIngredient(DyeMakerConfig.getDyeMakerConfig().REDBERRIES);
            DyeMakerConfig.getDyeMakerConfig().setDyeToMake(null);
            DyeMakerConfig.getDyeMakerConfig().setIngredientPrice(LivePrices.get(DyeMakerConfig.getDyeMakerConfig().REDBERRIES));
            DyeMakerConfig.getDyeMakerConfig().pricedItem = new PricedItem(DyeMakerConfig.getDyeMakerConfig().REDBERRIES, true);
        } else if (actionBranchComboBox.getSelectedItem().equals(ActionBranch.MAKE_RED_DYE)) {
            DyeMakerConfig.getDyeMakerConfig().setDyeIngredient(DyeMakerConfig.getDyeMakerConfig().REDBERRIES);
            DyeMakerConfig.getDyeMakerConfig().setIngredientPrice(LivePrices.get(DyeMakerConfig.getDyeMakerConfig().REDBERRIES));
            DyeMakerConfig.getDyeMakerConfig().setDyeToMake("Red dye");
            DyeMakerConfig.getDyeMakerConfig().pricedItem = new PricedItem("Red dye", true);
        } else if (actionBranchComboBox.getSelectedItem().equals(ActionBranch.COLLECT_ONIONS)) {
            DyeMakerConfig.getDyeMakerConfig().setDyeIngredient(DyeMakerConfig.getDyeMakerConfig().ONION);
            DyeMakerConfig.getDyeMakerConfig().setIngredientPrice(LivePrices.get(DyeMakerConfig.getDyeMakerConfig().ONION));
            DyeMakerConfig.getDyeMakerConfig().setDyeToMake(null);
            DyeMakerConfig.getDyeMakerConfig().pricedItem = new PricedItem(DyeMakerConfig.getDyeMakerConfig().ONION, true);
        } else if (actionBranchComboBox.getSelectedItem().equals(ActionBranch.MAKE_YELLOW_DYE)) {
            DyeMakerConfig.getDyeMakerConfig().setDyeIngredient(DyeMakerConfig.getDyeMakerConfig().ONION);
            DyeMakerConfig.getDyeMakerConfig().setIngredientPrice(LivePrices.get(DyeMakerConfig.getDyeMakerConfig().ONION));
            DyeMakerConfig.getDyeMakerConfig().setDyeToMake("Yellow dye");
            DyeMakerConfig.getDyeMakerConfig().pricedItem = new PricedItem("Yellow dye", true);
        }

        boolean isChecked = nrgPotionCheckBox.isSelected();
        if (isChecked) {
            DyeMakerConfig.isUseEnergyPotions();
        }
        int minValue = (int) minHopDelaySpinner.getValue();
        int maxValue = (int) maxHopDelaySpinner.getValue();
        DyeMakerConfig.setWorldHopDelayMin(minValue);
        DyeMakerConfig.setWorldHopDelayMax(maxValue);
        selectedBranch = (ActionBranch) actionBranchComboBox.getSelectedItem();
        startLoop = true;
        if (DyeMakerConfig.isUseEnergyPotions()) {
            Walking.setRunThreshold(20);
        }
        Logger.log("energy potions: " + isChecked);
        Logger.log("world hop delays: " + DyeMakerConfig.getWorldHopDelayMin() + ", " + DyeMakerConfig.getWorldHopDelayMax());
        Logger.log("selected activity: " + selectedBranch);
        Logger.log("script started: " + startLoop);
        this.dispose();
    }

    public Frame() {
        setTitle("PF Dye Maker");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(getOwner());
        setBounds(100, 100, 450, 300);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Choose activity:");
        lblNewLabel.setBounds(29, 31, 95, 16);
        contentPane.add(lblNewLabel);

        comboBox = new JComboBox<>();
        for (ActionBranch actionBranch : ActionBranch.values()) {
            comboBox.addItem(actionBranch);
        }
        comboBox.setBounds(190, 27, 199, 22);
        contentPane.add(comboBox);

        JLabel potionsLbl = new JLabel("Use Energy potions:");
        potionsLbl.setBounds(29, 71, 119, 16);
        contentPane.add(potionsLbl);

        nrgPotionCheckBox = new JCheckBox("Energy potions");
        nrgPotionCheckBox.setBounds(190, 67, 151, 23);
        contentPane.add(nrgPotionCheckBox);

        JLabel minDelayLbl = new JLabel("Min delay");
        minDelayLbl.setBounds(49, 130, 60, 14);
        contentPane.add(minDelayLbl);

        minDelaySpinner = new JSpinner();
        minDelaySpinner.setModel(new SpinnerNumberModel(0, 0, null, 1));
        minDelaySpinner.setBounds(49, 155, 55, 20);
        contentPane.add(minDelaySpinner);

        JLabel maxDelayLbl = new JLabel("Max delay");
        maxDelayLbl.setBounds(162, 130, 67, 14);
        contentPane.add(maxDelayLbl);

        maxDelaySpinner = new JSpinner();
        maxDelaySpinner.setModel(new SpinnerNumberModel(1, 1, null, 1));
        maxDelaySpinner.setBounds(minHopDelaySpinner.getX() + 76, 160, 55, 20);
        contentPane.add(maxDelaySpinner);

        minHopDelaySpinner.addChangeListener(e -> adjustMaxHopDelay());
        adjustMaxHopDelay();

        JLabel worldhopLabel = new JLabel((String) null);
        worldhopLabel.setBorder(new TitledBorder(new LineBorder(new Color(255, 255, 255)), " World Hopping Delay ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        worldhopLabel.setBounds(29, 110, 180, 97);
        worldhopLabel.setToolTipText("Time to sleep before hopping worlds");
        contentPane.add(worldhopLabel);

        JLabel minGoldDelayLbl = new JLabel("Min gold");
        minGoldDelayLbl.setBounds(241, 135, 60, 16);
        contentPane.add(minGoldDelayLbl);

        minGoldDelaySpinner = new JSpinner();
        minGoldDelaySpinner.setModel(new SpinnerNumberModel(0, 0, null, 1));
        minGoldDelaySpinner.setBounds(241, 160, 70, 20);
        contentPane.add(minGoldDelaySpinner);

        JLabel maxGoldDelayLbl = new JLabel("Max gold");
        maxGoldDelayLbl.setBounds(minGoldDelayLbl.getX() + 90, 135, 67, 16);
        contentPane.add(maxGoldDelayLbl);

        maxGoldDelaySpinner = new JSpinner();
        maxGoldDelaySpinner.setModel(new SpinnerNumberModel(1, 1, null, 1));
        maxGoldDelaySpinner.setBounds(minGoldDelaySpinner.getX() + 90, 160, 70, 20);
        contentPane.add(maxGoldDelaySpinner);

        JLabel goldAmountLabel = new JLabel((String) null);
        goldAmountLabel.setBorder(new TitledBorder(new LineBorder(new Color(255, 255, 255)), " Gold Amount ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        goldAmountLabel.setBounds(219, 110, 200, 97);
        goldAmountLabel.setToolTipText("Minimum and maximum gold amount to take for making dyes");
        contentPane.add(goldAmountLabel);

        JButton exitBtn = new JButton("Exit");
        exitBtn.addActionListener(this::exitBtn);
        exitBtn.setBackground(Color.GRAY);
        exitBtn.setBounds(worldhopLabel.getX(), 219, worldhopLabel.getWidth(), 22);
        contentPane.add(exitBtn);

        JButton startBtn = new JButton("Start");
        startBtn.addActionListener(this::startBtn);
        startBtn.setBounds(300, 219, 89, 23);
        contentPane.add(startBtn);
    }

    private void adjustMaxHopDelay() {
        int minValue = (int) minHopDelaySpinner.getValue();
        int maxValue = (int) maxDelaySpinner.getValue();

        if (minValue == 1 && maxValue < 2) {
            maxDelaySpinner.setValue(2);
        }
        if (minValue >= maxValue) {
            maxDelaySpinner.setValue(minValue + 1);
        }
    }

    public ActionBranch getSelectedBranch() {
        return selectedBranch;
    }

    public boolean isStartLoop() {
        return startLoop;
    }

    public static ActionBranch getSelectedItem() {
        return selectedBranch;
    }
}
