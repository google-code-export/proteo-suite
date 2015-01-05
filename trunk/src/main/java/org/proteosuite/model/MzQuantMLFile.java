package org.proteosuite.model;

import java.io.File;
import org.proteosuite.ProteoSuiteException;
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
        return unmarshaller != null;
    }

    @Override
    public void initiateLoading() {

        final BackgroundTask<QuantDataFile> task = new BackgroundTask(this, "Load Quantitation Data");
        task.setInvisibility(true);

        task.addAsynchronousProcessingAction(new ProteoSuiteAction<ProteoSuiteActionResult, QuantDataFile>() {
            @Override
            public ProteoSuiteActionResult<MzQuantMLUnmarshaller> act(QuantDataFile argument) {
                try {
                    MzQuantMLUnmarshaller unm = new MzQuantMLUnmarshaller(file);    
                    return new ProteoSuiteActionResult<>(unm);
                }
                catch (Exception e) {
                    return new ProteoSuiteActionResult<>(new ProteoSuiteException("Error creating mzQuantMLUnmarshaller.", e));
                }                
            }
        });

        task.addCompletionAction(new ProteoSuiteAction<ProteoSuiteActionResult, QuantDataFile>() {
            @Override
            public ProteoSuiteActionResult act(QuantDataFile argument) {
                unmarshaller = task.getResultOfClass(MzQuantMLUnmarshaller.class);
                if (unmarshaller == null) {
                    return new ProteoSuiteActionResult(new ProteoSuiteException("mzQuantML file not read correctly from task results."));
                }

                return ProteoSuiteActionResult.emptyResult();
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
