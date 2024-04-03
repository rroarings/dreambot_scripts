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

    private final JSpinner minDelaySpinner;
    private final JSpinner maxDelaySpinner;
    public static JComboBox<ActionBranch> comboBox;
    public static JCheckBox nrgPotionCheckBox;

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
    }

    private void startBtn(ActionEvent e) {
        if (comboBox.getSelectedItem().equals(ActionBranch.BUY_WOAD_LEAFS)) {
            DyeMakerConfig.getDyeMakerConfig().setDyeIngredient(DyeMakerConfig.getDyeMakerConfig().WOAD_LEAVES);
            DyeMakerConfig.getDyeMakerConfig().setDyeToMake(null);
            DyeMakerConfig.getDyeMakerConfig().setIngredientPrice(LivePrices.get(DyeMakerConfig.getDyeMakerConfig().WOAD_LEAVES));
            DyeMakerConfig.getDyeMakerConfig().pricedItem = new PricedItem("Woad leaf", true);
        } else if (comboBox.getSelectedItem().equals(ActionBranch.MAKE_BLUE_DYE)) {
            DyeMakerConfig.getDyeMakerConfig().setDyeIngredient(DyeMakerConfig.getDyeMakerConfig().WOAD_LEAVES);
            DyeMakerConfig.getDyeMakerConfig().setDyeToMake("Blue dye");
            DyeMakerConfig.getDyeMakerConfig().setIngredientPrice(LivePrices.get(DyeMakerConfig.getDyeMakerConfig().WOAD_LEAVES));
            DyeMakerConfig.getDyeMakerConfig().pricedItem = new PricedItem("Blue dye", true);
        } else if (comboBox.getSelectedItem().equals(ActionBranch.COLLECT_REDBERRIES)) {
            DyeMakerConfig.getDyeMakerConfig().setDyeIngredient(DyeMakerConfig.getDyeMakerConfig().REDBERRIES);
            DyeMakerConfig.getDyeMakerConfig().setDyeToMake(null);
            DyeMakerConfig.getDyeMakerConfig().setIngredientPrice(LivePrices.get(DyeMakerConfig.getDyeMakerConfig().REDBERRIES));
            DyeMakerConfig.getDyeMakerConfig().pricedItem = new PricedItem(DyeMakerConfig.getDyeMakerConfig().REDBERRIES, true);
        } else if (comboBox.getSelectedItem().equals(ActionBranch.MAKE_RED_DYE)) {
            DyeMakerConfig.getDyeMakerConfig().setDyeIngredient(DyeMakerConfig.getDyeMakerConfig().REDBERRIES);
            DyeMakerConfig.getDyeMakerConfig().setIngredientPrice(LivePrices.get(DyeMakerConfig.getDyeMakerConfig().REDBERRIES));
            DyeMakerConfig.getDyeMakerConfig().setDyeToMake("Red dye");
            DyeMakerConfig.getDyeMakerConfig().pricedItem = new PricedItem("Red dye", true);
        } else if (comboBox.getSelectedItem().equals(ActionBranch.COLLECT_ONIONS)) {
            DyeMakerConfig.getDyeMakerConfig().setDyeIngredient(DyeMakerConfig.getDyeMakerConfig().ONION);
            DyeMakerConfig.getDyeMakerConfig().setIngredientPrice(LivePrices.get(DyeMakerConfig.getDyeMakerConfig().ONION));
            DyeMakerConfig.getDyeMakerConfig().setDyeToMake(null);
            DyeMakerConfig.getDyeMakerConfig().pricedItem = new PricedItem(DyeMakerConfig.getDyeMakerConfig().ONION, true);
        } else if (comboBox.getSelectedItem().equals(ActionBranch.MAKE_YELLOW_DYE)) {
            DyeMakerConfig.getDyeMakerConfig().setDyeIngredient(DyeMakerConfig.getDyeMakerConfig().ONION);
            DyeMakerConfig.getDyeMakerConfig().setIngredientPrice(LivePrices.get(DyeMakerConfig.getDyeMakerConfig().ONION));
            DyeMakerConfig.getDyeMakerConfig().setDyeToMake("Yellow dye");
            DyeMakerConfig.getDyeMakerConfig().pricedItem = new PricedItem("Yellow dye", true);
        }

        boolean isChecked = nrgPotionCheckBox.isSelected();
        if (isChecked) {
            DyeMakerConfig.isUseEnergyPotions();
        }
        int minValue = (int) minDelaySpinner.getValue();
        int maxValue = (int) maxDelaySpinner.getValue();
        DyeMakerConfig.setWorldHopDelayMin(minValue);
        DyeMakerConfig.setWorldHopDelayMax(maxValue);
        selectedBranch = (ActionBranch) comboBox.getSelectedItem();
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
        maxDelaySpinner.setBounds(176, 164, 55, 20);
        contentPane.add(maxDelaySpinner);

        JLabel worldhopLabel = new JLabel((String) null);
        worldhopLabel.setBorder(new TitledBorder(new LineBorder(new Color(255, 255, 255)), "World Hopping", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        worldhopLabel.setBounds(29, 113, 360, 95);
        contentPane.add(worldhopLabel);

        JButton startBtn = new JButton("Start");
        startBtn.addActionListener(this::startBtn);
        startBtn.setBounds(300, 219, 89, 23);
        contentPane.add(startBtn);
    }

    public static boolean isStartLoop() {
        return startLoop;
    }

    public static ActionBranch getSelectedItem() {
        return selectedBranch;
    }
}
