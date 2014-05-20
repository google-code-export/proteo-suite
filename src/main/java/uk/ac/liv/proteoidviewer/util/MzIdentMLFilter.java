package uk.ac.liv.proteoidviewer.util;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class MzIdentMLFilter extends FileFilter {

	@Override
	public boolean accept(File file) {
		// Allow just directories and files with ".mzid" extension...
		return file.isDirectory() || file.getAbsolutePath().endsWith(".mzid")
				|| file.getAbsolutePath().endsWith(".gz");
	}

	@Override
	public String getDescription() {
		// This description will be displayed in the dialog,
		// hard-coded = ugly, should be done via I18N
		return "mzIdentML (*.mzid) or gzipped (*.gz)";
	}
}
