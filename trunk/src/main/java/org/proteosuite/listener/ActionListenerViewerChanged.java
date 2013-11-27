package org.proteosuite.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;

/*
 * 
 * @author acollins
 *
 */
public class ActionListenerViewerChanged implements ActionListener {
	private final JTabbedPane jtpViewer;

	private final JMenuItem jmSpectrum;
	private final JMenuItem jmTIC;
	private final JMenuItem jm2DView;
	private final JMenuItem jm3DView;

	private int selectIndex;

	public ActionListenerViewerChanged(final JTabbedPane jtpViewer,
			final JMenuItem jmSpectrum, final JMenuItem jmTIC,
			final JMenuItem jm2DView, final JMenuItem jm3DView, final int selectedIndex) {
		this.jtpViewer = jtpViewer;

		this.jmSpectrum = jmSpectrum;
		this.jmTIC = jmTIC;
		this.jm2DView = jm2DView;
		this.jm3DView = jm3DView;
		
		this.selectIndex = selectedIndex;
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		jtpViewer.setSelectedIndex(selectIndex);
		
		jmSpectrum.setIcon(null);
		jmTIC.setIcon(null);
		jm2DView.setIcon(null);
		jm3DView.setIcon(null);

		JMenuItem actionMenu = (JMenuItem) actionEvent.getSource();
		Icon thick = new ImageIcon(getClass().getClassLoader().getResource(
				"images/thick.gif"));
		actionMenu.setIcon(thick);
	}

}
