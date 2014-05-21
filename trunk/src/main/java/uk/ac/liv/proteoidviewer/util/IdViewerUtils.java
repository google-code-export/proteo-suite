package uk.ac.liv.proteoidviewer.util;

public class IdViewerUtils {

	public static double roundThreeDecimals(double d) {
		double multipicationFactor = Math.pow(10, 3);
		return Math.round(d * multipicationFactor) / multipicationFactor;
	}

	public static double roundTwoDecimals(double d) {
		double multipicationFactor = Math.pow(10, 2);
		
		return Math.round(d * multipicationFactor) / multipicationFactor;
	}
}
