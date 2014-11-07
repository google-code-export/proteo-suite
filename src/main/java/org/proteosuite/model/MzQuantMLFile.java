package org.proteosuite.model;

import java.io.File;
import org.proteosuite.actions.ProteoSuiteAction;
import org.proteosuite.actions.QuantFilePostLoadAction;
import uk.ac.liv.jmzqml.xml.io.MzQuantMLUnmarshaller;

/**
 *
 * @author SPerkins
 */
public class MzQuantMLFile extends QuantDataFile {
    private MzQuantMLUnmarshaller unmarshaller = null;
    public MzQuantMLFile(File file) {
        super(file);
    }
    
    @Override
    public boolean isLoaded() {
        if (unmarshaller != null) {
            return true;
        }
        
        return false;
    }
    
    @Override
    public void initiateLoading() {       
        
        final BackgroundTask task = new BackgroundTask(this, "Load Quantitation Data");
        task.setInvisibility(true);
        
        task.addAsynchronousProcessingAction(new ProteoSuiteAction<MzQuantMLUnmarshaller, BackgroundTaskSubject>() {
            @Override
            public MzQuantMLUnmarshaller act(BackgroundTaskSubject argument) {
                return new MzQuantMLUnmarshaller(file);
            }
        });
        
        task.addCompletionAction(new ProteoSuiteAction<Object, BackgroundTaskSubject>() {
            @Override
            public Void act(BackgroundTaskSubject argument) {
                unmarshaller = task.getResultOfClass(MzQuantMLUnmarshaller.class);
                if (unmarshaller == null) {
                    throw new RuntimeException("mzQuantML file not read in correctly.");
                }
                
                return null;
            }
        });
        
        task.addCompletionAction(new QuantFilePostLoadAction());
        
        BackgroundTaskManager.getInstance().submit(task);       
    }

    public MzQuantMLUnmarshaller getUnmarshaller() {
        return unmarshaller;
    }

    @Override
    public String getSubjectName() {
        return this.getFileName();
    }
}