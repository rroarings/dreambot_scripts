package pfdyemaker.src.ui;

import org.dreambot.api.methods.grandexchange.LivePrices;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.ScriptManager;
import org.dreambot.api.utilities.Logger;
import pfdyemaker.src.PFDyeMaker;
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
        if (actionBranchComboBox.getSelectedItem().equals(ActionBranch.BUY_WOAD_LEAVES)) {
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
        PFDyeMaker dyeMaker = new PFDyeMaker();
        setTitle("PF Dye Maker " + dyeMaker.getVersion());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setBounds(100, 100, 450, 300);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        /* Action */
        actionBranchComboBox = new JComboBox<>();
        for (ActionBranch actionBranch : ActionBranch.values()) {
            actionBranchComboBox.addItem(actionBranch);
        }
        actionBranchComboBox.setBounds(42, 37, 148, 24);
        contentPane.add(actionBranchComboBox);

        JLabel actionLbl = new JLabel((String) null);
        actionLbl.setBorder(new TitledBorder(new LineBorder(new Color(255, 255, 255)), " Choose Action ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        actionLbl.setBounds(29, 15, 175, 60);
        actionLbl.setToolTipText("Minimum and maximum gold amount to take for making dyes");
        contentPane.add(actionLbl);
        /* end Action */

        /* Optionals  */
        JLabel energyPotionLbl = new JLabel("Use Energy potions");
        energyPotionLbl.setBounds(250, 25, 119, 16);
        //contentPane.add(energyPotionLbl);

        JLabel worldHopLbl = new JLabel("Enable World Hop");
        worldHopLbl.setBounds(250, 80, 119, 16);
        //contentPane.add(worldHopLbl);

        nrgPotionCheckBox = new JCheckBox("Enable Energy potion(*)");
        nrgPotionCheckBox.setToolTipText("Click to enable using any available Energy potion(*)s");
        nrgPotionCheckBox.setBounds(230, 35, 180, 23);
        contentPane.add(nrgPotionCheckBox);

        worldHopCheckBox = new JCheckBox("Enable world hopping");
        worldHopCheckBox.setToolTipText("Click to enable world hopping");
        worldHopCheckBox.setBounds(230, 60, 151, 23);
        contentPane.add(worldHopCheckBox);

        JLabel optionalChecksLbl = new JLabel((String) null);
        optionalChecksLbl.setBorder(new TitledBorder(new LineBorder(new Color(255, 255, 255)), " Optional Checks ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        optionalChecksLbl.setBounds(219, 14, 200, 80);
        optionalChecksLbl.setToolTipText("Optional checks");
        contentPane.add(optionalChecksLbl);
        /* end Optionals */

        /* World Hop Delays */
        JLabel minDelayLbl = new JLabel("Min delay");
        minDelayLbl.setBounds(54, 135, 60, 14);
        contentPane.add(minDelayLbl);

        minHopDelaySpinner = new JSpinner();
        minHopDelaySpinner.setModel(new SpinnerNumberModel(0, 0, null, 1));
        minHopDelaySpinner.setBounds(54, 160, 55, 20);
        contentPane.add(minHopDelaySpinner);

        JLabel maxHopDelayLbl = new JLabel("Max delay");
        maxHopDelayLbl.setBounds(minDelayLbl.getX() + 76, 135, 67, 14);
        contentPane.add(maxHopDelayLbl);

        maxHopDelaySpinner = new JSpinner();
        maxHopDelaySpinner.setModel(new SpinnerNumberModel(1, 1, null, 1));
        maxHopDelaySpinner.setBounds(minHopDelaySpinner.getX() + 76, 160, 55, 20);
        contentPane.add(maxHopDelaySpinner);

        minHopDelaySpinner.addChangeListener(e -> adjustMaxHopDelay());
        adjustMaxHopDelay();

        JLabel worldhopLabel = new JLabel((String) null);
        worldhopLabel.setBorder(new TitledBorder(new LineBorder(new Color(255, 255, 255)), " World Hopping Delay ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        worldhopLabel.setBounds(29, 110, 180, 97);
        worldhopLabel.setToolTipText("Time to sleep before hopping worlds");
        contentPane.add(worldhopLabel);
        /* end World Hop Delays */

        /* Gold Amount */
        JLabel minGoldDelayLbl = new JLabel("Min gold");
        minGoldDelayLbl.setBounds(238, 135, 60, 16);
        contentPane.add(minGoldDelayLbl);

        minGoldDelaySpinner = new JSpinner();
        minGoldDelaySpinner.setModel(new SpinnerNumberModel(0, 0, null, 1));
        minGoldDelaySpinner.setBounds(238, 160, 70, 20);
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
        /* end Gold Amount */

        /* Exit button */
        JButton exitBtn = new JButton("Exit");
        exitBtn.addActionListener(this::exitBtn);
        exitBtn.setBackground(Color.GRAY);
        exitBtn.setBounds(worldhopLabel.getX(), 219, worldhopLabel.getWidth(), 22);
        contentPane.add(exitBtn);

        /* Start button */
        JButton startBtn = new JButton("Start");
        startBtn.addActionListener(this::startBtn);
        startBtn.setBounds(goldAmountLabel.getX(), 219, goldAmountLabel.getWidth(), 22);
        contentPane.add(startBtn);
    }

    private void adjustMaxHopDelay() {
        int minValue = (int) minHopDelaySpinner.getValue();
        int maxValue = (int) maxHopDelaySpinner.getValue();

        if (minValue == 1 && maxValue < 2) {
            maxHopDelaySpinner.setValue(2);
        }
        if (minValue >= maxValue) {
            maxHopDelaySpinner.setValue(minValue + 1);
        }
    }

    public ActionBranch getSelectedBranch() {
        return selectedBranch;
    }

    public boolean isStartLoop() {
        return startLoop;
    }

}