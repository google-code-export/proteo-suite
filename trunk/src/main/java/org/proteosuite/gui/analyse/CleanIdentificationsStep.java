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
import org.proteosuite.gui.listener.PreviousButtonListener;
import org.proteosuite.gui.tables.CleanIdentificationsTable;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.IdentDataFile;
import org.proteosuite.model.RawDataFile;

/**
 * 
 * @author SPerkins
 */
public class CleanIdentificationsStep extends JPanel {
	private static final long serialVersionUID = 1L;
	private CleanIdentificationsTable cleanIdentificationsTable = new CleanIdentificationsTable();

	public CleanIdentificationsStep() {
            super(new BorderLayout());
            
            JLabel stepTitle = new JLabel("Clean up your identifications:");
		stepTitle.setFont(new Font(stepTitle.getFont().getFontName(), stepTitle
				.getFont().getStyle(), 72));
                
                JPanel buttonsPanel = new JPanel(new GridLayout(1, 3));
                
                JButton continueButton = new JButton("Continue");
		JButton previousButton = new JButton("Previous");

		previousButton.addActionListener(new PreviousButtonListener(this));
		continueButton.addActionListener(new ContinueButtonListener(this));
                
                buttonsPanel.add(previousButton);
		buttonsPanel.add(Box.createGlue());
		buttonsPanel.add(continueButton);
                
                add(stepTitle, BorderLayout.PAGE_START);
                add(new JScrollPane(cleanIdentificationsTable), BorderLayout.CENTER);
                add(buttonsPanel, BorderLayout.PAGE_END);
        }
        
        public synchronized void refreshFromData() {
		cleanIdentificationsTable.clear();
		AnalyseData data = AnalyseData.getInstance();
		for (int i = 0; i < data.getRawDataCount(); i++) {
			IdentDataFile identData = data.getRawDataFile(i).getIdentificationDataFile();
			cleanIdentificationsTable.addIdentFileRow(identData);
		}
	}
}
