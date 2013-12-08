package org.proteosuite.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import org.proteosuite.ProteoSuiteView;
import org.proteosuite.fileformat.FileFormatMascot;
import org.proteosuite.gui.TabbedProperties;

public class MouseListenerIdentFiles implements MouseListener {

	private JTable jtMascotXMLView;
	private JTabbedPane jtpProperties;
	private JEditorPane jepMzIDView;
	private JTable jtMzIDProtGroup;
	private JComboBox<String>  jcbPSM;
	private JTable jtMzId;
	private JLabel jlFileNameMzIDText;
	private ProteoSuiteView proteoSuiteView;

	public MouseListenerIdentFiles(ProteoSuiteView proteoSuiteView, JTable jtMascotXMLView,
			TabbedProperties jtpProperties, JEditorPane jepMzIDView,
			JTable jtMzIDProtGroup, JComboBox<String> jcbPSM, JTable jtMzId,
			JLabel jlFileNameMzIDText) {
		this.proteoSuiteView = proteoSuiteView;
		this.jtMascotXMLView = jtMascotXMLView;
		this.jtpProperties = jtpProperties;
		this.jepMzIDView = jepMzIDView;
		this.jtMzIDProtGroup = jtMzIDProtGroup;
		this.jcbPSM = jcbPSM;
		this.jtMzId = jtMzId;
		this.jlFileNameMzIDText = jlFileNameMzIDText;
	}

	@Override
	public void mouseClicked(MouseEvent evt) {
		JTable jtIdentFiles = (JTable) evt.getSource();
		if ((evt.getButton() == 1)
				&& (jtIdentFiles.getValueAt(
						jtIdentFiles.getSelectedRow(), 2).toString()
						.equals("mascot_xml"))) {
			Runnable fileFormatMascot = new FileFormatMascot(
					jtIdentFiles.getValueAt(
							jtIdentFiles.getSelectedRow(), 0)
							.toString(),
					jtIdentFiles.getValueAt(
							jtIdentFiles.getSelectedRow(), 1)
							.toString(), jtMascotXMLView, jtpProperties);
			fileFormatMascot.run();
		}
		if ((evt.getButton() == 1)
				&& (jtIdentFiles.getValueAt(
						jtIdentFiles.getSelectedRow(), 2).toString()
						.equals("mzid"))) {
			proteoSuiteView.loadMzIdentMLView(
					jtIdentFiles.getSelectedRow(),
					jtIdentFiles.getValueAt(
							jtIdentFiles.getSelectedRow(), 0)
							.toString(), jtMzIDProtGroup,
					jtpProperties, jcbPSM, jepMzIDView,
					jlFileNameMzIDText, jtMzId);
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
