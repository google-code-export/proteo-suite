/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.liv.proteosuite;

/**
 *
 * @author faviel
 */
import java.awt.*;
import javax.swing.*;
import java.beans.*;
import java.util.Random;

public class ProgMonitor extends JPanel implements PropertyChangeListener {

    private ProgressMonitor progMonitor;    
    private Task task;

    class Task extends SwingWorker<Void, Void> {
        @Override
        public Void doInBackground()
        {
            Random random = new Random();
            int progress = 0;
            setProgress(0);
            try {
                Thread.sleep(500);
                while (progress < 100 && !isCancelled())
                {
                    //Sleep for up to one second.
                    Thread.sleep(random.nextInt(500));
                    //Make random progress.
                    progress += random.nextInt(10);
                    setProgress(Math.min(progress, 100));
                }
            } catch (InterruptedException ignore) {}
            return null; 
        }

        @Override
        public void done() {
            Toolkit.getDefaultToolkit().beep();
            progMonitor.setProgress(0);
        }
    }

    public ProgMonitor(String title) {
        progMonitor = new ProgressMonitor(ProgMonitor.this,
                                  title,
                                  "", 0, 100);
        progMonitor.setProgress(0);
        task = new Task();
        task.addPropertyChangeListener(this);
        task.execute();
    }

    /**
     * Invoked when task's progress property changes.
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName() )
        {
            int progress = (Integer) evt.getNewValue();
            progMonitor.setProgress(progress);
            String message = String.format("%d%%.\n", progress);
            progMonitor.setNote(message);
            if (progMonitor.isCanceled() || task.isDone())
            {
                Toolkit.getDefaultToolkit().beep();
                if (progMonitor.isCanceled())
                {
                    task.cancel(true);
                }
                else
                {
                    progMonitor.setNote("Completed.");
                }
            }
        }

    }
}
