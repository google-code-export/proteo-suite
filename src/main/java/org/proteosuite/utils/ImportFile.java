package org.proteosuite.utils;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import org.proteosuite.ProteoSuiteView;
import org.proteosuite.WorkSpace;
import org.proteosuite.fileformat.FileFormatMGF;
import org.proteosuite.fileformat.FileFormatMascot;
import org.proteosuite.fileformat.FileFormatMzIdentML;
import org.proteosuite.fileformat.FileFormatMzML;
import org.proteosuite.fileformat.FileFormatMzQuantML;
import org.proteosuite.fileformat2.FileFormatMascotImport;
import org.proteosuite.fileformat2.FileFormatMgfImport;
import org.proteosuite.fileformat2.FileFormatMzIdImport;
import org.proteosuite.fileformat2.FileFormatMzMLImport;
import org.proteosuite.fileformat2.FileFormatMzQuantImport;
import org.proteosuite.gui.AsynchronousTask;
import org.proteosuite.gui.TabbedChartViewer;
import org.proteosuite.gui.TabbedLog;
import org.proteosuite.gui.TabbedProperties;

import uk.ac.ebi.jmzidml.xml.io.MzIdentMLUnmarshaller;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshaller;
import uk.ac.liv.jmzqml.xml.io.MzQuantMLUnmarshaller;

/**
 * 
 * @author Andrew Collins
 */
public class ImportFile {
	private String sPreviousLocation;
	private WorkSpace WORKSPACE = WorkSpace.getInstance();
	private boolean isProjectModified;

	private static final byte LOAD_MZML = 0;
	private static final byte LOAD_MGF = 1;
	private static final byte LOAD_MZID = 2;
	private static final byte LOAD_MASCOT = 3;
	private static final byte LOAD_MZQ = 4;

	public void jmImportFileActionPerformed(
			final ProteoSuiteView proteoSuiteView, final JTable jtRawFiles,
			final JLabel jlFileNameMGFText, final JTable jtFeatureQuant,
			final JTable jtMzIDProtGroup, JMenuItem jmSaveProject,
			final TabbedLog jtpLog, final TabbedProperties jtpProperties,
			final TabbedChartViewer jtpViewer, final JButton jbSaveProject,
			final JComboBox<String> jcbPSM, final JEditorPane jepMZQView,
			final JEditorPane jepMzIDView, final JEditorPane jepMzMLView,
			final JLabel jlFileNameMzQText, final JLabel jlRawFilesStatus,
			final JLabel jlIdentFilesStatus, final JLabel jlFileNameMzIDText,
			final JLabel jlFileNameMzMLText, final JTable jtIdentFiles,
			final JTable jtMGF, final JTable jtMascotXMLView,
			final JTable jtMzId, final JTable jtMzML,
			final JTable jtPeptideQuant, final JTable jtProteinQuant,
			final JTable jtQuantFiles,
			final List<MzMLUnmarshaller> aMzMLUnmarshaller,
			final List<MzIdentMLUnmarshaller> aMzIDUnmarshaller,
			final List<MzQuantMLUnmarshaller> aMzQUnmarshaller) {

		// Selecting file(s)
		JFileChooser chooser = new JFileChooser(sPreviousLocation);
		chooser.setDialogTitle("Select the file(s) to analyze");

		// Filters must be in descending order
		chooser.addChoosableFileFilter(new FileNameExtensionFilter(
				"mzQuantML Files (*.mzq, *.mzq.gz)", "mzq"));
		chooser.addChoosableFileFilter(new FileNameExtensionFilter(
				"Identification Files (*.mzid, *.mzid.gz, *.xml)", "mzid",
				"gz", "xml"));
		chooser.addChoosableFileFilter(new FileNameExtensionFilter(
				"Raw Files (*.mzML, *.mzML.gz, *.mgf)", "mzML", "gz", "mgf"));

		// Enable multiple file selection
		chooser.setMultiSelectionEnabled(true);

		// Setting default directory
		if (sPreviousLocation == null || sPreviousLocation.contains(""))
			chooser.setCurrentDirectory(new File(WORKSPACE.getWorkSpace()));
		else
			chooser.setCurrentDirectory(new File(sPreviousLocation));

		// Retrieving selection from user
		int returnVal = chooser.showOpenDialog(proteoSuiteView);

		if (returnVal != JFileChooser.APPROVE_OPTION)
			return;

		final File[] inputFiles = chooser.getSelectedFiles();
		if (inputFiles == null || inputFiles.length == 0)
			return;

		isProjectModified = true;
		proteoSuiteView.updateSaveProjectStatus(jmSaveProject, jbSaveProject);
		sPreviousLocation = inputFiles[0].getParent();

		jtpLog.appendLog("Reading files (Total=" + inputFiles.length + ")");

		AsynchronousTask asyncTask = new AsynchronousTask(proteoSuiteView);
		File[] doLoad = new File[5];
		for (final File inputFile : inputFiles) {
			Runnable task = null;
			// Read mzML
			if ((inputFile.getName().toLowerCase().indexOf(".mzml") != -1)
					|| (inputFile.getName().toLowerCase().indexOf(".mzml.gz") != -1)) {
				task = new FileFormatMzMLImport(jtpLog, inputFile,
						aMzMLUnmarshaller,
						(DefaultTableModel) jtRawFiles.getModel());

				if (doLoad[LOAD_MZML] == null)
					doLoad[LOAD_MZML] = inputFile;
			}
			// Read MGF
			else if (inputFile.getName().toLowerCase().indexOf(".mgf") > 0) {
				task = new FileFormatMgfImport(inputFile, jtRawFiles);

				if (doLoad[LOAD_MGF] == null)
					doLoad[LOAD_MGF] = inputFile;
			}
			// Read mzIdentML
			else if ((inputFile.getName().toLowerCase().indexOf(".mzid") > 0)
					|| (inputFile.getName().toLowerCase().indexOf(".mzid.gz") > 0)) {
				task = new FileFormatMzIdImport(jtpLog, inputFile,
						aMzIDUnmarshaller,
						(DefaultTableModel) jtIdentFiles.getModel());

				if (doLoad[LOAD_MZID] == null)
					doLoad[LOAD_MZID] = inputFile;
			}
			// Read Mascot XML
			else if (inputFile.getName().toLowerCase().indexOf(".xml") > 0) {
				task = new FileFormatMascotImport(inputFile, jtIdentFiles);

				if (doLoad[LOAD_MASCOT] == null)
					doLoad[LOAD_MASCOT] = inputFile;
			}

			// Read mzQuantML
			else if (inputFile.getName().toLowerCase().indexOf(".mzq") > 0) {
				task = new FileFormatMzQuantImport(inputFile, jtQuantFiles,
						aMzQUnmarshaller);

				if (doLoad[LOAD_MZQ] == null)
					doLoad[LOAD_MZQ] = inputFile;
			}

			if (task != null)
				asyncTask.addTask(task);
		}
		asyncTask.execute();
		asyncTask.showDialog();

		try {
			asyncTask.get();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		asyncTask = new AsynchronousTask(proteoSuiteView);
		for (int i = 0; i < doLoad.length; i++) {
			if (doLoad[i] == null)
				continue;

			Runnable task = null;
			File inputFile = doLoad[i];
			switch (i) {
			case LOAD_MZML:
				//jtpLog.appendLog("Loading mzML view");
				task = new FileFormatMzML(jtMzML, jlFileNameMzMLText,
						jepMzMLView, jtpProperties, aMzMLUnmarshaller.get(0));
				asyncTask.addTask(task);
				jtpProperties.setExportMzMLExcel(true);
				break;
			case LOAD_MGF:
				//jtpLog.appendLog("Loading MGF view");
				final String sFileNameRef = jtRawFiles.getValueAt(0, 0)
						.toString();
				final String sFilePathRef = jtRawFiles.getValueAt(0, 1)
						.toString();

				task = new FileFormatMGF(jtMGF, jlFileNameMGFText,
						sFileNameRef, jtpProperties, sFilePathRef);
				asyncTask.addTask(task);

				jtpLog.appendLog("Raw files imported successfully!");
				jtpProperties.setExportMGFExcel(true);
				proteoSuiteView.renderIdentFiles(jtRawFiles, jtIdentFiles);
				proteoSuiteView.updateStatusPipeline(jlRawFilesStatus,
				jtRawFiles, jlIdentFilesStatus, jtIdentFiles);
				break;
			case LOAD_MZID:
				// Display first element
				jtpLog.appendLog("Loading mzIdentML view");

				task = new FileFormatMzIdentML(jtpProperties,
						jlFileNameMzIDText, inputFile.getName(), jtMzId,
						jcbPSM, jtMzIDProtGroup, jepMzIDView,
						aMzIDUnmarshaller.get(0));
				asyncTask.addTask(task);

				jtpLog.appendLog("Identification files imported successfully!");

				jtpProperties.setExportMzIdentMLExcel(true);
				proteoSuiteView.renderIdentFiles(jtRawFiles, jtIdentFiles);
				proteoSuiteView.updateStatusPipeline(jlRawFilesStatus,
						jtRawFiles, jlIdentFilesStatus, jtIdentFiles);
				break;
			case LOAD_MASCOT:

				// Display data for the first element
				jtpLog.appendLog("Loading Mascot XML view");
				Runnable fileFormatMascot = new FileFormatMascot(
						inputFile.getName(), inputFile.getPath().toString()
								.replace("\\", "/"), jtMascotXMLView,
						jtpProperties);
				fileFormatMascot.run();

				jtpLog.appendLog("Identifiation files imported successfully!");

				jtpProperties.setExportMascotXMLExcel(true);
				proteoSuiteView.renderIdentFiles(jtRawFiles, jtIdentFiles);
				proteoSuiteView.updateStatusPipeline(jlRawFilesStatus,
						jtRawFiles, jlIdentFilesStatus, jtIdentFiles);
				break;
			case LOAD_MZQ:
				boolean isOK = false;
				// For files
				if (isOK) {
					// Display first element
					jtpLog.appendLog("Loading mzQuantML view");

					if ((!jtQuantFiles.getValueAt(0, 0).toString()
							.equals(jlFileNameMzQText.getText()))
							|| (jtPeptideQuant.getRowCount() <= 0)) {

						task = new FileFormatMzQuantML(jtpProperties,
								jtProteinQuant, jtPeptideQuant, jtFeatureQuant,
								inputFile.getAbsolutePath(), aMzQUnmarshaller,
								jlFileNameMzQText, jepMZQView, jtpLog);
						asyncTask.addTask(task);

						jtpLog.appendLog("Quantification files imported successfully!");
					}
				}

				jtpProperties.setExportPepMZQExcel(true);
				jtpProperties.setExportProtMZQExcel(true);
				jtpProperties.setExportFeatMZQExcel(true);
				break;
			}
		}
		asyncTask.execute();
		asyncTask.showDialog();
		
		//try {
		//	asyncTask.get();
		//} catch (InterruptedException | ExecutionException e) {
		//	// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}

		if (doLoad[LOAD_MZML] != null) {
			jtpLog.appendLog("Displaying chromatogram");
			jtpViewer.updateChromatogram(aMzMLUnmarshaller.get(0));

			jtpLog.appendLog("Displaying 2D Plot");
			jtpViewer.update2DPlot(aMzMLUnmarshaller.get(0));

			jtpLog.appendLog("Raw files imported successfully!");

			proteoSuiteView.renderIdentFiles(jtRawFiles, jtIdentFiles);
			proteoSuiteView.updateStatusPipeline(jlRawFilesStatus, jtRawFiles,
					jlIdentFilesStatus, jtIdentFiles);
		}
	}
}
