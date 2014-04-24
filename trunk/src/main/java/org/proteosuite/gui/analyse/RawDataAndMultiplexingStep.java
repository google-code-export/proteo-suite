package org.proteosuite.gui.analyse;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.proteosuite.gui.listener.AddRawDataListener;
import org.proteosuite.gui.listener.ClearAllRawFileButtonListener;
import org.proteosuite.gui.listener.ContinueButtonListener;
import org.proteosuite.gui.listener.DeleteSelectedRawFileButtonListener;
import org.proteosuite.gui.listener.RawDataTableListener;
import org.proteosuite.gui.tables.RawDataAndMultiplexingTable;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.RawDataFile;
import org.proteosuite.quantitation.OpenMSLabelFreeWrapper;

/**
 * 
 * @author SPerkins
 */
public class RawDataAndMultiplexingStep extends JPanel {
	private static final long serialVersionUID = 1L;

	private RawDataAndMultiplexingTable rawDataTable = new RawDataAndMultiplexingTable();
	private JComboBox<String> multiplexingBox = new JComboBox<String>();        

	public RawDataAndMultiplexingStep() {
            
		super(new BorderLayout());
		JLabel stepTitle = new JLabel("Select your raw data and multiplexing:");
		stepTitle.setFont(new Font(stepTitle.getFont().getFontName(), stepTitle
				.getFont().getStyle(), 72));

		final JButton clearAllButton = new JButton("Clear All");
		final JButton continueButton = new JButton("Continue");
		final JButton deleteSelectedButton = new JButton("Delete Selected");
		JButton addRawDataButton = new JButton("Add Data Files...");

		clearAllButton.setEnabled(false);
		continueButton.setEnabled(false);
		deleteSelectedButton.setEnabled(false);

		addRawDataButton.addActionListener(new AddRawDataListener(this));
		deleteSelectedButton
				.addActionListener(new DeleteSelectedRawFileButtonListener(this));
		clearAllButton
				.addActionListener(new ClearAllRawFileButtonListener(this));
		continueButton.addActionListener(new ContinueButtonListener(this));
		rawDataTable.getSelectionModel().addListSelectionListener(
				new RawDataTableListener(rawDataTable, deleteSelectedButton));
		rawDataTable.getModel().addTableModelListener(
				new RawDataTableListener(deleteSelectedButton, clearAllButton,
						continueButton));
                
                multiplexingBox.addItem("iTRAQ 4-plex");
                multiplexingBox.addItem("iTRAQ 8-plex");
                if (OpenMSLabelFreeWrapper.checkIsInstalled()) {
                    multiplexingBox.addItem("None (label-free)");
                    multiplexingBox.setSelectedIndex(2);                    
                } else {
                    multiplexingBox.setSelectedIndex(0);
                }		

		JPanel buttonsPanel = new JPanel(new GridLayout(2, 3));

		buttonsPanel.add(addRawDataButton);
		buttonsPanel.add(new JLabel("Select multiplexing:"));
		buttonsPanel.add(clearAllButton);
		
		buttonsPanel.add(deleteSelectedButton);
		buttonsPanel.add(multiplexingBox);		
		buttonsPanel.add(continueButton);

		add(stepTitle, BorderLayout.PAGE_START);
		add(new JScrollPane(rawDataTable), BorderLayout.CENTER);
		add(buttonsPanel, BorderLayout.PAGE_END);
	}

	public void refreshFromData() {
		AnalyseData data = AnalyseData.getInstance();
		rawDataTable.clear();
		for (int i = 0; i < data.getRawDataCount(); i++) {
			RawDataFile dataFile = data.getRawDataFile(i);
			rawDataTable.addRawFileRow(dataFile);
		}
	}

	public RawDataAndMultiplexingTable getRawDataTable() {
		return rawDataTable;
	}

	public JComboBox<String> getMultiplexingBox() {
		return multiplexingBox;
	}
}