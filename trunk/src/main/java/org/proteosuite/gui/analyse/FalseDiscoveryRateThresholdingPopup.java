package org.proteosuite.gui.analyse;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author SPerkins
 */
public class FalseDiscoveryRateThresholdingPopup extends JDialog {

    private final Map<String, String> thresholdables;
    private boolean hasBeenClosed = false;
    private JComboBox<Object> thresholdingChoice;
    private JComboBox<String> thresholdingOperator;
    private final JCheckBox lowerScoresBetter = new JCheckBox("Lower Scores Better?");
    private final JComboBox<String> thresholdingValue= new JComboBox<>(new String[]{"0.00 (0%)", "0.001 (0.1%)", "0.005 (0.5%)", "0.01 (1%)", "0.02 (2%)", "0.03 (3%)", "0.04 (4%)", "0.05 (5%)"});
    private final JTextField decoyHitTag = new JTextField();
    private final JComboBox<String> fdrLevel = new JComboBox<>(new String[] {"PSM", "Peptide"});

    public FalseDiscoveryRateThresholdingPopup(Window owner, Map<String, String> thresholdables) {
        super(owner, "FDR Thresholding For Selected Files", Dialog.ModalityType.APPLICATION_MODAL);

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

        JLabel message = new JLabel("Choose a metric on which to apply FDR thresholding:");
        message.setAlignmentX(Component.CENTER_ALIGNMENT);

        thresholdingChoice = new JComboBox<>();
        for (Map.Entry<String, String> entry : thresholdables.entrySet()) {
            thresholdingChoice.addItem(entry.getKey());
        }

        thresholdingOperator = new JComboBox<>(new String[]{">", "<"});

        JButton thresholdButton = new JButton("Compute FDR & Threshold");
        thresholdButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        thresholdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                setVisible(false);
                dispose();

            }
        });
        
        panel.add(message);
        
        JPanel theGrid = new JPanel();
        theGrid.setLayout(new GridLayout(5, 2));
        
        JLabel thresholdUsingLabel = new JLabel("FDR Threshold Using:");
        thresholdUsingLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        theGrid.add(thresholdUsingLabel);
        
        theGrid.add(thresholdingChoice);
        
        theGrid.add(Box.createGlue());
        theGrid.add(lowerScoresBetter);
        JLabel valueLabel = new JLabel("FDR Threshold:");
        valueLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        theGrid.add(valueLabel);
        theGrid.add(thresholdingValue);
        JLabel levelLabel = new JLabel("FDR Level:");
        levelLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        theGrid.add(levelLabel);
        theGrid.add(fdrLevel);
        JLabel decoyFieldLabel = new JLabel("Decoy Hit Tag:");
        decoyFieldLabel.setToolTipText("Tag may occur anywhere in the identifier.");
        decoyFieldLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        theGrid.add(decoyFieldLabel);
        theGrid.add(decoyHitTag);
        panel.add(theGrid);
        panel.add(thresholdButton);

        return panel;
    }
    
    public boolean hasBeenClosed() {
        return this.hasBeenClosed;
    }
    
    public String getThresholdTermChosen() {
        return thresholdables.get((String) thresholdingChoice.getSelectedItem());
    }
    
    public double getFDRThresholdValueChosen() {
        String thresholdingValueString = ((String) thresholdingValue.getSelectedItem()).split(" ")[0];
        return Double.parseDouble(thresholdingValueString);
    }
    
    public boolean areLowerValuesBetter() {
        return this.lowerScoresBetter.isSelected();
    }
    
    public String getFDRLevel() {
        return (String) this.fdrLevel.getSelectedItem();
    }
    
    public String getDecoyHitTag() {
        return this.decoyHitTag.getText();
    }
}