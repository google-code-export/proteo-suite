package org.proteosuite.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
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
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;

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
			JTable jtMzML, JLabel jlFileNameMGFText,
			JEditorPane jepMGFView, JTable jtMGF, JTable jtMzId, 
			JLabel jlFileNameMzIDText, JComboBox<String> jcbPSM,
			JTable jtMzIDProtGroup, JEditorPane jepMzIDView,
			JEditorPane jepMascotXMLView, JTable jtMascotXMLView, 
			JLabel jlFileNameMzQText, JEditorPane jepMZQView,
			JTable jtPeptideQuant, JTable jtProteinQuant, JTable jtFeatureQuant) {

		jtpMzQuantMLDetail = new TabbedMzQuantMLDetail(
				jtPeptideQuant, jtProteinQuant, jtFeatureQuant, jbExportPepMZQExcel, jbExportProtMZQExcel, jbExportFeatMZQExcel);
		
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
		
		
		JTabbedPane jtpProperties = this;
		jtpProperties.addTab(
				"mzML View",
				getMzMLPanel(jlFileNameMzMLText, jepMzMLView, jtMzML));

		jtpProperties.addTab(
				"MGF View",
				getMgfPanel(jlFileNameMGFText, jepMGFView, jtMGF));
		jtpProperties.addTab(
				"mzIdentML View",
				getMzIdPanel(jtMzId, jlFileNameMzIDText, jcbPSM, jtMzIDProtGroup,
						jepMzIDView));
		jtpProperties.addTab(
				"Mascot XML View",
				getMascotXML(jepMascotXMLView, jtMascotXMLView));
		jtpProperties.addTab(
				"mzQuantML View",
				getMzQuantMLPanel(jtpMzQuantMLDetail, jlFileNameMzQText,
						jepMZQView));

		Icon propertiesMzMLIcon = new ImageIcon(getClass().getClassLoader()
				.getResource("images/properties.gif"));
		JLabel jlPropertiesMzMLIcon = new JLabel("mzML View",
				propertiesMzMLIcon, SwingConstants.RIGHT);
		jlPropertiesMzMLIcon.setIconTextGap(5);
		jtpProperties.setTabComponentAt(0, jlPropertiesMzMLIcon);

		Icon propertiesMGFIcon = new ImageIcon(getClass().getClassLoader()
				.getResource("images/properties.gif"));
		JLabel jlPropertiesMGFIcon = new JLabel("MGF View", propertiesMGFIcon,
				SwingConstants.RIGHT);
		jlPropertiesMGFIcon.setIconTextGap(5);
		jtpProperties.setTabComponentAt(1, jlPropertiesMGFIcon);

		Icon propertiesMzIdenMLIcon = new ImageIcon(getClass().getClassLoader()
				.getResource("images/properties.gif"));
		JLabel jlPropertiesMzIdentMLIcon = new JLabel("mzIdentML View",
				propertiesMzIdenMLIcon, SwingConstants.RIGHT);
		jlPropertiesMzIdentMLIcon.setIconTextGap(5);
		jtpProperties.setTabComponentAt(2, jlPropertiesMzIdentMLIcon);

		Icon propertiesMascotIcon = new ImageIcon(getClass().getClassLoader()
				.getResource("images/properties.gif"));
		JLabel jlPropertiesMascotIcon = new JLabel("Mascot XML View",
				propertiesMascotIcon, SwingConstants.RIGHT);
		jlPropertiesMascotIcon.setIconTextGap(5);
		jtpProperties.setTabComponentAt(3, jlPropertiesMascotIcon);

		Icon propertiesMzQuantMLIcon = new ImageIcon(getClass()
				.getClassLoader().getResource("images/properties.gif"));
		JLabel jlPropertiesMzQuantMLIcon = new JLabel("mzQuantML View",
				propertiesMzQuantMLIcon, SwingConstants.RIGHT);
		jlPropertiesMzQuantMLIcon.setIconTextGap(5);
		jtpProperties.setTabComponentAt(4, jlPropertiesMzQuantMLIcon);

		jtpProperties.setSelectedIndex(0);
	}

	private JPanel getMzMLPanel(JLabel jlFileNameMzMLText,
			JEditorPane jepMzMLView, JTable jtMzML) {
		JLabel jlRT = new JLabel("RT (sec):");
		final JTextField jtRT = new JTextField();

		jtRT.setToolTipText("Enter a retention time value (in seconds)");
		jtRT.addKeyListener(new KeyListenerSearch(5, jtMzML, false));

		final JTextField jtScan = new JTextField();
		jtScan.setToolTipText("Enter the scan number");
		jtScan.addKeyListener(new KeyListenerSearch(0, jtMzML, false));

		JLabel jlSearchMzML = new JLabel("Search:");
		jlSearchMzML.setFont(new Font("Tahoma", 1, 11)); // NOI18N
		JLabel jlScan = new JLabel("Scan Index:");

		JLabel jlExportMzMLXLS = new JLabel("Export to:");

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

		JPanel jpMzMLMenu = new JPanel();
		jpMzMLMenu.setBorder(BorderFactory.createEtchedBorder());
		GroupLayout jpMzMLMenuLayout = new GroupLayout(jpMzMLMenu);
		jpMzMLMenu.setLayout(jpMzMLMenuLayout);
		jpMzMLMenuLayout
				.setHorizontalGroup(jpMzMLMenuLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpMzMLMenuLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpMzMLMenuLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addComponent(
																jlSearchMzML)
														.addComponent(
																jlExportMzMLXLS))
										.addGap(18, 18, 18)
										.addGroup(
												jpMzMLMenuLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING,
																false)
														.addComponent(
																jtbMzMLOptions,
																GroupLayout.Alignment.TRAILING,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addGroup(
																jpMzMLMenuLayout
																		.createSequentialGroup()
																		.addComponent(
																				jlScan)
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				jtScan,
																				GroupLayout.PREFERRED_SIZE,
																				62,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				jlRT)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				jtRT,
																				GroupLayout.PREFERRED_SIZE,
																				62,
																				GroupLayout.PREFERRED_SIZE)))
										.addContainerGap(210, Short.MAX_VALUE)));
		jpMzMLMenuLayout
				.setVerticalGroup(jpMzMLMenuLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpMzMLMenuLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpMzMLMenuLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addComponent(jtScan)
														.addComponent(jtRT)
														.addGroup(
																jpMzMLMenuLayout
																		.createParallelGroup(
																				GroupLayout.Alignment.BASELINE)
																		.addComponent(
																				jlSearchMzML)
																		.addComponent(
																				jlScan)
																		.addComponent(
																				jlRT)))
										.addGroup(
												jpMzMLMenuLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																jpMzMLMenuLayout
																		.createSequentialGroup()
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				jtbMzMLOptions,
																				GroupLayout.PREFERRED_SIZE,
																				35,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																jpMzMLMenuLayout
																		.createSequentialGroup()
																		.addGap(17,
																				17,
																				17)
																		.addComponent(
																				jlExportMzMLXLS)))
										.addGap(13, 13, 13)));

		JScrollPane jspMzMLSubDetail = new JScrollPane();
		jspMzMLSubDetail.setViewportView(jtMzML);

		JSplitPane jspMzMLDetail = new JSplitPane();
		jspMzMLDetail.setDividerLocation(90);
		jspMzMLDetail.setDividerSize(1);
		jspMzMLDetail.setOrientation(JSplitPane.VERTICAL_SPLIT);
		jspMzMLDetail.setLeftComponent(jpMzMLMenu);
		jspMzMLDetail.setRightComponent(jspMzMLSubDetail);

		JLabel jlFileNameMzML = new JLabel("File:");
		jlFileNameMzML.setFont(new Font("Tahoma", 1, 11)); // NOI18N

		JScrollPane jScrollPane9 = new JScrollPane();
		jScrollPane9.setViewportView(jepMzMLView);

		JPanel jpMzMLViewHeader = new JPanel();
		GroupLayout jpMzMLViewHeaderLayout = new GroupLayout(jpMzMLViewHeader);
		jpMzMLViewHeader.setLayout(jpMzMLViewHeaderLayout);
		jpMzMLViewHeaderLayout
				.setHorizontalGroup(jpMzMLViewHeaderLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpMzMLViewHeaderLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpMzMLViewHeaderLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																jpMzMLViewHeaderLayout
																		.createSequentialGroup()
																		.addComponent(
																				jlFileNameMzML)
																		.addGap(38,
																				38,
																				38)
																		.addComponent(
																				jlFileNameMzMLText,
																				GroupLayout.PREFERRED_SIZE,
																				283,
																				GroupLayout.PREFERRED_SIZE))
														.addComponent(
																jScrollPane9,
																GroupLayout.PREFERRED_SIZE,
																535,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap(20, Short.MAX_VALUE)));
		jpMzMLViewHeaderLayout
				.setVerticalGroup(jpMzMLViewHeaderLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpMzMLViewHeaderLayout
										.createSequentialGroup()
										.addGap(19, 19, 19)
										.addGroup(
												jpMzMLViewHeaderLayout
														.createParallelGroup(
																GroupLayout.Alignment.TRAILING)
														.addComponent(
																jlFileNameMzML)
														.addComponent(
																jlFileNameMzMLText,
																GroupLayout.PREFERRED_SIZE,
																14,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jScrollPane9,
												GroupLayout.PREFERRED_SIZE, 52,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap(18, Short.MAX_VALUE)));

		final JSplitPane jspMzML = new JSplitPane();
		jspMzML.setBackground(new Color(255, 255, 255));
		jspMzML.setDividerLocation(110);
		jspMzML.setDividerSize(1);
		jspMzML.setOrientation(JSplitPane.VERTICAL_SPLIT);
		jspMzML.setBottomComponent(jspMzMLDetail);
		jspMzML.setLeftComponent(jpMzMLViewHeader);

		// MzML data
		jspMzML.setDividerLocation(100);

		JPanel jpMzML = new JPanel();
		GroupLayout jpMzMLLayout = new GroupLayout(jpMzML);
		jpMzML.setLayout(jpMzMLLayout);
		jpMzMLLayout.setHorizontalGroup(jpMzMLLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addComponent(jspMzML));
		jpMzMLLayout.setVerticalGroup(jpMzMLLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addComponent(jspMzML,
				GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE));

		return jpMzML;
	}

	private JPanel getMgfPanel(JLabel jlFileNameMGFText,
			JEditorPane jepMGFView, JTable jtMGF) {
		JLabel jlSearchMGF = new JLabel("Search:");
		jlSearchMGF.setFont(new Font("Tahoma", 1, 11)); // NOI18N

		JLabel jlExportMGFXLS = new JLabel("Export To:");

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

		JLabel jlScanTitle = new JLabel("Scan title:");

		final JTextField jtScanTitle = new JTextField();
		jtScanTitle
				.setToolTipText("Enter any character(s) that is (are) specified in the scan title");
		jtScanTitle.addKeyListener(new KeyListenerSearch(1, jtMGF, false));

		JPanel jpMGFMenu = new JPanel();
		GroupLayout jpMGFMenuLayout = new GroupLayout(jpMGFMenu);
		jpMGFMenu.setLayout(jpMGFMenuLayout);
		jpMGFMenuLayout
				.setHorizontalGroup(jpMGFMenuLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpMGFMenuLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpMGFMenuLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addComponent(
																jlSearchMGF)
														.addComponent(
																jlExportMGFXLS))
										.addGap(18, 18, 18)
										.addGroup(
												jpMGFMenuLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING,
																false)
														.addComponent(
																jtbMGFOptions,
																GroupLayout.Alignment.TRAILING,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addGroup(
																jpMGFMenuLayout
																		.createSequentialGroup()
																		.addComponent(
																				jlScanTitle)
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				jtScanTitle,
																				GroupLayout.PREFERRED_SIZE,
																				223,
																				GroupLayout.PREFERRED_SIZE)))
										.addContainerGap(197, Short.MAX_VALUE)));
		jpMGFMenuLayout
				.setVerticalGroup(jpMGFMenuLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpMGFMenuLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpMGFMenuLayout
														.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
														.addComponent(
																jlSearchMGF)
														.addComponent(
																jlScanTitle)
														.addComponent(
																jtScanTitle,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addGroup(
												jpMGFMenuLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																jpMGFMenuLayout
																		.createSequentialGroup()
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				jtbMGFOptions,
																				GroupLayout.PREFERRED_SIZE,
																				35,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																jpMGFMenuLayout
																		.createSequentialGroup()
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				jlExportMGFXLS)))
										.addContainerGap(17, Short.MAX_VALUE)));

		JScrollPane jspMGFSubDetail = new JScrollPane();
		jspMGFSubDetail.setViewportView(jtMGF);

		JSplitPane jspMGFDetail = new JSplitPane();
		jspMGFDetail.setDividerLocation(90);
		jspMGFDetail.setDividerSize(1);
		jspMGFDetail.setOrientation(JSplitPane.VERTICAL_SPLIT);
		jspMGFDetail.setTopComponent(jpMGFMenu);
		jspMGFDetail.setRightComponent(jspMGFSubDetail);

		JScrollPane jScrollPane10 = new JScrollPane();
		jScrollPane10.setViewportView(jepMGFView);

		JLabel jlFileNameMGF = new JLabel("File:");
		jlFileNameMGF.setFont(new Font("Tahoma", 1, 11)); // NOI18N

		JPanel jpMGFViewHeader = new JPanel();
		jpMGFViewHeader.setPreferredSize(new Dimension(604, 115));
		GroupLayout jpMGFViewHeaderLayout = new GroupLayout(jpMGFViewHeader);
		jpMGFViewHeader.setLayout(jpMGFViewHeaderLayout);
		jpMGFViewHeaderLayout
				.setHorizontalGroup(jpMGFViewHeaderLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpMGFViewHeaderLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpMGFViewHeaderLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																jpMGFViewHeaderLayout
																		.createSequentialGroup()
																		.addComponent(
																				jlFileNameMGF)
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				jlFileNameMGFText,
																				GroupLayout.PREFERRED_SIZE,
																				283,
																				GroupLayout.PREFERRED_SIZE))
														.addComponent(
																jScrollPane10,
																GroupLayout.PREFERRED_SIZE,
																535,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap(20, Short.MAX_VALUE)));
		jpMGFViewHeaderLayout
				.setVerticalGroup(jpMGFViewHeaderLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpMGFViewHeaderLayout
										.createSequentialGroup()
										.addGap(18, 18, 18)
										.addGroup(
												jpMGFViewHeaderLayout
														.createParallelGroup(
																GroupLayout.Alignment.TRAILING)
														.addComponent(
																jlFileNameMGFText,
																GroupLayout.PREFERRED_SIZE,
																14,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																jlFileNameMGF))
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jScrollPane10,
												GroupLayout.PREFERRED_SIZE, 52,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap(19, Short.MAX_VALUE)));

		JSplitPane jspMGF = new JSplitPane();
		jspMGF.setDividerLocation(110);
		jspMGF.setDividerSize(1);
		jspMGF.setOrientation(JSplitPane.VERTICAL_SPLIT);
		jspMGF.setLeftComponent(jpMGFViewHeader);
		jspMGF.setRightComponent(jspMGFDetail);

		JPanel jpMGF = new JPanel();
		GroupLayout jpMGFLayout = new GroupLayout(jpMGF);
		jpMGF.setLayout(jpMGFLayout);
		jpMGFLayout.setHorizontalGroup(jpMGFLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addComponent(jspMGF,
				GroupLayout.DEFAULT_SIZE, 567, Short.MAX_VALUE));
		jpMGFLayout.setVerticalGroup(jpMGFLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addComponent(jspMGF,
				GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE));

		return jpMGF;
	}

	private JPanel getMzIdPanel(JTable jtMzId, 	JLabel jlFileNameMzIDText, JComboBox<String> jcbPSM,
			JTable jtMzIDProtGroup, JEditorPane jepMzIDView) {
		JLabel jlPeptideSpectrumMatches = new JLabel(
				"Peptide-Spectrum matches with rank:");

		JScrollPane jspMzIdProtGroup = new JScrollPane();
		jspMzIdProtGroup.setViewportView(jtMzId);

		JScrollPane jspMzIDView = new JScrollPane();
		jspMzIDView.setViewportView(jepMzIDView);

		JLabel jlSearchMzId = new JLabel("Search:");
		jlSearchMzId.setFont(new Font("Tahoma", 1, 11)); // NOI18N

		JLabel jlProteinMzId = new JLabel("Protein:");

		final JTextField jtProteinMzId = new JTextField();
		jtProteinMzId.setToolTipText("Enter protein");
		jtProteinMzId.addKeyListener(new KeyListenerSearch(1, jtMzId, true));

		JLabel jlPeptideMzId = new JLabel("Peptide:");

		final JTextField jtPeptideMzId = new JTextField();
		jtPeptideMzId.setToolTipText("Enter peptide sequence");
		jtPeptideMzId.addKeyListener(new KeyListenerSearch(2, jtMzId, true));

		JScrollPane jspMzIDProtGroup = new JScrollPane();
		jspMzIDProtGroup.setViewportView(jtMzIDProtGroup);

		JLabel jlExportMzIDXLS = new JLabel("Export to:");

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

		JPanel jpMzIdMenu = new JPanel();
		GroupLayout jpMzIdMenuLayout = new GroupLayout(jpMzIdMenu);
		jpMzIdMenu.setLayout(jpMzIdMenuLayout);
		jpMzIdMenuLayout
				.setHorizontalGroup(jpMzIdMenuLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(jspMzIdProtGroup,
								GroupLayout.Alignment.TRAILING,
								GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE)
						.addGroup(
								jpMzIdMenuLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpMzIdMenuLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																jpMzIdMenuLayout
																		.createSequentialGroup()
																		.addGroup(
																				jpMzIdMenuLayout
																						.createParallelGroup(
																								GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jlSearchMzId)
																						.addComponent(
																								jlExportMzIDXLS))
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.UNRELATED)
																		.addGroup(
																				jpMzIdMenuLayout
																						.createParallelGroup(
																								GroupLayout.Alignment.LEADING,
																								false)
																						.addComponent(
																								jtbMzIdentMLOptions,
																								GroupLayout.Alignment.TRAILING,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addGroup(
																								jpMzIdMenuLayout
																										.createSequentialGroup()
																										.addComponent(
																												jlProteinMzId)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.UNRELATED)
																										.addComponent(
																												jtProteinMzId,
																												GroupLayout.PREFERRED_SIZE,
																												79,
																												GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.UNRELATED)
																										.addComponent(
																												jlPeptideMzId)
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												jtPeptideMzId,
																												GroupLayout.PREFERRED_SIZE,
																												191,
																												GroupLayout.PREFERRED_SIZE))))
														.addGroup(
																jpMzIdMenuLayout
																		.createSequentialGroup()
																		.addComponent(
																				jlPeptideSpectrumMatches)
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				jcbPSM,
																				GroupLayout.PREFERRED_SIZE,
																				82,
																				GroupLayout.PREFERRED_SIZE)))
										.addContainerGap()));
		jpMzIdMenuLayout
				.setVerticalGroup(jpMzIdMenuLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpMzIdMenuLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpMzIdMenuLayout
														.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
														.addComponent(
																jlSearchMzId)
														.addComponent(
																jlProteinMzId)
														.addComponent(
																jtProteinMzId,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																jlPeptideMzId)
														.addComponent(
																jtPeptideMzId,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addGroup(
												jpMzIdMenuLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																jpMzIdMenuLayout
																		.createSequentialGroup()
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				jtbMzIdentMLOptions,
																				GroupLayout.PREFERRED_SIZE,
																				35,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																jpMzIdMenuLayout
																		.createSequentialGroup()
																		.addGap(16,
																				16,
																				16)
																		.addComponent(
																				jlExportMzIDXLS)))
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(
												jpMzIdMenuLayout
														.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
														.addComponent(
																jcbPSM,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																jlPeptideSpectrumMatches))
										.addGap(18, 18, 18)
										.addComponent(jspMzIdProtGroup,
												GroupLayout.DEFAULT_SIZE, 155,
												Short.MAX_VALUE)
										.addGap(23, 23, 23)));

		JPanel jpMzIdentMLViewHeader = new JPanel();
		GroupLayout jpMzIdentMLViewHeaderLayout = new GroupLayout(
				jpMzIdentMLViewHeader);
		jpMzIdentMLViewHeader.setLayout(jpMzIdentMLViewHeaderLayout);
		jpMzIdentMLViewHeaderLayout
				.setHorizontalGroup(jpMzIdentMLViewHeaderLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpMzIdentMLViewHeaderLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpMzIdentMLViewHeaderLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																jpMzIdentMLViewHeaderLayout
																		.createSequentialGroup()
																		.addComponent(
																				jlFileNameMzID)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				jlFileNameMzIDText,
																				GroupLayout.PREFERRED_SIZE,
																				308,
																				GroupLayout.PREFERRED_SIZE))
														.addComponent(
																jspMzIDView,
																GroupLayout.PREFERRED_SIZE,
																535,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap(20, Short.MAX_VALUE)));
		jpMzIdentMLViewHeaderLayout
				.setVerticalGroup(jpMzIdentMLViewHeaderLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpMzIdentMLViewHeaderLayout
										.createSequentialGroup()
										.addGap(20, 20, 20)
										.addGroup(
												jpMzIdentMLViewHeaderLayout
														.createParallelGroup(
																GroupLayout.Alignment.TRAILING)
														.addComponent(
																jlFileNameMzID)
														.addComponent(
																jlFileNameMzIDText,
																GroupLayout.PREFERRED_SIZE,
																14,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jspMzIDView,
												GroupLayout.PREFERRED_SIZE, 52,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap(17, Short.MAX_VALUE)));

		JSplitPane jspMzIdDetail = new JSplitPane();
		jspMzIdDetail.setDividerLocation(300);
		jspMzIdDetail.setDividerSize(1);
		jspMzIdDetail.setOrientation(JSplitPane.VERTICAL_SPLIT);
		jspMzIdDetail.setTopComponent(jpMzIdMenu);
		jspMzIdDetail.setRightComponent(jspMzIDProtGroup);

		JSplitPane jspMzId = new JSplitPane();
		jspMzId.setDividerLocation(110);
		jspMzId.setDividerSize(1);
		jspMzId.setOrientation(JSplitPane.VERTICAL_SPLIT);
		jspMzId.setRightComponent(jspMzIdDetail);
		jspMzId.setLeftComponent(jpMzIdentMLViewHeader);

		JPanel jpMzId = new JPanel();
		GroupLayout jpMzIdLayout = new GroupLayout(jpMzId);
		jpMzId.setLayout(jpMzIdLayout);
		jpMzIdLayout.setHorizontalGroup(jpMzIdLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addComponent(jspMzId));
		jpMzIdLayout.setVerticalGroup(jpMzIdLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addComponent(jspMzId,
				GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 585,
				Short.MAX_VALUE));

		return jpMzId;
	}

	private JPanel getMascotXML(JEditorPane jepMascotXMLView,
			JTable jtMascotXMLView) {

		JToolBar jtbMzIdentMLOptions1 = new JToolBar();
		jtbMzIdentMLOptions1.setBorder(BorderFactory.createEtchedBorder());
		jtbMzIdentMLOptions1.setFloatable(false);
		jtbMzIdentMLOptions1.setMaximumSize(new Dimension(30, 23));
		jtbMzIdentMLOptions1.setMinimumSize(new Dimension(30, 23));
		jtbMzIdentMLOptions1.setPreferredSize(new Dimension(100, 23));

		jbExportMascotXMLExcel.setMaximumSize(new Dimension(28, 24));
		jbExportMascotXMLExcel.setMinimumSize(new Dimension(28, 24));
		jbExportMascotXMLExcel.setPreferredSize(new Dimension(28, 24));
		jbExportMascotXMLExcel.addMouseListener(new MouseListenerExcelExport(
				jtMascotXMLView));

		jtbMzIdentMLOptions1.add(jbExportMascotXMLExcel);

		final JTextField jtProtein = new JTextField();
		jtProtein
				.addKeyListener(new KeyListenerSearch(1, jtMascotXMLView, true));

		JLabel jlProtein = new JLabel("Protein:");

		JLabel jlSearchMascotXML = new JLabel("Search:");
		jlSearchMascotXML.setFont(new Font("Tahoma", 1, 11)); // NOI18N

		JLabel jlPeptide = new JLabel("Peptide:");

		final JTextField jtPeptide = new JTextField();
		jtPeptide
				.addKeyListener(new KeyListenerSearch(2, jtMascotXMLView, true));

		JLabel jlExportMzIDXLS1 = new JLabel("Export to:");

		JScrollPane jspMascotXMLDetail = new JScrollPane();
		jspMascotXMLDetail.setViewportView(jtMascotXMLView);

		JPanel jpMascotXMLMenu = new JPanel();
		GroupLayout jpMascotXMLMenuLayout = new GroupLayout(jpMascotXMLMenu);
		jpMascotXMLMenu.setLayout(jpMascotXMLMenuLayout);
		jpMascotXMLMenuLayout
				.setHorizontalGroup(jpMascotXMLMenuLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpMascotXMLMenuLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpMascotXMLMenuLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING,
																false)
														.addGroup(
																jpMascotXMLMenuLayout
																		.createSequentialGroup()
																		.addComponent(
																				jlSearchMascotXML)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				jlProtein)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				jtProtein,
																				GroupLayout.PREFERRED_SIZE,
																				66,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(14,
																				14,
																				14)
																		.addComponent(
																				jlPeptide)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				jtPeptide,
																				GroupLayout.PREFERRED_SIZE,
																				211,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																jpMascotXMLMenuLayout
																		.createSequentialGroup()
																		.addComponent(
																				jlExportMzIDXLS1)
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(
																				jtbMzIdentMLOptions1,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)))
										.addContainerGap(112, Short.MAX_VALUE)));
		jpMascotXMLMenuLayout
				.setVerticalGroup(jpMascotXMLMenuLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpMascotXMLMenuLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpMascotXMLMenuLayout
														.createParallelGroup(
																GroupLayout.Alignment.BASELINE)
														.addComponent(
																jtPeptide,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																jlSearchMascotXML)
														.addComponent(jlProtein)
														.addComponent(
																jtProtein,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(jlPeptide))
										.addGroup(
												jpMascotXMLMenuLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																jpMascotXMLMenuLayout
																		.createSequentialGroup()
																		.addGap(16,
																				16,
																				16)
																		.addComponent(
																				jlExportMzIDXLS1))
														.addGroup(
																jpMascotXMLMenuLayout
																		.createSequentialGroup()
																		.addPreferredGap(
																				LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				jtbMzIdentMLOptions1,
																				GroupLayout.PREFERRED_SIZE,
																				35,
																				GroupLayout.PREFERRED_SIZE)))
										.addContainerGap(
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

		JLabel jlFileNameMascotXML = new JLabel("File:");
		jlFileNameMascotXML.setFont(new Font("Tahoma", 1, 11)); // NOI18N
		final JLabel jlFileNameMascotXMLText = new JLabel();
		JScrollPane jspMascotXMLView = new JScrollPane();
		jspMascotXMLView.setViewportView(jepMascotXMLView);

		JPanel jpMascotXMLViewHeader = new JPanel();
		GroupLayout jpMascotXMLViewHeaderLayout = new GroupLayout(
				jpMascotXMLViewHeader);
		jpMascotXMLViewHeader.setLayout(jpMascotXMLViewHeaderLayout);
		jpMascotXMLViewHeaderLayout
				.setHorizontalGroup(jpMascotXMLViewHeaderLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpMascotXMLViewHeaderLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpMascotXMLViewHeaderLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																jpMascotXMLViewHeaderLayout
																		.createSequentialGroup()
																		.addComponent(
																				jlFileNameMascotXML)
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				jlFileNameMascotXMLText,
																				GroupLayout.PREFERRED_SIZE,
																				283,
																				GroupLayout.PREFERRED_SIZE))
														.addComponent(
																jspMascotXMLView,
																GroupLayout.PREFERRED_SIZE,
																535,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap(20, Short.MAX_VALUE)));
		jpMascotXMLViewHeaderLayout
				.setVerticalGroup(jpMascotXMLViewHeaderLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpMascotXMLViewHeaderLayout
										.createSequentialGroup()
										.addGap(18, 18, 18)
										.addGroup(
												jpMascotXMLViewHeaderLayout
														.createParallelGroup(
																GroupLayout.Alignment.TRAILING)
														.addComponent(
																jlFileNameMascotXMLText,
																GroupLayout.PREFERRED_SIZE,
																14,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																jlFileNameMascotXML))
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jspMascotXMLView,
												GroupLayout.PREFERRED_SIZE, 52,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap(19, Short.MAX_VALUE)));

		JSplitPane jspMascotXMLSubDetail = new JSplitPane();
		jspMascotXMLSubDetail.setDividerLocation(80);
		jspMascotXMLSubDetail.setDividerSize(1);
		jspMascotXMLSubDetail.setOrientation(JSplitPane.VERTICAL_SPLIT);
		jspMascotXMLSubDetail.setRightComponent(jspMascotXMLDetail);
		jspMascotXMLSubDetail.setLeftComponent(jpMascotXMLMenu);

		JSplitPane jspMascotXML = new JSplitPane();
		jspMascotXML.setDividerLocation(110);
		jspMascotXML.setDividerSize(1);
		jspMascotXML.setOrientation(JSplitPane.VERTICAL_SPLIT);
		jspMascotXML.setRightComponent(jspMascotXMLSubDetail);
		jspMascotXML.setLeftComponent(jpMascotXMLViewHeader);

		JPanel jpMascotXML = new JPanel();
		GroupLayout jpMascotXMLLayout = new GroupLayout(jpMascotXML);
		jpMascotXML.setLayout(jpMascotXMLLayout);
		jpMascotXMLLayout.setHorizontalGroup(jpMascotXMLLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspMascotXML));
		jpMascotXMLLayout.setVerticalGroup(jpMascotXMLLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspMascotXML, GroupLayout.DEFAULT_SIZE, 585,
						Short.MAX_VALUE));

		return jpMascotXML;
	}

	private JPanel getMzQuantMLPanel(JTabbedPane jtpMzQuantMLDetail,
			JLabel jlFileNameMzQText, JEditorPane jepMZQView) {
		JScrollPane jScrollPane12 = new JScrollPane();
		jScrollPane12.setViewportView(jepMZQView);

		JLabel jlFileNameMzQ = new JLabel("File:");
		jlFileNameMzQ.setFont(new Font("Tahoma", 1, 11)); // NOI18N

		JPanel jpMzQuantMLHeader = new JPanel();
		GroupLayout jpMzQuantMLHeaderLayout = new GroupLayout(jpMzQuantMLHeader);
		jpMzQuantMLHeader.setLayout(jpMzQuantMLHeaderLayout);
		jpMzQuantMLHeaderLayout
				.setHorizontalGroup(jpMzQuantMLHeaderLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpMzQuantMLHeaderLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpMzQuantMLHeaderLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																jpMzQuantMLHeaderLayout
																		.createSequentialGroup()
																		.addComponent(
																				jlFileNameMzQ)
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				jlFileNameMzQText,
																				GroupLayout.PREFERRED_SIZE,
																				265,
																				GroupLayout.PREFERRED_SIZE))
														.addComponent(
																jScrollPane12,
																GroupLayout.PREFERRED_SIZE,
																535,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap(20, Short.MAX_VALUE)));
		jpMzQuantMLHeaderLayout
				.setVerticalGroup(jpMzQuantMLHeaderLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								jpMzQuantMLHeaderLayout
										.createSequentialGroup()
										.addGap(19, 19, 19)
										.addGroup(
												jpMzQuantMLHeaderLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING,
																false)
														.addComponent(
																jlFileNameMzQ,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																jlFileNameMzQText,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE))
										.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jScrollPane12,
												GroupLayout.PREFERRED_SIZE, 52,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap(13, Short.MAX_VALUE)));

		JSplitPane jspMzQuantML = new JSplitPane();
		jspMzQuantML.setDividerLocation(110);
		jspMzQuantML.setDividerSize(1);
		jspMzQuantML.setOrientation(JSplitPane.VERTICAL_SPLIT);
		jspMzQuantML.setRightComponent(jtpMzQuantMLDetail);
		jspMzQuantML.setLeftComponent(jpMzQuantMLHeader);

		JPanel jpMzQuantML = new JPanel();
		GroupLayout jpMzQuantMLLayout = new GroupLayout(jpMzQuantML);
		jpMzQuantML.setLayout(jpMzQuantMLLayout);
		jpMzQuantMLLayout.setHorizontalGroup(jpMzQuantMLLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspMzQuantML));
		jpMzQuantMLLayout.setVerticalGroup(jpMzQuantMLLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jspMzQuantML));

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
