package org.proteosuite.gui;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.proteosuite.gui.listener.CloseProjectListener;
import org.proteosuite.gui.listener.ExportProjectListener;
import org.proteosuite.gui.listener.ImportProjectListener;
import org.proteosuite.gui.listener.NewProjectListener;
import org.proteosuite.gui.listener.OpenProjectListener;
import org.proteosuite.gui.listener.SaveAsProjectListener;
import org.proteosuite.gui.listener.SaveProjectListener;

/**
 *
 * @author SPerkins
 */
public class ProjectChoices extends JPanel {
    private static BoxLayout layout;
    
    public ProjectChoices() {
        layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);        
        
        JLabel newProject = new JLabel("New Project...");
        newProject.addMouseListener(new NewProjectListener());
        add(newProject);
        JLabel openProject = new JLabel("Open Project...");
        openProject.addMouseListener(new OpenProjectListener());
        add(openProject);
        JLabel saveProject = new JLabel("Save Project");
        saveProject.addMouseListener(new SaveProjectListener());
        add(saveProject);
        JLabel saveAsProject = new JLabel("Save Project...");
        saveAsProject.addMouseListener(new SaveAsProjectListener());
        add(saveAsProject);
        JLabel closeProject = new JLabel("Close Project");
        closeProject.addMouseListener(new CloseProjectListener());        
        add(closeProject);
        JLabel importProject = new JLabel("Import Project...");
        importProject.addMouseListener(new ImportProjectListener());
        add(importProject);
        JLabel exportProject = new JLabel("Export Project...");
        exportProject.addMouseListener(new ExportProjectListener());
        add(exportProject);
    }
}