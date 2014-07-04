package uk.ac.liv.core;

/**
 *
 * @author SPerkins
 */
public class ITRAQParameterSettingsException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ITRAQParameterSettingsException() {
        super("Please specify the parameter settings for each raw file in Project->Set Quantitation Parameters->iTRAQ.");
    }
}
