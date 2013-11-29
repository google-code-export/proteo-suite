package org.proteosuite.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import org.proteosuite.ProteoSuiteView;
import org.proteosuite.gui.TabbedLog;
import org.proteosuite.gui.TabbedProperties;

/**
 * 
 * @author Andrew Collins
 */
public class MouseListenerQuantFiles implements MouseListener {

	private JTable jtFeatureQuant;
	private TabbedLog jtpLog;
	private TabbedProperties jtpProperties;
	private JTabbedPane jtpMzQuantMLDetail;
	private JEditorPane jepMZQView;
	private JLabel jlFileNameMzQText;
	private JTable jtPeptideQuant;
	private JTable jtProteinQuant;
	private ProteoSuiteView proteoSuiteView;

	public MouseListenerQuantFiles(ProteoSuiteView proteoSuiteView,
			JTable jtFeatureQuant, TabbedLog jtpLog,
			TabbedProperties jtpProperties, JTabbedPane jtpMzQuantMLDetail,
			JEditorPane jepMZQView, JLabel jlFileNameMzQText,
			JTable jtPeptideQuant, JTable jtProteinQuant, JTable jtQuantFiles) {

		this.jtFeatureQuant = jtFeatureQuant;
		this.jtpLog = jtpLog;
		this.jtpProperties = jtpProperties;
		this.jtpMzQuantMLDetail = jtpMzQuantMLDetail;
		this.jepMZQView = jepMZQView;
		this.jlFileNameMzQText = jlFileNameMzQText;
		this.jtPeptideQuant = jtPeptideQuant;
		this.jtProteinQuant = jtProteinQuant;
		this.proteoSuiteView = proteoSuiteView;
		
	}

	@Override
	public void mouseClicked(MouseEvent evt) {
		JTable jtQuantFiles = (JTable) evt.getSource();

		if ((evt.getButton() == 1)
				&& (jtQuantFiles.getValueAt(
						jtQuantFiles.getSelectedRow(), 2).toString()
						.equals("mzq"))) {
			proteoSuiteView.loadMzQuantMLView(
					jtQuantFiles.getSelectedRow(),
					jtQuantFiles.getValueAt(
							jtQuantFiles.getSelectedRow(), 1)
							.toString(), jtFeatureQuant, jtpLog,
					jtpProperties, jtpMzQuantMLDetail, jepMZQView,
					jlFileNameMzQText, jtPeptideQuant, jtProteinQuant,
					jtQuantFiles);
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
