/*
 * Created by pharaoh Sun Jan 15 01:31:00 CST 2023
 */

package pfdriftnets.src.ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.*;

/**
 * @author Deceived FX
 */
public class TabMakerUI extends JFrame {
    public TabMakerUI() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        comboBox1 = new JComboBox();
        label1 = new JLabel();
        checkBox1 = new JCheckBox();
        label2 = new JLabel();
        comboBox2 = new JComboBox();
        label3 = new JLabel();
        radioButton1 = new JRadioButton();
        radioButton2 = new JRadioButton();
        radioButton3 = new JRadioButton();
        textField1 = new JTextField();
        label5 = new JLabel();
        checkBox2 = new JCheckBox();
        checkBox3 = new JCheckBox();
        textField2 = new JTextField();
        label6 = new JLabel();
        buttonBar = new JPanel();
        label4 = new JLabel();
        okButton = new JButton();
        cancelButton = new JButton();

        //======== this ========
        setTitle("PF Tab Maker UI");
        setName("this");
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setName("dialogPane");
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setName("contentPanel");

                //---- comboBox1 ----
                comboBox1.setName("comboBox1");

                //---- label1 ----
                label1.setText("Tab to make:");
                label1.setName("label1");

                //---- checkBox1 ----
                checkBox1.setText("Logout when out of supplies");
                checkBox1.setName("checkBox1");

                //---- label2 ----
                label2.setText("Banking:");
                label2.setName("label2");

                //---- comboBox2 ----
                comboBox2.setName("comboBox2");

                //---- label3 ----
                label3.setText("House Options:");
                label3.setName("label3");

                //---- radioButton1 ----
                radioButton1.setText("Advertised House");
                radioButton1.setName("radioButton1");

                //---- radioButton2 ----
                radioButton2.setText("Use Host Name (Use Portal)");
                radioButton2.setName("radioButton2");

                //---- radioButton3 ----
                radioButton3.setText("Use Personal House");
                radioButton3.setName("radioButton3");

                //---- textField1 ----
                textField1.setText("Host name");
                textField1.setToolTipText("Type the host name here");
                textField1.setName("textField1");

                //---- label5 ----
                label5.setText("Stop Conditions");
                label5.setName("label5");

                //---- checkBox2 ----
                checkBox2.setText("Stop when level is reached");
                checkBox2.setName("checkBox2");

                //---- checkBox3 ----
                checkBox3.setText("Stop bot after");
                checkBox3.setName("checkBox3");

                //---- textField2 ----
                textField2.setName("textField2");

                //---- label6 ----
                label6.setText("minutes");
                label6.setName("label6");

                GroupLayout contentPanelLayout = new GroupLayout(contentPanel);
                contentPanel.setLayout(contentPanelLayout);
                contentPanelLayout.setHorizontalGroup(
                    contentPanelLayout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, contentPanelLayout.createSequentialGroup()
                            .addGroup(contentPanelLayout.createParallelGroup()
                                .addGroup(contentPanelLayout.createSequentialGroup()
                                    .addGroup(contentPanelLayout.createParallelGroup()
                                        .addGroup(contentPanelLayout.createSequentialGroup()
                                            .addGap(10, 10, 10)
                                            .addComponent(label1))
                                        .addGroup(contentPanelLayout.createSequentialGroup()
                                            .addContainerGap()
                                            .addComponent(label2)))
                                    .addGap(26, 26, 26)
                                    .addGroup(contentPanelLayout.createParallelGroup()
                                        .addComponent(comboBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(comboBox2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                .addComponent(label3)
                                .addGroup(contentPanelLayout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(contentPanelLayout.createParallelGroup()
                                        .addComponent(radioButton1)
                                        .addComponent(radioButton2)
                                        .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(textField1, GroupLayout.Alignment.LEADING)
                                            .addComponent(radioButton3, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 82, Short.MAX_VALUE)
                            .addGroup(contentPanelLayout.createParallelGroup()
                                .addComponent(checkBox1)
                                .addComponent(checkBox2)
                                .addComponent(label5)
                                .addGroup(contentPanelLayout.createSequentialGroup()
                                    .addComponent(checkBox3)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(textField2, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(label6)))
                            .addGap(24, 24, 24))
                );
                contentPanelLayout.setVerticalGroup(
                    contentPanelLayout.createParallelGroup()
                        .addGroup(contentPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(comboBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(label1)
                                .addComponent(checkBox1))
                            .addGap(27, 27, 27)
                            .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(comboBox2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(label2))
                            .addGap(28, 28, 28)
                            .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label3)
                                .addComponent(label5))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(contentPanelLayout.createParallelGroup()
                                .addComponent(radioButton1)
                                .addComponent(checkBox2))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(radioButton2)
                                .addComponent(checkBox3)
                                .addComponent(textField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(label6))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(radioButton3)
                            .addGap(18, 18, 18)
                            .addComponent(textField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setName("buttonBar");
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 80};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0};

                //---- label4 ----
                label4.setText("Host name:");
                label4.setName("label4");
                buttonBar.add(label4, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- okButton ----
                okButton.setText("OK");
                okButton.setName("okButton");
                buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- cancelButton ----
                cancelButton.setText("Cancel");
                cancelButton.setName("cancelButton");
                buttonBar.add(cancelButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    public static JPanel dialogPane;
    public static JPanel contentPanel;
    public static JComboBox comboBox1;
    public static JLabel label1;
    public static JCheckBox checkBox1;
    public static JLabel label2;
    public static JComboBox comboBox2;
    public static JLabel label3;
    public static JRadioButton radioButton1;
    public static JRadioButton radioButton2;
    public static JRadioButton radioButton3;
    public static JTextField textField1;
    public static JLabel label5;
    public static JCheckBox checkBox2;
    public static JCheckBox checkBox3;
    public static JTextField textField2;
    public static JLabel label6;
    public static JPanel buttonBar;
    public static JLabel label4;
    public static JButton okButton;
    public static JButton cancelButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
