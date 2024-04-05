package pfdyemaker.src.ui;

import org.dreambot.api.methods.grandexchange.LivePrices;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.ScriptManager;
import org.dreambot.api.utilities.Logger;
import pfdyemaker.src.data.DyeMakerConfig;
import pfdyemaker.src.paint.PaintUtils;
import pfdyemaker.src.util.ActionBranch;
import pfdyemaker.src.util.PricedItem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Frame extends JFrame {

    private static ActionBranch selectedBranch;
    private static boolean startLoop = false;

    private final JSpinner minDelaySpinner;
    private final JSpinner maxDelaySpinner;
    public static JComboBox<ActionBranch> comboBox;
    public static JCheckBox nrgPotionCheckBox;
    public static JCheckBox worldHopCheckBox;

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

    public static boolean isEnergyPotions() {
        return nrgPotionCheckBox.isSelected();
    }

    private void exitBtn(ActionEvent e) {
        dispose();
        ScriptManager.getScriptManager().stop();
        Logger.log("(dyemaker) GUI exit button clicked - stopping script");
    }

    private void startBtn(ActionEvent e) {
        if (comboBox.getSelectedItem() != null) {
            if (comboBox.getSelectedItem().equals(ActionBranch.BUY_WOAD_LEAFS)) {
                DyeMakerConfig.dyeConfig().setDyeIngredient(DyeMakerConfig.dyeConfig().getWoadLeaves());
                DyeMakerConfig.dyeConfig().setDyeToMake(null);
                DyeMakerConfig.dyeConfig().setIngredientPrice(LivePrices.get(DyeMakerConfig.dyeConfig().getWoadLeaves()));
                DyeMakerConfig.dyeConfig().setPricedItem(new PricedItem("Woad leaf", true));
            } else if (comboBox.getSelectedItem().equals(ActionBranch.MAKE_BLUE_DYE)) {
                DyeMakerConfig.dyeConfig().setDyeIngredient(DyeMakerConfig.dyeConfig().getWoadLeaves());
                DyeMakerConfig.dyeConfig().setDyeToMake("Blue dye");
                DyeMakerConfig.dyeConfig().setIngredientPrice(LivePrices.get(DyeMakerConfig.dyeConfig().getWoadLeaves()));
                DyeMakerConfig.dyeConfig().setPricedItem(new PricedItem("Blue dye", true));
            } else if (comboBox.getSelectedItem().equals(ActionBranch.COLLECT_REDBERRIES)) {
                DyeMakerConfig.dyeConfig().setDyeIngredient(DyeMakerConfig.dyeConfig().getRedberries());
                DyeMakerConfig.dyeConfig().setDyeToMake(null);
                DyeMakerConfig.dyeConfig().setIngredientPrice(LivePrices.get(DyeMakerConfig.dyeConfig().getRedberries()));
                DyeMakerConfig.dyeConfig().setPricedItem(new PricedItem("Redberries", true));
            } else if (comboBox.getSelectedItem().equals(ActionBranch.MAKE_RED_DYE)) {
                DyeMakerConfig.dyeConfig().setDyeIngredient(DyeMakerConfig.dyeConfig().getRedberries());
                DyeMakerConfig.dyeConfig().setIngredientPrice(LivePrices.get(DyeMakerConfig.dyeConfig().getRedberries()));
                DyeMakerConfig.dyeConfig().setDyeToMake("Red dye");
                DyeMakerConfig.dyeConfig().setPricedItem(new PricedItem("Red dye", true));
            } else if (comboBox.getSelectedItem().equals(ActionBranch.COLLECT_ONIONS)) {
                DyeMakerConfig.dyeConfig().setDyeIngredient(DyeMakerConfig.dyeConfig().getOnion());
                DyeMakerConfig.dyeConfig().setIngredientPrice(LivePrices.get(DyeMakerConfig.dyeConfig().getOnion()));
                DyeMakerConfig.dyeConfig().setDyeToMake(null);
                DyeMakerConfig.dyeConfig().setPricedItem(new PricedItem(DyeMakerConfig.dyeConfig().getOnion(), true));
            } else if (comboBox.getSelectedItem().equals(ActionBranch.MAKE_YELLOW_DYE)) {
                DyeMakerConfig.dyeConfig().setDyeIngredient(DyeMakerConfig.dyeConfig().getOnion());
                DyeMakerConfig.dyeConfig().setIngredientPrice(LivePrices.get(DyeMakerConfig.dyeConfig().getOnion()));
                DyeMakerConfig.dyeConfig().setDyeToMake("Yellow dye");
                DyeMakerConfig.dyeConfig().setPricedItem(new PricedItem("Yellow dye", true));
            }
        } else {
            Logger.log("Uh oh, the action selection was null! Shutting down.");
            Logger.log("[ stop3 ] action selection was null");
        }

        boolean isChecked = nrgPotionCheckBox.isSelected();
        if (isChecked) {
            DyeMakerConfig.isUseEnergyPotions();
        }
        int minValue = (int) minDelaySpinner.getValue();
        int maxValue = (int) maxDelaySpinner.getValue();
        DyeMakerConfig.dyeConfig().setWorldHopDelayMin(minValue);
        DyeMakerConfig.dyeConfig().setWorldHopDelayMax(maxValue);
        selectedBranch = (ActionBranch) comboBox.getSelectedItem();
        startLoop = true;
        if (DyeMakerConfig.isUseEnergyPotions()) {
            Walking.setRunThreshold(50);
        }
        Logger.log("Use energy potions: " + isChecked);
        Logger.log("World hop delays: " + DyeMakerConfig.dyeConfig().getWorldHopDelayMin() + ", " + DyeMakerConfig.dyeConfig().getWorldHopDelayMax());
        Logger.log("Selected action: " + selectedBranch);
        Logger.log("Script loop: " + startLoop);
        dispose();
    }

    public Frame() {
        int BASE_X = 20;
        int BASE_Y = 20;
        setTitle("PF Dye Maker" + " v"+ScriptManager.getScriptManager().getCurrentScript().getVersion());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setBounds(100, 100, 475, 300);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        comboBox = new JComboBox<>();
        for (ActionBranch actionBranch : ActionBranch.values()) {
            comboBox.addItem(actionBranch);
        }

        comboBox.setBounds(BASE_X + 14, BASE_Y + 25, 150, 25);
        contentPane.add(comboBox);

        JLabel chooseActionLbl = new JLabel((String) null);
        chooseActionLbl.setBorder(new TitledBorder(new LineBorder(PaintUtils.LIGHT_GRAY), " Choose Action ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        chooseActionLbl.setBounds(BASE_X, BASE_Y, 205, 70);
        contentPane.add(chooseActionLbl);

        nrgPotionCheckBox = new JCheckBox("Use Energy potions");
        nrgPotionCheckBox.setBounds(BASE_X + 235, BASE_Y + 20, 160, 25);
        contentPane.add(nrgPotionCheckBox);

        worldHopCheckBox = new JCheckBox("World hop");
        worldHopCheckBox.setBounds(BASE_X + 235, nrgPotionCheckBox.getY() + 30, 160, 25);
        contentPane.add(worldHopCheckBox);

        JLabel optionalChecksLbl = new JLabel((String) null);
        optionalChecksLbl.setBorder(new TitledBorder(new LineBorder(PaintUtils.LIGHT_GRAY), " Optional Checks ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        optionalChecksLbl.setBounds(BASE_X + 225, BASE_Y, 205, 90);
        contentPane.add(optionalChecksLbl);

        JLabel minDelayLbl = new JLabel("Min delay");
        minDelayLbl.setBounds(BASE_X + 20, 155, 60, 14);
        contentPane.add(minDelayLbl);

        minDelaySpinner = new JSpinner();
        minDelaySpinner.setModel(new SpinnerNumberModel(0, 0, null, 1));
        minDelaySpinner.setBounds(BASE_X + 20, 180, 60, 20);
        contentPane.add(minDelaySpinner);

        JLabel maxDelayLbl = new JLabel("Max delay");
        maxDelayLbl.setBounds(minDelayLbl.getX() + 90, 155, 67, 14);
        contentPane.add(maxDelayLbl);

        maxDelaySpinner = new JSpinner();
        maxDelaySpinner.setModel(new SpinnerNumberModel(1, 1, null, 1));
        maxDelaySpinner.setBounds(minDelaySpinner.getX() + 90, 180, 60, 20);
        contentPane.add(maxDelaySpinner);

        JLabel worldhopLabel = new JLabel((String) null);
        worldhopLabel.setBorder(new TitledBorder(new LineBorder(PaintUtils.LIGHT_GRAY), " World Hopping ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        worldhopLabel.setBounds(BASE_X, 130, 205, 90);
        contentPane.add(worldhopLabel);

        JLabel coinsAmountLabel = new JLabel((String) null);
        coinsAmountLabel.setBorder(new TitledBorder(new LineBorder(PaintUtils.LIGHT_GRAY), " Coin Amount ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        coinsAmountLabel.setBounds(worldhopLabel.getX() + 225, 130, 205, 90);
        //contentPane.add(coinsAmountLabel);

        JButton startBtn = new JButton("Start");
        startBtn.addActionListener(this::startBtn);
        startBtn.setBounds(245, 230, 205, 25);
        contentPane.add(startBtn);

        JButton exitBtn = new JButton("Exit");
        exitBtn.addActionListener(this::exitBtn);
        exitBtn.setBackground(PaintUtils.GRAY);
        exitBtn.setBounds(BASE_X, 230, 205, 25);
        contentPane.add(exitBtn);
    }

    public static boolean isStartLoop() {
        return startLoop;
    }

    public static ActionBranch getSelectedItem() {
        return selectedBranch;
    }
}
