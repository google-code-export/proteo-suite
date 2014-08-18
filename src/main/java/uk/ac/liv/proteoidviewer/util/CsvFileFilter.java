package uk.ac.liv.proteoidviewer.util;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * @author Fawaz Ghali, University of Liverpool, 2012
 */
public class CsvFileFilter extends FileFilter {

	@Override
	public boolean accept(File file) {
		// Allow just directories and files with ".mzid" extension
		return file.isDirectory() || file.getAbsolutePath().endsWith(".csv");
	}

	@Override
	public String getDescription() {
		// This description will be displayed in the dialog,
		// hard-coded = ugly, should be done via I18N
		return "CSV (*.csv)";
	}
}
