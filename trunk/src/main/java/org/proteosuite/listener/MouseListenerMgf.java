package org.proteosuite.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTable;

import org.proteosuite.ProteoSuiteView;
import org.proteosuite.gui.TabbedChartViewer;
import org.proteosuite.gui.TabbedLog;

/**
 * 
 * @author Andrew Collins
 */
public class MouseListenerMgf implements MouseListener {

	private JTable jtRawFiles;
	private JTable jtRawData;
	private TabbedLog jtpLog;
	private TabbedChartViewer jtpViewer;
	private ProteoSuiteView proteoSuiteView;

	public MouseListenerMgf(ProteoSuiteView proteoSuiteView, JTable jtRawFiles,
			JTable jtRawData, TabbedLog jtpLog, TabbedChartViewer jtpViewer) {
		this.jtRawFiles = jtRawFiles;
		this.jtRawData = jtRawData;
		this.jtpLog = jtpLog;
		this.jtpViewer = jtpViewer;
		this.proteoSuiteView = proteoSuiteView;
	}

	@Override
	public void mouseClicked(MouseEvent evt) {
		JTable jtMGF = (JTable) evt.getSource();

		// ... Validate right click selection for MGF files ...//
		if (evt.getButton() == 1) {
			if (jtRawFiles.getSelectedRow() > -1) {
				proteoSuiteView.showRawDataMGF(jtRawFiles.getSelectedRow(),
						jtMGF.getValueAt(jtMGF.getSelectedRow(), 4).toString(),
						jtRawData, jtRawFiles, jtpLog);
			} else {
				proteoSuiteView.showRawDataMGF(0,
						jtMGF.getValueAt(jtMGF.getSelectedRow(), 4).toString(),
						jtRawData, jtRawFiles, jtpLog);
			}
			jtpViewer.setSelectedIndex(0);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
