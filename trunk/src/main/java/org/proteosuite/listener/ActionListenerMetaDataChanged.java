package org.proteosuite.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;

/**
 * 
 * @author Andrew Collins
 *
 */
public class ActionListenerMetaDataChanged implements ActionListener {

	private JTabbedPane jtpProperties;
	private JMenuItem jmMzMLView;
	private JMenuItem jmMGFView;
	private JMenuItem jmMzIdentMLView;
	private JMenuItem jmMascotXMLView;
	private JMenuItem jmMzQuantMLView;
	private int selectedIndex;

	public ActionListenerMetaDataChanged(final JTabbedPane jtpProperties,
			final JMenuItem jmMzMLView, final JMenuItem jmMGFView,
			final JMenuItem jmMzIdentMLView, final JMenuItem jmMascotXMLView,
			final JMenuItem jmMzQuantMLView, final int selectedIndex) 
	{
		this.jtpProperties = jtpProperties;
		this.jmMzMLView = jmMzMLView;
		this.jmMGFView = jmMGFView;
		this.jmMzIdentMLView = jmMzIdentMLView;
		this.jmMascotXMLView = jmMascotXMLView;
		this.jmMzQuantMLView = jmMzQuantMLView;
		this.selectedIndex = selectedIndex;
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		jtpProperties.setSelectedIndex(selectedIndex);
		
		jmMzMLView.setIcon(null);
		jmMGFView.setIcon(null);
		jmMzIdentMLView.setIcon(null);
		jmMascotXMLView.setIcon(null);
		jmMzQuantMLView.setIcon(null);
		
		JMenuItem actionMenu = (JMenuItem) actionEvent.getSource();
		Icon thick = new ImageIcon(getClass().getClassLoader().getResource(
				"images/thick.gif"));
		actionMenu.setIcon(thick);
	}

}
