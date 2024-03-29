package org.proteosuite.gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.proteosuite.config.Config;
import org.proteosuite.config.GlobalConfig;
import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.gui.analyse.CreateOrLoadIdentificationsStep;
import org.proteosuite.gui.tables.CreateOrLoadIdentificationsTable;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.MzIdentMLFile;

/**
 *
 * @author SPerkins
 */
public class LoadIdentificationsForSelectedListener implements ActionListener {
    private static final GlobalConfig config = Config.getInstance().getGlobalConfig();

    private CreateOrLoadIdentificationsStep step;

    public LoadIdentificationsForSelectedListener(CreateOrLoadIdentificationsStep step) {
        this.step = step;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        CreateOrLoadIdentificationsTable identificationsTable = step.getIdentificationsTable();
        int[] selectedRawFiles = identificationsTable.getSelectedRows();
        if (selectedRawFiles.length == 0) {
            return;
        }

        AnalyseData data = AnalyseData.getInstance();
        JFileChooser chooser = new JFileChooser(config.getRememberedDirectory());
        FileNameExtensionFilter mzq_filter = new FileNameExtensionFilter(
        "Identification Data Files", "mzIdentML", "mzid");
        chooser.setFileFilter(mzq_filter);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setMultiSelectionEnabled(true);
        int returnVal = chooser.showOpenDialog(step);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File[] files = chooser.getSelectedFiles();

            if (!files[0].getParent().equals(config.getRememberedDirectory())) {
                config.setRememberedDirectory(files[0].getParent());            	
            }
            
            if (files.length < 2) {
                MzIdentMLFile mzIdentML = new MzIdentMLFile(files[0], data.getRawDataFile(selectedRawFiles[0]));
                mzIdentML.setCleanable(true);
                for (int i : selectedRawFiles) {
                    data.getRawDataFile(i).setIdentificationDataFile(mzIdentML);
                }
            } else {
                for (File file : files) {
                    for (int i : selectedRawFiles) {
                        if (stripExtension(data.getRawDataFile(i).getAbsoluteFileName()).equals(stripExtension(file.getAbsolutePath()))) {
                            MzIdentMLFile mzIdentML = new MzIdentMLFile(file, data.getRawDataFile(i));
                            mzIdentML.setCleanable(true);
                            data.getRawDataFile(i).setIdentificationDataFile(mzIdentML);
                            break;
                        }
                    }
                }
            }   
            
            AnalyseDynamicTab.getInstance().getAnalyseStatusPanel().setIdentificationsProcessing();
        }

        step.refreshFromData();
    }
    
    public String stripExtension(String fileName) {
        return fileName.replaceAll("\\.[^\\.]+$", "").replace("_msgfplus", "").replace("_rt_corrected", "");
    }

}
