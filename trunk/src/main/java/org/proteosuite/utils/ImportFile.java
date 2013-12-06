package org.proteosuite.utils;

import java.io.File;
import java.util.List;

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
import org.proteosuite.fileformat.FileFormatMascot;
import org.proteosuite.fileformat2.FileFormatMzIdImport;
import org.proteosuite.fileformat2.FileFormatMzMLImport;
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

		for (final File inputFile : inputFiles) {
			// Read mzML
			if ((inputFile.getName().toLowerCase().indexOf(".mzml") > 0)
					|| (inputFile.getName().toLowerCase().indexOf(".mzml.gz") > 0)) {
				FileFormatMzMLImport fileFormatMzMLImport = new FileFormatMzMLImport(
						jtpLog, inputFile, aMzMLUnmarshaller,
						(DefaultTableModel) jtRawFiles.getModel());
				fileFormatMzMLImport.run();

				// We then display the first mzML element, the
				// corresponding chromatogram and the 2D plot
				jtpLog.appendLog("Loading mzML view");
				proteoSuiteView.loadMzMLView(0, jtRawFiles, jtpProperties,
						jepMzMLView, jlFileNameMzMLText, jtMzML);
				jtpLog.appendLog("Displaying chromatogram");

				// Clear container
				jtpViewer.updateChromatogram(aMzMLUnmarshaller.get(0));
				jtpLog.appendLog("Displaying 2D Plot");

				jtpViewer.update2DPlot(aMzMLUnmarshaller.get(0));
				jtpLog.appendLog("Raw files imported successfully!");

				jtpProperties.setExportMzMLExcel(true);
				proteoSuiteView.renderIdentFiles(jtRawFiles, jtIdentFiles);
				proteoSuiteView.updateStatusPipeline(jlRawFilesStatus,
						jtRawFiles, jlIdentFilesStatus, jtIdentFiles);
			}
			// Read MGF
			else if (inputFile.getName().toLowerCase().indexOf(".mgf") > 0) {
				// Fill JTable
				final DefaultTableModel model = (DefaultTableModel) jtRawFiles
						.getModel();

				model.insertRow(
						model.getRowCount(),
						new String[] {
								inputFile.getName(),
								inputFile.getPath().toString()
										.replace("\\", "/"), "MGF", "N/A" });

				// Display data for the first element
				jtpLog.appendLog("Loading MGF view");
				proteoSuiteView.loadMGFView(0, jlFileNameMGFText, jtRawFiles,
						jtpProperties, jtMGF);

				jtpLog.appendLog("Raw files imported successfully!");

				jtpProperties.setExportMGFExcel(true);
				proteoSuiteView.renderIdentFiles(jtRawFiles, jtIdentFiles);
				proteoSuiteView.updateStatusPipeline(jlRawFilesStatus,
						jtRawFiles, jlIdentFilesStatus, jtIdentFiles);
			}
			// Read mzIdentML
			else if ((inputFile.getName().toLowerCase().indexOf(".mzid") > 0)
					|| (inputFile.getName().toLowerCase().indexOf(".mzid.gz") > 0)) {

				FileFormatMzIdImport fileFormatMzIdImport = new FileFormatMzIdImport(
						jtpLog, inputFile, aMzIDUnmarshaller,
						(DefaultTableModel) jtIdentFiles.getModel());
				fileFormatMzIdImport.run();

				// Display first element
				jtpLog.appendLog("Loading mzIdentML view");
				proteoSuiteView.loadMzIdentMLView(0, inputFile.getName(),
						jtMzIDProtGroup, jtpProperties, jcbPSM, jepMzIDView,
						jlFileNameMzIDText, jtMzId);

				jtpLog.appendLog("Identification files imported successfully!");

				jtpProperties.setExportMzIdentMLExcel(true);
				proteoSuiteView.renderIdentFiles(jtRawFiles, jtIdentFiles);
				proteoSuiteView.updateStatusPipeline(jlRawFilesStatus,
						jtRawFiles, jlIdentFilesStatus, jtIdentFiles);
			}
			// Read Mascot XML
			else if (inputFile.getName().toLowerCase().indexOf(".xml") > 0) {
				// Fill JTable
				final DefaultTableModel model = (DefaultTableModel) jtIdentFiles
						.getModel();

				model.insertRow(
						model.getRowCount(),
						new String[] {
								inputFile.getName(),
								inputFile.getPath().toString()
										.replace("\\", "/"), "mascot_xml",
								"N/A", "" });

				// Display data for the first element
				jtpLog.appendLog("Loading Mascot XML view");
				FileFormatMascot.loadMascotView(inputFile.getName(), inputFile
						.getPath().toString().replace("\\", "/"),
						jtMascotXMLView, jtpProperties);

				jtpLog.appendLog("Identifiation files imported successfully!");

				jtpProperties.setExportMascotXMLExcel(true);
				proteoSuiteView.renderIdentFiles(jtRawFiles, jtIdentFiles);
				proteoSuiteView.updateStatusPipeline(jlRawFilesStatus,
						jtRawFiles, jlIdentFilesStatus, jtIdentFiles);
			}

			// Read mzQuantML
			else if (inputFile.getName().toLowerCase().indexOf(".mzq") > 0) {
				// Fill JTable
				final DefaultTableModel model = (DefaultTableModel) jtQuantFiles
						.getModel();

				// Reading selected files
				boolean isOK = true;
				// Validate file extension (mixed files)
				if (inputFile.getName().toLowerCase().indexOf(".mzq") > 0) {
					File xmlFile = new File(inputFile.getPath());

					// Unmarshall data using jmzIdentML API
					jtpLog.appendLog("Unmarshalling " + xmlFile.getName()
							+ " starts");

					try {
						isOK = true;
						aMzQUnmarshaller.add(Unmarshaller.unmarshalMzQMLFile(
								model, xmlFile));
						// Invalid mzq file
						if (!isOK)
							return;

						jtpLog.appendLog("Unmarshalling ends");
					} catch (Exception e) {
						jtpLog.appendLog("Error reading mzQuantML - the mzQuantML file may be invalid");
						jtpLog.appendLog(e.getMessage());
						e.printStackTrace();
						isOK = false;
					}
				}
				// For files
				if (isOK) {
					// Display first element
					jtpLog.appendLog("Loading mzQuantML view");
					// loadMzQuantMLView(0, inputFiles[0].getName());
					proteoSuiteView.loadMzQuantMLView(0,
							inputFile.getAbsolutePath(), jtFeatureQuant,
							jtpLog, jtpProperties, jepMZQView,
							jlFileNameMzQText, jtPeptideQuant, jtProteinQuant,
							jtQuantFiles);

					jtpLog.appendLog("Quantification files imported successfully!");
				}

				jtpProperties.setExportPepMZQExcel(true);
				jtpProperties.setExportProtMZQExcel(true);
				jtpProperties.setExportFeatMZQExcel(true);
			}
		}
	}
}
