package org.proteosuite.utils.compression;

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
     * @return CompressionResult Result object containing compression statistics.
     * @throws IOException Thrown if problem writing the compressed file.
     */
    CompressionResult compress(File inputFile, String outputDirectory) throws IOException;

    /**
     * Decompresses the MzML file.
     * @param inputFile MzML input file.
     * @param outputDirectory Output directory.
     * @return DecompressionResult Result object containing decompression statistics.
     * @throws java.io.IOException Thrown if problem writing the decompressed file.
     */
    DecompressionResult decompress(File inputFile, String outputDirectory) throws IOException;
}
