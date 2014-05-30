/*
 * ProteoIDViewer.java
 *
 * Created on 15-Dec-2010, 10:34:37
 * 
 * Fawaz Ghali
 * 
 */
package uk.ac.liv.proteoidviewer;

import uk.ac.liv.proteoidviewer.listener.exportFDRActionPerformed;
import uk.ac.liv.proteoidviewer.listener.ExportListener;
import uk.ac.liv.proteoidviewer.listener.mainTabbedPaneMouseClicked;
import uk.ac.liv.proteoidviewer.listener.openMenuItemActionPerformed;
import uk.ac.liv.proteoidviewer.tabs.GlobalStatisticsPanel;
import uk.ac.liv.proteoidviewer.tabs.PeptideSummary;
import uk.ac.liv.proteoidviewer.tabs.ProteinDBView;
import uk.ac.liv.proteoidviewer.tabs.ProteinView;
import uk.ac.liv.proteoidviewer.tabs.ProtocolPanel;
import uk.ac.liv.proteoidviewer.tabs.SpectrumSummary;
import uk.ac.liv.proteoidviewer.util.IdViewerUtils;
import uk.ac.liv.proteoidviewer.util.XmlFilter;
import uk.ac.liv.proteoidviewer.util.OmssaFilter;
import uk.ac.liv.proteoidviewer.util.SourceFileFilter;

import com.compomics.util.gui.interfaces.SpectrumAnnotation;
import com.compomics.util.gui.spectrum.DefaultSpectrumAnnotation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import uk.ac.ebi.jmzidml.model.mzidml.CvParam;
import uk.ac.ebi.jmzidml.model.mzidml.Fragmentation;
import uk.ac.ebi.jmzidml.model.mzidml.IonType;
import uk.ac.ebi.jmzidml.model.mzidml.Modification;
import uk.ac.ebi.jmzidml.model.mzidml.Peptide;
import uk.ac.ebi.jmzidml.model.mzidml.PeptideEvidence;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationItem;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationList;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationResult;
import uk.ac.ebi.jmzidml.xml.io.MzIdentMLUnmarshaller;
import uk.ac.liv.proteoidviewer.util.MzIdentMLFilter;
import uk.ac.liv.proteoidviewer.util.ProgressBarDialog;

import com.compomics.util.gui.spectrum.SpectrumPanel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;

import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu.Separator;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang.StringEscapeUtils;

import uk.ac.liv.mzidlib.FalseDiscoveryRate;
import uk.ac.ebi.jmzidml.MzIdentMLElement;
import uk.ac.ebi.jmzidml.model.mzidml.PeptideEvidenceRef;
import uk.ac.ebi.pride.tools.jmzreader.JMzReader;
import uk.ac.ebi.pride.tools.jmzreader.JMzReaderException;
import uk.ac.ebi.pride.tools.jmzreader.model.Spectrum;
import uk.ac.ebi.pride.tools.mgf_parser.MgfFile;
import uk.ac.ebi.pride.tools.mzml_wrapper.MzMlWrapper;
import uk.ac.liv.mzidconverters.Tandem2mzid;

/**
 *
 * @author Fawaz Ghali
 */
public class ProteoIDViewer extends JFrame {
	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		if (args != null && args.length == 1) {
			ProteoIDViewer proteoIDViewer = new ProteoIDViewer();
			proteoIDViewer = new ProteoIDViewer();
			proteoIDViewer.setVisible(true);
			proteoIDViewer.openCLI(args[0]);
		} else {
			EventQueue.invokeLater(new Runnable() {

				@Override
				public void run() {
					new ProteoIDViewer().setVisible(true);
				}
			});
		}
	}

	// GUI
	private final List<String> filterListIon = new ArrayList<>();
	private final List<String> filterListCharge = new ArrayList<>();
	private final List<SpectrumAnnotation> peakAnnotation = new ArrayList<>();
	// FDR and Stats
	// stats
	public FalseDiscoveryRate falseDiscoveryRate = null;
	public double falsePositiveSii;
	public double truePositiveSii;
	public double fdrSii;
	// Lists to store mzIdentML data
	private List<SpectrumIdentificationItem> spectrumIdentificationItemListForSpecificResult;
	public boolean secondTab;
	public boolean thirdTab;
	public boolean fourthTab;
	public boolean fifthTab;
	public boolean sixthTab;
	public JMzReader jmzreader = null;
	public File fileName = null;
	public String sourceFile = "";
	// maps to store mzIdentML data

	public MzIdentMLUnmarshaller mzIdentMLUnmarshaller = null;
	
	private final JFileChooser fileChooser = new JFileChooser();

	private final GlobalStatisticsPanel globalStatisticsPanel;
	private final PeptideSummary peptideSummary;
	private final ProteinDBView proteinDBView = new ProteinDBView();
	private final ProteinView proteinView;
	private final ProtocolPanel protocolPanel = new ProtocolPanel();
	private final SpectrumSummary spectrumSummary;

	/**
	 * Creates new form ProteoIDViewer
	 */
	public ProteoIDViewer() {		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("ProteoIDViewer");
		setMinimumSize(new Dimension(900, 800));
		final JTabbedPane mainTabbedPane = new JTabbedPane();
		setJMenuBar(createMenuBar(mainTabbedPane));

		// Swing init components

		spectrumSummary = new SpectrumSummary(this, proteinDBView,
				mainTabbedPane);
		globalStatisticsPanel = new GlobalStatisticsPanel(this,
				mainTabbedPane);
		peptideSummary = new PeptideSummary(this);
		proteinView = new ProteinView(this, mainTabbedPane, spectrumSummary);
		
		
		mainTabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		mainTabbedPane.setToolTipText("Global Statistics");
		mainTabbedPane.setPreferredSize(new Dimension(889, 939));
		mainTabbedPane.addMouseListener(new mainTabbedPaneMouseClicked(this,
				mainTabbedPane,
				protocolPanel, proteinDBView, spectrumSummary,
				peptideSummary, globalStatisticsPanel));
		mainTabbedPane
				.addTab("Protein View", null, proteinView, "Protein View");

		mainTabbedPane.addTab("Spectrum Summary", null, spectrumSummary,
				"Spectrum Summary");

		mainTabbedPane.addTab("Peptide Summary", peptideSummary);
		mainTabbedPane.addTab("Protein DB View", proteinDBView);

		mainTabbedPane.addTab("Global Statistics", null, globalStatisticsPanel,
				"Global Statistics");
		mainTabbedPane.addTab("Protocols", protocolPanel);

		getContentPane().add(mainTabbedPane);
		getAccessibleContext().setAccessibleDescription("MzIdentML Viewer");

		pack();

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(dim.width, dim.height - 40);
		setLocationRelativeTo(getRootPane());

		fileChooser.setCurrentDirectory(null);
		fileChooser.addChoosableFileFilter(new MzIdentMLFilter());
		fileChooser.addChoosableFileFilter(new OmssaFilter());
		fileChooser.addChoosableFileFilter(new XmlFilter());
		fileChooser.addChoosableFileFilter(new MzIdentMLFilter());
		repaint();
	}

	public boolean checkIfSpectrumIdentificationItemIsDecoy(
			SpectrumIdentificationItem spectrumIdentificationItem,
			MzIdentMLUnmarshaller mzIdentMLUnmarshaller) {
		boolean result = false;

		List<PeptideEvidenceRef> peptideEvidenceRefList = spectrumIdentificationItem
				.getPeptideEvidenceRef();
		for (int i = 0; i < peptideEvidenceRefList.size(); i++) {
			try {
				PeptideEvidenceRef peptideEvidenceRef = peptideEvidenceRefList
						.get(i);
				PeptideEvidence peptiedEvidence = mzIdentMLUnmarshaller
						.unmarshal(PeptideEvidence.class,
								peptideEvidenceRef.getPeptideEvidenceRef());
				if (peptiedEvidence != null && peptiedEvidence.isIsDecoy()) {
					result = true;
					break;
				}
			} catch (JAXBException ex) {
				Logger.getLogger(ProteoIDViewer.class.getName()).log(
						Level.SEVERE, null, ex);
			}

		}
		return result;
	}

	public void clearSummaryStats() {
		globalStatisticsPanel.reset();
	}

	private JMenuBar createMenuBar(JTabbedPane mainTabbedPane) {
		final JMenuBar jMenuBar = new JMenuBar();

		final JMenu fileMenu = new JMenu("File");
		final JMenuItem openMenuItem = new JMenuItem();
		openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				InputEvent.SHIFT_MASK | InputEvent.CTRL_MASK));
		openMenuItem.setText("Open");
		openMenuItem.setToolTipText("Open");
		openMenuItem
				.addActionListener(new openMenuItemActionPerformed(this,
						fileChooser, mainTabbedPane, proteinView,
						globalStatisticsPanel));
		fileMenu.add(openMenuItem);
		fileMenu.add(new Separator());

		jMenuBar.add(fileMenu);

		final JMenu exportMenu = new JMenu("Export");
		final JMenuItem jMenuItem1 = new JMenuItem("Export Proteins Only");
		final JMenuItem jMenuItem2 = new JMenuItem("Export Protein Groups");
		final JMenuItem jMenuItem3 = new JMenuItem("Export PSMs");

		jMenuItem1.addActionListener(new ExportListener(this,
				ExportListener.EXPORT_TYPE_PROTEIN));
		exportMenu.add(jMenuItem1);

		jMenuItem2.addActionListener(new ExportListener(this,
				ExportListener.EXPORT_TYPE_PROTEIN_GROUP));
		exportMenu.add(jMenuItem2);

		jMenuItem3.addActionListener(new ExportListener(this,
				ExportListener.EXPORT_TYPE_PSM));

		exportMenu.add(jMenuItem3);
		exportMenu.add(new Separator());

		final JMenuItem exportFDR = new JMenuItem("Export FDR as CSV");
		exportFDR.addActionListener(new exportFDRActionPerformed(this));
		exportMenu.add(exportFDR);

		jMenuBar.add(exportMenu);

		return jMenuBar;

	}

	public void createTables() {
		globalStatisticsPanel.reset();
		proteinDBView.reset();

		// protein view
		proteinView.reset();

		// spectrum view
		int spectrumIdentificationResultCvParamLengs = 0;
		int spectrumIdentificationItemCvParamLengs = 0;
		String[] sir = null;
		String[] sii = null;
		List<SpectrumIdentificationResult> spectrumIdentificationResultList = new ArrayList<>();

		Iterator<SpectrumIdentificationList> iterspectrumIdentificationList = mzIdentMLUnmarshaller
				.unmarshalCollectionFromXpath(MzIdentMLElement.SpectrumIdentificationList);
		while (iterspectrumIdentificationList.hasNext()) {
			SpectrumIdentificationList spectrumIdentificationList = iterspectrumIdentificationList
					.next();

			for (int j = 0; j < spectrumIdentificationList
					.getSpectrumIdentificationResult().size(); j++) {
				spectrumIdentificationResultList.add(spectrumIdentificationList
						.getSpectrumIdentificationResult().get(j));
			}
		}

		if (spectrumIdentificationResultList.size() > 0) {

			for (int i = 0; i < spectrumIdentificationResultList.size(); i++) {
				SpectrumIdentificationResult spectrumIdentificationResult = spectrumIdentificationResultList
						.get(i);
				if (spectrumIdentificationResultCvParamLengs < spectrumIdentificationResult
						.getCvParam().size()) {
					spectrumIdentificationResultCvParamLengs = spectrumIdentificationResult
							.getCvParam().size();
					sir = new String[spectrumIdentificationResult.getCvParam()
							.size()];
					for (int j = 0; j < spectrumIdentificationResult
							.getCvParam().size(); j++) {
						sir[j] = spectrumIdentificationResult.getCvParam()
								.get(j).getName();
					}
				}
				List<SpectrumIdentificationItem> spectrumIdentificationItemList = spectrumIdentificationResult
						.getSpectrumIdentificationItem();
				for (int j = 0; j < spectrumIdentificationItemList.size(); j++) {
					SpectrumIdentificationItem spectrumIdentificationItem = spectrumIdentificationItemList
							.get(j);
					if (spectrumIdentificationItemCvParamLengs < spectrumIdentificationItem
							.getCvParam().size()) {
						spectrumIdentificationItemCvParamLengs = spectrumIdentificationItem
								.getCvParam().size();
					}
					sii = new String[spectrumIdentificationItem.getCvParam()
							.size()];
					for (int k = 0; k < spectrumIdentificationItem.getCvParam()
							.size(); k++) {
						sii[k] = spectrumIdentificationItem.getCvParam().get(k)
								.getName();

					}

				}
			}
		}

		String[] spectrumIdentificationItemTableHeaders = new String[spectrumIdentificationItemCvParamLengs + 8];
		spectrumIdentificationItemTableHeaders[0] = "ID";
		spectrumIdentificationItemTableHeaders[1] = "Peptide Sequence";
		spectrumIdentificationItemTableHeaders[2] = "Modification";
		spectrumIdentificationItemTableHeaders[3] = "Calculated MassToCharge";
		spectrumIdentificationItemTableHeaders[4] = "Experimental MassToCharge";
		spectrumIdentificationItemTableHeaders[5] = "Rank";
		spectrumIdentificationItemTableHeaders[6] = "Is Decoy";
		spectrumIdentificationItemTableHeaders[7] = "PassThreshold";
		if (sii != null) {
			for (int i = 0; i < sii.length; i++) {
				String string = sii[i];

				string = string.replaceAll("\\\\", "");

				spectrumIdentificationItemTableHeaders[8 + i] = string;
			}
		}

		// TODO: Disabled - Andrew
		/*
		 * / if (spectrumIdentificationItemTable.getColumnExt("X!Tandem:expect")
		 * != null) {
		 * spectrumIdentificationItemTable.getColumnExt("X!Tandem:expect"
		 * ).setComparator(new DoubleComparator()); } if
		 * (spectrumIdentificationItemTable.getColumnExt("OMSSA:evalue") !=
		 * null) { spectrumIdentificationItemTable.getColumnExt("OMSSA:evalue").
		 * setComparator(new DoubleComparator()); } if
		 * (spectrumIdentificationItemTable
		 * .getColumnExt("mascot:expectation value") != null) {
		 * spectrumIdentificationItemTable
		 * .getColumnExt("mascot:expectation value").setComparator(new
		 * DoubleComparator()); } if
		 * (spectrumIdentificationItemTable.getColumnExt
		 * ("SEQUEST:expectation value") != null) {
		 * spectrumIdentificationItemTable
		 * .getColumnExt("SEQUEST:expectation value").setComparator(new
		 * DoubleComparator()); } /
		 */

		spectrumSummary.reset(sir, spectrumIdentificationResultCvParamLengs,
				spectrumIdentificationItemTableHeaders);

		//
		peptideSummary.reset(spectrumIdentificationItemTableHeaders);
		// peptideEvidenceTablePeptideView.setAutoCreateRowSorter(true);
	}

	public void loadSpectrumIdentificationList(
			MzIdentMLUnmarshaller mzIdentMLUnmarshaller,
			GlobalStatisticsPanel globalStatisticsPanel) {
		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		List<Peptide> peptideListNonReduntant = new ArrayList<>();
		List<SpectrumIdentificationItem> sIIListIsDecoyTrue = new ArrayList<>();
		List<SpectrumIdentificationItem> sIIListIsDecoyFalse = new ArrayList<>();
		List<SpectrumIdentificationItem> sIIListPassThresholdRankOne = new ArrayList<>();
		List<SpectrumIdentificationItem> sIIListBelowThreshold = new ArrayList<>();
		globalStatisticsPanel.getSIIListPassThreshold().clear();
		try {
			List<SpectrumIdentificationResult> sirListTemp = mzIdentMLUnmarshaller
					.unmarshal(SpectrumIdentificationList.class,
							globalStatisticsPanel.getSiiComboBox().getSelectedItem().toString())
					.getSpectrumIdentificationResult();
			List<SpectrumIdentificationItem> siiListTemp = new ArrayList<>();

			for (int i = 0; i < sirListTemp.size(); i++) {
				SpectrumIdentificationResult spectrumIdentificationResult = sirListTemp
						.get(i);

				for (int j = 0; j < spectrumIdentificationResult
						.getSpectrumIdentificationItem().size(); j++) {
					siiListTemp.add(spectrumIdentificationResult
							.getSpectrumIdentificationItem().get(j));
				}
			}

			if (siiListTemp.size() > 0) {
				SpectrumIdentificationItem spectrumIdentificationItem = siiListTemp
						.get(0);
				List<CvParam> cvParamList = spectrumIdentificationItem
						.getCvParam();
				for (int i = 0; i < cvParamList.size(); i++) {
					CvParam cvParam = cvParamList.get(i);
					if (cvParam.getName() != null
							&& !cvParam.getName().equals("")) {
						boolean isthere = false;
						for (int j = 0; j < globalStatisticsPanel.getJComboBox1().getItemCount(); j++) {
							if (globalStatisticsPanel.getJComboBox1().getItemAt(j).equals(
									cvParam.getName())) {
								isthere = true;
								break;
							}
						}
						if (!isthere) {
							globalStatisticsPanel.getJComboBox1().addItem(cvParam.getName());
							globalStatisticsPanel.getCvTermMap().put(cvParam.getName(),
									cvParam.getAccession());
						}
					}
				}
			}

			for (int j = 0; j < siiListTemp.size(); j++) {
				SpectrumIdentificationItem spectrumIdentificationItem = siiListTemp
						.get(j);

				if (spectrumIdentificationItem.isPassThreshold()) {
					globalStatisticsPanel.getSIIListPassThreshold().add(spectrumIdentificationItem);
					String p_ref = spectrumIdentificationItem.getPeptideRef();

					// Update FG 18-03-2013 escape XML
					Peptide peptide = mzIdentMLUnmarshaller.unmarshal(
							Peptide.class, StringEscapeUtils.escapeXml(p_ref));

					if (!peptideListNonReduntant.contains(peptide)) {
						peptideListNonReduntant.add(peptide);
					}
				} else {
					sIIListBelowThreshold.add(spectrumIdentificationItem);
				}

				if (spectrumIdentificationItem.isPassThreshold()
						&& spectrumIdentificationItem.getRank() == 1) {
					sIIListPassThresholdRankOne.add(spectrumIdentificationItem);
				}

				boolean isdecoy = true;
				List<PeptideEvidenceRef> peRefLst = spectrumIdentificationItem
						.getPeptideEvidenceRef();
				for (int k = 0; k < peRefLst.size(); k++) {
					PeptideEvidenceRef peptideEvidenceRef = peRefLst.get(k);
					PeptideEvidence peptideEvidence = mzIdentMLUnmarshaller
							.unmarshal(PeptideEvidence.class,
									peptideEvidenceRef.getPeptideEvidenceRef());
					if (!peptideEvidence.isIsDecoy()) {
						sIIListIsDecoyFalse.add(spectrumIdentificationItem);
						isdecoy = false;
						break;
					}
				}
				if (isdecoy) {
					sIIListIsDecoyTrue.add(spectrumIdentificationItem);
				}
			}

			int sirSize = mzIdentMLUnmarshaller
					.getObjectCountForXpath(MzIdentMLElement.SpectrumIdentificationResult
							.getXpath());
			globalStatisticsPanel.getTotalSIRLabelValue().setText("" + sirSize);

			if (globalStatisticsPanel.getSIIListPassThreshold() != null) {
				globalStatisticsPanel.getTotalSIIaboveThresholdLabelValue().setText(String
						.valueOf(globalStatisticsPanel.getSIIListPassThreshold().size()));
			}
			if (sIIListBelowThreshold != null) {
				globalStatisticsPanel.getTotalSIIbelowThresholdLabelValue().setText(String
						.valueOf(sIIListBelowThreshold.size()));
			}
			if (sIIListPassThresholdRankOne != null) {
				globalStatisticsPanel.getTotalSIIaboveThresholdRankOneLabelValue().setText(String
						.valueOf(sIIListPassThresholdRankOne.size()));
			}
			if (sIIListPassThresholdRankOne != null && sirSize > 0) {
				double percent = IdViewerUtils
						.roundTwoDecimals((float) sIIListPassThresholdRankOne
								.size() * 100 / sirSize);
				globalStatisticsPanel.getPercentIdentifiedSpectraLabelValue().setText(String
						.valueOf(percent) + "%");
			}
			if (peptideListNonReduntant != null) {
				globalStatisticsPanel.getTotalPeptidesaboveThresholdLabelValue().setText(String
						.valueOf(peptideListNonReduntant.size()));
			}
			int pagSize = mzIdentMLUnmarshaller
					.getObjectCountForXpath(MzIdentMLElement.ProteinAmbiguityGroup
							.getXpath());
			
			globalStatisticsPanel.getTotalPAGsLabelValue().setText("" + pagSize);

			if (globalStatisticsPanel.getPDHListPassThreshold() != null) {
				globalStatisticsPanel.getTotalPDHsaboveThresholdLabelValue().setText(String
						.valueOf(globalStatisticsPanel.getPDHListPassThreshold().size()));
			}
			if (sIIListIsDecoyTrue != null) {
				globalStatisticsPanel.getIsDecoySiiValue().setText(String.valueOf(sIIListIsDecoyTrue
						.size()));
			}
			if (sIIListIsDecoyFalse != null) {
				globalStatisticsPanel.getIsDecoySiiFalseValue().setText(String.valueOf(sIIListIsDecoyFalse
						.size()));
			}
			if (sIIListIsDecoyTrue != null) {
				falsePositiveSii = IdViewerUtils
						.roundThreeDecimals(sIIListIsDecoyTrue.size());
				globalStatisticsPanel.getFpSiiValue().setText(String.valueOf(falsePositiveSii));
			}
			if (sIIListIsDecoyTrue != null && sIIListIsDecoyFalse != null) {
				truePositiveSii = IdViewerUtils
						.roundThreeDecimals(globalStatisticsPanel.getSIIListPassThreshold().size()
								- sIIListIsDecoyTrue.size());
				
				globalStatisticsPanel.getTpSiiValue().setText(String.valueOf(truePositiveSii));
			}
			if (sIIListIsDecoyTrue != null && sIIListIsDecoyFalse != null) {
				if (falsePositiveSii + truePositiveSii == 0) {
					globalStatisticsPanel.getFdrSiiValue().setText("0.0");
				} else {
					fdrSii = IdViewerUtils
							.roundThreeDecimals((falsePositiveSii)
									/ (falsePositiveSii + truePositiveSii));
					globalStatisticsPanel.getFdrSiiValue().setText(String.valueOf(fdrSii));
				}
			}
			// thresholdValue.setText(spectrumIdentificationProtocol.get(0).getThreshold().getCvParam().get(0).getValue());
		} catch (JAXBException ex) {
			Logger.getLogger(ProteoIDViewer.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	private void openCLI(String filename) {
		ProgressBarDialog progressBarDialog = new ProgressBarDialog(this, true);
		final Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				progressBarDialog.setTitle("Parsing the mzid file. Please Wait...");
				progressBarDialog.setVisible(true);
			}
		}, "ProgressBarDialog");

		final File mzid_file = new File(filename);
		setTitle("ProteoIDViewer   -  " + mzid_file.getPath());
		thread.start();

		new Thread("LoadingThread") {

			@Override
			public void run() {
				try {
					if (mzid_file.getPath().endsWith(".gz")) {
						GZIPInputStream gin = new GZIPInputStream(
								new FileInputStream(mzid_file));
						File outFile = new File(mzid_file.getParent(),
								mzid_file.getName().replaceAll("\\.gz$", ""));
						FileOutputStream fos = new FileOutputStream(outFile);
						byte[] buf = new byte[100000];
						int len;
						while ((len = gin.read(buf)) > 0) {
							fos.write(buf, 0, len);
						}
						fos.close();
						gin.close();
					} else if (mzid_file.getPath().endsWith(".omx")) {
						File outFile = new File(
								fileChooser.getCurrentDirectory(), mzid_file
										.getName().replaceAll(".omx", ".mzid"));
						// TODO: Disabled - Andrew
						// new Omssa2mzid(mzid_file.getPath(),
						// outFile.getPath(), false);
					} else if (mzid_file.getPath().endsWith(".xml")) {
						File outFile = new File(
								fileChooser.getCurrentDirectory(), mzid_file
										.getName().replaceAll(".omx", ".mzid"));
						new Tandem2mzid(mzid_file.getPath(), outFile.getPath());
					}
					mzIdentMLUnmarshaller = new MzIdentMLUnmarshaller(mzid_file);
					fileName = mzid_file;

					if (!mzIdentMLUnmarshaller.getMzIdentMLVersion()
							.startsWith("1.1.")
							&& !mzIdentMLUnmarshaller.getMzIdentMLVersion()
									.startsWith("1.2.")) {
						progressBarDialog.setVisible(false);
						progressBarDialog.dispose();
						JOptionPane
								.showMessageDialog(
										null,
										"The file is not compatible with the Viewer: different mzIdentMl version",
										"mzIdentMl version",
										JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					jmzreader = null;
					createTables();
					clearSummaryStats();
					// TODO: work around required
					// mainTabbedPane.setSelectedIndex(0);
					secondTab = false;
					thirdTab = false;
					fourthTab = false;
					fifthTab = false;
					sixthTab = false;
					proteinView.loadProteinAmbiguityGroupTable(
							globalStatisticsPanel, mzIdentMLUnmarshaller);

					progressBarDialog.setVisible(false);
					progressBarDialog.dispose();

					String message = "Do you want to load spectrum source file?";

					int answer = JOptionPane.showConfirmDialog(null, message);
					if (answer == JOptionPane.YES_OPTION) {
						JFileChooser fc = new JFileChooser();
						fc.setCurrentDirectory(fileChooser
								.getCurrentDirectory());

						fc.addChoosableFileFilter(new SourceFileFilter());
						int returnVal1 = fc.showOpenDialog(null);

						if (returnVal1 == JFileChooser.APPROVE_OPTION) {
							try {
								File file = fc.getSelectedFile();
								if (file.getAbsolutePath().toLowerCase()
										.endsWith("mgf")) {
									jmzreader = new MgfFile(file);
									sourceFile = "mgf";
									JOptionPane.showMessageDialog(null,
											file.getName() + " is loaded",
											"Spectrum file",
											JOptionPane.INFORMATION_MESSAGE);
								} else if (file.getAbsolutePath().toLowerCase()
										.endsWith("mzml")) {
									jmzreader = new MzMlWrapper(file);
									sourceFile = "mzML";
									JOptionPane.showMessageDialog(null,
											file.getName() + " is loaded",
											"Spectrum file",
											JOptionPane.INFORMATION_MESSAGE);
								} else {
									JOptionPane.showMessageDialog(null,
											file.getName()
													+ " is not supported",
											"Spectrum file",
											JOptionPane.INFORMATION_MESSAGE);
								}
							} catch (JMzReaderException ex) {
								System.out.println(ex.getMessage());
							}
						}
					}

					if (proteinView.getAmbiguityGroupTable().getRowCount() == 0) {
						JOptionPane
								.showMessageDialog(
										null,
										"There is no protein view for this file",
										"Protein View",
										JOptionPane.INFORMATION_MESSAGE);
					}
					// loadSummaryStats();

				} catch (OutOfMemoryError error) {
					progressBarDialog.setVisible(false);
					progressBarDialog.dispose();
					Runtime.getRuntime().gc();
					JOptionPane.showMessageDialog(null, "Out of Memory Error.",
							"Error", JOptionPane.ERROR_MESSAGE);

					System.exit(0);
				} catch (Exception ex) {
					progressBarDialog.setVisible(false);
					progressBarDialog.dispose();
					System.out.println(ex.getMessage());
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

					String msg = ex.getMessage();

					if (msg.equals("No entry found for ID: null and Class: class uk.ac.ebi.jmzidml.model.mzidml.DBSequence. Make sure the element you are looking for has an ID attribute and is id-mapped!")) {
						msg = "No dbSequence_ref provided from ProteinDetectionHypothesis, please report this error back to the mzid exporter";
					}
					JOptionPane.showMessageDialog(null, msg, "Exception",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}.start();
	}

	/**
	 * Creates spectrum Identification Item Table Mouse Clicked
	 */
	public void spectrumIdentificationItemTableMouseClicked(int row,
			SpectrumSummary spectrumSummary, MzIdentMLUnmarshaller mzIdentMLUnmarshaller) {
		if (row == -1)
			return;

		this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		spectrumSummary.getFragmentationTable().removeAll();
		spectrumSummary.getEvidenceTable().removeAll();

		// TODO: Disabled - Andrew
		// fragmentationTable.scrollRowToVisible(0);
		SpectrumIdentificationItem spectrumIdentificationItem = spectrumIdentificationItemListForSpecificResult
				.get(row);

		if (spectrumIdentificationItem != null) {
			List<PeptideEvidenceRef> peptideEvidenceRefList = spectrumIdentificationItem
					.getPeptideEvidenceRef();
			if (peptideEvidenceRefList != null) {
				for (int i = 0; i < peptideEvidenceRefList.size(); i++) {
					try {
						PeptideEvidenceRef peptideEvidenceRef = peptideEvidenceRefList
								.get(i);

						PeptideEvidence peptideEvidence = mzIdentMLUnmarshaller
								.unmarshal(PeptideEvidence.class,
										peptideEvidenceRef
												.getPeptideEvidenceRef());

						((DefaultTableModel) spectrumSummary.getEvidenceTable()
								.getModel()).addRow(new Object[] {
								peptideEvidence.getStart(),
								peptideEvidence.getEnd(),
								peptideEvidence.getPre(),
								peptideEvidence.getPost(),
								peptideEvidence.isIsDecoy(),
								peptideEvidence.getPeptideRef(),
								peptideEvidence.getDBSequenceRef()
						// "<html><a href=>"
						// +peptideEvidence.getDBSequenceRef()+"</a>"
								});
					} catch (JAXBException ex) {
						Logger.getLogger(ProteoIDViewer.class.getName()).log(
								Level.SEVERE, null, ex);
					}
				}
			}
		}

		Fragmentation fragmentation = spectrumIdentificationItem
				.getFragmentation();

		if (fragmentation != null) {
			List<IonType> ionTypeList = fragmentation.getIonType();
			if (ionTypeList != null) {
				for (int i = 0; i < ionTypeList.size(); i++) {
					IonType ionType = ionTypeList.get(i);
					CvParam cvParam = ionType.getCvParam();
					if (!filterListIon.contains(cvParam.getName())) {
						filterListIon.add(cvParam.getName());
					}
					if (!filterListCharge.contains(String.valueOf(ionType
							.getCharge()))) {
						filterListCharge
								.add(String.valueOf(ionType.getCharge()));
					}
					List<Float> m_mz = ionType.getFragmentArray().get(0)
							.getValues();
					List<Float> m_intensity = ionType.getFragmentArray().get(1)
							.getValues();
					List<Float> m_error = ionType.getFragmentArray().get(2)
							.getValues();
					String type = cvParam.getName();
					type = type.replaceAll("param: ", "");
					type = type.replaceAll(" ion", "");

					if (m_mz != null && !m_mz.isEmpty()) {
						for (int j = 0; j < m_mz.size(); j++) {
							((DefaultTableModel) spectrumSummary
									.getFragmentationTable().getModel())
									.addRow(new Object[] { m_mz.get(j),
											m_intensity.get(j), m_error.get(j),
											type + ionType.getIndex().get(j),
											ionType.getCharge() });

						}
					}
				}
			}

			double[] mzValuesAsDouble = new double[spectrumSummary
					.getFragmentationTable().getModel().getRowCount()];
			double[] intensityValuesAsDouble = new double[spectrumSummary
					.getFragmentationTable().getModel().getRowCount()];
			double[] m_errorValuesAsDouble = new double[spectrumSummary
					.getFragmentationTable().getModel().getRowCount()];
			peakAnnotation.clear();
			for (int k = 0; k < spectrumSummary.getFragmentationTable()
					.getModel().getRowCount(); k++) {
				mzValuesAsDouble[k] = (double) (spectrumSummary
						.getFragmentationTable().getModel().getValueAt(k, 0));

				intensityValuesAsDouble[k] = (double) (spectrumSummary
						.getFragmentationTable().getModel().getValueAt(k, 1));
				m_errorValuesAsDouble[k] = (double) (spectrumSummary
						.getFragmentationTable().getModel().getValueAt(k, 2));

				String type = (String) spectrumSummary.getFragmentationTable()
						.getModel().getValueAt(k, 3);
				type = type.replaceFirst("frag:", "");
				type = type.replaceFirst("ion", "");
				type = type.replaceFirst("internal", "");

				peakAnnotation.add(new DefaultSpectrumAnnotation(
						mzValuesAsDouble[k], m_errorValuesAsDouble[k],
						Color.blue, type));
			}
			spectrumSummary.getGraph().removeAll();

			spectrumSummary.getGraph().validate();
			spectrumSummary.getGraph().repaint();
			if (jmzreader != null) {
				try {
					int row1 = spectrumSummary.getIdentificationResultTable()
							.getSelectedRow();
					String sir_id = (String) spectrumSummary
							.getIdentificationResultTable().getModel()
							.getValueAt(row1, 0);
					// System.out.println(sir_id);
					SpectrumIdentificationResult spectrumIdentificationResult = mzIdentMLUnmarshaller
							.unmarshal(SpectrumIdentificationResult.class,
									sir_id);
					Spectrum spectrum = null;
					String spectrumID = spectrumIdentificationResult
							.getSpectrumID();
					if (sourceFile.equals("mgf")) {
						String spectrumIndex = spectrumID.substring(6);
						Integer index1 = Integer.valueOf(spectrumIndex) + 1;
						spectrum = jmzreader.getSpectrumById(index1.toString());
					} else if (sourceFile.equals("mzML")) {
						spectrum = jmzreader.getSpectrumById(spectrumID);
					}

					List<Double> mzValues;
					if (spectrum.getPeakList() != null) {
						mzValues = new ArrayList<Double>(spectrum.getPeakList()
								.keySet());
					} else {
						mzValues = Collections.emptyList();
					}

					double[] mz = new double[mzValues.size()];
					double[] intensities = new double[mzValues.size()];

					int index = 0;
					for (double mzValue : mzValues) {
						mz[index] = mzValue;
						intensities[index] = spectrum.getPeakList()
								.get(mzValue);
						index++;
					}
					SpectrumPanel spectrumPanel = new SpectrumPanel(mz,
							intensities,
							spectrumIdentificationItem
									.getExperimentalMassToCharge(),
							String.valueOf(spectrumIdentificationItem
									.getChargeState()),
							spectrumIdentificationItem.getName());
					spectrumPanel.setAnnotations(peakAnnotation);
					spectrumSummary.getGraph().setLayout(new BorderLayout());
					spectrumSummary.getGraph().setLayout(new BoxLayout(spectrumSummary.getGraph(), BoxLayout.LINE_AXIS));
					spectrumSummary.getGraph().add(spectrumPanel);
					spectrumSummary.getGraph().validate();
					spectrumSummary.getGraph().repaint();
				} catch (JMzReaderException ex) {
					Logger.getLogger(ProteoIDViewer.class.getName()).log(
							Level.SEVERE, null, ex);
				} catch (JAXBException ex) {
					Logger.getLogger(ProteoIDViewer.class.getName()).log(
							Level.SEVERE, null, ex);
				}

			} else if (mzValuesAsDouble.length > 0) {
				SpectrumPanel spectrumPanel = new SpectrumPanel(
						mzValuesAsDouble, intensityValuesAsDouble,
						spectrumIdentificationItem
								.getExperimentalMassToCharge(),
						String.valueOf(spectrumIdentificationItem
								.getChargeState()),
						spectrumIdentificationItem.getName());

				spectrumPanel.setAnnotations(peakAnnotation);

				spectrumSummary.getGraph().setLayout(new BorderLayout());
				spectrumSummary.getGraph().setLayout(new BoxLayout(spectrumSummary.getGraph(), BoxLayout.LINE_AXIS));
				spectrumSummary.getGraph().add(spectrumPanel);
				spectrumSummary.getGraph().validate();
				spectrumSummary.getGraph().repaint();
				this.repaint();
			}
		}

		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * Creates spectrum Identification Result Table Mouse Clicked
	 */
	public void spectrumIdentificationResultTableMouseClicked(
			SpectrumSummary spectrumSummary, MzIdentMLUnmarshaller mzIdentMLUnmarshaller) {
		int row = spectrumSummary.getIdentificationResultTable()
				.getSelectedRow();
		if (row == -1)
			return;

		this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		// row =
		// spectrumIdentificationResultTable.convertRowIndexToModel(row);
		try {
			spectrumSummary.getIdentificationItemTable().removeAll();
			spectrumSummary.getEvidenceTable().removeAll();
			spectrumSummary.getFragmentationTable().removeAll();
			spectrumSummary.getGraph().removeAll();

			spectrumSummary.getGraph().validate();
			spectrumSummary.getGraph().repaint();
			// TODO: Disabled - Andrew
			// spectrumIdentificationItemTable.scrollRowToVisible(0);
			String sir_id = (String) spectrumSummary
					.getIdentificationResultTable().getModel()
					.getValueAt(row, 0);
			SpectrumIdentificationResult spectrumIdentificationResult = mzIdentMLUnmarshaller
					.unmarshal(SpectrumIdentificationResult.class, sir_id);
			if (jmzreader != null) {

				Spectrum spectrum = null;
				String spectrumID = spectrumIdentificationResult
						.getSpectrumID();
				if (sourceFile.equals("mgf")) {
					String spectrumIndex = spectrumID.substring(6);
					Integer index1 = Integer.valueOf(spectrumIndex) + 1;
					spectrum = jmzreader.getSpectrumById(index1.toString());
				}
				if (sourceFile.equals("mzML")) {
					spectrum = jmzreader.getSpectrumById(spectrumID);
				}

				List<Double> mzValues;
				if (spectrum.getPeakList() != null) {
					mzValues = new ArrayList<Double>(spectrum.getPeakList()
							.keySet());
				} else {
					mzValues = Collections.emptyList();
				}

				double[] mz = new double[mzValues.size()];
				double[] intensities = new double[mzValues.size()];

				int index = 0;
				peakAnnotation.clear();
				for (double mzValue : mzValues) {
					mz[index] = mzValue;
					intensities[index] = spectrum.getPeakList().get(mzValue);

					// peakAnnotation.add(
					// new DefaultSpectrumAnnotation(
					// mz[index],
					// intensities[index],
					// Color.blue,
					// ""));
					index++;
				}

				SpectrumPanel spectrumPanel = new SpectrumPanel(mz,
						intensities, 0.0, "", "");
				spectrumPanel.setAnnotations(peakAnnotation);
				spectrumSummary.getGraph().setLayout(new BorderLayout());
				spectrumSummary.getGraph().setLayout(new BoxLayout(spectrumSummary.getGraph(), BoxLayout.LINE_AXIS));
				spectrumSummary.getGraph().add(spectrumPanel);
				spectrumSummary.getGraph().validate();
				spectrumSummary.getGraph().repaint();
			}

			spectrumIdentificationItemListForSpecificResult = spectrumIdentificationResult
					.getSpectrumIdentificationItem();
			if (spectrumIdentificationItemListForSpecificResult.size() > 0) {

				for (int i = 0; i < spectrumIdentificationItemListForSpecificResult
						.size(); i++) {
					try {
						SpectrumIdentificationItem spectrumIdentificationItem = spectrumIdentificationItemListForSpecificResult
								.get(i);
						boolean isDecoy = checkIfSpectrumIdentificationItemIsDecoy(
								spectrumIdentificationItem,
								mzIdentMLUnmarshaller);

						Peptide peptide = mzIdentMLUnmarshaller.unmarshal(
								Peptide.class,
								spectrumIdentificationItem.getPeptideRef());
						if (peptide != null) {
							List<Modification> modificationList = peptide
									.getModification();
							Modification modification;
							String residues = null;
							Integer location = null;
							String modificationName = null;
							CvParam modificationCvParam;
							String combine = null;
							if (modificationList.size() > 0) {
								modification = modificationList.get(0);
								location = modification.getLocation();
								if (modification.getResidues().size() > 0) {
									residues = modification.getResidues()
											.get(0);
								}
								List<CvParam> modificationCvParamList = modification
										.getCvParam();
								if (modificationCvParamList.size() > 0) {
									modificationCvParam = modificationCvParamList
											.get(0);
									modificationName = modificationCvParam
											.getName();
								}
							}
							if (modificationName != null) {
								combine = modificationName;
							}
							if (residues != null) {
								combine = combine + " on residues: " + residues;
							}
							if (location != null) {
								combine = combine + " at location: " + location;
							}
							double calculatedMassToCharge = 0;
							if (spectrumIdentificationItem
									.getCalculatedMassToCharge() != null) {
								calculatedMassToCharge = spectrumIdentificationItem
										.getCalculatedMassToCharge()
										.doubleValue();
							}
							((DefaultTableModel) spectrumSummary
									.getIdentificationItemTable().getModel())
									.addRow(new Object[] {
											spectrumIdentificationItem.getId(),
											peptide.getPeptideSequence(),
											combine,
											IdViewerUtils
													.roundTwoDecimals(calculatedMassToCharge),
											IdViewerUtils
													.roundTwoDecimals(spectrumIdentificationItem
															.getExperimentalMassToCharge()),
											Integer.valueOf(spectrumIdentificationItem
													.getRank()),
											isDecoy,
											spectrumIdentificationItem
													.isPassThreshold() });

							List<CvParam> cvParamListspectrumIdentificationItem = spectrumIdentificationItem
									.getCvParam();

							for (int s = 0; s < cvParamListspectrumIdentificationItem
									.size(); s++) {
								CvParam cvParam = cvParamListspectrumIdentificationItem
										.get(s);
								String accession = cvParam.getAccession();

								if (cvParam.getName().equals(
										"peptide unique to one protein")) {
									((DefaultTableModel) spectrumSummary
											.getIdentificationItemTable()
											.getModel())
											.setValueAt(
													1,
													((DefaultTableModel) spectrumSummary
															.getIdentificationItemTable()
															.getModel())
															.getRowCount() - 1,
													8 + s);
								} else if (accession.equals("MS:1001330")
										|| accession.equals("MS:1001172")
										|| accession.equals("MS:1001159")
										|| accession.equals("MS:1001328")) {
									// ((DefaultTableModel)
									// spectrumIdentificationItemTable.getModel()).setValueAt(roundScientificNumbers(Double.valueOf(cvParam.getValue()).doubleValue()),
									// ((DefaultTableModel)
									// spectrumIdentificationItemTable.getModel()).getRowCount()
									// - 1, 8 + s);
									((DefaultTableModel) spectrumSummary
											.getIdentificationItemTable()
											.getModel())
											.setValueAt(
													cvParam.getValue(),
													((DefaultTableModel) spectrumSummary
															.getIdentificationItemTable()
															.getModel())
															.getRowCount() - 1,
													8 + s);
								} else {
									((DefaultTableModel) spectrumSummary
											.getIdentificationItemTable()
											.getModel())
											.setValueAt(
													cvParam.getValue(),
													((DefaultTableModel) spectrumSummary
															.getIdentificationItemTable()
															.getModel())
															.getRowCount() - 1,
													8 + s);
								}
							}

						}
					} catch (JAXBException ex) {
						Logger.getLogger(ProteoIDViewer.class.getName()).log(
								Level.SEVERE, null, ex);
					}
				}
			}
		} catch (JMzReaderException ex) {
			Logger.getLogger(ProteoIDViewer.class.getName()).log(Level.SEVERE,
					null, ex);
		} catch (JAXBException ex) {
			Logger.getLogger(ProteoIDViewer.class.getName()).log(Level.SEVERE,
					null, ex);
		}

		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}
}