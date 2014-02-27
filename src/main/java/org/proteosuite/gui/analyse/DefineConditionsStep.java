package org.proteosuite.gui.analyse;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.proteosuite.gui.listener.ContinueButtonListener;
import org.proteosuite.gui.listener.PreviousButtonListener;
import org.proteosuite.gui.tables.DefineConditionsTable;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.RawDataFile;

/**
 *
 * @author SPerkins
 */
public class DefineConditionsStep extends JPanel {
    private static final BorderLayout layout = new BorderLayout();
    private DefineConditionsTable conditionsTable;    
    public DefineConditionsStep() {
        setLayout(layout);        
        
        JLabel stepTitle = new JLabel("Define your conditions:");
        stepTitle.setFont(new Font(stepTitle.getFont().getFontName(), stepTitle.getFont().getStyle(), 72));
        add(stepTitle, BorderLayout.PAGE_START);
        conditionsTable = new DefineConditionsTable();
        JScrollPane conditionTableScroller = new JScrollPane(conditionsTable);
        add(conditionTableScroller, BorderLayout.CENTER);
       
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        JButton previousButton = new JButton("Previous");
        previousButton.addActionListener(new PreviousButtonListener(this));
        buttonsPanel.add(previousButton);
        JButton continueButton = new JButton("Continue");
        continueButton.addActionListener(new ContinueButtonListener(this));
        buttonsPanel.add(continueButton);
        
        
        add(buttonsPanel, BorderLayout.PAGE_END);
    }
    
    public DefineConditionsTable getConditionsTable() {
        return conditionsTable;
    }
    
    public void refreshFromData() {
        AnalyseData data = AnalyseData.getInstance();
        conditionsTable.clear();
        for (RawDataFile file: data.getRawDataFiles()) {
            for (String assay : file.getConditions().keySet()) {                
                conditionsTable.addConditionRow(file.getConditions().get(assay), file.getFileName(), assay);
            }
        }
    }
    
}
