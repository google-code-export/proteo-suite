package uk.ac.liv.proteoidviewer.listener;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.GZIPInputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import uk.ac.ebi.jmzidml.xml.io.MzIdentMLUnmarshaller;
import uk.ac.ebi.pride.tools.jmzreader.JMzReaderException;
import uk.ac.ebi.pride.tools.mgf_parser.MgfFile;
import uk.ac.ebi.pride.tools.mzml_wrapper.MzMlWrapper;
import uk.ac.liv.mzidconverters.Tandem2mzid;
import uk.ac.liv.proteoidviewer.ProteoIDViewer;
import uk.ac.liv.proteoidviewer.tabs.GlobalStatisticsPanel;
import uk.ac.liv.proteoidviewer.tabs.ProteinView;
import uk.ac.liv.proteoidviewer.util.ProgressBarDialog;
import uk.ac.liv.proteoidviewer.util.SourceFileFilter;

public class openMenuItemActionPerformed implements ActionListener {

	private final ProteoIDViewer proteoIDViewer;
	private final JFileChooser fileChooser;
	private final JTabbedPane mainTabbedPane;
	private final ProteinView proteinView;
	private GlobalStatisticsPanel globalStatisticsPanel;

	public openMenuItemActionPerformed(ProteoIDViewer proteoIDViewer,
			JFileChooser fileChooser, JTabbedPane mainTabbedPane,
			ProteinView proteinView, GlobalStatisticsPanel globalStatisticsPanel) {
		this.proteoIDViewer = proteoIDViewer;
		this.fileChooser = fileChooser;
		this.mainTabbedPane = mainTabbedPane;
		this.proteinView = proteinView;
		this.globalStatisticsPanel = globalStatisticsPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		ProgressBarDialog progressBarDialog = new ProgressBarDialog(
				proteoIDViewer, true);
		final Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				progressBarDialog.setTitle("Parsing the mzid file. Please Wait...");
				progressBarDialog.setVisible(true);
			}
		}, "ProgressBarDialog");

		int returnVal = fileChooser.showOpenDialog(proteoIDViewer);
		if (returnVal != JFileChooser.APPROVE_OPTION)
			return;

		final File mzid_file = fileChooser.getSelectedFile();
		proteoIDViewer.setTitle("ProteoIDViewer   -  " + mzid_file.getPath());
		thread.start();

		new Thread("LoadingThread") {

			@Override
			public void run() {
				try {
					if (mzid_file.getPath().endsWith(".gz")) {
						File outFile = null;
						FileOutputStream fos = null;

						GZIPInputStream gin = new GZIPInputStream(
								new FileInputStream(mzid_file));
						outFile = new File(mzid_file.getParent(), mzid_file
								.getName().replaceAll("\\.gz$", ""));
						fos = new FileOutputStream(outFile);
						byte[] buf = new byte[100000];
						int len;
						while ((len = gin.read(buf)) > 0) {
							fos.write(buf, 0, len);
						}
						fos.close();
						gin.close();

						proteoIDViewer.mzIdentMLUnmarshaller = new MzIdentMLUnmarshaller(
								outFile);
						proteoIDViewer.fileName = outFile;
					} else if (mzid_file.getPath().endsWith(".omx")) {
						File outFile = null;
						outFile = new File(fileChooser.getCurrentDirectory(),
								mzid_file.getName().replaceAll(".omx", ".mzid"));
						// TODO: Disabled - Andrew
						// new Omssa2mzid(mzid_file.getPath(),
						// outFile.getPath(), false);
						proteoIDViewer.mzIdentMLUnmarshaller = new MzIdentMLUnmarshaller(
								outFile);
						proteoIDViewer.fileName = outFile;
					} else if (mzid_file.getPath().endsWith(".xml")) {
						File outFile = null;
						outFile = new File(fileChooser.getCurrentDirectory(),
								mzid_file.getName().replaceAll(".omx", ".mzid"));
						new Tandem2mzid(mzid_file.getPath(), outFile.getPath());
						proteoIDViewer.mzIdentMLUnmarshaller = new MzIdentMLUnmarshaller(
								outFile);
						proteoIDViewer.fileName = outFile;
					} else {
						proteoIDViewer.mzIdentMLUnmarshaller = new MzIdentMLUnmarshaller(
								mzid_file);
						proteoIDViewer.fileName = mzid_file;
					}

					if (!proteoIDViewer.mzIdentMLUnmarshaller
							.getMzIdentMLVersion().startsWith("1.1.")
							&& !proteoIDViewer.mzIdentMLUnmarshaller
									.getMzIdentMLVersion().startsWith("1.2.")) {
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
					proteoIDViewer.jmzreader = null;
					proteoIDViewer.createTables();
					proteoIDViewer.clearSummaryStats();
					mainTabbedPane.setSelectedIndex(0);
					proteoIDViewer.secondTab = false;
					proteoIDViewer.thirdTab = false;
					proteoIDViewer.fourthTab = false;
					proteoIDViewer.fifthTab = false;
					proteoIDViewer.sixthTab = false;
					proteinView.loadProteinAmbiguityGroupTable(globalStatisticsPanel, proteoIDViewer.mzIdentMLUnmarshaller);

					progressBarDialog.setVisible(false);
					progressBarDialog.dispose();

					String message = "Do you want to load spectrum source file?";

					int answer = JOptionPane.showConfirmDialog(null, message);
					if (answer == JOptionPane.YES_OPTION) {
						JFileChooser fc;
						// Create a file chooser
						fc = new JFileChooser();
						fc.setCurrentDirectory(fileChooser
								.getCurrentDirectory());

						fc.addChoosableFileFilter(new SourceFileFilter());
						int returnVal1 = fc.showOpenDialog(null);

						if (returnVal1 == JFileChooser.APPROVE_OPTION) {
							try {
								File file = fc.getSelectedFile();
								if (file.getAbsolutePath().toLowerCase()
										.endsWith("mgf")) {
									proteoIDViewer.jmzreader = new MgfFile(file);
									proteoIDViewer.sourceFile = "mgf";
									JOptionPane.showMessageDialog(null,
											file.getName() + " is loaded",
											"Spectrum file",
											JOptionPane.INFORMATION_MESSAGE);
								} else if (file.getAbsolutePath().toLowerCase()
										.endsWith("mzml")) {
									proteoIDViewer.jmzreader = new MzMlWrapper(
											file);
									proteoIDViewer.sourceFile = "mzML";
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
					proteoIDViewer.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

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
}