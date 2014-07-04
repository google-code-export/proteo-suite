package org.proteosuite.gui.analyse;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import org.proteosuite.utils.NumericalUtils;

/**
 *
 * @author SPerkins
 */
public class ThresholdingPopup extends JDialog {

    private static final long serialVersionUID = 1L;
    private JComboBox<String> thresholdingChoice;
    private JComboBox<String> thresholdingOperator;
    private JTextField thresholdingValue;
    private final Map<String, String> thresholdables;
    private boolean hasBeenClosed = false;

    public ThresholdingPopup(Window owner, Map<String, String> thresholdables) {
        super(owner, "Choose Thresholding For Selected Files", Dialog.ModalityType.APPLICATION_MODAL);

        this.thresholdables = thresholdables;

        setIconImage(new ImageIcon(getClass().getClassLoader().getResource(
                "images/icon.gif")).getImage());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                hasBeenClosed = true;
                dispose();
            }
        });

        getContentPane().add(generateJPanel());
        pack();
    }

    private JPanel generateJPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel message = new JLabel("Choose a metric on which to apply thresholding:");
        message.setAlignmentX(Component.CENTER_ALIGNMENT);

        thresholdingChoice = new JComboBox<>();
        for (Entry<String, String> entry : thresholdables.entrySet()) {
            thresholdingChoice.addItem(entry.getKey());
        }

        thresholdingOperator = new JComboBox<>(new String[]{">", "<"});

        JButton thresholdButton = new JButton("Threshold!");
        thresholdButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        thresholdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (NumericalUtils.isDouble(thresholdingValue.getText())) {
                    setVisible(false);
                    dispose();
                } else {
                    JOptionPane.showConfirmDialog(
                            ThresholdingPopup.this,
                            "You have entered an invalid number for the thresholding value.\n"
                            + "Please enter a valid number to proceed.",
                            "Invalid Thresholding Value", JOptionPane.PLAIN_MESSAGE,
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        thresholdingValue = new JTextField("0.0");

        panel.add(message);

        JPanel theGrid = new JPanel();
        theGrid.setLayout(new GridLayout(3, 2));
        JLabel thresholdOnLabel = new JLabel("Threshold On:");
        thresholdOnLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        theGrid.add(thresholdOnLabel);
        theGrid.add(thresholdingChoice);
        theGrid.add(Box.createGlue());
        theGrid.add(thresholdingOperator);
        JLabel valueLabel = new JLabel("Value:");
        valueLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        theGrid.add(valueLabel);
        theGrid.add(thresholdingValue);
        panel.add(theGrid);
        panel.add(thresholdButton);

        return panel;
    }

    private JPanel getRow(Component... components) {
        JPanel row = new JPanel();
        row.setLayout(new FlowLayout(FlowLayout.LEFT));

        for (Component c : components) {
            row.add(c);
        }

        return row;
    }

    public String[] getThresholdingChosen() {
        return new String[]{thresholdables.get((String) thresholdingChoice.getSelectedItem()), (String) thresholdingOperator.getSelectedItem(), thresholdingValue.getText()};
    }

    public boolean hasBeenClosed() {
        return this.hasBeenClosed;
    }
}
