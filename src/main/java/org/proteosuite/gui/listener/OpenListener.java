package org.proteosuite.gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.proteosuite.config.Config;
import org.proteosuite.config.GlobalConfig;
import org.proteosuite.gui.TabbedMainPanel;
import org.proteosuite.gui.analyse.AnalyseDynamicTab;
import org.proteosuite.gui.analyse.RawDataAndMultiplexingStep;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.InspectModel;
import org.proteosuite.model.MascotGenericFormatFile;
import org.proteosuite.model.MzIdentMLFile;
import org.proteosuite.model.MzMLFile;
import org.proteosuite.model.MzQuantMLFile;
import org.proteosuite.model.RawDataFile;
import org.proteosuite.utils.StringUtils;

public class OpenListener implements ActionListener {

    private static final AnalyseData data = AnalyseData.getInstance();
    private static final InspectModel model = data.getInspectModel();
    private static final GlobalConfig config = Config.getInstance().getGlobalConfig();

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser openFile = new JFileChooser(config.getRememberedDirectory());
        boolean choicesValid = false;
        File[] files = null;
        boolean folderMode = false;

        while (!choicesValid) {
            openFile.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            openFile.setMultiSelectionEnabled(true);
            openFile.setDialogTitle("Choose Files/Folders");
            openFile.setAcceptAllFileFilterUsed(true);
            openFile.setMultiSelectionEnabled(true);
            openFile.setFileFilter(new FileNameExtensionFilter("MGF, mzML, mzIdentML, mzQuantML",
                    "mgf", "mzml", "mzq", "mzid", "mzidentml", "mzq", "mzquantml"));

            int result = openFile.showOpenDialog(null);
            if (result == JFileChooser.CANCEL_OPTION) {
                return;
            }

            files = openFile.getSelectedFiles();
            if (files == null || files.length == 0) {
                files = new File[]{openFile.getSelectedFile()};
            }

            // Need to check for a mixture of folders and files, and also disallow mutiple folders.            
            boolean foundFiles = false;
            boolean foundDirectories = false;
            boolean foundMultipleDirectories = false;
            for (File file : files) {
                if (file.isFile()) {
                    foundFiles = true;
                } else if (file.isDirectory()) {
                    if (foundDirectories) {
                        foundMultipleDirectories = true;
                    }

                    foundDirectories = true;
                }
            }

            if ((foundFiles && foundDirectories) || foundMultipleDirectories) {
                JOptionPane
                        .showConfirmDialog(
                                TabbedMainPanel.getInstance(),
                                "You have chosen a invalid selection of files and folders:\n"
                                + "- A directory/folder may not be selected together with files.\n"
                                + "- Multiple directories may not be selected together.\n"
                                + "You may wish to restructure your data locations before trying again.",
                                "Invalid Selection", JOptionPane.PLAIN_MESSAGE,
                                JOptionPane.ERROR_MESSAGE);
            } else {
                choicesValid = true;

                // At this stage if foundDirectories if true, then only a single folder was selected.
                folderMode = foundDirectories;
            }
        }

        Set<String> fileNamesAlreadyLoaded = new HashSet<>();

        for (File file : files) {
            if (!folderMode && !file.getParent().equals(config.getRememberedDirectory())) {
                config.setRememberedDirectory(file.getParent());                
            } else if (folderMode && !file.toString().equals(config.getRememberedDirectory())) {
                config.setRememberedDirectory(file.getPath());                
            }

            if (folderMode) {
                File[] folderFiles = file.listFiles(new FileExtensionFilter("MGF", "mzML", "mzQ", "mzQuantML", "mzIdentML", "mzid"));
                boolean detectedValidFile = false;
                for (File folderFile : folderFiles) {
                    if (rawFileAlreadyLoaded(folderFile)) {
                        fileNamesAlreadyLoaded.add(folderFile.getName());
                        continue;
                    }

                    if (processFile(folderFile, true)) {
                        detectedValidFile = true;
                    }
                }

                if (detectedValidFile) {
                    AnalyseDynamicTab.getInstance().getAnalyseStatusPanel()
                            .setRawDataProcessing();
                }

            } else {
                if (rawFileAlreadyLoaded(file)) {
                    fileNamesAlreadyLoaded.add(file.getName());
                    continue;
                }

                boolean rawFileProcessed = processFile(file, false);
                if (rawFileProcessed) {
                    AnalyseDynamicTab.getInstance().getAnalyseStatusPanel()
                            .setRawDataProcessing();
                }
            }
        }

        if (fileNamesAlreadyLoaded.size() > 0) {
            JOptionPane
                    .showConfirmDialog(
                            TabbedMainPanel.getInstance(),
                            "You have chosen to load data files that have already been loaded:\n"
                            + StringUtils.join("\n", fileNamesAlreadyLoaded) + "\n"
                            + "Each data file may only be loaded once.\n",
                            "Data File Already Loaded", JOptionPane.PLAIN_MESSAGE,
                            JOptionPane.ERROR_MESSAGE);
        }
    }

    private static boolean processFile(File file, boolean folderMode) {
        String name = file.getName();
        String[] tmp = name.split(Pattern.quote("."));
        String extension = tmp[tmp.length - 1];
        boolean rawFileDetected = false;

        if (extension.equalsIgnoreCase("mzq")) {
            MzQuantMLFile quantDataFile = new MzQuantMLFile(file);
            model.addQuantDataFile(quantDataFile);
        } else if (extension.equalsIgnoreCase("mzid")) {
            RawDataFile parent = null;
            if (model.getRawData().size() > 0) {
                // TODO: We possibly should poll the user here for an mzML file.
                Object[] selectionValues = new Object[model.getRawData().size() + 1];
                int i = 0;
                selectionValues[i++] = "None";
                for (RawDataFile rawDataFile : model.getRawData()) {
                    selectionValues[i++] = rawDataFile.getFileName();
                }

                String s = (String) JOptionPane.showInputDialog(
                        null,
                        "Please select the spectrum file",
                        "Parent Spectrum File",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        selectionValues,
                        "None");

                if (s != null && !s.equals("None")) {
                    parent = model.getRawDataFile(s);
                }
            }

            MzIdentMLFile identDataFile = new MzIdentMLFile(file, parent);
            identDataFile.setCleanable(true);
            model.addIdentDataFile(identDataFile);
        } else if (extension.equalsIgnoreCase("mzml")) {
            MzMLFile rawDataFile = new MzMLFile(file, true);
            rawDataFile.setSelectedUsingFolderMode(folderMode);
            data.addRawDataFile(rawDataFile);

            rawFileDetected = true;

            AnalyseDynamicTab.getInstance().getAnalyseStatusPanel()
                    .setRawDataProcessing();
            ((RawDataAndMultiplexingStep) AnalyseDynamicTab.RAW_DATA_AND_MULTIPLEXING_STEP)
                    .refreshFromData();
        } else if (extension.equalsIgnoreCase("mgf")) {
            MascotGenericFormatFile rawDataFile = new MascotGenericFormatFile(file, true);
            rawDataFile.setSelectedUsingFolderMode(folderMode);
            data.addRawDataFile(rawDataFile);

            rawFileDetected = true;

            AnalyseDynamicTab.getInstance().getAnalyseStatusPanel()
                    .setRawDataProcessing();
            ((RawDataAndMultiplexingStep) AnalyseDynamicTab.RAW_DATA_AND_MULTIPLEXING_STEP)
                    .refreshFromData();
        } else {
            System.out.println("Unknown File type");
        }

        return rawFileDetected;
    }

    private static boolean rawFileAlreadyLoaded(File file) {
        for (int i = 0; i < data.getRawDataCount(); i++) {
            RawDataFile rawData = data.getRawDataFile(i);
            if (rawData.getFileName().equals(file.getName())) {
                return true;
            }
        }

        return false;
    }

    private class FileExtensionFilter implements FileFilter {

        private final String[] extensions;
        private final String[] lowerCaseExtensions;

        public FileExtensionFilter(String... extensions) {
            if (extensions == null || extensions.length == 0) {
                throw new IllegalArgumentException(
                        "Extensions must be non-null and not empty");
            }

            this.extensions = new String[extensions.length];
            this.lowerCaseExtensions = new String[extensions.length];
            for (int i = 0; i < extensions.length; i++) {
                if (extensions[i] == null || extensions[i].length() == 0) {
                    throw new IllegalArgumentException(
                            "Each extension must be non-null and not empty");
                }

                this.extensions[i] = extensions[i];
                lowerCaseExtensions[i] = extensions[i].toLowerCase(Locale.ENGLISH);
            }
        }

        @Override
        public boolean accept(File f) {
            if (f != null) {
                if (f.isDirectory()) {
                    return false;
                }

                String fileName = f.getName();
                int i = fileName.lastIndexOf('.');
                if (i > 0 && i < fileName.length() - 1) {
                    String desiredExtension = fileName.substring(i + 1).
                            toLowerCase(Locale.ENGLISH);
                    for (String extension : lowerCaseExtensions) {
                        if (desiredExtension.equals(extension)) {
                            return true;
                        }
                    }
                }
            }

            return false;
        }
    }
}
