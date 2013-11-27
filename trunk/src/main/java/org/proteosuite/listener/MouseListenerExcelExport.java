package org.proteosuite.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTable;

import org.proteosuite.fileformat.FileFormatExcel;

/**
 * 
 * @author Andrew Collins
 */
public class MouseListenerExcelExport implements MouseListener {
	private final FileFormatExcel fileExport = new FileFormatExcel();
	private final JTable jTable;

	public MouseListenerExcelExport(JTable jTable) {
		this.jTable = jTable;
	}

	@Override
	public void mouseClicked(MouseEvent mouseEvent) {
		fileExport.export(jTable);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
