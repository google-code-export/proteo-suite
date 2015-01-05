/*
 * ProteoSuite is releases under the Apache 2 license.
 * This means that you are free to modify, use and distribute the software in all legislations provided you give credit to our project.
 */
package org.proteosuite.actions;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import org.proteosuite.model.ProteoSuiteActionSubject;

/**
 *
 * @author SPerkins
 */
public class Actions {
    private static Map<Class, EmptyAction> emptyActions = new HashMap<>();    
    private static Map<CountDownLatch, LatchedAction> latchedActions = new HashMap<>();
    public static <T extends ProteoSuiteActionSubject> EmptyAction emptyAction(T subject) {
        if (emptyActions.containsKey(subject.getClass())) {
            return emptyActions.get(subject.getClass());
        } else {
            EmptyAction<T> emptyAction = new EmptyAction<>();
            emptyActions.put(subject.getClass(), emptyAction);
            return emptyAction;
        }       
    }
    
    public static LatchedAction latchedAction(CountDownLatch latch) {
        if (!latchedActions.containsKey(latch)) {
            latchedActions.put(latch, new LatchedAction(latch));
        }
        
        return latchedActions.get(latch);        
    }
}