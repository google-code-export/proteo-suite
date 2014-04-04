package org.proteosuite.gui.listener;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class RawDataTableListener implements ListSelectionListener,
		TableModelListener {
	private final JTable table;
	private final JButton[] toggleButtons;
	private final JButton clearAllButton;
	private final JButton continueButton;
	
	private JButton deleteSelectedButton;

	public RawDataTableListener(JTable table, JButton... toggleButtons) {
		this.table = table;
		this.toggleButtons = toggleButtons;

		clearAllButton = null;
		continueButton = null;
	}

	public RawDataTableListener(JButton deleteSelectedButton,
			JButton clearAllButton, JButton continueButton) {
		this.deleteSelectedButton = deleteSelectedButton;
		this.clearAllButton = clearAllButton;
		this.continueButton = continueButton;

		toggleButtons = null;
		table = null;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting())
			return;

		DefaultListSelectionModel model = (DefaultListSelectionModel) e
				.getSource();
		
		boolean state = true;

		if (model.getAnchorSelectionIndex() == -1)
			state = false;

		if (table.getRowCount() == 0)
			state  = false;
		
		for (JButton button : toggleButtons)
		{
			button.setEnabled(state);
		}
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		switch (e.getType()) {
		case TableModelEvent.DELETE:
			if (table.getRowCount() == 0) {
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
