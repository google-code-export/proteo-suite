package org.proteosuite.gui.listener;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.proteosuite.model.AnalyseData;

public class TableButtonToggleListener implements ListSelectionListener {

    private final JTable table;
    private final JButton[] toggleButtons;

    public TableButtonToggleListener(JTable table, JButton... toggleButtons) {
        this.table = table;
        this.toggleButtons = toggleButtons;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (AnalyseData.getInstance().getGenomeAnnotationMode()) {
            return;
        }
        
        if (e.getValueIsAdjusting()) {
            return;
        }

        DefaultListSelectionModel model = (DefaultListSelectionModel) e
                .getSource();

        boolean state = true;

        if (model.getAnchorSelectionIndex() == -1) {
            state = false;
        }

        if (table.getRowCount() == 0) {
            state = false;
        }

        for (JButton button : toggleButtons) {
            button.setEnabled(state);
        }
    }
}
