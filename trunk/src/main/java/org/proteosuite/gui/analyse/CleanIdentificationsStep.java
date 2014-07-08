package org.proteosuite.gui.analyse;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.proteosuite.gui.listener.ContinueButtonListener;
import org.proteosuite.gui.listener.PerformThresholdingForSelectedListener;
import org.proteosuite.gui.listener.PreviousButtonListener;
import org.proteosuite.gui.tables.CleanIdentificationsTable;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.IdentDataFile;

/**
 * 
 * @author SPerkins
 */
public class CleanIdentificationsStep extends JPanel {
	private static final long serialVersionUID = 1L;
	private final CleanIdentificationsTable cleanIdentificationsTable = new CleanIdentificationsTable();

	public CleanIdentificationsStep() {
            super(new BorderLayout());
            
            JLabel stepTitle = new JLabel("Clean up your identifications:");
		stepTitle.setFont(new Font(stepTitle.getFont().getFontName(), stepTitle
				.getFont().getStyle(), 72));
                
                JPanel buttonsPanel = new JPanel(new GridLayout(2, 3));
                
                JButton thresholdButton = new JButton("Threshold Selected...");
                
                thresholdButton.addActionListener(new PerformThresholdingForSelectedListener(this));
                
                JButton continueButton = new JButton("Continue");
		JButton previousButton = new JButton("Previous");

		previousButton.addActionListener(new PreviousButtonListener(this));
		continueButton.addActionListener(new ContinueButtonListener(this));
                
                buttonsPanel.add(Box.createGlue());
                buttonsPanel.add(thresholdButton);
                buttonsPanel.add(Box.createGlue());
                
                buttonsPanel.add(previousButton);
		buttonsPanel.add(Box.createGlue());
		buttonsPanel.add(continueButton);
                
                add(stepTitle, BorderLayout.PAGE_START);
                add(new JScrollPane(cleanIdentificationsTable), BorderLayout.CENTER);
                add(buttonsPanel, BorderLayout.PAGE_END);
        }
        
        public CleanIdentificationsTable getCleanIdentificationsTable() {
            return this.cleanIdentificationsTable;
        }
        
        public synchronized void refreshFromData() {
		cleanIdentificationsTable.clear();
		AnalyseData data = AnalyseData.getInstance();
		for (int i = 0; i < data.getRawDataCount(); i++) {
			IdentDataFile identData = data.getRawDataFile(i).getIdentificationDataFile();
                        if (identData.isCleanable()) {
                            cleanIdentificationsTable.addIdentFileRow(identData);
                        }			
		}
	}
}