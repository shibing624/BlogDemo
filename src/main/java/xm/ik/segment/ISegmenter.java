package xm.ik.segment;

/**
 * segmenter interface
 *
 * @author xuming
 */
public interface ISegmenter {
    /**
     * analysis the lexeme object
     * @param context
     */
    void analyze(AnalyzerContext context);

    /**
     * reset analyzer status(init:-1)
     */
    void reset();
}
