package org.proteosuite.gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.util.Locale;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.proteosuite.WorkSpace;
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
    private boolean folderMode = false;

    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem menuItem = (JMenuItem) e.getSource();
        folderMode = menuItem.getText().equals("Import MGFs From Folder");
        JFileChooser openFile = new JFileChooser(WorkSpace.sPreviousLocation);
        boolean choicesValid = false;
        File[] files = null;

        while (!choicesValid) {
            if (folderMode) {
                openFile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                openFile.setDialogTitle("Select Folder To Import MGFs From");
            } else {
                openFile.setAcceptAllFileFilterUsed(false);
                openFile.setMultiSelectionEnabled(true);
                openFile.setFileFilter(new FileNameExtensionFilter("MGF, mzML, mzIdentML, mzQuantML",
                        "mgf", "mzml", "mzq", "mzid", "mzidentml", "mzq", "mzquantml"));
            }

            int result = openFile.showOpenDialog(null);

            if (result == JFileChooser.CANCEL_OPTION) {
                return;
            }

            files = openFile.getSelectedFiles();

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
                                + "- Multiple directories may not be selected together.le.\n"
                                + "You may wish to restructure your data locations before trying again.",
                                "Invalid Selection", JOptionPane.PLAIN_MESSAGE,
                                JOptionPane.ERROR_MESSAGE);
            } else {
                choicesValid = true;
            }
        }

        for (File file : files) {
            if (!folderMode && !file.getParent().equals(WorkSpace.sPreviousLocation)) {
                WorkSpace.sPreviousLocation = file.getParent();
            } else if (folderMode && !file.toString().equals(WorkSpace.sPreviousLocation)) {
                WorkSpace.sPreviousLocation = file.getPath();
            }

            if (folderMode) {
                File[] mgfFiles = file.listFiles(new FileExtensionFilter("MGF"));
                AnalyseDynamicTab.getInstance().getAnalyseStatusPanel()
                        .setRawDataProcessing();
                for (File mgfFile : mgfFiles) {
                    RawDataFile rawDataFile = new MascotGenericFormatFile(mgfFile);
                    rawDataFile.setSelectedUsingFolderMode(true);
                    data.addRawDataFile(rawDataFile);
                    ((RawDataAndMultiplexingStep) AnalyseDynamicTab.RAW_DATA_AND_MULTIPLEXING_STEP)
                            .refreshFromData();
                }

                return;
            }

            String name = file.getName();
            String[] tmp = name.split(Pattern.quote("."));
            String extension = tmp[tmp.length - 1];

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
                data.addRawDataFile(rawDataFile);

                AnalyseDynamicTab.getInstance().getAnalyseStatusPanel()
                        .setRawDataProcessing();
                ((RawDataAndMultiplexingStep) AnalyseDynamicTab.RAW_DATA_AND_MULTIPLEXING_STEP)
                        .refreshFromData();
            } else if (extension.equalsIgnoreCase("mgf")) {
                data.addRawDataFile(new MascotGenericFormatFile(file, true));
                AnalyseDynamicTab.getInstance().getAnalyseStatusPanel()
                        .setRawDataProcessing();
                ((RawDataAndMultiplexingStep) AnalyseDynamicTab.RAW_DATA_AND_MULTIPLEXING_STEP)
                        .refreshFromData();
            } else {
                System.out.println("Unknown File type");
            }
        }
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
