package org.proteosuite.utils;

import java.io.File;
import java.io.IOException;

/**
 * Interface for MzML compressors.
 * @author SPerkins
 */
public interface MzMLCompressor {

    /** Gets the name of the MzML compressor.
     * @return Compressor name.
     */
    String getCompressorName();
    
    /**
     * Gets the file suffix for the compressor.
     * @return Compressor file suffix.
     */
    String getCompressorSpecificSuffix();

    /** Compresses the MzML file.
     * @param inputFile MzML input file.
     * @param outputDirectory Output directory.
     * @throws IOException Thrown if problem writing the compressed file.
     */
    void compress(File inputFile, String outputDirectory) throws IOException;

    /**
     * Decompresses the MzML file.
     * @param inputFile MzML input file.
     * @param outputDirectory Output directory.
     */
    void decompress(File inputFile, String outputDirectory);
}
