package org.proteosuite.gui.renderer;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import org.proteosuite.model.Modification;
import org.proteosuite.model.Peptide;

public class PeptideCellRenderer extends DefaultTableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		JLabel tableCellRendererComponent = (JLabel) super
				.getTableCellRendererComponent(table, value, isSelected,
						hasFocus, row, column);

		if (value instanceof Peptide)
			return getRenderedPeptide(tableCellRendererComponent, (Peptide) value);
		
		if (value instanceof Boolean)
			return getRenderedBoolean(tableCellRendererComponent, (boolean) value);
		
		
		return tableCellRendererComponent;
	}
	
	private Component getRenderedBoolean(JLabel tableCellRendererComponent,
			boolean value) {

		// Turn true and false to tick/cross
		// If you get a compile error here, this is a UTF8 source file!

		if (value)
			setText("✔");
		else
			setText("✘");

		return tableCellRendererComponent;
	}

	private JLabel getRenderedPeptide(JLabel tableCellRendererComponent, Peptide peptide)
	{

		// Firstly identify mod locations

		String html = "<html>";
		boolean[] modLocations = new boolean[peptide.sequence.length()];

		String modList = "";
		int mods = 0;
		for (Modification m : peptide.modifications) {
			if (m == null)
				continue;
			int location = m.location == 0 ? 0 : m.location - 1;
			modLocations[location] = true;
			if (mods < peptide.modifications.length)
				modList += m.name + ", ";
			mods++;
		}

		for (int i = 0; i < peptide.sequence.length(); i++) {
			char c = peptide.sequence.charAt(i);
			if (modLocations[i])
				html += "<span style=\"color: red;\"><b><u>" + c
						+ "</u></b></span>";
			else
				html += c;
		}
		setText(html + "</html>");

		tableCellRendererComponent.setToolTipText(modList);

		return tableCellRendererComponent;
		
	}
}
