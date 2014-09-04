package org.proteosuite.model;

public class Peptide {
	
	public final String sequence;
	public final Modification[] modifications;

	public Peptide (String sequence, Modification[] modifications)
	{
		this.sequence = sequence;
		this.modifications = modifications;
	}
}