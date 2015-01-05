/*
 * ProteoSuite is releases under the Apache 2 license.
 * This means that you are free to modify, use and distribute the software in all legislations provided you give credit to our project.
 */
package org.proteosuite.actions;

import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.proteosuite.ProteoSuiteException;
import org.proteosuite.model.ProteoSuiteActionResult;
import org.proteosuite.model.ProteoSuiteActionSubject;

/**
 *
 * @author SPerkins
 */
public class LatchedAction implements ProteoSuiteAction<ProteoSuiteActionResult, ProteoSuiteActionSubject> {
    private CountDownLatch latch = null;
    protected LatchedAction(CountDownLatch latch) {
        this.latch = latch;
    }   

    @Override
    public ProteoSuiteActionResult act(ProteoSuiteActionSubject argument) {
        try {
            latch.await();
            System.out.println("Latched action complete.");
        }
        catch (InterruptedException ex) {
            Logger.getLogger(LatchedAction.class.getName()).log(Level.SEVERE, null, ex);
            return new ProteoSuiteActionResult(new ProteoSuiteException("Problem waiting for latch to release in LatchedAction.", ex));
        }
        
        return ProteoSuiteActionResult.emptyResult();
    }
}
