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
public class ActionListenerViewerExtraChanged implements ActionListener {
	private JTabbedPane jtpLog;
	private JMenuItem jmLog;
	private JMenuItem jmRawData;
	private int selectedIndex;

	public ActionListenerViewerExtraChanged(final JTabbedPane jtpLog,
			final JMenuItem jmLog, final JMenuItem jmRawData,
			final int selectedIndex) 
	{
		this.jtpLog = jtpLog;
		this.jmLog = jmLog;
		this.jmRawData = jmRawData;

		this.selectedIndex = selectedIndex;
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		jtpLog.setSelectedIndex(selectedIndex);
		
		jmLog.setIcon(null);
		jmRawData.setIcon(null);
		
		JMenuItem actionMenu = (JMenuItem) actionEvent.getSource();
		Icon thick = new ImageIcon(getClass().getClassLoader().getResource(
				"images/thick.gif"));
		actionMenu.setIcon(thick);
	}
}
