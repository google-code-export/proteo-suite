package org.proteosuite.gui.toolbars;

import java.awt.Dimension;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import org.proteosuite.ProteoSuiteView;
import org.proteosuite.gui.TabbedChartViewer;



import uk.ac.ebi.jmzidml.xml.io.MzIdentMLUnmarshaller;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshaller;
import uk.ac.liv.jmzqml.xml.io.MzQuantMLUnmarshaller;

/**
 * 
 * @author Andew Collins
 */
public class ProjectToolBar extends JToolBar {

	public ProjectToolBar(final ProteoSuiteView proteoSuiteView, final JMenuItem jmCloseProject,
			final JMenuItem jmSaveProject,
			final TabbedChartViewer jtpViewer, 
			final JButton jbSaveProject, final JComboBox<String> jcbTechnique,			
			final JLabel jlFileNameMzMLText,
			final JLabel jlFileNameMzIDText, final JLabel jlIdentFilesStatus,
			final JLabel jlFileNameMzQText, final JComboBox<String> jcbOutputFormat, 
			final JLabel jlFileNameMGFText,
			final JComboBox<String> jcbPSM, final JLabel jlRawFilesStatus,
			final List<MzMLUnmarshaller> aMzMLUnmarshaller,
			final List<MzIdentMLUnmarshaller> aMzIDUnmarshaller,
			final List<MzQuantMLUnmarshaller> aMzQUnmarshaller)
	{
		setFloatable(false);
		JButton jbNewProject = new JButton(new ImageIcon(getClass()
				.getClassLoader().getResource("images/new.gif")));
		jbNewProject.setToolTipText("New Project (Ctrl + N)");
		jbNewProject.setFocusable(false);
		jbNewProject.setHorizontalTextPosition(SwingConstants.CENTER);
		jbNewProject.setVerticalTextPosition(SwingConstants.BOTTOM);
		

		add(jbNewProject);

		JButton jbImportFile = new JButton(new ImageIcon(getClass()
				.getClassLoader().getResource("images/import.gif")));
		jbImportFile.setToolTipText("Import File (Ctrl + I)");
		jbImportFile.setFocusable(false);
		jbImportFile.setHorizontalTextPosition(SwingConstants.CENTER);
		jbImportFile.setMaximumSize(new Dimension(27, 21));
		jbImportFile.setMinimumSize(new Dimension(27, 21));
		jbImportFile.setPreferredSize(new Dimension(27, 21));
		jbImportFile.setVerticalTextPosition(SwingConstants.BOTTOM);
		
		add(jbImportFile);
		



		JButton jbOpenProject = new JButton(new ImageIcon(getClass()
				.getClassLoader().getResource("images/open.gif")));
		jbOpenProject.setToolTipText("Open Project (Ctrl + O)");
		jbOpenProject.setFocusable(false);
		jbOpenProject.setHorizontalTextPosition(SwingConstants.CENTER);
		jbOpenProject.setVerticalTextPosition(SwingConstants.BOTTOM);
		
		add(jbOpenProject);

		jbSaveProject.setToolTipText("Save Project (Ctrl + S)");
		jbSaveProject.setEnabled(false);
		jbSaveProject.setFocusable(false);
		jbSaveProject.setHorizontalTextPosition(SwingConstants.CENTER);
		jbSaveProject.setVerticalTextPosition(SwingConstants.BOTTOM);
		
		add(jbSaveProject);
	}
}
