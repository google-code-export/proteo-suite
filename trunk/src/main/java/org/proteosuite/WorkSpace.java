package org.proteosuite;

/**
 * Work space management class
 * @author Andrew Collins
 *
 */
public class WorkSpace {
	private static WorkSpace instance = null;
	
	private String sWorkspace = "C:/temp";
	
	public WorkSpace()
	{
		
	}
	
	public static WorkSpace getInstance()
	{
		if (instance == null)
			instance = new WorkSpace();
		
		return instance;
	}

	public String getWorkSpace() {
		return sWorkspace;
	}

	public void setWorkSpace(String string) {
		sWorkspace = string;		
	}
	
}
