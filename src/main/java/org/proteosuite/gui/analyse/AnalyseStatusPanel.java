package org.proteosuite.gui.analyse;

import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.proteosuite.model.AnalyseData;

/**
 *
 * @author SPerkins
 */
public class AnalyseStatusPanel extends JPanel {
    private static final JPanel rawDataSection = new JPanel();
    private static final JLabel rawData = new JLabel("Raw Data");    
    private static final JLabel rawDataStatus = new JLabel();
    private static final JPanel conditionsSection = new JPanel();
    private static final JLabel conditions = new JLabel("Conditions");
    private static final JLabel conditionsStatus = new JLabel();
    private static final JPanel identificationsSection = new JPanel();
    private static final JLabel identifications = new JLabel("Identifications");
    private static final JLabel identificationsStatus = new JLabel();
    private static final JPanel quantitationSection = new JPanel();
    private static final JLabel quantitation = new JLabel("Quantitation");
    private static final JLabel quantitationStatus = new JLabel();
    private static final JPanel mappingSection = new JPanel();
    private static final JLabel mapping = new JLabel("Mapping");
    private static final JLabel mappingStatus = new JLabel();
    private static ImageIcon notDone;
    private static ImageIcon done;
    private static ImageIcon processing;
    public AnalyseStatusPanel() {
        notDone = new ImageIcon(getClass()
				.getClassLoader().getResource("images/empty.gif"));
        done = new ImageIcon(getClass()
				.getClassLoader().getResource("images/fill.gif"));
        processing = new ImageIcon(getClass()
				.getClassLoader().getResource("images/loading.gif"));
        
        rawData.setFont(new Font(rawData.getFont().getFontName(), rawData.getFont().getStyle(), 20));
        conditions.setFont(new Font(rawData.getFont().getFontName(), rawData.getFont().getStyle(), 20));
        identifications.setFont(new Font(rawData.getFont().getFontName(), rawData.getFont().getStyle(), 20));
        quantitation.setFont(new Font(rawData.getFont().getFontName(), rawData.getFont().getStyle(), 20));
        mapping.setFont(new Font(rawData.getFont().getFontName(), rawData.getFont().getStyle(), 20));
        
        rawDataStatus.setIcon(notDone);
        conditionsStatus.setIcon(notDone);
        identificationsStatus.setIcon(notDone);
        quantitationStatus.setIcon(notDone);
        mappingStatus.setIcon(notDone);
        
        rawDataSection.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 4));
        conditionsSection.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 4));
        identificationsSection.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 4));
        quantitationSection.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 4));
        mappingSection.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 4));
        
        //setBorder(BorderFactory.createEtchedBorder());
	//setPreferredSize(new Dimension(102, 30));
        
        setLayout(new FlowLayout(FlowLayout.CENTER, 30, 4));        
        
        rawDataSection.add(rawDataStatus);
        rawDataSection.add(rawData);        
        conditionsSection.add(conditionsStatus);
        conditionsSection.add(conditions);        
        identificationsSection.add(identificationsStatus);
        identificationsSection.add(identifications);        
        quantitationSection.add(quantitationStatus); 
        quantitationSection.add(quantitation);
        mappingSection.add(mappingStatus);
        mappingSection.add(mapping);
        
        add(rawDataSection);
        add(conditionsSection);
        add(identificationsSection);
        add(quantitationSection);
        add(mappingSection);
    }
    
    public void reset() {
        setRawDataNotDone();
        setConditionsNotDone();
        setIdentificationsNotDone();
        setQuantitationNotDone();
        setMappingNotDone();
    }
    
    public void setRawDataDone() {
        rawDataStatus.setIcon(done);
    }
    
    public void setRawDataNotDone() {
        rawDataStatus.setIcon(notDone);
    }
    
    public void setRawDataProcessing() {
        rawDataStatus.setIcon(processing);
    }
    
    public void setConditionsDone() {
        conditionsStatus.setIcon(done);
    }
    
    public void setConditionsNotDone() {
        conditionsStatus.setIcon(notDone);
    }
    
    public void setConditionsProcessing() {
        conditionsStatus.setIcon(processing);
    }
    
    public void setIdentificationsDone() {
        identificationsStatus.setIcon(done);
    }
    
    public void setIdentificationsNotDone() {
        identificationsStatus.setIcon(notDone);
    }
    
    public void setIdentificationsProcessing() {
        identificationsStatus.setIcon(processing);
    }
    
    public void setQuantitationDone() {
        quantitationStatus.setIcon(done);
    }
    
    public void setQuantitationNotDone() {
        quantitationStatus.setIcon(notDone);
    }
    
    public void setQuantitationProcessing() {
        quantitationStatus.setIcon(processing);
    }
    
    public void setMappingDone() {
        mappingStatus.setIcon(done);
    }
    
    public void setMappingNotDone() {
        mappingStatus.setIcon(notDone);
    }
    
    public void setMappingProcessing() {
        mappingStatus.setIcon(processing);
    }    
    
    public synchronized void checkAndUpdateRawDataStatus() {
        AnalyseData data = AnalyseData.getInstance();
        if (data.getRawDataCount() == 0) {
            setRawDataNotDone();
            return;
        }
        
        boolean allDataLoaded = true;
        for (int i = 0; i < data.getRawDataCount(); i++ ) {
            if (!data.getRawDataFile(i).isLoaded()) {
                allDataLoaded = false;
                break;
            }
        }
        
        if (allDataLoaded) {
            setRawDataDone();
        }
    }
    
    public synchronized void checkAndUpdateIdentificationsStatus() {
        AnalyseData data = AnalyseData.getInstance();
        boolean allDataLoaded = true;
        for (int i = 0; i < data.getRawDataCount(); i++) {
            if (data.getRawDataFile(i).getIdentificationDataFile() == null  || !data.getRawDataFile(i).getIdentificationDataFile().isLoaded()) {
                allDataLoaded = false;
                break;
            }
        }
        
        if (allDataLoaded) {
            setIdentificationsDone();
        }
    }
}
