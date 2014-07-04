package org.proteosuite.gui.listener;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class RawDataTableListener implements TableModelListener {
	private final JTable table;
	private final JButton continueButton;	

	public RawDataTableListener(JTable table, JButton continueButton) {
		this.continueButton = continueButton;

		this.table = table;
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		switch (e.getType()) {
		case TableModelEvent.DELETE:
			if (table.getRowCount() == 0) {
				continueButton.setEnabled(false);
			}
			break;
		case TableModelEvent.INSERT:
			continueButton.setEnabled(true);
			break;
		}
	}

}
