package org.proteosuite.gui.analyse;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.proteosuite.gui.listener.RawDataAndMultiplexingSectionListener;
import org.proteosuite.model.AnalyseData;

/**
 * 
 * @author SPerkins
 */
public class AnalyseStatusPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final JPanel rawDataSection = new JPanel();
	private static final JLabel rawData = new JLabel("Raw Data");
	private static final JLabel rawDataStatus = new JLabel();
	private static final JPanel conditionsSection = new JPanel();
	private static final JLabel conditions = new JLabel("Conditions");
	private static final JLabel conditionsStatus = new JLabel();
	private static final JPanel identificationsSection = new JPanel();
	private static final JLabel identifications = new JLabel("Identifications");
	private static final JLabel identificationsStatus = new JLabel();
	private static final JPanel cleanIdentificationsSection = new JPanel();
	private static final JLabel cleanIdentifications = new JLabel(
			"Clean Identifications");
	private static final JLabel cleanIdentificationsStatus = new JLabel();
	private static final JPanel quantitationSection = new JPanel();
	private static final JLabel quantitation = new JLabel("Quantitation");
	private static final JLabel quantitationStatus = new JLabel();
	private static final JPanel mappingSection = new JPanel();
	private static final JLabel mapping = new JLabel("Mapping");
	private static final JLabel mappingStatus = new JLabel();
        private static final JPanel normalisationSection = new JPanel();
        private static final JLabel normalisation = new JLabel("Normalisation");
        private static final JLabel normalisationStatus = new JLabel();
	private static final JPanel proteinInferenceSection = new JPanel();
	private static final JLabel proteinInference = new JLabel(
			"Protein Inference");
	private static final JLabel proteinInferenceStatus = new JLabel();
        private static final JPanel anovaSection = new JPanel();
        private static final JLabel anova = new JLabel("ANOVA");
        private static final JLabel anovaStatus = new JLabel();
        private static final JPanel resultsSection = new JPanel();
        private static final JLabel results = new JLabel("Results");
        private static final JLabel resultsStatus = new JLabel();
	private static ImageIcon notDone;
	private static ImageIcon done;
	private static ImageIcon processing;
        private static AnalyseStatusPanel INSTANCE = null;
        
        public static AnalyseStatusPanel getInstance() {
            if (INSTANCE == null) {
                INSTANCE = new AnalyseStatusPanel();
            }
            
            return INSTANCE;
        }

	private AnalyseStatusPanel() {
		super(new FlowLayout(FlowLayout.CENTER, 30, 4));
		
		notDone = new ImageIcon(getClass().getClassLoader().getResource(
				"images/empty.gif"));
		done = new ImageIcon(getClass().getClassLoader().getResource(
				"images/fill.gif"));
		processing = new ImageIcon(getClass().getClassLoader().getResource(
				"images/loading.gif"));

		rawData.setFont(new Font(rawData.getFont().getFamily(), rawData
				.getFont().getStyle(), 20));
		conditions.setFont(new Font(conditions.getFont().getFamily(),
				conditions.getFont().getStyle(), 20));
		identifications.setFont(new Font(identifications.getFont()
				.getFamily(), identifications.getFont().getStyle(), 20));
		cleanIdentifications.setFont(new Font(cleanIdentifications.getFont()
				.getName(), cleanIdentifications.getFont().getStyle(), 20));
		quantitation.setFont(new Font(quantitation.getFont().getFamily(),
				quantitation.getFont().getStyle(), 20));
		mapping.setFont(new Font(mapping.getFont().getFamily(), mapping
				.getFont().getStyle(), 20));
                normalisation.setFont(new Font(normalisation.getFont()
				.getFamily(), normalisation.getFont().getStyle(), 20));
		proteinInference.setFont(new Font(proteinInference.getFont()
				.getFamily(), proteinInference.getFont().getStyle(), 20));
                anova.setFont(new Font(anova.getFont()
				.getFamily(), anova.getFont().getStyle(), 20));
                results.setFont(new Font(results.getFont()
				.getFamily(), results.getFont().getStyle(), 20));

		rawDataStatus.setIcon(notDone);
		conditionsStatus.setIcon(notDone);
		identificationsStatus.setIcon(notDone);
		cleanIdentificationsStatus.setIcon(notDone);
		quantitationStatus.setIcon(notDone);
		mappingStatus.setIcon(notDone);
                normalisationStatus.setIcon(notDone);
		proteinInferenceStatus.setIcon(notDone);
                anovaStatus.setIcon(notDone);
                resultsStatus.setIcon(notDone);

		rawDataSection.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 4));
		conditionsSection.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 4));
		identificationsSection
				.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 4));
		cleanIdentificationsSection.setLayout(new FlowLayout(FlowLayout.CENTER,
				5, 4));
		quantitationSection.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 4));
		mappingSection.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 4));
                normalisationSection.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 4));
		proteinInferenceSection.setLayout(new FlowLayout(FlowLayout.CENTER, 5,
				4));
                anovaSection.setLayout(new FlowLayout(FlowLayout.CENTER, 5,
				4));
                resultsSection.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 4));

		rawDataSection.add(rawDataStatus);
		rawDataSection.add(rawData);
		conditionsSection.add(conditionsStatus);
		conditionsSection.add(conditions);
		identificationsSection.add(identificationsStatus);
		identificationsSection.add(identifications);
		cleanIdentificationsSection.add(cleanIdentificationsStatus);
		cleanIdentificationsSection.add(cleanIdentifications);
		quantitationSection.add(quantitationStatus);
		quantitationSection.add(quantitation);
		mappingSection.add(mappingStatus);
		mappingSection.add(mapping);
                normalisationSection.add(normalisationStatus);
                normalisationSection.add(normalisation);
		proteinInferenceSection.add(proteinInferenceStatus);
		proteinInferenceSection.add(proteinInference);
                anovaSection.add(anovaStatus);
                anovaSection.add(anova);
                resultsSection.add(resultsStatus);
                resultsSection.add(results);

		add(rawDataSection);
		add(conditionsSection);
		add(identificationsSection);
		add(cleanIdentificationsSection);
		add(quantitationSection);
		add(mappingSection);
                add(normalisationSection);
		add(proteinInferenceSection);
                add(anovaSection);
                add(resultsSection);
                
                rawData.addMouseListener(new RawDataAndMultiplexingSectionListener());
	}
        
        public void setGenomeAnnotationMode() {
            conditionsSection.remove(conditionsStatus);
            cleanIdentificationsSection.remove(cleanIdentificationsStatus);
            quantitationSection.remove(quantitationStatus);
            mappingSection.remove(mappingStatus);
            proteinInferenceSection.remove(proteinInferenceStatus);
            anovaSection.remove(anovaStatus);
            
            conditions.setForeground(Color.LIGHT_GRAY);
            cleanIdentifications.setForeground(Color.LIGHT_GRAY);
            quantitation.setForeground(Color.LIGHT_GRAY);
            mapping.setForeground(Color.LIGHT_GRAY);
            normalisation.setForeground(Color.LIGHT_GRAY);
            proteinInference.setForeground(Color.LIGHT_GRAY);
            anovaSection.setForeground(Color.LIGHT_GRAY);
            
            this.revalidate();
        }

	public void reset() {
		setRawDataNotDone();
		setConditionsNotDone();
		setIdentificationsNotDone();
		setCleanIdentificationsNotDone();
		setQuantitationNotDone();
		setMappingNotDone();
                setNormalisationNotDone();
		setProteinInferenceNotDone();
                setAnovaNotDone();
                setResultsNotDone();
	}       
        
        private void setAllAsPlainText() {
            rawData.setFont(new Font(rawData.getFont()
				.getFamily(), Font.PLAIN, 20));
            
            conditions.setFont(new Font(conditions.getFont()
				.getFamily(), Font.PLAIN, 20));
            
            identifications.setFont(new Font(identifications.getFont()
				.getFamily(), Font.PLAIN, 20));
            
            cleanIdentifications.setFont(new Font(cleanIdentifications.getFont()
				.getFamily(), Font.PLAIN, 20));
            
            quantitation.setFont(new Font(quantitation.getFont()
				.getFamily(), Font.PLAIN, 20));
            
            mapping.setFont(new Font(mapping.getFont()
				.getFamily(), Font.PLAIN, 20));
            
            normalisation.setFont(new Font(normalisation.getFont()
				.getFamily(), Font.PLAIN, 20));
            
            proteinInference.setFont(new Font(proteinInference.getFont()
				.getFamily(), Font.PLAIN, 20));
            
            anova.setFont(new Font(anova.getFont()
				.getFamily(), Font.PLAIN, 20));
            
            results.setFont(new Font(results.getFont()
				.getFamily(), Font.PLAIN, 20));
        }
        
        public void setRawDataAsCurrentStep() {        
            setAllAsPlainText();
            rawData.setFont(new Font(rawData.getFont()
				.getFamily(), Font.BOLD, 20));
        }
        
        public void setConditionsAsCurrentStep() {      
            setAllAsPlainText();
            conditions.setFont(new Font(conditions.getFont()
				.getFamily(), Font.BOLD, 20));
        }
        
        public void setIdentificationsAsCurrentStep() {   
            setAllAsPlainText();
            identifications.setFont(new Font(identifications.getFont()
				.getFamily(), Font.BOLD, 20));
        }
        
        public void setCleanConditionsAsCurrentStep() { 
            setAllAsPlainText();
            cleanIdentifications.setFont(new Font(cleanIdentifications.getFont()
				.getFamily(), Font.BOLD, 20));
        }
        
        public void setQuantitationAsCurrentStep() {  
            setAllAsPlainText();
            quantitation.setFont(new Font(quantitation.getFont()
				.getFamily(), Font.BOLD, 20));
        }
        
        public void setNormalisationAsCurrentStep() {
            setAllAsPlainText();
            normalisation.setFont(new Font(normalisation.getFont()
				.getFamily(), Font.BOLD, 20));
        }
        
        public void setAnovaAsCurrentStep() {
            setAllAsPlainText();
            anova.setFont(new Font(anova.getFont()
				.getFamily(), Font.BOLD, 20));
        }
        
        public void setResultsAsCurrentStep() {
            setAllAsPlainText();
            results.setFont(new Font(results.getFont()
				.getFamily(), Font.BOLD, 20));
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

	public void setCleanIdentificationsDone() {
		cleanIdentificationsStatus.setIcon(done);
	}

	public void setCleanIdentificationsNotDone() {
		cleanIdentificationsStatus.setIcon(notDone);
	}

	public void setCleanIdentificationsProcessing() {
		cleanIdentificationsStatus.setIcon(processing);
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
        
        public void setNormalisationDone() {
		normalisationStatus.setIcon(done);
	}

	public void setNormalisationNotDone() {
		normalisationStatus.setIcon(notDone);
	}

	public void setNormalisationProcessing() {
		normalisationStatus.setIcon(processing);
	}

	public void setProteinInferenceDone() {
		proteinInferenceStatus.setIcon(done);
	}

	public void setProteinInferenceNotDone() {
		proteinInferenceStatus.setIcon(notDone);
	}

	public void setProteinInferenceProcessing() {
		proteinInferenceStatus.setIcon(processing);
	}
        
        public void setAnovaDone() {
		anovaStatus.setIcon(done);
	}

	public void setAnovaNotDone() {
		anovaStatus.setIcon(notDone);
	}

	public void setAnovaProcessing() {
		anovaStatus.setIcon(processing);
	}
        
        public void setResultsDone() {
		resultsStatus.setIcon(done);
	}

	public void setResultsNotDone() {
		resultsStatus.setIcon(notDone);
	}

	public void setResultsProcessing() {
		resultsStatus.setIcon(processing);
	}

	public synchronized void checkAndUpdateRawDataStatus() {
		AnalyseData data = AnalyseData.getInstance();
		if (data.getRawDataCount() == 0) {
			setRawDataNotDone();
			return;
		}

		boolean allDataLoaded = true;
		for (int i = 0; i < data.getRawDataCount(); i++) {
			if (!data.getRawDataFile(i).isLoaded()) {
				allDataLoaded = false;
				break;
			}
		}

		if (allDataLoaded) {
			setRawDataDone();
		}
	}

	public synchronized boolean checkAndUpdateIdentificationsStatus() {
		AnalyseData data = AnalyseData.getInstance();
		boolean allDataLoaded = true;
		for (int i = 0; i < data.getRawDataCount(); i++) {
			if (data.getRawDataFile(i).getIdentificationDataFile() == null
					|| !data.getRawDataFile(i).getIdentificationDataFile()
							.isLoaded()) {
				allDataLoaded = false;
				break;
			}
		}

		if (allDataLoaded) {
			setIdentificationsDone();
		}
                
                return allDataLoaded;
	}
}