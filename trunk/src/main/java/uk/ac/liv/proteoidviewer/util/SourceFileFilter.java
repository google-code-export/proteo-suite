package uk.ac.liv.proteoidviewer.util;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 *
 * @author fwzghl
 */
public class SourceFileFilter extends FileFilter {

	@Override
	public boolean accept(File file) {
		// Allow just directories and files with ".mzid" extension...
		return file.isDirectory() || file.getAbsolutePath().endsWith(".mgf")
				|| file.getAbsolutePath().endsWith(".mzml");
	}

	@Override
	public String getDescription() {
		// This description will be displayed in the dialog,
		// hard-coded = ugly, should be done via I18N
		return "Mascot generic format (*.mgf) or Mass spectrometry data format (*.mzML)";
	}

}
