package org.proteosuite.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;

import org.proteosuite.listener.KeyListenerSearch;
import org.proteosuite.listener.MouseListenerExcelExport;

/**
 * 
 * @author Andrew Collins
 */
public class TabbedProperties extends JTabbedPane {
	final JButton jbExportFeatMZQExcel = new JButton();
	final JButton jbExportMGFExcel = new JButton();
	final JButton jbExportMascotXMLExcel = new JButton();
	final JButton jbExportMzIdentMLExcel = new JButton();
	final JButton jbExportMzMLExcel = new JButton();
	final JButton jbExportPepMZQExcel = new JButton();
	final JButton jbExportProtMZQExcel = new JButton();
	final JTabbedPane jtpMzQuantMLDetail;

	public TabbedProperties(JLabel jlFileNameMzMLText, JEditorPane jepMzMLView,
			JTable jtMzML, JLabel jlFileNameMGFText, JEditorPane jepMGFView,
			JTable jtMGF, JTable jtMzId, JLabel jlFileNameMzIDText,
			JComboBox<String> jcbPSM, JTable jtMzIDProtGroup,
			JEditorPane jepMzIDView, JEditorPane jepMascotXMLView,
			JTable jtMascotXMLView, JLabel jlFileNameMzQText,
			JEditorPane jepMZQView, JTable jtPeptideQuant,
			JTable jtProteinQuant, JTable jtFeatureQuant) {

		jtpMzQuantMLDetail = new TabbedMzQuantMLDetail(jtPeptideQuant,
				jtProteinQuant, jtFeatureQuant, jbExportPepMZQExcel,
				jbExportProtMZQExcel, jbExportFeatMZQExcel);

		// Buttons
		Icon iconExcel = new ImageIcon(getClass().getClassLoader().getResource(
				"images/xls.gif"));
		jbExportMzMLExcel.setIcon(iconExcel);
		jbExportMzMLExcel.setToolTipText("Export to Excel Spreadsheet (.xls)");
		jbExportMGFExcel.setIcon(iconExcel);
		jbExportMGFExcel.setToolTipText("Export to Excel Spreadsheet (.xls)");
		jbExportMzIdentMLExcel.setIcon(iconExcel);
		jbExportMzIdentMLExcel
				.setToolTipText("Export to Excel Spreadsheet (.xls)");
		jbExportMascotXMLExcel.setIcon(iconExcel);
		jbExportMascotXMLExcel
				.setToolTipText("Export to Excel Spreadsheet (.xls)");
		jbExportPepMZQExcel.setIcon(iconExcel);
		jbExportPepMZQExcel
				.setToolTipText("Export to Excel Spreadsheet (.xls)");
		jbExportProtMZQExcel.setIcon(iconExcel);
		jbExportProtMZQExcel
				.setToolTipText("Export to Excel Spreadsheet (.xls)");
		jbExportFeatMZQExcel.setIcon(iconExcel);
		jbExportFeatMZQExcel
				.setToolTipText("Export to Excel Spreadsheet (.xls)");

		addTab("mzML View", new ImageIcon(getClass().getClassLoader()
				.getResource("images/properties.gif")),
				getMzMLPanel(jlFileNameMzMLText, jepMzMLView, jtMzML));

		addTab("MGF View", new ImageIcon(getClass().getClassLoader()
				.getResource("images/properties.gif")),
				getMgfPanel(jlFileNameMGFText, jepMGFView, jtMGF));
		addTab("mzIdentML View",
				new ImageIcon(getClass().getClassLoader().getResource(
						"images/properties.gif")),
				getMzIdPanel(jtMzId, jlFileNameMzIDText, jcbPSM,
						jtMzIDProtGroup, jepMzIDView));
		addTab("Mascot XML View", new ImageIcon(getClass().getClassLoader()
				.getResource("images/properties.gif")),
				getMascotXML(jepMascotXMLView, jtMascotXMLView));
		addTab("mzQuantML View",
				new ImageIcon(getClass().getClassLoader().getResource(
						"images/properties.gif")),
				getMzQuantMLPanel(jtpMzQuantMLDetail, jlFileNameMzQText,
						jepMZQView));
	}

	private JPanel getMzMLPanel(JLabel jlFileNameMzMLText,
			JEditorPane jepMzMLView, JTable jtMzML) {
		
		final JTextField jtRT = new JTextField(20);
		jtRT.setToolTipText("Enter a retention time value (in seconds)");
		jtRT.addKeyListener(new KeyListenerSearch(5, jtMzML, false));

		final JTextField jtScan = new JTextField(20);
		jtScan.setToolTipText("Enter the scan number");
		jtScan.addKeyListener(new KeyListenerSearch(0, jtMzML, false));

		JLabel jlSearchMzML = new JLabel("Search:");
		jlSearchMzML.setFont(new Font("Tahoma", 1, 11)); // NOI18N

		JToolBar jtbMzMLOptions = new JToolBar();
		jtbMzMLOptions.setBorder(BorderFactory.createEtchedBorder());
		jtbMzMLOptions.setFloatable(false);
		jtbMzMLOptions.setMaximumSize(new Dimension(30, 23));
		jtbMzMLOptions.setMinimumSize(new Dimension(30, 23));
		jtbMzMLOptions.setPreferredSize(new Dimension(100, 23));

		jbExportMzMLExcel.setMaximumSize(new Dimension(28, 24));
		jbExportMzMLExcel.setMinimumSize(new Dimension(28, 24));
		jbExportMzMLExcel.setPreferredSize(new Dimension(28, 24));
		jbExportMzMLExcel
				.addMouseListener(new MouseListenerExcelExport(jtMzML));
		jtbMzMLOptions.add(jbExportMzMLExcel);

		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		inputPanel.add(jlSearchMzML);
		inputPanel.add(new JLabel("Scan Index:"));
		inputPanel.add(jtScan);
		inputPanel.add(new JLabel("RT (sec):"));
		inputPanel.add(jtRT);
		inputPanel.setBorder(new EmptyBorder(10, 10, 0, 10));

		JPanel exportPanel = new JPanel();
		exportPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		exportPanel.add(new JLabel("Export to:"));
		exportPanel.add(jtbMzMLOptions);
		exportPanel.setBorder(new EmptyBorder(0, 10, 10, 10));
		
		JPanel jpMzMLMenu = new JPanel();
		jpMzMLMenu.setLayout(new BoxLayout(jpMzMLMenu, BoxLayout.Y_AXIS));
		jpMzMLMenu.add(inputPanel);
		jpMzMLMenu.add(exportPanel);

		JScrollPane jspMzMLSubDetail = new JScrollPane();
		jspMzMLSubDetail.setViewportView(jtMzML);

		JPanel jspMzMLDetail = new JPanel();
		jspMzMLDetail.setLayout(new BorderLayout());
		jspMzMLDetail.add(jpMzMLMenu, BorderLayout.PAGE_START);
		jspMzMLDetail.add(jspMzMLSubDetail, BorderLayout.CENTER);

		JLabel jlFileNameMzML = new JLabel("File:");
		jlFileNameMzML.setFont(new Font("Tahoma", 1, 11)); // NOI18N

		JScrollPane jScrollPane9 = new JScrollPane();
		jScrollPane9.setViewportView(jepMzMLView);

		JPanel fileNamePanel = new JPanel();
		fileNamePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		fileNamePanel.add(jlFileNameMzML);
		fileNamePanel.add(jlFileNameMzMLText);

		JPanel jpMzMLViewHeader = new JPanel();
		jpMzMLViewHeader.setLayout(new BorderLayout());
		jpMzMLViewHeader.add(fileNamePanel, BorderLayout.PAGE_START);
		jpMzMLViewHeader.add(jScrollPane9, BorderLayout.CENTER);
		jScrollPane9.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), 
				jScrollPane9.getBorder()));

		final JPanel jspMzML = new JPanel();
		jspMzML.setLayout(new BorderLayout());
		jspMzML.add(jpMzMLViewHeader, BorderLayout.PAGE_START);
		jspMzML.add(jspMzMLDetail, BorderLayout.CENTER);
		
		return jspMzML;
	}

	private JPanel getMgfPanel(JLabel jlFileNameMGFText,
			JEditorPane jepMGFView, JTable jtMGF) {
		JLabel jlSearchMGF = new JLabel("Search:");
		jlSearchMGF.setFont(new Font("Tahoma", 1, 11)); // NOI18N

		JToolBar jtbMGFOptions = new JToolBar();
		jtbMGFOptions.setBorder(BorderFactory.createEtchedBorder());
		jtbMGFOptions.setFloatable(false);
		jtbMGFOptions.setMaximumSize(new Dimension(30, 23));
		jtbMGFOptions.setMinimumSize(new Dimension(30, 23));
		jtbMGFOptions.setPreferredSize(new Dimension(100, 23));

		jbExportMGFExcel.setMaximumSize(new Dimension(28, 24));
		jbExportMGFExcel.setMinimumSize(new Dimension(28, 24));
		jbExportMGFExcel.setPreferredSize(new Dimension(28, 24));
		jbExportMGFExcel.addMouseListener(new MouseListenerExcelExport(jtMGF));
		jtbMGFOptions.add(jbExportMGFExcel);

		final JTextField jtScanTitle = new JTextField(20);
		jtScanTitle
				.setToolTipText("Enter any character(s) that is (are) specified in the scan title");
		jtScanTitle.addKeyListener(new KeyListenerSearch(1, jtMGF, false));

		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		inputPanel.add(jlSearchMGF);
		inputPanel.add(new JLabel("Scan title:"));
		inputPanel.add(jtScanTitle);
		inputPanel.setBorder(new EmptyBorder(10, 10, 0, 10));

		JPanel exportPanel = new JPanel();
		exportPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		exportPanel.add(new JLabel("Export to:"));
		exportPanel.add(jtbMGFOptions);
		exportPanel.setBorder(new EmptyBorder(0, 10, 10, 10));
		
		JPanel jpMGFMenu = new JPanel();
		jpMGFMenu.setLayout(new BoxLayout(jpMGFMenu, BoxLayout.Y_AXIS));
		jpMGFMenu.add(inputPanel);
		jpMGFMenu.add(exportPanel);

		JScrollPane jspMGFSubDetail = new JScrollPane();
		jspMGFSubDetail.setViewportView(jtMGF);

		JPanel jspMGFDetail = new JPanel();
		jspMGFDetail.setLayout(new BorderLayout());
		jspMGFDetail.add(jpMGFMenu, BorderLayout.PAGE_START);
		jspMGFDetail.add(jspMGFSubDetail, BorderLayout.CENTER);

		JScrollPane jScrollPane10 = new JScrollPane();
		jScrollPane10.setViewportView(jepMGFView);

		JLabel jlFileNameMGF = new JLabel("File:");
		jlFileNameMGF.setFont(new Font("Tahoma", 1, 11)); // NOI18N

		JPanel fileNamePanel = new JPanel();
		fileNamePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		fileNamePanel.add(jlFileNameMGF);
		fileNamePanel.add(jlFileNameMGFText);

		JPanel jpMGFViewHeader = new JPanel();
		jpMGFViewHeader.setLayout(new BorderLayout());
		jpMGFViewHeader.add(fileNamePanel, BorderLayout.PAGE_START);
		jpMGFViewHeader.add(jScrollPane10, BorderLayout.CENTER);
		jScrollPane10.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), 
				jScrollPane10.getBorder()));

		JPanel jpMGF = new JPanel();
		jpMGF.setLayout(new BorderLayout());
		jpMGF.add(jpMGFViewHeader, BorderLayout.PAGE_START);
		jpMGF.add(jspMGFDetail, BorderLayout.CENTER);

		return jpMGF;
	}

	private JPanel getMzIdPanel(JTable jtMzId, JLabel jlFileNameMzIDText,
			JComboBox<String> jcbPSM, JTable jtMzIDProtGroup,
			JEditorPane jepMzIDView) {

		JScrollPane jspMzIdProtGroup = new JScrollPane();
		jspMzIdProtGroup.setViewportView(jtMzId);

		JScrollPane jspMzIDView = new JScrollPane();
		jspMzIDView.setViewportView(jepMzIDView);

		JLabel jlSearchMzId = new JLabel("Search:");
		jlSearchMzId.setFont(new Font("Tahoma", 1, 11)); // NOI18N

		final JTextField jtProteinMzId = new JTextField(20);
		jtProteinMzId.setToolTipText("Enter protein");
		jtProteinMzId.addKeyListener(new KeyListenerSearch(1, jtMzId, true));

		final JTextField jtPeptideMzId = new JTextField(30);
		jtPeptideMzId.setToolTipText("Enter peptide sequence");
		jtPeptideMzId.addKeyListener(new KeyListenerSearch(2, jtMzId, true));

		JScrollPane jspMzIDProtGroup = new JScrollPane();
		jspMzIDProtGroup.setViewportView(jtMzIDProtGroup);

		JLabel jlFileNameMzID = new JLabel("File:");
		jlFileNameMzID.setFont(new Font("Tahoma", 1, 11)); // NOI18N

		JToolBar jtbMzIdentMLOptions = new JToolBar();
		jtbMzIdentMLOptions.setBorder(BorderFactory.createEtchedBorder());
		jtbMzIdentMLOptions.setFloatable(false);
		jtbMzIdentMLOptions.setMaximumSize(new Dimension(30, 23));
		jtbMzIdentMLOptions.setMinimumSize(new Dimension(30, 23));
		jtbMzIdentMLOptions.setPreferredSize(new Dimension(100, 23));

		jbExportMzIdentMLExcel.setMaximumSize(new Dimension(28, 24));
		jbExportMzIdentMLExcel.setMinimumSize(new Dimension(28, 24));
		jbExportMzIdentMLExcel.setPreferredSize(new Dimension(28, 24));
		jbExportMzIdentMLExcel.addMouseListener(new MouseListenerExcelExport(
				jtMzId));
		jtbMzIdentMLOptions.add(jbExportMzIdentMLExcel);

		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		inputPanel.add(jlSearchMzId);
		inputPanel.add(new JLabel("Protein:"));
		inputPanel.add(jtProteinMzId);
		inputPanel.add(new JLabel("Peptide:"));
		inputPanel.add(jtPeptideMzId);
		inputPanel.setBorder(new EmptyBorder(10, 10, 0, 10));

		JPanel exportPanel = new JPanel();
		exportPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		exportPanel.add(new JLabel("Export to:"));
		exportPanel.add(jtbMzIdentMLOptions);
		exportPanel.setBorder(new EmptyBorder(0, 10, 0, 10));
		
		JPanel spectrumMatches = new JPanel();
		spectrumMatches.setLayout(new FlowLayout(FlowLayout.LEFT));
		spectrumMatches.add(new JLabel("Peptide-Spectrum matches with rank:"));
		spectrumMatches.add(jcbPSM);
		spectrumMatches.setBorder(new EmptyBorder(0, 10, 10, 10));
		
		JPanel jpMzIdMenu = new JPanel();
		jpMzIdMenu.setLayout(new BoxLayout(jpMzIdMenu, BoxLayout.Y_AXIS));
		jpMzIdMenu.add(inputPanel);
		jpMzIdMenu.add(exportPanel);
		jpMzIdMenu.add(spectrumMatches);
		jpMzIdMenu.add(jspMzIdProtGroup);

		JPanel fileNamePanel = new JPanel();
		fileNamePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		fileNamePanel.add(jlFileNameMzID);
		fileNamePanel.add(jlFileNameMzIDText);

		JPanel jpMzIdentMLViewHeader = new JPanel();
		jpMzIdentMLViewHeader.setLayout(new BorderLayout());
		jpMzIdentMLViewHeader.add(fileNamePanel, BorderLayout.PAGE_START);
		jpMzIdentMLViewHeader.add(jspMzIDView, BorderLayout.CENTER);
		jspMzIDView.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), 
				jspMzIDView.getBorder()));
		
		JSplitPane jspMzIdDetail = new JSplitPane();
		jspMzIdDetail.setDividerLocation(300);
		jspMzIdDetail.setDividerSize(10);
		jspMzIdDetail.setOrientation(JSplitPane.VERTICAL_SPLIT);
		jspMzIdDetail.setTopComponent(jpMzIdMenu);
		jspMzIdDetail.setRightComponent(jspMzIDProtGroup);
		jspMzIdDetail.setBorder(null);

		JPanel jpMzId = new JPanel();
		jpMzId.setLayout(new BorderLayout());
		jpMzId.add(jpMzIdentMLViewHeader, BorderLayout.PAGE_START);
		jpMzId.add(jspMzIdDetail, BorderLayout.CENTER);
		
		return jpMzId;
	}

	private JPanel getMascotXML(JEditorPane jepMascotXMLView,
			JTable jtMascotXMLView) {
		JToolBar jtbMzIdentMLOptions1 = new JToolBar();
		jtbMzIdentMLOptions1.setBorder(BorderFactory.createEtchedBorder());
		jtbMzIdentMLOptions1.setFloatable(false);
		jtbMzIdentMLOptions1.setMaximumSize(new Dimension(50, 23));
		jtbMzIdentMLOptions1.setMinimumSize(new Dimension(50, 23));
		jtbMzIdentMLOptions1.setPreferredSize(new Dimension(100, 23));

		jbExportMascotXMLExcel.setMaximumSize(new Dimension(28, 24));
		jbExportMascotXMLExcel.setMinimumSize(new Dimension(28, 24));
		jbExportMascotXMLExcel.setPreferredSize(new Dimension(28, 24));
		jbExportMascotXMLExcel.addMouseListener(new MouseListenerExcelExport(
				jtMascotXMLView));

		jtbMzIdentMLOptions1.add(jbExportMascotXMLExcel);

		final JTextField jtProtein = new JTextField(20);
		jtProtein
				.addKeyListener(new KeyListenerSearch(1, jtMascotXMLView, true));

		JLabel jlSearchMascotXML = new JLabel("Search:");
		jlSearchMascotXML.setFont(new Font("Tahoma", 1, 11)); // NOI18N

		final JTextField jtPeptide = new JTextField(30);
		jtPeptide
				.addKeyListener(new KeyListenerSearch(2, jtMascotXMLView, true));

		JScrollPane jspMascotXMLDetail = new JScrollPane();
		jspMascotXMLDetail.setViewportView(jtMascotXMLView);

		JPanel jpMascotXMLMenu = new JPanel();
		jpMascotXMLMenu.setLayout(new BorderLayout());

		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		inputPanel.add(jlSearchMascotXML);
		inputPanel.add(new JLabel("Protein:"));
		inputPanel.add(jtProtein);
		inputPanel.add(new JLabel("Peptide:"));
		inputPanel.add(jtPeptide);
		inputPanel.setBorder(new EmptyBorder(10, 10, 0, 10));

		JPanel exportPanel = new JPanel();
		exportPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		exportPanel.add(new JLabel("Export to:"));
		exportPanel.add(jtbMzIdentMLOptions1);
		exportPanel.setBorder(new EmptyBorder(0, 10, 10, 10));
		
		jpMascotXMLMenu.add(inputPanel, BorderLayout.PAGE_START);
		jpMascotXMLMenu.add(exportPanel, BorderLayout.PAGE_END);
		
		JLabel jlFileNameMascotXML = new JLabel("File:");
		jlFileNameMascotXML.setFont(new Font("Tahoma", 1, 11)); // NOI18N
		final JLabel jlFileNameMascotXMLText = new JLabel();
		JScrollPane jspMascotXMLView = new JScrollPane();
		jspMascotXMLView.setViewportView(jepMascotXMLView);
		jspMascotXMLView.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), 
				jspMascotXMLView.getBorder()));

		JPanel fileNamePanel = new JPanel();
		fileNamePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		fileNamePanel.add(jlFileNameMascotXML);
		fileNamePanel.add(jlFileNameMascotXMLText);

		JPanel jpMascotXMLViewHeader = new JPanel();
		jpMascotXMLViewHeader.setLayout(new BorderLayout());
		jpMascotXMLViewHeader.add(fileNamePanel, BorderLayout.PAGE_START);
		jpMascotXMLViewHeader.add(jspMascotXMLView, BorderLayout.CENTER);
		
		JPanel jspMascotXMLSubDetail = new JPanel();
		jspMascotXMLSubDetail.setLayout(new BorderLayout());
		jspMascotXMLSubDetail.add(jpMascotXMLMenu, BorderLayout.PAGE_START);
		jspMascotXMLSubDetail.add(jspMascotXMLDetail, BorderLayout.CENTER);

		JPanel jpMascotXML = new JPanel();
		jpMascotXML.setLayout(new BorderLayout());
		jpMascotXML.add(jpMascotXMLViewHeader, BorderLayout.PAGE_START);
		jpMascotXML.add(jspMascotXMLSubDetail, BorderLayout.CENTER);

		return jpMascotXML;
	}

	private JPanel getMzQuantMLPanel(JTabbedPane jtpMzQuantMLDetail,
			JLabel jlFileNameMzQText, JEditorPane jepMZQView) {
		JScrollPane jScrollPane12 = new JScrollPane();
		jScrollPane12.setViewportView(jepMZQView);

		JLabel jlFileNameMzQ = new JLabel("File:");
		jlFileNameMzQ.setFont(new Font("Tahoma", 1, 11)); // NOI18N

		JPanel fileNamePanel = new JPanel();
		fileNamePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		fileNamePanel.add(jlFileNameMzQ);
		fileNamePanel.add(jlFileNameMzQText);

		JPanel jpMzQuantMLHeader = new JPanel();
		jpMzQuantMLHeader.setLayout(new BorderLayout());
		jpMzQuantMLHeader.add(fileNamePanel, BorderLayout.PAGE_START);
		jpMzQuantMLHeader.add(jScrollPane12, BorderLayout.CENTER);
		jScrollPane12.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), jScrollPane12.getBorder()));
		
		JPanel jpMzQuantML = new JPanel();
		jpMzQuantML.setLayout(new BorderLayout());
		jpMzQuantML.add(jpMzQuantMLHeader, BorderLayout.PAGE_START);
		jpMzQuantML.add(jtpMzQuantMLDetail, BorderLayout.CENTER);

		return jpMzQuantML;
	}

	public void setMzQuantMLDetailIndex(int index) {
		jtpMzQuantMLDetail.setSelectedIndex(index);
	}

	public void setExportMGFExcel(boolean state) {
		jbExportMGFExcel.setEnabled(state);
	}

	public void setExportMzIdentMLExcel(boolean state) {
		jbExportMzIdentMLExcel.setEnabled(state);
	}

	public void setExportMascotXMLExcel(boolean state) {
		jbExportMascotXMLExcel.setEnabled(state);

	}

	public void setExportPepMZQExcel(boolean state) {
		jbExportPepMZQExcel.setEnabled(state);
	}

	public void setExportProtMZQExcel(boolean state) {
		jbExportProtMZQExcel.setEnabled(state);
	}

	public void setExportFeatMZQExcel(boolean state) {
		jbExportFeatMZQExcel.setEnabled(state);

	}

	public void setExportMzMLExcel(boolean state) {
		jbExportMzMLExcel.setEnabled(state);
	}
}