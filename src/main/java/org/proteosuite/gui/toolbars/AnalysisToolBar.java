package org.proteosuite.gui.toolbars;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import org.proteosuite.ProteoSuiteView;
import org.proteosuite.gui.IdentParamsView;
import org.proteosuite.gui.TabbedLog;
import org.proteosuite.gui.TabbedProperties;

import uk.ac.ebi.jmzidml.xml.io.MzIdentMLUnmarshaller;

public class AnalysisToolBar extends JToolBar {
	
	public AnalysisToolBar(final ProteoSuiteView proteoSuiteView, final JMenuItem jmCloseProject,
			final JMenuItem jmSaveProject,
			final TabbedProperties jtpProperties, final TabbedLog jtpLog,
			final JButton jbSaveProject, final JComboBox<String> jcbTechnique,
			final JEditorPane jepMZQView,
			final JEditorPane jepMzIDView,
			final JLabel jlFileNameMzIDText, final JLabel jlIdentFilesStatus,
			final JLabel jlFileNameMzQText, final JTable jtRawFiles,
			final JTable jtIdentFiles, final JTable jtQuantFiles,
			final JTable jtMzId,
			final JTable jtPeptideQuant, final JTable jtProteinQuant,
			final JTable jtFeatureQuant, final JComboBox<String> jcbOutputFormat, 
			final JTable jtMzIDProtGroup, final JComboBox<String> jcbPSM, final JLabel jlRawFilesStatus, final IdentParamsView identParamsExecute, 
			final List<MzIdentMLUnmarshaller> aMzIDUnmarshaller)
	{
		setMaximumSize(new Dimension(34, 9));
		setMinimumSize(new Dimension(34, 9));
		setFloatable(false);
		


		JButton jbRunIdentAnalysis = new JButton(new ImageIcon(getClass()
				.getClassLoader().getResource("images/runid.gif")));
		jbRunIdentAnalysis.setToolTipText("Run Identification Pipeline (F7)");
		jbRunIdentAnalysis.setFocusable(false);
		jbRunIdentAnalysis.setHorizontalTextPosition(SwingConstants.CENTER);
		jbRunIdentAnalysis.setVerticalTextPosition(SwingConstants.BOTTOM);
		jbRunIdentAnalysis.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				proteoSuiteView.jmRunIdentAnalysisActionPerformed(jtRawFiles,
						jtMzIDProtGroup, identParamsExecute, jtpLog,
						jtIdentFiles, aMzIDUnmarshaller, jlRawFilesStatus,
						jlIdentFilesStatus, jtpProperties, jcbPSM, jepMzIDView,
						jlFileNameMzIDText, jtMzId);
			}
		});
		add(jbRunIdentAnalysis);
		

		JButton jbRunQuantAnalysis = new JButton(new ImageIcon(getClass()
				.getClassLoader().getResource("images/run.gif")));
		jbRunQuantAnalysis.setToolTipText("Run Quantitation Pipeline (F5)");
		jbRunQuantAnalysis.setFocusable(false);
		jbRunQuantAnalysis.setHorizontalTextPosition(SwingConstants.CENTER);
		jbRunQuantAnalysis.setVerticalTextPosition(SwingConstants.BOTTOM);
		jbRunQuantAnalysis.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				proteoSuiteView.jmRunQuantAnalysisActionPerformed(jtpLog, jmCloseProject,
						jmSaveProject, jtpProperties,
						jbSaveProject, jcbTechnique, jcbOutputFormat, jepMZQView,
						jlFileNameMzQText, jtFeatureQuant, jtIdentFiles,
						jtPeptideQuant, jtProteinQuant, jtQuantFiles,
						jtRawFiles);
			}
		});
		add(jbRunQuantAnalysis);
	}
}
