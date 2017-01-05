package xm.ik.analyzer;

import xm.ik.collection.Lexeme;
import xm.ik.config.Config;
import xm.ik.config.DefaultConfig;
import xm.ik.dic.Dictionary;
import xm.ik.segment.*;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xuming
 */
public class Analyzer {
    private Reader input;
    private Config config;
    private AnalyzerContext context;
    private List<ISegmenter> segmenters;
    private AmbiguitySegmenter ambiguitySegmenter;

    public Analyzer(Reader input, Config config) {
        this.input = input;
        this.config = config;
        init();
    }

    public Analyzer(Reader input, boolean useSmart) {
        this.input = input;
        this.config = DefaultConfig.getInstance();
        this.config.setUseSmart(useSmart);
        init();
    }

    public Analyzer(Reader input) {
        this(input, true);
    }

    private void init() {
        Dictionary.init(config);
        context = new AnalyzerContext(config);
        segmenters = loadSegmenters();
        ambiguitySegmenter = new AmbiguitySegmenter();
    }

    private List<ISegmenter> loadSegmenters() {
        List<ISegmenter> segmenters = new ArrayList<>();
        segmenters.add(new LetterSegmenter());
        segmenters.add(new CJKSegmenter());
        segmenters.add(new QuantifierSegmenter());
        return segmenters;
    }

    public synchronized Lexeme next() {
        Lexeme lexeme;
        while ((lexeme = context.getNextLexeme()) == null) {
            int available = 0;
            try {
                available = context.fillBuffer(this.input);
            } catch (IOException e) {
                System.err.println("io exception:" + this.input + ", " + e);
            }
            if (available <= 0) {
                context.reset();
                return null;
            } else {
                // init cursor
                context.initCursor();
                while (context.moveCursor()) {
                    for (ISegmenter segmenter : segmenters)
                        segmenter.analyze(context);
                    // buffer is full, need new input new char
                    if (context.needRefillBuffer()) break;
                }
                // reset segmenter list
                segmenters.forEach(ISegmenter::reset);
            }
            // ambiguity word
            ambiguitySegmenter.process(context, config.useSmart());
            // output result to list, deal with CJK chars
            context.outputRawSegmentation();
            context.markBufferOffset();
        }
        return lexeme;
    }

    public synchronized void reset(Reader input) {
        this.input = input;
        context.reset();
        segmenters.forEach(ISegmenter::reset);
    }
}
