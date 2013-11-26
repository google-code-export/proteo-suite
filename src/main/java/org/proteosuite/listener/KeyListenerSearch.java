package org.proteosuite.listener;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class KeyListenerSearch implements KeyListener {
	private final int iColumn;
	private final JTable jTable;
	private boolean patternMatch;
	
	public KeyListenerSearch (int iColumn, JTable jTable, boolean patternMatch)
	{
		this.iColumn = iColumn;
		this.jTable = jTable;
		this.patternMatch = patternMatch;
	}

	@Override
	public void keyPressed(KeyEvent evt) {
		if (patternMatch)
		{
			keyPressedPattern(evt);
			return;
		}

		JTextField jtScanTitle = (JTextField) evt.getSource();
		String sChain = "";
		sChain = jtScanTitle.getText() + evt.getKeyChar();
		searchValueInJTable(sChain, iColumn, jTable);
	}

	private void keyPressedPattern(KeyEvent evt) {
		JTextField jtMSIndex = (JTextField) evt.getSource();
		String sChain = "" + evt.getKeyChar();
		Pattern p = Pattern.compile("^[a-zA-Z0-9]*$");
		Matcher m = p.matcher(sChain);
		if (m.find()) {
			sChain = jtMSIndex.getText() + evt.getKeyChar();
			searchValueInJTable(sChain, iColumn, jTable);
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}


	/*-------------------------------------------------------------------------------
	 * Search a value within the raw table and position the cursor accordingly 
	 * @param sChain - Value
	 * @param iColumn - Look up column 
	 * @return void
	 --------------------------------------------------------------------------------*/
	private void searchValueInJTable(String sChain, int iColumn, JTable jtable) {
		DefaultTableModel dtm = (DefaultTableModel) jtable.getModel();
		int nRow = dtm.getRowCount();
		for (int iI = 0; iI < nRow; iI++) {
			if (dtm.getValueAt(iI, iColumn).toString().startsWith(sChain)) {
				jtable.setRowSelectionInterval(iI, iI);
				jtable.scrollRectToVisible(new Rectangle(jtable.getCellRect(iI, 0, true)));
				break;
			}
		}
	}
}
