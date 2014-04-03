package org.proteosuite.gui.analyse;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.Box;
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
import org.proteosuite.gui.listener.RestartButtonListener;
import org.proteosuite.gui.tables.RawDataAndMultiplexingTable;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.RawDataFile;

/**
 * 
 * @author SPerkins
 */
public class RawDataAndMultiplexingStep extends JPanel {
	private static final long serialVersionUID = 1L;

	private RawDataAndMultiplexingTable rawDataTable = new RawDataAndMultiplexingTable();
	private JComboBox<String> multiplexingBox = new JComboBox<String>(
			new String[] { "iTRAQ 4-plex", "None (label-free)" });

	public RawDataAndMultiplexingStep() {
		JLabel stepTitle = new JLabel("Select your raw data and multiplexing:");
		stepTitle.setFont(new Font(stepTitle.getFont().getFontName(), stepTitle
				.getFont().getStyle(), 72));

		final JButton clearAllButton = new JButton("Clear All");
		final JButton continueButton = new JButton("Continue");
		final JButton deleteSelectedButton = new JButton("Delete Selected");
		final JButton restartButton = new JButton("Restart");
		JButton addRawDataButton = new JButton("Add Data Files...");

		clearAllButton.setEnabled(false);
		continueButton.setEnabled(false);
		deleteSelectedButton.setEnabled(false);

		addRawDataButton.addActionListener(new AddRawDataListener(this));
		deleteSelectedButton
				.addActionListener(new DeleteSelectedRawFileButtonListener(this));
		clearAllButton
				.addActionListener(new ClearAllRawFileButtonListener(this));
		restartButton.addActionListener(new RestartButtonListener(this));
		continueButton.addActionListener(new ContinueButtonListener(this));
		rawDataTable.getSelectionModel().addListSelectionListener(
				new RawDataTableListener(rawDataTable, deleteSelectedButton));
		rawDataTable.getModel().addTableModelListener(
				new RawDataTableListener(deleteSelectedButton, clearAllButton,
						continueButton, restartButton));

		multiplexingBox.setSelectedIndex(1);

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridLayout(3, 3));

		buttonsPanel.add(addRawDataButton);
		buttonsPanel.add(deleteSelectedButton);
		buttonsPanel.add(clearAllButton);

		buttonsPanel.add(restartButton);
		buttonsPanel.add(Box.createGlue());
		buttonsPanel.add(continueButton);

		buttonsPanel.add(new JLabel("Select multiplexing:"));
		buttonsPanel.add(multiplexingBox);

		setLayout(new BorderLayout());
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