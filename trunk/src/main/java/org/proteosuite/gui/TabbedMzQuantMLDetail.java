package org.proteosuite.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.EtchedBorder;



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

	private JPanel getFeatureQuantPanel(JTable jtFeatureQuant,
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
		
		jtbFeatMZQ.add(jbExportFeatMZQExcel);

		final JTextField jtFeatureMZQ = new JTextField(20);
		jtFeatureMZQ.setToolTipText("Enter the scan number");
		

		JLabel jlSearchMzQFeat = new JLabel("Search:");
		jlSearchMzQFeat.setFont(new Font("Tahoma", 1, 11)); // NOI18N

		JPanel jpFeatureQuantHeader = new JPanel();

		jpFeatureQuantHeader.setLayout(new FlowLayout(FlowLayout.LEFT));
		jpFeatureQuantHeader.add(jlSearchMzQFeat);
		jpFeatureQuantHeader.add(new JLabel("Feature:"));
		jpFeatureQuantHeader.add(jtFeatureMZQ);
		jpFeatureQuantHeader.add(jtbFeatMZQ);

		JScrollPane jspFeatureQuantDetail = new JScrollPane();
		jspFeatureQuantDetail.setViewportView(jtFeatureQuant);

		JPanel jpFeatureQuant = new JPanel();
		jpFeatureQuant.setLayout(new BorderLayout());
		jpFeatureQuant.add(jpFeatureQuantHeader, BorderLayout.PAGE_START);
		jpFeatureQuant.add(jspFeatureQuantDetail, BorderLayout.CENTER);
		
		
		return jpFeatureQuant;
	}

	private JPanel getJPanelQuant(JTable jtPeptideQuant,
			JButton jbExportPepMZQExcel) {
		jbExportPepMZQExcel.setIconTextGap(1);
		jbExportPepMZQExcel.setMaximumSize(new Dimension(26, 20));
		jbExportPepMZQExcel.setMinimumSize(new Dimension(26, 20));
		jbExportPepMZQExcel.setPreferredSize(new Dimension(26, 20));
		

		JToolBar jtbPepMZQ = new JToolBar();
		jtbPepMZQ.setBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.RAISED));
		jtbPepMZQ.setFloatable(false);
		jtbPepMZQ.setMaximumSize(new Dimension(30, 23));
		jtbPepMZQ.setMinimumSize(new Dimension(30, 23));
		jtbPepMZQ.setPreferredSize(new Dimension(100, 23));
		jtbPepMZQ.add(jbExportPepMZQExcel);

		final JTextField jtPeptideMZQ = new JTextField(20);
		jtPeptideMZQ.setToolTipText("Enter the scan number");
		

		JLabel jlSearchMzQPep = new JLabel("Search:");
		jlSearchMzQPep.setFont(new Font("Tahoma", 1, 11)); // NOI18N

		JPanel jpPeptideQuantHeader = new JPanel();

		jpPeptideQuantHeader.setLayout(new FlowLayout(FlowLayout.LEFT));
		jpPeptideQuantHeader.add(jlSearchMzQPep);
		jpPeptideQuantHeader.add(new JLabel("Peptide:"));
		jpPeptideQuantHeader.add(jtPeptideMZQ);
		jpPeptideQuantHeader.add(jtbPepMZQ);

		JScrollPane jspPeptideQuantDetail = new JScrollPane();
		jspPeptideQuantDetail.setViewportView(jtPeptideQuant);

		JPanel jpPeptideQuant = new JPanel();
		jpPeptideQuant.setLayout(new BorderLayout());
		jpPeptideQuant.add(jpPeptideQuantHeader, BorderLayout.PAGE_START);
		jpPeptideQuant.add(jspPeptideQuantDetail, BorderLayout.CENTER);
		
		return jpPeptideQuant;
	}

	private Component getJPanelProteinQuant(JTable jtProteinQuant,
			JButton jbExportProtMZQExcel) {
		jbExportProtMZQExcel.setIconTextGap(1);
		jbExportProtMZQExcel.setMaximumSize(new Dimension(26, 20));
		jbExportProtMZQExcel.setMinimumSize(new Dimension(26, 20));
		jbExportProtMZQExcel.setPreferredSize(new Dimension(26, 20));
		

		JToolBar jtbProtMZQ = new JToolBar();
		jtbProtMZQ.setBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.RAISED));
		jtbProtMZQ.setFloatable(false);
		jtbProtMZQ.setMaximumSize(new Dimension(30, 23));
		jtbProtMZQ.setMinimumSize(new Dimension(30, 23));
		jtbProtMZQ.setPreferredSize(new Dimension(100, 23));
		jtbProtMZQ.add(jbExportProtMZQExcel);

		final JTextField jtProteinMZQ = new JTextField(20);
		jtProteinMZQ.setToolTipText("Enter the scan number");
		

		JLabel jlSearchMzQProt = new JLabel("Search:");
		jlSearchMzQProt.setFont(new Font("Tahoma", 1, 11)); // NOI18N

		JPanel jpProteinQuantHeader = new JPanel();
		jpProteinQuantHeader.setLayout(new FlowLayout(FlowLayout.LEFT));
		jpProteinQuantHeader.add(jlSearchMzQProt);
		jpProteinQuantHeader.add(new JLabel("Protein:"));
		jpProteinQuantHeader.add(jtProteinMZQ);
		jpProteinQuantHeader.add(jtbProtMZQ);

		JScrollPane jspProteinQuantDetail = new JScrollPane();
		jspProteinQuantDetail.setViewportView(jtProteinQuant);

		JPanel jspProteinQuant = new JPanel();
		jspProteinQuant.setLayout(new BorderLayout());
		jspProteinQuant.add(jpProteinQuantHeader, BorderLayout.PAGE_START);
		jspProteinQuant.add(jspProteinQuantDetail, BorderLayout.CENTER);

		return jspProteinQuant;
	}
}
