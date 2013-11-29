package org.proteosuite.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JTable;

import org.proteosuite.ProteoSuiteView;
import org.proteosuite.gui.TabbedChartViewer;
import org.proteosuite.gui.TabbedProperties;

import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshaller;

public class MouseListenerRawFiles implements MouseListener {

	private TabbedProperties jtpProperties;
	private JEditorPane jepMzMLView;
	private JLabel jlFileNameMzMLText;
	private JTable jtMzML;
	private List<MzMLUnmarshaller> aMzMLUnmarshaller;
	private JLabel jlFileNameMGFText;
	private JTable jtMGF;
	private TabbedChartViewer jtpViewer;
	private ProteoSuiteView proteoSuiteView;

	public MouseListenerRawFiles(ProteoSuiteView proteoSuiteView,
			TabbedProperties jtpProperties, JEditorPane jepMzMLView,
			JLabel jlFileNameMzMLText, JTable jtMzML,
			List<MzMLUnmarshaller> aMzMLUnmarshaller,
			JLabel jlFileNameMGFText, JTable jtMGF,
			TabbedChartViewer jtpViewer) {
		this.proteoSuiteView = proteoSuiteView;
		this.jtpProperties = jtpProperties;
		this.jepMzMLView = jepMzMLView;
		this.jlFileNameMzMLText = jlFileNameMzMLText;
		this.jtMzML = jtMzML;
		this.aMzMLUnmarshaller = aMzMLUnmarshaller;
		this.jlFileNameMGFText = jlFileNameMGFText;
		this.jtMGF = jtMGF;
		this.jtpViewer = jtpViewer;

	}

	@Override
	public void mouseClicked(MouseEvent evt) {
		JTable jtRawFiles = (JTable) evt.getSource();

		// ... Right click event for displaying mzML data ...//
		if ((evt.getButton() == 1)
				&& (jtRawFiles.getValueAt(jtRawFiles.getSelectedRow(),
						2).toString().equals("mzML"))) {
			proteoSuiteView.loadMzMLView(jtRawFiles.getSelectedRow(), jtRawFiles,
					jtpProperties, jepMzMLView, jlFileNameMzMLText,
					jtMzML);

			// Clear container
			jtpViewer.updateChromatogram(
					(String) jtRawFiles.getValueAt(
							jtRawFiles.getSelectedRow(), 1),
					aMzMLUnmarshaller.get(jtRawFiles.getSelectedRow()));
		}
		// ... Right click event for displaying MGF data ...//
		if ((evt.getButton() == 1)
				&& (jtRawFiles.getValueAt(jtRawFiles.getSelectedRow(),
						2).toString().equals("MGF"))) {
			proteoSuiteView.loadMGFView(jtRawFiles.getSelectedRow(), jlFileNameMGFText,
					jtRawFiles, jtpProperties, jtMGF);
		}
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
