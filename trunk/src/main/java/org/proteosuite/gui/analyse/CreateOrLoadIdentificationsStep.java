package org.proteosuite.gui.analyse;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.proteosuite.gui.IdentParamsView;
import org.proteosuite.gui.listener.ContinueButtonListener;
import org.proteosuite.gui.listener.CreateIdentificationsForSelectedListener;
import org.proteosuite.gui.listener.LoadIdentificationsForSelectedListener;
import org.proteosuite.gui.listener.PreviousButtonListener;
import org.proteosuite.gui.listener.ResetIdentificationsForSelectedListener;
import org.proteosuite.gui.listener.TableButtonToggleListener;
import org.proteosuite.gui.tables.CreateOrLoadIdentificationsTable;
import org.proteosuite.model.AnalyseData;
import org.proteosuite.model.RawDataFile;

/**
 *
 * @author SPerkins
 */
public class CreateOrLoadIdentificationsStep extends JPanel {

    private static final long serialVersionUID = 1L;
    private final CreateOrLoadIdentificationsTable identificationsTable = new CreateOrLoadIdentificationsTable();
    private IdentParamsView identParamsView = null;
    private final static AnalyseData data = AnalyseData.getInstance();

    public CreateOrLoadIdentificationsStep() {
        super(new BorderLayout());

        JLabel stepTitle = new JLabel("Create or load your identifications:");
        stepTitle.setFont(new Font(stepTitle.getFont().getFontName(), stepTitle
                .getFont().getStyle(), 72));

        JPanel buttonsPanel = new JPanel(new GridLayout(2, 3));

        JButton loadIdentifications = new JButton(
                "Load identifications for selected...");
        JButton createIdentifications = new JButton(
                "Create identifications for selected...");
        JButton resetIdentifications = new JButton(
                "Reset status for selected...");

        loadIdentifications.setEnabled(false);
        createIdentifications.setEnabled(false);
        resetIdentifications.setEnabled(false);

        loadIdentifications
                .addActionListener(new LoadIdentificationsForSelectedListener(
                                this));
        createIdentifications
                .addActionListener(new CreateIdentificationsForSelectedListener(
                                this));
        resetIdentifications
                .addActionListener(new ResetIdentificationsForSelectedListener(
                                this));
        identificationsTable.getSelectionModel().addListSelectionListener(
                new TableButtonToggleListener(identificationsTable,
                        loadIdentifications, createIdentifications,
                        resetIdentifications));

        JButton continueButton = new JButton("Continue");
        JButton previousButton = new JButton("Previous");

        previousButton.addActionListener(new PreviousButtonListener(this));
        continueButton.addActionListener(new ContinueButtonListener(this));

        buttonsPanel.add(loadIdentifications);
        buttonsPanel.add(createIdentifications);
        buttonsPanel.add(resetIdentifications);

        buttonsPanel.add(previousButton);
        buttonsPanel.add(Box.createGlue());
        buttonsPanel.add(continueButton);

        add(stepTitle, BorderLayout.PAGE_START);
        add(new JScrollPane(identificationsTable), BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.PAGE_END);
    }

    public CreateOrLoadIdentificationsTable getIdentificationsTable() {
        return identificationsTable;
    }

    public IdentParamsView getIdentParamsView() {
        if (identParamsView == null) {
            Component parent = this.getParent();
            while (!(parent instanceof JFrame)) {
                parent = parent.getParent();
            }
            
            identParamsView = new IdentParamsView(
                    (Window) parent, data.getGenomeAnnotationMode());
        }
        return identParamsView;
    }

    public synchronized void refreshFromData() {
        identificationsTable.clear();        
        for (int i = 0; i < data.getRawDataCount(); i++) {
            RawDataFile dataFile = data.getRawDataFile(i);
            identificationsTable.addRawFileRow(dataFile);
        }
    }
}
