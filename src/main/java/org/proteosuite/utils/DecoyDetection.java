package org.proteosuite.utils;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;

import org.proteosuite.ProteoSuite;

import uk.ac.ebi.jmzidml.model.mzidml.PeptideEvidence;
import uk.ac.ebi.jmzidml.model.mzidml.PeptideHypothesis;
import uk.ac.ebi.jmzidml.model.mzidml.ProteinDetectionHypothesis;
import uk.ac.ebi.jmzidml.xml.io.MzIdentMLUnmarshaller;

public class DecoyDetection {

	/**
	 * Checks if a protein detection hypothesis is decoy 
	 * @param unmarshaller - unmarshaller element
	 * @param proteinDetectionHypothesis - proteinDetectionHypothesis element
	 * @return true|false
	 */
	public static boolean checkIfProteinDetectionHypothesisIsDecoy(MzIdentMLUnmarshaller unmarshaller,
			ProteinDetectionHypothesis proteinDetectionHypothesis) {
		
		List<PeptideHypothesis> PeptideHyposthesisList = proteinDetectionHypothesis
				.getPeptideHypothesis();
		
		for (int i = 0; i < PeptideHyposthesisList.size(); i++) {
			try {
				PeptideHypothesis peptideHypothesis = PeptideHyposthesisList
						.get(i);
				String peptidRef = peptideHypothesis.getPeptideEvidenceRef();
				PeptideEvidence peptiedEvidence = unmarshaller.unmarshal(
						PeptideEvidence.class, peptidRef);
				if (peptiedEvidence.isIsDecoy()) {
					return true;
				}
			} catch (JAXBException ex) {
				Logger.getLogger(ProteoSuite.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
		
		return false;
	}
}
