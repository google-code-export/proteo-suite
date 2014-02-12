package org.proteosuite;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Work space management class
 * 
 * @author Andrew Collins
 * 
 */
public class WorkSpace {
	private static final String CONFIG_FILE = "config.xml";
	private static WorkSpace instance = null;

	private String sWorkspace = "C:/temp";
        private boolean projectModified = false;

	private WorkSpace() {
		readConfig();
	}

	public static WorkSpace getInstance() {
		if (instance == null)
			instance = new WorkSpace();

		return instance;
	}

	public String getWorkSpace() {
		return sWorkspace;
	}

	public void setWorkSpace(String string) {
		sWorkspace = string;
	}
        
        public boolean isProjectModified() {
            return projectModified;
        }
        
        public void setProjectModifiedTag(boolean tag) {
            this.projectModified = tag;
        }

	/**
	 * Checks if the working space is valid
	 * 
	 * @param void
	 * @return true/false
	 */
	public boolean isValidWorkSpace() {
		
		// Check if default workspace is valid
		boolean exists = new File(getWorkSpace()).exists();
		if (!exists) {
			JOptionPane
					.showMessageDialog(
							null,
							"The default \"workspace\" does not exist. Please set up your directory in \"Tools\"->\"Options\" ",
							"Error", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		return true;
	}
	
	private void readConfig()
	{
		// Validate if config file exists
		if (!isValidConfigFile())
			return;

		// Read files using SAX (get workspace)
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {
				boolean isWorkSpace = false;

				@Override
				public void startElement(String uri, String localName,
						String qName, Attributes attributes)
						throws SAXException {
					if (qName.equalsIgnoreCase("workspace"))
						isWorkSpace = true;
				}

				@Override
				public void characters(char ch[], int start, int length)
						throws SAXException {
					if (isWorkSpace) {
						isWorkSpace = false;
						setWorkSpace(new String(ch, start, length));
					}
				}
			};
			saxParser.parse(CONFIG_FILE, handler);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isValidConfigFile()
	{
		return new File(CONFIG_FILE).exists();
	}
	
	/**
	 * TODO
	 **/
	private void writeConfigFile(String sFileName, JTable jtRawFiles) {
		String sProjectName = "TO DO";
		
		if (sFileName.indexOf(".psx") <= 0) {
			sFileName = sFileName + ".psx";
		}
		try {

			FileWriter fstream = new FileWriter(sFileName);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
			out.newLine();
			out.write("<ProteoSuiteProject xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" ");
			out.write("xsi:schemaLocation=\"ProteoSuite.xsd\" name=\""
					+ sProjectName + "\" workspace=\""
					+ getWorkSpace() + "\">");
			out.newLine();
			out.write(" <configSettings>");
			out.newLine();
			out.write("     <rawDataSettings>");
			out.newLine();
			for (int iI = 0; iI < jtRawFiles.getRowCount(); iI++) {
				out.write("         <rawFile ");
				out.write(" name=\"" + jtRawFiles.getValueAt(iI, 0) + "\"");
				out.write(" path=\"" + jtRawFiles.getValueAt(iI, 1) + "\"");
				out.write(" type=\"" + jtRawFiles.getValueAt(iI, 2) + "\"");
				out.write(" version=\"" + jtRawFiles.getValueAt(iI, 3) + "\"");
				out.write(" scans=\"" + jtRawFiles.getValueAt(iI, 4) + "\" >");
				out.newLine();
				out.write("         </rawFile>");
				out.newLine();
			}
			out.write("     </rawDataSettings>");
			out.newLine();
			out.write("     <identDataSettings>");
			out.newLine();
			out.write("     </identDataSettings>");
			out.newLine();
			out.write("     <quantDataSettings>");
			out.newLine();
			out.write("     </quantDataSettings>");
			out.newLine();
			out.write(" </configSettings>");
			out.newLine();
			out.write("</ProteoSuiteProject>");
			out.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
}
