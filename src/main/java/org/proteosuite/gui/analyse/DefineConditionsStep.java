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
	private static final long serialVersionUID = 1L;
	private final DefineConditionsTable conditionsTable = new DefineConditionsTable();

	public DefineConditionsStep() {
		setLayout(new BorderLayout());

		JLabel stepTitle = new JLabel("Define your conditions:");
		stepTitle.setFont(new Font(stepTitle.getFont().getFontName(), stepTitle
				.getFont().getStyle(), 72));


		JButton previousButton = new JButton("Previous");
		JButton continueButton = new JButton("Continue");
		
		previousButton.addActionListener(new PreviousButtonListener(this));
		continueButton.addActionListener(new ContinueButtonListener(this));

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
		buttonsPanel.add(previousButton);
		buttonsPanel.add(continueButton);
		
		add(stepTitle, BorderLayout.PAGE_START);
		add(new JScrollPane(conditionsTable), BorderLayout.CENTER);
		add(buttonsPanel, BorderLayout.PAGE_END);
	}

	public DefineConditionsTable getConditionsTable() {
		return conditionsTable;
	}

	public synchronized void refreshFromData() {
		AnalyseData data = AnalyseData.getInstance();
		conditionsTable.clear();
		for (int i = 0; i < data.getRawDataCount(); i++) {
			RawDataFile file = data.getRawDataFile(i);
			for (String assay : file.getConditions().keySet()) {
				conditionsTable.addConditionRow(
						file.getConditions().get(assay), file.getFileName(),
						assay);
			}
		}
	}

}
