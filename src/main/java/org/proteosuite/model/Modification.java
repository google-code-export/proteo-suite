package org.proteosuite.model;

public class Modification {
	public final int location;
	public final String[] residues;
	public final String name;

	public Modification (int location, String[] residues, String name)
	{
		this.location = location;
		this.residues = residues;
		this.name = name;
	}
	
}
