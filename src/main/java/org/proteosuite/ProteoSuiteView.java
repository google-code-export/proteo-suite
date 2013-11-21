/*
 * --------------------------------------------------------------------------
 * ProteoSuiteView.java
 * --------------------------------------------------------------------------
 * Description:       ProteoSuite Graphical Unit Interface
 * Developer:         fgonzalez
 * Created:           08 February 2011
 * Notes:             GUI generated using NetBeans IDE 7.0.1
 * Read our documentation file under our Google SVN repository
 * SVN: http://code.google.com/p/proteo-suite/
 * Project Website: http://www.proteosuite.org/
 * --------------------------------------------------------------------------
 */
package org.proteosuite;

import com.compomics.util.gui.spectrum.ChromatogramPanel;
import com.compomics.util.gui.spectrum.SpectrumPanel;
import edu.ucsd.msjava.mzml.MzMLAdapter;
import edu.ucsd.msjava.params.ParamManager;
import edu.ucsd.msjava.ui.MSGFPlus;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultCellEditor;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.apache.commons.io.FileUtils;
import org.proteosuite.data.psSyntheticArray;
import org.proteosuite.data.psTemplateQuant;
import org.proteosuite.data.psTemplate;
import org.proteosuite.data.psTemplateQuant2;
import org.proteosuite.gui.*;
import org.proteosuite.external.IPC;
import org.proteosuite.external.IPC.Options;
import org.proteosuite.external.IPC.Results;
import org.proteosuite.external.ViewChartGUI;
import org.proteosuite.utils.ExcelExporter;
import org.proteosuite.utils.MzMLCompress;
import org.proteosuite.utils.OpenURL;
import org.proteosuite.utils.ProgressBarDialog;
import org.proteosuite.utils.SystemUtils;
import org.proteosuite.utils.TwoDPlot;
import org.proteosuite.utils.XIC;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;
import uk.ac.ebi.jmzml.model.mzml.BinaryDataArray;
import uk.ac.ebi.jmzml.model.mzml.FileDescription;
import uk.ac.ebi.jmzml.model.mzml.CVParam;
import uk.ac.ebi.jmzml.model.mzml.Chromatogram;
import uk.ac.ebi.jmzml.model.mzml.PrecursorList;
import uk.ac.ebi.jmzml.model.mzml.Spectrum;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshaller;
import uk.ac.ebi.jmzml.xml.io.MzMLObjectIterator;
import uk.ac.ebi.jmzml.xml.io.MzMLUnmarshallerException;
import uk.ac.cranfield.xTracker.*;
import uk.ac.cranfield.xTracker.utils.XMLparser;
import uk.ac.ebi.jmzidml.MzIdentMLElement;
import uk.ac.ebi.jmzidml.model.mzidml.AnalysisData;
import uk.ac.ebi.jmzidml.model.mzidml.AnalysisSoftware;
import uk.ac.ebi.jmzidml.model.mzidml.AnalysisSoftwareList;
import uk.ac.ebi.jmzidml.model.mzidml.DBSequence;
import uk.ac.ebi.jmzidml.model.mzidml.Peptide;
import uk.ac.ebi.jmzidml.model.mzidml.PeptideEvidence;
import uk.ac.ebi.jmzidml.model.mzidml.PeptideEvidenceRef;
import uk.ac.ebi.jmzidml.model.mzidml.PeptideHypothesis;
import uk.ac.ebi.jmzidml.model.mzidml.ProteinAmbiguityGroup;
import uk.ac.ebi.jmzidml.model.mzidml.ProteinDetectionHypothesis;
import uk.ac.ebi.jmzidml.model.mzidml.ProteinDetectionList;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationItem;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationList;
import uk.ac.ebi.jmzidml.model.mzidml.SpectrumIdentificationResult;
import uk.ac.ebi.jmzidml.xml.io.MzIdentMLUnmarshaller;
import uk.ac.ebi.jmzml.model.mzml.BinaryDataArrayList;
import uk.ac.ebi.jmzml.model.mzml.CV;
import uk.ac.ebi.jmzml.model.mzml.CVList;
import uk.ac.ebi.jmzml.model.mzml.ChromatogramList;
import uk.ac.ebi.jmzml.model.mzml.IndexList;
import uk.ac.ebi.jmzml.model.mzml.InstrumentConfigurationList;
import uk.ac.ebi.jmzml.model.mzml.MzML;
import uk.ac.ebi.jmzml.model.mzml.ReferenceableParamGroup;
import uk.ac.ebi.jmzml.model.mzml.ReferenceableParamGroupList;
import uk.ac.ebi.jmzml.model.mzml.Run;
import uk.ac.ebi.jmzml.model.mzml.SpectrumList;
import uk.ac.ebi.jmzml.model.mzml.params.BinaryDataArrayCVParam;
import uk.ac.ebi.jmzml.xml.io.MzMLMarshaller;
import uk.ac.liv.jmzqml.MzQuantMLElement;
import uk.ac.liv.jmzqml.model.mzqml.AbstractParam;
import uk.ac.liv.jmzqml.model.mzqml.Assay;
import uk.ac.liv.jmzqml.model.mzqml.AssayList;
import uk.ac.liv.jmzqml.model.mzqml.Column;
import uk.ac.liv.jmzqml.model.mzqml.Cv;
import uk.ac.liv.jmzqml.model.mzqml.CvList;
import uk.ac.liv.jmzqml.model.mzqml.CvParam;
import uk.ac.liv.jmzqml.model.mzqml.CvParamRef;
import uk.ac.liv.jmzqml.model.mzqml.DataMatrix;
import uk.ac.liv.jmzqml.model.mzqml.DataProcessing;
import uk.ac.liv.jmzqml.model.mzqml.DataProcessingList;
import uk.ac.liv.jmzqml.model.mzqml.Feature;
import uk.ac.liv.jmzqml.model.mzqml.FeatureList;
import uk.ac.liv.jmzqml.model.mzqml.IdentificationFile;
import uk.ac.liv.jmzqml.model.mzqml.IdentificationFiles;
import uk.ac.liv.jmzqml.model.mzqml.InputFiles;
import uk.ac.liv.jmzqml.model.mzqml.Label;
import uk.ac.liv.jmzqml.model.mzqml.ModParam;
import uk.ac.liv.jmzqml.model.mzqml.MzQuantML;
import uk.ac.liv.jmzqml.model.mzqml.Param;
import uk.ac.liv.jmzqml.model.mzqml.ParamList;
import uk.ac.liv.jmzqml.model.mzqml.PeptideConsensus;
import uk.ac.liv.jmzqml.model.mzqml.PeptideConsensusList;
import uk.ac.liv.jmzqml.model.mzqml.ProcessingMethod;
import uk.ac.liv.jmzqml.model.mzqml.Protein;
import uk.ac.liv.jmzqml.model.mzqml.ProteinList;
import uk.ac.liv.jmzqml.model.mzqml.RawFile;
import uk.ac.liv.jmzqml.model.mzqml.RawFilesGroup;
import uk.ac.liv.jmzqml.model.mzqml.Row;
import uk.ac.liv.jmzqml.model.mzqml.SearchDatabase;
import uk.ac.liv.jmzqml.model.mzqml.Software;
import uk.ac.liv.jmzqml.model.mzqml.SoftwareList;
import uk.ac.liv.jmzqml.model.mzqml.StudyVariable;
import uk.ac.liv.jmzqml.model.mzqml.StudyVariableList;
import uk.ac.liv.jmzqml.model.mzqml.UserParam;
import uk.ac.liv.jmzqml.xml.io.MzQuantMLMarshaller;
import uk.ac.liv.jmzqml.xml.io.MzQuantMLObjectIterator;
import uk.ac.liv.jmzqml.xml.io.MzQuantMLUnmarshaller;
import uk.ac.liv.mzidlib.FalseDiscoveryRate;
import uk.ac.liv.mzidlib.MzIdentMLLib;
        
/**
 * This class corresponds to the main form in ProteoSuite. The form includes the visualisation of data and other associated tools.
 * @author fgonzalez
 */
public class ProteoSuiteView extends JFrame {

    //... Project settings ...//
    private final String sPS_Version = "0.3.1";
    private String sProjectName = "";
    private String sWorkspace = "";
    private String sPreviousLocation = "user.home";
    private boolean bProjectModified = false;
    private final String MZQ_XSD = "mzQuantML_1_0_0.xsd";    
    private final String mzMLVersion = "1.1";    
    private final String mzIDVersion = "1.1";
    private final String mzQVersion = "1.0.0";
    private final String PSI_MS_VERSION = "3.37.0";
    private final String PSI_MOD_VERSION = "1.2";
    private SystemUtils sysutils = new SystemUtils();
    
    private IdentParamsView identParamsExecute;    
    private JDialog dialogIdentParamsExecute;    

    //... List of unmarshaller objects ...//
    private ArrayList<MzMLUnmarshaller> aMzMLUnmarshaller = new ArrayList<MzMLUnmarshaller>();
    private ArrayList<MzIdentMLUnmarshaller> aMzIDUnmarshaller = new ArrayList<MzIdentMLUnmarshaller>();
    private ArrayList<MzQuantMLUnmarshaller> aMzQUnmarshaller = new ArrayList<MzQuantMLUnmarshaller>();
    
    public ProteoSuiteView(){
        
        //... Setting standard look and feel ...//
        try {
                  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException ex) {
                    System.out.println(ex.getStackTrace());
                } catch (InstantiationException ex) {
                    System.out.println(ex.getStackTrace());
                } catch (IllegalAccessException ex) {
                    System.out.println(ex.getStackTrace());
                } catch (UnsupportedLookAndFeelException ex) {
                    System.out.println(ex.getStackTrace());
        }
        
        //... Initializing all componentes (handled by netbeans) ...//
        initComponents();
        
        //... Load parameter settings ...//
        identParamsExecute = new IdentParamsView(sWorkspace, "execute");
        
        //... Project default settings ...//
        initProjectValues();
        
        setTitle("ProteoSuite " + sPS_Version + " (Beta Version) - <Project: " + sWorkspace + " - " + sProjectName +  ">         http://www.proteosuite.org");
        
        //... Setting project icons ...//
        Image iconApp = new ImageIcon(getClass().getClassLoader().getResource("images/icon.gif")).getImage();
        setIconImage(iconApp);
        
        //.. Check Java version and architecture ...//
        System.out.println("****************************************");
        System.out.println("*****    P R O T E O S U I T E    ******");
        System.out.println("****************************************");       
        sysutils.checkMemory("starting up");
        System.out.println("Java version: "+System.getProperty("java.version"));
        System.out.println("Architecture: "+System.getProperty("sun.arch.data.model")+"-bit");
        System.out.println("Classpath: "+System.getProperty("java.class.path"));
        System.out.println("****************************************");        
        
        //... Main Menu icons ...//
        //... File ...//
        Icon newIcon = new ImageIcon(getClass().getClassLoader().getResource("images/new.gif"));
        Icon importIcon = new ImageIcon(getClass().getClassLoader().getResource("images/import.gif"));
        Icon openIcon = new ImageIcon(getClass().getClassLoader().getResource("images/open.gif"));
        Icon saveIcon = new ImageIcon(getClass().getClassLoader().getResource("images/save.gif"));
        Icon printIcon = new ImageIcon(getClass().getClassLoader().getResource("images/print.gif"));                       
        //... Edit ...//
        Icon cutIcon = new ImageIcon(getClass().getClassLoader().getResource("images/cut.gif"));
        Icon copyIcon = new ImageIcon(getClass().getClassLoader().getResource("images/copy.gif"));
        Icon pasteIcon = new ImageIcon(getClass().getClassLoader().getResource("images/paste.gif"));                
        //... Project ...//
        Icon setIcon = new ImageIcon(getClass().getClassLoader().getResource("images/settings.gif"));                
        Icon runIcon = new ImageIcon(getClass().getClassLoader().getResource("images/run.gif"));        
        Icon runIdIcon = new ImageIcon(getClass().getClassLoader().getResource("images/runid.gif"));        
        //... Help ...//
        Icon helpIcon = new ImageIcon(getClass().getClassLoader().getResource("images/help.gif"));
                
        jmNewProject.setIcon(newIcon);        
        jmImportFile.setIcon(importIcon);
        jmOpenProject.setIcon(openIcon);
        jmSaveProject.setIcon(saveIcon);        
        jmPrint.setIcon(printIcon);        
        jmCut.setIcon(cutIcon);
        jmCopy.setIcon(copyIcon);        
        jmPaste.setIcon(pasteIcon);
        jmEditIdent.setIcon(setIcon);
        jmEditQuant.setIcon(setIcon);
        jmRunQuantAnalysis.setIcon(runIcon);
        jmRunIdentAnalysis.setIcon(runIdIcon);
        jmHelpContent.setIcon(helpIcon);        

        //... Toolbar icons ...//
        jbNewProject.setIcon(newIcon);
        jbImportFile.setIcon(importIcon);        
        jbOpenProject.setIcon(openIcon);        
        jbSaveProject.setIcon(saveIcon);
        jbCut.setIcon(cutIcon);
        jbCopy.setIcon(copyIcon);
        jbPaste.setIcon(pasteIcon);                
        jbRunQuantAnalysis.setIcon(runIcon);
        jbRunIdentAnalysis.setIcon(runIdIcon);
        
        //... Tabsheet and other shortcut icons ...//
        JLabel jlRawFilesIcon = new JLabel("Raw Files                                 ");
        Icon rawFilesIcon = new ImageIcon(getClass().getClassLoader().getResource("images/raw_file.gif"));
        jlRawFilesIcon.setIcon(rawFilesIcon);
        jlRawFilesIcon.setIconTextGap(5);
        jlRawFilesIcon.setHorizontalTextPosition(SwingConstants.RIGHT);
        jtpRawFiles.setTabComponentAt(0, jlRawFilesIcon);
        
        JLabel jlIdentFilesIcon = new JLabel("Identification Files                    ");
        Icon identFilesIcon = new ImageIcon(getClass().getClassLoader().getResource("images/ident_file.gif"));
        jlIdentFilesIcon.setIcon(identFilesIcon);
        jlIdentFilesIcon.setIconTextGap(5);
        jlIdentFilesIcon.setHorizontalTextPosition(SwingConstants.RIGHT);
        jtpIdentFiles.setTabComponentAt(0, jlIdentFilesIcon);
        
        JLabel jlQuantFilesIcon = new JLabel("Quantitation Files                      ");
        Icon quantFilesIcon = new ImageIcon(getClass().getClassLoader().getResource("images/quant_file.gif"));
        jlQuantFilesIcon.setIcon(quantFilesIcon);
        jlQuantFilesIcon.setIconTextGap(5);
        jlQuantFilesIcon.setHorizontalTextPosition(SwingConstants.RIGHT);
        jtpQuantFiles.setTabComponentAt(0, jlQuantFilesIcon);        
        
        JLabel jlSpectrumIcon = new JLabel("Spectrum");
        Icon spectrumIcon = new ImageIcon(getClass().getClassLoader().getResource("images/ident_file.gif"));
        jlSpectrumIcon.setIcon(spectrumIcon);
        jlSpectrumIcon.setIconTextGap(5);
        jlSpectrumIcon.setHorizontalTextPosition(SwingConstants.RIGHT);
        jtpViewer.setTabComponentAt(0, jlSpectrumIcon);
        
        JLabel jlTICIcon = new JLabel("TIC");
        Icon TICIcon = new ImageIcon(getClass().getClassLoader().getResource("images/chromat.gif"));
        jlTICIcon.setIcon(TICIcon);
        jlTICIcon.setIconTextGap(5);
        jlTICIcon.setHorizontalTextPosition(SwingConstants.RIGHT);
        jtpViewer.setTabComponentAt(1, jlTICIcon);                
        
        JLabel jlTwoDIcon = new JLabel("2D View");
        Icon twoDIcon = new ImageIcon(getClass().getClassLoader().getResource("images/twod.gif"));
        jlTwoDIcon.setIcon(twoDIcon);
        jlTwoDIcon.setIconTextGap(5);
        jlTwoDIcon.setHorizontalTextPosition(SwingConstants.RIGHT);
        jtpViewer.setTabComponentAt(2, jlTwoDIcon);           
        
        JLabel jlThreeDIcon = new JLabel("3D View");
        Icon threeDIcon = new ImageIcon(getClass().getClassLoader().getResource("images/threed.gif"));
        jlThreeDIcon.setIcon(threeDIcon);
        jlThreeDIcon.setIconTextGap(5);
        jlThreeDIcon.setHorizontalTextPosition(SwingConstants.RIGHT);
        jtpViewer.setTabComponentAt(3, jlThreeDIcon);        

        JLabel jlLogIcon = new JLabel("Log  ");
        Icon logIcon = new ImageIcon(getClass().getClassLoader().getResource("images/log.gif"));
        jlLogIcon.setIcon(logIcon);
        jlLogIcon.setIconTextGap(5);
        jlLogIcon.setHorizontalTextPosition(SwingConstants.RIGHT);
        jtpLog.setTabComponentAt(0, jlLogIcon);
        
        JLabel jlRawDataIcon = new JLabel("Raw data");
        Icon rawDataIcon = new ImageIcon(getClass().getClassLoader().getResource("images/raw_data.gif"));
        jlRawDataIcon.setIcon(rawDataIcon);
        jlRawDataIcon.setIconTextGap(5);
        jlRawDataIcon.setHorizontalTextPosition(SwingConstants.RIGHT);
        jtpLog.setTabComponentAt(1, jlRawDataIcon);
        
        JLabel jlPropertiesMzMLIcon = new JLabel("mzML View");
        Icon propertiesMzMLIcon = new ImageIcon(getClass().getClassLoader().getResource("images/properties.gif"));
        jlPropertiesMzMLIcon.setIcon(propertiesMzMLIcon);
        jlPropertiesMzMLIcon.setIconTextGap(5);
        jlPropertiesMzMLIcon.setHorizontalTextPosition(SwingConstants.RIGHT);
        jtpProperties.setTabComponentAt(0, jlPropertiesMzMLIcon);
        
        JLabel jlPropertiesMGFIcon = new JLabel("MGF View");
        Icon propertiesMGFIcon = new ImageIcon(getClass().getClassLoader().getResource("images/properties.gif"));
        jlPropertiesMGFIcon.setIcon(propertiesMGFIcon);
        jlPropertiesMGFIcon.setIconTextGap(5);
        jlPropertiesMGFIcon.setHorizontalTextPosition(SwingConstants.RIGHT);
        jtpProperties.setTabComponentAt(1, jlPropertiesMGFIcon);          
        
        JLabel jlPropertiesMzIdentMLIcon = new JLabel("mzIdentML View");
        Icon propertiesMzIdenMLIcon = new ImageIcon(getClass().getClassLoader().getResource("images/properties.gif"));
        jlPropertiesMzIdentMLIcon.setIcon(propertiesMzIdenMLIcon);
        jlPropertiesMzIdentMLIcon.setIconTextGap(5);
        jlPropertiesMzIdentMLIcon.setHorizontalTextPosition(SwingConstants.RIGHT);
        jtpProperties.setTabComponentAt(2, jlPropertiesMzIdentMLIcon);
        
        JLabel jlPropertiesMascotIcon = new JLabel("Mascot XML View");
        Icon propertiesMascotIcon = new ImageIcon(getClass().getClassLoader().getResource("images/properties.gif"));
        jlPropertiesMascotIcon.setIcon(propertiesMascotIcon);
        jlPropertiesMascotIcon.setIconTextGap(5);
        jlPropertiesMascotIcon.setHorizontalTextPosition(SwingConstants.RIGHT);
        jtpProperties.setTabComponentAt(3, jlPropertiesMascotIcon);        
        
        JLabel jlPropertiesMzQuantMLIcon = new JLabel("mzQuantML View");
        Icon propertiesMzQuantMLIcon = new ImageIcon(getClass().getClassLoader().getResource("images/properties.gif"));
        jlPropertiesMzQuantMLIcon.setIcon(propertiesMzQuantMLIcon);
        jlPropertiesMzQuantMLIcon.setIconTextGap(5);
        jlPropertiesMzQuantMLIcon.setHorizontalTextPosition(SwingConstants.RIGHT);
        jtpProperties.setTabComponentAt(4, jlPropertiesMzQuantMLIcon);                
        
        //... Buttons ...//
        Icon iconExcel = new ImageIcon(getClass().getClassLoader().getResource("images/xls.gif"));
        jbExportMzMLExcel.setIcon(iconExcel);        
        jbExportMzMLExcel.setToolTipText("Export to Excel Spreadsheet (.xls)");
        jbExportMGFExcel.setIcon(iconExcel);        
        jbExportMGFExcel.setToolTipText("Export to Excel Spreadsheet (.xls)");        
        jbExportMzIdentMLExcel.setIcon(iconExcel);        
        jbExportMzIdentMLExcel.setToolTipText("Export to Excel Spreadsheet (.xls)");
        jbExportMascotXMLExcel.setIcon(iconExcel);        
        jbExportMascotXMLExcel.setToolTipText("Export to Excel Spreadsheet (.xls)");                
        jbExportPepMZQExcel.setIcon(iconExcel);        
        jbExportPepMZQExcel.setToolTipText("Export to Excel Spreadsheet (.xls)");
        jbExportProtMZQExcel.setIcon(iconExcel);        
        jbExportProtMZQExcel.setToolTipText("Export to Excel Spreadsheet (.xls)");
        jbExportFeatMZQExcel.setIcon(iconExcel);
        jbExportFeatMZQExcel.setToolTipText("Export to Excel Spreadsheet (.xls)");
        
        //... Setting Window Height and Width ...//
        setMinimumSize(new Dimension(1024, 768));
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        //... Setting dividers ...//
        jspMainPanelView.setDividerLocation(250);           //... Left Menu (Files) ...//
        jspLeftMenuBottom.setDividerLocation(200);          //... Ident and Quantitation separator ...//
        
        jspLeftViewerDetails.setDividerLocation(480);       //... Viewer Height (Viewer) ...//
        jspViewerAndProperties.setDividerLocation(600);     //... Viewer Width  (Viewer) ...//
        
        jspMzML.setDividerLocation(100);                    //... MzML data ...//
        jspProjectDetails.setDividerLocation(200);          //... MzML files ...//
        jspLeftMenuBottom.setDividerLocation(200);          //... IdentML files ...//
        
        jlFiles.requestFocusInWindow();
        
        //... Setting default selection (Viewers) ...//
        jtpViewer.setSelectedIndex(0);        
        jtpLog.setSelectedIndex(0);
        jtpProperties.setSelectedIndex(0);
        Icon thick = new ImageIcon(getClass().getClassLoader().getResource("images/thick.gif"));
        jmSpectrum.setIcon(thick);        
        jmMzMLView.setIcon(thick);
        jmLog.setIcon(thick);                        
        jmShowProjectFiles.setIcon(thick);        
        jmShowViewer.setIcon(thick);
        jmShowProperties.setIcon(thick);               
        
        //... Configuring exit events...//
        pack();
    }
    
    /** This method is called from within the constructor to
     * initialise the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpToolBar = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jbNewProject = new javax.swing.JButton();
        jbImportFile = new javax.swing.JButton();
        jbOpenProject = new javax.swing.JButton();
        jbSaveProject = new javax.swing.JButton();
        jToolBar2 = new javax.swing.JToolBar();
        jbCut = new javax.swing.JButton();
        jbCopy = new javax.swing.JButton();
        jbPaste = new javax.swing.JButton();
        jToolBar3 = new javax.swing.JToolBar();
        jbRunIdentAnalysis = new javax.swing.JButton();
        jbRunQuantAnalysis = new javax.swing.JButton();
        jpMainPanelView = new javax.swing.JPanel();
        jspMainPanelView = new javax.swing.JSplitPane();
        jpLeftPanelView = new javax.swing.JPanel();
        jspLeftPanelView = new javax.swing.JSplitPane();
        jpProjectHeader = new javax.swing.JPanel();
        jlFiles = new javax.swing.JLabel();
        jpProjectDetails = new javax.swing.JPanel();
        jspProjectDetails = new javax.swing.JSplitPane();
        jpLeftMenuTop = new javax.swing.JPanel();
        jtpRawFiles = new javax.swing.JTabbedPane();
        jspRawFiles = new javax.swing.JScrollPane();
        jtRawFiles = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                if (colIndex < 4) {
                    return false;
                } else {
                    return true;
                }
            }};
            jpLeftMenuBottom = new javax.swing.JPanel();
            jspLeftMenuBottom = new javax.swing.JSplitPane();
            jpQuantFiles = new javax.swing.JPanel();
            jtpQuantFiles = new javax.swing.JTabbedPane();
            jspQuantFiles = new javax.swing.JScrollPane();
            jtQuantFiles = new javax.swing.JTable(){
                public boolean isCellEditable(int rowIndex, int colIndex) {
                    return false;
                }};
                jtpIdentFiles = new javax.swing.JTabbedPane();
                jspIdentFiles = new javax.swing.JScrollPane();
                jtIdentFiles = new javax.swing.JTable(){
                    public boolean isCellEditable(int rowIndex, int colIndex) {
                        if (colIndex < 4) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                };
                jpViewerAndProperties = new javax.swing.JPanel();
                jspViewerAndProperties = new javax.swing.JSplitPane();
                jpLeftViewer = new javax.swing.JPanel();
                jspLeftViewer = new javax.swing.JSplitPane();
                jspLeftViewerHeader = new javax.swing.JPanel();
                jlViewer = new javax.swing.JLabel();
                jpLeftViewerDetails = new javax.swing.JPanel();
                jspLeftViewerDetails = new javax.swing.JSplitPane();
                jpLeftViewerBottom = new javax.swing.JPanel();
                jtpLog = new javax.swing.JTabbedPane();
                jspLog = new javax.swing.JScrollPane();
                jtaLog = new javax.swing.JTextArea();
                jpRawDataValues = new javax.swing.JPanel();
                jspRawDataValues = new javax.swing.JSplitPane();
                jpRawDataValuesMenu = new javax.swing.JPanel();
                jLabel1 = new javax.swing.JLabel();
                jtMSIndex = new javax.swing.JTextField();
                jLabel2 = new javax.swing.JLabel();
                jLabel3 = new javax.swing.JLabel();
                jtMSMz = new javax.swing.JTextField();
                jspRawData = new javax.swing.JScrollPane();
                jtRawData = new javax.swing.JTable(){
                    public boolean isCellEditable(int rowIndex, int colIndex) {
                        return false;
                    }};
                    jPanel1 = new javax.swing.JPanel();
                    jScrollPane1 = new javax.swing.JScrollPane();
                    jtTemplate1 = new javax.swing.JTable(){
                        public boolean isCellEditable(int rowIndex, int colIndex) {
                            return false;
                        }};
                        jPanel5 = new javax.swing.JPanel();
                        jPanel2 = new javax.swing.JPanel();
                        jScrollPane2 = new javax.swing.JScrollPane();
                        jtTemplate2 = new javax.swing.JTable(){
                            public boolean isCellEditable(int rowIndex, int colIndex) {
                                return false;
                            }};
                            jPanel6 = new javax.swing.JPanel();
                            jPanel3 = new javax.swing.JPanel();
                            jScrollPane3 = new javax.swing.JScrollPane();
                            jTable1 = new javax.swing.JTable();
                            jPanel4 = new javax.swing.JPanel();
                            jpPanel4 = new javax.swing.JPanel();
                            jScrollPane7 = new javax.swing.JScrollPane();
                            jTable2 = new javax.swing.JTable();
                            jPanel7 = new javax.swing.JPanel();
                            jtpViewer = new javax.swing.JTabbedPane();
                            jpSpectrum = new javax.swing.JPanel();
                            jdpMS = new javax.swing.JDesktopPane();
                            jpTIC = new javax.swing.JPanel();
                            jdpTIC = new javax.swing.JDesktopPane();
                            jp2D = new javax.swing.JPanel();
                            jp3D = new javax.swing.JPanel();
                            jpPropertiesBox = new javax.swing.JPanel();
                            jspProperties = new javax.swing.JSplitPane();
                            jpProperties = new javax.swing.JPanel();
                            jlProperties = new javax.swing.JLabel();
                            jpPropetiesTab = new javax.swing.JPanel();
                            jtpProperties = new javax.swing.JTabbedPane();
                            jpMzML = new javax.swing.JPanel();
                            jspMzML = new javax.swing.JSplitPane();
                            jspMzMLDetail = new javax.swing.JSplitPane();
                            jpMzMLMenu = new javax.swing.JPanel();
                            jlScan = new javax.swing.JLabel();
                            jtScan = new javax.swing.JTextField();
                            jlRT = new javax.swing.JLabel();
                            jtRT = new javax.swing.JTextField();
                            jlSearchMzML = new javax.swing.JLabel();
                            jtbMzMLOptions = new javax.swing.JToolBar();
                            jbExportMzMLExcel = new javax.swing.JButton();
                            jlExportMzMLXLS = new javax.swing.JLabel();
                            jspMzMLSubDetail = new javax.swing.JScrollPane();
                            jtMzML = new javax.swing.JTable(){
                                public boolean isCellEditable(int rowIndex, int colIndex) {
                                    return false;
                                }};
                                jpMzMLViewHeader = new javax.swing.JPanel();
                                jlFileNameMzMLText = new javax.swing.JLabel();
                                jlFileNameMzML = new javax.swing.JLabel();
                                jScrollPane9 = new javax.swing.JScrollPane();
                                jepMzMLView = new javax.swing.JEditorPane();
                                jpMGF = new javax.swing.JPanel();
                                jspMGF = new javax.swing.JSplitPane();
                                jpMGFViewHeader = new javax.swing.JPanel();
                                jlFileNameMGF = new javax.swing.JLabel();
                                jlFileNameMGFText = new javax.swing.JLabel();
                                jScrollPane10 = new javax.swing.JScrollPane();
                                jepMGFView = new javax.swing.JEditorPane();
                                jspMGFDetail = new javax.swing.JSplitPane();
                                jpMGFMenu = new javax.swing.JPanel();
                                jlSearchMGF = new javax.swing.JLabel();
                                jlScanTitle = new javax.swing.JLabel();
                                jtScanTitle = new javax.swing.JTextField();
                                jtbMGFOptions = new javax.swing.JToolBar();
                                jbExportMGFExcel = new javax.swing.JButton();
                                jlExportMGFXLS = new javax.swing.JLabel();
                                jspMGFSubDetail = new javax.swing.JScrollPane();
                                jtMGF = new javax.swing.JTable(){
                                    public boolean isCellEditable(int rowIndex, int colIndex) {
                                        return false;
                                    }};
                                    jpMzId = new javax.swing.JPanel();
                                    jspMzId = new javax.swing.JSplitPane();
                                    jspMzIdDetail = new javax.swing.JSplitPane();
                                    jpMzIdMenu = new javax.swing.JPanel();
                                    jlSearchMzId = new javax.swing.JLabel();
                                    jlProteinMzId = new javax.swing.JLabel();
                                    jtProteinMzId = new javax.swing.JTextField();
                                    jlPeptideMzId = new javax.swing.JLabel();
                                    jtPeptideMzId = new javax.swing.JTextField();
                                    jtbMzIdentMLOptions = new javax.swing.JToolBar();
                                    jbExportMzIdentMLExcel = new javax.swing.JButton();
                                    jlExportMzIDXLS = new javax.swing.JLabel();
                                    jspMzIdProtGroup = new javax.swing.JScrollPane();
                                    jtMzId = new javax.swing.JTable(){
                                        public boolean isCellEditable(int rowIndex, int colIndex) {
                                            return false;
                                        }};
                                        jlPeptideSpectrumMatches = new javax.swing.JLabel();
                                        jcbPSM = new javax.swing.JComboBox();
                                        jspMzIDProtGroup = new javax.swing.JScrollPane();
                                        jtMzIDProtGroup = new javax.swing.JTable();
                                        jpMzIdentMLViewHeader = new javax.swing.JPanel();
                                        jlFileNameMzID = new javax.swing.JLabel();
                                        jlFileNameMzIDText = new javax.swing.JLabel();
                                        jspMzIDView = new javax.swing.JScrollPane();
                                        jepMzIDView = new javax.swing.JEditorPane();
                                        jpMascotXML = new javax.swing.JPanel();
                                        jspMascotXML = new javax.swing.JSplitPane();
                                        jspMascotXMLSubDetail = new javax.swing.JSplitPane();
                                        jspMascotXMLDetail = new javax.swing.JScrollPane();
                                        jtMascotXMLView = new javax.swing.JTable(){
                                            public boolean isCellEditable(int rowIndex, int colIndex) {
                                                return false;
                                            }};
                                            jpMascotXMLMenu = new javax.swing.JPanel();
                                            jlSearchMascotXML = new javax.swing.JLabel();
                                            jlProtein = new javax.swing.JLabel();
                                            jtProtein = new javax.swing.JTextField();
                                            jlPeptide = new javax.swing.JLabel();
                                            jtPeptide = new javax.swing.JTextField();
                                            jlExportMzIDXLS1 = new javax.swing.JLabel();
                                            jtbMzIdentMLOptions1 = new javax.swing.JToolBar();
                                            jbExportMascotXMLExcel = new javax.swing.JButton();
                                            jpMascotXMLViewHeader = new javax.swing.JPanel();
                                            jlFileNameMascotXML = new javax.swing.JLabel();
                                            jlFileNameMascotXMLText = new javax.swing.JLabel();
                                            jspMascotXMLView = new javax.swing.JScrollPane();
                                            jepMascotXMLView = new javax.swing.JEditorPane();
                                            jpMzQuantML = new javax.swing.JPanel();
                                            jspMzQuantML = new javax.swing.JSplitPane();
                                            jtpMzQuantMLDetail = new javax.swing.JTabbedPane();
                                            jpPeptideQuant = new javax.swing.JPanel();
                                            jspPeptideQuant = new javax.swing.JSplitPane();
                                            jpPeptideQuantHeader = new javax.swing.JPanel();
                                            jlSearchMzQPep = new javax.swing.JLabel();
                                            jlPeptideMZQ = new javax.swing.JLabel();
                                            jtPeptideMZQ = new javax.swing.JTextField();
                                            jtbPepMZQ = new javax.swing.JToolBar();
                                            jbExportPepMZQExcel = new javax.swing.JButton();
                                            jspPeptideQuantDetail = new javax.swing.JScrollPane();
                                            jtPeptideQuant = new javax.swing.JTable(){
                                                public boolean isCellEditable(int rowIndex, int colIndex) {
                                                    return false;
                                                }};
                                                jpProteinQuant = new javax.swing.JPanel();
                                                jspProteinQuant = new javax.swing.JSplitPane();
                                                jpProteinQuantHeader = new javax.swing.JPanel();
                                                jlSearchMzQProt = new javax.swing.JLabel();
                                                jlProteinMZQ = new javax.swing.JLabel();
                                                jtProteinMZQ = new javax.swing.JTextField();
                                                jtbProtMZQ = new javax.swing.JToolBar();
                                                jbExportProtMZQExcel = new javax.swing.JButton();
                                                jspProteinQuantDetail = new javax.swing.JScrollPane();
                                                jtProteinQuant = new javax.swing.JTable(){
                                                    public boolean isCellEditable(int rowIndex, int colIndex) {
                                                        return false;
                                                    }};
                                                    FeatureQuant = new javax.swing.JPanel();
                                                    jspFeatureQuant = new javax.swing.JSplitPane();
                                                    jpFeatureQuantHeader = new javax.swing.JPanel();
                                                    jlSearchMzQFeat = new javax.swing.JLabel();
                                                    jlFeatureMZQ = new javax.swing.JLabel();
                                                    jtFeatureMZQ = new javax.swing.JTextField();
                                                    jtbFeatMZQ = new javax.swing.JToolBar();
                                                    jbExportFeatMZQExcel = new javax.swing.JButton();
                                                    jspFeatureQuantDetail = new javax.swing.JScrollPane();
                                                    jtFeatureQuant = new javax.swing.JTable(){
                                                        public boolean isCellEditable(int rowIndex, int colIndex) {
                                                            return false;
                                                        }};
                                                        jpMzQuantMLHeader = new javax.swing.JPanel();
                                                        jlFileNameMzQ = new javax.swing.JLabel();
                                                        jlFileNameMzQText = new javax.swing.JLabel();
                                                        jScrollPane12 = new javax.swing.JScrollPane();
                                                        jepMZQView = new javax.swing.JEditorPane();
                                                        jpProjectStatus = new javax.swing.JPanel();
                                                        jlRawFiles = new javax.swing.JLabel();
                                                        jlIdentFiles = new javax.swing.JLabel();
                                                        jlTechnique = new javax.swing.JLabel();
                                                        jlQuantFiles = new javax.swing.JLabel();
                                                        jlRawFilesStatus = new javax.swing.JLabel();
                                                        jlIdentFilesStatus = new javax.swing.JLabel();
                                                        jlQuantFilesStatus = new javax.swing.JLabel();
                                                        jcbTechnique = new javax.swing.JComboBox();
                                                        jcbOutputFormat = new javax.swing.JComboBox();
                                                        jmMain = new javax.swing.JMenuBar();
                                                        jmFile = new javax.swing.JMenu();
                                                        jmNewProject = new javax.swing.JMenuItem();
                                                        jmImportFile = new javax.swing.JMenuItem();
                                                        jSeparator1 = new javax.swing.JPopupMenu.Separator();
                                                        jmOpenProject = new javax.swing.JMenuItem();
                                                        jmOpenRecentProject = new javax.swing.JMenuItem();
                                                        jmCloseProject = new javax.swing.JMenuItem();
                                                        jSeparator3 = new javax.swing.JPopupMenu.Separator();
                                                        jmSaveProject = new javax.swing.JMenuItem();
                                                        jmSaveProjectAs = new javax.swing.JMenuItem();
                                                        jSeparator2 = new javax.swing.JPopupMenu.Separator();
                                                        jmPrint = new javax.swing.JMenuItem();
                                                        jSeparator4 = new javax.swing.JPopupMenu.Separator();
                                                        jmExit = new javax.swing.JMenuItem();
                                                        jmEdit = new javax.swing.JMenu();
                                                        jmCut = new javax.swing.JMenuItem();
                                                        jmCopy = new javax.swing.JMenuItem();
                                                        jmPaste = new javax.swing.JMenuItem();
                                                        jSeparator6 = new javax.swing.JPopupMenu.Separator();
                                                        jmView = new javax.swing.JMenu();
                                                        jmShowProjectFiles = new javax.swing.JMenuItem();
                                                        jmShowViewer = new javax.swing.JMenuItem();
                                                        jmShowProperties = new javax.swing.JMenuItem();
                                                        jmProject = new javax.swing.JMenu();
                                                        jmEditIdent = new javax.swing.JMenuItem();
                                                        jmEditQuant = new javax.swing.JMenuItem();
                                                        jmAnalyze = new javax.swing.JMenu();
                                                        jmRunIdentAnalysis = new javax.swing.JMenuItem();
                                                        jmRunQuantAnalysis = new javax.swing.JMenuItem();
                                                        jmTools = new javax.swing.JMenu();
                                                        jmConverters = new javax.swing.JMenu();
                                                        jmMzML2MGF = new javax.swing.JMenuItem();
                                                        jmMaxQ2MZQ = new javax.swing.JMenuItem();
                                                        jmProgenesis2MZQ = new javax.swing.JMenuItem();
                                                        jMzMLCompressed = new javax.swing.JMenuItem();
                                                        jmCustomize = new javax.swing.JMenuItem();
                                                        jmOptions = new javax.swing.JMenuItem();
                                                        jmDatabases = new javax.swing.JMenu();
                                                        jmSubmitPRIDE = new javax.swing.JMenuItem();
                                                        jmWindow = new javax.swing.JMenu();
                                                        jmSpectrum = new javax.swing.JMenuItem();
                                                        jmTIC = new javax.swing.JMenuItem();
                                                        jm2DView = new javax.swing.JMenuItem();
                                                        jm3DView = new javax.swing.JMenuItem();
                                                        jSeparator8 = new javax.swing.JPopupMenu.Separator();
                                                        jmMzMLView = new javax.swing.JMenuItem();
                                                        jmMGFView = new javax.swing.JMenuItem();
                                                        jmMzIdentMLView = new javax.swing.JMenuItem();
                                                        jmMascotXMLView = new javax.swing.JMenuItem();
                                                        jmMzQuantMLView = new javax.swing.JMenuItem();
                                                        jSeparator9 = new javax.swing.JPopupMenu.Separator();
                                                        jmLog = new javax.swing.JMenuItem();
                                                        jmRawData = new javax.swing.JMenuItem();
                                                        jmHelp = new javax.swing.JMenu();
                                                        jmHelpContent = new javax.swing.JMenuItem();
                                                        jSeparator5 = new javax.swing.JPopupMenu.Separator();
                                                        jmContactUs = new javax.swing.JMenuItem();
                                                        jmCheckUpdates = new javax.swing.JMenuItem();
                                                        jSeparator7 = new javax.swing.JPopupMenu.Separator();
                                                        jmAbout = new javax.swing.JMenuItem();

                                                        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
                                                        addWindowListener(new java.awt.event.WindowAdapter() {
                                                            public void windowClosing(java.awt.event.WindowEvent evt) {
                                                                formWindowClosed(evt);
                                                            }
                                                        });

                                                        jpToolBar.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
                                                        jpToolBar.setPreferredSize(new java.awt.Dimension(933, 32));

                                                        jbNewProject.setToolTipText("New Project (Ctrl + N)");
                                                        jbNewProject.setFocusable(false);
                                                        jbNewProject.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                                                        jbNewProject.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                                                        jbNewProject.addMouseListener(new java.awt.event.MouseAdapter() {
                                                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                                                jbNewProjectMouseClicked(evt);
                                                            }
                                                        });
                                                        jToolBar1.add(jbNewProject);

                                                        jbImportFile.setToolTipText("Import File (Ctrl + I)");
                                                        jbImportFile.setFocusable(false);
                                                        jbImportFile.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                                                        jbImportFile.setMaximumSize(new java.awt.Dimension(27, 21));
                                                        jbImportFile.setMinimumSize(new java.awt.Dimension(27, 21));
                                                        jbImportFile.setPreferredSize(new java.awt.Dimension(27, 21));
                                                        jbImportFile.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                                                        jbImportFile.addMouseListener(new java.awt.event.MouseAdapter() {
                                                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                                                jbImportFileMouseClicked(evt);
                                                            }
                                                        });
                                                        jToolBar1.add(jbImportFile);

                                                        jbOpenProject.setToolTipText("Open Project (Ctrl + O)");
                                                        jbOpenProject.setFocusable(false);
                                                        jbOpenProject.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                                                        jbOpenProject.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                                                        jbOpenProject.addMouseListener(new java.awt.event.MouseAdapter() {
                                                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                                                jbOpenProjectMouseClicked(evt);
                                                            }
                                                        });
                                                        jToolBar1.add(jbOpenProject);

                                                        jbSaveProject.setToolTipText("Save Project (Ctrl + S)");
                                                        jbSaveProject.setEnabled(false);
                                                        jbSaveProject.setFocusable(false);
                                                        jbSaveProject.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                                                        jbSaveProject.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                                                        jbSaveProject.addMouseListener(new java.awt.event.MouseAdapter() {
                                                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                                                jbSaveProjectMouseClicked(evt);
                                                            }
                                                        });
                                                        jToolBar1.add(jbSaveProject);

                                                        jbCut.setToolTipText("Cut (Ctrl + X)");
                                                        jbCut.setEnabled(false);
                                                        jbCut.setFocusable(false);
                                                        jbCut.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                                                        jbCut.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                                                        jToolBar2.add(jbCut);

                                                        jbCopy.setEnabled(false);
                                                        jbCopy.setFocusable(false);
                                                        jbCopy.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                                                        jbCopy.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                                                        jToolBar2.add(jbCopy);

                                                        jbPaste.setEnabled(false);
                                                        jbPaste.setFocusable(false);
                                                        jbPaste.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                                                        jbPaste.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                                                        jToolBar2.add(jbPaste);

                                                        jToolBar3.setMaximumSize(new java.awt.Dimension(34, 9));
                                                        jToolBar3.setMinimumSize(new java.awt.Dimension(34, 9));

                                                        jbRunIdentAnalysis.setToolTipText("Run Identification Pipeline (F7)");
                                                        jbRunIdentAnalysis.setFocusable(false);
                                                        jbRunIdentAnalysis.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                                                        jbRunIdentAnalysis.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                                                        jbRunIdentAnalysis.addMouseListener(new java.awt.event.MouseAdapter() {
                                                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                                                jbRunIdentAnalysisMouseClicked(evt);
                                                            }
                                                        });
                                                        jToolBar3.add(jbRunIdentAnalysis);

                                                        jbRunQuantAnalysis.setToolTipText("Run Quantitation Pipeline (F5)");
                                                        jbRunQuantAnalysis.setFocusable(false);
                                                        jbRunQuantAnalysis.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                                                        jbRunQuantAnalysis.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                                                        jbRunQuantAnalysis.addMouseListener(new java.awt.event.MouseAdapter() {
                                                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                                                jbRunQuantAnalysisMouseClicked(evt);
                                                            }
                                                        });
                                                        jToolBar3.add(jbRunQuantAnalysis);

                                                        javax.swing.GroupLayout jpToolBarLayout = new javax.swing.GroupLayout(jpToolBar);
                                                        jpToolBar.setLayout(jpToolBarLayout);
                                                        jpToolBarLayout.setHorizontalGroup(
                                                            jpToolBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jpToolBarLayout.createSequentialGroup()
                                                                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jToolBar3, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap(826, Short.MAX_VALUE))
                                                        );
                                                        jpToolBarLayout.setVerticalGroup(
                                                            jpToolBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpToolBarLayout.createSequentialGroup()
                                                                .addGroup(jpToolBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                    .addComponent(jToolBar3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                    .addComponent(jToolBar2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                    .addComponent(jToolBar1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addContainerGap())
                                                        );

                                                        jspMainPanelView.setDividerLocation(280);
                                                        jspMainPanelView.setDividerSize(3);

                                                        jpLeftPanelView.setBackground(new java.awt.Color(204, 204, 255));

                                                        jspLeftPanelView.setDividerLocation(20);
                                                        jspLeftPanelView.setDividerSize(1);
                                                        jspLeftPanelView.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

                                                        jpProjectHeader.setMaximumSize(new java.awt.Dimension(32767, 25));
                                                        jpProjectHeader.setMinimumSize(new java.awt.Dimension(279, 25));
                                                        jpProjectHeader.setPreferredSize(new java.awt.Dimension(280, 25));

                                                        jlFiles.setBackground(new java.awt.Color(255, 255, 255));
                                                        jlFiles.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
                                                        jlFiles.setForeground(new java.awt.Color(102, 102, 102));
                                                        jlFiles.setText("Files");

                                                        javax.swing.GroupLayout jpProjectHeaderLayout = new javax.swing.GroupLayout(jpProjectHeader);
                                                        jpProjectHeader.setLayout(jpProjectHeaderLayout);
                                                        jpProjectHeaderLayout.setHorizontalGroup(
                                                            jpProjectHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jpProjectHeaderLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jlFiles)
                                                                .addContainerGap(235, Short.MAX_VALUE))
                                                        );
                                                        jpProjectHeaderLayout.setVerticalGroup(
                                                            jpProjectHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jpProjectHeaderLayout.createSequentialGroup()
                                                                .addComponent(jlFiles)
                                                                .addContainerGap(7, Short.MAX_VALUE))
                                                        );

                                                        jspLeftPanelView.setTopComponent(jpProjectHeader);

                                                        jpProjectDetails.setBackground(new java.awt.Color(255, 255, 255));

                                                        jspProjectDetails.setBackground(new java.awt.Color(255, 255, 255));
                                                        jspProjectDetails.setDividerLocation(130);
                                                        jspProjectDetails.setDividerSize(1);
                                                        jspProjectDetails.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

                                                        jtpRawFiles.setBackground(new java.awt.Color(255, 255, 255));

                                                        jspRawFiles.setBackground(new java.awt.Color(255, 255, 255));

                                                        jtRawFiles.setModel(new javax.swing.table.DefaultTableModel(
                                                            new Object [][] {

                                                            },
                                                            new String [] {
                                                                "Name", "Path", "Type", "Version"
                                                            }
                                                        ));
                                                        jtRawFiles.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
                                                        jtRawFiles.addMouseListener(new java.awt.event.MouseAdapter() {
                                                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                                                jtRawFilesMouseClicked(evt);
                                                            }
                                                        });
                                                        jspRawFiles.setViewportView(jtRawFiles);

                                                        jtpRawFiles.addTab("Raw Files", jspRawFiles);

                                                        javax.swing.GroupLayout jpLeftMenuTopLayout = new javax.swing.GroupLayout(jpLeftMenuTop);
                                                        jpLeftMenuTop.setLayout(jpLeftMenuTopLayout);
                                                        jpLeftMenuTopLayout.setHorizontalGroup(
                                                            jpLeftMenuTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGap(0, 277, Short.MAX_VALUE)
                                                            .addGroup(jpLeftMenuTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(jtpRawFiles, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE))
                                                        );
                                                        jpLeftMenuTopLayout.setVerticalGroup(
                                                            jpLeftMenuTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGap(0, 129, Short.MAX_VALUE)
                                                            .addGroup(jpLeftMenuTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(jtpRawFiles, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE))
                                                        );

                                                        jspProjectDetails.setTopComponent(jpLeftMenuTop);

                                                        jspLeftMenuBottom.setDividerLocation(200);
                                                        jspLeftMenuBottom.setDividerSize(1);
                                                        jspLeftMenuBottom.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

                                                        jtpQuantFiles.setBackground(new java.awt.Color(255, 255, 255));

                                                        jtQuantFiles.setModel(new javax.swing.table.DefaultTableModel(
                                                            new Object [][] {

                                                            },
                                                            new String [] {
                                                                "Name", "Path", "Type", "Version"
                                                            }
                                                        ));
                                                        jtQuantFiles.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
                                                        jtQuantFiles.addMouseListener(new java.awt.event.MouseAdapter() {
                                                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                                                jtQuantFilesMouseClicked(evt);
                                                            }
                                                        });
                                                        jspQuantFiles.setViewportView(jtQuantFiles);

                                                        jtpQuantFiles.addTab("Quantitation Files", jspQuantFiles);

                                                        javax.swing.GroupLayout jpQuantFilesLayout = new javax.swing.GroupLayout(jpQuantFiles);
                                                        jpQuantFiles.setLayout(jpQuantFilesLayout);
                                                        jpQuantFilesLayout.setHorizontalGroup(
                                                            jpQuantFilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jtpQuantFiles, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                                                        );
                                                        jpQuantFilesLayout.setVerticalGroup(
                                                            jpQuantFilesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jtpQuantFiles, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
                                                        );

                                                        jspLeftMenuBottom.setRightComponent(jpQuantFiles);

                                                        jtpIdentFiles.setBackground(new java.awt.Color(255, 255, 255));

                                                        jtIdentFiles.setModel(new javax.swing.table.DefaultTableModel(
                                                            new Object [][] {

                                                            },
                                                            new String [] {
                                                                "Name", "Path", "Type", "Version", "Raw File"
                                                            }
                                                        ));
                                                        jtIdentFiles.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
                                                        jtIdentFiles.addMouseListener(new java.awt.event.MouseAdapter() {
                                                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                                                jtIdentFilesMouseClicked(evt);
                                                            }
                                                        });
                                                        jspIdentFiles.setViewportView(jtIdentFiles);

                                                        jtpIdentFiles.addTab("Identification Files", jspIdentFiles);

                                                        jspLeftMenuBottom.setTopComponent(jtpIdentFiles);

                                                        javax.swing.GroupLayout jpLeftMenuBottomLayout = new javax.swing.GroupLayout(jpLeftMenuBottom);
                                                        jpLeftMenuBottom.setLayout(jpLeftMenuBottomLayout);
                                                        jpLeftMenuBottomLayout.setHorizontalGroup(
                                                            jpLeftMenuBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jspLeftMenuBottom, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                                                        );
                                                        jpLeftMenuBottomLayout.setVerticalGroup(
                                                            jpLeftMenuBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jspLeftMenuBottom, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
                                                        );

                                                        jspProjectDetails.setRightComponent(jpLeftMenuBottom);

                                                        javax.swing.GroupLayout jpProjectDetailsLayout = new javax.swing.GroupLayout(jpProjectDetails);
                                                        jpProjectDetails.setLayout(jpProjectDetailsLayout);
                                                        jpProjectDetailsLayout.setHorizontalGroup(
                                                            jpProjectDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jspProjectDetails)
                                                        );
                                                        jpProjectDetailsLayout.setVerticalGroup(
                                                            jpProjectDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jspProjectDetails, javax.swing.GroupLayout.Alignment.TRAILING)
                                                        );

                                                        jspLeftPanelView.setRightComponent(jpProjectDetails);

                                                        javax.swing.GroupLayout jpLeftPanelViewLayout = new javax.swing.GroupLayout(jpLeftPanelView);
                                                        jpLeftPanelView.setLayout(jpLeftPanelViewLayout);
                                                        jpLeftPanelViewLayout.setHorizontalGroup(
                                                            jpLeftPanelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jspLeftPanelView, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
                                                        );
                                                        jpLeftPanelViewLayout.setVerticalGroup(
                                                            jpLeftPanelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jspLeftPanelView, javax.swing.GroupLayout.DEFAULT_SIZE, 637, Short.MAX_VALUE)
                                                        );

                                                        jspMainPanelView.setLeftComponent(jpLeftPanelView);

                                                        jpViewerAndProperties.setBackground(new java.awt.Color(204, 204, 255));

                                                        jspViewerAndProperties.setDividerLocation(380);
                                                        jspViewerAndProperties.setDividerSize(3);

                                                        jspLeftViewer.setDividerLocation(20);
                                                        jspLeftViewer.setDividerSize(1);
                                                        jspLeftViewer.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

                                                        jspLeftViewerHeader.setMaximumSize(new java.awt.Dimension(32767, 25));
                                                        jspLeftViewerHeader.setMinimumSize(new java.awt.Dimension(380, 25));
                                                        jspLeftViewerHeader.setPreferredSize(new java.awt.Dimension(380, 25));

                                                        jlViewer.setBackground(new java.awt.Color(255, 255, 255));
                                                        jlViewer.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
                                                        jlViewer.setForeground(new java.awt.Color(102, 102, 102));
                                                        jlViewer.setText("Viewer");

                                                        javax.swing.GroupLayout jspLeftViewerHeaderLayout = new javax.swing.GroupLayout(jspLeftViewerHeader);
                                                        jspLeftViewerHeader.setLayout(jspLeftViewerHeaderLayout);
                                                        jspLeftViewerHeaderLayout.setHorizontalGroup(
                                                            jspLeftViewerHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jspLeftViewerHeaderLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jlViewer)
                                                                .addContainerGap(316, Short.MAX_VALUE))
                                                        );
                                                        jspLeftViewerHeaderLayout.setVerticalGroup(
                                                            jspLeftViewerHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jspLeftViewerHeaderLayout.createSequentialGroup()
                                                                .addComponent(jlViewer)
                                                                .addContainerGap(7, Short.MAX_VALUE))
                                                        );

                                                        jspLeftViewer.setTopComponent(jspLeftViewerHeader);

                                                        jpLeftViewerDetails.setBackground(new java.awt.Color(255, 255, 255));

                                                        jspLeftViewerDetails.setDividerLocation(350);
                                                        jspLeftViewerDetails.setDividerSize(2);
                                                        jspLeftViewerDetails.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

                                                        jtaLog.setColumns(20);
                                                        jtaLog.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
                                                        jtaLog.setRows(5);
                                                        jspLog.setViewportView(jtaLog);

                                                        jtpLog.addTab("Log", jspLog);

                                                        jspRawDataValues.setDividerLocation(40);
                                                        jspRawDataValues.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

                                                        jpRawDataValuesMenu.setMaximumSize(new java.awt.Dimension(32767, 50));
                                                        jpRawDataValuesMenu.setMinimumSize(new java.awt.Dimension(0, 50));
                                                        jpRawDataValuesMenu.setPreferredSize(new java.awt.Dimension(462, 50));

                                                        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
                                                        jLabel1.setText("Search:");

                                                        jtMSIndex.setToolTipText("Enter the index");
                                                        jtMSIndex.addKeyListener(new java.awt.event.KeyAdapter() {
                                                            public void keyPressed(java.awt.event.KeyEvent evt) {
                                                                jtMSIndexKeyPressed(evt);
                                                            }
                                                        });

                                                        jLabel2.setText("Index:");

                                                        jLabel3.setText("m/z:");

                                                        jtMSMz.setToolTipText("Enter the m/z value ");
                                                        jtMSMz.addKeyListener(new java.awt.event.KeyAdapter() {
                                                            public void keyPressed(java.awt.event.KeyEvent evt) {
                                                                jtMSMzKeyPressed(evt);
                                                            }
                                                        });

                                                        javax.swing.GroupLayout jpRawDataValuesMenuLayout = new javax.swing.GroupLayout(jpRawDataValuesMenu);
                                                        jpRawDataValuesMenu.setLayout(jpRawDataValuesMenuLayout);
                                                        jpRawDataValuesMenuLayout.setHorizontalGroup(
                                                            jpRawDataValuesMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jpRawDataValuesMenuLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jLabel1)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(jLabel2)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(jtMSIndex, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(jLabel3)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(jtMSMz, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap(103, Short.MAX_VALUE))
                                                        );
                                                        jpRawDataValuesMenuLayout.setVerticalGroup(
                                                            jpRawDataValuesMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jpRawDataValuesMenuLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jpRawDataValuesMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                    .addComponent(jLabel1)
                                                                    .addComponent(jLabel2)
                                                                    .addComponent(jtMSIndex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addComponent(jLabel3)
                                                                    .addComponent(jtMSMz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addContainerGap(19, Short.MAX_VALUE))
                                                        );

                                                        jspRawDataValues.setLeftComponent(jpRawDataValuesMenu);

                                                        jtRawData.setModel(new javax.swing.table.DefaultTableModel(
                                                            new Object [][] {

                                                            },
                                                            new String [] {
                                                                "Index", "m/z", "Intensity"
                                                            }
                                                        ));
                                                        jtRawData.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                                                        jspRawData.setViewportView(jtRawData);

                                                        jspRawDataValues.setBottomComponent(jspRawData);

                                                        javax.swing.GroupLayout jpRawDataValuesLayout = new javax.swing.GroupLayout(jpRawDataValues);
                                                        jpRawDataValues.setLayout(jpRawDataValuesLayout);
                                                        jpRawDataValuesLayout.setHorizontalGroup(
                                                            jpRawDataValuesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jspRawDataValues, javax.swing.GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
                                                        );
                                                        jpRawDataValuesLayout.setVerticalGroup(
                                                            jpRawDataValuesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jspRawDataValues, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                                                        );

                                                        jtpLog.addTab("Raw Data", jpRawDataValues);

                                                        jtTemplate1.setModel(new javax.swing.table.DefaultTableModel(
                                                            new Object [][] {

                                                            },
                                                            new String [] {
                                                                "m/z Index", "Scan Index", "Quant", "Template2 Index"
                                                            }
                                                        ));
                                                        jScrollPane1.setViewportView(jtTemplate1);

                                                        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
                                                        jPanel5.setLayout(jPanel5Layout);
                                                        jPanel5Layout.setHorizontalGroup(
                                                            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGap(0, 373, Short.MAX_VALUE)
                                                        );
                                                        jPanel5Layout.setVerticalGroup(
                                                            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGap(0, 51, Short.MAX_VALUE)
                                                        );

                                                        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                                                        jPanel1.setLayout(jPanel1Layout);
                                                        jPanel1Layout.setHorizontalGroup(
                                                            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
                                                        );
                                                        jPanel1Layout.setVerticalGroup(
                                                            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE))
                                                        );

                                                        jtpLog.addTab("Template 1", jPanel1);

                                                        jtTemplate2.setModel(new javax.swing.table.DefaultTableModel(
                                                            new Object [][] {

                                                            },
                                                            new String [] {
                                                                "Template Index", "x", "y", "i"
                                                            }
                                                        ));
                                                        jScrollPane2.setViewportView(jtTemplate2);

                                                        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
                                                        jPanel6.setLayout(jPanel6Layout);
                                                        jPanel6Layout.setHorizontalGroup(
                                                            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGap(0, 373, Short.MAX_VALUE)
                                                        );
                                                        jPanel6Layout.setVerticalGroup(
                                                            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGap(0, 52, Short.MAX_VALUE)
                                                        );

                                                        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
                                                        jPanel2.setLayout(jPanel2Layout);
                                                        jPanel2Layout.setHorizontalGroup(
                                                            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
                                                        );
                                                        jPanel2Layout.setVerticalGroup(
                                                            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE))
                                                        );

                                                        jtpLog.addTab("Template 2", jPanel2);

                                                        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                                                            new Object [][] {

                                                            },
                                                            new String [] {
                                                                "Scan Number"
                                                            }
                                                        ));
                                                        jScrollPane3.setViewportView(jTable1);

                                                        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
                                                        jPanel4.setLayout(jPanel4Layout);
                                                        jPanel4Layout.setHorizontalGroup(
                                                            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGap(0, 373, Short.MAX_VALUE)
                                                        );
                                                        jPanel4Layout.setVerticalGroup(
                                                            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGap(0, 58, Short.MAX_VALUE)
                                                        );

                                                        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
                                                        jPanel3.setLayout(jPanel3Layout);
                                                        jPanel3Layout.setHorizontalGroup(
                                                            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
                                                        );
                                                        jPanel3Layout.setVerticalGroup(
                                                            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE))
                                                        );

                                                        jtpLog.addTab("Synth Array", jPanel3);

                                                        jTable2.setModel(new javax.swing.table.DefaultTableModel(
                                                            new Object [][] {

                                                            },
                                                            new String [] {
                                                                "Scan Number"
                                                            }
                                                        ));
                                                        jScrollPane7.setViewportView(jTable2);

                                                        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
                                                        jPanel7.setLayout(jPanel7Layout);
                                                        jPanel7Layout.setHorizontalGroup(
                                                            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGap(0, 373, Short.MAX_VALUE)
                                                        );
                                                        jPanel7Layout.setVerticalGroup(
                                                            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGap(0, 57, Short.MAX_VALUE)
                                                        );

                                                        javax.swing.GroupLayout jpPanel4Layout = new javax.swing.GroupLayout(jpPanel4);
                                                        jpPanel4.setLayout(jpPanel4Layout);
                                                        jpPanel4Layout.setHorizontalGroup(
                                                            jpPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
                                                        );
                                                        jpPanel4Layout.setVerticalGroup(
                                                            jpPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jpPanel4Layout.createSequentialGroup()
                                                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE))
                                                        );

                                                        jtpLog.addTab("Resamp Array", jpPanel4);

                                                        javax.swing.GroupLayout jpLeftViewerBottomLayout = new javax.swing.GroupLayout(jpLeftViewerBottom);
                                                        jpLeftViewerBottom.setLayout(jpLeftViewerBottomLayout);
                                                        jpLeftViewerBottomLayout.setHorizontalGroup(
                                                            jpLeftViewerBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jtpLog)
                                                        );
                                                        jpLeftViewerBottomLayout.setVerticalGroup(
                                                            jpLeftViewerBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jtpLog)
                                                        );

                                                        jspLeftViewerDetails.setRightComponent(jpLeftViewerBottom);

                                                        jdpMS.setBackground(new java.awt.Color(255, 255, 255));

                                                        javax.swing.GroupLayout jpSpectrumLayout = new javax.swing.GroupLayout(jpSpectrum);
                                                        jpSpectrum.setLayout(jpSpectrumLayout);
                                                        jpSpectrumLayout.setHorizontalGroup(
                                                            jpSpectrumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGap(0, 373, Short.MAX_VALUE)
                                                            .addGroup(jpSpectrumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(jdpMS, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE))
                                                        );
                                                        jpSpectrumLayout.setVerticalGroup(
                                                            jpSpectrumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGap(0, 321, Short.MAX_VALUE)
                                                            .addGroup(jpSpectrumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(jdpMS, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE))
                                                        );

                                                        jtpViewer.addTab("Spectrum View", jpSpectrum);

                                                        jpTIC.setBackground(new java.awt.Color(255, 255, 255));

                                                        jdpTIC.setBackground(new java.awt.Color(255, 255, 255));
                                                        jdpTIC.setForeground(new java.awt.Color(255, 255, 255));

                                                        javax.swing.GroupLayout jpTICLayout = new javax.swing.GroupLayout(jpTIC);
                                                        jpTIC.setLayout(jpTICLayout);
                                                        jpTICLayout.setHorizontalGroup(
                                                            jpTICLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jdpTIC, javax.swing.GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
                                                        );
                                                        jpTICLayout.setVerticalGroup(
                                                            jpTICLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jdpTIC, javax.swing.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
                                                        );

                                                        jtpViewer.addTab("TIC View", jpTIC);

                                                        jp2D.setBackground(new java.awt.Color(255, 255, 255));

                                                        javax.swing.GroupLayout jp2DLayout = new javax.swing.GroupLayout(jp2D);
                                                        jp2D.setLayout(jp2DLayout);
                                                        jp2DLayout.setHorizontalGroup(
                                                            jp2DLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGap(0, 373, Short.MAX_VALUE)
                                                        );
                                                        jp2DLayout.setVerticalGroup(
                                                            jp2DLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGap(0, 321, Short.MAX_VALUE)
                                                        );

                                                        jtpViewer.addTab("2D View", jp2D);

                                                        jp3D.setBackground(new java.awt.Color(255, 255, 255));

                                                        javax.swing.GroupLayout jp3DLayout = new javax.swing.GroupLayout(jp3D);
                                                        jp3D.setLayout(jp3DLayout);
                                                        jp3DLayout.setHorizontalGroup(
                                                            jp3DLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGap(0, 373, Short.MAX_VALUE)
                                                        );
                                                        jp3DLayout.setVerticalGroup(
                                                            jp3DLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGap(0, 321, Short.MAX_VALUE)
                                                        );

                                                        jtpViewer.addTab("3D View", jp3D);

                                                        jspLeftViewerDetails.setTopComponent(jtpViewer);

                                                        javax.swing.GroupLayout jpLeftViewerDetailsLayout = new javax.swing.GroupLayout(jpLeftViewerDetails);
                                                        jpLeftViewerDetails.setLayout(jpLeftViewerDetailsLayout);
                                                        jpLeftViewerDetailsLayout.setHorizontalGroup(
                                                            jpLeftViewerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jspLeftViewerDetails)
                                                        );
                                                        jpLeftViewerDetailsLayout.setVerticalGroup(
                                                            jpLeftViewerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jspLeftViewerDetails)
                                                        );

                                                        jspLeftViewer.setRightComponent(jpLeftViewerDetails);

                                                        javax.swing.GroupLayout jpLeftViewerLayout = new javax.swing.GroupLayout(jpLeftViewer);
                                                        jpLeftViewer.setLayout(jpLeftViewerLayout);
                                                        jpLeftViewerLayout.setHorizontalGroup(
                                                            jpLeftViewerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jspLeftViewer)
                                                        );
                                                        jpLeftViewerLayout.setVerticalGroup(
                                                            jpLeftViewerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jspLeftViewer, javax.swing.GroupLayout.DEFAULT_SIZE, 635, Short.MAX_VALUE)
                                                        );

                                                        jspViewerAndProperties.setLeftComponent(jpLeftViewer);

                                                        jspProperties.setDividerLocation(20);
                                                        jspProperties.setDividerSize(1);
                                                        jspProperties.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

                                                        jpProperties.setMaximumSize(new java.awt.Dimension(32767, 25));
                                                        jpProperties.setMinimumSize(new java.awt.Dimension(302, 25));
                                                        jpProperties.setPreferredSize(new java.awt.Dimension(313, 25));

                                                        jlProperties.setBackground(new java.awt.Color(255, 255, 255));
                                                        jlProperties.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
                                                        jlProperties.setForeground(new java.awt.Color(102, 102, 102));
                                                        jlProperties.setText("Data and Metadata");

                                                        javax.swing.GroupLayout jpPropertiesLayout = new javax.swing.GroupLayout(jpProperties);
                                                        jpProperties.setLayout(jpPropertiesLayout);
                                                        jpPropertiesLayout.setHorizontalGroup(
                                                            jpPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jpPropertiesLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jlProperties)
                                                                .addContainerGap(417, Short.MAX_VALUE))
                                                        );
                                                        jpPropertiesLayout.setVerticalGroup(
                                                            jpPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jpPropertiesLayout.createSequentialGroup()
                                                                .addComponent(jlProperties)
                                                                .addContainerGap(7, Short.MAX_VALUE))
                                                        );

                                                        jspProperties.setTopComponent(jpProperties);

                                                        jpPropetiesTab.setBackground(new java.awt.Color(255, 255, 255));
                                                        jpPropetiesTab.setForeground(new java.awt.Color(153, 153, 255));

                                                        jtpProperties.setBackground(new java.awt.Color(255, 255, 255));

                                                        jspMzML.setBackground(new java.awt.Color(255, 255, 255));
                                                        jspMzML.setDividerLocation(110);
                                                        jspMzML.setDividerSize(1);
                                                        jspMzML.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

                                                        jspMzMLDetail.setDividerLocation(90);
                                                        jspMzMLDetail.setDividerSize(1);
                                                        jspMzMLDetail.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

                                                        jpMzMLMenu.setBorder(javax.swing.BorderFactory.createEtchedBorder());

                                                        jlScan.setText("Scan Index:");

                                                        jtScan.setToolTipText("Enter the scan number");
                                                        jtScan.addKeyListener(new java.awt.event.KeyAdapter() {
                                                            public void keyPressed(java.awt.event.KeyEvent evt) {
                                                                jtScanKeyPressed(evt);
                                                            }
                                                        });

                                                        jlRT.setText("RT (sec):");

                                                        jtRT.setToolTipText("Enter a retention time value (in seconds)");
                                                        jtRT.addKeyListener(new java.awt.event.KeyAdapter() {
                                                            public void keyPressed(java.awt.event.KeyEvent evt) {
                                                                jtRTKeyPressed(evt);
                                                            }
                                                        });

                                                        jlSearchMzML.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
                                                        jlSearchMzML.setText("Search:");

                                                        jtbMzMLOptions.setBorder(javax.swing.BorderFactory.createEtchedBorder());
                                                        jtbMzMLOptions.setFloatable(false);
                                                        jtbMzMLOptions.setMaximumSize(new java.awt.Dimension(30, 23));
                                                        jtbMzMLOptions.setMinimumSize(new java.awt.Dimension(30, 23));
                                                        jtbMzMLOptions.setPreferredSize(new java.awt.Dimension(100, 23));

                                                        jbExportMzMLExcel.setMaximumSize(new java.awt.Dimension(28, 24));
                                                        jbExportMzMLExcel.setMinimumSize(new java.awt.Dimension(28, 24));
                                                        jbExportMzMLExcel.setPreferredSize(new java.awt.Dimension(28, 24));
                                                        jbExportMzMLExcel.addMouseListener(new java.awt.event.MouseAdapter() {
                                                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                                                jbExportMzMLExcelMouseClicked(evt);
                                                            }
                                                        });
                                                        jtbMzMLOptions.add(jbExportMzMLExcel);

                                                        jlExportMzMLXLS.setText("Export to:");

                                                        javax.swing.GroupLayout jpMzMLMenuLayout = new javax.swing.GroupLayout(jpMzMLMenu);
                                                        jpMzMLMenu.setLayout(jpMzMLMenuLayout);
                                                        jpMzMLMenuLayout.setHorizontalGroup(
                                                            jpMzMLMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jpMzMLMenuLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jpMzMLMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                    .addComponent(jlSearchMzML)
                                                                    .addComponent(jlExportMzMLXLS))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jpMzMLMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                    .addComponent(jtbMzMLOptions, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                    .addGroup(jpMzMLMenuLayout.createSequentialGroup()
                                                                        .addComponent(jlScan)
                                                                        .addGap(18, 18, 18)
                                                                        .addComponent(jtScan, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addGap(18, 18, 18)
                                                                        .addComponent(jlRT)
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                        .addComponent(jtRT, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addContainerGap(210, Short.MAX_VALUE))
                                                        );
                                                        jpMzMLMenuLayout.setVerticalGroup(
                                                            jpMzMLMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jpMzMLMenuLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jpMzMLMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                    .addComponent(jtScan)
                                                                    .addComponent(jtRT)
                                                                    .addGroup(jpMzMLMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jlSearchMzML)
                                                                        .addComponent(jlScan)
                                                                        .addComponent(jlRT)))
                                                                .addGroup(jpMzMLMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                    .addGroup(jpMzMLMenuLayout.createSequentialGroup()
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(jtbMzMLOptions, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                    .addGroup(jpMzMLMenuLayout.createSequentialGroup()
                                                                        .addGap(17, 17, 17)
                                                                        .addComponent(jlExportMzMLXLS)))
                                                                .addGap(13, 13, 13))
                                                        );

                                                        jspMzMLDetail.setLeftComponent(jpMzMLMenu);

                                                        jtMzML.setModel(new javax.swing.table.DefaultTableModel(
                                                            new Object [][] {

                                                            },
                                                            new String [] {
                                                                "", "Index", "ID", "MS", "Base peak m/z", "Base peak int", "RT (sec)", "Precurs m/z"
                                                            }
                                                        ));
                                                        jtMzML.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
                                                        jtMzML.addMouseListener(new java.awt.event.MouseAdapter() {
                                                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                                                jtMzMLMouseClicked(evt);
                                                            }
                                                        });
                                                        jspMzMLSubDetail.setViewportView(jtMzML);

                                                        jspMzMLDetail.setRightComponent(jspMzMLSubDetail);

                                                        jspMzML.setBottomComponent(jspMzMLDetail);

                                                        jlFileNameMzML.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
                                                        jlFileNameMzML.setText("File:");

                                                        jepMzMLView.setContentType("text/html");
                                                        jepMzMLView.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
                                                        jepMzMLView.setPreferredSize(new java.awt.Dimension(144, 84));
                                                        jScrollPane9.setViewportView(jepMzMLView);

                                                        javax.swing.GroupLayout jpMzMLViewHeaderLayout = new javax.swing.GroupLayout(jpMzMLViewHeader);
                                                        jpMzMLViewHeader.setLayout(jpMzMLViewHeaderLayout);
                                                        jpMzMLViewHeaderLayout.setHorizontalGroup(
                                                            jpMzMLViewHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jpMzMLViewHeaderLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jpMzMLViewHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                    .addGroup(jpMzMLViewHeaderLayout.createSequentialGroup()
                                                                        .addComponent(jlFileNameMzML)
                                                                        .addGap(38, 38, 38)
                                                                        .addComponent(jlFileNameMzMLText, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 535, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addContainerGap(20, Short.MAX_VALUE))
                                                        );
                                                        jpMzMLViewHeaderLayout.setVerticalGroup(
                                                            jpMzMLViewHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jpMzMLViewHeaderLayout.createSequentialGroup()
                                                                .addGap(19, 19, 19)
                                                                .addGroup(jpMzMLViewHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                    .addComponent(jlFileNameMzML)
                                                                    .addComponent(jlFileNameMzMLText, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap(18, Short.MAX_VALUE))
                                                        );

                                                        jspMzML.setLeftComponent(jpMzMLViewHeader);

                                                        javax.swing.GroupLayout jpMzMLLayout = new javax.swing.GroupLayout(jpMzML);
                                                        jpMzML.setLayout(jpMzMLLayout);
                                                        jpMzMLLayout.setHorizontalGroup(
                                                            jpMzMLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jspMzML)
                                                        );
                                                        jpMzMLLayout.setVerticalGroup(
                                                            jpMzMLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jspMzML, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE)
                                                        );

                                                        jtpProperties.addTab("mzML View", jpMzML);

                                                        jspMGF.setDividerLocation(110);
                                                        jspMGF.setDividerSize(1);
                                                        jspMGF.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

                                                        jpMGFViewHeader.setPreferredSize(new java.awt.Dimension(604, 115));

                                                        jlFileNameMGF.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
                                                        jlFileNameMGF.setText("File:");

                                                        jepMGFView.setContentType("text/html");
                                                        jepMGFView.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
                                                        jepMGFView.setPreferredSize(new java.awt.Dimension(144, 84));
                                                        jScrollPane10.setViewportView(jepMGFView);

                                                        javax.swing.GroupLayout jpMGFViewHeaderLayout = new javax.swing.GroupLayout(jpMGFViewHeader);
                                                        jpMGFViewHeader.setLayout(jpMGFViewHeaderLayout);
                                                        jpMGFViewHeaderLayout.setHorizontalGroup(
                                                            jpMGFViewHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jpMGFViewHeaderLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jpMGFViewHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                    .addGroup(jpMGFViewHeaderLayout.createSequentialGroup()
                                                                        .addComponent(jlFileNameMGF)
                                                                        .addGap(18, 18, 18)
                                                                        .addComponent(jlFileNameMGFText, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 535, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addContainerGap(20, Short.MAX_VALUE))
                                                        );
                                                        jpMGFViewHeaderLayout.setVerticalGroup(
                                                            jpMGFViewHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jpMGFViewHeaderLayout.createSequentialGroup()
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jpMGFViewHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                    .addComponent(jlFileNameMGFText, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addComponent(jlFileNameMGF))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap(19, Short.MAX_VALUE))
                                                        );

                                                        jspMGF.setLeftComponent(jpMGFViewHeader);

                                                        jspMGFDetail.setDividerLocation(90);
                                                        jspMGFDetail.setDividerSize(1);
                                                        jspMGFDetail.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

                                                        jlSearchMGF.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
                                                        jlSearchMGF.setText("Search:");

                                                        jlScanTitle.setText("Scan title:");

                                                        jtScanTitle.setToolTipText("Enter any character(s) that is (are) specified in the scan title");
                                                        jtScanTitle.addKeyListener(new java.awt.event.KeyAdapter() {
                                                            public void keyPressed(java.awt.event.KeyEvent evt) {
                                                                jtScanTitleKeyPressed(evt);
                                                            }
                                                        });

                                                        jtbMGFOptions.setBorder(javax.swing.BorderFactory.createEtchedBorder());
                                                        jtbMGFOptions.setFloatable(false);
                                                        jtbMGFOptions.setMaximumSize(new java.awt.Dimension(30, 23));
                                                        jtbMGFOptions.setMinimumSize(new java.awt.Dimension(30, 23));
                                                        jtbMGFOptions.setPreferredSize(new java.awt.Dimension(100, 23));

                                                        jbExportMGFExcel.setMaximumSize(new java.awt.Dimension(28, 24));
                                                        jbExportMGFExcel.setMinimumSize(new java.awt.Dimension(28, 24));
                                                        jbExportMGFExcel.setPreferredSize(new java.awt.Dimension(28, 24));
                                                        jbExportMGFExcel.addMouseListener(new java.awt.event.MouseAdapter() {
                                                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                                                jbExportMGFExcelMouseClicked(evt);
                                                            }
                                                        });
                                                        jtbMGFOptions.add(jbExportMGFExcel);

                                                        jlExportMGFXLS.setText("Export to:");

                                                        javax.swing.GroupLayout jpMGFMenuLayout = new javax.swing.GroupLayout(jpMGFMenu);
                                                        jpMGFMenu.setLayout(jpMGFMenuLayout);
                                                        jpMGFMenuLayout.setHorizontalGroup(
                                                            jpMGFMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jpMGFMenuLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jpMGFMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                    .addComponent(jlSearchMGF)
                                                                    .addComponent(jlExportMGFXLS))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jpMGFMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                    .addComponent(jtbMGFOptions, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                    .addGroup(jpMGFMenuLayout.createSequentialGroup()
                                                                        .addComponent(jlScanTitle)
                                                                        .addGap(18, 18, 18)
                                                                        .addComponent(jtScanTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addContainerGap(197, Short.MAX_VALUE))
                                                        );
                                                        jpMGFMenuLayout.setVerticalGroup(
                                                            jpMGFMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jpMGFMenuLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jpMGFMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                    .addComponent(jlSearchMGF)
                                                                    .addComponent(jlScanTitle)
                                                                    .addComponent(jtScanTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGroup(jpMGFMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                    .addGroup(jpMGFMenuLayout.createSequentialGroup()
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(jtbMGFOptions, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                    .addGroup(jpMGFMenuLayout.createSequentialGroup()
                                                                        .addGap(18, 18, 18)
                                                                        .addComponent(jlExportMGFXLS)))
                                                                .addContainerGap(17, Short.MAX_VALUE))
                                                        );

                                                        jspMGFDetail.setTopComponent(jpMGFMenu);

                                                        jtMGF.setModel(new javax.swing.table.DefaultTableModel(
                                                            new Object [][] {

                                                            },
                                                            new String [] {
                                                                "Index", "Scan Title", "Peptide Mass", "Charge", "Reference Line"
                                                            }
                                                        ));
                                                        jtMGF.addMouseListener(new java.awt.event.MouseAdapter() {
                                                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                                                jtMGFMouseClicked(evt);
                                                            }
                                                        });
                                                        jspMGFSubDetail.setViewportView(jtMGF);

                                                        jspMGFDetail.setRightComponent(jspMGFSubDetail);

                                                        jspMGF.setRightComponent(jspMGFDetail);

                                                        javax.swing.GroupLayout jpMGFLayout = new javax.swing.GroupLayout(jpMGF);
                                                        jpMGF.setLayout(jpMGFLayout);
                                                        jpMGFLayout.setHorizontalGroup(
                                                            jpMGFLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jspMGF, javax.swing.GroupLayout.DEFAULT_SIZE, 567, Short.MAX_VALUE)
                                                        );
                                                        jpMGFLayout.setVerticalGroup(
                                                            jpMGFLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jspMGF, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE)
                                                        );

                                                        jtpProperties.addTab("MGF View", jpMGF);

                                                        jspMzId.setDividerLocation(110);
                                                        jspMzId.setDividerSize(1);
                                                        jspMzId.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

                                                        jspMzIdDetail.setDividerLocation(300);
                                                        jspMzIdDetail.setDividerSize(1);
                                                        jspMzIdDetail.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

                                                        jlSearchMzId.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
                                                        jlSearchMzId.setText("Search:");

                                                        jlProteinMzId.setText("Protein:");

                                                        jtProteinMzId.setToolTipText("Enter protein");
                                                        jtProteinMzId.addKeyListener(new java.awt.event.KeyAdapter() {
                                                            public void keyPressed(java.awt.event.KeyEvent evt) {
                                                                jtProteinMzIdKeyPressed(evt);
                                                            }
                                                        });

                                                        jlPeptideMzId.setText("Peptide:");

                                                        jtPeptideMzId.setToolTipText("Enter peptide sequence");
                                                        jtPeptideMzId.addKeyListener(new java.awt.event.KeyAdapter() {
                                                            public void keyPressed(java.awt.event.KeyEvent evt) {
                                                                jtPeptideMzIdKeyPressed(evt);
                                                            }
                                                        });

                                                        jtbMzIdentMLOptions.setBorder(javax.swing.BorderFactory.createEtchedBorder());
                                                        jtbMzIdentMLOptions.setFloatable(false);
                                                        jtbMzIdentMLOptions.setMaximumSize(new java.awt.Dimension(30, 23));
                                                        jtbMzIdentMLOptions.setMinimumSize(new java.awt.Dimension(30, 23));
                                                        jtbMzIdentMLOptions.setPreferredSize(new java.awt.Dimension(100, 23));

                                                        jbExportMzIdentMLExcel.setMaximumSize(new java.awt.Dimension(28, 24));
                                                        jbExportMzIdentMLExcel.setMinimumSize(new java.awt.Dimension(28, 24));
                                                        jbExportMzIdentMLExcel.setPreferredSize(new java.awt.Dimension(28, 24));
                                                        jbExportMzIdentMLExcel.addMouseListener(new java.awt.event.MouseAdapter() {
                                                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                                                jbExportMzIdentMLExcelMouseClicked(evt);
                                                            }
                                                        });
                                                        jtbMzIdentMLOptions.add(jbExportMzIdentMLExcel);

                                                        jlExportMzIDXLS.setText("Export to:");

                                                        jtMzId.setModel(new javax.swing.table.DefaultTableModel(
                                                            new Object [][] {

                                                            },
                                                            new Object [] {
                                                                "Index", "Protein", "Peptide", "Rank", "Score", "Spectrum ID"
                                                            }
                                                        ));
                                                        jspMzIdProtGroup.setViewportView(jtMzId);

                                                        jlPeptideSpectrumMatches.setText("Peptide-Spectrum matches with rank:");

                                                        jcbPSM.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<=1", "<=2", "<=3", "All" }));
                                                        jcbPSM.addItemListener(new java.awt.event.ItemListener() {
                                                            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                                                                jcbPSMItemStateChanged(evt);
                                                            }
                                                        });
                                                        jcbPSM.addActionListener(new java.awt.event.ActionListener() {
                                                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                                jcbPSMActionPerformed(evt);
                                                            }
                                                        });

                                                        javax.swing.GroupLayout jpMzIdMenuLayout = new javax.swing.GroupLayout(jpMzIdMenu);
                                                        jpMzIdMenu.setLayout(jpMzIdMenuLayout);
                                                        jpMzIdMenuLayout.setHorizontalGroup(
                                                            jpMzIdMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jspMzIdProtGroup, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE)
                                                            .addGroup(jpMzIdMenuLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jpMzIdMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                    .addGroup(jpMzIdMenuLayout.createSequentialGroup()
                                                                        .addGroup(jpMzIdMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                            .addComponent(jlSearchMzId)
                                                                            .addComponent(jlExportMzIDXLS))
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                        .addGroup(jpMzIdMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                            .addComponent(jtbMzIdentMLOptions, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                            .addGroup(jpMzIdMenuLayout.createSequentialGroup()
                                                                                .addComponent(jlProteinMzId)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                .addComponent(jtProteinMzId, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                .addComponent(jlPeptideMzId)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(jtPeptideMzId, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                                    .addGroup(jpMzIdMenuLayout.createSequentialGroup()
                                                                        .addComponent(jlPeptideSpectrumMatches)
                                                                        .addGap(18, 18, 18)
                                                                        .addComponent(jcbPSM, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addContainerGap())
                                                        );
                                                        jpMzIdMenuLayout.setVerticalGroup(
                                                            jpMzIdMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jpMzIdMenuLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jpMzIdMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                    .addComponent(jlSearchMzId)
                                                                    .addComponent(jlProteinMzId)
                                                                    .addComponent(jtProteinMzId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addComponent(jlPeptideMzId)
                                                                    .addComponent(jtPeptideMzId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGroup(jpMzIdMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                    .addGroup(jpMzIdMenuLayout.createSequentialGroup()
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(jtbMzIdentMLOptions, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                    .addGroup(jpMzIdMenuLayout.createSequentialGroup()
                                                                        .addGap(16, 16, 16)
                                                                        .addComponent(jlExportMzIDXLS)))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addGroup(jpMzIdMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                    .addComponent(jcbPSM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addComponent(jlPeptideSpectrumMatches))
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jspMzIdProtGroup, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                                                                .addGap(23, 23, 23))
                                                        );

                                                        jspMzIdDetail.setTopComponent(jpMzIdMenu);

                                                        jtMzIDProtGroup.setModel(new javax.swing.table.DefaultTableModel(
                                                            new Object [][] {

                                                            },
                                                            new Object [] {
                                                                "ID", "Name", "Protein Accesions", "Representative Protein", "Scores", "P-values", "Number of peptides", "Is Decoy", "passThreshold"
                                                            }
                                                        ));
                                                        jspMzIDProtGroup.setViewportView(jtMzIDProtGroup);

                                                        jspMzIdDetail.setRightComponent(jspMzIDProtGroup);

                                                        jspMzId.setRightComponent(jspMzIdDetail);

                                                        jlFileNameMzID.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
                                                        jlFileNameMzID.setText("File:");

                                                        jepMzIDView.setContentType("text/html");
                                                        jepMzIDView.setPreferredSize(new java.awt.Dimension(144, 84));
                                                        jspMzIDView.setViewportView(jepMzIDView);

                                                        javax.swing.GroupLayout jpMzIdentMLViewHeaderLayout = new javax.swing.GroupLayout(jpMzIdentMLViewHeader);
                                                        jpMzIdentMLViewHeader.setLayout(jpMzIdentMLViewHeaderLayout);
                                                        jpMzIdentMLViewHeaderLayout.setHorizontalGroup(
                                                            jpMzIdentMLViewHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jpMzIdentMLViewHeaderLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jpMzIdentMLViewHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                    .addGroup(jpMzIdentMLViewHeaderLayout.createSequentialGroup()
                                                                        .addComponent(jlFileNameMzID)
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(jlFileNameMzIDText, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                    .addComponent(jspMzIDView, javax.swing.GroupLayout.PREFERRED_SIZE, 535, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addContainerGap(20, Short.MAX_VALUE))
                                                        );
                                                        jpMzIdentMLViewHeaderLayout.setVerticalGroup(
                                                            jpMzIdentMLViewHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jpMzIdentMLViewHeaderLayout.createSequentialGroup()
                                                                .addGap(20, 20, 20)
                                                                .addGroup(jpMzIdentMLViewHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                    .addComponent(jlFileNameMzID)
                                                                    .addComponent(jlFileNameMzIDText, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jspMzIDView, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap(17, Short.MAX_VALUE))
                                                        );

                                                        jspMzId.setLeftComponent(jpMzIdentMLViewHeader);

                                                        javax.swing.GroupLayout jpMzIdLayout = new javax.swing.GroupLayout(jpMzId);
                                                        jpMzId.setLayout(jpMzIdLayout);
                                                        jpMzIdLayout.setHorizontalGroup(
                                                            jpMzIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jspMzId)
                                                        );
                                                        jpMzIdLayout.setVerticalGroup(
                                                            jpMzIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jspMzId, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE)
                                                        );

                                                        jtpProperties.addTab("mzIdentML View", jpMzId);

                                                        jspMascotXML.setDividerLocation(110);
                                                        jspMascotXML.setDividerSize(1);
                                                        jspMascotXML.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

                                                        jspMascotXMLSubDetail.setDividerLocation(80);
                                                        jspMascotXMLSubDetail.setDividerSize(1);
                                                        jspMascotXMLSubDetail.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

                                                        jtMascotXMLView.setModel(new javax.swing.table.DefaultTableModel(
                                                            new Object [][] {

                                                            },
                                                            new String [] {
                                                                "Index", "Protein", "Peptide", "Composition", "Exp Mz", "Exp Mr", "Charge", "Score", "Scan", "Scan ID", "RT (sec)"
                                                            }
                                                        ));
                                                        jspMascotXMLDetail.setViewportView(jtMascotXMLView);

                                                        jspMascotXMLSubDetail.setRightComponent(jspMascotXMLDetail);

                                                        jlSearchMascotXML.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
                                                        jlSearchMascotXML.setText("Search:");

                                                        jlProtein.setText("Protein:");

                                                        jtProtein.addKeyListener(new java.awt.event.KeyAdapter() {
                                                            public void keyPressed(java.awt.event.KeyEvent evt) {
                                                                jtProteinKeyPressed(evt);
                                                            }
                                                        });

                                                        jlPeptide.setText("Peptide:");

                                                        jtPeptide.addKeyListener(new java.awt.event.KeyAdapter() {
                                                            public void keyPressed(java.awt.event.KeyEvent evt) {
                                                                jtPeptideKeyPressed(evt);
                                                            }
                                                        });

                                                        jlExportMzIDXLS1.setText("Export to:");

                                                        jtbMzIdentMLOptions1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
                                                        jtbMzIdentMLOptions1.setFloatable(false);
                                                        jtbMzIdentMLOptions1.setMaximumSize(new java.awt.Dimension(30, 23));
                                                        jtbMzIdentMLOptions1.setMinimumSize(new java.awt.Dimension(30, 23));
                                                        jtbMzIdentMLOptions1.setPreferredSize(new java.awt.Dimension(100, 23));

                                                        jbExportMascotXMLExcel.setMaximumSize(new java.awt.Dimension(28, 24));
                                                        jbExportMascotXMLExcel.setMinimumSize(new java.awt.Dimension(28, 24));
                                                        jbExportMascotXMLExcel.setPreferredSize(new java.awt.Dimension(28, 24));
                                                        jbExportMascotXMLExcel.addMouseListener(new java.awt.event.MouseAdapter() {
                                                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                                                jbExportMascotXMLExcelMouseClicked(evt);
                                                            }
                                                        });
                                                        jtbMzIdentMLOptions1.add(jbExportMascotXMLExcel);

                                                        javax.swing.GroupLayout jpMascotXMLMenuLayout = new javax.swing.GroupLayout(jpMascotXMLMenu);
                                                        jpMascotXMLMenu.setLayout(jpMascotXMLMenuLayout);
                                                        jpMascotXMLMenuLayout.setHorizontalGroup(
                                                            jpMascotXMLMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jpMascotXMLMenuLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jpMascotXMLMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                    .addGroup(jpMascotXMLMenuLayout.createSequentialGroup()
                                                                        .addComponent(jlSearchMascotXML)
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                        .addComponent(jlProtein)
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                        .addComponent(jtProtein, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addGap(14, 14, 14)
                                                                        .addComponent(jlPeptide)
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                        .addComponent(jtPeptide, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                    .addGroup(jpMascotXMLMenuLayout.createSequentialGroup()
                                                                        .addComponent(jlExportMzIDXLS1)
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                        .addComponent(jtbMzIdentMLOptions1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                                                .addContainerGap(112, Short.MAX_VALUE))
                                                        );
                                                        jpMascotXMLMenuLayout.setVerticalGroup(
                                                            jpMascotXMLMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jpMascotXMLMenuLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jpMascotXMLMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                    .addComponent(jtPeptide, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addComponent(jlSearchMascotXML)
                                                                    .addComponent(jlProtein)
                                                                    .addComponent(jtProtein, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addComponent(jlPeptide))
                                                                .addGroup(jpMascotXMLMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                    .addGroup(jpMascotXMLMenuLayout.createSequentialGroup()
                                                                        .addGap(16, 16, 16)
                                                                        .addComponent(jlExportMzIDXLS1))
                                                                    .addGroup(jpMascotXMLMenuLayout.createSequentialGroup()
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(jtbMzIdentMLOptions1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                        );

                                                        jspMascotXMLSubDetail.setLeftComponent(jpMascotXMLMenu);

                                                        jspMascotXML.setRightComponent(jspMascotXMLSubDetail);

                                                        jlFileNameMascotXML.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
                                                        jlFileNameMascotXML.setText("File:");

                                                        jepMascotXMLView.setContentType("text/html");
                                                        jepMascotXMLView.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
                                                        jepMascotXMLView.setPreferredSize(new java.awt.Dimension(144, 84));
                                                        jspMascotXMLView.setViewportView(jepMascotXMLView);

                                                        javax.swing.GroupLayout jpMascotXMLViewHeaderLayout = new javax.swing.GroupLayout(jpMascotXMLViewHeader);
                                                        jpMascotXMLViewHeader.setLayout(jpMascotXMLViewHeaderLayout);
                                                        jpMascotXMLViewHeaderLayout.setHorizontalGroup(
                                                            jpMascotXMLViewHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jpMascotXMLViewHeaderLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jpMascotXMLViewHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                    .addGroup(jpMascotXMLViewHeaderLayout.createSequentialGroup()
                                                                        .addComponent(jlFileNameMascotXML)
                                                                        .addGap(18, 18, 18)
                                                                        .addComponent(jlFileNameMascotXMLText, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                    .addComponent(jspMascotXMLView, javax.swing.GroupLayout.PREFERRED_SIZE, 535, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addContainerGap(20, Short.MAX_VALUE))
                                                        );
                                                        jpMascotXMLViewHeaderLayout.setVerticalGroup(
                                                            jpMascotXMLViewHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jpMascotXMLViewHeaderLayout.createSequentialGroup()
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jpMascotXMLViewHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                    .addComponent(jlFileNameMascotXMLText, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addComponent(jlFileNameMascotXML))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jspMascotXMLView, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap(19, Short.MAX_VALUE))
                                                        );

                                                        jspMascotXML.setLeftComponent(jpMascotXMLViewHeader);

                                                        javax.swing.GroupLayout jpMascotXMLLayout = new javax.swing.GroupLayout(jpMascotXML);
                                                        jpMascotXML.setLayout(jpMascotXMLLayout);
                                                        jpMascotXMLLayout.setHorizontalGroup(
                                                            jpMascotXMLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jspMascotXML)
                                                        );
                                                        jpMascotXMLLayout.setVerticalGroup(
                                                            jpMascotXMLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jspMascotXML, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE)
                                                        );

                                                        jtpProperties.addTab("Mascot XML View", jpMascotXML);

                                                        jspMzQuantML.setDividerLocation(110);
                                                        jspMzQuantML.setDividerSize(1);
                                                        jspMzQuantML.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

                                                        jspPeptideQuant.setDividerLocation(40);
                                                        jspPeptideQuant.setDividerSize(1);
                                                        jspPeptideQuant.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

                                                        jlSearchMzQPep.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
                                                        jlSearchMzQPep.setText("Search:");

                                                        jlPeptideMZQ.setText("Peptide:");

                                                        jtPeptideMZQ.setToolTipText("Enter the scan number");
                                                        jtPeptideMZQ.addKeyListener(new java.awt.event.KeyAdapter() {
                                                            public void keyPressed(java.awt.event.KeyEvent evt) {
                                                                jtPeptideMZQKeyPressed(evt);
                                                            }
                                                        });

                                                        jtbPepMZQ.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
                                                        jtbPepMZQ.setFloatable(false);
                                                        jtbPepMZQ.setMaximumSize(new java.awt.Dimension(30, 23));
                                                        jtbPepMZQ.setMinimumSize(new java.awt.Dimension(30, 23));
                                                        jtbPepMZQ.setPreferredSize(new java.awt.Dimension(100, 23));

                                                        jbExportPepMZQExcel.setIconTextGap(1);
                                                        jbExportPepMZQExcel.setMaximumSize(new java.awt.Dimension(26, 20));
                                                        jbExportPepMZQExcel.setMinimumSize(new java.awt.Dimension(26, 20));
                                                        jbExportPepMZQExcel.setPreferredSize(new java.awt.Dimension(26, 20));
                                                        jbExportPepMZQExcel.addMouseListener(new java.awt.event.MouseAdapter() {
                                                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                                                jbExportPepMZQExcelMouseClicked(evt);
                                                            }
                                                        });
                                                        jtbPepMZQ.add(jbExportPepMZQExcel);

                                                        javax.swing.GroupLayout jpPeptideQuantHeaderLayout = new javax.swing.GroupLayout(jpPeptideQuantHeader);
                                                        jpPeptideQuantHeader.setLayout(jpPeptideQuantHeaderLayout);
                                                        jpPeptideQuantHeaderLayout.setHorizontalGroup(
                                                            jpPeptideQuantHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jpPeptideQuantHeaderLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jlSearchMzQPep)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jlPeptideMZQ)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jtPeptideMZQ, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jtbPepMZQ, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                                                                .addContainerGap())
                                                        );
                                                        jpPeptideQuantHeaderLayout.setVerticalGroup(
                                                            jpPeptideQuantHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jpPeptideQuantHeaderLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jpPeptideQuantHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                    .addComponent(jtbPepMZQ, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                    .addGroup(jpPeptideQuantHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jlSearchMzQPep)
                                                                        .addComponent(jlPeptideMZQ)
                                                                        .addComponent(jtPeptideMZQ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addContainerGap())
                                                        );

                                                        jspPeptideQuant.setTopComponent(jpPeptideQuantHeader);

                                                        jtPeptideQuant.setModel(new javax.swing.table.DefaultTableModel(
                                                            new Object [][] {

                                                            },
                                                            new String [] {
                                                                "Peptide", "Label 1", "Label 2", "Label 3", "Label 4"
                                                            }
                                                        ));
                                                        jspPeptideQuantDetail.setViewportView(jtPeptideQuant);

                                                        jspPeptideQuant.setRightComponent(jspPeptideQuantDetail);

                                                        javax.swing.GroupLayout jpPeptideQuantLayout = new javax.swing.GroupLayout(jpPeptideQuant);
                                                        jpPeptideQuant.setLayout(jpPeptideQuantLayout);
                                                        jpPeptideQuantLayout.setHorizontalGroup(
                                                            jpPeptideQuantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jspPeptideQuant)
                                                        );
                                                        jpPeptideQuantLayout.setVerticalGroup(
                                                            jpPeptideQuantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jspPeptideQuant, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
                                                        );

                                                        jtpMzQuantMLDetail.addTab("Peptide Quantitation", jpPeptideQuant);

                                                        jspProteinQuant.setDividerLocation(40);
                                                        jspProteinQuant.setDividerSize(1);
                                                        jspProteinQuant.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

                                                        jlSearchMzQProt.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
                                                        jlSearchMzQProt.setText("Search:");

                                                        jlProteinMZQ.setText("Protein:");

                                                        jtProteinMZQ.setToolTipText("Enter the scan number");
                                                        jtProteinMZQ.addKeyListener(new java.awt.event.KeyAdapter() {
                                                            public void keyPressed(java.awt.event.KeyEvent evt) {
                                                                jtProteinMZQKeyPressed(evt);
                                                            }
                                                        });

                                                        jtbProtMZQ.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
                                                        jtbProtMZQ.setFloatable(false);
                                                        jtbProtMZQ.setMaximumSize(new java.awt.Dimension(30, 23));
                                                        jtbProtMZQ.setMinimumSize(new java.awt.Dimension(30, 23));
                                                        jtbProtMZQ.setPreferredSize(new java.awt.Dimension(100, 23));

                                                        jbExportProtMZQExcel.setIconTextGap(1);
                                                        jbExportProtMZQExcel.setMaximumSize(new java.awt.Dimension(26, 20));
                                                        jbExportProtMZQExcel.setMinimumSize(new java.awt.Dimension(26, 20));
                                                        jbExportProtMZQExcel.setPreferredSize(new java.awt.Dimension(26, 20));
                                                        jbExportProtMZQExcel.addMouseListener(new java.awt.event.MouseAdapter() {
                                                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                                                jbExportProtMZQExcelMouseClicked(evt);
                                                            }
                                                        });
                                                        jtbProtMZQ.add(jbExportProtMZQExcel);

                                                        javax.swing.GroupLayout jpProteinQuantHeaderLayout = new javax.swing.GroupLayout(jpProteinQuantHeader);
                                                        jpProteinQuantHeader.setLayout(jpProteinQuantHeaderLayout);
                                                        jpProteinQuantHeaderLayout.setHorizontalGroup(
                                                            jpProteinQuantHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jpProteinQuantHeaderLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jlSearchMzQProt)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jlProteinMZQ)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jtProteinMZQ, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jtbProtMZQ, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                                                                .addContainerGap())
                                                        );
                                                        jpProteinQuantHeaderLayout.setVerticalGroup(
                                                            jpProteinQuantHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jpProteinQuantHeaderLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jpProteinQuantHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                    .addComponent(jtbProtMZQ, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                    .addGroup(jpProteinQuantHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jlSearchMzQProt)
                                                                        .addComponent(jlProteinMZQ)
                                                                        .addComponent(jtProteinMZQ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addContainerGap())
                                                        );

                                                        jspProteinQuant.setTopComponent(jpProteinQuantHeader);

                                                        jtProteinQuant.setModel(new javax.swing.table.DefaultTableModel(
                                                            new Object [][] {

                                                            },
                                                            new String [] {
                                                                "Protein", "Label 1", "Label 2", "Label 3", "Label 4"
                                                            }
                                                        ));
                                                        jspProteinQuantDetail.setViewportView(jtProteinQuant);

                                                        jspProteinQuant.setRightComponent(jspProteinQuantDetail);

                                                        javax.swing.GroupLayout jpProteinQuantLayout = new javax.swing.GroupLayout(jpProteinQuant);
                                                        jpProteinQuant.setLayout(jpProteinQuantLayout);
                                                        jpProteinQuantLayout.setHorizontalGroup(
                                                            jpProteinQuantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jspProteinQuant)
                                                        );
                                                        jpProteinQuantLayout.setVerticalGroup(
                                                            jpProteinQuantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jspProteinQuant, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
                                                        );

                                                        jtpMzQuantMLDetail.addTab("Protein Quantitation", jpProteinQuant);

                                                        jspFeatureQuant.setDividerLocation(40);
                                                        jspFeatureQuant.setDividerSize(1);
                                                        jspFeatureQuant.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

                                                        jlSearchMzQFeat.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
                                                        jlSearchMzQFeat.setText("Search:");

                                                        jlFeatureMZQ.setText("Feature:");

                                                        jtFeatureMZQ.setToolTipText("Enter the scan number");
                                                        jtFeatureMZQ.addKeyListener(new java.awt.event.KeyAdapter() {
                                                            public void keyPressed(java.awt.event.KeyEvent evt) {
                                                                jtFeatureMZQKeyPressed(evt);
                                                            }
                                                        });

                                                        jtbFeatMZQ.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
                                                        jtbFeatMZQ.setFloatable(false);
                                                        jtbFeatMZQ.setMaximumSize(new java.awt.Dimension(30, 23));
                                                        jtbFeatMZQ.setMinimumSize(new java.awt.Dimension(30, 23));
                                                        jtbFeatMZQ.setPreferredSize(new java.awt.Dimension(100, 23));

                                                        jbExportFeatMZQExcel.setIconTextGap(1);
                                                        jbExportFeatMZQExcel.setMaximumSize(new java.awt.Dimension(26, 20));
                                                        jbExportFeatMZQExcel.setMinimumSize(new java.awt.Dimension(26, 20));
                                                        jbExportFeatMZQExcel.setPreferredSize(new java.awt.Dimension(26, 20));
                                                        jbExportFeatMZQExcel.addMouseListener(new java.awt.event.MouseAdapter() {
                                                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                                                jbExportFeatMZQExcelMouseClicked(evt);
                                                            }
                                                        });
                                                        jtbFeatMZQ.add(jbExportFeatMZQExcel);

                                                        javax.swing.GroupLayout jpFeatureQuantHeaderLayout = new javax.swing.GroupLayout(jpFeatureQuantHeader);
                                                        jpFeatureQuantHeader.setLayout(jpFeatureQuantHeaderLayout);
                                                        jpFeatureQuantHeaderLayout.setHorizontalGroup(
                                                            jpFeatureQuantHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jpFeatureQuantHeaderLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jlSearchMzQFeat)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jlFeatureMZQ)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jtFeatureMZQ, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jtbFeatMZQ, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                                                                .addContainerGap())
                                                        );
                                                        jpFeatureQuantHeaderLayout.setVerticalGroup(
                                                            jpFeatureQuantHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jpFeatureQuantHeaderLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jpFeatureQuantHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                    .addComponent(jtbFeatMZQ, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                    .addGroup(jpFeatureQuantHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jlSearchMzQFeat)
                                                                        .addComponent(jlFeatureMZQ)
                                                                        .addComponent(jtFeatureMZQ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addContainerGap())
                                                        );

                                                        jspFeatureQuant.setTopComponent(jpFeatureQuantHeader);

                                                        jtFeatureQuant.setModel(new javax.swing.table.DefaultTableModel(
                                                            new Object [][] {

                                                            },
                                                            new String [] {
                                                                "Feature", "Label 1", "Label 2", "Label 3", "Label 4"
                                                            }
                                                        ));
                                                        jspFeatureQuantDetail.setViewportView(jtFeatureQuant);

                                                        jspFeatureQuant.setRightComponent(jspFeatureQuantDetail);

                                                        javax.swing.GroupLayout FeatureQuantLayout = new javax.swing.GroupLayout(FeatureQuant);
                                                        FeatureQuant.setLayout(FeatureQuantLayout);
                                                        FeatureQuantLayout.setHorizontalGroup(
                                                            FeatureQuantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jspFeatureQuant, javax.swing.GroupLayout.Alignment.TRAILING)
                                                        );
                                                        FeatureQuantLayout.setVerticalGroup(
                                                            FeatureQuantLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jspFeatureQuant, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
                                                        );

                                                        jtpMzQuantMLDetail.addTab("Feature Quantitation", FeatureQuant);

                                                        jspMzQuantML.setRightComponent(jtpMzQuantMLDetail);

                                                        jlFileNameMzQ.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
                                                        jlFileNameMzQ.setText("File:");

                                                        jepMZQView.setContentType("text/html");
                                                        jepMZQView.setPreferredSize(new java.awt.Dimension(144, 84));
                                                        jScrollPane12.setViewportView(jepMZQView);

                                                        javax.swing.GroupLayout jpMzQuantMLHeaderLayout = new javax.swing.GroupLayout(jpMzQuantMLHeader);
                                                        jpMzQuantMLHeader.setLayout(jpMzQuantMLHeaderLayout);
                                                        jpMzQuantMLHeaderLayout.setHorizontalGroup(
                                                            jpMzQuantMLHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jpMzQuantMLHeaderLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jpMzQuantMLHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                    .addGroup(jpMzQuantMLHeaderLayout.createSequentialGroup()
                                                                        .addComponent(jlFileNameMzQ)
                                                                        .addGap(18, 18, 18)
                                                                        .addComponent(jlFileNameMzQText, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                    .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 535, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addContainerGap(20, Short.MAX_VALUE))
                                                        );
                                                        jpMzQuantMLHeaderLayout.setVerticalGroup(
                                                            jpMzQuantMLHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jpMzQuantMLHeaderLayout.createSequentialGroup()
                                                                .addGap(19, 19, 19)
                                                                .addGroup(jpMzQuantMLHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                    .addComponent(jlFileNameMzQ, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                    .addComponent(jlFileNameMzQText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap(13, Short.MAX_VALUE))
                                                        );

                                                        jspMzQuantML.setLeftComponent(jpMzQuantMLHeader);

                                                        javax.swing.GroupLayout jpMzQuantMLLayout = new javax.swing.GroupLayout(jpMzQuantML);
                                                        jpMzQuantML.setLayout(jpMzQuantMLLayout);
                                                        jpMzQuantMLLayout.setHorizontalGroup(
                                                            jpMzQuantMLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jspMzQuantML)
                                                        );
                                                        jpMzQuantMLLayout.setVerticalGroup(
                                                            jpMzQuantMLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jspMzQuantML)
                                                        );

                                                        jtpProperties.addTab("mzQuantML View", jpMzQuantML);

                                                        javax.swing.GroupLayout jpPropetiesTabLayout = new javax.swing.GroupLayout(jpPropetiesTab);
                                                        jpPropetiesTab.setLayout(jpPropetiesTabLayout);
                                                        jpPropetiesTabLayout.setHorizontalGroup(
                                                            jpPropetiesTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jtpProperties)
                                                        );
                                                        jpPropetiesTabLayout.setVerticalGroup(
                                                            jpPropetiesTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jtpProperties)
                                                        );

                                                        jspProperties.setRightComponent(jpPropetiesTab);

                                                        javax.swing.GroupLayout jpPropertiesBoxLayout = new javax.swing.GroupLayout(jpPropertiesBox);
                                                        jpPropertiesBox.setLayout(jpPropertiesBoxLayout);
                                                        jpPropertiesBoxLayout.setHorizontalGroup(
                                                            jpPropertiesBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jspProperties)
                                                        );
                                                        jpPropertiesBoxLayout.setVerticalGroup(
                                                            jpPropertiesBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jspProperties, javax.swing.GroupLayout.DEFAULT_SIZE, 635, Short.MAX_VALUE)
                                                        );

                                                        jspViewerAndProperties.setRightComponent(jpPropertiesBox);

                                                        javax.swing.GroupLayout jpViewerAndPropertiesLayout = new javax.swing.GroupLayout(jpViewerAndProperties);
                                                        jpViewerAndProperties.setLayout(jpViewerAndPropertiesLayout);
                                                        jpViewerAndPropertiesLayout.setHorizontalGroup(
                                                            jpViewerAndPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jspViewerAndProperties, javax.swing.GroupLayout.DEFAULT_SIZE, 958, Short.MAX_VALUE)
                                                        );
                                                        jpViewerAndPropertiesLayout.setVerticalGroup(
                                                            jpViewerAndPropertiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jspViewerAndProperties)
                                                        );

                                                        jspMainPanelView.setRightComponent(jpViewerAndProperties);

                                                        javax.swing.GroupLayout jpMainPanelViewLayout = new javax.swing.GroupLayout(jpMainPanelView);
                                                        jpMainPanelView.setLayout(jpMainPanelViewLayout);
                                                        jpMainPanelViewLayout.setHorizontalGroup(
                                                            jpMainPanelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jspMainPanelView, javax.swing.GroupLayout.DEFAULT_SIZE, 1242, Short.MAX_VALUE)
                                                        );
                                                        jpMainPanelViewLayout.setVerticalGroup(
                                                            jpMainPanelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jspMainPanelView)
                                                        );

                                                        jpProjectStatus.setBorder(javax.swing.BorderFactory.createEtchedBorder());
                                                        jpProjectStatus.setForeground(new java.awt.Color(153, 153, 153));
                                                        jpProjectStatus.setPreferredSize(new java.awt.Dimension(102, 30));

                                                        jlRawFiles.setText("1) Raw Files:");

                                                        jlIdentFiles.setText("2) Identification Files:");

                                                        jlTechnique.setText("3) Technique:");

                                                        jlQuantFiles.setBackground(new java.awt.Color(255, 255, 255));
                                                        jlQuantFiles.setText("4) Quant Files:");

                                                        jlRawFilesStatus.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
                                                        jlRawFilesStatus.setForeground(new java.awt.Color(51, 102, 0));

                                                        jlIdentFilesStatus.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
                                                        jlIdentFilesStatus.setForeground(new java.awt.Color(51, 102, 0));

                                                        jlQuantFilesStatus.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
                                                        jlQuantFilesStatus.setForeground(new java.awt.Color(51, 102, 0));

                                                        jcbTechnique.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select technique", "iTRAQ", "TMT", "emPAI" }));
                                                        jcbTechnique.setToolTipText("Select a technique for the analysis");
                                                        jcbTechnique.setBorder(javax.swing.BorderFactory.createEtchedBorder());

                                                        jcbOutputFormat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select format", "mzq" }));
                                                        jcbOutputFormat.setToolTipText("Select an output format");

                                                        javax.swing.GroupLayout jpProjectStatusLayout = new javax.swing.GroupLayout(jpProjectStatus);
                                                        jpProjectStatus.setLayout(jpProjectStatusLayout);
                                                        jpProjectStatusLayout.setHorizontalGroup(
                                                            jpProjectStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jpProjectStatusLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jlRawFiles)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jlRawFilesStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jlIdentFiles)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(jlIdentFilesStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(jlTechnique)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jcbTechnique, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jlQuantFiles)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(jcbOutputFormat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jlQuantFilesStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap(499, Short.MAX_VALUE))
                                                        );
                                                        jpProjectStatusLayout.setVerticalGroup(
                                                            jpProjectStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jpProjectStatusLayout.createSequentialGroup()
                                                                .addGroup(jpProjectStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                    .addGroup(jpProjectStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jlRawFiles)
                                                                        .addComponent(jlRawFilesStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(jlIdentFiles)
                                                                        .addComponent(jlIdentFilesStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(jlTechnique)
                                                                        .addComponent(jcbTechnique, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(jlQuantFiles)
                                                                        .addComponent(jcbOutputFormat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                    .addComponent(jlQuantFilesStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addContainerGap())
                                                        );

                                                        jmFile.setText("File");

                                                        jmNewProject.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
                                                        jmNewProject.setText("New Project");
                                                        jmNewProject.addActionListener(new java.awt.event.ActionListener() {
                                                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                                jmNewProjectActionPerformed(evt);
                                                            }
                                                        });
                                                        jmFile.add(jmNewProject);

                                                        jmImportFile.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
                                                        jmImportFile.setText("Import File");
                                                        jmImportFile.addActionListener(new java.awt.event.ActionListener() {
                                                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                                jmImportFileActionPerformed(evt);
                                                            }
                                                        });
                                                        jmFile.add(jmImportFile);
                                                        jmFile.add(jSeparator1);

                                                        jmOpenProject.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
                                                        jmOpenProject.setText("Open Project");
                                                        jmOpenProject.addActionListener(new java.awt.event.ActionListener() {
                                                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                                jmOpenProjectActionPerformed(evt);
                                                            }
                                                        });
                                                        jmFile.add(jmOpenProject);

                                                        jmOpenRecentProject.setText("Open Recent Project");
                                                        jmOpenRecentProject.setEnabled(false);
                                                        jmOpenRecentProject.addActionListener(new java.awt.event.ActionListener() {
                                                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                                jmOpenRecentProjectActionPerformed(evt);
                                                            }
                                                        });
                                                        jmFile.add(jmOpenRecentProject);

                                                        jmCloseProject.setText("Close Project");
                                                        jmCloseProject.setEnabled(false);
                                                        jmCloseProject.addActionListener(new java.awt.event.ActionListener() {
                                                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                                jmCloseProjectActionPerformed(evt);
                                                            }
                                                        });
                                                        jmFile.add(jmCloseProject);
                                                        jmFile.add(jSeparator3);

                                                        jmSaveProject.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
                                                        jmSaveProject.setText("Save Project");
                                                        jmSaveProject.setEnabled(false);
                                                        jmSaveProject.addActionListener(new java.awt.event.ActionListener() {
                                                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                                jmSaveProjectActionPerformed(evt);
                                                            }
                                                        });
                                                        jmFile.add(jmSaveProject);

                                                        jmSaveProjectAs.setText("Save Project As");
                                                        jmSaveProjectAs.setEnabled(false);
                                                        jmSaveProjectAs.addActionListener(new java.awt.event.ActionListener() {
                                                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                                jmSaveProjectAsActionPerformed(evt);
                                                            }
                                                        });
                                                        jmFile.add(jmSaveProjectAs);
                                                        jmFile.add(jSeparator2);

                                                        jmPrint.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
                                                        jmPrint.setText("Print");
                                                        jmPrint.setEnabled(false);
                                                        jmFile.add(jmPrint);
                                                        jmFile.add(jSeparator4);

                                                        jmExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
                                                        jmExit.setText("Exit");
                                                        jmExit.addActionListener(new java.awt.event.ActionListener() {
                                                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                                jmExitActionPerformed(evt);
                                                            }
                                                        });
                                                        jmFile.add(jmExit);

                                                        jmMain.add(jmFile);

                                                        jmEdit.setText("Edit");

                                                        jmCut.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
                                                        jmCut.setText("Cut");
                                                        jmCut.setEnabled(false);
                                                        jmEdit.add(jmCut);

                                                        jmCopy.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
                                                        jmCopy.setText("Copy");
                                                        jmCopy.setEnabled(false);
                                                        jmEdit.add(jmCopy);

                                                        jmPaste.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
                                                        jmPaste.setText("Paste");
                                                        jmPaste.setEnabled(false);
                                                        jmEdit.add(jmPaste);
                                                        jmEdit.add(jSeparator6);

                                                        jmMain.add(jmEdit);

                                                        jmView.setText("View");

                                                        jmShowProjectFiles.setText("Show/Hide Project Files");
                                                        jmShowProjectFiles.addActionListener(new java.awt.event.ActionListener() {
                                                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                                jmShowProjectFilesActionPerformed(evt);
                                                            }
                                                        });
                                                        jmView.add(jmShowProjectFiles);

                                                        jmShowViewer.setText("Show/Hide Viewer");
                                                        jmShowViewer.addActionListener(new java.awt.event.ActionListener() {
                                                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                                jmShowViewerActionPerformed(evt);
                                                            }
                                                        });
                                                        jmView.add(jmShowViewer);

                                                        jmShowProperties.setText("Show/Hide Properties");
                                                        jmShowProperties.setEnabled(false);
                                                        jmShowProperties.addActionListener(new java.awt.event.ActionListener() {
                                                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                                jmShowPropertiesActionPerformed(evt);
                                                            }
                                                        });
                                                        jmView.add(jmShowProperties);

                                                        jmMain.add(jmView);

                                                        jmProject.setText("Project");

                                                        jmEditIdent.setText("Set Identification Parameters");
                                                        jmEditIdent.addActionListener(new java.awt.event.ActionListener() {
                                                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                                jmEditIdentActionPerformed(evt);
                                                            }
                                                        });
                                                        jmProject.add(jmEditIdent);

                                                        jmEditQuant.setText("Set Quantitation Parameters");
                                                        jmEditQuant.addActionListener(new java.awt.event.ActionListener() {
                                                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                                jmEditQuantActionPerformed(evt);
                                                            }
                                                        });
                                                        jmProject.add(jmEditQuant);

                                                        jmMain.add(jmProject);

                                                        jmAnalyze.setText("Analyze");

                                                        jmRunIdentAnalysis.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F7, 0));
                                                        jmRunIdentAnalysis.setText("Run Identification Analysis");
                                                        jmRunIdentAnalysis.addActionListener(new java.awt.event.ActionListener() {
                                                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                                jmRunIdentAnalysisActionPerformed(evt);
                                                            }
                                                        });
                                                        jmAnalyze.add(jmRunIdentAnalysis);

                                                        jmRunQuantAnalysis.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
                                                        jmRunQuantAnalysis.setText("Run Quantition Analysis");
                                                        jmRunQuantAnalysis.addActionListener(new java.awt.event.ActionListener() {
                                                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                                jmRunQuantAnalysisActionPerformed(evt);
                                                            }
                                                        });
                                                        jmAnalyze.add(jmRunQuantAnalysis);

                                                        jmMain.add(jmAnalyze);

                                                        jmTools.setText("Tools");

                                                        jmConverters.setText("Converters");

                                                        jmMzML2MGF.setText("mzML to MGF");
                                                        jmMzML2MGF.addActionListener(new java.awt.event.ActionListener() {
                                                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                                jmMzML2MGFActionPerformed(evt);
                                                            }
                                                        });
                                                        jmConverters.add(jmMzML2MGF);

                                                        jmMaxQ2MZQ.setText("MaxQuant to mzQuantML");
                                                        jmMaxQ2MZQ.addActionListener(new java.awt.event.ActionListener() {
                                                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                                jmMaxQ2MZQActionPerformed(evt);
                                                            }
                                                        });
                                                        jmConverters.add(jmMaxQ2MZQ);

                                                        jmProgenesis2MZQ.setText("Progenesis to mzQuantML");
                                                        jmProgenesis2MZQ.addActionListener(new java.awt.event.ActionListener() {
                                                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                                jmProgenesis2MZQActionPerformed(evt);
                                                            }
                                                        });
                                                        jmConverters.add(jmProgenesis2MZQ);

                                                        jmTools.add(jmConverters);

                                                        jMzMLCompressed.setText("Compress MzML file");
                                                        jMzMLCompressed.addActionListener(new java.awt.event.ActionListener() {
                                                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                                jMzMLCompressedActionPerformed(evt);
                                                            }
                                                        });
                                                        jmTools.add(jMzMLCompressed);

                                                        jmCustomize.setText("Customize");
                                                        jmCustomize.setEnabled(false);
                                                        jmTools.add(jmCustomize);

                                                        jmOptions.setText("Options");
                                                        jmOptions.addActionListener(new java.awt.event.ActionListener() {
                                                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                                jmOptionsActionPerformed(evt);
                                                            }
                                                        });
                                                        jmTools.add(jmOptions);

                                                        jmMain.add(jmTools);

                                                        jmDatabases.setText("Databases");

                                                        jmSubmitPRIDE.setText("Submit to PRIDE");
                                                        jmSubmitPRIDE.addActionListener(new java.awt.event.ActionListener() {
                                                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                                jmSubmitPRIDEActionPerformed(evt);
                                                            }
                                                        });
                                                        jmDatabases.add(jmSubmitPRIDE);

                                                        jmMain.add(jmDatabases);

                                                        jmWindow.setText("Window");

                                                        jmSpectrum.setText("Spectrum");
                                                        jmSpectrum.addActionListener(new java.awt.event.ActionListener() {
                                                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                                jmSpectrumActionPerformed(evt);
                                                            }
                                                        });
                                                        jmWindow.add(jmSpectrum);

                                                        jmTIC.setText("TIC");
                                                        jmTIC.addActionListener(new java.awt.event.ActionListener() {
                                                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                                jmTICActionPerformed(evt);
                                                            }
                                                        });
                                                        jmWindow.add(jmTIC);

                                                        jm2DView.setText("2D View");
                                                        jm2DView.addActionListener(new java.awt.event.ActionListener() {
                                                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                                jm2DViewActionPerformed(evt);
                                                            }
                                                        });
                                                        jmWindow.add(jm2DView);

                                                        jm3DView.setText("3D View");
                                                        jm3DView.addActionListener(new java.awt.event.ActionListener() {
                                                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                                jm3DViewActionPerformed(evt);
                                                            }
                                                        });
                                                        jmWindow.add(jm3DView);
                                                        jmWindow.add(jSeparator8);

                                                        jmMzMLView.setText("mzML View");
                                                        jmMzMLView.addActionListener(new java.awt.event.ActionListener() {
                                                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                                jmMzMLViewActionPerformed(evt);
                                                            }
                                                        });
                                                        jmWindow.add(jmMzMLView);

                                                        jmMGFView.setText("MGF View");
                                                        jmMGFView.addActionListener(new java.awt.event.ActionListener() {
                                                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                                jmMGFViewActionPerformed(evt);
                                                            }
                                                        });
                                                        jmWindow.add(jmMGFView);

                                                        jmMzIdentMLView.setText("mzIdentML View");
                                                        jmMzIdentMLView.addActionListener(new java.awt.event.ActionListener() {
                                                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                                jmMzIdentMLViewActionPerformed(evt);
                                                            }
                                                        });
                                                        jmWindow.add(jmMzIdentMLView);

                                                        jmMascotXMLView.setText("Mascot XML View");
                                                        jmMascotXMLView.addActionListener(new java.awt.event.ActionListener() {
                                                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                                jmMascotXMLViewActionPerformed(evt);
                                                            }
                                                        });
                                                        jmWindow.add(jmMascotXMLView);

                                                        jmMzQuantMLView.setText("mzQuantML View");
                                                        jmMzQuantMLView.addActionListener(new java.awt.event.ActionListener() {
                                                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                                jmMzQuantMLViewActionPerformed(evt);
                                                            }
                                                        });
                                                        jmWindow.add(jmMzQuantMLView);
                                                        jmWindow.add(jSeparator9);

                                                        jmLog.setText("Log");
                                                        jmLog.addActionListener(new java.awt.event.ActionListener() {
                                                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                                jmLogActionPerformed(evt);
                                                            }
                                                        });
                                                        jmWindow.add(jmLog);

                                                        jmRawData.setText("Raw Data");
                                                        jmRawData.addActionListener(new java.awt.event.ActionListener() {
                                                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                                jmRawDataActionPerformed(evt);
                                                            }
                                                        });
                                                        jmWindow.add(jmRawData);

                                                        jmMain.add(jmWindow);

                                                        jmHelp.setText("Help");

                                                        jmHelpContent.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
                                                        jmHelpContent.setText("ProteoSuite Help");
                                                        jmHelpContent.setToolTipText("Find how to use ProteoSuite");
                                                        jmHelpContent.addActionListener(new java.awt.event.ActionListener() {
                                                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                                jmHelpContentActionPerformed(evt);
                                                            }
                                                        });
                                                        jmHelp.add(jmHelpContent);
                                                        jmHelp.add(jSeparator5);

                                                        jmContactUs.setText("Contact us");
                                                        jmContactUs.setToolTipText("Click here for contacting our group and collaborators");
                                                        jmContactUs.addActionListener(new java.awt.event.ActionListener() {
                                                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                                jmContactUsActionPerformed(evt);
                                                            }
                                                        });
                                                        jmHelp.add(jmContactUs);

                                                        jmCheckUpdates.setText("Check for updates");
                                                        jmCheckUpdates.addActionListener(new java.awt.event.ActionListener() {
                                                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                                jmCheckUpdatesActionPerformed(evt);
                                                            }
                                                        });
                                                        jmHelp.add(jmCheckUpdates);
                                                        jmHelp.add(jSeparator7);

                                                        jmAbout.setText("About ProteoSuite");
                                                        jmAbout.addActionListener(new java.awt.event.ActionListener() {
                                                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                                jmAboutActionPerformed(evt);
                                                            }
                                                        });
                                                        jmHelp.add(jmAbout);

                                                        jmMain.add(jmHelp);

                                                        setJMenuBar(jmMain);

                                                        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                                                        getContentPane().setLayout(layout);
                                                        layout.setHorizontalGroup(
                                                            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jpMainPanelView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                    .addComponent(jpProjectStatus, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1232, Short.MAX_VALUE)
                                                                    .addComponent(jpToolBar, javax.swing.GroupLayout.DEFAULT_SIZE, 1232, Short.MAX_VALUE))
                                                                .addContainerGap())
                                                        );
                                                        layout.setVerticalGroup(
                                                            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jpToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jpProjectStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(23, 23, 23)
                                                                .addComponent(jpMainPanelView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                        );

                                                        pack();
                                                    }// </editor-fold>//GEN-END:initComponents

    private void jmExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmExitActionPerformed
        //... Exiting from proteosuite ...//
        int iExit = JOptionPane.showConfirmDialog(this, "Do you want to exit?", "Exiting from ProteoSuite", JOptionPane.YES_NO_OPTION);
        if (iExit == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_jmExitActionPerformed

    private void jmNewProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmNewProjectActionPerformed
        //... Check if the project needs to be saved ...//
        if(bProjectModified){
            int iOption = JOptionPane.showConfirmDialog(this, "You have not saved your changes. Do you want to save them now?", "Warning", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (iOption == JOptionPane.OK_OPTION) {            
                jmSaveProjectActionPerformed(null);
                initProjectValues();
            }else if (iOption == JOptionPane.NO_OPTION){
                initProjectValues();
            }
        }else{
            initProjectValues();
        }
    }//GEN-LAST:event_jmNewProjectActionPerformed
   
    private void jmImportFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmImportFileActionPerformed
                
        //... Selecting file(s) ...//
        JFileChooser chooser = new JFileChooser(sPreviousLocation);
        chooser.setDialogTitle("Select the file(s) to analyze");

        //... Applying file extension filters ...//
        FileNameExtensionFilter filter1 = new FileNameExtensionFilter("Raw Files (*.mzML, *.mzML.gz, *.mgf)", "mzML", "gz", "mgf");
        FileNameExtensionFilter filter2 = new FileNameExtensionFilter("Identification Files (*.mzid, *.mzid.gz, *.xml)", "mzid", "gz", "xml");
        FileNameExtensionFilter filter3 = new FileNameExtensionFilter("mzQuantML Files (*.mzq, *.mzq.gz)", "mzq");

        //... Filters must be in descending order ...//
        chooser.addChoosableFileFilter(filter3);
        chooser.addChoosableFileFilter(filter2);        
        chooser.addChoosableFileFilter(filter1);

        //... Enable multiple file selection ...//
        chooser.setMultiSelectionEnabled(true);

        //... Setting default directory ...//
        if (sPreviousLocation == null || sPreviousLocation.contains("")){
            chooser.setCurrentDirectory(new java.io.File(sWorkspace)); //... If not found it goes to Home, exception not needed ...//   
        }
        else{
            chooser.setCurrentDirectory(new java.io.File(sPreviousLocation)); //... If not found it goes to Home, exception not needed ...//               
        }
        
        //... Retrieving selection from user ...//
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION){
            
            final File [] aFiles = chooser.getSelectedFiles();
	    if (aFiles != null && aFiles.length > 0){
                bProjectModified = true;                
                uptSaveProjectStatus();
                sPreviousLocation = aFiles[0].getParent();                
//---------------//
//  Read mzML    //
//---------------//
                //... Usually, the first element will contain the type of files, but we can also expect mixed entries ...//
                if ((aFiles[0].getName().toLowerCase().indexOf(".mzml") > 0)||(aFiles[0].getName().toLowerCase().indexOf(".mzml.gz") > 0)){                                                                                                  
                    //... Fill JTable ...//
                    final DefaultTableModel model = new DefaultTableModel();
                    jtRawFiles.setModel(model);
                    model.addColumn("Name");
                    model.addColumn("Path");
                    model.addColumn("Type");
                    model.addColumn("Version");
                    
                    final ProgressBarDialog progressBarDialog = new ProgressBarDialog(this, true, "ReadingMzML");
                    final Thread thread = new Thread(new Runnable(){
                        @Override
                        public void run(){
                            //... Progress Bar ...//
                            progressBarDialog.setTitle("Reading mzML files");
                            progressBarDialog.setVisible(true);
                        }
                    }, "ProgressBarDialog");
                    thread.start();
                    new Thread("ReadingMzML"){
                        @Override
                        public void run(){                            
                            String sOutput = "";
                            String sMessage = "";
                            jtaLog.setText(sOutput);
                            
                            //... Release unmarshallers ...//
                            if (aFiles.length>0){                     
                                sMessage = sysutils.getTime()+" - Reading mzML files (Total="+aFiles.length+")";
                                System.out.println(sMessage);
                                sOutput = sOutput + sMessage+"\n";
                                jtaLog.setText(sOutput);
                                aMzMLUnmarshaller.clear();
                            }
                            //... Reading selected files ...//
                            for (int iI = 0; iI < aFiles.length; iI++){                                                                
                                //... Validate file extension (mixed files) ...// 
                                if ((aFiles[iI].getName().toLowerCase().indexOf(".mzml") > 0)||(aFiles[iI].getName().toLowerCase().indexOf(".mzml.gz") > 0)){
                                    File xmlFile = new File(aFiles[iI].getPath());
                                    
                                    progressBarDialog.setTitle("Reading mzml files ("+(iI+1)+"/"+aFiles.length+") - " + xmlFile.getName());
                                    progressBarDialog.setVisible(true);
                                    
                                    //... Uncompress mzML.gz files ...//
                                    if (aFiles[iI].getName().toLowerCase().indexOf(".mzml.gz") > 0){
                                        try{
                                            sMessage = sysutils.getTime()+" - Uncompressing " + xmlFile.getName();
                                            progressBarDialog.setTitle("Uncompressing " + xmlFile.getName());
                                            progressBarDialog.setVisible(true);
                                            
                                            System.out.println(sMessage);
                                            sOutput = sOutput + sMessage+"\n";  
                                            jtaLog.setText(sOutput);

                                            File outFile = null;
                                            FileOutputStream fos = null;

                                            GZIPInputStream gin = new GZIPInputStream(new FileInputStream(xmlFile));
                                            outFile = new File(aFiles[0].getParent(), aFiles[0].getName().replaceAll("\\.gz$", ""));
                                            fos = new FileOutputStream(outFile);
                                            byte[] buf = new byte[100000];
                                            int len;
                                            while ((len = gin.read(buf)) > 0) {
                                                fos.write(buf, 0, len);
                                            }
                                            fos.close();      
                                            xmlFile = outFile;
                                            sMessage = sysutils.getTime()+" - Uncompressing ends ";
                                            System.out.println(sMessage);
                                            sOutput = sOutput + sMessage+"\n";                                            
                                        }
                                        catch(IOException ioe){
                                              System.out.println("Exception has been thrown" + ioe);
                                        }
                                    }
                                    //... Unmarshall data using jzmzML API ...//
                                    sMessage = sysutils.getTime()+" - Unmarshalling " + xmlFile.getName() + " starts ";
                                    System.out.println(sMessage);
                                    sOutput = sOutput + sMessage+"\n"; 
                                    jtaLog.setText(sOutput);
                                    unmarshalMzMLFile(model, xmlFile, "");
                                    sMessage = sysutils.getTime()+" - Unmarshalling ends ";
                                    System.out.println(sMessage);
                                    sOutput = sOutput + sMessage+"\n";
                                    jtaLog.setText(sOutput);
                                }
                            } //... For files ...//
                            
                            //... We then display the first mzML element, the corresponding chromatogram and the 2D plot ...//        
                            sMessage = sysutils.getTime()+" - Loading mzML view ";
                            System.out.println(sMessage);
                            sOutput = sOutput + sMessage+"\n";
                            jtaLog.setText(sOutput);
                            loadMzMLView(0);
                            sMessage = sysutils.getTime()+" - Displaying chromatogram ";
                            System.out.println(sMessage);
                            sOutput = sOutput + sMessage+"\n";
                            jtaLog.setText(sOutput);
                            showChromatogram(0, aFiles[0].getPath());
                            sMessage = sysutils.getTime()+" - Displaying 2D Plot";
                            System.out.println(sMessage);
                            sOutput = sOutput + sMessage+"\n";
                            jtaLog.setText(sOutput);
                            show2DPlot(0, aFiles[0].getPath());
            
                            progressBarDialog.setVisible(false);
                            progressBarDialog.dispose();
                            sMessage = sysutils.getTime()+" - Raw files imported successfully!";
                            System.out.println(sMessage);
                            sOutput = sOutput + sMessage+"\n";
                            jtaLog.setText(sOutput);
                            jbExportMzMLExcel.setEnabled(true);
                            renderIdentFiles();
                            uptStatusPipeline();
                        }
                    }.start();  
                } //... From reading mzML files ...//
//---------------//
//  Read MGF     //
//---------------//                
                if (aFiles[0].getName().toLowerCase().indexOf(".mgf") > 0) {                    
                    //... Fill JTable ...//
                    final DefaultTableModel model = new DefaultTableModel();
                    jtRawFiles.setModel(model);
                    model.addColumn("Name");
                    model.addColumn("Path");
                    model.addColumn("Type");
                    model.addColumn("Version");
    
                    final ProgressBarDialog progressBarDialog = new ProgressBarDialog(this, true, "ReadingMGF");
                    final Thread thread = new Thread(new Runnable(){
                        @Override
                        public void run(){
                            //... Progress Bar ...//
                            progressBarDialog.setTitle("Reading MGF files");
                            progressBarDialog.setVisible(true);
                        }
                    }, "ProgressBarDialog");
                    thread.start();
                    new Thread("ReadingMGF"){
                        @Override
                        public void run(){
                            String sMessage = "";
                            String sOutput = "";
                            jtaLog.setText(sOutput);
                            if (aFiles.length>0){                     
                                sMessage = sysutils.getTime()+" - Reading MGF files (Total="+aFiles.length+")";
                                System.out.println(sMessage);
                                sOutput = sOutput + sMessage+"\n";
                                jtaLog.setText(sOutput);
                            }
                            
                            //... Reading selected files ...//
                            for (int iI = 0; iI < aFiles.length; iI++){
                                progressBarDialog.setTitle("Reading MGF files ("+(iI+1)+"/"+aFiles.length+") - " + aFiles[iI].getName());
                                progressBarDialog.setVisible(true);                                
                                //... Validate file extension (mixed files) ...// 
                                if (aFiles[iI].getName().toLowerCase().indexOf(".mgf") > 0){
                                    model.insertRow(model.getRowCount(), new Object[]{aFiles[iI].getName(), 
                                        aFiles[iI].getPath().toString().replace("\\", "/"),
                                        "MGF",
                                        "N/A"});
                                }
                            } //... For files ...//
                            
                            //... Display data for the first element ...//
                            sMessage = sysutils.getTime()+" - Loading MGF view ";
                            System.out.println(sMessage);
                            sOutput = sOutput + sMessage+"\n";
                            jtaLog.setText(sOutput);
                            loadMGFView(0);
            
                            progressBarDialog.setVisible(false);
                            progressBarDialog.dispose();
                            sMessage = sysutils.getTime()+" - Raw files imported successfully!";
                            System.out.println(sMessage);
                            sOutput = sOutput + sMessage+"\n";
                            jtaLog.setText(sOutput);
                            jbExportMGFExcel.setEnabled(true);
                            renderIdentFiles();
                            uptStatusPipeline();
                        }
                    }.start();                    
                } //... From reading MGF files ...//         
//-------------------//
//  Read mzIdentML   //
//-------------------//
                if ((aFiles[0].getName().toLowerCase().indexOf(".mzid") > 0)||(aFiles[0].getName().toLowerCase().indexOf(".mzid.gz") > 0)) {   
                    //... Fill JTable ...//
                    final DefaultTableModel model = new DefaultTableModel();
                    jtIdentFiles.setModel(model);
                    model.addColumn("Name");
                    model.addColumn("Path");
                    model.addColumn("Type");
                    model.addColumn("Version");
                    model.addColumn("Raw File");

                    final ProgressBarDialog progressBarDialog = new ProgressBarDialog(this, true, "ReadingMzID");
                    final Thread thread = new Thread(new Runnable(){
                        @Override
                        public void run(){
                            //... Progress Bar ...//
                            progressBarDialog.setTitle("Reading mzIdentML files");
                            progressBarDialog.setVisible(true);
                        }
                    }, "ProgressBarDialog");
                    thread.start();
                    new Thread("ReadingMzID"){
                        @Override
                        public void run(){          
                            String sOutput = "";
                            String sMessage = "";
                            jtaLog.setText(sOutput);
                            
                            //... Release unmarshallers ...//
                            if (aFiles.length>0){           
                                sMessage = sysutils.getTime()+" - Reading mzML files (Total="+aFiles.length+")";
                                System.out.println(sMessage);
                                sOutput = sOutput + sMessage+"\n";
                                jtaLog.setText(sOutput);
                                aMzIDUnmarshaller.clear();
                            }                            
                            //... Reading selected files ...//
                            for (int iI = 0; iI < aFiles.length; iI++){
                                //... Validate file extension (mixed files) ...// 
                                if ((aFiles[iI].getName().toLowerCase().indexOf(".mzid") > 0)||(aFiles[iI].getName().toLowerCase().indexOf(".mzid.gz") > 0)){                                
                                    File xmlFile = new File(aFiles[iI].getPath());
                                    
                                    progressBarDialog.setTitle("Reading mzid Files ("+(iI+1)+"/"+aFiles.length+") - " + xmlFile.getName());
                                    progressBarDialog.setVisible(true);
                                    
                                    //... Uncompress .gz files ...//
                                    if (aFiles[iI].getName().toLowerCase().indexOf(".mzid.gz") > 0){
                                        try{                                                                            
                                            sMessage = sysutils.getTime()+" - Uncompressing " + xmlFile.getName();
                                            progressBarDialog.setTitle("Uncompressing " + xmlFile.getName());
                                            progressBarDialog.setVisible(true);

                                            File outFile = null;
                                            FileOutputStream fos = null;

                                            GZIPInputStream gin = new GZIPInputStream(new FileInputStream(xmlFile));
                                            outFile = new File(aFiles[0].getParent(), aFiles[0].getName().replaceAll("\\.gz$", ""));
                                            fos = new FileOutputStream(outFile);
                                            byte[] buf = new byte[100000];
                                            int len;
                                            while ((len = gin.read(buf)) > 0) {
                                                fos.write(buf, 0, len);
                                            }
                                            fos.close();      
                                            xmlFile = outFile;
                                            sMessage = sysutils.getTime()+" - Uncompressing ends ";
                                            System.out.println(sMessage);
                                            sOutput = sOutput + sMessage+"\n";
                                            jtaLog.setText(sOutput);
                                        }
                                        catch(IOException ioe){
                                              System.out.println("Exception has been thrown" + ioe);
                                        }
                                    }
                                    //... Unmarshall data using jmzIdentML API ...//
                                    sMessage = sysutils.getTime()+" - Unmarshalling " + xmlFile.getName() + " starts ";
                                    System.out.println(sMessage);
                                    sOutput = sOutput + sMessage+"\n";  
                                    jtaLog.setText(sOutput);
                                    unmarshalMzIDFile(model, xmlFile, "");
                                    sMessage = sysutils.getTime()+" - Unmarshalling ends ";
                                    System.out.println(sMessage);
                                    sOutput = sOutput + sMessage+"\n";
                                    jtaLog.setText(sOutput);
                                }
                            } //... For files ...//
                            
                            //... Display first element ...//
                            sMessage = sysutils.getTime()+" - Loading mzIdentML view ";
                            System.out.println(sMessage);
                            sOutput = sOutput + sMessage+"\n";
                            jtaLog.setText(sOutput);
                            loadMzIdentMLView(0, aFiles[0].getName());
                            
                            progressBarDialog.setVisible(false);
                            progressBarDialog.dispose();
                            
                            sMessage = sysutils.getTime()+" - Identification files imported successfully!";
                            System.out.println(sMessage);
                            sOutput = sOutput + sMessage+"\n";                            
                            jtaLog.setText(sOutput);
                            jbExportMzIdentMLExcel.setEnabled(true);
                            renderIdentFiles();
                            uptStatusPipeline();
                        }
                    }.start();
                }
//-------------------//
//  Read Mascot XML  //
//-------------------//
                if (aFiles[0].getName().toLowerCase().indexOf(".xml") >0){
                    //... Fill JTable ...//
                    final DefaultTableModel model = new DefaultTableModel();
                    jtIdentFiles.setModel(model);
                    model.addColumn("Name");
                    model.addColumn("Path");
                    model.addColumn("Type");
                    model.addColumn("Version");
                    model.addColumn("Raw File");

                    final ProgressBarDialog progressBarDialog = new ProgressBarDialog(this, true, "ReadingMascot");
                    final Thread thread = new Thread(new Runnable(){
                        @Override
                        public void run(){
                            //... Progress Bar ...//
                            progressBarDialog.setTitle("Reading Mascot XML files");
                            progressBarDialog.setVisible(true);
                        }
                    }, "ProgressBarDialog");
                    thread.start();
                    new Thread("ReadingMascot"){
                        @Override
                        public void run(){
                            String sMessage = "";
                            String sOutput = "";
                            jtaLog.setText(sOutput);
                            if (aFiles.length>0){                     
                                sMessage = sysutils.getTime()+" - Reading Mascot XML files (Total="+aFiles.length+")";
                                System.out.println(sMessage);
                                sOutput = sOutput + sMessage+"\n";
                                jtaLog.setText(sOutput);
                            }
                            
                            //... Reading selected files ...//                            
                            for (int iI = 0; iI < aFiles.length; iI++){
                                progressBarDialog.setTitle("Reading Mascot XML files ("+(iI+1)+"/"+aFiles.length+") - " + aFiles[iI].getName());
                                progressBarDialog.setVisible(true);
                                
                                //... Validate file extension (mixed files) ...// 
                                if (aFiles[iI].getName().toLowerCase().indexOf(".xml") >0){
                                    model.insertRow(model.getRowCount(), new Object[]{aFiles[iI].getName(), 
                                        aFiles[iI].getPath().toString().replace("\\", "/"), 
                                        "mascot_xml", 
                                        "N/A", ""});
                                }
                            } //... For files ...// 
                            
                            //... Display data for the first element ...//
                            sMessage = sysutils.getTime()+" - Loading Mascot XML view ";
                            System.out.println(sMessage);
                            sOutput = sOutput + sMessage+"\n";
                            jtaLog.setText(sOutput);
                            loadMascotView(aFiles[0].getName(), aFiles[0].getPath().toString().replace("\\", "/"));
            
                            progressBarDialog.setVisible(false);
                            progressBarDialog.dispose();
                            sMessage = sysutils.getTime()+" - Identifiation files imported successfully!";
                            System.out.println(sMessage);
                            sOutput = sOutput + sMessage+"\n";
                            jtaLog.setText(sOutput);
                            jbExportMascotXMLExcel.setEnabled(true);                            
                            renderIdentFiles();
                            uptStatusPipeline();
                            
                        }
                    }.start();
                }
//-------------------//
//  Read mzQuantML   //
//-------------------//
                if (aFiles[0].getName().toLowerCase().indexOf(".mzq") >0){
                    //... Fill JTable ...//
                    final DefaultTableModel model = new DefaultTableModel();
                    jtQuantFiles.setModel(model);
                    model.addColumn("Name");
                    model.addColumn("Path");
                    model.addColumn("Type");
                    model.addColumn("Version");

                    final ProgressBarDialog progressBarDialog = new ProgressBarDialog(this, true, "ReadingMZQ");
                    final Thread thread = new Thread(new Runnable(){
                        @Override
                        public void run(){
                            //... Progress Bar ...//
                            progressBarDialog.setTitle("Reading mzQuantML files");
                            progressBarDialog.setVisible(true);
                        }
                    }, "ProgressBarDialog");
                    thread.start();
                    new Thread("ReadingMZQ"){
                        @Override
                        public void run(){  
                            String sOutput = "";
                            String sMessage = "";
                            jtaLog.setText(sOutput);
                            
                            //... Release unmarshallers ...//
                            if (aFiles.length>0){           
                                sMessage = sysutils.getTime()+" - Reading mzQuantML files (Total="+aFiles.length+")";
                                System.out.println(sMessage);
                                sOutput = sOutput + sMessage+"\n";
                                jtaLog.setText(sOutput);
                                aMzQUnmarshaller.clear();
                            }  
                            
                            //... Reading selected files ...//
                            boolean isOK = true;
                            for (int iI = 0; iI < aFiles.length; iI++){                                   
                                //... Validate file extension (mixed files) ...// 
                                if (aFiles[iI].getName().toLowerCase().indexOf(".mzq") >0){
                                    File xmlFile = new File(aFiles[iI].getPath());
                                    progressBarDialog.setTitle("Reading mzq Files ("+(iI+1)+"/"+aFiles.length+") - " + xmlFile.getName());
                                    progressBarDialog.setVisible(true);

                                    //... Unmarshall data using jmzIdentML API ...//
                                    sMessage = sysutils.getTime()+" - Unmarshalling " + xmlFile.getName() + " starts ";
                                    System.out.println(sMessage);
                                    sOutput = sOutput + sMessage+"\n";  
                                    jtaLog.setText(sOutput);
                                    isOK = unmarshalMzQMLFile(model, xmlFile);
                                    if (!isOK){ //... Invalid mzq file ...//
                                        break;
                                    }
                                    sMessage = sysutils.getTime()+" - Unmarshalling ends ";
                                    System.out.println(sMessage);
                                    sOutput = sOutput + sMessage+"\n";
                                    jtaLog.setText(sOutput);
                                }
                            } //... For files ...//                                                       
                            if (isOK){
                                //... Display first element ...//
                                sMessage = sysutils.getTime()+" - Loading mzQuantML view ";
                                System.out.println(sMessage);
                                sOutput = sOutput + sMessage+"\n";
                                jtaLog.setText(sOutput);
                                loadMzQuantMLView(0, aFiles[0].getName());

                                sMessage = sysutils.getTime()+" - Quantification files imported successfully!";
                                System.out.println(sMessage);
                                sOutput = sOutput + sMessage+"\n";
                                jtaLog.setText(sOutput);
                            }
                            jbExportPepMZQExcel.setEnabled(true);
                            jbExportProtMZQExcel.setEnabled(true);
                            jbExportFeatMZQExcel.setEnabled(true);
                            progressBarDialog.setVisible(false);
                            progressBarDialog.dispose();
                        }
                    }.start();
                }
	    } //... From Files            
        }  //... From If                
    }//GEN-LAST:event_jmImportFileActionPerformed

    private void jmOpenProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmOpenProjectActionPerformed
        openProject();        
    }//GEN-LAST:event_jmOpenProjectActionPerformed

    private void jmOpenRecentProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmOpenRecentProjectActionPerformed
        
        
    }//GEN-LAST:event_jmOpenRecentProjectActionPerformed

    private void jmSaveProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmSaveProjectActionPerformed
        //... Save data using a mzq file ...//
        boolean isOK = saveProject();
        if(isOK){
            JOptionPane.showMessageDialog(this, "Your file was saved successfully!", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jmSaveProjectActionPerformed

    private void jmSaveProjectAsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmSaveProjectAsActionPerformed
        
    }//GEN-LAST:event_jmSaveProjectAsActionPerformed

    private void jmCloseProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmCloseProjectActionPerformed
        //... Check if the project needs to be saved ...//
        if(bProjectModified){
            int iOption = JOptionPane.showConfirmDialog(this, "You have not saved your changes. Do you want to save them now?", "Warning", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (iOption == JOptionPane.OK_OPTION) {            
                jmSaveProjectActionPerformed(null);
                initProjectValues();
            }else if (iOption == JOptionPane.NO_OPTION){
                initProjectValues();
            }
        }else{
            initProjectValues();
        }
    }//GEN-LAST:event_jmCloseProjectActionPerformed

    private void jbNewProjectMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbNewProjectMouseClicked
        jmNewProjectActionPerformed(null);
    }//GEN-LAST:event_jbNewProjectMouseClicked

    private void jbImportFileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbImportFileMouseClicked
        
        jmImportFileActionPerformed(null);
    }//GEN-LAST:event_jbImportFileMouseClicked

    private void jbOpenProjectMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbOpenProjectMouseClicked
       
        jmOpenProjectActionPerformed(null);        
    }//GEN-LAST:event_jbOpenProjectMouseClicked

    private void jbSaveProjectMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbSaveProjectMouseClicked
        jmSaveProjectActionPerformed(null);
    }//GEN-LAST:event_jbSaveProjectMouseClicked

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        
        jmExitActionPerformed(null);
    }//GEN-LAST:event_formWindowClosed

    private void jmShowProjectFilesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmShowProjectFilesActionPerformed
        if (jspMainPanelView.getDividerLocation() <= 5)
        {
            jspMainPanelView.setDividerLocation(250); 
            Icon thick = new ImageIcon(getClass().getClassLoader().getResource("images/thick.gif"));            
            jmShowProjectFiles.setIcon(thick);
        }
        else
        {
            jspMainPanelView.setDividerLocation(0);            
            jmShowProjectFiles.setIcon(null);
        }
    }//GEN-LAST:event_jmShowProjectFilesActionPerformed

    private void jmShowViewerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmShowViewerActionPerformed
        if (jspViewerAndProperties.getDividerLocation() <= 5)
        {
            jspViewerAndProperties.setDividerLocation(600); 
            Icon thick = new ImageIcon(getClass().getClassLoader().getResource("images/thick.gif"));
            jmShowViewer.setIcon(thick);
        }
        else
        {
            jspViewerAndProperties.setDividerLocation(0); 
            jmShowViewer.setIcon(null);
        }
    }//GEN-LAST:event_jmShowViewerActionPerformed

    private void jmShowPropertiesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmShowPropertiesActionPerformed
        
    }//GEN-LAST:event_jmShowPropertiesActionPerformed

    private void jmAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmAboutActionPerformed
        About about = new About();
        final JFrame jfAbout = new JFrame("About ProteoSuite");
        jfAbout.setResizable(false);
        jfAbout.setSize(400, 300);
        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0, false);
        Action escapeAction = new AbstractAction() 
        {
            public void actionPerformed(ActionEvent e) {
                jfAbout.dispose();
            }
        };        
        jfAbout.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
        jfAbout.getRootPane().getActionMap().put("ESCAPE", escapeAction);
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x1 = dim.width / 2;
        int y1 = dim.height / 2;
        int x2 = jfAbout.getSize().width / 2;
        int y2 = jfAbout.getSize().height / 2;        
        jfAbout.setLocation(x1-x2, y1-y2);
        Image iconApp = new ImageIcon(getClass().getClassLoader().getResource("images/icon.gif")).getImage();
        jfAbout.setIconImage(iconApp);
        jfAbout.setAlwaysOnTop(true);
       
        jfAbout.add(about);
        jfAbout.pack();
        jfAbout.setVisible(true);       
    }//GEN-LAST:event_jmAboutActionPerformed

    private void jtMzMLMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtMzMLMouseClicked
        //... Right click event for displaying MS raw data and spectrum ...//
        if (evt.getButton()== 1){                        
            if (jtRawFiles.getSelectedRow()>-1){
                showRawData(jtRawFiles.getSelectedRow(), jtMzML.getValueAt(jtMzML.getSelectedRow(), 1).toString());
                showSpectrum(jtRawFiles.getSelectedRow(), jtMzML.getValueAt(jtMzML.getSelectedRow(), 1).toString());            
            }
            else{
                showRawData(0, jtMzML.getValueAt(jtMzML.getSelectedRow(), 1).toString());
                showSpectrum(0, jtMzML.getValueAt(jtMzML.getSelectedRow(), 1).toString());                            
            }
            jtpViewer.setSelectedIndex(0);
        }
    }//GEN-LAST:event_jtMzMLMouseClicked

    private void jtRawFilesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtRawFilesMouseClicked
        //... Right click event for displaying mzML data ...//
        if ((evt.getButton() == 1)&&(jtRawFiles.getValueAt(jtRawFiles.getSelectedRow(), 2).toString().equals("mzML"))) {
            loadMzMLView(jtRawFiles.getSelectedRow());
            showChromatogram(jtRawFiles.getSelectedRow(), jtRawFiles.getValueAt(jtRawFiles.getSelectedRow(), 1).toString());
        }
        //... Right click event for displaying MGF data ...//
        if ((evt.getButton() == 1)&&(jtRawFiles.getValueAt(jtRawFiles.getSelectedRow(), 2).toString().equals("MGF"))) {                        
            loadMGFView(jtRawFiles.getSelectedRow());
        }
    }//GEN-LAST:event_jtRawFilesMouseClicked

    private void jmMzML2MGFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmMzML2MGFActionPerformed
        
        //... Load MzML2MGF GUI ...//
        final JFrame jfWinParams = new JFrame("Convert mzML files to MGF");        
        MzML2MGFView winParams = new MzML2MGFView(jfWinParams, sWorkspace);
        jfWinParams.setResizable(false);
        jfWinParams.setSize(500, 450);
        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0, false);
        Action escapeAction = new AbstractAction() 
        {
            public void actionPerformed(ActionEvent e) {
                jfWinParams.dispose();
            }
        };        
        jfWinParams.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
        jfWinParams.getRootPane().getActionMap().put("ESCAPE", escapeAction);
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x1 = dim.width / 2;
        int y1 = dim.height / 2;
        int x2 = jfWinParams.getSize().width / 2;
        int y2 = jfWinParams.getSize().height / 2;        
        jfWinParams.setLocation(x1-x2, y1-y2);
        Image iconApp = new ImageIcon(getClass().getClassLoader().getResource("images/icon.gif")).getImage();
        jfWinParams.setIconImage(iconApp);
        jfWinParams.setAlwaysOnTop(true);
       
        jfWinParams.add(winParams);
        jfWinParams.pack();
        jfWinParams.setVisible(true);
    }//GEN-LAST:event_jmMzML2MGFActionPerformed

    private void jmOptionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmOptionsActionPerformed
        ProjectParamsView panelParams = new ProjectParamsView();
        final JFrame jfWinParams = new JFrame("Options");
        jfWinParams.setResizable(false);
        jfWinParams.setSize(580, 370);
        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0, false);
        final Action escapeAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                jfWinParams.dispose();
                boolean isOK = getWorkspace();
                if(isOK){
                    uptTitle();
                }
            }
        };
        jfWinParams.addWindowListener(new WindowAdapter(){
            @Override
             public void windowClosing(WindowEvent e){ 
                 escapeAction.actionPerformed(null);}
             });
        
        jfWinParams.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
        jfWinParams.getRootPane().getActionMap().put("ESCAPE", escapeAction);
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x1 = dim.width / 2;
        int y1 = dim.height / 2;
        int x2 = jfWinParams.getSize().width / 2;
        int y2 = jfWinParams.getSize().height / 2;        
        jfWinParams.setLocation(x1-x2, y1-y2);
        Image iconApp = new ImageIcon(getClass().getClassLoader().getResource("images/icon.gif")).getImage();
        jfWinParams.setIconImage(iconApp);
        jfWinParams.setAlwaysOnTop(true);
       
        jfWinParams.add(panelParams);
        jfWinParams.pack();
        jfWinParams.setVisible(true);    
    }//GEN-LAST:event_jmOptionsActionPerformed

    private void jmSubmitPRIDEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmSubmitPRIDEActionPerformed
        JOptionPane.showMessageDialog(this, "The module for submitting data into PRIDE is under development.", "Information", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jmSubmitPRIDEActionPerformed

    private void jmEditIdentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmEditIdentActionPerformed
        
    }//GEN-LAST:event_jmEditIdentActionPerformed

    private void jmEditQuantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmEditQuantActionPerformed
        if (jcbTechnique.getSelectedItem().equals("Select technique")){
            JOptionPane.showMessageDialog(this, "Please specify the technique used in your pipeline (e.g. iTRAQ, TMT, etc.)", "Error", JOptionPane.ERROR_MESSAGE);
        }else{
            ArrayList alRawFiles = new ArrayList();
            for(int iI=0; iI<jtRawFiles.getRowCount(); iI++){
                alRawFiles.add(jtRawFiles.getValueAt(iI, 0));
            }
            QuantParamsView quantParams = new QuantParamsView(alRawFiles, sWorkspace, jcbTechnique.getSelectedItem().toString());
            final JFrame jfQuantParams = new JFrame("Edit Quantitation Parameters");
            jfQuantParams.setResizable(false);
            jfQuantParams.setSize(638, 585);
            KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0, false);
            final Action escapeAction = new AbstractAction() 
            {
                public void actionPerformed(ActionEvent e) {
                    jfQuantParams.dispose();
                }
            };        
            jfQuantParams.addWindowListener(new WindowAdapter(){
                @Override
                 public void windowClosing(WindowEvent e){ 
                     escapeAction.actionPerformed(null);}
                 });        

            jfQuantParams.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
            jfQuantParams.getRootPane().getActionMap().put("ESCAPE", escapeAction);

            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            int x1 = dim.width / 2;
            int y1 = dim.height / 2;
            int x2 = jfQuantParams.getSize().width / 2;
            int y2 = jfQuantParams.getSize().height / 2;        
            jfQuantParams.setLocation(x1-x2, y1-y2);
            Image iconApp = new ImageIcon(getClass().getClassLoader().getResource("images/icon.gif")).getImage();
            jfQuantParams.setIconImage(iconApp);
            jfQuantParams.setAlwaysOnTop(true);

            jfQuantParams.add(quantParams);
            jfQuantParams.pack();
            jfQuantParams.setVisible(true);        
        }
    }//GEN-LAST:event_jmEditQuantActionPerformed
    /*
     * Description: This method allows the user to read our online documentation of proteosuite consisting of tutorials and user manuals.     
     */
    private void jmHelpContentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmHelpContentActionPerformed
	OpenURL url = new OpenURL("http://www.proteosuite.org/?q=tutorials");
    }//GEN-LAST:event_jmHelpContentActionPerformed

    private void jbRunQuantAnalysisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbRunQuantAnalysisMouseClicked
        //... Call the quantitation method ...//
        jmRunQuantAnalysisActionPerformed(null);
    }//GEN-LAST:event_jbRunQuantAnalysisMouseClicked
    
    private void jmRunQuantAnalysisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmRunQuantAnalysisActionPerformed
        
        //... Check if there is a valid workspace ...//
        boolean isOK = getWorkspace();
        if (isOK){        
            //... Validate if we got raw and identification files ...//        
            if (jtRawFiles.getRowCount()<=0){
                JOptionPane.showMessageDialog(this, "Please select at least one raw file. (Use the import file option in the menu).", "Error", JOptionPane.ERROR_MESSAGE);
            } 
            else if (jtIdentFiles.getRowCount()<=0){
                JOptionPane.showMessageDialog(this, "Please select at least one identification file. (Use the import file option in the menu).", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else if (jcbTechnique.getSelectedItem().equals("Select technique")){
                JOptionPane.showMessageDialog(this, "Please specify the technique used in your pipeline (e.g. iTRAQ, TMT, etc.)", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else if (jcbOutputFormat.getSelectedItem().equals("Select format")){
                JOptionPane.showMessageDialog(this, "Please specify the output format (e.g. .mzq, .csv, etc.)", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else{
                isOK = checkMapping();
                if(isOK){
                    //... In this thread, we call xTracker module (parameter files are generated using proteosuite) ...//
                    final ProgressBarDialog progressBarDialog = new ProgressBarDialog(this, true, "RunPipeline");
                    final Thread thread = new Thread(new Runnable(){
                        @Override
                        public void run(){
                            //... Progress Bar ...//
                            if ((jcbTechnique.getSelectedItem().toString().equals("iTRAQ"))||
                                (jcbTechnique.getSelectedItem().toString().equals("TMT"))||
                                (jcbTechnique.getSelectedItem().toString().equals("emPAI"))){
                                progressBarDialog.setTitle("Running Quantitation Analysis via x-Tracker");
                                progressBarDialog.setVisible(true);
                            }
                        }
                    }, "ProgressBarDialog");
                    thread.start();
                    new Thread("RunPipeline"){
                        @Override
                        public void run(){
                            boolean isOK = false;
                            if (jcbTechnique.getSelectedItem().toString().equals("Label free")){ //... Label free will be performed in proteosuite ...//                                                                        
                                
                                progressBarDialog.setVisible(false);
                                progressBarDialog.dispose();
                                JOptionPane.showMessageDialog(ProteoSuiteView.this, "SILAC, Label Free and 15N are under development. Please stay tuned for new releases.", "Information", JOptionPane.INFORMATION_MESSAGE);
                                        
//                                final JPanel run = new JPanel();
//                                JLabel jLabel1 = new JLabel("Please specify the parameters to perform the label free method.");
//                                JLabel jLabel2 = new JLabel("Scan Window:");
//                                JLabel jLabel3 = new JLabel("To:");
//                                JLabel jLabel4 = new JLabel("m/z Window:");
//                                JLabel jLabel5 = new JLabel("To:");                                
//                                JLabel jLabel6 = new JLabel("Peaks left-right:");                                 
//                                JTextField jTextField1 = new JTextField("1671");
//                                JTextField jTextField2 = new JTextField("1672");
//                                JTextField jTextField3 = new JTextField("300.00");
//                                JTextField jTextField4 = new JTextField("1000.00");
//                                JTextField jTextField5 = new JTextField("4");
//                                GridLayout layout=new GridLayout(11,1);
//                                jTextField1.requestFocusInWindow();                        
//                                run.setLayout(layout);
//                                run.add(jLabel1);
//                                run.add(jLabel2);
//                                run.add(jTextField1);
//                                run.add(jLabel3);
//                                run.add(jTextField2);
//                                run.add(jLabel4);
//                                run.add(jTextField3);
//                                run.add(jLabel5);
//                                run.add(jTextField4);
//                                run.add(jLabel6);
//                                run.add(jTextField5);                                
//                                int iOption = JOptionPane.showConfirmDialog(null, run, "Edit Quantitation Parameters", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
//                                if (iOption == JOptionPane.OK_OPTION) {
//                                    generateTemplate(Integer.parseInt(jTextField1.getText()), 
//                                                     Integer.parseInt(jTextField2.getText()),
//                                                     Double.parseDouble(jTextField3.getText()),
//                                                     Double.parseDouble(jTextField4.getText()),
//                                                     Integer.parseInt(jTextField5.getText())
//                                            );
//                                }
                            }
                            else{
                                if ((jcbTechnique.getSelectedItem().toString().equals("iTRAQ"))||
                                    (jcbTechnique.getSelectedItem().toString().equals("TMT"))||
                                    (jcbTechnique.getSelectedItem().toString().equals("emPAI"))){ 
                                        //... Label free will be performed in proteosuite ...//                                
                                        //... Generate config files for xTracker ...//                                    
                                        isOK = generateFiles();
                                        if(isOK){
                                            //... Run xTracker ...//
                                            xTracker run = null;
                                            System.out.println(sysutils.getTime()+" - ****** Running x-Tracker *****");
                                            jtaLog.append("\n"+sysutils.getTime()+" - ****** Running x-Tracker *****");                                            
                                            run = new xTracker(sWorkspace.replace("\\", "/") + "/" + sProjectName, sWorkspace.replace("\\", "/"));
                                            
                                            //... Load the mzQuantML file into memory (With results included) ...//
                                            File xmlFile = new File(sWorkspace + "/" +  sProjectName);
                                            aMzQUnmarshaller.clear();
                                            final DefaultTableModel model = new DefaultTableModel();
                                            jtQuantFiles.setModel(model);
                                            model.addColumn("Name");
                                            model.addColumn("Path");
                                            model.addColumn("Type");
                                            model.addColumn("Version");
                                            unmarshalMzQMLFile(model, xmlFile);
                
                                            System.out.println(sysutils.getTime()+" - Displaying mzquantml results...");
                                            jtaLog.append("\n"+sysutils.getTime()+" - Displaying mzquantml results...");
                                            jlFileNameMzQText.setText(sProjectName); //... Display of the results ...//
                                            loadMzQuantMLView(0, sProjectName);
                                            System.out.println(sysutils.getTime()+" - Execution finished...");
                                            jtaLog.append("\n"+sysutils.getTime()+" - Execution finished...");
                                            
                                            jbExportPepMZQExcel.setEnabled(true);
                                            jbExportProtMZQExcel.setEnabled(true);
                                            jbExportFeatMZQExcel.setEnabled(true);
                            
                                            JOptionPane.showMessageDialog(ProteoSuiteView.this, "Process finished. Quantitation results can be viewed in the mzQuantML View.", "Information", JOptionPane.INFORMATION_MESSAGE);
                                        }
                                        progressBarDialog.setVisible(false);
                                        progressBarDialog.dispose();
                                        uptTitle();
                                        bProjectModified = false;
                                        uptSaveProjectStatus();
                                        uptCloseProjectStatus();                                        
                                }
                                else{
                                        progressBarDialog.setVisible(false);
                                        progressBarDialog.dispose();
                                        JOptionPane.showMessageDialog(ProteoSuiteView.this, "SILAC, Label Free and 15N are under development. Please stay tuned for new releases.", "Information", JOptionPane.INFORMATION_MESSAGE);
                                }
                            }
                        }
                    }.start();                    
                }
            }
        }
    }//GEN-LAST:event_jmRunQuantAnalysisActionPerformed

    private void jmSpectrumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmSpectrumActionPerformed
        jtpViewer.setSelectedIndex(0);        
        Icon thick = new ImageIcon(getClass().getClassLoader().getResource("images/thick.gif"));
        jmSpectrum.setIcon(thick);         
        jmTIC.setIcon(null);
        jm2DView.setIcon(null);
        jm3DView.setIcon(null); 
    }//GEN-LAST:event_jmSpectrumActionPerformed

    private void jmTICActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmTICActionPerformed
        jtpViewer.setSelectedIndex(1);
        Icon thick = new ImageIcon(getClass().getClassLoader().getResource("images/thick.gif"));
        jmSpectrum.setIcon(null);         
        jmTIC.setIcon(thick);
        jm2DView.setIcon(null);
        jm3DView.setIcon(null);         
    }//GEN-LAST:event_jmTICActionPerformed

    private void jm2DViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jm2DViewActionPerformed
        jtpViewer.setSelectedIndex(2);
        Icon thick = new ImageIcon(getClass().getClassLoader().getResource("images/thick.gif"));
        jmSpectrum.setIcon(null);         
        jmTIC.setIcon(null);
        jm2DView.setIcon(thick);
        jm3DView.setIcon(null);
    }//GEN-LAST:event_jm2DViewActionPerformed

    private void jm3DViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jm3DViewActionPerformed
        jtpViewer.setSelectedIndex(3);
        Icon thick = new ImageIcon(getClass().getClassLoader().getResource("images/thick.gif"));
        jmSpectrum.setIcon(null);         
        jmTIC.setIcon(null);
        jm2DView.setIcon(null);
        jm3DView.setIcon(thick);
    }//GEN-LAST:event_jm3DViewActionPerformed

    private void jmMzMLViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmMzMLViewActionPerformed
        jtpProperties.setSelectedIndex(0);
        Icon thick = new ImageIcon(getClass().getClassLoader().getResource("images/thick.gif"));
        jmMzMLView.setIcon(thick);
        jmMGFView.setIcon(null);
        jmMzIdentMLView.setIcon(null);
        jmMascotXMLView.setIcon(null);
        jmMzQuantMLView.setIcon(null);
    }//GEN-LAST:event_jmMzMLViewActionPerformed

    private void jmMzQuantMLViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmMzQuantMLViewActionPerformed
        jtpProperties.setSelectedIndex(4);
        Icon thick = new ImageIcon(getClass().getClassLoader().getResource("images/thick.gif"));
        jmMzMLView.setIcon(null);
        jmMGFView.setIcon(null);
        jmMzIdentMLView.setIcon(null);        
        jmMascotXMLView.setIcon(null);
        jmMzQuantMLView.setIcon(thick);
    }//GEN-LAST:event_jmMzQuantMLViewActionPerformed

    private void jmLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmLogActionPerformed
        jtpLog.setSelectedIndex(0);
        Icon thick = new ImageIcon(getClass().getClassLoader().getResource("images/thick.gif"));
        jmLog.setIcon(thick);
        jmRawData.setIcon(null);
    }//GEN-LAST:event_jmLogActionPerformed

    private void jmRawDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmRawDataActionPerformed
        jtpLog.setSelectedIndex(1);
        Icon thick = new ImageIcon(getClass().getClassLoader().getResource("images/thick.gif"));
        jmLog.setIcon(null);
        jmRawData.setIcon(thick);
    }//GEN-LAST:event_jmRawDataActionPerformed

    private void jmMascotXMLViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmMascotXMLViewActionPerformed
        jtpProperties.setSelectedIndex(3);
        Icon thick = new ImageIcon(getClass().getClassLoader().getResource("images/thick.gif"));
        jmMzMLView.setIcon(null);
        jmMGFView.setIcon(null);
        jmMzIdentMLView.setIcon(null);        
        jmMascotXMLView.setIcon(thick);
        jmMzQuantMLView.setIcon(null);
    }//GEN-LAST:event_jmMascotXMLViewActionPerformed

    private void jtScanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtScanKeyPressed
        String sChain = "";
        sChain = jtScan.getText() + evt.getKeyChar();
        searchValueInMzML(sChain, 0);
    }//GEN-LAST:event_jtScanKeyPressed

    private void jtRTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtRTKeyPressed
        String sChain = "";
        sChain = jtRT.getText() + evt.getKeyChar();
        searchValueInMzML(sChain, 5);        
    }//GEN-LAST:event_jtRTKeyPressed

    private void jtProteinKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtProteinKeyPressed
        String sChain = "";
        sChain = "" + evt.getKeyChar();
            
        Pattern p = Pattern.compile("^[a-zA-Z0-9]*$"); 
        Matcher m = p.matcher(sChain);
        if (m.find())
        {
            sChain = jtProtein.getText() + evt.getKeyChar();
            sChain = sChain.toUpperCase();
            searchValueInMascotXML(sChain, 1); 
        }
    }//GEN-LAST:event_jtProteinKeyPressed

    private void jtPeptideKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtPeptideKeyPressed
       
        String sChain = "";
        sChain = "" + evt.getKeyChar();
            
        Pattern p = Pattern.compile("^[a-zA-Z0-9]*$"); 
        Matcher m = p.matcher(sChain);
        if (m.find())
        {
            sChain = jtPeptide.getText() + evt.getKeyChar();            
            sChain = sChain.toUpperCase();
            searchValueInMascotXML(sChain, 2); 
        }
    }//GEN-LAST:event_jtPeptideKeyPressed

    private void jtMSMzKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtMSMzKeyPressed
        String sChain = "";         
        sChain = "" + evt.getKeyChar();          
        Pattern p = Pattern.compile("^[a-zA-Z0-9]*$");         
        Matcher m = p.matcher(sChain);         
        if (m.find()) 
        {             
            sChain = jtMSMz.getText() + evt.getKeyChar();             
            searchValueInRawData(sChain, 1);         
        }
    }//GEN-LAST:event_jtMSMzKeyPressed

    private void jtMSIndexKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtMSIndexKeyPressed
        String sChain = "";         
        sChain = "" + evt.getKeyChar();          
        Pattern p = Pattern.compile("^[a-zA-Z0-9]*$");         
        Matcher m = p.matcher(sChain);         
        if (m.find()) 
        {             
            sChain = jtMSIndex.getText() + evt.getKeyChar();             
            searchValueInRawData(sChain, 0);         
        }
    }//GEN-LAST:event_jtMSIndexKeyPressed

    private void jmContactUsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmContactUsActionPerformed
        OpenURL url = new OpenURL("http://www.proteosuite.org/?q=contact");
    }//GEN-LAST:event_jmContactUsActionPerformed

    private void jmMGFViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmMGFViewActionPerformed
        jtpProperties.setSelectedIndex(1);
        Icon thick = new ImageIcon(getClass().getClassLoader().getResource("images/thick.gif"));
        jmMzMLView.setIcon(null);
        jmMGFView.setIcon(thick);
        jmMzIdentMLView.setIcon(null);        
        jmMascotXMLView.setIcon(null);
        jmMzQuantMLView.setIcon(null);        
    }//GEN-LAST:event_jmMGFViewActionPerformed

    private void jmMzIdentMLViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmMzIdentMLViewActionPerformed
        jtpProperties.setSelectedIndex(2);
        Icon thick = new ImageIcon(getClass().getClassLoader().getResource("images/thick.gif"));
        jmMzMLView.setIcon(null);
        jmMGFView.setIcon(null);
        jmMzIdentMLView.setIcon(thick);        
        jmMascotXMLView.setIcon(null);
        jmMzQuantMLView.setIcon(null);        
    }//GEN-LAST:event_jmMzIdentMLViewActionPerformed

    private void jtMGFMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtMGFMouseClicked
        //... Validate right click selection for MGF files ...//
        if (evt.getButton()== 1){   
            if (jtRawFiles.getSelectedRow()>-1){
                showRawDataMGF(jtRawFiles.getSelectedRow(), jtMGF.getValueAt(jtMGF.getSelectedRow(), 4).toString());                
            }
            else{
                showRawDataMGF(0, jtMGF.getValueAt(jtMGF.getSelectedRow(), 4).toString());                
            }
            jtpViewer.setSelectedIndex(0);
        }
    }//GEN-LAST:event_jtMGFMouseClicked

    private void jtScanTitleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtScanTitleKeyPressed
        String sChain = "";
        sChain = jtScanTitle.getText() + evt.getKeyChar();
        searchValueInMGF(sChain, 1);
    }//GEN-LAST:event_jtScanTitleKeyPressed

    private void jtProteinMzIdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtProteinMzIdKeyPressed
        String sChain = "";
        sChain = "" + evt.getKeyChar();            
        Pattern p = Pattern.compile("^[a-zA-Z0-9]*$"); 
        Matcher m = p.matcher(sChain);
        if (m.find()){
            sChain = jtProteinMzId.getText() + evt.getKeyChar();
            searchValueInIdentML(sChain, 1);
        }
    }//GEN-LAST:event_jtProteinMzIdKeyPressed

    private void jtPeptideMzIdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtPeptideMzIdKeyPressed
        String sChain = "";
        sChain = "" + evt.getKeyChar();
            
        Pattern p = Pattern.compile("^[a-zA-Z0-9]*$"); 
        Matcher m = p.matcher(sChain);
        if (m.find())
        {
            sChain = jtPeptideMzId.getText() + evt.getKeyChar();            
            sChain = sChain.toUpperCase();
            searchValueInIdentML(sChain, 2); 
        }        
    }//GEN-LAST:event_jtPeptideMzIdKeyPressed

    private void jtQuantFilesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtQuantFilesMouseClicked
        if ((evt.getButton() == 1)&&(jtQuantFiles.getValueAt(jtQuantFiles.getSelectedRow(), 2).toString().equals("mzq"))) {            
            loadMzQuantMLView(jtQuantFiles.getSelectedRow(), jtQuantFiles.getValueAt(jtQuantFiles.getSelectedRow(), 0).toString());
        }
    }//GEN-LAST:event_jtQuantFilesMouseClicked

    private void jtIdentFilesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtIdentFilesMouseClicked
        if ((evt.getButton() == 1) && (jtIdentFiles.getValueAt(jtIdentFiles.getSelectedRow(), 2).toString().equals("mascot_xml"))) {
            loadMascotView(jtIdentFiles.getValueAt(jtIdentFiles.getSelectedRow(), 0).toString(), 
                           jtIdentFiles.getValueAt(jtIdentFiles.getSelectedRow(), 1).toString());         
        }
        if ((evt.getButton() == 1) && (jtIdentFiles.getValueAt(jtIdentFiles.getSelectedRow(), 2).toString().equals("mzid"))) {             
            loadMzIdentMLView(jtIdentFiles.getSelectedRow(), jtIdentFiles.getValueAt(jtIdentFiles.getSelectedRow(), 0).toString());         
        }
    }//GEN-LAST:event_jtIdentFilesMouseClicked

    private void jMzMLCompressedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMzMLCompressedActionPerformed
        //... Load MzML2MGF GUI ...//
        final JFrame jfWinParams = new JFrame("Compress mzML files");        
        MzMLCompressView winParams = new MzMLCompressView(jfWinParams, sWorkspace);
        jfWinParams.setResizable(false);
        jfWinParams.setSize(500, 450);
        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0, false);
        Action escapeAction = new AbstractAction() 
        {
            public void actionPerformed(ActionEvent e) {
                jfWinParams.dispose();
            }
        };        
        jfWinParams.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
        jfWinParams.getRootPane().getActionMap().put("ESCAPE", escapeAction);
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x1 = dim.width / 2;
        int y1 = dim.height / 2;
        int x2 = jfWinParams.getSize().width / 2;
        int y2 = jfWinParams.getSize().height / 2;        
        jfWinParams.setLocation(x1-x2, y1-y2);
        Image iconApp = new ImageIcon(getClass().getClassLoader().getResource("images/icon.gif")).getImage();
        jfWinParams.setIconImage(iconApp);
        jfWinParams.setAlwaysOnTop(true);
       
        jfWinParams.add(winParams);
        jfWinParams.pack();
        jfWinParams.setVisible(true);                
    }//GEN-LAST:event_jMzMLCompressedActionPerformed

    private void jmMaxQ2MZQActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmMaxQ2MZQActionPerformed
        //... Load MaxQuant2MZQ GUI ...//
        final JFrame jfWinParams = new JFrame("Convert MaxQuant result files to mzQuantML");        
        MaxQ2MZQView winParams = new MaxQ2MZQView(jfWinParams, sWorkspace);
        jfWinParams.setResizable(false);
        jfWinParams.setSize(500, 150);
        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0, false);
        Action escapeAction = new AbstractAction() 
        {
            public void actionPerformed(ActionEvent e) {
                jfWinParams.dispose();
            }
        };        
        jfWinParams.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
        jfWinParams.getRootPane().getActionMap().put("ESCAPE", escapeAction);
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x1 = dim.width / 2;
        int y1 = dim.height / 2;
        int x2 = jfWinParams.getSize().width / 2;
        int y2 = jfWinParams.getSize().height / 2;        
        jfWinParams.setLocation(x1-x2, y1-y2);
        Image iconApp = new ImageIcon(getClass().getClassLoader().getResource("images/icon.gif")).getImage();
        jfWinParams.setIconImage(iconApp);
        jfWinParams.setAlwaysOnTop(true);
        
        jfWinParams.add(winParams);
        jfWinParams.pack();
        jfWinParams.setVisible(true);
    }//GEN-LAST:event_jmMaxQ2MZQActionPerformed

    private void jmProgenesis2MZQActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmProgenesis2MZQActionPerformed
        //... Load Progenesis2MZQ GUI ...//
        final JFrame jfWinParams = new JFrame("Convert Progenesis result files to mzQuantML");        
        Prog2MZQView winParams = new Prog2MZQView(jfWinParams, sWorkspace);
        jfWinParams.setResizable(false);
        jfWinParams.setSize(500, 180);
        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0, false);
        Action escapeAction = new AbstractAction() 
        {
            public void actionPerformed(ActionEvent e) {
                jfWinParams.dispose();
            }
        };
        jfWinParams.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
        jfWinParams.getRootPane().getActionMap().put("ESCAPE", escapeAction);
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x1 = dim.width / 2;
        int y1 = dim.height / 2;
        int x2 = jfWinParams.getSize().width / 2;
        int y2 = jfWinParams.getSize().height / 2;        
        jfWinParams.setLocation(x1-x2, y1-y2);
        Image iconApp = new ImageIcon(getClass().getClassLoader().getResource("images/icon.gif")).getImage();
        jfWinParams.setIconImage(iconApp);
        jfWinParams.setAlwaysOnTop(true);
        
        jfWinParams.add(winParams);
        jfWinParams.pack();
        jfWinParams.setVisible(true);
    }//GEN-LAST:event_jmProgenesis2MZQActionPerformed

    private void jbExportMzMLExcelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbExportMzMLExcelMouseClicked
       if (this.jtMzML.getRowCount()>0){
        try {           
            JFileChooser chooser = new JFileChooser(sPreviousLocation);
            chooser.setDialogTitle("Please type the file name");

            //... Applying file extension filters ...//
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Files (*.xls)", "xls");
            //... Filters must be in descending order ...//
            chooser.addChoosableFileFilter(filter);

            //... Setting default directory ...//
            if (sPreviousLocation == null || sPreviousLocation.contains("")){
                chooser.setCurrentDirectory(new java.io.File(sWorkspace)); //... If not found it goes to Home, exception not needed ...//   
            }
            else{
                chooser.setCurrentDirectory(new java.io.File(sPreviousLocation)); //... If not found it goes to Home, exception not needed ...//               
            }

            int returnVal = chooser.showSaveDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION){     
                File file = chooser.getSelectedFile();
                int mid= 0;
                mid = file.getName().lastIndexOf(".xls");
                String fileLocation = "";
                if(mid<=0){
                    fileLocation = file.getPath()+".xls";
                }else{
                    fileLocation = file.getPath();
                }
                fileLocation.replace("\\", "/");
                
                //... Check if file exists ...//
                boolean exists = (new File(fileLocation).exists());
                boolean write = true;
                if (exists){
                    int n = JOptionPane.showConfirmDialog(null, "The file already exists in this directory, do you want to overwrite it?", "Information", JOptionPane.YES_NO_OPTION);
                    if(n==JOptionPane.NO_OPTION){
                        write = false;
                    }
                }
                if (write){
                    ExcelExporter exp = new ExcelExporter();
                    exp.fillData(jtMzML, new File(fileLocation), true, 0);
                    JOptionPane.showMessageDialog(this, "Data saved at "+fileLocation, "Information", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } 
       }
       else{
           JOptionPane.showMessageDialog(this, "No data to export", "Error", JOptionPane.INFORMATION_MESSAGE);
       }
    }//GEN-LAST:event_jbExportMzMLExcelMouseClicked

    private void jtPeptideMZQKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtPeptideMZQKeyPressed
        String sChain = "";
        sChain = jtPeptideMZQ.getText() + evt.getKeyChar();
        searchValueInMZQPep(sChain, 0);
    }//GEN-LAST:event_jtPeptideMZQKeyPressed

    private void jbExportPepMZQExcelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbExportPepMZQExcelMouseClicked
       if (this.jtPeptideQuant.getRowCount()>0){
        try {           
            JFileChooser chooser = new JFileChooser(sPreviousLocation);
            chooser.setDialogTitle("Please type the file name");

            //... Applying file extension filters ...//
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Files (*.xls)", "xls");
            //... Filters must be in descending order ...//
            chooser.addChoosableFileFilter(filter);

            //... Setting default directory ...//
            if (sPreviousLocation == null || sPreviousLocation.contains("")){
                chooser.setCurrentDirectory(new java.io.File(sWorkspace)); //... If not found it goes to Home, exception not needed ...//   
            }
            else{
                chooser.setCurrentDirectory(new java.io.File(sPreviousLocation)); //... If not found it goes to Home, exception not needed ...//               
            }

            int returnVal = chooser.showSaveDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION){     
                File file = chooser.getSelectedFile();
                int mid= 0;
                mid = file.getName().lastIndexOf(".xls");
                String fileLocation = "";
                if(mid<=0){
                    fileLocation = file.getPath()+".xls";
                }else{
                    fileLocation = file.getPath();
                }
                fileLocation.replace("\\", "/");
                
                //... Check if file exists ...//
                boolean exists = (new File(fileLocation).exists());
                boolean write = true;
                if (exists){
                    int n = JOptionPane.showConfirmDialog(null, "The file already exists in this directory, do you want to overwrite it?", "Information", JOptionPane.YES_NO_OPTION);
                    if(n==JOptionPane.NO_OPTION){
                        write = false;
                    }
                }
                if (write){
                    ExcelExporter exp = new ExcelExporter();
                    exp.fillData(jtPeptideQuant, new File(fileLocation), true, 0);
                    JOptionPane.showMessageDialog(this, "Data saved at "+fileLocation, "Information", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } 
       }
       else{
           JOptionPane.showMessageDialog(this, "No data to export", "Error", JOptionPane.INFORMATION_MESSAGE);
       }        
    }//GEN-LAST:event_jbExportPepMZQExcelMouseClicked

    private void jtProteinMZQKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtProteinMZQKeyPressed
        String sChain = "";
        sChain = jtProteinMZQ.getText() + evt.getKeyChar();
        searchValueInMZQProt(sChain, 0);        
    }//GEN-LAST:event_jtProteinMZQKeyPressed

    private void jbExportProtMZQExcelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbExportProtMZQExcelMouseClicked
       if (this.jtProteinQuant.getRowCount()>0){
        try {           
            JFileChooser chooser = new JFileChooser(sPreviousLocation);
            chooser.setDialogTitle("Please type the file name");

            //... Applying file extension filters ...//
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Files (*.xls)", "xls");
            //... Filters must be in descending order ...//
            chooser.addChoosableFileFilter(filter);

            //... Setting default directory ...//
            if (sPreviousLocation == null || sPreviousLocation.contains("")){
                chooser.setCurrentDirectory(new java.io.File(sWorkspace)); //... If not found it goes to Home, exception not needed ...//   
            }
            else{
                chooser.setCurrentDirectory(new java.io.File(sPreviousLocation)); //... If not found it goes to Home, exception not needed ...//               
            }

            int returnVal = chooser.showSaveDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION){     
                File file = chooser.getSelectedFile();
                int mid= 0;
                mid = file.getName().lastIndexOf(".xls");
                String fileLocation = "";
                if(mid<=0){
                    fileLocation = file.getPath()+".xls";
                }else{
                    fileLocation = file.getPath();
                }
                fileLocation.replace("\\", "/");
                
                //... Check if file exists ...//
                boolean exists = (new File(fileLocation).exists());
                boolean write = true;
                if (exists){
                    int n = JOptionPane.showConfirmDialog(null, "The file already exists in this directory, do you want to overwrite it?", "Information", JOptionPane.YES_NO_OPTION);
                    if(n==JOptionPane.NO_OPTION){
                        write = false;
                    }
                }
                if (write){
                    ExcelExporter exp = new ExcelExporter();
                    exp.fillData(jtProteinQuant, new File(fileLocation), true, 0);
                    JOptionPane.showMessageDialog(this, "Data saved at "+fileLocation, "Information", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } 
       }
       else{
           JOptionPane.showMessageDialog(this, "No data to export", "Error", JOptionPane.INFORMATION_MESSAGE);
       }            
    }//GEN-LAST:event_jbExportProtMZQExcelMouseClicked

    private void jtFeatureMZQKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtFeatureMZQKeyPressed
        String sChain = "";
        sChain = jtFeatureMZQ.getText() + evt.getKeyChar();
        searchValueInMZQFeat(sChain, 0);
    }//GEN-LAST:event_jtFeatureMZQKeyPressed

    private void jbExportFeatMZQExcelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbExportFeatMZQExcelMouseClicked
       if (this.jtFeatureQuant.getRowCount()>0){
        try {           
            JFileChooser chooser = new JFileChooser(sPreviousLocation);
            chooser.setDialogTitle("Please type the file name");

            //... Applying file extension filters ...//
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Files (*.xls)", "xls");
            //... Filters must be in descending order ...//
            chooser.addChoosableFileFilter(filter);

            //... Setting default directory ...//
            if (sPreviousLocation == null || sPreviousLocation.contains("")){
                chooser.setCurrentDirectory(new java.io.File(sWorkspace)); //... If not found it goes to Home, exception not needed ...//   
            }
            else{
                chooser.setCurrentDirectory(new java.io.File(sPreviousLocation)); //... If not found it goes to Home, exception not needed ...//               
            }

            int returnVal = chooser.showSaveDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION){     
                File file = chooser.getSelectedFile();
                int mid= 0;
                mid = file.getName().lastIndexOf(".xls");
                String fileLocation = "";
                if(mid<=0){
                    fileLocation = file.getPath()+".xls";
                }else{
                    fileLocation = file.getPath();
                }
                fileLocation.replace("\\", "/");
                
                //... Check if file exists ...//
                boolean exists = (new File(fileLocation).exists());
                boolean write = true;
                if (exists){
                    int n = JOptionPane.showConfirmDialog(null, "The file already exists in this directory, do you want to overwrite it?", "Information", JOptionPane.YES_NO_OPTION);
                    if(n==JOptionPane.NO_OPTION){
                        write = false;
                    }
                }
                if (write){
                    ExcelExporter exp = new ExcelExporter();
                    exp.fillData(jtFeatureQuant, new File(fileLocation), true, 0);
                    JOptionPane.showMessageDialog(this, "Data saved at "+fileLocation, "Information", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } 
       }
       else{
           JOptionPane.showMessageDialog(this, "No data to export", "Error", JOptionPane.INFORMATION_MESSAGE);
       }          
    }//GEN-LAST:event_jbExportFeatMZQExcelMouseClicked

    private void jbExportMGFExcelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbExportMGFExcelMouseClicked
       if (this.jtMGF.getRowCount()>0){
        try {           
            JFileChooser chooser = new JFileChooser(sPreviousLocation);
            chooser.setDialogTitle("Please type the file name");

            //... Applying file extension filters ...//
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Files (*.xls)", "xls");
            //... Filters must be in descending order ...//
            chooser.addChoosableFileFilter(filter);

            //... Setting default directory ...//
            if (sPreviousLocation == null || sPreviousLocation.contains("")){
                chooser.setCurrentDirectory(new java.io.File(sWorkspace)); //... If not found it goes to Home, exception not needed ...//   
            }
            else{
                chooser.setCurrentDirectory(new java.io.File(sPreviousLocation)); //... If not found it goes to Home, exception not needed ...//               
            }

            int returnVal = chooser.showSaveDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION){     
                File file = chooser.getSelectedFile();
                int mid= 0;
                mid = file.getName().lastIndexOf(".xls");
                String fileLocation = "";
                if(mid<=0){
                    fileLocation = file.getPath()+".xls";
                }else{
                    fileLocation = file.getPath();
                }
                fileLocation.replace("\\", "/");
                
                //... Check if file exists ...//
                boolean exists = (new File(fileLocation).exists());
                boolean write = true;
                if (exists){
                    int n = JOptionPane.showConfirmDialog(null, "The file already exists in this directory, do you want to overwrite it?", "Information", JOptionPane.YES_NO_OPTION);
                    if(n==JOptionPane.NO_OPTION){
                        write = false;
                    }
                }
                if (write){
                    ExcelExporter exp = new ExcelExporter();
                    exp.fillData(jtMGF, new File(fileLocation), true, 0);
                    JOptionPane.showMessageDialog(this, "Data saved at "+fileLocation, "Information", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } 
       }
       else{
           JOptionPane.showMessageDialog(this, "No data to export", "Error", JOptionPane.INFORMATION_MESSAGE);
       }        
    }//GEN-LAST:event_jbExportMGFExcelMouseClicked

    private void jbExportMzIdentMLExcelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbExportMzIdentMLExcelMouseClicked
       if (this.jtMzId.getRowCount()>0){
        try {           
            JFileChooser chooser = new JFileChooser(sPreviousLocation);
            chooser.setDialogTitle("Please type the file name");

            //... Applying file extension filters ...//
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Files (*.xls)", "xls");
            //... Filters must be in descending order ...//
            chooser.addChoosableFileFilter(filter);

            //... Setting default directory ...//
            if (sPreviousLocation == null || sPreviousLocation.contains("")){
                chooser.setCurrentDirectory(new java.io.File(sWorkspace)); //... If not found it goes to Home, exception not needed ...//   
            }
            else{
                chooser.setCurrentDirectory(new java.io.File(sPreviousLocation)); //... If not found it goes to Home, exception not needed ...//               
            }

            int returnVal = chooser.showSaveDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION){     
                File file = chooser.getSelectedFile();
                int mid= 0;
                mid = file.getName().lastIndexOf(".xls");
                String fileLocation = "";
                if(mid<=0){
                    fileLocation = file.getPath()+".xls";
                }else{
                    fileLocation = file.getPath();
                }
                fileLocation.replace("\\", "/");
                
                //... Check if file exists ...//
                boolean exists = (new File(fileLocation).exists());
                boolean write = true;
                if (exists){
                    int n = JOptionPane.showConfirmDialog(null, "The file already exists in this directory, do you want to overwrite it?", "Information", JOptionPane.YES_NO_OPTION);
                    if(n==JOptionPane.NO_OPTION){
                        write = false;
                    }
                }
                if (write){
                    ExcelExporter exp = new ExcelExporter();
                    exp.fillData(jtMzId, new File(fileLocation), true, 0);
                    JOptionPane.showMessageDialog(this, "Data saved at "+fileLocation, "Information", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } 
       }
       else{
           JOptionPane.showMessageDialog(this, "No data to export", "Error", JOptionPane.INFORMATION_MESSAGE);
       }
    }//GEN-LAST:event_jbExportMzIdentMLExcelMouseClicked

    private void jmRunIdentAnalysisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmRunIdentAnalysisActionPerformed
                                    
        //... Check if there is a valid workspace ...//        
        boolean bRun = false;
        boolean isOK = getWorkspace();      
        identParamsExecute.setWorkspace(sWorkspace);
        if (isOK){
            //... Validate if we got raw and identification files ...//        
            if (jtRawFiles.getRowCount()<=0){
                JOptionPane.showMessageDialog(this, "Please select at least one raw file. (Use the import file option in the menu).", "Error", JOptionPane.ERROR_MESSAGE);
            } 
            else{      
                if (dialogIdentParamsExecute == null) {
                    dialogIdentParamsExecute = new JDialog(this, "Set Identification Parameters", ModalityType.APPLICATION_MODAL);
                    KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0, false);
                    final Action escapeAction = new AbstractAction() {
                        public void actionPerformed(ActionEvent e) {
                            identParamsExecute.setRun(false);
                            dialogIdentParamsExecute.dispose();
                        }
                    };
                    dialogIdentParamsExecute.addWindowListener(new WindowAdapter(){
                        @Override
                         public void windowClosing(WindowEvent e){ 
                             escapeAction.actionPerformed(null);}
                         });        

                    dialogIdentParamsExecute.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
                    dialogIdentParamsExecute.getRootPane().getActionMap().put("ESCAPE", escapeAction);                    
                    dialogIdentParamsExecute.getContentPane().add(identParamsExecute);
                    dialogIdentParamsExecute.pack();
                    dialogIdentParamsExecute.setLocationRelativeTo(null);
                }
                dialogIdentParamsExecute.setVisible(true);
                                                
                //... Get values from form ...//
                bRun = identParamsExecute.getRun();
                if (bRun){                    
                    //... Start process ...//
                    
                    //... In this thread, we call MSGF module (parameter files are generated using proteosuite) ...//
                    final ProgressBarDialog progressBarDialog = new ProgressBarDialog(this, true, "RunIdentification");
                    final Thread thread = new Thread(new Runnable(){
                        @Override
                        public void run(){
                            //... Progress Bar ...//
                            progressBarDialog.setTitle("Running Identification Analysis via MSGF+");
                            progressBarDialog.setTaskName("This task may take up to several minutes. Please wait...");
                            progressBarDialog.setVisible(true);
                        }
                    }, "ProgressBarDialog");                
                    thread.start();

                    new Thread("RunIdentification"){
                        @Override
                        public void run(){
                            System.out.println(sysutils.getTime()+" - ****** Running MSGF+ (Ver "+MSGFPlus.VERSION+") *****");
                            jtaLog.append("\n"+sysutils.getTime()+" - ****** Running MSGF+ (Ver "+MSGFPlus.VERSION+") *****");

                            ParamManager paramManager = new ParamManager("MS-GF+", MSGFPlus.VERSION, MSGFPlus.RELEASE_DATE, "java -Xmx"+sysutils.getMaxMemory()+"M -jar MSGFPlus.jar");
                            paramManager.addMSGFPlusParams();

                            Map<String, String> map = new HashMap();
                            map = identParamsExecute.getParams();
                            
                            for(String param : map.keySet()){
                                System.out.println("MSGF param:" + param + " value:" + map.get(param));
                            }
                            
                            
                            boolean bProteinInference = identParamsExecute.getProteinInference();
                            String sRegex = identParamsExecute.getRegex();
                            String sFirstFile = "";
                            
                            for(int iFile=0; iFile<jtRawFiles.getRowCount(); iFile++){
                                progressBarDialog.setTitle("Running MSGF+ (Ver "+MSGFPlus.VERSION+") "+"("+(iFile+1)+"/"+jtRawFiles.getRowCount()+") - "+jtRawFiles.getValueAt(iFile, 0).toString());
                                map.put("-s", jtRawFiles.getValueAt(iFile, 1).toString());
                                File file = new File(jtRawFiles.getValueAt(iFile, 1).toString());
                                String fileName = file.getName();
                                fileName = fileName.replace("\\", "/");
                                String fileExtension = "";
                                String Path = "";
                                int iIndex = fileName.lastIndexOf('.');
                                if (iIndex > 0) {
                                    fileExtension = fileName.substring(iIndex);
                                }
                                fileName = fileName.replace(fileExtension, "_msgfplus.mzid");
                                map.put("-o", file.getParent().replace("\\", "/") +"/"+fileName);
                                System.out.println(file.getParent().replace("\\", "/")+"/"+fileName);
                                Path = file.getParent().replace("\\", "/")+"/"+fileName;
                                if (iFile==0){
                                    sFirstFile = file.getParent().replace("\\", "/")+"/"+fileName;
                                }
                                String[] argv = new String[map.size()*2];
                                int iI=0;
                                for (Map.Entry<String, String> entry : map.entrySet()) {
                                    argv[iI] = entry.getKey();
                                    System.out.print(" " + argv[iI]);
                                    argv[iI+1] = entry.getValue();
                                    System.out.print(" " + argv[iI+1]);
                                    iI = iI+2;
                                }

                                if(argv.length == 0){
                                    paramManager.printUsageInfo();
                                    return;
                                }
                                MzMLAdapter.turnOffLogs();

                                //... Parse parameters ...//
                                String errMessage = paramManager.parseParams(argv); 
                                if(errMessage != null){
                                    System.err.println(sysutils.getTime()+" - [Error] " + errMessage);
                                    System.out.println();
                                    paramManager.printUsageInfo();
                                    return;
                                }

                                //... Running MS-GF+ ...//
                                paramManager.printToolInfo();
                                MSGFPlus run = new MSGFPlus();
                                String errorMessage = null;
                                try {
                                    errorMessage = run.runMSGFPlus(paramManager);
                                }
                                catch(Exception e){
                                    e.printStackTrace();
                                    System.exit(-1);
                                }
                                if(errorMessage != null){
                                    System.err.println(sysutils.getTime()+" - [Error] " + errorMessage);
                                    jtaLog.append("\n"+sysutils.getTime()+" - [Error] " + errorMessage);
                                }
                                else{
                                    System.out.println(sysutils.getTime()+" - MS-GF+ completed");
                                    jtaLog.append("\n"+sysutils.getTime()+" - MS-GF+ completed");
                                }
                                if(bProteinInference){
                                    System.out.println(sysutils.getTime()+" - Performing protein inference");
                                    jtaLog.append("\n"+sysutils.getTime()+" - Performing protein inference");

                                    MzIdentMLLib mzlib = new MzIdentMLLib();
                                    String[] args = {"FalseDiscoveryRate", Path, Path.replace(".mzid", "_fdr.mzid"), "-decoyRegex", sRegex, "-decoyValue", "1", "-cvTerm", "\"MS:1002053\"", "-betterScoresAreLower", "true", "-compress", "false"};
                                    try {                                        
                                        System.out.println(sysutils.getTime()+" - Performing FDR");
                                        jtaLog.append("\n"+sysutils.getTime()+" - Performing FDR");
                                        mzlib.init(args);
                                        System.out.println(sysutils.getTime()+" - Setting Threshold");
                                        jtaLog.append("\n"+sysutils.getTime()+" - Setting Threshold");
                                        String[] args2 = {"Threshold", Path.replace(".mzid", "_fdr.mzid"), Path.replace(".mzid", "_fdr_thresh.mzid"), "-isPSMThreshold", "true", "-cvAccessionForScoreThreshold", "MS:1001874", "-threshValue", "0.01", "-betterScoresAreLower", "true", "-deleteUnderThreshold", "false", "-compress", "false"};
                                        mzlib.init(args2);
                                        System.out.println(sysutils.getTime()+" - Grouping proteins");
                                        jtaLog.append("\n"+sysutils.getTime()+" - Grouping proteins");
                                        sFirstFile = Path.replace(".mzid", "_fdr_thresh_group.mzid");
                                        String[] args3 = {"ProteoGrouper", Path.replace(".mzid", "_fdr_thresh.mzid"), sFirstFile, "-requireSIIsToPassThreshold", "true", "-verboseOutput", "false", "-cvAccForSIIScore", "MS:1001874", "-logTransScore", "true", "-compress", "false"};
                                        System.out.print("Command line arguments for proteogrouper:");
                                        
                                       
                                        for(String arg : args3){
                                            System.out.print(arg + " ");
                                        }
                                        mzlib.init(args3);
                                        
                                    } catch (Exception ex) {
                                        Logger.getLogger(ProteoSuiteView.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }
                            if (!sFirstFile.equals("")){
                                final DefaultTableModel model = new DefaultTableModel();
                                jtIdentFiles.setModel(model);
                                model.addColumn("Name");
                                model.addColumn("Path");
                                model.addColumn("Type");
                                model.addColumn("Version");
                                model.addColumn("Raw File");
                                File xmlFile = new File(sFirstFile);
                                unmarshalMzIDFile(model, xmlFile, "");
                                loadMzIdentMLView(0, xmlFile.getName());
                                renderIdentFiles();
                                uptStatusPipeline();
                            }
                            progressBarDialog.setVisible(false);
                            progressBarDialog.dispose();
                        }
                    }.start();
                }                
            }
        }
    }//GEN-LAST:event_jmRunIdentAnalysisActionPerformed

    private void jbRunIdentAnalysisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbRunIdentAnalysisMouseClicked
        //... Call the identification method ...//
        jmRunIdentAnalysisActionPerformed(null);
    }//GEN-LAST:event_jbRunIdentAnalysisMouseClicked

    private void jmCheckUpdatesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmCheckUpdatesActionPerformed
        String sURL = "http://www.proteosuite.org/datasets/D-000-PublicFiles/updates.xml";
        boolean bURL = sysutils.CheckURL(sURL);
        if (bURL){
            //... Read files using XPath xml parser ...//
            try{
                DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
                domFactory.setNamespaceAware(true); 
                DocumentBuilder builder = domFactory.newDocumentBuilder();
                Document doc = builder.parse(sURL);
                XPath xpath = XPathFactory.newInstance().newXPath();

                //... Reading the version ...//
                XPathExpression expr = xpath.compile("/ProteoSuite");
                String ver = "";
                NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
                for (int iI = 0; iI < nodes.getLength(); iI++) {
                    Node node = nodes.item(iI);
                    if (node.getNodeType() == Node.ELEMENT_NODE){

                        Element element = (Element) node;
                        NodeList nodelist = element.getElementsByTagName("version");
                        Element element1 = (Element) nodelist.item(0);
                        NodeList fstNm = element1.getChildNodes();                    
                        if (fstNm.getLength()<=0){
                            ver = "";
                        }
                        else{
                            ver = fstNm.item(0).getNodeValue();
                        }
                    }
                }
                if (sPS_Version.equals(ver)){
                    JOptionPane.showMessageDialog(this, "Your ProteoSuite version is up to date!!!", "Information", JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                    JOptionPane.showMessageDialog(this, 
                            "There is a new version of ProteoSuite available at http://code.google.com/p/proteo-suite/\n Update this manually. We will have an automatic download soon.", 
                            "Information", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch ( XPathExpressionException  e) {
                e.printStackTrace();
            }           
        }
        else{
            JOptionPane.showMessageDialog(this, "Unable to connect. Check that you have internet connection or \nupdate your version manually (http://code.google.com/p/proteo-suite/)", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jmCheckUpdatesActionPerformed

    private void jbExportMascotXMLExcelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbExportMascotXMLExcelMouseClicked
       if (this.jtMascotXMLView.getRowCount()>0){
        try {           
            JFileChooser chooser = new JFileChooser(sPreviousLocation);
            chooser.setDialogTitle("Please type the file name");

            //... Applying file extension filters ...//
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Files (*.xls)", "xls");
            //... Filters must be in descending order ...//
            chooser.addChoosableFileFilter(filter);

            //... Setting default directory ...//
            if (sPreviousLocation == null || sPreviousLocation.contains("")){
                chooser.setCurrentDirectory(new java.io.File(sWorkspace)); //... If not found it goes to Home, exception not needed ...//   
            }
            else{
                chooser.setCurrentDirectory(new java.io.File(sPreviousLocation)); //... If not found it goes to Home, exception not needed ...//               
            }

            int returnVal = chooser.showSaveDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION){     
                File file = chooser.getSelectedFile();
                int mid= 0;
                mid = file.getName().lastIndexOf(".xls");
                String fileLocation = "";
                if(mid<=0){
                    fileLocation = file.getPath()+".xls";
                }else{
                    fileLocation = file.getPath();
                }
                fileLocation.replace("\\", "/");
                
                //... Check if file exists ...//
                boolean exists = (new File(fileLocation).exists());
                boolean write = true;
                if (exists){
                    int n = JOptionPane.showConfirmDialog(null, "The file already exists in this directory, do you want to overwrite it?", "Information", JOptionPane.YES_NO_OPTION);
                    if(n==JOptionPane.NO_OPTION){
                        write = false;
                    }
                }
                if (write){
                    ExcelExporter exp = new ExcelExporter();
                    exp.fillData(jtMascotXMLView, new File(fileLocation), true, 0);
                    JOptionPane.showMessageDialog(this, "Data saved at "+fileLocation, "Information", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } 
       }
       else{
           JOptionPane.showMessageDialog(this, "No data to export", "Error", JOptionPane.INFORMATION_MESSAGE);
       }       
    }//GEN-LAST:event_jbExportMascotXMLExcelMouseClicked

    private void jcbPSMItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcbPSMItemStateChanged

    }//GEN-LAST:event_jcbPSMItemStateChanged

    private void jcbPSMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbPSMActionPerformed
        //... Check file to visualize .../
        if(!jlFileNameMzIDText.equals("")){
            int iIndex = getMzMLIndex(jlFileNameMzIDText.getText());
            loadMzIdentMLView(iIndex, jlFileNameMzIDText.getText());
        }
    }//GEN-LAST:event_jcbPSMActionPerformed
    private int getMzMLIndex(String sFileName){
        int iIndex = 0;
        for(int iI=0; iI<jtIdentFiles.getRowCount(); iI++){
            if(jtIdentFiles.getValueAt(iI, 0).toString().equals(sFileName)){
                iIndex = iI;
                break;
            }
        }
        return iIndex;
    }
            
    /*--------------------------------------------------------------
     * Mapping identification files with the corresponding raw file 
     * @param void
     * @return void
     ---------------------------------------------------------------*/
    private void renderIdentFiles(){
        //... Rendering ...//
        JComboBox combo = new JComboBox();
        for(int iI=0; iI<jtRawFiles.getRowCount(); iI++){
            combo.addItem(jtRawFiles.getValueAt(iI, 0).toString());
        }
        TableColumn column = jtIdentFiles.getColumnModel().getColumn(4);
        column.setCellEditor(new DefaultCellEditor(combo));
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("Please specify the associated raw file");
        column.setCellRenderer(renderer);
        DefaultTableModel model = (DefaultTableModel)jtIdentFiles.getModel();
        model.fireTableDataChanged();
    }
    /*------------------------------------------------------------------
     * Check if all identification files are associated to one raw file
     * @param void
     * @return true/false
     -------------------------------------------------------------------*/
    private boolean checkMapping()
    {
        //... Check if all identification files have been assigned to one raw file ...//
        System.out.println(sysutils.getTime()+" - Checking identification-spectra mapping");
        jtaLog.append(sysutils.getTime()+" - Checking identification-spectra mapping");
        jtpLog.setSelectedIndex(0);
        for(int iI=0; iI<jtIdentFiles.getRowCount(); iI++){            
            if(jtIdentFiles.getValueAt(iI, 4).equals("")){
                JOptionPane.showMessageDialog(this, "Please select a raw file for each identification file.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }    
    /*----------------------------------
     * Update save icon
     * @param void
     * @return void
     -----------------------------------*/    
    private void uptSaveProjectStatus()            
    {
        jmSaveProject.setEnabled(bProjectModified);
        jbSaveProject.setEnabled(bProjectModified);
    }
    /*----------------------------------
     * Update close project icon
     * @param void
     * @return void
     -----------------------------------*/    
    private void uptCloseProjectStatus()
    {
        if(sProjectName.equals("New")){
            jmCloseProject.setEnabled(false);
        }else{
            jmCloseProject.setEnabled(true);
        }
    }
    /*----------------------------------
     * Save a project using a mzq file
     * @param void
     * @return true/false
     -----------------------------------*/    
    private boolean saveProject()            
    {
        if (jtRawFiles.getRowCount()<=0){
            JOptionPane.showMessageDialog(this, "Please select at least one raw file. (Use the import file option in the menu).", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } 
        else if (jtIdentFiles.getRowCount()<=0){
            JOptionPane.showMessageDialog(this, "Please select at least one identification file. (Use the import file option in the menu).", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }else if (jcbTechnique.getSelectedItem().equals("Select technique")){
            JOptionPane.showMessageDialog(this, "Please specify the technique to save your pipeline (e.g. iTRAQ, SILAC, 15N, etc.)", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        JFileChooser chooser = new JFileChooser(sPreviousLocation);
        chooser.setDialogTitle("Please enter the name of your project");

        //... Applying file extension filters ...//
        FileNameExtensionFilter filter3 = new FileNameExtensionFilter("mzQuantML Files (*.mzq)", "mzq");
        //... Filters must be in descending order ...//
        chooser.addChoosableFileFilter(filter3);

        //... Setting default directory ...//
        if (sPreviousLocation == null || sPreviousLocation.contains("")){
            chooser.setCurrentDirectory(new java.io.File(sWorkspace)); //... If not found it goes to Home, exception not needed ...//   
        }
        else{
            chooser.setCurrentDirectory(new java.io.File(sPreviousLocation)); //... If not found it goes to Home, exception not needed ...//               
        }
        
        int returnVal = chooser.showSaveDialog(this);
        System.out.println(returnVal);
        if(returnVal == JFileChooser.APPROVE_OPTION){
            //... Check extension ...//
            File file = chooser.getSelectedFile();
            int mid= 0;
            mid = file.getName().lastIndexOf(".mzq");
            String fileLocation = "";
            if(mid<=0){
                fileLocation = file.getPath()+".mzq";
            }else{
                fileLocation = file.getPath();
            }
            System.out.println("Creating file="+fileLocation);
            fileLocation.replace("\\", "/");
            File file2 = new File(fileLocation);
            
            //... Check if file exists ...//
            boolean exists = (new File(file2.getPath())).exists();
            if (exists){
                int n = JOptionPane.showConfirmDialog(null, "The file already exists in this directory, do you want to overwrite it?", "Information", JOptionPane.YES_NO_OPTION);
                if(n==JOptionPane.NO_OPTION){
                    return false;
                }
            }
            System.out.println("Saving mzq file...");
            boolean bOK = writeMzQuantML(jcbTechnique.getSelectedItem().toString(), file2.getName());
            if(bOK){
                sProjectName = file2.getName();   
                uptTitle();
                bProjectModified = false;
                uptSaveProjectStatus();
                uptCloseProjectStatus();
            }
        }
        
        return true;
    }      
    /*----------------------------------
     * Update window title main project
     * @param void
     * @return void
     -----------------------------------*/    
    private void uptTitle()            
    {
        setTitle("ProteoSuite " + sPS_Version + " (Beta Version) - <Project: " + sWorkspace + " - " + sProjectName +  ">         http://www.proteosuite.org");
    }
    /*-------------------------
     * Update status pipeline
     * @param void  
     * @return void
     -------------------------*/
    private void uptStatusPipeline()
    {
        Icon loadRawFilesIcon = new ImageIcon(getClass().getClassLoader().getResource("images/fill.gif"));        
        if(jtRawFiles.getRowCount()>0){            
            jlRawFilesStatus.setIcon(loadRawFilesIcon);
        }
        if(jtIdentFiles.getRowCount()>0){            
            jlIdentFilesStatus.setIcon(loadRawFilesIcon);
        }
    }
    /*---------------------------------------
     * Checks if the working space is valid
     * @param void
     * @return true/false
     ----------------------------------------*/
    private boolean getWorkspace()
    {
        //... Validate if config file exists ...//
        boolean exists = (new File("config.xml")).exists();
        if (exists){
            //... Read files using SAX (get workspace) ...//
            try {
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser saxParser = factory.newSAXParser();

                DefaultHandler handler = new DefaultHandler() {                   
                    boolean bWorkspace = false;
                    
                    @Override
                    public void startElement(String uri, String localName, String qName, Attributes attributes)
                    throws SAXException {
                        if (qName.equalsIgnoreCase("workspace")) {
                            bWorkspace = true;
                        }
                    }
                    @Override
                    public void characters(char ch[], int start, int length)
                    throws SAXException {
                        if (bWorkspace) {
                            bWorkspace = false;
                            sWorkspace = new String(ch, start, length);                            
                        }
                    }
                };
                saxParser.parse("config.xml", handler);
            } 
            catch (Exception e) {
              e.printStackTrace();
            }         
            //... Check if default workspace is valid ...//
            exists = (new File(sWorkspace)).exists();
            if (!exists){
                JOptionPane.showMessageDialog(this, "The default \"workspace\" does not exist. Please set up your directory in \"Tools\"->\"Options\" ", "Error", JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
        }
        else{            
            return false;
        }
        return true;
    }     
    /*--------------------------------------------
     * Generate template for Label Free Analysis
     * @param scanIndex1 - Scan number e.g. 1671
     * @param scanIndex2 - Scan number e.g. 1672
     * @param mzStarts - m/z starts from
     * @param mzEnds - m/z ends at
     * @param resolution - peaks left-right
     * @return - void
     ---------------------------------------------*/
    private void generateTemplate(int scanIndex1, int scanIndex2, double mzStarts, double mzEnds, int peaks)     
    {
        int iFileIndex = 0; //... Index to mzML raw data ...//
        DefaultTableModel model = new DefaultTableModel();
        DefaultTableModel model2 = new DefaultTableModel();
        jtTemplate1.setModel(model);
        jtTemplate2.setModel(model2);        
        model.addColumn("m/z Index");
        model.addColumn("Scan Index");
        model.addColumn("Quant");
        model.addColumn("Template2 Index");        
        model2.addColumn("Template Index");
        model2.addColumn("x");
        model2.addColumn("y");
        model2.addColumn("i");
        
        System.out.println("Starting label free plugin...");
                
        //... 1) Select MS/MS identifications that are specified in the parameter window (e.g. From scan 1671 to 1672)  ...//
        Map<String, ArrayList<String>> hmPeptides = new HashMap<String, ArrayList<String>>();
        int iCountPeptides = 0; //... Peptide counter ...//
        boolean blnExists = false; 
        for(int iI=0; iI<jtMascotXMLView.getRowCount(); iI++){
            //... Range based on scan index window ...//
            if ((scanIndex1<=Integer.parseInt(jtMascotXMLView.getValueAt(iI, 8).toString()))&&(scanIndex2>=Integer.parseInt(jtMascotXMLView.getValueAt(iI, 8).toString()))){
                ArrayList al = new ArrayList();
                al.add(Integer.toString(iI));                           //... Index in the grid ...//
                al.add(jtMascotXMLView.getValueAt(iI, 3).toString());   //... 2) Peptide molecular composition which was calculated previously ...//
                al.add(jtMascotXMLView.getValueAt(iI, 8).toString());   //... Scan index ...//
                al.add(jtMascotXMLView.getValueAt(iI, 9).toString());   //... Scan ID (jmzml API only supports getElementBy(scanID) ...//
                al.add(jtMascotXMLView.getValueAt(iI, 10).toString());  //... Retention time ...//
                //... Check if peptide has been added previously ...//
                blnExists = hmPeptides.containsKey(jtMascotXMLView.getValueAt(iI, 2).toString());
                if (blnExists == false){
                    hmPeptides.put(jtMascotXMLView.getValueAt(iI, 2).toString(), al); //... This eliminates peptides which have been identified more than once (e.g. for different proteins) ...//
                    iCountPeptides++; //... Total peptides (identifications) in the scan range ...//
                }
            }
        }
        if (iCountPeptides > 0){            
            System.out.println("Peptides in range = " + iCountPeptides);
            
            //... Determine the length (resolution) of the resampling array. ...//
            Map<String, float[][]> hmResamplingArray = new HashMap<String, float[][]>();
            int mzWindow = (int)(mzEnds-mzStarts);
            System.out.println("mzWindow="+mzWindow);
            float resamplingFactor = 1.0f/120.0f; //... e.g. 0.008333334 Daltons ...//
            System.out.println("Resampling factor="+resamplingFactor);
            float lengthArray = Round(mzWindow/resamplingFactor, 1);
            System.out.println("length="+lengthArray);
            final int MZ_SIZE = (int) lengthArray;
            System.out.println("SIZE="+MZ_SIZE);
            float resolution = (float)Truncate((Round((float)(resamplingFactor), 8)), 8); 
            System.out.println("resolution="+resolution);
            float[][] resamplingArray = new float[2][MZ_SIZE];            
            for (int iI=0; iI<MZ_SIZE; iI++){                
                resamplingArray[0][iI] = (float)mzStarts + (resolution*iI);
                resamplingArray[1][iI] = 0;
            }
            Number[] mzSyntValues = new Number[MZ_SIZE];
            for(int iX=0; iX<resamplingArray[0].length; iX++){
                mzSyntValues[iX] = resamplingArray[0][iX];
            }

            final int PEAKS = peaks; //... Number of peaks to retrieve (width of the template, e.g. 4 left, 4 right) ...//
            final int NUMBER_PEAKS = PEAKS*2+1; //... Number of peaks for each isotope ...//
            int[] aPeakIndexes = new int[PEAKS*2+1];
            
            System.out.println("PEAKS_PARAM="+PEAKS);
            System.out.println("TOTAL_PEAKS="+NUMBER_PEAKS);

            //.. Define templates (A and C) ...//
            int iTempAIndex = 0; //... Index for Template A ...//
            int iQuantTemplateIndex = 0; //... Index for Template C ...//
            
            psTemplate[] templates = new psTemplate[iCountPeptides];
            psTemplateQuant[] quantTemplate = new psTemplateQuant[iCountPeptides*NUMBER_PEAKS];
            //psTemplateQuant2[] quantTemplate2 = new psTemplateQuant2[iCountPeptides*NUMBER_PEAKS]; //... This will be the final template once the resampling is done ...//

            //... Initialize arrays via their constructors (TemplateA=templates and TemplateC=templateQuant) ...//
            for(int iI=0; iI<templates.length; iI++){
                templates[iI] = new psTemplate();
            }
            for(int iI=0; iI<quantTemplate.length; iI++){
                quantTemplate[iI] = new psTemplateQuant();            
            }

            //... 3) For each peptide, calculate the isotopic pattern distribution ...//
            String sScanID = "";        
            int iScanIndex = 0;
            for (Map.Entry<String, ArrayList<String>> entry : hmPeptides.entrySet()){
                System.out.println("Calculating the Isotopic Patter Distribution (IPD) for peptide " + entry.getKey());

                //... Specify parameters ...//
                ArrayList<String> saParams = entry.getValue();
                Object saArray[] = saParams.toArray();
                Iterator<String> itr = saParams.iterator();
                while (itr.hasNext()){ //... Show the parameters in the array list (index, molecular composition, scanNumber, scanID, rt) ...//                
                    System.out.println("Param = " + itr.next().toString() + "; "); 
                }
                //... Using IPC to calculate the isotopic pattern distribution ...//
                String[] args = {};
                String sCharge = jtMascotXMLView.getValueAt(Integer.parseInt(saArray[0].toString()), 6).toString();
                args = new String[]{"-a", entry.getKey().toString(), 
                    "-fc", "-z", sCharge, 
                    "-f", "1000", "-r", "10000"};
                if (args.length == 0) {
                    System.exit(0);
                }
                IPC ipc = new IPC();                 
                Options options = Options.parseArgs(args);
                options.setPrintOutput(false);
                Results res = ipc.execute(options);
                Object[] objArray = res.getPeaks().toArray();     
                float[] aMz = new float[objArray.length];           //... There will be six isotopes estimated using the IPC class ...//
                float[] aRelIntens = new float[objArray.length];     
                System.out.println("==== IPD ====");
                for(int iI=0; iI<objArray.length; iI++){
                    aMz[iI] = Float.parseFloat(String.format("%.4f", res.getPeaks().first().getMass()));
                    aRelIntens[iI] = Float.parseFloat(String.format("%.4f", res.getPeaks().first().getRelInt()*100));        
                    System.out.println(aMz[iI] + "\t" + aRelIntens[iI]);
                    res.getPeaks().pollFirst();
                }

                //... 4) Generate templates for seaMass by getting the m/z indexes for each isotope ...//                                                
                MzMLUnmarshaller unmarshaller = aMzMLUnmarshaller.get(iFileIndex);
                try{
                    //... 4.1) Get precursor ion (from 1671 would be 1666 on the CTPAC example) ...//
                    sScanID = saArray[3].toString();
                    iScanIndex = Integer.parseInt(saArray[2].toString());
                    Spectrum spectrum = unmarshaller.getSpectrumById(sScanID);
                    PrecursorList plist = spectrum.getPrecursorList(); //... Get precursor ion ...//
                    if (plist != null){
                        if (plist.getCount().intValue() == 1){

                            //... Perform a binary search to find the m/z index closed to the m/z values estimated from the IPC ...//
                            int iPos = -1, iPosPrev = -1;
                            iPos = binarySearch(mzSyntValues, 0, mzSyntValues.length, aMz[0], true);
                            aPeakIndexes = getPeaks(mzSyntValues, iPos, PEAKS); 

                            //... Generate psTemplate (index, {x, y, i}) ...//
                            templates[iTempAIndex].setIndex(iTempAIndex);
                            templates[iTempAIndex].setCoords(0, aRelIntens[0], 0); //... aRelIntens[0] contains the highest value i.e. 100% ...//

                            //... Generate psTemplateQuant (mzIndex, scanIndex, quant, temp2Index) ...//
                            for (int iI=0; iI<NUMBER_PEAKS; iI++){
                                quantTemplate[iI+iQuantTemplateIndex].setTemplate(
                                              aPeakIndexes[iI],                                         //... mzIndex ...//
                                              plist.getPrecursor().get(0).getSpectrumRef().toString(),  //... scan ID ...//
                                              iScanIndex,                                               //... scanIndex ...//
                                              1,                                                        //... Initial quant value ...//
                                              iTempAIndex);                                             //... Index to template A ...//
                            }
                            iQuantTemplateIndex+=NUMBER_PEAKS; //... Skip 9 positions (2*4+1) ..../
                            iPosPrev = iPos;

                            iPos = binarySearch(mzSyntValues, 0, mzSyntValues.length, aMz[1], true); 
                            aPeakIndexes = getPeaks(mzSyntValues, iPos, PEAKS);
                            templates[iTempAIndex].setCoords(iPos - iPosPrev, aRelIntens[1], 1);

                            iPos = binarySearch(mzSyntValues, 0, mzSyntValues.length, aMz[2], true);
                            aPeakIndexes = getPeaks(mzSyntValues, iPos, PEAKS);
                            templates[iTempAIndex].setCoords(iPos - iPosPrev, aRelIntens[2], 2);

                            iPos = binarySearch(mzSyntValues, 0, mzSyntValues.length, aMz[3], true);
                            aPeakIndexes = getPeaks(mzSyntValues, iPos, PEAKS);
                            templates[iTempAIndex].setCoords(iPos - iPosPrev, aRelIntens[3], 3);

                            iPos = binarySearch(mzSyntValues, 0, mzSyntValues.length, aMz[4], true);
                            aPeakIndexes = getPeaks(mzSyntValues, iPos, PEAKS);
                            templates[iTempAIndex].setCoords(iPos - iPosPrev, aRelIntens[4], 4);

                            iPos = binarySearch(mzSyntValues, 0, mzSyntValues.length, aMz[5], true);
                            aPeakIndexes = getPeaks(mzSyntValues, iPos, PEAKS);
                            templates[iTempAIndex].setCoords(iPos - iPosPrev, aRelIntens[5], 5);
                            iTempAIndex++;
                        }
                    }  //... If precursor ion ...//
                    jtpLog.setSelectedIndex(2);
                }
                catch(MzMLUnmarshallerException e){
                    System.out.println(e);
                }            
            } //... From For ...//
            
            //... Perform normalisation ...//
            float fSum=0.0f;
            float fNewValue=0.0f;
            for(int iI=0; iI<templates.length; iI++){
                for (int iJ=0; iJ<templates[iI].getCoords().length; iJ++){                                
                    fSum+=Math.pow(templates[iI].getCoord(iJ).getRelIntensity(), 2.0);  
                }
                for (int iJ=0; iJ<templates[iI].getCoords().length; iJ++){                                
                    fNewValue = templates[iI].getCoord(iJ).getRelIntensity()/fSum;  
                    templates[iI].setCoords(templates[iI].getCoord(iJ).getX(), templates[iI].getCoord(iJ).getY(), fNewValue, iJ);
                }                
            }            
            System.out.println("Normalisation finished!");
            
            //... Populate the Grids ...//
            System.out.println("Populating grids...");
            for(int iI=0; iI<quantTemplate.length; iI++){
                model.insertRow(model.getRowCount(), new Object[]{quantTemplate[iI].getMzIndex(),
                quantTemplate[iI].getScanID(),
                quantTemplate[iI].getQuantIntensities(),
                quantTemplate[iI].getTemplate2Index()
                });                            
            }
            for(int iI=0; iI<templates.length; iI++){                            
                for (int iJ=0; iJ<templates[iI].getCoords().length; iJ++){                                
                    model2.insertRow(model2.getRowCount(), new Object[]{templates[iI].getIndex(),
                    templates[iI].getCoord(iJ).getX(),
                    templates[iI].getCoord(iJ).getY(),
                    templates[iI].getCoord(iJ).getRelIntensity()
                    });  
                }                           
            }
            
            //... Generate the synthetic array which will be used by seaMass to calculate the quantitation values ...//
            Map<String, float[][]> hmSyntheticArray = new HashMap<String, float[][]>();
            MzMLUnmarshaller unmarshaller = aMzMLUnmarshaller.get(iFileIndex);
            String sPrev = "", sNext = "";
            
            //... Parse MS1 data ...//
            System.out.println("Parsing MS1 data ...");
            ArrayList<String[]> array = getMS1DataRefs(unmarshaller);
            String scanID = "";
            String[] triplets = new String[]{"", "", ""};
            
            System.out.println("Generate resampling array ...");
            for(int iI=0; iI<quantTemplate.length; iI++){
                scanID = quantTemplate[iI].getScanID();                
                blnExists = hmSyntheticArray.containsKey(quantTemplate[iI].getScanID());                                      
                if (blnExists == false){
                    //... Get Previous and Next MS1 scans ...//
                    sPrev = getPrevScan(array, scanID);
                    sNext = getNextScan(array, scanID);
                    System.out.println("sPrev="+sPrev);                    
                    System.out.println("scanID="+scanID);                    
                    System.out.println("sNext="+sNext);
                    triplets[0] = sPrev;
                    triplets[1] = scanID;
                    triplets[2] = sNext;
                    
                    for (int iTrip=0; iTrip<triplets.length; iTrip++){
                        //... Go to the raw data and create an resampling array ...//                
                        try{
                            Spectrum spectrum = unmarshaller.getSpectrumById(triplets[iTrip]);
                            List<BinaryDataArray> bdal = spectrum.getBinaryDataArrayList().getBinaryDataArray();

                            Number[] mzNumbers = null;
                            Number[] intenNumbers = null;
                            //... Reading mz and intensity values ...//
                            for (BinaryDataArray bda:bdal){
                                List<CVParam> cvpList = bda.getCvParam();
                                for (CVParam cvp:cvpList){                            
                                    if(cvp.getAccession().equals("MS:1000514")){
                                        mzNumbers = bda.getBinaryDataAsNumberArray();                                
                                    }
                                    if(cvp.getAccession().equals("MS:1000515")){
                                        intenNumbers = bda.getBinaryDataAsNumberArray();
                                    }
                                }
                            }
                            //... Create the hashmap for the resampling array ...//                            
                            blnExists = hmResamplingArray.containsKey(triplets[iTrip]);
                            if (blnExists == false){
                                System.out.println("Performing resampling in scan = "+triplets[iTrip]);
                                float[][] resArray = binArray(resamplingArray, mzNumbers, intenNumbers, mzWindow, true);
                                hmResamplingArray.put(triplets[iTrip], resArray);
                                hmSyntheticArray.put(triplets[iTrip], resamplingArray); //... Initalising synthetic array ...//
                            }
                        }
                        catch (MzMLUnmarshallerException ume){
                            System.out.println(ume.getMessage());
                        }
                    }
                }
            }
            
            System.out.println("Creating mapping for scan indexes...");
            
            //... Map keys for Scan IDs ...//
            String[] scanMap = new String[hmResamplingArray.size()];
            int iMapIndex = 0;
            for (Map.Entry<String, float[][]> entry : hmResamplingArray.entrySet()){
                scanMap[iMapIndex] = entry.getKey();
                iMapIndex++;
            }
            //... Sort array ...//
            Arrays.sort(scanMap);
            
            //... Populate Arrays ...//
            DefaultTableModel model3 = new DefaultTableModel();
            DefaultTableModel model4 = new DefaultTableModel();
            jTable1.setModel(model3);
            jTable2.setModel(model4);
            model3.addColumn("m/z Index");
            model3.addColumn("m/z Value");
            model4.addColumn("m/z Index");
            model4.addColumn("m/z Value");
            
            //... Adding column headers ...//
            for (int iI=0; iI < scanMap.length; iI++){
                model3.addColumn(scanMap[iI]);
                model4.addColumn(scanMap[iI]);
            }
            
            System.out.println("Populating arrays  (inseting rows) ...");
            if(scanMap.length>=0){
                blnExists = hmResamplingArray.containsKey(scanMap[0]);
                if (blnExists == true){
                    float[][] temp = hmResamplingArray.get(scanMap[0]);
                    for(int iI=0; iI<temp[0].length; iI++){
                        model3.insertRow(model3.getRowCount(), new Object[]{
                            iI,
                            temp[0][iI],
                            temp[1][iI]
                        });
                        model4.insertRow(model4.getRowCount(), new Object[]{
                            iI,
                            temp[0][iI],
                            temp[1][iI]
                        });
                    }
                }
            }
            System.out.println("Populating arrays  (updating rows from hmSyntheticArray) ...");
            //... Now it's time to load the rest of the columns ...//
            for (int iJ=1; iJ < scanMap.length; iJ++){
                blnExists = hmSyntheticArray.containsKey(scanMap[iJ]);
                if (blnExists == true){
                    float[][] temp2 = hmSyntheticArray.get(scanMap[iJ]);
                    for(int iI=0; iI<temp2[0].length; iI++){
                        model3.setValueAt(temp2[1][iI], iI, iJ+2);
                    }
                }
            }
            System.out.println("Populating arrays  (updating rows from hmResamplingArray) ...");
            //... Now it's time to load the rest of the columns ...//
            for (int iJ=1; iJ < scanMap.length; iJ++){
                System.out.println("updating rows for hmsResamplingArray " + scanMap[iJ]);
                blnExists = hmResamplingArray.containsKey(scanMap[iJ]);
                if (blnExists == true){
                    float[][] temp3 = hmResamplingArray.get(scanMap[iJ]);
                    System.out.println("In_memory2="+temp3[0][16]+"\tmz="+temp3[1][16]);
                    for(int iI=0; iI<temp3[0].length; iI++){
                        model4.setValueAt(temp3[1][iI], iI, iJ+2);
                    }
                }
            }
//            //... Update synthetic array and quant templates ...//
//            System.out.println("Updating synthetic array and quant templates...");
//            int iTemp1Index = 0, iMzIndexResamplArray = 0;
//            float quantValue = 0.0f;
//            int iX=0, iY=0;
//            float fI=0.0f;
//            for(int iI=0; iI<quantTemplate.length; iI++){
//                scanID = quantTemplate[iI].getScanID();    
//                blnExists = hmSyntheticArray.containsKey(quantTemplate[iI].getScanID());                
//                if (blnExists){
//                    System.out.println("Updating scanID="+scanID);
//                    //... Get previous and next scans (-1, 0 and +1 positions in rt) ...//
//                    sPrev = getPrevScan(array, scanID);
//                    sNext = getNextScan(array, scanID);
//                    
//                    triplets[0] = sPrev;
//                    triplets[1] = scanID;
//                    triplets[2] = sNext;
//                    
//                    iTemp1Index = quantTemplate[iI].getTemplate2Index();
//                    iMzIndexResamplArray = quantTemplate[iI].getMzIndex(); //... This will be used to calculate -1, 0, +1 in m/z ...//
//                    quantValue = quantTemplate[iI].getQuantIntensities();
//                    
//                    for(int iZ=0; iZ<templates[iTemp1Index].getCoords().length; iZ++){ //... 54 positions (9 points x 6 isotopes) ...//
//                        //... Update values using template A ...//                                               
//                        iX = templates[iI].getCoord(iZ).getX();
//                        iY = templates[iI].getCoord(iZ).getY();
//                        fI = templates[iI].getCoord(iZ).getRelIntensity();
//                        
//                        //... Get synthetic array for each scan ...//
//                        float[][] temp = hmSyntheticArray.get(triplets[iY+1]);
//                        temp[1][iTemp1Index+iX] = temp[1][iTemp1Index+iX] + (quantValue * fI);
//                        hmSyntheticArray.put(triplets[iY+1], temp);
//                    }
//                }
//            }            
            
        } //... From if countPeptides ...//        
    }    
    /*----------------------------------------------------------------------
     * Assign mz values in a resampled array using linear interpolation
     * @param array - bidimensional array that contains m/z and int values
     * @param mzArray - m/z values
     * @param intArrray2 - intensity values
     * @param mzWindow - m/z window
     * @param debug - true/false
     * @return array with values assigned
     -----------------------------------------------------------------------*/
    private float[][] binArray(float[][] array, Number[] mzArray, Number[] intArray, int mzWindow, boolean debug)
    {       
        //... The dimension of the array has to be same length as the one privided in the resampling array ...//
        float[][] arrayret = null;
        arrayret = new float[array.length][array[0].length]; 
        
        //... Convert bidimensional array into one dimension (m/z values for binary search) ...//
        Number[] onedimension = new Number[array[0].length];
        for (int iI=0; iI<array[0].length; iI++){
            onedimension[iI] = array[0][iI];
        }
        arrayret = array; //... Contains initalised locations (with 0 values) ...//
        //... Clean intensity values ...//
        for(int iI=0; iI<arrayret[0].length; iI++){
            arrayret[1][iI] = 0.0f;
        }
        
        int iLocation = 0; //... Location in the array ...//
        float fMzRef = 0.0f, fX1=0.0f, fX2=0.0f, fDx1=0.0f, fDx2=0.0f, fDx1x2=0.0f, fValX1=0.0f, fValX2=0.0f;
        for (int iI=0; iI<intArray.length; iI++){            
            //... Perform binary search to find the location of the m/z value in the resampling array ..//
            if(intArray[iI].floatValue()>0&&mzArray[iI].floatValue()<mzWindow){ //... Only data with intensities higher than 0 and within the m/z window ...//
                iLocation = binarySearch(onedimension, 0, onedimension.length, mzArray[iI].floatValue(), false);
                //... Validate indexes (first and last position) ...//
                if(iLocation>0&&iLocation<onedimension.length){
                    //... Perform linear interpolation ...//                    
                    
                    //... Get m/z value ...//
                    fMzRef = mzArray[iI].floatValue();
                    System.out.println("Look for m/z="+fMzRef + "\tInt=" + intArray[iI].floatValue());
                    if(fMzRef<onedimension[iLocation].floatValue()){
                        fX1 = onedimension[iLocation-1].floatValue();                         
                        fX2 = onedimension[iLocation].floatValue();
                        System.out.println("iLocation-1="+fX1);
                        System.out.println("iLocation="+iLocation+"\tMz="+fX2);
                    }else{
                        fX1 = onedimension[iLocation].floatValue(); 
                        fX2 = onedimension[iLocation+1].floatValue();
                        System.out.println("iLocation="+iLocation+"\tMz="+fX1);
                        System.out.println("iLocation+1="+fX2);
                    }
                    fDx1 = Math.abs(fX1-fMzRef);
                    System.out.println("fDx1="+fDx1);
                    fDx2 = Math.abs(fX2-fMzRef);
                    System.out.println("fDx2="+fDx2);
                    fDx1x2 = Math.abs(fX1-fX2); //... 100 % ...//
                    System.out.println("fDx1x2="+fDx1x2);
                    fValX1 = Math.abs(1-fDx1/fDx1x2);
                    System.out.println("fValX1="+fValX1);
                    fValX2 = Math.abs(1-fDx2/fDx1x2);   
                    System.out.println("fValX2="+fValX2);
                    if(fMzRef<onedimension[iLocation].floatValue()){
                        arrayret[1][iLocation-1] = arrayret[1][iLocation-1] + intArray[iI].floatValue()*fValX1;
                        arrayret[1][iLocation] = arrayret[1][iLocation] + intArray[iI].floatValue()*fValX2;
                        System.out.println(arrayret[1][iLocation-1]+" goes to index = "+(iLocation-1));
                        System.out.println(+arrayret[1][iLocation]+" goes to index = "+(iLocation));
                    }else{
                        arrayret[1][iLocation] = arrayret[1][iLocation] + intArray[iI].floatValue()*fValX1;
                        arrayret[1][iLocation+1] = arrayret[1][iLocation+1] + intArray[iI].floatValue()*fValX2;
                        System.out.println(arrayret[1][iLocation]+" goes to index = "+(iLocation));
                        System.out.println(arrayret[1][iLocation+1]+" goes to index = "+(iLocation+1));
                    }
                }
            }
        }
        if(debug){
            System.out.println("Print binned array...");
            for(int iD=0; iD<arrayret[0].length; iD++){
                System.out.println("mz="+arrayret[0][iD]+"\tint="+arrayret[1][iD]);
            }
        }
        return arrayret;
    }    
    /*----------------------------------------------------------
     * Get spectrum IDs and spectrum References across MS1 data
     * @param unmarshall - Raw file
     * @return - array of strings containing the spectrum IDs
     -----------------------------------------------------------*/      
    private ArrayList<String[]> getMS1DataRefs(MzMLUnmarshaller unmarshaller){
        ArrayList<String[]> array = new ArrayList<String[]>();        
        MzMLObjectIterator<Spectrum> spectrumIterator = unmarshaller.unmarshalCollectionFromXpath("/run/spectrumList/spectrum", Spectrum.class);
        while (spectrumIterator.hasNext()){
            Spectrum spectrum = spectrumIterator.next();            
            
            //... Identify MS1 data ...//
            String mslevel = "";                        
            List<CVParam> specParam = spectrum.getCvParam();       
            for (Iterator lCVParamIterator = specParam.iterator(); lCVParamIterator.hasNext();){
                CVParam lCVParam = (CVParam) lCVParamIterator.next();
                if (lCVParam.getAccession().equals("MS:1000511")){
                    mslevel = lCVParam.getValue().trim();
                }
            }            
            if (mslevel.equals("1")){
                array.add(new String[] {spectrum.getIndex().toString(), spectrum.getId()});
            }
        }
        //... Sort by spectrum Index ...//
        Collections.sort(array,new Comparator<String[]>() {
            public int compare(String[] strings1, String[] strings2) {
                strings1[0] = String.format("%08d", Integer.parseInt(strings1[0]));
                strings2[0] = String.format("%08d", Integer.parseInt(strings2[0]));
                return strings1[0].compareTo(strings2[0]);
            }
        });
        return array;
    }
    /*----------------------------------------
     * Get previous MS1 scan
     * @param array - Array with MS1 scan ids
     * @param sScanID - Scan ID reference 
     -----------------------------------------*/
    private String getPrevScan(ArrayList<String[]> array, String sScanID)
    {
        String sPrev = "";        
        int iJ = 0;
        for (String[] value : array) {
            if (value[1].equals(sScanID)){
                break;
            }
            else{
                sPrev = value[1];
            }                
            iJ++;
        } 
        return sPrev;
    }    
    /*----------------------------------------
     * Get next MS1 scan
     * @param array - Array with MS1 scan ids
     * @param sScanID - Scan ID reference 
     -----------------------------------------*/    
    private String getNextScan(ArrayList<String[]> array, String sScanID)
    {
        String sNext = "";        
        int iJ = 0;
        for (String[] value : array) {
            if (value[1].equals(sScanID)){
                if (iJ<array.size()){                    
                    sNext = array.get(iJ+1)[1];
                    break;
                }
                else{
                    sNext = value[1];
                }                
            }
            iJ++;
        } 
        return sNext;
    }        
    /*---------------------------------------------
     * Get neighbour peaks from a given mz value 
     * @param array - Array with MS data
     * @param iIndex - location in the array
     * @param iOffset - Number of peaks to cover
     * @return array of integers with peak indexes 
     ----------------------------------------------*/    
    private int[] getPeaks(Number[] nArray, int iIndex, int iOffset)
    {
        int[] aPeaks = new int[iOffset*2+1];
        int iCount = 0;
        if ((iIndex-iOffset > 0)&&(iIndex+iOffset < nArray.length)){
            for (int iI=iIndex-iOffset; iI<=iIndex+iOffset; iI++){
                aPeaks[iCount] = iI;
                iCount++;
                System.out.println("Index="+iI+"\t"+nArray[iI].floatValue());
            }
        }
        return aPeaks;
    }     
    /*----------------------------------------
     * Binary search
     * @param nArray - Array
     * @param iLowerBound - From position
     * @param iUpperBound - To position
     * @param fKey - Value to look for
     * @param debug - debug mode
     * @return position in the array 
     -----------------------------------------*/
    private int binarySearch(Number[] nArray, int iLowerBound, int iUpperBound, float fKey, boolean debug)
    {
        int iPos;
        int iCompCount = 1;    

        iPos = (iLowerBound + iUpperBound) / 2;
        while((Round(nArray[iPos].floatValue(), 4) != Round(fKey,4)) && (iLowerBound <= iUpperBound)){
             iCompCount++;
             if (Round(nArray[iPos].floatValue(), 4) > Round(fKey,4)){                                              
                iUpperBound = iPos - 1;  
             }                                                            
             else{                                                       
                iLowerBound = iPos + 1;   
             }
             iPos = (iLowerBound + iUpperBound) / 2;
        }
        if(debug){
            System.out.println("Searching for m/z = " + Round(fKey,4) + " in array");
            if (iLowerBound <= iUpperBound){            
                System.out.println("The number was found in array at position " + iPos);
                System.out.println("The binary search found the number after " + iCompCount + " comparisons.");
            }
            else{
                System.out.println("Sorry, the number is not in this array. The binary search made " + iCompCount  + " comparisons."); 
                System.out.println("The closest indexes were iLowerBound=" + iLowerBound + " and iUpperBound=" + iUpperBound); 
                iPos = iLowerBound;
                if (Math.abs(fKey-nArray[iPos].floatValue())>Math.abs(fKey-nArray[iPos-1].floatValue())){
                    iPos = iPos - 1;
                }   
                System.out.println("The pointer was set up to Index=" + iPos); 
            }
        }
        return iPos;
    }     
    /*---------------------------------------
     * Round a number to n decimals
     * @param fValue - Value
     * @param iDecimals - Number of decimals
     * @return Number rounded
     ----------------------------------------*/
    private float Round(float fValue, int iDecimals) {
        float p = (float)Math.pow(10,iDecimals);
        fValue = fValue * p;
        float tmp = Math.round(fValue);
        return (float)tmp/p;
    }
    /*---------------------------------------
     * Truncate a number to n decimals
     * @param fValue - Value
     * @param iDecimals - Number of decimals
     * @return Number truncated
     ----------------------------------------*/    
    private double Truncate(double fValue, int iDecimals) {
        double multiplier = Math.pow(10, iDecimals);
        return Math.floor(multiplier * fValue) / multiplier;
    }    
    /*-------------------------------------------------------------------------------
     * Search a value within the raw data and positionate the cursor accordingly 
     * @param sChain - Value
     * @param iColumn - Look up column 
     * @return void
     --------------------------------------------------------------------------------*/        
    private void searchValueInRawData(String sChain, int iColumn)     
    {
        DefaultTableModel dtm = (DefaultTableModel) jtRawData.getModel();
        int nRow = dtm.getRowCount();
        for (int iI = 0 ; iI < nRow ; iI++){                
            if (dtm.getValueAt(iI,iColumn).toString().startsWith(sChain)){
                jtRawData.setRowSelectionInterval(iI, iI);
                jtRawData.scrollRectToVisible(new Rectangle(jtRawData.getCellRect(iI, 0, true)));
                break;
            }
        }
    }
    /*-------------------------------------------------------------------------------
     * Search a value within the MGF view and positionate the cursor accordingly 
     * @param sChain - Value
     * @param iColumn - Look up column 
     * @return void
     --------------------------------------------------------------------------------*/    
    private void searchValueInMGF(String sChain, int iColumn)     
    {
        DefaultTableModel dtm = (DefaultTableModel) jtMGF.getModel();
        int nRow = dtm.getRowCount();
        for (int iI=0; iI < nRow ; iI++){                
            if (dtm.getValueAt(iI,iColumn).toString().trim().contains(sChain)){
                jtMGF.setRowSelectionInterval(iI, iI);
                jtMGF.scrollRectToVisible(new Rectangle(jtMGF.getCellRect(iI, 0, true)));
                break;
            }
        }
    }    
    /*-------------------------------------------------------------------------------
     * Search a value within the MzML view and positionate the cursor accordingly 
     * @param sChain - Value
     * @param iColumn - Look up column 
     * @return void
     --------------------------------------------------------------------------------*/    
    private void searchValueInMzML(String sChain, int iColumn)     
    {
        DefaultTableModel dtm = (DefaultTableModel) jtMzML.getModel();
        int nRow = dtm.getRowCount();
        for (int iI=0; iI < nRow ; iI++){
            if (dtm.getValueAt(iI,iColumn).toString().trim().startsWith(sChain)){
                jtMzML.setRowSelectionInterval(iI, iI);
                jtMzML.scrollRectToVisible(new Rectangle(jtMzML.getCellRect(iI, 0, true)));
                break;
            }
        }
    }
    /*-------------------------------------------------------------------------------
     * Search a value within the IdentML view and positionate the cursor accordingly 
     * @param sChain - Value
     * @param iColumn - Look up column 
     * @return void
     --------------------------------------------------------------------------------*/
    private void searchValueInIdentML(String sChain, int iColumn)     
    {
        DefaultTableModel dtm = (DefaultTableModel) jtMzId.getModel();
        int nRow = dtm.getRowCount();       
        for (int iI = 0 ; iI < nRow ; iI++){
            System.out.println(dtm.getValueAt(iI,iColumn).toString()+" "+sChain);
            if (dtm.getValueAt(iI,iColumn).toString().contains(sChain)){
                //System.out.println("Found at "+iI+" ("+dtm.getValueAt(iI,iColumn).toString()+" "+sChain+")");
                jtMzId.setRowSelectionInterval(iI, iI);
                jtMzId.scrollRectToVisible(new Rectangle(jtMzId.getCellRect(iI, 0, true)));
                break;
            }
        }
    }     
    /*----------------------------------------------------------------------------------
     * Search a value within the Mascot XML view and positionate the cursor accordingly 
     * @param sChain - Value
     * @param iColumn - Look up column 
     * @return void
     -----------------------------------------------------------------------------------*/    
    private void searchValueInMascotXML(String sChain, int iColumn)     
    {
        DefaultTableModel dtm = (DefaultTableModel) jtMascotXMLView.getModel();
        int nRow = dtm.getRowCount();       
        for (int iI = 0 ; iI < nRow ; iI++){
            if (dtm.getValueAt(iI,iColumn).toString().contains(sChain)){
                jtMascotXMLView.setRowSelectionInterval(iI, iI);
                jtMascotXMLView.scrollRectToVisible(new Rectangle(jtMascotXMLView.getCellRect(iI, 0, true)));
                break;
            }
        }
    }    
    /*----------------------------------------------------------------------------------
     * Search a value within the mzQuantML view and positionate the cursor accordingly 
     * @param sChain - Value
     * @param iColumn - Look up column 
     * @return void
     -----------------------------------------------------------------------------------*/    
    private void searchValueInMZQPep(String sChain, int iColumn)     
    {
        DefaultTableModel dtm = (DefaultTableModel) jtPeptideQuant.getModel();
        int nRow = dtm.getRowCount();       
        for (int iI = 0 ; iI < nRow ; iI++){
            if (dtm.getValueAt(iI,iColumn).toString().contains(sChain)){
                jtPeptideQuant.setRowSelectionInterval(iI, iI);
                jtPeptideQuant.scrollRectToVisible(new Rectangle(jtPeptideQuant.getCellRect(iI, 0, true)));
                break;
            }
        }
    }     
    /*----------------------------------------------------------------------------------
     * Search a value within the mzQuantML view and positionate the cursor accordingly 
     * @param sChain - Value
     * @param iColumn - Look up column 
     * @return void
     -----------------------------------------------------------------------------------*/    
    private void searchValueInMZQProt(String sChain, int iColumn)     
    {
        DefaultTableModel dtm = (DefaultTableModel) jtProteinQuant.getModel();
        int nRow = dtm.getRowCount();       
        for (int iI = 0 ; iI < nRow ; iI++){
            if (dtm.getValueAt(iI,iColumn).toString().contains(sChain)){
                jtProteinQuant.setRowSelectionInterval(iI, iI);
                jtProteinQuant.scrollRectToVisible(new Rectangle(jtProteinQuant.getCellRect(iI, 0, true)));
                break;
            }
        }
    }     
    /*----------------------------------------------------------------------------------
     * Search a value within the mzQuantML view and positionate the cursor accordingly 
     * @param sChain - Value
     * @param iColumn - Look up column 
     * @return void
     -----------------------------------------------------------------------------------*/    
    private void searchValueInMZQFeat(String sChain, int iColumn)     
    {
        DefaultTableModel dtm = (DefaultTableModel) jtFeatureQuant.getModel();
        int nRow = dtm.getRowCount();       
        for (int iI = 0 ; iI < nRow ; iI++){
            if (dtm.getValueAt(iI,iColumn).toString().contains(sChain)){
                jtFeatureQuant.setRowSelectionInterval(iI, iI);
                jtFeatureQuant.scrollRectToVisible(new Rectangle(jtFeatureQuant.getCellRect(iI, 0, true)));
                break;
            }
        }
    }  
    /*---------------------------------------------------------
     * Display MS spectrum 
     * @param iIndex - Index to the aMzMLUnmarshaller arraylist
     * @param sID - spectrum ID
     * @return void
     ----------------------------------------------------------*/
    private void showSpectrum(int iIndex, String sID) {                                                               
        
        //... Get index from spectra ...//
        MzMLUnmarshaller unmarshaller = aMzMLUnmarshaller.get(iIndex);            
        try{
            Spectrum spectrum = unmarshaller.getSpectrumById(sID);

            //... Reading CvParam to identify the MS level (1, 2) ...//
            String mslevel = "";
            List<CVParam> specParam = spectrum.getCvParam();
            for (Iterator lCVParamIterator = specParam.iterator(); lCVParamIterator.hasNext();){
                CVParam lCVParam = (CVParam) lCVParamIterator.next();
                if (lCVParam.getAccession().equals("MS:1000511")){
                    mslevel = lCVParam.getValue().trim();
                }
            }
            float parIonMz =0;
            int parCharge = 0;            
            if (mslevel.toString().indexOf("2") >= 0){
                PrecursorList plist = spectrum.getPrecursorList(); //... Get precursor ion ...//
                if (plist != null){
                    if (plist.getCount().intValue() == 1){            
                        List<CVParam> scanPrecParam = plist.getPrecursor().get(0).getSelectedIonList().getSelectedIon().get(0).getCvParam();

                        //... Detect parent ion m/z and charge ...//
                        for (Iterator lCVParamIterator = scanPrecParam.iterator(); lCVParamIterator.hasNext();){
                            CVParam lCVParam = (CVParam) lCVParamIterator.next();
                            if (lCVParam.getAccession().equals("MS:1000744")){
                                parIonMz = Float.parseFloat(lCVParam.getValue().trim());
                            }
                            if (lCVParam.getAccession().equals("MS:1000041")){
                                parCharge = Integer.parseInt(lCVParam.getValue().trim());
                            }
                        }
                    }
                }
            }
            
            Number[] mzNumbers = null;
            Number[] intenNumbers = null;

            boolean bCompressed = false;
            //... Reading mz Values ...//
            List<BinaryDataArray> bdal = spectrum.getBinaryDataArrayList().getBinaryDataArray();
            for (BinaryDataArray bda:bdal){
                List<CVParam> cvpList = bda.getCvParam();
                for (CVParam cvp:cvpList){                            
                    if(cvp.getAccession().equals("MS:1000000")){
                        bCompressed = true;
                    }
                }
            } 
            
            //... Reading mz and intensity values ...//
            for (BinaryDataArray bda:bdal){
                List<CVParam> cvpList = bda.getCvParam();
                for (CVParam cvp:cvpList){                            
                    if(cvp.getAccession().equals("MS:1000514")){
                        mzNumbers = bda.getBinaryDataAsNumberArray();      
                        if(bCompressed){
                            mzNumbers = decodeMzDeltas(mzNumbers); 
                        }                        
                    }
                    if(cvp.getAccession().equals("MS:1000515")){
                        intenNumbers = bda.getBinaryDataAsNumberArray();                                
                    }
                }
            }           
            
            double[] mz = new double[mzNumbers.length];
            for (int iI = 0; iI < mzNumbers.length; iI++){
                mz[iI] = mzNumbers[iI].doubleValue();
            }
            double[] intensities = new double[intenNumbers.length];
            for (int iI = 0; iI < intenNumbers.length; iI++)
            {
                intensities[iI] = intenNumbers[iI].doubleValue();
            }
            //... Call the spectrum panel from compomics.org ...//
            jdpMS.removeAll();
            JInternalFrame jifViewSpectrum = getInternalFrame();            
            
            JPanel specpanel = new SpectrumPanel(mz, intensities, 
                    parIonMz, Integer.toString(parCharge), 
                    sID, 50, false, 
                    true, true, 
                    true, Integer.parseInt(mslevel));
            jifViewSpectrum.setTitle("MS spectrum <ScanID: " + sID + ">");
            specpanel.setSize(new java.awt.Dimension(600, 400));
            specpanel.setPreferredSize(new java.awt.Dimension(600, 400));
            jifViewSpectrum.add(specpanel, BorderLayout.CENTER);            
            jifViewSpectrum.setSize(jdpMS.getSize());
            jdpMS.add(jifViewSpectrum, BorderLayout.CENTER);
            jdpMS.revalidate();
            jdpMS.repaint();
        }
        catch (MzMLUnmarshallerException ume){
            System.out.println(ume.getMessage());
        }
    }    
    /*-------------------------------------------------------
     * Show raw data from a Mascot Generic Format (MGF) file
     * @param iIndex - Index to the raw file
     * @param sID - Scan title
     * @return void
     --------------------------------------------------------*/
    private void showRawDataMGF(int iIndex, String sID) {                                             
                
        DefaultTableModel model = new DefaultTableModel();
        jtRawData.setModel(model);
        model.addColumn("Index");
        model.addColumn("m/z");
        model.addColumn("Intensity");
        
        //... Reading file ...//
        try{
            BufferedReader in = new BufferedReader(new FileReader(jtRawFiles.getValueAt(iIndex, 1).toString()));
            long refLine=Long.parseLong(sID);
            String string = null;
            
            long lineNum = 0; 
            int iCount = 0;
            while ((string = in.readLine()) != null) {
                lineNum++;
                string = string.trim();
                if (lineNum==refLine) {
                    break;
                }
            }                     
            while ((string = in.readLine()) != null) {               
                if (string.charAt(0) >= '1' && string.charAt(0) <= '9') {
                    String[] split = string.split("\\s+");
                    double mz = Double.parseDouble(split[0]);
                    double intensity = Double.parseDouble(split[1]);
                    iCount++;
                    
                   model.insertRow(model.getRowCount(), new Object[]{
                        iCount,
                        mz,
                        intensity
                        });
                    continue;
                }
                if (string.startsWith("END IONS")){
                    break;
                }
            }
        }catch(Exception e){
            System.exit(1);
        }
        
        jtpLog.setSelectedIndex(1);
    }
    /*---------------------------------------------------
     * Displays raw data
     * @iIndex - Index to the aMzMLUnmarshaller arraylist 
     * @sID - scan ID
     * @return void
     ---------------------------------------------------*/    
    private void showRawData(int iIndex, String sID) {                                             
                
        DefaultTableModel model = new DefaultTableModel(){
            Class[] types = new Class[]{Integer.class, Float.class, Float.class};
            @Override  
            public Class getColumnClass(int columnIndex) {  
                return types [columnIndex];  
            }
        };
        jtRawData.setModel(model);                    
        model.addColumn("Index");
        model.addColumn("m/z");
        model.addColumn("Intensity");        
        jtpLog.setSelectedIndex(1);
        
        //... Get index from spectra ...//
        MzMLUnmarshaller unmarshaller = aMzMLUnmarshaller.get(iIndex);            
        try{
            Spectrum spectrum = unmarshaller.getSpectrumById(sID);
            Number[] mzNumbers = null;
            Number[] intenNumbers = null;
            
            boolean bCompressed = false;
            //... Reading mz Values ...//
            List<BinaryDataArray> bdal = spectrum.getBinaryDataArrayList().getBinaryDataArray();
            for (BinaryDataArray bda:bdal){
                List<CVParam> cvpList = bda.getCvParam();
                for (CVParam cvp:cvpList){                            
                    if(cvp.getAccession().equals("MS:1000000")){
                        bCompressed = true;
                    }
                }
            } 
                    
            //... Reading mz and intensity values ...//            
            for (BinaryDataArray bda:bdal){
                List<CVParam> cvpList = bda.getCvParam();
                for (CVParam cvp:cvpList){                            
                    if(cvp.getAccession().equals("MS:1000514")){                        
                        mzNumbers = bda.getBinaryDataAsNumberArray();                          
                        if(bCompressed){
                            mzNumbers = decodeMzDeltas(mzNumbers); 
                        }                        
                    }
                    if(cvp.getAccession().equals("MS:1000515")){
                        intenNumbers = bda.getBinaryDataAsNumberArray();                                
                    }
                }
            }
            for (int iI=0; iI < mzNumbers.length; iI++){
               model.insertRow(model.getRowCount(), new Object[]{
                    iI,
                    mzNumbers[iI].doubleValue(),
                    intenNumbers[iI].doubleValue()
                    });
            }
            jtRawData.getTableHeader().setDefaultRenderer(new TableCellRenderer() {  
                final TableCellRenderer defaultRenderer = jtRawData.getTableHeader().getDefaultRenderer();  
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row,  
                                                            int column) {  
                    JComponent component = (JComponent)defaultRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);  
                    component.setToolTipText("" + jtRawData.getColumnName(column));
                    return component;  
                }  
            }); 
            jtRawData.setAutoCreateRowSorter(true);            
        }
        catch (MzMLUnmarshallerException ume){
            System.out.println(ume.getMessage());
        }
    }         
    public Number[] decodeMzDeltas(Number[] mzNumbers){           
                
        //... Storing normal values ...//
        if (mzNumbers!=null){
            double previous=0.0d;
            for (int iI = 0; iI < mzNumbers.length; iI++){
                previous=mzNumbers[iI].doubleValue()+previous;
                mzNumbers[iI] = previous;           
            }            
        }               
        return mzNumbers;
    }
    /*---------------------------------------
     * Get internal frame dimensions
     * @param void 
     * @return JInternalFrame as a container
     ----------------------------------------*/
    private JInternalFrame getInternalFrame(){ 
        JInternalFrame internalFrame = new JInternalFrame("");  
        Icon icon = new ImageIcon(getClass().getClassLoader().getResource("images/icon.gif"));
        
        internalFrame.setLocation(0,0);
        internalFrame.setSize(new java.awt.Dimension(620, 420));  
        internalFrame.setPreferredSize(new java.awt.Dimension(620, 420));  
        internalFrame.setFrameIcon(icon);
        internalFrame.setResizable(true);
        internalFrame.setMaximizable(true);
        internalFrame.setClosable(true);
        internalFrame.setIconifiable(true);        
        internalFrame.setVisible(true);  
        return internalFrame;
    }
    /* ---------------------------------------------------------
     * Display total ion chromatogram 
     * @ iIndex - Index to the aMzMLUnmarshaller arraylist ...//
     * @ sTitle - Window title
     * @return void
     ---------------------------------------------------------*/
    private void showChromatogram(int iIndex, String sTitle) {                                             
        
            //... Get index from spectra ...//
            MzMLUnmarshaller unmarshaller = aMzMLUnmarshaller.get(iIndex);
            try{
                //... Check if mzML contains MS1 data ...//
                Set<String> chromats = unmarshaller.getChromatogramIDs();
                if(!chromats.isEmpty()){
                    Chromatogram chromatogram = unmarshaller.getChromatogramById("TIC");
                    Number[] rtNumbers = null;
                    Number[] intenNumbers = null;                    
                    List<BinaryDataArray> bdal = chromatogram.getBinaryDataArrayList().getBinaryDataArray();

                    for (BinaryDataArray bda:bdal){
                        List<CVParam> cvpList = bda.getCvParam();
                        for (CVParam cvp:cvpList){                            
                            if(cvp.getAccession().equals("MS:1000595")){
                                rtNumbers = bda.getBinaryDataAsNumberArray();                                
                            }
                            if(cvp.getAccession().equals("MS:1000515")){
                                intenNumbers = bda.getBinaryDataAsNumberArray();                                
                            }
                        }
                    }    
                    //... Converting numbers to doubles ...//
                    double[] rt = new double[rtNumbers.length];
                    for (int iI = 0; iI < rtNumbers.length; iI++){
                        rt[iI] = rtNumbers[iI].doubleValue();
                    }
                    double[] intensities = new double[intenNumbers.length];
                    for (int iI = 0; iI < intenNumbers.length; iI++){
                       intensities[iI] = intenNumbers[iI].doubleValue();
                    }
                    //... Clear container ...//
                    jdpTIC.removeAll();
                    JInternalFrame jifViewChromatogram = getInternalFrame();            
                    //... Class chromatogram from compomics.org ...//
                    ChromatogramPanel panel = new ChromatogramPanel(rt, intensities, "RT (mins)", "Intensity (counts)");
                    jifViewChromatogram.setTitle("Chromatogram <" + sTitle + ">");
                    panel.setSize(new java.awt.Dimension(600, 400));
                    panel.setPreferredSize(new java.awt.Dimension(600, 400));
                    jifViewChromatogram.add(panel);
                    jdpTIC.add(jifViewChromatogram);
                    jdpTIC.revalidate();
                    jdpTIC.repaint();
               }
               else{
                   System.out.println(sysutils.getTime()+" - This mzML file doesn't contain MS2 raw data.");
               }
            }
            catch (MzMLUnmarshallerException ume){
                System.out.println(ume.getMessage());
            }
    }         
    /*----------------------------------------------------------
     * Displays the MS1 raw data as 2D plots
     * @param iIndex - Index to the aMzMLUnmarshaller arraylist
     * @return void
     -----------------------------------------------------------*/
    private void show2DPlot(int iIndex, String sTitle) {                                             
        
        //CheckMemory chm = new CheckMemory("Before allocating memory for 2D plot");
        //... Display about 27 x 3 = 51 MB maximum for now ...//
        float[] mz = new float[200000];
        float[] intensities = new float[200000];
        float[] art = new float[200000];
        //CheckMemory chm2 = new CheckMemory("After allocating memory for 2D plot");

        float rt = 0;
        int iCounter = 0;        
        
        //... Get index from spectra ...//
        MzMLUnmarshaller unmarshaller = aMzMLUnmarshaller.get(iIndex);   

        //... Check if mzML contains MS1 data ...//
        Set<String> chromats = unmarshaller.getChromatogramIDs();
        if(!chromats.isEmpty()){
            MzMLObjectIterator<Spectrum> spectrumIterator = unmarshaller.unmarshalCollectionFromXpath("/run/spectrumList/spectrum", Spectrum.class);
            int iK = 0;
            String unitRT = "";
            //... Display first 100 scans for now ...//
            while ((spectrumIterator.hasNext())&&(iK<100)){                                         
                Spectrum spectrumobj = spectrumIterator.next();
                String spectrumid = spectrumobj.getId();                
                
                //... Identify MS1 data ...//
                String mslevel = "";                        
                List<CVParam> specParam = spectrumobj.getCvParam();       
                for (Iterator lCVParamIterator = specParam.iterator(); lCVParamIterator.hasNext();){
                    CVParam lCVParam = (CVParam) lCVParamIterator.next();
                    if (lCVParam.getAccession().equals("MS:1000511")){
                        mslevel = lCVParam.getValue().trim();
                    }
                }
                if (mslevel.equals("1")){
                    try{
                        Spectrum spectrum = unmarshaller.getSpectrumById(spectrumid);
                        List<CVParam> scanParam = spectrum.getScanList().getScan().get(0).getCvParam();
                        for (Iterator lCVParamIterator = scanParam.iterator(); lCVParamIterator.hasNext();){
                           CVParam lCVParam = (CVParam) lCVParamIterator.next();
                           if (lCVParam.getAccession().equals("MS:1000016")){ //... Get RT ...//
                               rt = Float.parseFloat(lCVParam.getValue().trim());
                               unitRT = lCVParam.getUnitAccession().trim();
                               if (unitRT.equals("UO:0000031")){  //... Convert RT into seconds ...//
                                   rt = Float.parseFloat(lCVParam.getValue().trim());
                                   rt = rt * 60;
                               }else
                               {
                                   rt = Float.parseFloat(lCVParam.getValue().trim());
                               }                           
                           }
                        }
                        Number[] mzNumbers = null;
                        Number[] intenNumbers = null;
                        //... Reading mz Values ...//
                        List<BinaryDataArray> bdal = spectrum.getBinaryDataArrayList().getBinaryDataArray();
                        for (BinaryDataArray bda:bdal){
                            List<CVParam> cvpList = bda.getCvParam();
                            for (CVParam cvp:cvpList){                            
                                if(cvp.getAccession().equals("MS:1000514")){
                                    mzNumbers = bda.getBinaryDataAsNumberArray();                                
                                }
                                if(cvp.getAccession().equals("MS:1000515")){
                                    intenNumbers = bda.getBinaryDataAsNumberArray();                                
                                }
                            }
                        }
                        int iI=0;
                        while (iI<mzNumbers.length){
                            if (intenNumbers[iI].doubleValue() > 0){ //... Removing zero values ...//                                                        
                                if(iCounter<200000){
                                    mz[iCounter] = mzNumbers[iI].floatValue();                                              
                                    intensities[iCounter] = intenNumbers[iI].floatValue();
                                    art[iCounter] = rt;
                                    iCounter++;
                                }    
                            }
                            iI = iI + 10;                              
                        }
                    }
                    catch (MzMLUnmarshallerException ume){
                        System.out.println(ume.getMessage());
                    }
                    iK++;
                }                            
            }
            System.out.println(sysutils.getTime()+" - 2D view holding " + iCounter + " elements.");            
            TwoDPlot jifView2D = new TwoDPlot(sTitle, mz, intensities, art);
            jp2D.add(jifView2D);
            jifView2D.pack();
            jifView2D.setVisible(true);
        }      
        else{
            System.out.println(sysutils.getTime()+" - This mzML file doesn't contain MS2 raw data.");
        }
    }         
    /*--------------------------
     * TO DO
     ---------------------------*/
    private void writeConfigFile(String sFileName) 
    {
        if (sFileName.indexOf(".psx") <= 0){
            sFileName = sFileName + ".psx";
        }
        try{

            FileWriter fstream = new FileWriter(sFileName);
            BufferedWriter out = new BufferedWriter(fstream);            
            out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            out.newLine();
            out.write("<ProteoSuiteProject xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" ");
            out.write("xsi:schemaLocation=\"ProteoSuite.xsd\" name=\"" + sProjectName + "\" workspace=\"" + sWorkspace + "\">");
            out.newLine();
            out.write(" <configSettings>");
            out.newLine();
            out.write("     <rawDataSettings>");
            out.newLine();
            for (int iI=0; iI<jtRawFiles.getRowCount(); iI++){
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
        }
        catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }             
    }
    /*----------------------------------------------------------
     * Load MzIdentML viewer
     * @param iIndex - Index to the aMzIDUnmarshaller arraylist 
     * @param sFileName - File name
     * @return void
     -----------------------------------------------------------*/
    private void loadMzIdentMLView(int iIndex, String sFileName) 
    {                            
        final int iIndexRef = iIndex;
        final String filename = sFileName;
        final ProgressBarDialog progressBarDialog = new ProgressBarDialog(this, true, "LoadMzIDView");
        final Thread thread = new Thread(new Runnable(){
            @Override
            public void run(){
                //... Progress Bar ...//
                progressBarDialog.setTitle("Loading MzIdentML data");
                progressBarDialog.setTaskName("Please wait while loading the data ...");
                progressBarDialog.setVisible(true);
            }
        }, "ProgressBarDialog");
        thread.start();
        new Thread("LoadMzIDView"){
            @Override
            public void run(){                              
                String sOutput="";
                jtpProperties.setSelectedIndex(2);
                //... File Name and Version ...//
                jlFileNameMzIDText.setText(filename);
                
                System.out.println("Unmarshalling mzid file" + filename);

                MzIdentMLUnmarshaller unmarshaller = aMzIDUnmarshaller.get(iIndexRef);
                sOutput = "<b>mzIdentML Version:</b> <font color='red'>" + unmarshaller.getMzIdentMLVersion() + "</font><br />";

                //... File Content ...//
                sOutput = sOutput + "<b>Analysis Software:</b><br />";
                String sSoftwareID = "Default";
                HashMap<String, AnalysisSoftware> analysisSoftwareHashMap;
                analysisSoftwareHashMap = new HashMap();

                Iterator<AnalysisSoftware> iterAnalysisSoftware = unmarshaller.unmarshalCollectionFromXpath(MzIdentMLElement.AnalysisSoftware);
                while (iterAnalysisSoftware.hasNext()) {
                    AnalysisSoftware analysisSoftware = iterAnalysisSoftware.next();
                    analysisSoftwareHashMap.put(analysisSoftware.getId(), analysisSoftware);
                    sOutput = sOutput + " - " + analysisSoftware.getName().trim()+"<br />";
                    sSoftwareID = analysisSoftware.getVersion();
                }                
                ArrayList alClasses = new ArrayList();
                alClasses.add(Integer.class);
                alClasses.add(String.class);
                alClasses.add(String.class);
                alClasses.add(String.class);
                alClasses.add(Double.class);
                alClasses.add(Double.class);
                alClasses.add(Integer.class);
                alClasses.add(Integer.class);
                alClasses.add(String.class);
                alClasses.add(String.class);

                //... Based on the number of CV params from a given search engine we define the table model ...//
                ArrayList alScoreName = new ArrayList();
                ArrayList alScoreAccession = new ArrayList();                    
                AnalysisData analysisData = unmarshaller.unmarshal(MzIdentMLElement.AnalysisData);
                List<SpectrumIdentificationList> silList = analysisData.getSpectrumIdentificationList();
                init:
                for (SpectrumIdentificationList sil : silList) {
                    List<SpectrumIdentificationResult> sirList = sil.getSpectrumIdentificationResult();
                    for (SpectrumIdentificationResult sir : sirList) {
                        SpectrumIdentificationItem selected = null;
                        List<SpectrumIdentificationItem> siiList = sir.getSpectrumIdentificationItem();
                        for (SpectrumIdentificationItem sii : siiList) {
                            selected = sii;
                            Peptide peptide = selected.getPeptide();
                            if (peptide != null) {
                                List<uk.ac.ebi.jmzidml.model.mzidml.CvParam> cvpList = sii.getCvParam();
                                if(cvpList!=null){
                                    for(uk.ac.ebi.jmzidml.model.mzidml.CvParam cvp : cvpList){
                                        if (cvp.getValue()!=null){
                                            alScoreName.add(cvp.getName().toString());
                                            alScoreAccession.add(cvp.getAccession().toString());
                                            alClasses.add(Double.class);
                                        }
                                    }
                                    break init;                       
                                }                                    
                            }
                        }
                    }
                }  
                //... Determine columns and format type ...//
                final Class[] types = new Class[alClasses.size()];
                for(int iI=0; iI<alClasses.size(); iI++){
                    types[iI] = (Class)alClasses.get(iI);
                }
                DefaultTableModel model = new DefaultTableModel(){                        
                    @Override  
                    public Class getColumnClass(int columnIndex) {  
                        return types [columnIndex];  
                    }
                };
                
                model.addColumn("Index");
                model.addColumn("Protein");
                model.addColumn("Peptide");
                model.addColumn("Composition");
                model.addColumn("Exp m/z");
                model.addColumn("Calc m/z");
                model.addColumn("Charge");
                model.addColumn("Rank");
                model.addColumn("Spectrum ID");
                model.addColumn("PassThreshold");


                
                System.out.println(sysutils.getTime()+" - TotalScores="+alScoreName.size());
                for (int iSize=0; iSize<alScoreName.size(); iSize++){
                    //System.out.println("alScoreName:" + alScoreName.get(iSize).toString());
                    model.addColumn(alScoreName.get(iSize).toString());
                }                
                jtMzId.setModel(model);

                int iCount=0;
                String sAccesion = "";
                ArrayList alScoreValues = new ArrayList();

                //... Populating protein and peptide identifications ...//
                for (SpectrumIdentificationList sil : silList) {
                    List<SpectrumIdentificationResult> sirList = sil.getSpectrumIdentificationResult();
                    for (SpectrumIdentificationResult sir : sirList) {
                        SpectrumIdentificationItem selected = null;
                        List<SpectrumIdentificationItem> siiList = sir.getSpectrumIdentificationItem();
                        for (SpectrumIdentificationItem sii : siiList) {
                            selected = sii;
                            iCount++;
                            Peptide peptide = selected.getPeptide();
                            //System.out.println("Peptide="+peptide.getPeptideSequence());
                            if (peptide != null) {
                                List<PeptideEvidenceRef> pepRefList = selected.getPeptideEvidenceRef();
                                sAccesion = "";
                                //System.out.println("Peptide evidence Refs="+pepRefList.size());    
                                if (pepRefList.size() > 0){
                                    for(PeptideEvidenceRef per : pepRefList){                                        
                                        //System.out.println("Here is the problem");                                            
                                        //System.out.println("per="+per.getPeptideEvidenceRef());
                                        PeptideEvidence pe;
                                        try {
                                            pe = unmarshaller.unmarshal(PeptideEvidence.class, per.getPeptideEvidenceRef());
                                            //System.out.println("pe="+pe.getDBSequenceRef());
                                            DBSequence dbs = unmarshaller.unmarshal(DBSequence.class, pe.getDBSequenceRef());
                                            //System.out.println("dbs="+dbs.getAccession());
                                            sAccesion = sAccesion + dbs.getAccession().replace("|", "-") + ";";                                            
                                        } catch (JAXBException ex) {
                                            Logger.getLogger(ProteoSuiteView.class.getName()).log(Level.SEVERE, null, ex);
                                        }                                       
                                    }
                                }else{
                                    sAccesion = "N/A";
                                }
                                alScoreValues.clear();
                                List<uk.ac.ebi.jmzidml.model.mzidml.CvParam> cvpList = sii.getCvParam();
                                if(cvpList!=null){
                                    for(uk.ac.ebi.jmzidml.model.mzidml.CvParam cvp : cvpList){
                                        if (cvp.getValue()!=null){
                                            alScoreValues.add(cvp.getValue());
                                        }
                                    }
                                }                                    

                                ArrayList alValues = new ArrayList();
                                alValues.add(iCount);
                                alValues.add(sAccesion);
                                alValues.add(peptide.getPeptideSequence());
                                alValues.add(getResidueComposition(peptide.getPeptideSequence()));
                                alValues.add(sii.getExperimentalMassToCharge());
                                alValues.add(sii.getCalculatedMassToCharge());
                                alValues.add(sii.getChargeState());
                                alValues.add(sii.getRank());
                                alValues.add(sir.getSpectrumID().toString());
                                alValues.add(String.valueOf(sii.isPassThreshold()));
                                
                                
                                for (int iI=0; iI<alScoreValues.size(); iI++){
                                    alValues.add(Double.parseDouble(alScoreValues.get(iI).toString()));
                                }

                                Object[] objects = new Object[alValues.size()];
                                for(int iObjects=0; iObjects<alValues.size(); iObjects++){
                                    objects[iObjects] = alValues.get(iObjects);
                                }
                                
                                if(jcbPSM.getSelectedItem().toString().equals("All")){                                    
                                    model.insertRow(model.getRowCount(), objects);
                                }
                                else{
                                    if (sii.getRank()<=(jcbPSM.getSelectedIndex()+1)){                                      
                                        model.insertRow(model.getRowCount(), objects);
                                    }
                                }
                            }
                        }                        
                    }
                }                                 
                jtMzId.getTableHeader().setDefaultRenderer(new TableCellRenderer() {  
                    final TableCellRenderer defaultRenderer = jtMzId.getTableHeader().getDefaultRenderer();  
                    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row,  
                                                                int column) {  
                        JComponent component = (JComponent)defaultRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);  
                        component.setToolTipText("" + jtMzId.getColumnName(column));
                        return component;  
                    }  
                });
                jtMzId.setAutoCreateRowSorter(true);   

                //... Filling protein groups ...//
                while (((DefaultTableModel) jtMzIDProtGroup.getModel()).getRowCount() > 0) {
                    ((DefaultTableModel) jtMzIDProtGroup.getModel()).removeRow(0);
                }                
                Iterator<ProteinAmbiguityGroup> iterProteinAmbiguityGroup = unmarshaller.unmarshalCollectionFromXpath(MzIdentMLElement.ProteinAmbiguityGroup);
                List<ProteinDetectionHypothesis> proteinDetectionHypothesisList;
                String protein_accessions = "";
                while (iterProteinAmbiguityGroup.hasNext()) {
                    ProteinAmbiguityGroup proteinAmbiguityGroup = iterProteinAmbiguityGroup.next();

                    protein_accessions = "";
                    proteinDetectionHypothesisList = proteinAmbiguityGroup.getProteinDetectionHypothesis();
                    boolean representativeProtein = false;
                    String representativeProteinAccession = "";
                    boolean isDecoy = false;
                    String score = " ";
                    String number_peptide = " ";
                    boolean isPassThreshold = false;
                    if (proteinDetectionHypothesisList.size() > 0) {
                        for (int j = 0; j < proteinDetectionHypothesisList.size(); j++) {
                            ProteinDetectionHypothesis proteinDetectionHypothesis = proteinDetectionHypothesisList.get(j);
                            try {
                                DBSequence dBSequence = unmarshaller.unmarshal(DBSequence.class, proteinDetectionHypothesis.getDBSequenceRef());
                                if (dBSequence.getAccession() != null) {
                                    protein_accessions = protein_accessions + dBSequence.getAccession() + ";";
                                }                               
                                List<uk.ac.ebi.jmzidml.model.mzidml.CvParam> cvParamList = proteinDetectionHypothesis.getCvParam();
                                for (int i = 0; i < cvParamList.size(); i++) {
                                    uk.ac.ebi.jmzidml.model.mzidml.CvParam cvParam = cvParamList.get(i);
                                    if (cvParam.getName().equals("anchor protein")) {
                                        representativeProtein = true;
                                        representativeProteinAccession = dBSequence.getAccession();
                                        isDecoy = checkIfProteinDetectionHypothesisIsDecoy(unmarshaller, proteinDetectionHypothesis);
                                        if (proteinDetectionHypothesis.getPeptideHypothesis() != null) {
                                            number_peptide = String.valueOf(proteinDetectionHypothesis.getPeptideHypothesis().size());
                                        }

                                        isPassThreshold = proteinDetectionHypothesis.isPassThreshold();
                                    }
                                    if (cvParam.getName().contains("score")) {
                                        score = cvParam.getValue();
                                    }
                                }                                    
                            } catch (JAXBException ex) {
                                Logger.getLogger(ProteoSuiteView.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    protein_accessions = protein_accessions.substring(0, protein_accessions.length() - 1);
                    if (representativeProtein) {
                        ((DefaultTableModel) jtMzIDProtGroup.getModel()).addRow(new Object[]{
                                    proteinAmbiguityGroup.getId(),
                                    proteinAmbiguityGroup.getName(),
                                    protein_accessions,
                                    representativeProteinAccession,
                                    Double.valueOf(score).doubleValue(),
                                    " ",
                                    Integer.valueOf(number_peptide),
                                    String.valueOf(isDecoy),
                                    String.valueOf(isPassThreshold)
                                });
                    } else {
                        ((DefaultTableModel) jtMzIDProtGroup.getModel()).addRow(new Object[]{
                                    proteinAmbiguityGroup.getId(),
                                    proteinAmbiguityGroup.getName(),
                                    protein_accessions,
                                    " ",
                                    " ",
                                    " ",
                                    " ",
                                    " ",
                                    " "
                                });
                    }
                }

                jepMzIDView.setText(sOutput);
                progressBarDialog.setVisible(false);
                progressBarDialog.dispose();
            }
        }.start();            
    }    
    /*--------------------------------------------------------
     * Display MZQ data
     * @param iIndex - Index to the aMzQUnmarshaller arraylist 
     * @param sFile - File name
     * @return void
     ---------------------------------------------------------*/
    private void loadMzQuantMLView(int iIndex, String sFile) 
    {
        if ((!jtQuantFiles.getValueAt(iIndex, 0).toString().equals(jlFileNameMzQText.getText()))||(jtPeptideQuant.getRowCount()<=0)){        
            final String sFileRef = sFile;
            final int iIndexRef = iIndex;
            final ProgressBarDialog progressBarDialog = new ProgressBarDialog(this, true, "LoadMZQView");
            final Thread thread = new Thread(new Runnable(){
                @Override
                public void run(){
                    //... Progress Bar ...//
                    progressBarDialog.setTitle("Loading MZQ data");
                    progressBarDialog.setVisible(true);
                }
            }, "ProgressBarDialog");
            thread.start();
            new Thread("LoadMZQView"){
                @Override
                public void run(){                    
                    jtpProperties.setSelectedIndex(4);
                    DefaultTableModel model = new DefaultTableModel();
                    DefaultTableModel model2 = new DefaultTableModel();
                    DefaultTableModel model3 = new DefaultTableModel();
                    jtProteinQuant.setModel(model);
                    jtPeptideQuant.setModel(model2);
                    jtFeatureQuant.setModel(model3);
                    model.addColumn("Protein");
                    model2.addColumn("Peptide");
                    model3.addColumn("Feature");
                    
                    MzQuantMLUnmarshaller unmarshaller = new MzQuantMLUnmarshaller(jtQuantFiles.getValueAt(0, iIndexRef).toString());
                    MzQuantML mzq = unmarshaller.unmarshall();
                    
                    System.out.println(sysutils.getTime()+" - (Loading mzQuantMLView)");
                    System.out.println(sysutils.getTime()+" - MZQ elements="+aMzQUnmarshaller.size());
                    System.out.println(sysutils.getTime()+" - Unmarshalling element "+iIndexRef+" from the array");
                    
                    //... File Name and Version ...//
                    jlFileNameMzQText.setText(sFileRef);
                    System.out.println(sysutils.getTime()+" - Loading "+sFileRef);
                    String sOutput="";
                    String sMessage="";
                    sOutput = "<b>mzML Version:</b> <font color='red'>" + mzq.getVersion() + "</font><br />";
                    System.out.println(sysutils.getTime()+" - mzML Version: " + mzq.getVersion());
                    
                    sOutput = sOutput + "<b>Software List:</b><br />";                    
                    SoftwareList softwareList = unmarshaller.unmarshal(MzQuantMLElement.SoftwareList);
                    System.out.println(sysutils.getTime()+" - Software List Size: "+softwareList.getSoftware().size());
                    if(softwareList!=null){
                        for(Software software : softwareList.getSoftware()){
                            List<CvParam> cvlSoftware = software.getCvParam();
                            for (CvParam cvp : cvlSoftware){
                                sOutput = sOutput + " - " + cvp.getName().trim()+"<br />";
                            }
                        }
                    }
                    
                    sOutput = sOutput + "<b>Data Processing:</b><br />";
                    DataProcessingList dpList = unmarshaller.unmarshal(MzQuantMLElement.DataProcessingList);
                    if(dpList!=null){
                        for(DataProcessing dp : dpList.getDataProcessing()){
                            List<ProcessingMethod> dataProcessingMethodList = dp.getProcessingMethod();
                            for (ProcessingMethod procMeth : dataProcessingMethodList){
                                List<UserParam> usrpProcMeth = procMeth.getUserParam();
                                for (UserParam usrp : usrpProcMeth){                                    
                                    sOutput = sOutput + " - ["+ usrp.getName().trim()+ " = " + usrp.getValue().trim()+"]<br />";
                                }
                            }
                        }
                    }
                    
                    jepMZQView.setText(sOutput);
                    sOutput="";

    //============================//
    //... Protein Quantitation ...//                
    //============================//
                    //... Based on the the assay list and study variables we will include the different columns ...//
                    AssayList assayList = unmarshaller.unmarshal(MzQuantMLElement.AssayList);
                    
                    List<Assay> listAssay = assayList.getAssay();
                    int iAssays = 0;
                    for (Assay assay : listAssay) {
                        model.addColumn(assay.getName());
                        iAssays++;                        
                    }
                    sMessage = sysutils.getTime()+" - Assays: "+iAssays;
                    System.out.println(sMessage);
                    sOutput = sOutput + sMessage+"\n";
                    
                    StudyVariableList studyList = unmarshaller.unmarshal(MzQuantMLElement.StudyVariableList);
                    
                    List<StudyVariable> listStudy = studyList.getStudyVariable();
                    int iStudyVars = 0;
                    for (StudyVariable study : listStudy) {
                        model.addColumn(study.getName());                        
                        iStudyVars++;
                    }
                    sMessage = sysutils.getTime()+" - Study Variables: "+iStudyVars;
                    System.out.println(sMessage);
                    sOutput = sOutput + sMessage+"\n";
                    
                    //... Fill rows ...//           
                    Map<String, ArrayList<String>> hmProtein = new HashMap<String, ArrayList<String>>();
                    
                    sMessage = sysutils.getTime()+" - Reading Protein Quantitation ...";
                    System.out.println(sMessage);
                    sOutput = sOutput + sMessage+"\n";

                    //... Check if mzq file contains protein list ...//
                    ProteinList proteinList = unmarshaller.unmarshal(MzQuantMLElement.ProteinList);
                    if (proteinList!=null){                        
                        //... Getting DataMatrix from AssayQuantLayer ...//
                        if (proteinList.getAssayQuantLayer().size()>0){
                            List<Row> dataMatrix = proteinList.getAssayQuantLayer().get(0).getDataMatrix().getRow();
                            for(Row row:dataMatrix){
                                Protein prot = (Protein) row.getObjectRef();
                                List<String> values = row.getValue();       
                                ArrayList al = (ArrayList) values;
                                hmProtein.put(prot.getAccession(), al);                                
                            }
                        }
                        //... Getting DataMatrix from StudyVariableQuantLayer ...//
                        if (proteinList.getStudyVariableQuantLayer().size()>0){
                            List<Row> dataMatrix2 = proteinList.getStudyVariableQuantLayer().get(0).getDataMatrix().getRow();
                            for(Row row:dataMatrix2){
                                Protein prot = (Protein) row.getObjectRef();
                                List<String> values = row.getValue();       
                                ArrayList al = (ArrayList) values;
                                ArrayList al2 = hmProtein.get(prot.getAccession());                                
                                for(Object obj:al){
                                    al2.add(obj);
                                }
                                hmProtein.put(prot.getAccession(), al2);
                            }
                        }
                        for (Map.Entry<String, ArrayList<String>> entry : hmProtein.entrySet()){                    
                            Object[] aObj = new Object[iAssays+iStudyVars+1];                    
                            aObj[0] = entry.getKey();

                            ArrayList<String> saParams = entry.getValue();
                            Iterator<String> itr = saParams.iterator();
                            int iI = 1;
                            while (itr.hasNext()){
                                if (iI>=iAssays+iStudyVars+1){
                                    JOptionPane.showMessageDialog(ProteoSuiteView.this, 
                                            "Invalid file. The mzq file contains duplications in the DataMatrix on "+
                                            "the AssayQuantLayer or StudyVariableQuantLayer",
                                            "Information", JOptionPane.INFORMATION_MESSAGE);
                                    break;
                                }
                                else{
                                    aObj[iI] = itr.next().toString(); 
                                    iI++;                                    
                                }                                   
                            }
                            model.insertRow(model.getRowCount(), aObj);
                        }
                        //... Tooltip for headers ...//
                        jtProteinQuant.getTableHeader().setDefaultRenderer(new TableCellRenderer() {  
                            final TableCellRenderer defaultRenderer = jtProteinQuant.getTableHeader().getDefaultRenderer();  
                            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row,  
                                                                        int column) {  
                                JComponent component = (JComponent)defaultRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);  
                                component.setToolTipText("" + jtProteinQuant.getColumnName(column));
                                return component;  
                            }
                        }); 
                        jtProteinQuant.setAutoCreateRowSorter(true);

        //============================//
        //... Peptide Quantitation ...//                
        //============================//
                        sMessage = sysutils.getTime()+" - Reading Peptide Quantitation ... ";
                        System.out.println(sMessage);
                        sOutput = sOutput + sMessage+"\n";                        
                        //... Based on the the assay list and study variables we will include the different columns ...//                
                        for (Assay assay : listAssay) {
                            model2.addColumn(assay.getName());
                        }
                        
                        //... Fill rows ...//           

                        //... Getting DataMatrix from AssayQuantLayer ...//
                        PeptideConsensusList pepConsList = unmarshaller.unmarshal(MzQuantMLElement.PeptideConsensusList);
                        if (pepConsList.getAssayQuantLayer().size()>0){
                            List<Row> dataMatrix3 = pepConsList.getAssayQuantLayer().get(0).getDataMatrix().getRow();
                            for(Row row:dataMatrix3){
                                Object[] aObj = new Object[iAssays+1];                                                            
                                PeptideConsensus pepConsensus = (PeptideConsensus) row.getObjectRef();

                                aObj[0] = pepConsensus.getId();
                                List<String> values = row.getValue();       
                                ArrayList al = (ArrayList) values;
                                Iterator<String> itr = al.iterator();
                                int iI = 1;
                                while (itr.hasNext()) {
                                    aObj[iI] = itr.next().toString(); 
                                    iI++;
                                }
                                model2.insertRow(model2.getRowCount(), aObj);
                            }
                        }
                        //... Tooltip for headers ...//
                        jtPeptideQuant.getTableHeader().setDefaultRenderer(new TableCellRenderer() {  
                            final TableCellRenderer defaultRenderer = jtPeptideQuant.getTableHeader().getDefaultRenderer();  
                            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row,  
                                                                        int column) {  
                                JComponent component = (JComponent)defaultRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);  
                                component.setToolTipText("" + jtPeptideQuant.getColumnName(column));
                                return component;  
                            }
                        }); 
                        jtPeptideQuant.setAutoCreateRowSorter(true);                        

        //============================//
        //... Feature Quantitation ...//                
        //============================//
                        sMessage = sysutils.getTime()+" - Reading Feature Quantitation ... ";
                        System.out.println(sMessage);
                        sOutput = sOutput + sMessage+"\n";                        
                        //... Based on the the assay list and study variables we will include the different columns ...//
                        for (Assay assay : listAssay) {
                            model3.addColumn(assay.getName());
                        }
                        //... Fill rows ...//

                        //... Getting DataMatrix from AssayQuantLayer ...//
                        FeatureList featureList = unmarshaller.unmarshal(MzQuantMLElement.FeatureList);
                        if (featureList.getMS2AssayQuantLayer().size()>0){
                            List<Row> dataMatrix4 = featureList.getMS2AssayQuantLayer().get(0).getDataMatrix().getRow();
                            for(Row row:dataMatrix4){
                                Object[] aObj = new Object[iAssays+1];
                                Feature feature = (Feature) row.getObjectRef();

                                aObj[0] = feature.getId();
                                List<String> values = row.getValue();
                                ArrayList al = (ArrayList) values;
                                Iterator<String> itr = al.iterator();
                                int iI = 1;
                                while (itr.hasNext()) {
                                    aObj[iI] = itr.next().toString(); 
                                    iI++;
                                }
                                model3.insertRow(model3.getRowCount(), aObj);
                            }   
                        }                        
                        jtaLog.setText(sOutput);
                        
                        //... Tooltip for headers ...//
                        jtFeatureQuant.getTableHeader().setDefaultRenderer(new TableCellRenderer() {  
                            final TableCellRenderer defaultRenderer = jtFeatureQuant.getTableHeader().getDefaultRenderer();  
                            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row,  
                                                                        int column) {  
                                JComponent component = (JComponent)defaultRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);  
                                component.setToolTipText("" + jtFeatureQuant.getColumnName(column));
                                return component;  
                            }
                        }); 
                        jtFeatureQuant.setAutoCreateRowSorter(true);                        
                    }
                    jtpProperties.setSelectedIndex(4);
                    jtpMzQuantMLDetail.setSelectedIndex(1);

                    progressBarDialog.setVisible(false);
                    progressBarDialog.dispose();
                }
            }.start();
        }
    }         
    /*----------------------------------------------------------------
     * Loading mzML View
     * @param iIndex - index for the aMzMLUnmarshaller arraylist
     * @return void
     -----------------------------------------------------------------*/
    private void loadMzMLView(int iIndex) 
    {                            
        //... Check if not previously loaded ...//
        if (!jtRawFiles.getValueAt(iIndex, 0).toString().equals(jlFileNameMzMLText.getText())){
            final int iIndexRef = iIndex;
            final ProgressBarDialog progressBarDialog = new ProgressBarDialog(this, true, "LoadMZMLView");
            final Thread thread = new Thread(new Runnable(){
                @Override
                public void run(){
                    //... Progress Bar ...//
                    progressBarDialog.setTitle("Loading mzML data");
                    progressBarDialog.setTaskName("Please wait while loading the data ...");
                    progressBarDialog.setVisible(true);
                }
            }, "ProgressBarDialog");
            thread.start();
            new Thread("LoadMZMLView"){
                @Override
                public void run(){
                    //... Loading table ...//
                    DefaultTableModel model = new DefaultTableModel(){
                        Class[] types = new Class[]{Integer.class, String.class, String.class, Float.class, Float.class, Float.class, String.class};
                        @Override  
                        public Class getColumnClass(int columnIndex) {  
                            return types [columnIndex];  
                        }
                    };
                    jtMzML.setModel(model);
                    model.addColumn("Index");
                    model.addColumn("ID");
                    model.addColumn("MS");
                    model.addColumn("Base peak m/z");
                    model.addColumn("Base peak int");
                    model.addColumn("RT (sec)");
                    model.addColumn("Precurs m/z");
                    String sOutput="";

                    MzMLUnmarshaller unmarshaller = aMzMLUnmarshaller.get(iIndexRef);

                    //... File Name and Version ...//
                    jlFileNameMzMLText.setText(unmarshaller.getMzMLId()+".mzML");
                    sOutput = "<b>mzML Version:</b> <font color='red'>" + unmarshaller.getMzMLVersion() + "</font><br />";

                    //... File Content ...//
                    sOutput = sOutput + "<b>File Content:</b><br />";
                    FileDescription fdList = unmarshaller.unmarshalFromXpath("/fileDescription", FileDescription.class);
                    List<CVParam> fileContent = fdList.getFileContent().getCvParam();
                    for (Iterator lCVParamIterator = fileContent.iterator(); lCVParamIterator.hasNext();){
                        CVParam lCVParam = (CVParam) lCVParamIterator.next();
                        sOutput = sOutput + " - " + lCVParam.getName().trim()+"<br />";
                    } 
                    //... Source File ...//
                    sOutput = sOutput + "<b>Source File:</b><br />";
                    List<CVParam> sourceParam = fdList.getSourceFileList().getSourceFile().get(0).getCvParam();
                    for (Iterator lCVParamIterator = sourceParam.iterator(); lCVParamIterator.hasNext();){
                        CVParam lCVParam = (CVParam) lCVParamIterator.next();
                        sOutput = sOutput + " - " + lCVParam.getName().trim()+"<br />";
                    } 
                    sOutput = sOutput + "<b>Software:</b><br />";                
                    uk.ac.ebi.jmzml.model.mzml.SoftwareList softList = unmarshaller.unmarshalFromXpath("/softwareList", uk.ac.ebi.jmzml.model.mzml.SoftwareList.class);
                    List<CVParam> softParam = softList.getSoftware().get(0).getCvParam();
                    for (Iterator lCVParamIterator = softParam.iterator(); lCVParamIterator.hasNext();){
                        CVParam lCVParam = (CVParam) lCVParamIterator.next();
                        sOutput = sOutput + " - " + lCVParam.getName().trim() + "<br />";
                    }                                        
                    jepMzMLView.setText(sOutput);
                    jtpProperties.setSelectedIndex(0);

                    //... Reading spectrum data ...//
                    MzMLObjectIterator<Spectrum> spectrumIterator = unmarshaller.unmarshalCollectionFromXpath("/run/spectrumList/spectrum", Spectrum.class);
                    String msLevel = "";
                    float basePeakMZ = 0;
                    float basePeakInt = 0;
                    String unitRT = "";
                    float rt = 0;
                    float rtMin = 0;
                    int iCount = 1;
                    double dScansMS1 = 0;
                    double dScansMS2 = 0;
                    while (spectrumIterator.hasNext()){ //... Reading MS data ...//
                        Spectrum spectrum = spectrumIterator.next();
                        List<CVParam> specParam = spectrum.getCvParam();
                        for (Iterator lCVParamIterator = specParam.iterator(); lCVParamIterator.hasNext();){
                           CVParam lCVParam = (CVParam) lCVParamIterator.next();
                           if (lCVParam.getAccession().equals("MS:1000511")) {
                               msLevel = lCVParam.getValue().trim();
                               if (msLevel.equals("1")){ //... Type of data ...//
                                   dScansMS1++;
                               }
                               else{
                                   dScansMS2++;
                               }
                           }
                           if (lCVParam.getAccession().equals("MS:1000504")){
                               basePeakMZ = Float.parseFloat(lCVParam.getValue().trim());
                           }
                           if (lCVParam.getAccession().equals("MS:1000505")){
                               basePeakInt = Float.parseFloat(lCVParam.getValue().trim());
                           }
                        }
                        List<CVParam> scanParam = spectrum.getScanList().getScan().get(0).getCvParam();
                        for (Iterator lCVParamIterator = scanParam.iterator(); lCVParamIterator.hasNext();){
                            CVParam lCVParam = (CVParam) lCVParamIterator.next();
                            if (lCVParam.getAccession().equals("MS:1000016")){ //... Extracting RT ...//
                                unitRT = lCVParam.getUnitAccession().trim();
                                if (unitRT.equals("UO:0000031")){ //... Convert RT into seconds ...//
                                    rt = Float.parseFloat(lCVParam.getValue().trim());
                                    rtMin = rt;
                                    rt = rt * 60;
                                }
                                else{   //... Convert RT into minutes ...//
                                    rt = Float.parseFloat(lCVParam.getValue().trim());
                                    rtMin = rt/60;
                                }
                            }
                        }
                        PrecursorList plist = spectrum.getPrecursorList(); //... Get precursor ion ...//
                        float precursMZ = 0.0f;
                        if (plist != null){
                            if (plist.getCount().intValue() == 1){
                                List<CVParam> scanPrecParam = plist.getPrecursor().get(0).getSelectedIonList().getSelectedIon().get(0).getCvParam();

                                //... Detect parent ion m/z and charge ...//
                                for (Iterator lCVParamIterator = scanPrecParam.iterator(); lCVParamIterator.hasNext();){
                                    CVParam lCVParam = (CVParam) lCVParamIterator.next();
                                    if (lCVParam.getAccession().equals("MS:1000744")){
                                        precursMZ = Float.parseFloat(lCVParam.getValue().trim());
                                    }
                                }
                            }
                        }
                        
                        Object color = null;
                        if (msLevel.equals("1")){
                            color = new Color(204, 192, 218);
                        }else{
                            color = new Color(252, 213, 218);
                        }
                        if(precursMZ>0.0){
                            model.insertRow(model.getRowCount(), new Object[]{
                                Integer.parseInt(spectrum.getIndex().toString()), 
                                spectrum.getId().toString(),
                                msLevel,
                                Float.parseFloat(String.format("%.2f", basePeakMZ)),
                                Float.parseFloat(String.format("%.2f", basePeakInt)),
                                Float.parseFloat(String.format("%.2f", rt)),
                                String.format("%.4f", precursMZ)                
                                });                            
                        }else{
                            model.insertRow(model.getRowCount(), new Object[]{
                                Integer.parseInt(spectrum.getIndex().toString()), 
                                spectrum.getId().toString(),
                                msLevel,
                                Float.parseFloat(String.format("%.2f", basePeakMZ)),
                                Float.parseFloat(String.format("%.2f", basePeakInt)),
                                Float.parseFloat(String.format("%.2f", rt)),
                                ""                
                                });
                        }
                        iCount++;
                        //jtMzML.setDefaultRenderer(Color.class, new MSLevelRender(true, msLevel));
                    }
                    jtMzML.getTableHeader().setDefaultRenderer(new TableCellRenderer() {  
                        final TableCellRenderer defaultRenderer = jtMzML.getTableHeader().getDefaultRenderer();  
                        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row,  
                                                                    int column) {  
                            JComponent component = (JComponent)defaultRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);  
                            component.setToolTipText("" + jtMzML.getColumnName(column));
                            return component;  
                        }  
                    }); 
        
                    jtMzML.setAutoCreateRowSorter(true);
                    progressBarDialog.setVisible(false);
                    progressBarDialog.dispose();
                }
            }.start();         
        }
    }   
    /*---------------------------------------
     * Displays MGF raw data
     * @param iIndex - index for the array
     * @return void
     ----------------------------------------*/
    private void loadMGFView(int iIndex) 
    {  
        //... Check if not previously loaded ...//
        if (!jtRawFiles.getValueAt(iIndex, 0).toString().equals(jlFileNameMGFText.getText())){
            final String sFileNameRef = jtRawFiles.getValueAt(iIndex, 0).toString();
            final String sFilePathRef = jtRawFiles.getValueAt(iIndex, 1).toString();
            final ProgressBarDialog progressBarDialog = new ProgressBarDialog(this, true, "LoadMGF");        
            final Thread thread = new Thread(new Runnable(){
                @Override
                public void run(){
                    //... Progress Bar ...//
                    progressBarDialog.setTitle("Loading MGF data");
                    progressBarDialog.setVisible(true);
                }
            }, "ProgressBarDialog");
            thread.start();
            new Thread("LoadMGF"){
                @Override
                public void run(){                
                    DefaultTableModel model = new DefaultTableModel(){
                        Class[] types = new Class[]{Integer.class, String.class, Double.class, String.class, Long.class};
                        @Override  
                        public Class getColumnClass(int columnIndex) {  
                            return types [columnIndex];  
                        }
                    };
                    jtMGF.setModel(model);
                    model.addColumn("Index");
                    model.addColumn("Scan Title");
                    model.addColumn("Peptide Mass");
                    model.addColumn("Charge");
                    model.addColumn("Reference Line");        

                    jlFileNameMGFText.setText(sFileNameRef);  
                    jtpProperties.setSelectedIndex(1);       

                    //... Reading file ...//
                    try{
                        BufferedReader in = new BufferedReader(new FileReader(sFilePathRef));
                        String title = "", charge="", sPepmass="";            
                        double pepmass=0.0;
                        long refLine=0;
                        String string = null;

                        long lineNum = 0; 
                        int iCount = 0;
                        while ((string = in.readLine()) != null) {
                            lineNum++;
                            string = string.trim();
                            if (string.startsWith("BEGIN IONS")) {
                                break;
                            }
                        }                    
                        while ((string = in.readLine()) != null) {
                            lineNum++;
                            string = string.trim();
                            if (string.equals("")) {
                                continue;
                            }else if (string.startsWith("BEGIN IONS")) {
                                continue;
                            }else if (string.startsWith("TITLE")) {
                                title = string.substring(6);
                                continue;
                            }else if (string.startsWith("RTINSECONDS")) {
                                continue;
                            }else if (string.startsWith("PEPMASS")) {             
                                //... Check for double values ...//
                                sPepmass = string.substring(8);
                                if (sPepmass.contains(" ")){
                                    sPepmass = sPepmass.substring(0, sPepmass.indexOf(" "));
                                }
                                pepmass = Double.parseDouble(sPepmass);                            
                                continue;
                            }else if (string.startsWith("CHARGE")) {
                                charge = string.substring(7);
                                refLine = lineNum;
                                continue;
                            }else if (string.charAt(0) >= '1' && string.charAt(0) <= '9') {
                                continue;
                            }else if (string.contains("END IONS")) {
                                iCount++;
                                //... Insert rows ...//
                                model.insertRow(model.getRowCount(), new Object[]{
                                iCount,
                                title,
                                pepmass,
                                charge,
                                refLine});
                                continue;
                            }else{
                                continue;
                            }
                        }
                    }catch(Exception e){
                        System.exit(1);
                    }    
                    jtMGF.getTableHeader().setDefaultRenderer(new TableCellRenderer() {  
                        final TableCellRenderer defaultRenderer = jtMGF.getTableHeader().getDefaultRenderer();  
                        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row,  
                                                                    int column) {  
                            JComponent component = (JComponent)defaultRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);  
                            component.setToolTipText("" + jtMGF.getColumnName(column));
                            return component;  
                        }  
                    });

                    jtMGF.setAutoCreateRowSorter(true);
                    progressBarDialog.setVisible(false);
                    progressBarDialog.dispose();
                }
            }.start();                    
        }        
    }        
    /*----------------------------------
     * Display Mascot XML files
     * @param sFileName - File name
     * @param sFilePath - File location
     * @return void
     -----------------------------------*/
    private void loadMascotView(String sFileName, String sFilePath) 
    {  
        DefaultTableModel model = new DefaultTableModel(){
            Class[] types = new Class[]{Integer.class, String.class, String.class, String.class, Float.class, Float.class, Integer.class,
                                        Float.class, Integer.class, String.class, Float.class};
            @Override  
            public Class getColumnClass(int columnIndex) {  
                return types [columnIndex];  
            }
        };
        jtMascotXMLView.setModel(model);
        model.addColumn("Index");
        model.addColumn("Protein");
        model.addColumn("Peptide");
        model.addColumn("Composition");
        model.addColumn("Exp m/z");
        model.addColumn("Exp Mr");
        model.addColumn("Charge");
        model.addColumn("Score");       
        model.addColumn("Scan");
        model.addColumn("Scan ID");
        model.addColumn("RT (sec)");
                       
        jtpProperties.setSelectedIndex(3);
        jlFileNameMascotXMLText.setText(sFileName);
        
        //... Open mascot file and extract identifications ...//
        File file = new File(sFilePath);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            Node nodeLst = doc.getElementsByTagName("hits").item(0); //... Reading hits ...//
            NodeList hitsList = nodeLst.getChildNodes();                             
            
            //... Initializing proteinId, peptide sequence and other variables ...//
            Integer iIndex = 0;
            String proteinId = "";
            String peptideSeq = "";
            String peptideCompos = "";
            float hitScore = 0.0f;
            float retTime = 0.0f;
            String idRef = "";
            int scanNumber = 0;
            float parIonMz = 0.0f;
            float parIonMr = 0.0f;
            
            String[] modification = new String[6];
            int numMods = 1;
            boolean isVariableMod = false;
            int dbCharge = -1;
            String mascotPos = "";
            int iProteins = 0;
            int iPeptides = 0;
            String sChainToInsert="";
                        
            for (int iI = 0; iI < hitsList.getLength(); iI++){ //... Identifying all protein hits from the search ...//                
                NodeList subHitsList = hitsList.item(iI).getChildNodes();
                for (int iJ = 0; iJ < subHitsList.getLength(); iJ++) {
                    Node proteinItem = subHitsList.item(iJ);
                    if (proteinItem.getNodeType() == Node.ELEMENT_NODE) {
                        if (proteinItem.getNodeName().equals("protein")){ //... Reading the protein ...//                        
                            iProteins++;
                            proteinId = proteinItem.getAttributes().item(0).getTextContent().toString();
                            NodeList subProteinList = proteinItem.getChildNodes(); //... Protein subnodes ...//
                            for (int iK = 0; iK < subProteinList.getLength(); iK++) {
                                Node peptideItem = subProteinList.item(iK);
                                if (peptideItem.getNodeType() == Node.ELEMENT_NODE) {
                                    if (peptideItem.getNodeName().equals("peptide")){ //... Reading peptides ...//
                                        iPeptides++;                                        
                                        NodeList peptideDataList = peptideItem.getChildNodes(); //... Peptide subnodes ...//                                        
                                        //... Initializing variables ...//
                                        for (int iA=0;iA<6;iA++){ //... Cleaning array ...//
                                            modification[iA] = null;
                                        }
                                        int iContMod = 0;
                                        isVariableMod = false;
                                        numMods = -1;
                                        dbCharge = -1;
                                        sChainToInsert = "";
                                        for (int iL = 0; iL < peptideDataList.getLength(); iL++) {
                                            Node peptideElem = peptideDataList.item(iL);
                                            if (peptideElem.getNodeType() == Node.ELEMENT_NODE) {
                                                if (peptideElem.getNodeName().equals("pep_seq")){
                                                    peptideSeq = peptideElem.getTextContent().toString();                                                    
                                                    //... Calculate the residue composition ...//
                                                    peptideCompos = getResidueComposition(peptideSeq);
                                                } 
                                                else if (peptideElem.getNodeName().equals("pep_exp_mz")){
                                                    parIonMz = Double.valueOf(peptideElem.getTextContent().toString()).floatValue();
                                                } 
                                                else if (peptideElem.getNodeName().equals("pep_exp_mr")) {
                                                    parIonMr = Double.valueOf(peptideElem.getTextContent().toString()).floatValue();
                                                }                                                 
                                                else if (peptideElem.getNodeName().equals("pep_exp_z")) {
                                                    dbCharge = Integer.valueOf(peptideElem.getTextContent().toString()).intValue();
                                                } 
                                                else if (peptideElem.getNodeName().equals("pep_score")) {
                                                    hitScore = Double.valueOf(peptideElem.getTextContent().toString()).floatValue();
                                                } 
                                                else if (hitScore >= 20){ //... Verify if hits are over minimum threshold ...//
                                                    if (peptideElem.getNodeName().equals("pep_var_mod")){ //... Modification variables ...//                                                    
                                                        String sModVariable = peptideElem.getTextContent().toString();
                                                        
                                                        String[] differentMods = new String[3];
                                                        differentMods = sModVariable.split(";"); //... Check if different modifications are separated by colons ..//
                                                        if (differentMods[0].equals("")){ //... Verify if has data ...//                                                        
                                                            isVariableMod = false; 
                                                        }
                                                        else{
                                                            for (int iDiffMod=0; iDiffMod < differentMods.length; iDiffMod++){
                                                                //... This code parses the number of modifications (1, 2, n) once we have identified the different modif variables ...//                                                            
                                                                if (differentMods[iDiffMod].startsWith(" ")){
                                                                     differentMods[iDiffMod] = differentMods[iDiffMod].substring(1, differentMods[iDiffMod].length());
                                                                }
                                                                if (differentMods[iDiffMod].length() > 0) {
                                                                    String[] multipleMods = new String[3]; //... New array for multiple modifications in different modifications ...//
                                                                    numMods = 1;
                                                                    if (differentMods[iDiffMod].matches("^\\d+[\\w|\\D|\\S|\\s|\\W]*")) {
                                                                        multipleMods = differentMods[iDiffMod].split(" ");
                                                                        numMods = Integer.valueOf(multipleMods[0]).intValue();
                                                                        sChainToInsert = differentMods[iDiffMod].replace(numMods + " ", "");
                                                                        numMods = 1;
                                                                    }
                                                                    else{
                                                                        sChainToInsert = differentMods[iDiffMod];
                                                                        numMods = 1;
                                                                    }
                                                                    for (int iFound=0; iFound<numMods; iFound++){
                                                                        modification[iContMod] = sChainToInsert;
                                                                        iContMod++;
                                                                    }
                                                                } 
                                                            }
                                                            isVariableMod = true;         
                                                        }
                                                    } 
                                                    else if (peptideElem.getNodeName().equals("pep_var_mod_pos") && isVariableMod){
                                                        mascotPos = peptideElem.getTextContent().toString();
                                                        mascotPos = mascotPos.replace(".", "");
                                                    } 
                                                    else if (peptideElem.getNodeName().equals("pep_scan_title")){ //... Only in some cases the rt is specified in the scan title ...//                                                    
                                                        if (peptideElem.getTextContent().toString().indexOf("rt:")>0){ //... Option 1, reading on scan title ...//                                                        
                                                            String myTmpString = peptideElem.getTextContent().toString();
                                                            int ind = 0;
                                                            int ind1 = 0;

                                                            //... Get retention time ...//
                                                            ind = myTmpString.indexOf("(rt:");
                                                            if(ind>0){
                                                                ind1 = myTmpString.indexOf(")");
                                                                retTime = Double.valueOf(myTmpString.substring(ind + 4, ind1)).floatValue();
                                                            }
                                                            else{
                                                                retTime=0;
                                                            }
                                                            
                                                            //... Get ID ref ...//
                                                            ind = myTmpString.indexOf("(id:");
                                                            if(ind>0){
                                                                ind1 = myTmpString.lastIndexOf(")");
                                                                idRef = myTmpString.substring(ind + 4, ind1);
                                                            }
                                                            else{
                                                                idRef="";
                                                            }                                                            
                                                            
                                                            //... Get scan number ...//
                                                            ind = myTmpString.indexOf("Scan:");
                                                            if(ind>-1){
                                                                ind1 = myTmpString.indexOf(",");
                                                                scanNumber = Double.valueOf(myTmpString.substring(ind + 5, ind1)).intValue();
                                                            }
                                                            else{
                                                                scanNumber=0;
                                                            }
                                                        } 
                                                        else {
                                                            //... Read mzML file or MGF (TO DO) ...//                                                            
                                                            retTime = 0;                                                            
                                                        }
                                                        model.insertRow(model.getRowCount(), new Object[]{
                                                            iIndex,
                                                            proteinId,
                                                            peptideSeq, 
                                                            peptideCompos,
                                                            parIonMz,
                                                            parIonMr,
                                                            dbCharge,
                                                            hitScore,
                                                            scanNumber,
                                                            idRef,
                                                            retTime});
                                                        iIndex++;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            jtMascotXMLView.setAutoCreateRowSorter(true);
        } 
        catch (Exception e) {
            System.out.println("LoadMascotIdent: Exception while reading " + file + "\n" + e);
            System.exit(1);
        }
    }    
    /*------------------------------------------------
     * Get molecular compisition from a given peptide
     * @param sPeptide - Peptide sequence
     * @return Molecular composition
     -------------------------------------------------*/
    private String getResidueComposition(String sPeptide) 
    {
        /*         
         * --------------------------------------------------------------
         * Name            3-Sym  1-Sym     Mono        Avg     Residues
         * --------------------------------------------------------------
         * Alanine         Ala     A        71.03711    71.08   C3H5NO
         * Arginine        Arg     R        156.10111   156.2   C6H12N4O
         * Asparagine      Asn     N        114.04293   114.1   C4H6N2O2
         * Aspartic Acid   Asp     D        115.02694   115.1   C4H5NO3
         * Cysteine        Cys     C        103.00919   103.1   C3H5NOS
         * Glutamic Acid   Glu     E        129.04259   129.1   C5H7NO3 
         * Glutamine       Gln     Q        128.05858   128.1   C5H8N2O2
         * Glycine         Gly     G        57.02146    57.05   C2H3NO
         * Histidine       His     H        137.05891   137.1   C6H7N3O
         * Isoleucine      Ile     I        113.08406   113.2   C6H11NO
         * Leucine         Leu     L        113.08406   113.2   C6H11NO
         * Lysine          Lys     K        128.09496   128.2   C6H12N2O
         * Methionine      Met     M        131.04049   131.2   C5H9NOS
         * Phenyalanine    Phe     F        147.06841   147.2   C9H9NO
         * Proline         Pro     P        97.05276    97.12   C5H7NO
         * Serine          Ser     S        87.03203    87.08   C3H5NO2
         * Threonine       Thr     T        101.04768   101.1   C4H7NO2
         * Tryptophan      Trp     W        186.07931   186.2   C11H10N2O
         * Tyrosine        Tyr     Y        163.06333   163.2   C9H9NO2
         * Valine          Val     V        99.06841    99.13   C5H9NO
         * --------------------------------------------------------------
        */
        
        int iCarb=0, iHydro=3, iNitro=0, iOxy=1, iSulf=0; //... Water molecule (H2O) + H1 ...//
        String sPeptideRet="";
        for (int iI = 0; iI < sPeptide.length(); iI++) {
            switch (sPeptide.toUpperCase().charAt(iI)) {
                case 'A': {
                    iCarb+=3;
                    iHydro+=5;
                    iNitro++;
                    iOxy++;
                    break;
                }   
                case 'R': {
                    iCarb+=6;
                    iHydro+=12;
                    iNitro+=4;
                    iOxy++;
                    break;
                }                       
                case 'N': {
                    iCarb+=4;
                    iHydro+=6;
                    iNitro+=2;
                    iOxy+=2;
                    break;
                }   
                case 'D': {
                    iCarb+=4;
                    iHydro+=5;
                    iNitro++;
                    iOxy+=3;
                    break;
                }   
                case 'C': {
                    iCarb+=3;
                    iHydro+=5;
                    iNitro++;
                    iOxy++;
                    iSulf++;
                    break;
                }   
                case 'E': {
                    iCarb+=5;
                    iHydro+=7;
                    iNitro++;
                    iOxy+=3;
                    break;
                }   
                case 'Q': {
                    iCarb+=5;
                    iHydro+=8;
                    iNitro+=2;
                    iOxy+=2;
                    break;
                }   
                case 'G': {
                    iCarb+=2;
                    iHydro+=3;
                    iNitro++;
                    iOxy++;
                    break;
                }   
                case 'H': {
                    iCarb+=6;
                    iHydro+=7;
                    iNitro+=3;
                    iOxy++;
                    break;
                }   
                case 'I': {
                    iCarb+=6;
                    iHydro+=11;
                    iNitro++;
                    iOxy++;
                    break;
                }   
                case 'L': {
                    iCarb+=6;
                    iHydro+=11;
                    iNitro++;
                    iOxy++;
                    break;
                }   
                case 'K': {
                    iCarb+=6;
                    iHydro+=12;
                    iNitro+=2;
                    iOxy++;
                    break;
                }   
                case 'M': {
                    iCarb+=5;
                    iHydro+=9;
                    iNitro++;
                    iOxy++;
                    iSulf++;
                    break;
                }   
                case 'F': {
                    iCarb+=9;
                    iHydro+=9;
                    iNitro++;
                    iOxy++;
                    break;
                }   
                case 'P': {
                    iCarb+=5;
                    iHydro+=7;
                    iNitro++;
                    iOxy++;
                    break;
                }   
                case 'S': {
                    iCarb+=3;
                    iHydro+=5;
                    iNitro++;
                    iOxy+=2;
                    break;
                }   
                case 'T': {
                    iCarb+=4;
                    iHydro+=7;
                    iNitro++;
                    iOxy+=2;
                    break;
                }   
                case 'W': {
                    iCarb+=11;
                    iHydro+=10;
                    iNitro+=2;
                    iOxy++;
                    break;
                }   
                case 'Y': {
                    iCarb+=9;
                    iHydro+=9;
                    iNitro++;
                    iOxy+=2;
                    break;
                }   
                case 'V': {
                    iCarb+=5;
                    iHydro+=9;
                    iNitro++;
                    iOxy++;
                    break;
                }    
            }
        }
        if (iCarb>0){
            sPeptideRet = "C" + iCarb;
        }
        if (iHydro>0){
            sPeptideRet = sPeptideRet + "H" + iHydro;
        }
        if (iNitro>0){
            sPeptideRet = sPeptideRet + "N" + iNitro;
        }
        if (iOxy>0){
            sPeptideRet = sPeptideRet + "O" + iOxy;
        }
        if (iSulf>0){
            sPeptideRet = sPeptideRet + "S" + iSulf;
        }        
        return sPeptideRet;
    }    
    /*-------------------------------------------
     * Initialise project settings (all modules) 
     * @param void
     * @return void
     --------------------------------------------*/
    private void initProjectValues() 
    {        
        //... Loading values from config file ...//
        initSettings();                 //... From config file (workspace) ...//
        initProjectStatusIcons();       //... Project pipeline ...//
        
        //... Initialising components ...//
        initTables();                  
        initTextAreas();
        initViews();
        initButtons();
        
        //... Values ...//
        Locale.setDefault(new Locale("en", "UK"));
    }     
    /*------------------------------------------------------
     * Initialise project settings (configuration XML file) 
     * @param void
     * @return void
     -------------------------------------------------------*/
    private void initSettings() 
    {
        //... Validate if config file exists ...//
        boolean exists = (new File("config.xml")).exists();
        if (exists){
            //... Read files using SAX (get workspace) ...//
            try {
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser saxParser = factory.newSAXParser();

                DefaultHandler handler = new DefaultHandler(){                   
                    boolean bWorkspace = false;
                    
                    @Override
                    public void startElement(String uri, String localName, String qName, Attributes attributes)
                    throws SAXException {
                        if (qName.equalsIgnoreCase("workspace")) {
                            bWorkspace = true;
                        }
                    }
                    @Override
                    public void characters(char ch[], int start, int length)
                    throws SAXException {
                        if (bWorkspace) {
                            bWorkspace = false;
                            sWorkspace = new String(ch, start, length);                            
                        }
                    }
                };
                saxParser.parse("config.xml", handler);

            } catch (Exception e) {
              e.printStackTrace();
            }         
        }
        else
        {            
            sWorkspace = "";            
            String sMessage = "The config.xml file was not found, please make sure that this file exists \n";
            sMessage = sMessage + "under your installation directory. ProteoSuite will continue launching, however \n";
            sMessage = sMessage + "it is recommended that you copy the file as indicated in the readme.txt file.";
            JOptionPane.showMessageDialog(this, sMessage, "Warning", JOptionPane.INFORMATION_MESSAGE);            
        }
        sProjectName = "New";
        bProjectModified = false;
        uptSaveProjectStatus();
        uptCloseProjectStatus();
        uptTitle();
    }
    /*---------------------------------
     * Initialise project status icons
     * @param void
     * @return void
     ----------------------------------*/
    private void initProjectStatusIcons()
    {    
        //... Project status pipeline ...//
        Icon loadRawFilesIcon = new ImageIcon(getClass().getClassLoader().getResource("images/empty.gif"));
        jlRawFilesStatus.setIcon(loadRawFilesIcon);
        jlIdentFilesStatus.setIcon(loadRawFilesIcon);
        jlQuantFilesStatus.setIcon(loadRawFilesIcon);
        jcbTechnique.setSelectedIndex(0);
    }    
    /*--------------------------
     * Initialise table values 
     * @param void
     * @return void
     --------------------------*/
    private void initTables()
    {
        DefaultTableModel model = new DefaultTableModel();        
        jtRawFiles.setModel(model);
        model.addColumn("Name");
        model.addColumn("Path");        
        model.addColumn("Type");
        model.addColumn("Version");
        
        DefaultTableModel model2 = new DefaultTableModel();        
        jtIdentFiles.setModel(model2);
        model2.addColumn("Name");      
        model2.addColumn("Path");        
        model2.addColumn("Type");
        model2.addColumn("Version");
        model2.addColumn("Raw File");
        
        DefaultTableModel model3 = new DefaultTableModel();
        jtQuantFiles.setModel(model3);
        model3.addColumn("Name");
        model3.addColumn("Path");
        model3.addColumn("Type");
        model3.addColumn("Version");
        
        DefaultTableModel model4 = new DefaultTableModel();        
        jtRawData.setModel(model4);
        model4.addColumn("Index");
        model4.addColumn("m/z");
        model4.addColumn("Intensity");
        
        DefaultTableModel model5 = new DefaultTableModel();
        jtMzML.setModel(model5);     
        model5.addColumn("Index");
        model5.addColumn("ID");
        model5.addColumn("MS");
        model5.addColumn("Base peak m/z");
        model5.addColumn("Base peak int");
        model5.addColumn("RT (sec)");
        model5.addColumn("Precurs m/z");
        
        DefaultTableModel model6 = new DefaultTableModel();
        jtMGF.setModel(model6);     
        model6.addColumn("Index");
        model6.addColumn("Scan Title");
        model6.addColumn("Peptide Mass");
        model6.addColumn("Charge");
        model6.addColumn("Reference Line");
        
        DefaultTableModel model7 = new DefaultTableModel();
        jtMzId.setModel(model7);     
        model7.addColumn("Index");
        model7.addColumn("Protein");
        model7.addColumn("Peptide");
        model7.addColumn("Rank");
        model7.addColumn("Score");
        model7.addColumn("Spectrum ID");
        
        DefaultTableModel model8 = new DefaultTableModel();
        jtMascotXMLView.setModel(model8);     
        model8.addColumn("Index");
        model8.addColumn("Protein");
        model8.addColumn("Peptide");
        model8.addColumn("Composition");
        model8.addColumn("Exp Mz");
        model8.addColumn("Exp Mr");
        model8.addColumn("Charge");
        model8.addColumn("Score");
        model8.addColumn("Scan");
        model8.addColumn("Scan ID");
        model8.addColumn("RT (sec)");
        
        DefaultTableModel model9 = new DefaultTableModel();
        jtPeptideQuant.setModel(model9);     
        model9.addColumn("Peptide");
        model9.addColumn("Label 1");
        model9.addColumn("Label 2");
        model9.addColumn("Label 3");
        model9.addColumn("Label 4");
        
        DefaultTableModel model10 = new DefaultTableModel();
        jtProteinQuant.setModel(model10);     
        model10.addColumn("Protein");
        model10.addColumn("Label 1");
        model10.addColumn("Label 2");
        model10.addColumn("Label 3");
        model10.addColumn("Label 4");
        
        DefaultTableModel model11 = new DefaultTableModel();
        jtFeatureQuant.setModel(model11);     
        model11.addColumn("Feature");
        model11.addColumn("Label 1");
        model11.addColumn("Label 2");
        model11.addColumn("Label 3");
        model11.addColumn("Label 4");
    }
    /*---------------------------
     * Initialise Text Areas
     * @param void
     * @return void
     ----------------------------*/
    private void initTextAreas()
    {
        jtaLog.setText("");
        jepMzMLView.setText("");
        jepMGFView.setText("");
        jlFileNameMzMLText.setText("");
        jlFileNameMzIDText.setText("");
        jlFileNameMzQText.setText("");
        jlFileNameMGFText.setText("");
        jlFileNameMascotXMLText.setText("");
        jepMzIDView.setText("");
        jepMZQView.setText("");
        jepMascotXMLView.setText("");
    }    
    /*---------------------------------
     * Initialise project buttons 
     * @param void
     * @return void
     ----------------------------------*/
    private void initButtons()
    {    
        //... Project buttons ...//
        jbExportMzMLExcel.setEnabled(false);
        jbExportMGFExcel.setEnabled(false);
        jbExportMzIdentMLExcel.setEnabled(false);
        jbExportMascotXMLExcel.setEnabled(false);
        jbExportPepMZQExcel.setEnabled(false);
        jbExportProtMZQExcel.setEnabled(false);
        jbExportFeatMZQExcel.setEnabled(false);
    }    
    /* ----------------------
     * Initialise Windows
     * @param void
     * @return void
     ------------------------*/
    private void initViews()
    {
        jdpMS.removeAll();
        jdpTIC.removeAll();
    }        
    /*-----------------------------------------------------
     * Open the mzq file which will be the PS file project
     * @param void
     * @return void
     ------------------------------------------------------*/
    private void openProject() 
    {        
        //... Load project settings from a mzq file ...//
        //... Selecting file(s) ...//
        JFileChooser chooser = new JFileChooser(sPreviousLocation);
        chooser.setDialogTitle("Select the file(s) to analyze");        

        //... Applying file extension filters ...//
        FileNameExtensionFilter filter3 = new FileNameExtensionFilter("mzQuantML Files (*.mzq, *.mzq.gz)", "mzq");
        //... Filters must be in descending order ...//
        chooser.addChoosableFileFilter(filter3);
        //... Enable multiple file selection ...//
        chooser.setMultiSelectionEnabled(false);

        //... Setting default directory ...//
        if (sPreviousLocation == null || sPreviousLocation.contains("")){
            chooser.setCurrentDirectory(new java.io.File(sWorkspace)); //... If not found it goes to Home, exception not needed ...//   
        }
        else{
            chooser.setCurrentDirectory(new java.io.File(sPreviousLocation)); //... If not found it goes to Home, exception not needed ...//               
        }
        //... Retrieving selection from user ...//
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION){
            File file = chooser.getSelectedFile();
	    if (file != null){
                if (file.getName().toLowerCase().indexOf(".mzq") >0){                    
                    //... Unmarshall mzquantml file ...//
                    System.out.println(sysutils.getTime()+" - (Validation) Validating " + file.getPath());
                    Validator validator = XMLparser.getValidator(MZQ_XSD);
                    boolean validFlag = XMLparser.validate(validator, file.getPath());
                    if(!validFlag){
                        JOptionPane.showMessageDialog(this, "The file " + file + " is not valid. \n Please make sure that the file is a valid mzq file (Release "+MZQ_XSD+")", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    else{
                        boolean isOK = false;
                        aMzQUnmarshaller.clear();
                        final DefaultTableModel model = new DefaultTableModel();
                        jtQuantFiles.setModel(model);
                        model.addColumn("Name");
                        model.addColumn("Path");
                        model.addColumn("Type");
                        model.addColumn("Version");
                        isOK = unmarshalMzQMLFile(model, file);
                        if (isOK){ 
                            isOK = loadProjectFromMZQ(0, file);                                                
                            if (isOK){
                                bProjectModified = false;
                                
                                sProjectName = file.getName();
                                uptStatusPipeline();
                                uptSaveProjectStatus();
                                uptCloseProjectStatus();                                
                            }
                        }
                        else{
                            JOptionPane.showMessageDialog(this, "ProteoSuite was unable to unmarshall this file."+
                                    "Please make sure you have selected a valid file.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        }
    }    
    /*----------------------------------------------
     * Load project from mzq 
     * @param iIndex - Index to the aMzQUnmarshaller arraylist 
     * @param sFile - File name 
     * @boolean Success
     ----------------------------------------------*/
    private boolean loadProjectFromMZQ(int iIndex, File sFile){
        
        final DefaultTableModel model = new DefaultTableModel();
        final DefaultTableModel model2 = new DefaultTableModel();
        jtRawFiles.setModel(model);
        jtIdentFiles.setModel(model2);
        model.addColumn("Name");
        model.addColumn("Path");
        model.addColumn("Type");
        model.addColumn("Version");
        
        model2.addColumn("Name");
        model2.addColumn("Path");
        model2.addColumn("Type");
        model2.addColumn("Version");
        model2.addColumn("Raw File");
                
        final File sFileRef = sFile;
        final int iIndexRef = iIndex;
        final ProgressBarDialog progressBarDialog = new ProgressBarDialog(this, true, "LoadProjectFromMZQ");
        final Thread thread = new Thread(new Runnable(){
            @Override
            public void run(){
                //... Progress Bar ...//
                progressBarDialog.setTitle("Loading project");
                progressBarDialog.setVisible(true);
            }
        }, "ProgressBarDialog");
        thread.start();
        new Thread("LoadProjectFromMZQ"){
            @Override
            public void run(){
                System.out.println(sysutils.getTime()+" - (From Project) Loading settings from the mzq file ...");                                                   
                            
                String sGroup = "";

                MzQuantMLUnmarshaller unmarshaller = new MzQuantMLUnmarshaller(jtQuantFiles.getValueAt(0, 1).toString());
                MzQuantML mzq = unmarshaller.unmarshall();
                System.out.println(sysutils.getTime()+" - MzQuantML version="+mzq.getVersion());
                System.out.println(sysutils.getTime()+" - ID="+mzq.getId());
                System.out.println(sysutils.getTime()+" - Name="+mzq.getName());
                
                InputFiles inputFiles = mzq.getInputFiles();
                
                //... Load raw files ...//
                List<RawFilesGroup> rawFilesGroup = inputFiles.getRawFilesGroup();
                System.out.println(sysutils.getTime()+" - RawFilesGroup="+rawFilesGroup.size());
                int iMGFRaw=0, iMzMLRaw=0;
                for(RawFilesGroup group:rawFilesGroup){                    
                    sGroup = group.getId();
                    List<RawFile> rawFileList = group.getRawFile();
                    for (RawFile rawFile:rawFileList){
                        //... Validate type of file ...//
                        File file = new File(rawFile.getLocation());
                        if (!file.exists()){
                            JOptionPane.showMessageDialog(ProteoSuiteView.this, "The file "+rawFile.getName()+" was not found at "+rawFile.getLocation()+".\n"+
                                    "Please make sure that the file exist, or reupload the file again.", "Warning", JOptionPane.WARNING_MESSAGE);
                        }
                        else{
                            progressBarDialog.setTitle("Reading " + rawFile.getName());
                            progressBarDialog.setVisible(true);
                            
                            int mid= rawFile.getName().lastIndexOf(".");
                            String ext="";
                            ext=rawFile.getName().substring(mid+1,rawFile.getName().length());
                            if(ext.toLowerCase().equals("mgf")){
                                model.insertRow(model.getRowCount(), new Object[]{
                                    rawFile.getName(),
                                    rawFile.getLocation(),
                                    "MGF",
                                    mzMLVersion});
                                iMGFRaw++;
                            }
                            if(ext.toLowerCase().equals("mzml")){
                                unmarshalMzMLFile(model, file, "");
                                iMzMLRaw++;
                            }
                        }
                    }
                }
                if (iMGFRaw>0){
                    System.out.println(sysutils.getTime()+" - Loading MGF view ...");                    
                    if(jtRawFiles.getRowCount()>0){
                        loadMGFView(0);
                    }
                }
                if (iMzMLRaw>0){
                    System.out.println(sysutils.getTime()+" - Loading mzML view ...");
                    loadMzMLView(0);
                    if(jtRawFiles.getRowCount()>0){
                        System.out.println(sysutils.getTime()+" - Showing chromatogram ...");
                        showChromatogram(0, jtRawFiles.getValueAt(0, 1).toString());
                        System.out.println(sysutils.getTime()+" - Showing 2D plot ...");
                        show2DPlot(0, jtRawFiles.getValueAt(0, 1).toString());
                    }
                }
                //... Load ident files ...//
                List<IdentificationFile> idFilesList = inputFiles.getIdentificationFiles().getIdentificationFile();
                System.out.println(sysutils.getTime()+" - Loading identification files");
                int iXMLIdent=0, iMzID=0;
                for(IdentificationFile idFile:idFilesList){
                        //... Validate type of file ...//
                        File file = new File(idFile.getLocation());
                        if (!file.exists()){
                            JOptionPane.showMessageDialog(ProteoSuiteView.this, "The file "+idFile.getName()+" was not found at "+idFile.getLocation()+".\n"+
                                    "Please make sure that the file exist, or reupload the file again.", "Warning", JOptionPane.WARNING_MESSAGE);
                        }
                        else{
                            progressBarDialog.setTitle("Reading " + idFile.getName());
                            progressBarDialog.setVisible(true);
                            System.out.println(sysutils.getTime()+" - Reading " + idFile.getName());

                            int mid= idFile.getName().lastIndexOf(".");
                            String ext="";
                            ext=idFile.getName().substring(mid+1,idFile.getName().length());
                            if(ext.toLowerCase().equals("xml")){
                                model2.insertRow(model2.getRowCount(), new Object[]{
                                    idFile.getName(),
                                    idFile.getLocation(),
                                    "xml",
                                    mzIDVersion,
                                    sGroup});   
                               iXMLIdent++;
                            }                    
                            if(ext.toLowerCase().equals("mzid")){
                               unmarshalMzIDFile(model2, file, "");
                               iMzID++;
                            }
                        }
                }
                if (iXMLIdent>0){
                    if(jtIdentFiles.getRowCount()>0){
                        loadMascotView(jtIdentFiles.getValueAt(0, 0).toString(), jtIdentFiles.getValueAt(0, 1).toString());
                    }
                }                
                if (iMzID>0){
                    if(jtIdentFiles.getRowCount()>0){
                        loadMzIdentMLView(0, jtIdentFiles.getValueAt(0, 0).toString());
                    }                                        
                }                
                sProjectName = sFileRef.getName();
                sWorkspace = sFileRef.getParent();
                sWorkspace = sWorkspace.replace("\\", "/");
                System.out.println(sysutils.getTime()+" - Project has been set up to: " + sWorkspace + ", " + sProjectName);
                uptTitle();
                renderIdentFiles();
                uptStatusPipeline();
                progressBarDialog.setVisible(false);
                progressBarDialog.dispose();    
                
                loadMzQuantMLView(0, sFileRef.getName());
            }            
        }.start();
        return true;
    }     
    /* ------------------------------------------------
     * Unmarshall mzML file 
     * @param model - table model 
     * @param xmlFile - XML file to unmarshall 
     * @param sGroup - Raw file group
     * @return void
     --------------------------------------------------*/
    private void unmarshalMzMLFile(DefaultTableModel model, File xmlFile, String sGroup)
    {
        //... Unmarshall mzML file and add on the aMzMLUnmarshaller arraylist ...//
        MzMLUnmarshaller unmarshaller = new MzMLUnmarshaller(xmlFile);
        aMzMLUnmarshaller.add(unmarshaller);

        //... split name and file extension ...//
        int mid= xmlFile.getName().lastIndexOf(".");
        String ext="";
        ext=xmlFile.getName().substring(mid+1,xmlFile.getName().length());             
        model.insertRow(model.getRowCount(), new Object[]{xmlFile.getName(),
                                                                  xmlFile.getPath().toString().replace("\\", "/"),
                                                                  ext,
                                                                  unmarshaller.getMzMLVersion()});
        System.out.println(sysutils.getTime()+" - "+xmlFile.getName() + " was unmarshalled successfully!");
    }    
    /*----------------------------------------------------------
     * Unmarshall mzIdentML files
     * @param model - Table model
     * @param iIndex - Index to the aMzIDUnmarshaller arraylist 
     * @param xmlFile - File to unmarshall
     * @param sGroup - Raw data group
     * @return void
     -----------------------------------------------------------*/
    private void unmarshalMzIDFile(DefaultTableModel model, File xmlFile, String sGroup)
    {        
        //... For each mzid file we must specify the corresponding mzML file ...//        
        MzIdentMLUnmarshaller unmarshaller = new MzIdentMLUnmarshaller(xmlFile);
        aMzIDUnmarshaller.add(unmarshaller);        
        model.insertRow(model.getRowCount(), new Object[]{xmlFile.getName(), 
               xmlFile.getPath().replace("\\", "/"),
               "mzid", 
               unmarshaller.getMzIdentMLVersion().toString(), 
               sGroup});
        System.out.println(sysutils.getTime()+" - "+xmlFile.getName() + " was unmarshalled successfully!");
    }
    /*-------------------------------------
     * Unmarshall mzQML files
     * @param model - Table model
     * @param xmlFile - File to unmarshall
     * @return boolean - Flag
     --------------------------------------*/
    private boolean unmarshalMzQMLFile(DefaultTableModel model, File xmlFile)
    {
        //... Unmarshall mzquantml file ...//
        MzQuantMLUnmarshaller unmarshaller = new MzQuantMLUnmarshaller(xmlFile);                        
        aMzQUnmarshaller.add(unmarshaller);
            
        model.insertRow(model.getRowCount(), new Object[]{xmlFile.getName(), xmlFile.getPath().replace("\\", "/"), "mzq", mzQVersion});            
        System.out.println(sysutils.getTime()+" - (Unmarshalling) "+xmlFile.getName() + " was unmarshalled successfully!");
        return true;
    }    
    /*------------------------------------------------
     * Generate files (Quant file and xtracker files)    
     * @param void
     * @return true/false
     -------------------------------------------------*/
    private boolean generateFiles() 
    {        
        boolean isOK;
        
        //... Check project name ...//  
        String sFile = sProjectName;
        System.out.println(sysutils.getTime()+" - Generating files for the pipeline ...");
        jtaLog.append("\n"+sysutils.getTime()+" - Generating files for the pipeline ...");
        if (sFile.equals("New")){
            sFile = "test.mzq";
            String sMessage = "This project has not been saved. Proteosuite will create a test.mzq file \n";
            sMessage = sMessage + " under " + sWorkspace + " to run the pipeline. \n";
            JOptionPane.showMessageDialog(this, sMessage, "Information", JOptionPane.INFORMATION_MESSAGE);
            sProjectName = sFile;
        }
        
        //... Generate mzq file ...//
        System.out.println(sysutils.getTime()+" - Generating mzq file ...");
        jtaLog.append("\n"+sysutils.getTime()+" - Generating mzq file ...");
        isOK = writeMzQuantML(jcbTechnique.getSelectedItem().toString(), sFile);
        if(isOK){
            //... Unmarshall mzquantml file ...//
            Validator validator = XMLparser.getValidator(MZQ_XSD);
            boolean validFlag = XMLparser.validate(validator, sWorkspace.replace("\\", "/") + "/" +  sProjectName);
            System.out.println(sysutils.getTime()+" - Validating mzQuantML ...");
            jtaLog.append("\n"+sysutils.getTime()+" - Validating mzQuantML ...");
            if(!validFlag){
                JOptionPane.showMessageDialog(this, "Invalid mzQuantML file", "Error", JOptionPane.INFORMATION_MESSAGE);                
            }
            else{
                //... Modify the mzQuantML structure according to the experiment ...//
                writeXTrackerFiles(jcbTechnique.getSelectedItem().toString());
            }
        }
        else{
            return false;
        }            
        return true;
    }  
    /*-----------------------------------------------
     * Write mzQuantML file 
     * @param sExperiment - Type of experiment 
     * @param sFile - File name
     * @return true/false
     ------------------------------------------------*/
    private boolean writeMzQuantML(String sExperiment, String sFile) 
    {   
        boolean ret = true;
        
        //... Create object ...//
        MzQuantML qml = new MzQuantML();
        
        //... Set version ...//        
        String Version = "1.0.0";
        qml.setVersion(Version);
        Calendar rightNow = Calendar.getInstance();
        qml.setCreationDate(rightNow);
        qml.setId(sFile);
        
        //----------------------//
        //... CREATE CV LIST ...//
        //----------------------//
        CvList cvs = new CvList();
        List<Cv> cvList = cvs.getCv();
        Cv cvPSI_MS = new Cv();
        cvPSI_MS.setId("PSI-MS");
        cvPSI_MS.setUri("http://psidev.cvs.sourceforge.net/viewvc/*checkout*/psidev/psi/psi-ms/mzML/controlledVocabulary/psi-ms.obo");
        cvPSI_MS.setFullName("Proteomics Standards Initiative Mass Spectrometry Vocabulary");
        cvPSI_MS.setVersion(PSI_MS_VERSION);

        Cv cvUO = new Cv();
        cvUO.setId("UO");
        cvUO.setUri("http://obo.cvs.sourceforge.net/*checkout*/obo/obo/ontology/phenotype/unit.obo");
        cvUO.setFullName("Unit Ontology");
        
        Cv cvPSI_MOD = new Cv();
        cvPSI_MOD.setId("PSI-MOD");
        cvPSI_MOD.setUri("http://psidev.cvs.sourceforge.net/psidev/psi/mod/data/PSI-MOD.obo");
        cvPSI_MOD.setFullName("Proteomics Standards Initiative Protein Modifications");        
        cvPSI_MOD.setVersion(PSI_MOD_VERSION);

        Cv cvUNI_MOD = new Cv();
        cvUNI_MOD.setId("UNIMOD");
        cvUNI_MOD.setUri("http://www.unimod.org/obo/unimod.obo");
        cvUNI_MOD.setFullName("UNIMOD");
        
        cvList.add(cvPSI_MS);
        cvList.add(cvUO);
        cvList.add(cvPSI_MOD);
        cvList.add(cvUNI_MOD);
        qml.setCvList(cvs);
        
        //-------------------------------//
        //... CREATE ANALYSIS SUMMARY ...//
        //-------------------------------//
        
        if (sExperiment.contains("iTRAQ")){                    
            ParamList pl = new ParamList();
            List<AbstractParam> cvParamList = pl.getParamGroup();
            CvParam cvp = new CvParam();
            cvp.setAccession("MS:1002010");
            cvp.setCvRef(cvPSI_MS);
            cvp.setName("TMT quantitation analysis");

            CvParam cvp6 = new CvParam();
            cvp6.setAccession("MS:1002023");
            cvp6.setCvRef(cvPSI_MS);
            cvp6.setName("MS2 tag-based analysis");        
            
            CvParam cvp2 = new CvParam();
            cvp2.setAccession("MS:1002024");
            cvp2.setCvRef(cvPSI_MS);
            cvp2.setValue("true");
            cvp2.setName("MS2 tag-based analysis feature level quantitation");                   

            CvParam cvp3 = new CvParam();
            cvp3.setAccession("MS:1002025");
            cvp3.setCvRef(cvPSI_MS);
            cvp3.setValue("true");
            cvp3.setName("MS2 tag-based analysis group features by peptide quantitation");

            CvParam cvp4 = new CvParam();
            cvp4.setAccession("MS:1002026");
            cvp4.setCvRef(cvPSI_MS);
            cvp4.setValue("true");
            cvp4.setName("MS2 tag-based analysis protein level quantitation");

            CvParam cvp5 = new CvParam();
            cvp5.setAccession("MS:1002027");
            cvp5.setCvRef(cvPSI_MS);
            cvp5.setValue("false");
            cvp5.setName("MS2 tag-based analysis protein group level quantitation");        

            cvParamList.add(cvp6);            
            cvParamList.add(cvp2);
            cvParamList.add(cvp3);
            cvParamList.add(cvp4);
            cvParamList.add(cvp5);

            qml.setAnalysisSummary(pl);
        }
        if (sExperiment.contains("TMT")){                    
            ParamList pl = new ParamList();
            List<AbstractParam> cvParamList = pl.getParamGroup();
            CvParam cvp = new CvParam();
            cvp.setAccession("MS:1001837");
            cvp.setCvRef(cvPSI_MS);
            cvp.setName("TMT quantitation analysis");

            CvParam cvp6 = new CvParam();
            cvp6.setAccession("MS:1002023");
            cvp6.setCvRef(cvPSI_MS);
            cvp6.setName("MS2 tag-based analysis");        
            
            CvParam cvp2 = new CvParam();
            cvp2.setAccession("MS:1002024");
            cvp2.setCvRef(cvPSI_MS);
            cvp2.setValue("true");
            cvp2.setName("MS2 tag-based analysis feature level quantitation");                   

            CvParam cvp3 = new CvParam();
            cvp3.setAccession("MS:1002025");
            cvp3.setCvRef(cvPSI_MS);
            cvp3.setValue("true");
            cvp3.setName("MS2 tag-based analysis group features by peptide quantitation");

            CvParam cvp4 = new CvParam();
            cvp4.setAccession("MS:1002026");
            cvp4.setCvRef(cvPSI_MS);
            cvp4.setValue("true");
            cvp4.setName("MS2 tag-based analysis protein level quantitation");

            CvParam cvp5 = new CvParam();
            cvp5.setAccession("MS:1002027");
            cvp5.setCvRef(cvPSI_MS);
            cvp5.setValue("false");
            cvp5.setName("MS2 tag-based analysis protein group level quantitation");        

            cvParamList.add(cvp6);            
            cvParamList.add(cvp2);
            cvParamList.add(cvp3);
            cvParamList.add(cvp4);
            cvParamList.add(cvp5);

            qml.setAnalysisSummary(pl);
        }
        if (sExperiment.contains("emPAI")){                    
            ParamList pl = new ParamList();
            List<AbstractParam> cvParamList = pl.getParamGroup();
            
            CvParam cvp = new CvParam();
            cvp.setAccession("MS:1001836");
            cvp.setCvRef(cvPSI_MS);
            cvp.setName("spectral counting quantitation analysis");

            CvParam cvp1 = new CvParam();
            cvp1.setAccession("MS:1002015");
            cvp1.setCvRef(cvPSI_MS);
            cvp1.setValue("false");
            cvp1.setName("spectral count peptide level quantitation");
            
            CvParam cvp2 = new CvParam();
            cvp2.setAccession("MS:1002016");
            cvp2.setCvRef(cvPSI_MS);
            cvp2.setValue("true");
            cvp2.setName("spectral count protein level quantitation");

            CvParam cvp3 = new CvParam();
            cvp3.setAccession("MS:1002017");
            cvp3.setCvRef(cvPSI_MS);
            cvp3.setValue("false");
            cvp3.setName("spectral count proteingroup level quantitation");      

            cvParamList.add(cvp);
            cvParamList.add(cvp1);
            cvParamList.add(cvp2);
            cvParamList.add(cvp3);

            qml.setAnalysisSummary(pl);
        }
        
        //--------------------------//
        //... CREATE INPUT FILES ...//
        //--------------------------//
        InputFiles inputFiles = new InputFiles();
        
        //--------------------------//
        //... RAW FILES          ...//
        //--------------------------//        
        
        //... Get raw files ...//
        List<RawFilesGroup> rawFilesGroupList = inputFiles.getRawFilesGroup();
        
        Map<String, ArrayList<String[]>> hmRawFiles = new HashMap<String, ArrayList<String[]>>();
        
        boolean bExists = false;                    
        //... Select all raw files from grid ...//
        for (int iI=0; iI<jtRawFiles.getRowCount(); iI++){            
            //... Check if group has been added previously ...//
            String sKey = jtRawFiles.getValueAt(iI, 0).toString();
            bExists = hmRawFiles.containsKey(sKey);
            if (bExists == false){
                ArrayList al = new ArrayList();
                String[] sTemp = new String[2];
                sTemp[0] = jtRawFiles.getValueAt(iI, 0).toString();
                sTemp[1] = jtRawFiles.getValueAt(iI, 1).toString();
                al.add(sTemp);
                hmRawFiles.put(sKey, al); 
            }else{
                ArrayList al2 = hmRawFiles.get(jtRawFiles.getValueAt(iI, 4).toString());                   
                String[] sTemp = new String[2];
                sTemp[0] = jtRawFiles.getValueAt(iI, 0).toString();
                sTemp[1] = jtRawFiles.getValueAt(iI, 1).toString();
                al2.add(sTemp);
                hmRawFiles.put(sKey, al2);
            }
        }
        //... Set raw files groups ...//
        int iCounter=0;
        for (Map.Entry<String, ArrayList<String[]>> entry : hmRawFiles.entrySet()){
            
            RawFilesGroup rawFilesGroup = new RawFilesGroup();
            List<RawFile> rawFilesList = rawFilesGroup.getRawFile();
        
            //String rgId = "raw_"+Integer.toString(iCounter+1);            
            String rgId = jtRawFiles.getValueAt(iCounter, 0).toString();
            ArrayList<String[]> saGroups = entry.getValue();
            Iterator<String[]> itr = saGroups.iterator();
            while (itr.hasNext()) { //... Identify the corresponding raw files ...//                
                String[] slFiles = new String[2];
                slFiles = itr.next();
                String rawFname = slFiles[0];
                String rawId = "r" + Integer.toString(iCounter+1);
                //... Raw files ...//
                RawFile rawFile = new RawFile();
                rawFile.setName(rawFname);
                rawFile.setId(rawId);
                rawFile.setLocation(slFiles[1]);            
                rawFilesList.add(rawFile); 
                iCounter++;
            }
            rawFilesGroup.setId(rgId);
            rawFilesGroupList.add(rawFilesGroup);            
        }
        
        //-----------------------------//
        //... IDENTIFICATION FILES  ...//
        //-----------------------------//                

        //... Define those structures that will be further used  (e.g. in AssayList) ...//
        HashMap<String, String> idFileNameIdMap = new HashMap<String, String>();         
        
        //... Identification file containers ...//
        IdentificationFiles idFiles = inputFiles.getIdentificationFiles();
        if(idFiles==null){
            idFiles = new IdentificationFiles();
        }
        List<IdentificationFile> idFilesList = idFiles.getIdentificationFile();
            
        //... Select all raw files from grid ...//
        for (int iI=0; iI<jtIdentFiles.getRowCount(); iI++){
            //... Identification files ...//
            String idFname = jtIdentFiles.getValueAt(iI, 0).toString();
            String idId = "id_file" + Integer.toString(iI+1);
            
            IdentificationFile idFile = new IdentificationFile();
            idFile.setName(idFname);
            idFile.setId(idId);
            idFile.setLocation(jtIdentFiles.getValueAt(iI, 1).toString().replace("\\", "/"));            
            idFilesList.add(idFile);
            idFileNameIdMap.put(idFname, idId); //... Saving hashmap for AssayList ...//                        
        }
        inputFiles.setIdentificationFiles(idFiles);
        qml.setInputFiles(inputFiles);
        
        //---------------------------//
        //... Create SoftwareList ...//
        //---------------------------//
        SoftwareList softwareList = new SoftwareList();
        Software software = new Software();
        softwareList.getSoftware().add(software);
        software.setId("x-Tracker");
        software.setVersion("2.0-ALPHA");
        CvParam cvpSW = new CvParam();
        cvpSW.setAccession("MS:1002123");
        cvpSW.setCvRef(cvPSI_MS);
        cvpSW.setName("x-Tracker");
        software.getCvParam().add(cvpSW);
        
        Software software2 = new Software();
        softwareList.getSoftware().add(software2);
        software2.setId("ProteoSuite");
        software2.setVersion(sPS_Version);        
        CvParam cvpSW2 = new CvParam();
        cvpSW2.setAccession("MS:1002124");
        cvpSW2.setCvRef(cvPSI_MS);
        cvpSW2.setName("ProteoSuite");    
        software2.getCvParam().add(cvpSW2);
        qml.setSoftwareList(softwareList);
        
        //---------------------------------//
        //... Create DataProcessingList ...//
        //---------------------------------//
        DataProcessingList dataProcessingList = new DataProcessingList();
        DataProcessing dataProcessing = new DataProcessing();
        dataProcessing.setId("DP1");
        dataProcessing.setSoftwareRef(software);
        dataProcessing.setOrder(BigInteger.ONE);
        
        //... Based on the technique, select the plugins that are available to perform the quantitation ...//
        if (sExperiment.contains("iTRAQ")){
            String[] sPipeline;
            sPipeline = new String[4];
            sPipeline = getPlugins(sExperiment); 

            //... Processing methods ...//
            ProcessingMethod processingMethod = new ProcessingMethod();        
            processingMethod.setOrder(BigInteger.ONE);
            List<AbstractParam> pmList = processingMethod.getParamGroup();        
            UserParam up1 = new UserParam();
            up1.setName("Plugin type");
            up1.setValue("load identification");
            pmList.add(up1);
            UserParam up2 = new UserParam();
            up2.setName("Plugin name");
            up2.setValue(sPipeline[0]);
            pmList.add(up2);
            UserParam up3 = new UserParam();
            up3.setName("Plugin configuration file");
            up3.setValue(sWorkspace.replace("\\", "/") + "/xTracker_" + sPipeline[0] + ".xtp");
            pmList.add(up3);
            dataProcessing.getProcessingMethod().add(processingMethod);

            ProcessingMethod processingMethod2 = new ProcessingMethod();        
            processingMethod2.setOrder(BigInteger.valueOf(2));
            List<AbstractParam> pmList2 = processingMethod2.getParamGroup();        
            UserParam up2_1 = new UserParam();
            up2_1.setName("Plugin type");
            up2_1.setValue("load raw spectra");
            pmList2.add(up2_1);
            UserParam up2_2 = new UserParam();
            up2_2.setName("Plugin name");
            up2_2.setValue(sPipeline[1]);
            pmList2.add(up2_2);
            UserParam up2_3 = new UserParam();
            up2_3.setName("Plugin configuration file");
            up2_3.setValue(sWorkspace.replace("\\", "/") + "/xTracker_" + sPipeline[1] + ".xtp");
            pmList2.add(up2_3);
            dataProcessing.getProcessingMethod().add(processingMethod2);

            ProcessingMethod processingMethod3 = new ProcessingMethod();        
            processingMethod3.setOrder(BigInteger.valueOf(3));
            List<AbstractParam> pmList3 = processingMethod3.getParamGroup();        
            UserParam up3_1 = new UserParam();
            up3_1.setName("Plugin type");
            up3_1.setValue("feature detection and quantitation");
            pmList3.add(up3_1);
            UserParam up3_2 = new UserParam();
            up3_2.setName("Plugin name");
            up3_2.setValue(sPipeline[2]);
            pmList3.add(up3_2);
            UserParam up3_3 = new UserParam();
            up3_3.setName("Plugin configuration file");
            up3_3.setValue(sWorkspace.replace("\\", "/") + "/xTracker_" + sPipeline[2] + ".xtp");
            pmList3.add(up3_3);
            UserParam up3_4 = new UserParam();
            up3_4.setName("Feature to peptide inference method");
            up3_4.setValue("mean");
            pmList3.add(up3_4);
            UserParam up3_5 = new UserParam();
            up3_5.setName("Peptide to protein inference method");
            up3_5.setValue("weightedAverage");
            pmList3.add(up3_5);
            UserParam up3_6 = new UserParam();
            up3_6.setName("Assay to Study Variables inference method");
            up3_6.setValue("sum");
            pmList3.add(up3_6);
            UserParam up3_7 = new UserParam();
            up3_7.setName("Protein ratio calculation infer from peptide ratio");
            up3_7.setValue("true");
            pmList3.add(up3_7);
            dataProcessing.getProcessingMethod().add(processingMethod3);        

            ProcessingMethod processingMethod4 = new ProcessingMethod();        
            processingMethod4.setOrder(BigInteger.valueOf(4));
            List<AbstractParam> pmList4 = processingMethod4.getParamGroup();        
            UserParam up4_1 = new UserParam();
            up4_1.setName("Plugin type");
            up4_1.setValue("Output");
            pmList4.add(up4_1);
            UserParam up4_2 = new UserParam();
            up4_2.setName("Plugin name");
            up4_2.setValue(sPipeline[3]);
            pmList4.add(up4_2);
            UserParam up4_3 = new UserParam();
            up4_3.setName("Plugin configuration file");
            up4_3.setValue(sWorkspace.replace("\\", "/") + "/xTracker_" + sPipeline[3] + ".xtp");
            pmList4.add(up4_3);
            dataProcessing.getProcessingMethod().add(processingMethod4);
        }
        if (sExperiment.contains("emPAI")){
            String[] sPipeline;
            sPipeline = new String[4];
            sPipeline = getPlugins(sExperiment); 

            //... Processing methods ...//
            ProcessingMethod processingMethod = new ProcessingMethod();        
            processingMethod.setOrder(BigInteger.ONE);
            List<AbstractParam> pmList = processingMethod.getParamGroup();        
            UserParam up1 = new UserParam();
            up1.setName("Plugin type");
            up1.setValue("load identification");
            pmList.add(up1);
            UserParam up2 = new UserParam();
            up2.setName("Plugin name");
            up2.setValue(sPipeline[0]);
            pmList.add(up2);
            UserParam up3 = new UserParam();
            up3.setName("Plugin configuration file");
            up3.setValue(sWorkspace.replace("\\", "/") + "/xTracker_" + sPipeline[0] + ".xtp");
            pmList.add(up3);
            dataProcessing.getProcessingMethod().add(processingMethod);

            ProcessingMethod processingMethod3 = new ProcessingMethod();        
            processingMethod3.setOrder(BigInteger.valueOf(3));
            List<AbstractParam> pmList3 = processingMethod3.getParamGroup();        
            UserParam up3_1 = new UserParam();
            up3_1.setName("Plugin type");
            up3_1.setValue("feature detection and quantitation");
            pmList3.add(up3_1);
            UserParam up3_2 = new UserParam();
            up3_2.setName("Plugin name");
            up3_2.setValue(sPipeline[2]);
            pmList3.add(up3_2);
            UserParam up3_3 = new UserParam();
            up3_3.setName("Plugin configuration file");
            up3_3.setValue(sWorkspace.replace("\\", "/") + "/xTracker_" + sPipeline[2] + ".xtp");
            pmList3.add(up3_3);
            dataProcessing.getProcessingMethod().add(processingMethod3);        

            ProcessingMethod processingMethod4 = new ProcessingMethod();        
            processingMethod4.setOrder(BigInteger.valueOf(4));
            List<AbstractParam> pmList4 = processingMethod4.getParamGroup();        
            UserParam up4_1 = new UserParam();
            up4_1.setName("Plugin type");
            up4_1.setValue("Output");
            pmList4.add(up4_1);
            UserParam up4_2 = new UserParam();
            up4_2.setName("Plugin name");
            up4_2.setValue(sPipeline[3]);
            pmList4.add(up4_2);
            UserParam up4_3 = new UserParam();
            up4_3.setName("Plugin configuration file");
            up4_3.setValue(sWorkspace.replace("\\", "/") + "/xTracker_" + sPipeline[3] + ".xtp");
            pmList4.add(up4_3);
            dataProcessing.getProcessingMethod().add(processingMethod4);
        }
        dataProcessingList.getDataProcessing().add(dataProcessing);
        qml.setDataProcessingList(dataProcessingList);
        
        //------------------------//
        //... Create AssayList ...//        
        //------------------------//
        AssayList assays = new AssayList();
        assays.setId("AssayList1");
        List<Assay> assayList = assays.getAssay();
        HashMap<String, ArrayList<Assay>> studyVarAssayID = new HashMap<String, ArrayList<Assay>>(); //... This will be used in StudyVariableList ...//
        
        if (sExperiment.contains("iTRAQ")){
            //... Assay list will be retrieved from the parameters set up in the configQuantML file ...//        
            try{
                DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
                domFactory.setNamespaceAware(true); 
                DocumentBuilder builder = domFactory.newDocumentBuilder();
                Document doc = builder.parse("configQuant.xml");
                XPath xpath = XPathFactory.newInstance().newXPath();

                //... Check raw files ...//
                for(int iJ=0; iJ<jtRawFiles.getRowCount();iJ++){
                    XPathExpression expr = xpath.compile("/ProteoSuiteApplication/configSettings/quantParamSettings/techniques/technique[@id='iTRAQ']/AssayParamList/RawFile[@id='" + jtRawFiles.getValueAt(iJ, 0)  + "']");
                    NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
                    if (nodes.getLength()<=0){  
                        JOptionPane.showMessageDialog(this, "Please specify the parameter settings for each raw file in Project->Set Quantitation Parameters->iTRAQ.", "Error", JOptionPane.INFORMATION_MESSAGE);
                        return false;
                    }
                    //... Validating files ...//
                    expr = xpath.compile("/ProteoSuiteApplication/configSettings/quantParamSettings/techniques/technique[@id='iTRAQ']/AssayParamList/RawFile[@id='" + jtRawFiles.getValueAt(iJ, 0)  + "']/AssayParam");
                    nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
                    boolean blnExists = false;
                    for (int iI = 0; iI < nodes.getLength(); iI++) {
                        Node node = nodes.item(iI);
                        if (node.getNodeType() == Node.ELEMENT_NODE){
                            Element element = (Element) node;
                            NodeList nodelist = element.getElementsByTagName("AssayID");
                            Element element2 = (Element) nodelist.item(0);
                            NodeList fstNm1 = element2.getChildNodes();
                            String sAssayID = fstNm1.item(0).getNodeValue().toString();

                            Element element3 = (Element) node;
                            NodeList nodelist2 = element3.getElementsByTagName("AssayName");
                            Element element4 = (Element) nodelist2.item(0);
                            NodeList fstNm2 = element4.getChildNodes();                     
                            String sAssayName = fstNm2.item(0).getNodeValue().toString();

                            Element element5 = (Element) node;
                            NodeList nodelist3 = element5.getElementsByTagName("mzValue");
                            Element element6 = (Element) nodelist3.item(0);
                            NodeList fstNm3 = element6.getChildNodes();
                            String sMzValue = fstNm3.item(0).getNodeValue().toString();

                            Element element7 = (Element) node;
                            NodeList nodelist4 = element7.getElementsByTagName("StudyVariable");
                            Element element8 = (Element) nodelist4.item(0);
                            NodeList fstNm4 = element8.getChildNodes();
                            String sStudyVariable = fstNm4.item(0).getNodeValue().toString();

                            Assay assay = new Assay();
                            String sRawFile = jtRawFiles.getValueAt(iJ, 0).toString();
                            String assName = sAssayID;                //... e.g. 114 ...//
                            String assId = "i"+(iJ+1)+"_"+sAssayID;
                            assay.setId(assId);
                            assay.setName(assName);

                            //... Create the study variable tree ...//
                            blnExists = studyVarAssayID.containsKey(sStudyVariable);
                            if (blnExists == false){                        
                                ArrayList al = new ArrayList();
                                al.add(assay);
                                studyVarAssayID.put(sStudyVariable, al);
                            }
                            else{
                                ArrayList al2 = studyVarAssayID.get(sStudyVariable);                   
                                al2.add(assay);
                                studyVarAssayID.put(sStudyVariable, al2);
                            }

                            //... Check the rawFileGroup associated to that assay ...//
                            int iK = 0;
                            for (RawFilesGroup rfg:rawFilesGroupList){
                                String sKey = sRawFile;
                                RawFilesGroup rfGroup = rawFilesGroupList.get(iK);
                                if(rfg.getId().equals(sKey)){
                                    assay.setRawFilesGroupRef(rfGroup);                            
                                    break;
                                }
                                iK++;
                            }
                            Label label = new Label();
                            CvParam labelCvParam = new CvParam();
                            labelCvParam.setAccession("");
                            labelCvParam.setName(sAssayName);
                            labelCvParam.setValue(sMzValue);
                            labelCvParam.setCvRef(cvPSI_MOD);
                            List<ModParam> modParams = label.getModification();
                            ModParam modParam = new ModParam();
                            modParam.setCvParam(labelCvParam);
                            modParam.setMassDelta(145.0f);
                            modParams.add(modParam);
                            assay.setLabel(label);
                            assayList.add(assay);
                        }
                    }                                
                }            
            }
            catch ( ParserConfigurationException e) {
              e.printStackTrace();
            } catch ( SAXException e) {
              e.printStackTrace();
            } catch ( IOException e) {
              e.printStackTrace();
            } catch ( XPathExpressionException  e) {
              e.printStackTrace();
            }
        }
        if (sExperiment.contains("emPAI")){
            //... Check the rawFileGroup associated to that assay ...//
            int iK = 0;
            for (RawFilesGroup rfg:rawFilesGroupList){
                Assay assay = new Assay();
                assay.setId("assay_"+Integer.toString(iK+1));
                assay.setName("assay_"+Integer.toString(iK+1));
                assay.setRawFilesGroupRef(rfg);
                Label label = new Label();
                CvParam labelCvParam = new CvParam();
                labelCvParam.setAccession("MS:1002038");
                labelCvParam.setName("unlabeled sample");
                labelCvParam.setCvRef(cvPSI_MS);
                List<ModParam> modParams = label.getModification();
                ModParam modParam = new ModParam();
                modParam.setCvParam(labelCvParam);
                modParam.setMassDelta(0.0f);
                modParams.add(modParam);
                assay.setLabel(label);
                assayList.add(assay);
                iK++;
            }
        }
        qml.setAssayList(assays);
        
        //--------------------------------//
        //... Create StudyVariableList ...//        
        //--------------------------------//        
        
        StudyVariableList studyVariables = new StudyVariableList();
        List<StudyVariable> studyVariableList = studyVariables.getStudyVariable();

        if (sExperiment.contains("iTRAQ")){
            Iterator it = studyVarAssayID.entrySet().iterator();
            while (it.hasNext()){
                Map.Entry pairs = (Map.Entry)it.next();
                String group = pairs.getKey().toString();
                ArrayList al = (ArrayList) pairs.getValue();
                StudyVariable studyVariable = new StudyVariable();
                studyVariable.setName(group);
                studyVariable.setId("SV_"+group);
                List<Object> assayRefList = studyVariable.getAssayRefs();        
                for(Object obj:al){
                    assayRefList.add(obj);
                }
                CvParam cvp9 = new CvParam();
                cvp9.setAccession("MS:1001807");
                cvp9.setCvRef(cvPSI_MS);
                cvp9.setValue("1");
                cvp9.setName("StudyVariable attribute");
                List<AbstractParam> paramList = studyVariable.getParamGroup();
                paramList.add(cvp9);
                studyVariableList.add(studyVariable);            
            }            
        }        
        if (sExperiment.contains("emPAI")){
            StudyVariable studyVariable = new StudyVariable();
            studyVariable.setName("Group");
            studyVariable.setId("SV_group");
            List<Object> assayRefList = studyVariable.getAssayRefs();
            assayRefList.add(assayList.get(0));
            studyVariableList.add(studyVariable);
        }
        qml.setStudyVariableList(studyVariables);
        
        //... Marshal mzQuantML object ...//
        MzQuantMLMarshaller marshaller = new MzQuantMLMarshaller(sWorkspace + "/" + sFile);
        marshaller.marshall(qml);
        return ret;
    }
    /*-------------------------------------------
     * Write xTrackerMain based on the technique 
     * @param sExperiment - Pipeline type 
     * @return void
     --------------------------------------------*/
    private void writeXTrackerFiles(String sExperiment) 
    {            
        //... Based on the technique, select the plugins that are available to perform the quantitation ...//
        String[] sPipeline;
        sPipeline = new String[4];            
        sPipeline = getPlugins(sExperiment);               

        //... xTracker consists of 4 main plugins (read more on www.x-tracker.info) ...//                
        writeXTrackerIdent(sExperiment, sPipeline[0]);         
        writeXTrackerRaw(sPipeline[1]);        
        writeXTrackerQuant(sPipeline[2]);        
        writeXTrackerOutput(sPipeline[3]);  
    }
    /*--------------------------------------------------------------
     * This method gets the plugins based on the selected pipeline
     * @param sExperiment - Pipeline type 
     * @return Returns an array with the different plugins
     ---------------------------------------------------------------*/
    private String[] getPlugins(String sExperiment)
    {
        final List<List<String>> alPlugins = new ArrayList<List<String>>();
        String[] sPipeline;
        sPipeline = new String[5];                        
        
        //... Read files using XML parser (Creates an Array of ArrayList) ...//
        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(new File("config.xml"));

            NodeList nodeList = document.getElementsByTagName("pluginLoadIdentFiles");
            for(int x=0,size= nodeList.getLength(); x<size; x++) {
                alPlugins.add(Arrays.asList(nodeList.item(x).getAttributes().getNamedItem("type").getNodeValue(), 
                                nodeList.item(x).getAttributes().getNamedItem("id").getNodeValue(), 
                                nodeList.item(x).getAttributes().getNamedItem("value").getNodeValue(), 
                                nodeList.item(x).getAttributes().getNamedItem("desc").getNodeValue()));
            }
            nodeList = document.getElementsByTagName("pluginLoadRawFiles");
            for(int x=0,size= nodeList.getLength(); x<size; x++) {
                alPlugins.add(Arrays.asList(nodeList.item(x).getAttributes().getNamedItem("type").getNodeValue(), 
                                nodeList.item(x).getAttributes().getNamedItem("id").getNodeValue(), 
                                nodeList.item(x).getAttributes().getNamedItem("value").getNodeValue(), 
                                nodeList.item(x).getAttributes().getNamedItem("desc").getNodeValue()));
            }
            nodeList = document.getElementsByTagName("pluginQuantitation");
            for(int x=0,size= nodeList.getLength(); x<size; x++) {
                alPlugins.add(Arrays.asList(nodeList.item(x).getAttributes().getNamedItem("type").getNodeValue(), 
                                nodeList.item(x).getAttributes().getNamedItem("id").getNodeValue(), 
                                nodeList.item(x).getAttributes().getNamedItem("value").getNodeValue(), 
                                nodeList.item(x).getAttributes().getNamedItem("desc").getNodeValue()));
            }
            nodeList = document.getElementsByTagName("pluginOutput");
            for(int x=0,size= nodeList.getLength(); x<size; x++) {
                alPlugins.add(Arrays.asList(nodeList.item(x).getAttributes().getNamedItem("type").getNodeValue(), 
                                nodeList.item(x).getAttributes().getNamedItem("id").getNodeValue(), 
                                nodeList.item(x).getAttributes().getNamedItem("value").getNodeValue(), 
                                nodeList.item(x).getAttributes().getNamedItem("desc").getNodeValue()));
            }
        }catch ( ParserConfigurationException e) {
          e.printStackTrace();
        } catch ( SAXException e) {
          e.printStackTrace();
        } catch ( IOException e) {
          e.printStackTrace();
        }
        
        //... Using the array list we need to find the pipeline and corresponding plugins ...//   
        
        //... Find ident file plugin ...//
        for (int iI=0; iI < alPlugins.size(); iI++){
            List<String> sublist = alPlugins.get(iI);            
            String[] arrayOfStrings = (String[]) sublist.toArray();
            if (arrayOfStrings[1].toString().toLowerCase().equals(jtIdentFiles.getValueAt(0, 2).toString().toLowerCase())){
                sPipeline[0] = arrayOfStrings[2].toString();
                break;
            }
        }
        //... Find raw file plugin ...//
        for (int iI=0; iI < alPlugins.size(); iI++){
            List<String> sublist = alPlugins.get(iI);            
            String[] arrayOfStrings = (String[]) sublist.toArray();
            if (arrayOfStrings[1].toString().toLowerCase().equals(jtRawFiles.getValueAt(0, 2).toString().toLowerCase())){
                sPipeline[1] = arrayOfStrings[2].toString();
                break;
            }
        }        
        //... Find quant file plugin ...//
        for (int iI=0; iI < alPlugins.size(); iI++){
            List<String> sublist = alPlugins.get(iI);            
            String[] arrayOfStrings = (String[]) sublist.toArray();
            if (arrayOfStrings[1].toString().toLowerCase().equals(sExperiment.toLowerCase())){
                sPipeline[2] = arrayOfStrings[2].toString();
                break;
            }
        }        
        //... Find output file plugin ...//
        for (int iI=0; iI < alPlugins.size(); iI++){
            List<String> sublist = alPlugins.get(iI);            
            String[] arrayOfStrings = (String[]) sublist.toArray();
            if (arrayOfStrings[1].toString().toLowerCase().equals(jcbOutputFormat.getSelectedItem().toString().toLowerCase())){
                sPipeline[3] = arrayOfStrings[2].toString();
                break;
            }
        }        
        return sPipeline;
    }
    /*----------------------------------------------------------
     * Write the xTracker identification plugin
     * @param sExperiment - Type of experiment (e.g. emPAI, etc.)
     * @param sPlugin - Plugin (e.g. loadRawMzML111, etc.)
     * @return void
     ----------------------------------------------------------*/
    private void writeXTrackerIdent(String sExperiment, String sPlugin) 
    {        
        String sFileName = sWorkspace + "/xTracker_" + sPlugin + ".xtp";
        try{
            FileWriter fstream = new FileWriter(sFileName);
            BufferedWriter out = new BufferedWriter(fstream);            
            out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            out.newLine();
            out.write("<!-- ");
            out.newLine();
            out.write("    *** FILE GENERATED VIA PROTEOSUITE ***");            
            out.newLine();
            out.write("-->");
            out.newLine();
            if (sPlugin.equals("loadMascotIdent")){
                out.write("<!-- ");                
                out.write("    This XML file specifies a list of raw data files with their corresponding identification files.");
                out.newLine();
                out.write("    Also, modifications and mass shifts are specified here in case mascot does not report fixed modification mass shifts.");
                out.newLine();
                out.write("    Finally, the minimum threshold use for the search engine is specified in the pop_score_threshold tag. ");
                out.newLine();
                out.write("-->");
                out.newLine();
                out.write("<param xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"Plugins/loadMascotIdent.xsd\">");
                out.newLine();
                out.write("    <inputFiles>");
                out.newLine();
                for (int iI=0; iI<jtIdentFiles.getRowCount(); iI++){
                    String sRawFile = "";
                    for (int iJ=0; iJ<jtRawFiles.getRowCount(); iJ++){
                        if(jtRawFiles.getValueAt(iJ, 0).toString().equals(jtIdentFiles.getValueAt(iI, 4).toString())){
                            sRawFile = jtRawFiles.getValueAt(iJ, 1).toString();
                            break;
                        }
                    }
                    out.write("        <datafile identification_file=\"" + jtIdentFiles.getValueAt(iI, 1) + "\">" + sRawFile + "</datafile>");
                    out.newLine();
                }           
                out.write("    </inputFiles>");
                out.newLine();
                out.write("<!-- Modifications (including amino-acid affected) and mass shifts considered in the search are reported below.");
                out.newLine();
                out.write("    NOTE THAT MONOISOTOPIC MASSES ARE USED IN THIS FILE!!!");
                out.newLine();
                out.write("-->");
                out.newLine();
                out.write("    <modificationData>");
                out.newLine();            
                out.write("        <modification delta=\"57.021469\">Carbamidomethyl (C)</modification>");
                out.newLine();                    
                out.write("        <modification delta=\"144.1020633\">iTRAQ4plex (K)</modification>");
                out.newLine();
                out.write("        <modification delta=\"144.102063\">iTRAQ4plex (N-term)</modification>");
                out.newLine();
                out.write("    </modificationData>");
                out.newLine();
                
                //... Read files using XPath xml parser ...//
                String SearchScore = "";
                try{
                    DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
                    domFactory.setNamespaceAware(true); 
                    DocumentBuilder builder = domFactory.newDocumentBuilder();
                    Document doc = builder.parse("configQuant.xml");
                    XPath xpath = XPathFactory.newInstance().newXPath();
                    XPathExpression expr = null;                    

                    if (sExperiment.equals("iTRAQ")){
                        expr = xpath.compile("/ProteoSuiteApplication/configSettings/quantParamSettings/techniques/technique[@id='iTRAQ']/SearchScore");
                    }else if (sExperiment.equals("emPAI")){
                        expr = xpath.compile("/ProteoSuiteApplication/configSettings/quantParamSettings/techniques/technique[@id='emPAI']/SearchScore");                        
                    }
                    NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
                    for (int iI = 0; iI < nodes.getLength(); iI++) {
                        SearchScore = nodes.item(iI).getTextContent();
                    }
                }
                catch ( ParserConfigurationException e) {
                  e.printStackTrace();
                } catch ( SAXException e) {
                  e.printStackTrace();
                } catch ( IOException e) {
                  e.printStackTrace();
                } catch ( XPathExpressionException  e) {
                  e.printStackTrace();
                }
                out.write("    <pep_score_threshold>" + SearchScore + "</pep_score_threshold>");
                out.newLine();   
                out.write("</param> ");                
            }
            if (sPlugin.equals("loadMzIdentML")){
                out.write("<!-- ");                
                out.newLine();
                out.write("    This XML file specifies a list of raw data files with their corresponding identification files.");
                out.newLine();
                out.write("-->");
                out.newLine();
                out.write("<SpectralIdentificationList xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"Plugins/loadMzIdentML.xsd\">");
                out.newLine();
                for (int iI=0; iI<jtIdentFiles.getRowCount(); iI++){
                    String sRawFile = "";
                    for (int iJ=0; iJ<jtRawFiles.getRowCount(); iJ++){
                        if(jtRawFiles.getValueAt(iJ, 0).toString().equals(jtIdentFiles.getValueAt(iI, 4).toString())){
                            sRawFile = jtRawFiles.getValueAt(iJ, 1).toString();
                            break;
                        }
                    }                    
                    out.write("    <SpectralIdentificationPair spectra=\"" + sRawFile + "\" identification=\"" + jtIdentFiles.getValueAt(iI, 1) + "\" />");
                    out.newLine();
                }
                out.write("</SpectralIdentificationList>");
                out.newLine();                
            }
            out.close();
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
        }             
    } 
    /*----------------------------------------------------
     * Write the xTracker load raw data plugin
     * @param sPlugin - Plugin (e.g. loadMzIdentML, etc.)
     * @return void
     -----------------------------------------------------*/
    private void writeXTrackerRaw(String sPlugin) 
    {
        String sFileName = sWorkspace + "/xTracker_" + sPlugin + ".xtp";
        try{
            FileWriter fstream = new FileWriter(sFileName);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
            out.newLine();
            out.write("<!--");
            out.newLine();
            out.write("    *** FILE GENERATED VIA PROTEOSUITE ***");            
            out.newLine();            
            out.write("-->");
            out.newLine();            
            out.write("<!-- ");                
            out.write("    This XML file specifies a list of raw data files which will be used in the analysis.");
            out.newLine();
            out.write("-->");            
            out.newLine();
            out.write("<param xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"Plugins/loadRawMGF.xsd\">");
            out.newLine();
            for (int iI=0; iI<jtRawFiles.getRowCount(); iI++){
                out.write("    <datafile>" + jtRawFiles.getValueAt(iI, 1).toString() + "</datafile>");
                out.newLine();
            }
            out.write("</param>");
            out.newLine();
            out.close();
        }
        catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }
    /*-------------------------------------------------------
     * Write the xTracker quantitation plugin
     * @param sPlugin - Plugin (e.g. iTraqQuantitation, etc.)
     * @return void
     --------------------------------------------------------*/    
    private void writeXTrackerQuant(String sPlugin) 
    {       
        System.out.println("Quant="+sPlugin);
        String sFileName = sWorkspace + "/xTracker_" + sPlugin + ".xtp";
        
        //... Read files using XPath xml parser ...//
        String mzRangeMin="", mzRangeMax="", integrationMethod="";
        String minPepRange="", maxPepRange="", searchScore="", enzyme="";
        ArrayList alFastaFiles = new ArrayList();
        List<List<String>> twoDim = new ArrayList<List<String>>();
        try{
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setNamespaceAware(true); 
            DocumentBuilder builder = domFactory.newDocumentBuilder();
            Document doc = builder.parse("configQuant.xml");
            XPath xpath = XPathFactory.newInstance().newXPath();

            //------------------//
            //... iTRAQ data ...//
            //------------------//
            
            //... Reading mzRanges (min and max) ...//
            XPathExpression expr = xpath.compile("/ProteoSuiteApplication/configSettings/quantParamSettings/techniques/technique[@id='iTRAQ']/mzRange/minus");
            NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int iI = 0; iI < nodes.getLength(); iI++) {
                mzRangeMin = nodes.item(iI).getTextContent();
            }
            expr = xpath.compile("/ProteoSuiteApplication/configSettings/quantParamSettings/techniques/technique[@id='iTRAQ']/mzRange/plus");
            nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int iI = 0; iI < nodes.getLength(); iI++) {
                mzRangeMax = nodes.item(iI).getTextContent();
            }
            expr = xpath.compile("/ProteoSuiteApplication/configSettings/quantParamSettings/techniques/technique[@id='iTRAQ']/IntegrationMethod");
            nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int iI = 0; iI < nodes.getLength(); iI++) {
                integrationMethod = nodes.item(iI).getTextContent();
            }

            for(int iJ=0; iJ<1;iJ++){ //... This has been set up to only one iteration as x-Tracker cannot cope with multiple configurations ...//
                expr = xpath.compile("/ProteoSuiteApplication/configSettings/quantParamSettings/techniques/technique[@id='iTRAQ']/AssayParamList/RawFile[@id='" + jtRawFiles.getValueAt(iJ, 0)  + "']");
                nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

                //... Assay Parameters (Labels) ...//
                expr = xpath.compile("/ProteoSuiteApplication/configSettings/quantParamSettings/techniques/technique[@id='iTRAQ']/AssayParamList/RawFile[@id='" + jtRawFiles.getValueAt(iJ, 0)  + "']/AssayParam");
                nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
                for (int iI = 0; iI < nodes.getLength(); iI++) {
                    Node node = nodes.item(iI);
                    if (node.getNodeType() == Node.ELEMENT_NODE){

                        String sAssayName = "", sMzValue="";
                        String[] sCorrFactors = new String[4];            
                        Element element = (Element) node;
                        NodeList nodelist = element.getElementsByTagName("AssayName");
                        Element element1 = (Element) nodelist.item(0);
                        NodeList fstNm = element1.getChildNodes();
                        sAssayName = fstNm.item(0).getNodeValue();

                        Element element2 = (Element) node;
                        NodeList nodelist1 = element2.getElementsByTagName("mzValue");
                        Element element3 = (Element) nodelist1.item(0);
                        NodeList fstNm1 = element3.getChildNodes();
                        sMzValue = fstNm1.item(0).getNodeValue();

                        Element element4 = (Element) node;
                        NodeList nodelist2 = element2.getElementsByTagName("factor");
                        for (int iK = 0; iK < nodelist2.getLength(); iK++){
                            Element element5 = (Element) nodelist2.item(iK);
                            NodeList fstNm2 = element5.getChildNodes();
                            sCorrFactors[iK] = fstNm2.item(0).getNodeValue();
                        }
                        twoDim.add(Arrays.asList(sAssayName, sMzValue, sCorrFactors[0], sCorrFactors[1], sCorrFactors[2], sCorrFactors[3]));
                    }
                }
            }
            //------------------//
            //... emPAI data ...//
            //------------------//
            expr = xpath.compile("/ProteoSuiteApplication/configSettings/quantParamSettings/techniques/technique[@id='emPAI']/minRange");
            nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int iI = 0; iI < nodes.getLength(); iI++) {
                minPepRange = nodes.item(iI).getTextContent();
            }
            expr = xpath.compile("/ProteoSuiteApplication/configSettings/quantParamSettings/techniques/technique[@id='emPAI']/maxRange");
            nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int iI = 0; iI < nodes.getLength(); iI++) {
                maxPepRange = nodes.item(iI).getTextContent();
            }
            expr = xpath.compile("/ProteoSuiteApplication/configSettings/quantParamSettings/techniques/technique[@id='emPAI']/SearchScore");
            nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int iI = 0; iI < nodes.getLength(); iI++) {
                searchScore = nodes.item(iI).getTextContent();
            }
            expr = xpath.compile("/ProteoSuiteApplication/configSettings/quantParamSettings/techniques/technique[@id='emPAI']/Enzyme");
            nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int iI = 0; iI < nodes.getLength(); iI++) {
                enzyme = nodes.item(iI).getTextContent();
            }
            expr = xpath.compile("/ProteoSuiteApplication/configSettings/quantParamSettings/techniques/technique[@id='emPAI']/FastaFiles/File");
            nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int iI = 0; iI < nodes.getLength(); iI++) {
                alFastaFiles.add(nodes.item(iI).getTextContent());
            }
        }
        catch ( ParserConfigurationException e) {
          e.printStackTrace();
        } catch ( SAXException e) {
          e.printStackTrace();
        } catch ( IOException e) {
          e.printStackTrace();
        } catch ( XPathExpressionException  e) {
          e.printStackTrace();
        }        
        
        //... Depending on which plugin was selected we must write the corresponding x-Tracker file ...//
        if (sPlugin.equals("SILAC")){            
            
        }
        else if (sPlugin.equals("iTraqQuantitation"))
        {            
            //... Write configuration file ...//
            try{
                FileWriter fstream = new FileWriter(sFileName);
                BufferedWriter out = new BufferedWriter(fstream);            
                out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                out.newLine();
                out.write("<!-- ");
                out.newLine();
                out.write("    *** FILE GENERATED VIA PROTEOSUITE ***");                            
                out.newLine();                
                out.write("    This plugin allows the specification of the parameters used for the quantitation process in iTRAQ. Here, we");
                out.newLine();
                out.write("    specify the quantitation method and the correction factors used for the reporter ions.");
                out.newLine();
                out.write("-->");
                out.newLine();
                out.write("<iTraqQuantitation xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"Plugins/iTraqQuantitation.xsd\">");
                out.newLine();
                out.write("	<AssayParamList>");
                out.newLine();

                for (int iI=0; iI<twoDim.size(); iI++)
                {
                    out.write("		<AssayParam>");
                    out.newLine();                                                            
                    List<String> lList = twoDim.get(iI);
                    out.write("			<AssayName>" + lList.get(0).toString() + "</AssayName>");
                    out.newLine();                    
                    out.write("			<mzValue>" + lList.get(1).toString() + "</mzValue>");
                    out.newLine();                    
                    out.write("			<CorrectionFactors>");
                    out.newLine();                     
                    out.write("				<factor deltaMass=\"-2\">" + lList.get(2) + "</factor>");
                    out.newLine();                    
                    out.write("				<factor deltaMass=\"-1\">" + lList.get(3) + "</factor>");
                    out.newLine();
                    out.write("				<factor deltaMass=\"+1\">" + lList.get(4) + "</factor>");
                    out.newLine();
                    out.write("				<factor deltaMass=\"+2\">" + lList.get(5) + "</factor>");
                    out.newLine();                                        
                    out.write("			</CorrectionFactors>");
                    out.newLine();                     
                    out.write("		</AssayParam>");
                    out.newLine();                     
                }
                out.write("	</AssayParamList>");
                out.newLine();
                out.write("	<Setting>");
                out.newLine();
                out.write("		<mzRange>");
                out.newLine();                
                out.write("    			<minus>" + mzRangeMin + "</minus>");
                out.newLine();                
                out.write("    			<plus>" + mzRangeMax + "</plus>");
                out.newLine();
                out.write("		</mzRange>");
                out.newLine();
                out.write("		<IntegrationMethod>" + integrationMethod + "</IntegrationMethod><!--the method used to calculate the area of the peak-->");
                out.newLine();
                out.write("	</Setting>");
                out.newLine();
                out.write("</iTraqQuantitation>");
                out.newLine();
                out.close();
            }
            catch (Exception e)
            {
                System.err.println("Error: " + e.getMessage());
            }   
        } else if (sPlugin.startsWith("emPAI")){
            
            //... Write configuration file ...//
            try{
                FileWriter fstream = new FileWriter(sFileName);
                BufferedWriter out = new BufferedWriter(fstream);            
                out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                out.newLine();
                out.write("<!-- ");
                out.newLine();
                out.write("    *** FILE GENERATED VIA PROTEOSUITE ***");                            
                out.newLine();
                out.write("    This plugin allows the specification of the parameters used for the quantitation process in iTRAQ. Here, we");
                out.newLine();
                out.write("    specify the quantitation method and the correction factors used for the reporter ions.");
                out.newLine();
                out.write("-->");
                out.newLine();
                out.write("<emPaiQuantitation xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"Plugins/emPaiQuantitation.xsd\">");
                out.newLine();
                out.write("    <!--the molecular weight of observable peptide-->");
                out.newLine();
                out.write("    <peptideMwRange>");                
                out.newLine();
                out.write("        <minimum>"+ minPepRange + "</minimum>");
                out.newLine();
                out.write("        <maximum>"+ maxPepRange + "</maximum>");
                out.newLine();
                out.write("    </peptideMwRange>");
                out.newLine();
                out.write("    <fastaFiles>");
                out.newLine();
                for (int iI=0; iI<alFastaFiles.size(); iI++){
                    out.write("        <fastaFile>"+alFastaFiles.get(iI).toString()+"</fastaFile>");
                    out.newLine();
                }
                out.write("    </fastaFiles>");
                out.newLine();
                out.write("    <enzyme>"+ enzyme + "</enzyme>");
                out.newLine();                                
                out.write("</emPaiQuantitation>");
                out.newLine();                
                out.close();
            }
            catch (Exception e)
            {
                System.err.println("Error: " + e.getMessage());
            }               
        }
    }
    /*-----------------------------------------------
     * Generate XTracker output plugin 
     * @param sPlugin - Plugin type (e.g. outputMZQ)
     * @return void
     ------------------------------------------------*/
    private void writeXTrackerOutput(String sPlugin) 
    {
        String sFileName = sWorkspace.replace("\\", "/") + "/xTracker_" + sPlugin + ".xtp";
        String sType="", sVersion="";        
        try{
            FileWriter fstream = new FileWriter(sFileName);
            BufferedWriter out = new BufferedWriter(fstream);            
            out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            out.newLine();
            out.write("<!--");
            out.newLine();
            out.write("    *** FILE GENERATED VIA PROTEOSUITE ***");                            
            out.newLine();
            out.write("    This plugin allows the specification of the output format. ");
            out.newLine();
	    out.write("-->");
            out.newLine();
            if (sPlugin.equals("outputCSV")){
                out.write("<output xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"Plugins/outputGeneral.xsd\">");
                out.newLine();                
                out.write("    <outputFilename>" + sWorkspace.replace("\\", "/") + "/" + sProjectName.replace(".mzq", ".csv") +"</outputFilename>");
                out.newLine();
                out.write("</output>");            
                out.newLine();  
                sType = "CSV";
                sVersion = "N/A";
            }
            if (sPlugin.equals("outputMZQ")){
                out.write("<output xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"Plugins/outputGeneral.xsd\">");
                out.newLine();                
                String sTemp = sProjectName.replace(".mzq", "");
                out.write("    <outputFilename>" + sWorkspace.replace("\\", "/") + "/" + sTemp + ".mzq</outputFilename>");
                out.newLine();
                out.write("</output>");
                out.newLine();
                sType = "MZQ";
                sVersion = mzQVersion;
            }
            out.close();                 
        }
        catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }             
        //... Add file on the Quantitation Files tabsheet ...//
        final DefaultTableModel model = new DefaultTableModel();
        jtQuantFiles.setModel(model);
        model.addColumn("Name");
        model.addColumn("Path");
        model.addColumn("Type");
        model.addColumn("Version");
        if (sType.equals("MZQ")){
            model.insertRow(model.getRowCount(), new Object[]{
                sProjectName.replace(".mzq", "") + ".mzq", 
                sWorkspace.replace("\\", "/") + "/" + sProjectName.replace(".mzq", "") + ".mzq", 
                sType.toLowerCase(), 
                sVersion});
        }
        if (sType.equals("CSV")){
            model.insertRow(model.getRowCount(), new Object[]{
                sProjectName.replace(".mzq", ".csv"), 
                sWorkspace.replace("\\", "/") + "/" + sProjectName.replace(".mzq", ".csv"), 
                sType.toLowerCase(), 
                sVersion});
        }
    }
    /*------------------------------------------------------------------------
     * Checks if a proten detection hypothesis is decoy 
     * @param unmarshaller - unmarshaller element
     * @param proteinDetectionHypothesis - proteinDetectionHypothesis element
     * @return true|false
     -------------------------------------------------------------------------*/    
    private boolean checkIfProteinDetectionHypothesisIsDecoy(MzIdentMLUnmarshaller unmarshaller, ProteinDetectionHypothesis proteinDetectionHypothesis) {
        boolean result = false;
        List<PeptideHypothesis> PeptideHyposthesisList = proteinDetectionHypothesis.getPeptideHypothesis();
        for (int i = 0; i < PeptideHyposthesisList.size(); i++) {
            try {
                PeptideHypothesis peptideHypothesis = PeptideHyposthesisList.get(i);
                String peptidRef = peptideHypothesis.getPeptideEvidenceRef();
                PeptideEvidence peptiedEvidence = unmarshaller.unmarshal(PeptideEvidence.class, peptidRef);
                if (peptiedEvidence.isIsDecoy()) {
                    result = true;
                    break;
                }
            } catch (JAXBException ex) {
                Logger.getLogger(ProteoSuiteView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }    
    /*------------------------------------------------------
     * Main
     * @param args the command line arguments (leave empty)
     * @return void
     -------------------------------------------------------*/
    public static void main(String args[]) {

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() 
        {
            public void run() {
                new ProteoSuiteView().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel FeatureQuant;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenuItem jMzMLCompressed;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JPopupMenu.Separator jSeparator9;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JButton jbCopy;
    private javax.swing.JButton jbCut;
    private javax.swing.JButton jbExportFeatMZQExcel;
    private javax.swing.JButton jbExportMGFExcel;
    private javax.swing.JButton jbExportMascotXMLExcel;
    private javax.swing.JButton jbExportMzIdentMLExcel;
    private javax.swing.JButton jbExportMzMLExcel;
    private javax.swing.JButton jbExportPepMZQExcel;
    private javax.swing.JButton jbExportProtMZQExcel;
    private javax.swing.JButton jbImportFile;
    private javax.swing.JButton jbNewProject;
    private javax.swing.JButton jbOpenProject;
    private javax.swing.JButton jbPaste;
    private javax.swing.JButton jbRunIdentAnalysis;
    private javax.swing.JButton jbRunQuantAnalysis;
    private javax.swing.JButton jbSaveProject;
    private javax.swing.JComboBox jcbOutputFormat;
    private javax.swing.JComboBox jcbPSM;
    private javax.swing.JComboBox jcbTechnique;
    private javax.swing.JDesktopPane jdpMS;
    private javax.swing.JDesktopPane jdpTIC;
    private javax.swing.JEditorPane jepMGFView;
    private javax.swing.JEditorPane jepMZQView;
    private javax.swing.JEditorPane jepMascotXMLView;
    private javax.swing.JEditorPane jepMzIDView;
    private javax.swing.JEditorPane jepMzMLView;
    private javax.swing.JLabel jlExportMGFXLS;
    private javax.swing.JLabel jlExportMzIDXLS;
    private javax.swing.JLabel jlExportMzIDXLS1;
    private javax.swing.JLabel jlExportMzMLXLS;
    private javax.swing.JLabel jlFeatureMZQ;
    private javax.swing.JLabel jlFileNameMGF;
    private javax.swing.JLabel jlFileNameMGFText;
    private javax.swing.JLabel jlFileNameMascotXML;
    private javax.swing.JLabel jlFileNameMascotXMLText;
    private javax.swing.JLabel jlFileNameMzID;
    private javax.swing.JLabel jlFileNameMzIDText;
    private javax.swing.JLabel jlFileNameMzML;
    private javax.swing.JLabel jlFileNameMzMLText;
    private javax.swing.JLabel jlFileNameMzQ;
    private javax.swing.JLabel jlFileNameMzQText;
    private javax.swing.JLabel jlFiles;
    private javax.swing.JLabel jlIdentFiles;
    private javax.swing.JLabel jlIdentFilesStatus;
    private javax.swing.JLabel jlPeptide;
    private javax.swing.JLabel jlPeptideMZQ;
    private javax.swing.JLabel jlPeptideMzId;
    private javax.swing.JLabel jlPeptideSpectrumMatches;
    private javax.swing.JLabel jlProperties;
    private javax.swing.JLabel jlProtein;
    private javax.swing.JLabel jlProteinMZQ;
    private javax.swing.JLabel jlProteinMzId;
    private javax.swing.JLabel jlQuantFiles;
    private javax.swing.JLabel jlQuantFilesStatus;
    private javax.swing.JLabel jlRT;
    private javax.swing.JLabel jlRawFiles;
    private javax.swing.JLabel jlRawFilesStatus;
    private javax.swing.JLabel jlScan;
    private javax.swing.JLabel jlScanTitle;
    private javax.swing.JLabel jlSearchMGF;
    private javax.swing.JLabel jlSearchMascotXML;
    private javax.swing.JLabel jlSearchMzId;
    private javax.swing.JLabel jlSearchMzML;
    private javax.swing.JLabel jlSearchMzQFeat;
    private javax.swing.JLabel jlSearchMzQPep;
    private javax.swing.JLabel jlSearchMzQProt;
    private javax.swing.JLabel jlTechnique;
    private javax.swing.JLabel jlViewer;
    private javax.swing.JMenuItem jm2DView;
    private javax.swing.JMenuItem jm3DView;
    private javax.swing.JMenuItem jmAbout;
    private javax.swing.JMenu jmAnalyze;
    private javax.swing.JMenuItem jmCheckUpdates;
    private javax.swing.JMenuItem jmCloseProject;
    private javax.swing.JMenuItem jmContactUs;
    private javax.swing.JMenu jmConverters;
    private javax.swing.JMenuItem jmCopy;
    private javax.swing.JMenuItem jmCustomize;
    private javax.swing.JMenuItem jmCut;
    private javax.swing.JMenu jmDatabases;
    private javax.swing.JMenu jmEdit;
    private javax.swing.JMenuItem jmEditIdent;
    private javax.swing.JMenuItem jmEditQuant;
    private javax.swing.JMenuItem jmExit;
    private javax.swing.JMenu jmFile;
    private javax.swing.JMenu jmHelp;
    private javax.swing.JMenuItem jmHelpContent;
    private javax.swing.JMenuItem jmImportFile;
    private javax.swing.JMenuItem jmLog;
    private javax.swing.JMenuItem jmMGFView;
    private javax.swing.JMenuBar jmMain;
    private javax.swing.JMenuItem jmMascotXMLView;
    private javax.swing.JMenuItem jmMaxQ2MZQ;
    private javax.swing.JMenuItem jmMzIdentMLView;
    private javax.swing.JMenuItem jmMzML2MGF;
    private javax.swing.JMenuItem jmMzMLView;
    private javax.swing.JMenuItem jmMzQuantMLView;
    private javax.swing.JMenuItem jmNewProject;
    private javax.swing.JMenuItem jmOpenProject;
    private javax.swing.JMenuItem jmOpenRecentProject;
    private javax.swing.JMenuItem jmOptions;
    private javax.swing.JMenuItem jmPaste;
    private javax.swing.JMenuItem jmPrint;
    private javax.swing.JMenuItem jmProgenesis2MZQ;
    private javax.swing.JMenu jmProject;
    private javax.swing.JMenuItem jmRawData;
    private javax.swing.JMenuItem jmRunIdentAnalysis;
    private javax.swing.JMenuItem jmRunQuantAnalysis;
    private javax.swing.JMenuItem jmSaveProject;
    private javax.swing.JMenuItem jmSaveProjectAs;
    private javax.swing.JMenuItem jmShowProjectFiles;
    private javax.swing.JMenuItem jmShowProperties;
    private javax.swing.JMenuItem jmShowViewer;
    private javax.swing.JMenuItem jmSpectrum;
    private javax.swing.JMenuItem jmSubmitPRIDE;
    private javax.swing.JMenuItem jmTIC;
    private javax.swing.JMenu jmTools;
    private javax.swing.JMenu jmView;
    private javax.swing.JMenu jmWindow;
    private javax.swing.JPanel jp2D;
    private javax.swing.JPanel jp3D;
    private javax.swing.JPanel jpFeatureQuantHeader;
    private javax.swing.JPanel jpLeftMenuBottom;
    private javax.swing.JPanel jpLeftMenuTop;
    private javax.swing.JPanel jpLeftPanelView;
    private javax.swing.JPanel jpLeftViewer;
    private javax.swing.JPanel jpLeftViewerBottom;
    private javax.swing.JPanel jpLeftViewerDetails;
    private javax.swing.JPanel jpMGF;
    private javax.swing.JPanel jpMGFMenu;
    private javax.swing.JPanel jpMGFViewHeader;
    private javax.swing.JPanel jpMainPanelView;
    private javax.swing.JPanel jpMascotXML;
    private javax.swing.JPanel jpMascotXMLMenu;
    private javax.swing.JPanel jpMascotXMLViewHeader;
    private javax.swing.JPanel jpMzId;
    private javax.swing.JPanel jpMzIdMenu;
    private javax.swing.JPanel jpMzIdentMLViewHeader;
    private javax.swing.JPanel jpMzML;
    private javax.swing.JPanel jpMzMLMenu;
    private javax.swing.JPanel jpMzMLViewHeader;
    private javax.swing.JPanel jpMzQuantML;
    private javax.swing.JPanel jpMzQuantMLHeader;
    private javax.swing.JPanel jpPanel4;
    private javax.swing.JPanel jpPeptideQuant;
    private javax.swing.JPanel jpPeptideQuantHeader;
    private javax.swing.JPanel jpProjectDetails;
    private javax.swing.JPanel jpProjectHeader;
    private javax.swing.JPanel jpProjectStatus;
    private javax.swing.JPanel jpProperties;
    private javax.swing.JPanel jpPropertiesBox;
    private javax.swing.JPanel jpPropetiesTab;
    private javax.swing.JPanel jpProteinQuant;
    private javax.swing.JPanel jpProteinQuantHeader;
    private javax.swing.JPanel jpQuantFiles;
    private javax.swing.JPanel jpRawDataValues;
    private javax.swing.JPanel jpRawDataValuesMenu;
    private javax.swing.JPanel jpSpectrum;
    private javax.swing.JPanel jpTIC;
    private javax.swing.JPanel jpToolBar;
    private javax.swing.JPanel jpViewerAndProperties;
    private javax.swing.JSplitPane jspFeatureQuant;
    private javax.swing.JScrollPane jspFeatureQuantDetail;
    private javax.swing.JScrollPane jspIdentFiles;
    private javax.swing.JSplitPane jspLeftMenuBottom;
    private javax.swing.JSplitPane jspLeftPanelView;
    private javax.swing.JSplitPane jspLeftViewer;
    private javax.swing.JSplitPane jspLeftViewerDetails;
    private javax.swing.JPanel jspLeftViewerHeader;
    private javax.swing.JScrollPane jspLog;
    private javax.swing.JSplitPane jspMGF;
    private javax.swing.JSplitPane jspMGFDetail;
    private javax.swing.JScrollPane jspMGFSubDetail;
    private javax.swing.JSplitPane jspMainPanelView;
    private javax.swing.JSplitPane jspMascotXML;
    private javax.swing.JScrollPane jspMascotXMLDetail;
    private javax.swing.JSplitPane jspMascotXMLSubDetail;
    private javax.swing.JScrollPane jspMascotXMLView;
    private javax.swing.JScrollPane jspMzIDProtGroup;
    private javax.swing.JScrollPane jspMzIDView;
    private javax.swing.JSplitPane jspMzId;
    private javax.swing.JSplitPane jspMzIdDetail;
    private javax.swing.JScrollPane jspMzIdProtGroup;
    private javax.swing.JSplitPane jspMzML;
    private javax.swing.JSplitPane jspMzMLDetail;
    private javax.swing.JScrollPane jspMzMLSubDetail;
    private javax.swing.JSplitPane jspMzQuantML;
    private javax.swing.JSplitPane jspPeptideQuant;
    private javax.swing.JScrollPane jspPeptideQuantDetail;
    private javax.swing.JSplitPane jspProjectDetails;
    private javax.swing.JSplitPane jspProperties;
    private javax.swing.JSplitPane jspProteinQuant;
    private javax.swing.JScrollPane jspProteinQuantDetail;
    private javax.swing.JScrollPane jspQuantFiles;
    private javax.swing.JScrollPane jspRawData;
    private javax.swing.JSplitPane jspRawDataValues;
    private javax.swing.JScrollPane jspRawFiles;
    private javax.swing.JSplitPane jspViewerAndProperties;
    private javax.swing.JTextField jtFeatureMZQ;
    private javax.swing.JTable jtFeatureQuant;
    private javax.swing.JTable jtIdentFiles;
    private javax.swing.JTable jtMGF;
    private javax.swing.JTextField jtMSIndex;
    private javax.swing.JTextField jtMSMz;
    private javax.swing.JTable jtMascotXMLView;
    private javax.swing.JTable jtMzIDProtGroup;
    private javax.swing.JTable jtMzId;
    private javax.swing.JTable jtMzML;
    private javax.swing.JTextField jtPeptide;
    private javax.swing.JTextField jtPeptideMZQ;
    private javax.swing.JTextField jtPeptideMzId;
    private javax.swing.JTable jtPeptideQuant;
    private javax.swing.JTextField jtProtein;
    private javax.swing.JTextField jtProteinMZQ;
    private javax.swing.JTextField jtProteinMzId;
    private javax.swing.JTable jtProteinQuant;
    private javax.swing.JTable jtQuantFiles;
    private javax.swing.JTextField jtRT;
    private javax.swing.JTable jtRawData;
    private javax.swing.JTable jtRawFiles;
    private javax.swing.JTextField jtScan;
    private javax.swing.JTextField jtScanTitle;
    private javax.swing.JTable jtTemplate1;
    private javax.swing.JTable jtTemplate2;
    private javax.swing.JTextArea jtaLog;
    private javax.swing.JToolBar jtbFeatMZQ;
    private javax.swing.JToolBar jtbMGFOptions;
    private javax.swing.JToolBar jtbMzIdentMLOptions;
    private javax.swing.JToolBar jtbMzIdentMLOptions1;
    private javax.swing.JToolBar jtbMzMLOptions;
    private javax.swing.JToolBar jtbPepMZQ;
    private javax.swing.JToolBar jtbProtMZQ;
    private javax.swing.JTabbedPane jtpIdentFiles;
    private javax.swing.JTabbedPane jtpLog;
    private javax.swing.JTabbedPane jtpMzQuantMLDetail;
    private javax.swing.JTabbedPane jtpProperties;
    private javax.swing.JTabbedPane jtpQuantFiles;
    private javax.swing.JTabbedPane jtpRawFiles;
    private javax.swing.JTabbedPane jtpViewer;
    // End of variables declaration//GEN-END:variables
}
