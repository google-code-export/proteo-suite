package org.proteosuite.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.EtchedBorder;

import org.proteosuite.listener.KeyListenerSearch;
import org.proteosuite.listener.MouseListenerExcelExport;

/**
 * 
 * @author Andrew collins
 */
public class TabbedMzQuantMLDetail extends JTabbedPane {

	public TabbedMzQuantMLDetail(JTable jtPeptideQuant, JTable jtProteinQuant,
			JTable jtFeatureQuant, JButton jbExportPepMZQExcel,
			JButton jbExportProtMZQExcel, JButton jbExportFeatMZQExcel) {
		addTab("Peptide Quantitation",
				getJPanelQuant(jtPeptideQuant, jbExportPepMZQExcel));
		addTab("Protein Quantitation",
				getJPanelProteinQuant(jtProteinQuant, jbExportProtMZQExcel));
		addTab("Feature Quantitation",
				getFeatureQuantPanel(jtFeatureQuant, jbExportFeatMZQExcel));
	}

	private Component getFeatureQuantPanel(JTable jtFeatureQuant,
			JButton jbExportFeatMZQExcel) {

		JToolBar jtbFeatMZQ = new JToolBar();
		jtbFeatMZQ.setBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.RAISED));
		jtbFeatMZQ.setFloatable(false);
		jtbFeatMZQ.setMaximumSize(new Dimension(30, 23));
		jtbFeatMZQ.setMinimumSize(new Dimension(30, 23));
		jtbFeatMZQ.setPreferredSize(new Dimension(100, 23));

		jbExportFeatMZQExcel.setIconTextGap(1);
		jbExportFeatMZQExcel.setMaximumSize(new Dimension(26, 20));
		jbExportFeatMZQExcel.setMinimumSize(new Dimension(26, 20));
		jbExportFeatMZQExcel.setPreferredSize(new Dimension(26, 20));
		jbExportFeatMZQExcel.addMouseListener(new MouseListenerExcelExport(
				jtFeatureQuant));
		jtbFeatMZQ.add(jbExportFeatMZQExcel);

		final JTextField jtFeatureMZQ = new JTextField();
		jtFeatureMZQ.setToolTipText("Enter the scan number");
		jtFeatureMZQ.addKeyListener(new KeyListenerSearch(0, jtFeatureQuant,
				false));

		JLabel jlSearchMzQFeat = new JLabel("Search:");
		jlSearchMzQFeat.setFont(new Font("Tahoma", 1, 11)); // NOI18N

		JLabel jlFeatureMZQ = new JLabel("Feature:");

		JPanel jpFeatureQuantHeader = new JPanel();
		GroupLayout jpFeatureQuantHeaderLayout = new GroupLayout(
				jpFeatureQuantHeader);
		jpFeatureQuantHeader.setLayout(jpFeatureQuantHeaderLayout);
		jpFeatureQuantHeaderLayout
				.setHorizontalGroup(jpFeatureQuantHeaderLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpFeatureQuantHeaderLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(jlSearchMzQFeat)
										.addGap(18, 18, 18)
										.addComponent(jlFeatureMZQ)
										.addGap(18, 18, 18)
										.addComponent(jtFeatureMZQ,
												GroupLayout.PREFERRED_SIZE,
												209, GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18)
										.addComponent(jtbFeatMZQ,
												GroupLayout.DEFAULT_SIZE, 191,
												Short.MAX_VALUE)
										.addContainerGap()));
		jpFeatureQuantHeaderLayout
				.setVerticalGroup(jpFeatureQuantHeaderLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpFeatureQuantHeaderLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpFeatureQuantHeaderLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addComponent(
																jtbFeatMZQ,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addGroup(
																jpFeatureQuantHeaderLayout
																		.createParallelGroup(
																				GroupLayout.Alignment.BASELINE)
																		.addComponent(
																				jlSearchMzQFeat)
																		.addComponent(
																				jlFeatureMZQ)
																		.addComponent(
																				jtFeatureMZQ,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)))
										.addContainerGap()));

		JScrollPane jspFeatureQuantDetail = new JScrollPane();
		jspFeatureQuantDetail.setViewportView(jtFeatureQuant);

		JSplitPane jspFeatureQuant = new JSplitPane();
		jspFeatureQuant.setDividerLocation(40);
		jspFeatureQuant.setDividerSize(1);
		jspFeatureQuant.setOrientation(JSplitPane.VERTICAL_SPLIT);
		jspFeatureQuant.setTopComponent(jpFeatureQuantHeader);
		jspFeatureQuant.setRightComponent(jspFeatureQuantDetail);

		final JPanel FeatureQuant = new JPanel();
		GroupLayout FeatureQuantLayout = new GroupLayout(FeatureQuant);
		FeatureQuant.setLayout(FeatureQuantLayout);
		FeatureQuantLayout.setHorizontalGroup(FeatureQuantLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspFeatureQuant, GroupLayout.Alignment.TRAILING));
		FeatureQuantLayout.setVerticalGroup(FeatureQuantLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspFeatureQuant, GroupLayout.DEFAULT_SIZE, 445,
						Short.MAX_VALUE));
		return null;
	}

	private JPanel getJPanelQuant(JTable jtPeptideQuant,
			JButton jbExportPepMZQExcel) {
		jbExportPepMZQExcel.setIconTextGap(1);
		jbExportPepMZQExcel.setMaximumSize(new Dimension(26, 20));
		jbExportPepMZQExcel.setMinimumSize(new Dimension(26, 20));
		jbExportPepMZQExcel.setPreferredSize(new Dimension(26, 20));
		jbExportPepMZQExcel.addMouseListener(new MouseListenerExcelExport(
				jtPeptideQuant));

		JToolBar jtbPepMZQ = new JToolBar();
		jtbPepMZQ.setBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.RAISED));
		jtbPepMZQ.setFloatable(false);
		jtbPepMZQ.setMaximumSize(new Dimension(30, 23));
		jtbPepMZQ.setMinimumSize(new Dimension(30, 23));
		jtbPepMZQ.setPreferredSize(new Dimension(100, 23));
		jtbPepMZQ.add(jbExportPepMZQExcel);

		final JTextField jtPeptideMZQ = new JTextField();
		jtPeptideMZQ.setToolTipText("Enter the scan number");
		jtPeptideMZQ.addKeyListener(new KeyListenerSearch(0, jtPeptideQuant,
				false));

		JLabel jlSearchMzQPep = new JLabel("Search:");
		jlSearchMzQPep.setFont(new Font("Tahoma", 1, 11)); // NOI18N

		JLabel jlPeptideMZQ = new JLabel("Peptide:");

		JPanel jpPeptideQuantHeader = new JPanel();
		GroupLayout jpPeptideQuantHeaderLayout = new GroupLayout(
				jpPeptideQuantHeader);
		jpPeptideQuantHeader.setLayout(jpPeptideQuantHeaderLayout);
		jpPeptideQuantHeaderLayout
				.setHorizontalGroup(jpPeptideQuantHeaderLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpPeptideQuantHeaderLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(jlSearchMzQPep)
										.addGap(18, 18, 18)
										.addComponent(jlPeptideMZQ)
										.addGap(18, 18, 18)
										.addComponent(jtPeptideMZQ,
												GroupLayout.PREFERRED_SIZE,
												209, GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18)
										.addComponent(jtbPepMZQ,
												GroupLayout.DEFAULT_SIZE, 193,
												Short.MAX_VALUE)
										.addContainerGap()));
		jpPeptideQuantHeaderLayout
				.setVerticalGroup(jpPeptideQuantHeaderLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpPeptideQuantHeaderLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpPeptideQuantHeaderLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addComponent(
																jtbPepMZQ,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addGroup(
																jpPeptideQuantHeaderLayout
																		.createParallelGroup(
																				GroupLayout.Alignment.BASELINE)
																		.addComponent(
																				jlSearchMzQPep)
																		.addComponent(
																				jlPeptideMZQ)
																		.addComponent(
																				jtPeptideMZQ,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)))
										.addContainerGap()));

		JScrollPane jspPeptideQuantDetail = new JScrollPane();
		jspPeptideQuantDetail.setViewportView(jtPeptideQuant);

		JSplitPane jspPeptideQuant = new JSplitPane();
		jspPeptideQuant.setDividerLocation(40);
		jspPeptideQuant.setDividerSize(1);
		jspPeptideQuant.setOrientation(JSplitPane.VERTICAL_SPLIT);
		jspPeptideQuant.setTopComponent(jpPeptideQuantHeader);
		jspPeptideQuant.setRightComponent(jspPeptideQuantDetail);

		JPanel jpPeptideQuant = new JPanel();

		GroupLayout jpPeptideQuantLayout = new GroupLayout(jpPeptideQuant);
		jpPeptideQuant.setLayout(jpPeptideQuantLayout);
		jpPeptideQuantLayout.setHorizontalGroup(jpPeptideQuantLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspPeptideQuant));
		jpPeptideQuantLayout.setVerticalGroup(jpPeptideQuantLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspPeptideQuant, GroupLayout.DEFAULT_SIZE, 445,
						Short.MAX_VALUE));

		return jpPeptideQuant;
	}

	private JPanel getJPanelProteinQuant(JTable jtProteinQuant,
			JButton jbExportProtMZQExcel) {
		jbExportProtMZQExcel.setIconTextGap(1);
		jbExportProtMZQExcel.setMaximumSize(new Dimension(26, 20));
		jbExportProtMZQExcel.setMinimumSize(new Dimension(26, 20));
		jbExportProtMZQExcel.setPreferredSize(new Dimension(26, 20));
		jbExportProtMZQExcel.addMouseListener(new MouseListenerExcelExport(
				jtProteinQuant));

		JToolBar jtbProtMZQ = new JToolBar();
		jtbProtMZQ.setBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.RAISED));
		jtbProtMZQ.setFloatable(false);
		jtbProtMZQ.setMaximumSize(new Dimension(30, 23));
		jtbProtMZQ.setMinimumSize(new Dimension(30, 23));
		jtbProtMZQ.setPreferredSize(new Dimension(100, 23));
		jtbProtMZQ.add(jbExportProtMZQExcel);

		final JTextField jtProteinMZQ = new JTextField();
		jtProteinMZQ.setToolTipText("Enter the scan number");
		jtProteinMZQ.addKeyListener(new KeyListenerSearch(0, jtProteinQuant,
				false));

		JLabel jlProteinMZQ = new JLabel("Protein:");
		JLabel jlSearchMzQProt = new JLabel("Search:");
		jlSearchMzQProt.setFont(new Font("Tahoma", 1, 11)); // NOI18N

		JPanel jpProteinQuantHeader = new JPanel();
		GroupLayout jpProteinQuantHeaderLayout = new GroupLayout(
				jpProteinQuantHeader);
		jpProteinQuantHeader.setLayout(jpProteinQuantHeaderLayout);
		jpProteinQuantHeaderLayout
				.setHorizontalGroup(jpProteinQuantHeaderLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpProteinQuantHeaderLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(jlSearchMzQProt)
										.addGap(18, 18, 18)
										.addComponent(jlProteinMZQ)
										.addGap(18, 18, 18)
										.addComponent(jtProteinMZQ,
												GroupLayout.PREFERRED_SIZE,
												209, GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18)
										.addComponent(jtbProtMZQ,
												GroupLayout.DEFAULT_SIZE, 195,
												Short.MAX_VALUE)
										.addContainerGap()));
		jpProteinQuantHeaderLayout
				.setVerticalGroup(jpProteinQuantHeaderLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpProteinQuantHeaderLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpProteinQuantHeaderLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addComponent(
																jtbProtMZQ,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addGroup(
																jpProteinQuantHeaderLayout
																		.createParallelGroup(
																				GroupLayout.Alignment.BASELINE)
																		.addComponent(
																				jlSearchMzQProt)
																		.addComponent(
																				jlProteinMZQ)
																		.addComponent(
																				jtProteinMZQ,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)))
										.addContainerGap()));

		JSplitPane jspProteinQuant = new JSplitPane();
		jspProteinQuant.setDividerLocation(40);
		jspProteinQuant.setDividerSize(1);
		jspProteinQuant.setOrientation(JSplitPane.VERTICAL_SPLIT);
		jspProteinQuant.setTopComponent(jpProteinQuantHeader);

		JScrollPane jspProteinQuantDetail = new JScrollPane();
		jspProteinQuantDetail.setViewportView(jtProteinQuant);
		jspProteinQuant.setRightComponent(jspProteinQuantDetail);

		JPanel jpProteinQuant = new JPanel();
		GroupLayout jpProteinQuantLayout = new GroupLayout(jpProteinQuant);
		jpProteinQuant.setLayout(jpProteinQuantLayout);
		jpProteinQuantLayout.setHorizontalGroup(jpProteinQuantLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspProteinQuant));
		jpProteinQuantLayout.setVerticalGroup(jpProteinQuantLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspProteinQuant, GroupLayout.DEFAULT_SIZE, 445,
						Short.MAX_VALUE));

		return jpProteinQuant;
	}
}
