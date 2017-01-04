package xm.ik.segment;

/**
 * segmenter interface
 * @author xuming
 */
public interface ISegmenter {
    void analyze(AnalyzerContext context);
    void reset();
}
