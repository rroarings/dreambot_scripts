/*
 * Created by pharaoh Fri Dec 16 10:31:13 CST 2022
 */

package pfdyemaker.src.ui;

import org.dreambot.api.methods.grandexchange.LivePrices;
import org.dreambot.api.script.ScriptManager;
import org.dreambot.api.utilities.Logger;
import pfdyemaker.src.data.DyeMakerConfig;
import pfdyemaker.src.util.ActionBranch;
import pfdyemaker.src.util.PricedItem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author ovlack
 */
public class DyeMakerUI extends JFrame {

    DyeMakerConfig config = DyeMakerConfig.getDyeMakerConfig();

    private static ActionBranch selectedBranch;
    private static boolean startLoop = false;

    public DyeMakerUI() {
        initComponents();
    }

    private void startBtn(ActionEvent e) {
        if (comboBox1.getSelectedItem().equals(ActionBranch.BUY_WOAD_LEAFS)) {
            config.setDyeIngredient(config.WOAD_LEAVES);
            config.setDyeToMake(null);
            config.setIngredientPrice(LivePrices.get(config.WOAD_LEAVES));
            config.pricedItem = new PricedItem("Woad leaf", true);
        } else if (comboBox1.getSelectedItem().equals(ActionBranch.MAKE_BLUE_DYE)) {
            config.setDyeIngredient(config.WOAD_LEAVES);
            config.setDyeToMake("Blue dye");
            config.setIngredientPrice(LivePrices.get(config.WOAD_LEAVES));
            config.pricedItem = new PricedItem("Blue dye", true);
        } else if (comboBox1.getSelectedItem().equals(ActionBranch.COLLECT_REDBERRIES)) {
            config.setDyeIngredient(config.REDBERRIES);
            config.setDyeToMake(null);
            config.setIngredientPrice(LivePrices.get(config.REDBERRIES));
            config.pricedItem = new PricedItem(config.REDBERRIES, true);
        } else if (comboBox1.getSelectedItem().equals(ActionBranch.MAKE_RED_DYE)) {
            config.setDyeIngredient(config.REDBERRIES);
            config.setIngredientPrice(LivePrices.get(config.REDBERRIES));
            config.setDyeToMake("Red dye");
            config.pricedItem = new PricedItem("Red dye", true);
        } else if (comboBox1.getSelectedItem().equals(ActionBranch.COLLECT_ONIONS)) {
            config.setDyeIngredient(config.ONION);
            config.setIngredientPrice(LivePrices.get(config.ONION));
            config.setDyeToMake(null);
            config.pricedItem = new PricedItem(config.ONION, true);
        } else if (comboBox1.getSelectedItem().equals(ActionBranch.MAKE_YELLOW_DYE)) {
            config.setDyeIngredient(config.ONION);
            config.setIngredientPrice(LivePrices.get(config.ONION));
            config.setDyeToMake("Yellow dye");
            config.pricedItem = new PricedItem("Yellow dye", true);
        }

        selectedBranch = (ActionBranch) comboBox1.getSelectedItem();
        startLoop = true;
        Logger.log("selected activity: " + selectedBranch);
        Logger.log("script started: " + startLoop);
        this.dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        label1 = new JLabel();
        comboBox1 = new JComboBox<>();
        startButton = new JButton();
        label2 = new JLabel();
        energyPotCheckBox = new JCheckBox();

        //======== this ========
        setTitle("PF Dye Maker " + ScriptManager.getScriptManager().getCurrentScript().getVersion());
        setName("this");
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setName("dialogPane");
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setName("contentPanel");
                contentPanel.setLayout(null);

                //---- label1 ----
                label1.setText("Choose Activity");
                label1.setName("label1");
                contentPanel.add(label1);
                label1.setBounds(new Rectangle(new Point(0, 15), label1.getPreferredSize()));

                //---- comboBox1 ----
               for (ActionBranch actionBranch : ActionBranch.values()) {
                   comboBox1.addItem(actionBranch);
               }
                comboBox1.setName("comboBox1");
                contentPanel.add(comboBox1);
                comboBox1.setBounds(145, 5, 180, comboBox1.getPreferredSize().height);

                //---- startButton ----
                startButton.setText("Start");
                startButton.setName("startButton");
                startButton.addActionListener(this::startBtn);
                contentPanel.add(startButton);
                startButton.setBounds(new Rectangle(new Point(160, 145), startButton.getPreferredSize()));

                //---- label2 ----
                label2.setText("Use Energy Potions");
                label2.setName("label2");
                contentPanel.add(label2);
                label2.setBounds(new Rectangle(new Point(0, 55), label2.getPreferredSize()));

                //---- energyPotCheckBox ----
                energyPotCheckBox.setText("Energy Potions");
                energyPotCheckBox.setEnabled(false);
                energyPotCheckBox.setToolTipText("To be added soon");
                energyPotCheckBox.setName("energyPotCheckBox");
                contentPanel.add(energyPotCheckBox);
                energyPotCheckBox.setBounds(new Rectangle(new Point(145, 55), energyPotCheckBox.getPreferredSize()));

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < contentPanel.getComponentCount(); i++) {
                        Rectangle bounds = contentPanel.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = contentPanel.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    contentPanel.setMinimumSize(preferredSize);
                    contentPanel.setPreferredSize(preferredSize);
                }
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    public static JPanel dialogPane;
    public static JPanel contentPanel;
    public static JLabel label1;
    public static JComboBox comboBox1;
    public static JButton startButton;
    public static JLabel label2;
    public static JCheckBox energyPotCheckBox;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public static boolean isStartLoop() {
        return startLoop;
    }

    public static ActionBranch getSelectedItem() {
        return selectedBranch;
    }
}