package org.proteosuite.jopenms;

/**
 *
 * @author Da Qi
 * @institute University of Liverpool
 * @time 19-Feb-2014 23:05:54
 */
public enum OpenMSExecutable {

    FeatureFinderCentroided("FeatureFinderCentroided"),
    MapAlignerPoseClustering("MapAlignerPoseClustering"),
    FeatureLinkerUnlabeledQT("FeatureLinkerUnlabeledQT"),
    TextExporter("TextExporter"),
    //
    AccurateMassSearch("AccurateMassSearch"),
    AdditiveSeries("AdditiveSeries"),
    BaselineFilter("BaselineFilter"),
    CompNovo("CompNovo"),
    CompNovoCID("CompNovoCID"),
    ConsensusID("ConsensusID"),
    ConsensusMapNormalizer("ConsensusMapNormalizer"),
    ConvertTraMLToTSV("ConvertTraMLToTSV"),
    ConvertTSVToTraML("ConvertTSVToTraML"),
    CVInspector("CVInspector"),
    DBExporter("DBExporter"),
    DBImporter("DBImporter"),
    Decharger("Decharger"),
    DecoyDatabase("DecoyDatabase"),
    DeMeanderize("DeMeanderize"),
    Digestor("Digestor"),
    DigestorMotif("DigestorMotif"),
    DTAExtractor("DTAExtractor"),
    EICExtractor("EICExtractor"),
    ERPairFinder("ERPairFinder"),
    ExecutePipeline("ExecutePipeline"),
    FalseDiscoveryRate("FalseDiscoveryRate"),
    FeatureFinderIsotopeWavelet("FeatureFinderIsotopeWavelet"),
    FeatureFinderMetabo("FeatureFinderMetabo"),
    FeatureFinderMRM("FeatureFinderMRM"),
    FeatureFinderRaw("FeatureFinderRaw"),
    FeatureFinderSuperHirn("FeatureFinderSuperHirn"),
    FeatureLinkerLabeled("FeatureLinkerLabeled"),
    FeatureLinkerUnlabeled("FeatureLinkerUnlabeled"),
    FFEval("FFEval"),
    FileConverter("FileConverter"),
    FileFilter("FileFilter"),
    FileInfo("FileInfo"),
    FileMerger("FileMerger"),
    FuzzyDiff("FuzzyDiff");
    private final String name;

    OpenMSExecutable(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
