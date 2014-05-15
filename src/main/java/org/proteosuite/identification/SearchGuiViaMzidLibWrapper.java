

package org.proteosuite.identification;

import java.util.Map;
import java.util.Set;
import org.proteosuite.model.MascotGenericFormatFile;

/**
 *
 * @author SPerkins
 */
public class SearchGuiViaMzidLibWrapper implements SearchEngine {
    
    public SearchGuiViaMzidLibWrapper(Set<MascotGenericFormatFile> inputSpectra, String databasePath, Map<String, String> searchParamters, boolean doProteoGrouper, boolean doEmPAI) {
    
    }
    
    public SearchGuiViaMzidLibWrapper(Set<MascotGenericFormatFile> inputSpectra, String[] geneModel, Map<String, String> otherModels, Map<String, String> searchParameters) {
    
    }
    
    public void compute() {}
}
