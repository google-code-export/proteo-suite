package org.proteosuite.gui.listener;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.proteosuite.gui.tables.RawDataAndMultiplexingTable;

public class RawDataTableListener implements ListSelectionListener,
		TableModelListener {
	private final RawDataAndMultiplexingTable rawDataTable;
	private final JButton deleteSelectedButton;
	private final JButton clearAllButton;
	private final JButton continueButton;

	public RawDataTableListener(RawDataAndMultiplexingTable rawDataTable,
			JButton deleteSelectedButton) {
		this.rawDataTable = rawDataTable;
		this.deleteSelectedButton = deleteSelectedButton;

		clearAllButton = null;
		continueButton = null;
	}

	public RawDataTableListener(JButton deleteSelectedButton,
			JButton clearAllButton, JButton continueButton) {
		this.deleteSelectedButton = deleteSelectedButton;
		this.clearAllButton = clearAllButton;
		this.continueButton = continueButton;

		rawDataTable = null;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting())
			return;

		DefaultListSelectionModel model = (DefaultListSelectionModel) e
				.getSource();

		if (model.getAnchorSelectionIndex() == -1)
			deleteSelectedButton.setEnabled(false);
		else
			deleteSelectedButton.setEnabled(true);

		if (rawDataTable.getRowCount() == 0)
			deleteSelectedButton.setEnabled(false);
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		switch (e.getType()) {
		case TableModelEvent.DELETE:
			if (rawDataTable.getRowCount() == 0) {
				clearAllButton.setEnabled(false);
				deleteSelectedButton.setEnabled(false);
				continueButton.setEnabled(false);
			}
			break;
		case TableModelEvent.INSERT:
			continueButton.setEnabled(true);
			clearAllButton.setEnabled(true);
			break;
		}
	}

}
