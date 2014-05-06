package uk.ac.liv.mzqlib.idmapper;

import java.util.List;
import java.util.Map;

import uk.ac.liv.jmzqml.model.mzqml.SearchDatabase;
import uk.ac.liv.mzqlib.idmapper.data.SIIData;

/**
 *
 * @author Da Qi
 * @institute University of Liverpool
 * @time 05-Mar-2014 11:25:22
 */
public interface MzqProcessor {

    public Map<String, List<SIIData>> getFeatureToSIIsMap();

    public Map<String, List<String>> getCombPepModStringToProtAccessionsMap();

    public Map<String, List<SIIData>> getCombinedPepModStringToSIIsMap();
    
    public SearchDatabase getSearchDatabase();

}