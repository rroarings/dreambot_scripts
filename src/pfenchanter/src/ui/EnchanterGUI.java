package pfenchanter.src.ui;

import org.dreambot.api.methods.magic.Normal;
import pfenchanter.src.data.EnchantSpell;
import pfenchanter.src.data.Enchantable;
import pfenchanter.src.data.EnchanterConfig;
import pfenchanter.src.util.ActivityBranch;

import javax.swing.*;
import java.awt.*;

import static org.dreambot.api.utilities.Logger.log;

public class EnchanterGUI extends JFrame {

    private static boolean startLoop = false;
    private static ActivityBranch selectedBranch;

    public EnchantSpell getEnchantSpell() {
        return enchantSpell;
    }

    public Enchantable getEnchantable() {
        return enchantable;
    }

    EnchantSpell enchantSpell;
    Enchantable enchantable;

    private final ButtonGroup stopConditions = new ButtonGroup();
    private final JCheckBox stopAtLevelCheckbox = new JCheckBox("Stop at Level");
    private final JCheckBox stopAtTimeCheckbox = new JCheckBox("Stop at Time");

    private final JPanel spellPanel = new JPanel();
    private final JPanel itemPanel = new JPanel();
    private final JPanel buttonPanel = new JPanel();
    private final JPanel stopPanel = new JPanel();

    private final JComboBox<EnchantSpell> spellBox;
    private JComboBox<Enchantable> enchantItemBox;
    private final JButton startButton = new JButton("Start");

    private final JLabel minutesLabel = new JLabel("minutes");
    private final JTextField stopAtLevelInput = new JTextField();
    private final JTextField stopAtTimeInput = new JTextField();

    public EnchanterGUI(final EnchanterConfig ec) {
        stopConditions.add(stopAtLevelCheckbox);
        stopConditions.add(stopAtTimeCheckbox);

        setTitle("PF Enchanter");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(400, 300));
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new FlowLayout());

        // Spells Panel
        spellPanel.setBorder(BorderFactory.createTitledBorder("Spell Selection"));
        spellPanel.add(new JLabel("Spell:"));
        spellBox = new JComboBox<>(EnchantSpell.values());
        spellBox.addActionListener(e -> {
            enchantSpell = (EnchantSpell) spellBox.getSelectedItem();

            if (enchantSpell != null) {
                        switch (enchantSpell) {
                            case LEVEL_1 -> ec.setSpellToCast(Normal.LEVEL_1_ENCHANT);
                            case LEVEL_2 -> ec.setSpellToCast(Normal.LEVEL_2_ENCHANT);
                            case LEVEL_3 -> ec.setSpellToCast(Normal.LEVEL_3_ENCHANT);
                            case LEVEL_4 -> ec.setSpellToCast(Normal.LEVEL_4_ENCHANT);
                            case LEVEL_5 -> ec.setSpellToCast(Normal.LEVEL_5_ENCHANT);
                            case LEVEL_6 -> ec.setSpellToCast(Normal.LEVEL_6_ENCHANT);
                            case LEVEL_7 -> ec.setSpellToCast(Normal.LEVEL_7_ENCHANT);
                            default -> throw new IllegalStateException("Unexpected value: " + enchantSpell);
                        }
                    }
                });
        spellPanel.add(spellBox);

        // Item Selection
        itemPanel.setBorder(BorderFactory.createTitledBorder("Item Selection"));
        itemPanel.add(new JLabel("Item:"));
        enchantItemBox = new JComboBox<>(Enchantable.values());
        enchantItemBox.addActionListener(e -> enchantable = (Enchantable) enchantItemBox.getSelectedItem());
        itemPanel.add(enchantItemBox);

        // Stop Conditions
        stopPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        stopPanel.setBorder(BorderFactory.createTitledBorder("Stop Conditions"));
        stopAtLevelInput.setPreferredSize(new Dimension(30, 20));
        stopAtTimeInput.setPreferredSize(new Dimension(30, 20));
        stopPanel.add(stopAtLevelCheckbox);
        stopAtLevelCheckbox.addActionListener(e -> {
            if (stopAtLevelCheckbox.isSelected()) {
                ec.setStopAtLevel(Integer.parseInt(stopAtLevelInput.getText()));
                log("stopping at Magic level: " + ec.getStopAtLevel());
            }
        });
        stopPanel.add(stopAtLevelInput);
        stopPanel.add(new JLabel());
        stopPanel.add(stopAtTimeCheckbox);
        stopAtTimeCheckbox.addActionListener(e -> {
            if (stopAtTimeCheckbox.isSelected()) {
                ec.setStopAtMinutesRan(Integer.parseInt(stopAtTimeInput.getText()));
                log("running for: " + ec.getStopAtMinutesRan() + " minutes");
            }
        });
        stopPanel.add(stopAtTimeInput);
        stopPanel.add(minutesLabel);

        // Start Panel
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Start"));
        buttonPanel.add(new JLabel("                                             "));
        startButton.addActionListener(e2 -> {
            ec.setSpellToCast(getEnchantSpell().getSpell());
            ec.setItemToEnchant(getEnchantable().getRegularName());
            ec.setEnchantedItem(getEnchantable().getEnchantedName());
            log("spell :: " + ec.getSpellToCast());
            log("item to enchant :: " + ec.getItemToEnchant());
            log("producing item :: " + ec.getEnchantedItem());
            selectedBranch = ActivityBranch.ENCHANT_ITEM;
            startLoop = true;
            log("starting script");
            dispose();
            });

            /*if (stopAtLevelCheckbox.isSelected()) {
                ec.setStopAtLevel(Integer.parseInt(stopAtLevelInput.getText()));
                log("stopping at Magic level: " + ec.getStopAtLevel());
            } else if (stopAtTimeCheckbox.isSelected()) {
                ec.setStopAtMinutesRan(Integer.parseInt(stopAtTimeInput.getText()));
                log("running for: " + ec.getStopAtMinutesRan() + " minutes");
            }*/


        getContentPane().add(spellPanel);
        getContentPane().add(itemPanel);
        getContentPane().add(stopPanel);
        getContentPane().add(startButton);
    }

    public static boolean isStartLoop() {
        return startLoop;
    }

    public static ActivityBranch getSelectedItem() {
        return selectedBranch;
    }

    /*public EnchanterGUI(final EnchanterConfig ec) {
        setTitle("PF Enchanter");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(320, 300));
        setLocationRelativeTo(null);

        getContentPane().setLayout(new FlowLayout(FlowLayout.LEFT));

        spellPanel.setBorder(BorderFactory.createTitledBorder("Gem Selection"));
        spellPanel.add(new JLabel("Select Gem Spell:"));

        String[] enchantSpells = {"Sapphire", "Opal", "Emerald", "Jade", "Ruby", "Topaz", "Diamond", "Dragonstone", "Onyx", "Zenyte"};
        spellBox = new JComboBox<>(EnchantSpell.values());
        spellPanel.add(spellBox);

        // Add a label and a combobox for item
        itemPanel.setBorder(BorderFactory.createTitledBorder("Gem Selection"));
        itemPanel.add(new JLabel("Select item:"));
        String[] jewellry = {"Ring", "Necklace", "Bracelet", "Amulet"};
        enchantItemBox = new JComboBox<>(Enchantable.values());
        itemPanel.add(enchantItemBox);

        stopPanel.setBorder(BorderFactory.createTitledBorder("Stop Conditions"));
        //stopPanel.setLayout(new BoxLayout(stopPanel, BoxLayout.Y_AXIS));
        stopAtLevelInput.setPreferredSize(new Dimension(30, 25));
        stopAtTimeInput.setPreferredSize(new Dimension(30, 25));

        stopPanel.add(stopAtLevelLabel);
        stopPanel.add(stopAtLevelInput);
        stopPanel.add(stopAtTimeLabel);
        stopPanel.add(stopAtTimeInput);
        stopPanel.add(minutesLabel);

        buttonPanel.setBorder(BorderFactory.createTitledBorder("Start"));
        buttonPanel.add(new JLabel("Select item:"));
        startButton.addActionListener(e -> {
            EnchantSpell enchantSpell = (EnchantSpell) spellBox.getSelectedItem();
            Enchantable jewellryItem = (Enchantable) enchantItemBox.getSelectedItem();

            if (enchantSpell != null) {
                switch (enchantSpell) {
                    case LEVEL_1 -> {
                        log("penis is hot");
                        ec.setSpellToCast(Normal.LEVEL_1_ENCHANT);
                        ec.setItemToEnchant(jewellryItem != null ? jewellryItem.getRegularName() : null);
                        ec.setEnchantedItem(jewellryItem != null ? jewellryItem.getEnchantedName() : null);
                        log("config - casting spell :: " + ec.getSpellToCast());
                        log("config - enchanting item :: " + ec.getItemToEnchant());
                        log("config - producing item :: " + ec.getEnchantedItem());
                        selectedBranch = ActivityBranch.ENCHANT_ITEM;
                        log("added [BRANCH] - " + selectedBranch);
                    }
                    case LEVEL_2 -> {
                        log("tittes is hot");

                        ec.setSpellToCast(Normal.LEVEL_2_ENCHANT);
                        ec.setItemToEnchant(jewellryItem.getRegularName());
                        log("config - casting spell :: " + ec.getSpellToCast());
                        log("config - enchanting item :: " + ec.getItemToEnchant());
                        selectedBranch = ActivityBranch.ENCHANT_ITEM;
                        log("added [BRANCH] - " + selectedBranch);
                        // Add more cases as needed
                    }
                    case LEVEL_3 -> {
                    }
                    case LEVEL_4 -> {
                    }
                    case LEVEL_5 -> {
                    }
                    case LEVEL_6 -> {
                    }
                    case LEVEL_7 -> {
                    }
                }// end of switch
            }
            //log(combined);
            startLoop = true;
            log("script started");
            dispose();
        });
        buttonPanel.add(startButton);

        getContentPane().add(buttonPanel);
        getContentPane().add(spellPanel);
        getContentPane().add(itemPanel);
        getContentPane().add(stopPanel);
    }*/
}
    ;