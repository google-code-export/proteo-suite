package org.proteosuite.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JTable;

import org.proteosuite.ProteoSuiteView;
import org.proteosuite.gui.TabbedChartViewer;
import org.proteosuite.gui.TabbedLog;

import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshaller;

/**
 * 
 * @author Andew Collins
 */
public class MouseListenerMzMl implements MouseListener {

	private JTable jtRawFiles;
	private JTable jtRawData;
	private TabbedLog jtpLog;
	private List<MzMLUnmarshaller> aMzMLUnmarshaller;
	private TabbedChartViewer jtpViewer;
	private ProteoSuiteView proteoSuiteView;

	public MouseListenerMzMl(ProteoSuiteView proteoSuiteView,
			JTable jtRawFiles, JTable jtRawData, TabbedLog jtpLog,
			List<MzMLUnmarshaller> aMzMLUnmarshaller,
			TabbedChartViewer jtpViewer) {
		this.jtRawFiles = jtRawFiles;
		this.jtRawData = jtRawData;
		this.jtpLog = jtpLog;
		this.aMzMLUnmarshaller = aMzMLUnmarshaller;
		this.jtpViewer = jtpViewer;
		this.proteoSuiteView = proteoSuiteView;

	}

	@Override
	public void mouseClicked(MouseEvent evt) {
		JTable jtMzML = (JTable) evt.getSource();

		// Right click event for displaying MS raw data and spectrum
		if (evt.getButton() == 1) {
			if (jtRawFiles.getSelectedRow() > -1) {
				proteoSuiteView.showRawData(jtRawFiles.getSelectedRow(), jtMzML
						.getValueAt(jtMzML.getSelectedRow(), 1).toString(),
						jtRawData, jtpLog, aMzMLUnmarshaller);

				jtpViewer.updateSpectrum(
						jtMzML.getValueAt(jtMzML.getSelectedRow(), 1)
								.toString(), aMzMLUnmarshaller.get(0));
			} else {
				proteoSuiteView.showRawData(0,
						jtMzML.getValueAt(jtMzML.getSelectedRow(), 1)
								.toString(), jtRawData, jtpLog,
						aMzMLUnmarshaller);

				jtpViewer.updateSpectrum(
						jtMzML.getValueAt(jtMzML.getSelectedRow(), 1)
								.toString(), aMzMLUnmarshaller.get(0));
			}
			jtpViewer.setSelectedIndex(0);
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}

}
