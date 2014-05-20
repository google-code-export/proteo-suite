
package uk.ac.liv.proteoidviewer.util;

import java.io.File;

import javax.swing.filechooser.FileFilter;
/**
 * @author Fawaz Ghali, University of Liverpool, 2012
 */
public class XmlFilter extends FileFilter {

	@Override
	public boolean accept(File file) {
		// Allow just directories and files with ".mzid" extension...
		return file.isDirectory() || file.getAbsolutePath().endsWith(".xml");
	}

	@Override
	public String getDescription() {
		// This description will be displayed in the dialog,
		// hard-coded = ugly, should be done via I18N
		return "Tandem files (*.xml)";
	}
}
