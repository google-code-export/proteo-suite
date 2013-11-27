package org.proteosuite.listener;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;

import org.proteosuite.ProteoSuiteView;
import org.proteosuite.gui.QuantParamsView;

public class ActionListenerEditQuant implements ActionListener {
	private final JTable jtRawFiles;
	private final JComboBox<String> jcbTechnique;

	public ActionListenerEditQuant(final JTable jtRawFiles, final JComboBox<String> jcbTechnique)
	{
		this.jtRawFiles = jtRawFiles;
		this.jcbTechnique = jcbTechnique;
	}
	
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		if (jcbTechnique.getSelectedItem().equals("Select technique")) {
			JOptionPane
					.showMessageDialog(
							null,
							"Please specify the technique used in your pipeline (e.g. iTRAQ, TMT, etc.)",
							"Error", JOptionPane.ERROR_MESSAGE);
		} else {
			List<String> alRawFiles = new ArrayList<String>();
			for (int iI = 0; iI < jtRawFiles.getRowCount(); iI++) {
				alRawFiles.add((String) jtRawFiles.getValueAt(iI, 0));
			}
			QuantParamsView quantParams = new QuantParamsView(alRawFiles,
					ProteoSuiteView.sWorkspace, jcbTechnique.getSelectedItem().toString());
			final JFrame jfQuantParams = new JFrame(
					"Edit Quantitation Parameters");
			jfQuantParams.setResizable(false);
			jfQuantParams.setSize(638, 585);
			KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(
					KeyEvent.VK_ESCAPE, 0, false);
			final Action escapeAction = new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					jfQuantParams.dispose();
				}
			};
			jfQuantParams.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					escapeAction.actionPerformed(null);
				}
			});

			jfQuantParams.getRootPane()
					.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
					.put(escapeKeyStroke, "ESCAPE");
			jfQuantParams.getRootPane().getActionMap()
					.put("ESCAPE", escapeAction);

			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			int x1 = dim.width / 2;
			int y1 = dim.height / 2;
			int x2 = jfQuantParams.getSize().width / 2;
			int y2 = jfQuantParams.getSize().height / 2;
			jfQuantParams.setLocation(x1 - x2, y1 - y2);
			Image iconApp = new ImageIcon(getClass().getClassLoader()
					.getResource("images/icon.gif")).getImage();
			jfQuantParams.setIconImage(iconApp);
			jfQuantParams.setAlwaysOnTop(true);

			jfQuantParams.add(quantParams);
			jfQuantParams.pack();
			jfQuantParams.setVisible(true);
		}
	}
}
