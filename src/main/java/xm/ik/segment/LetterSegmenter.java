package xm.ik.segment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xm.ik.util.CharacterUtil;

import java.util.Arrays;

/**
 * english letter and arabic num segmenter
 *
 * @author xuming
 */
public class LetterSegmenter implements ISegmenter {
    private static final Logger log = LoggerFactory.getLogger(LetterSegmenter.class);

    public static final String SEGMENTER_NAME = "LETTER_SEGMENTER";
    private static final char[] LETTER_LINK_SYMBOL = new char[]{'#', '&', '+', '-', '.', '@', '_'};
    private static final char[] NUM_LINK_SYMBOL = new char[]{',', '.'};
    private int start;
    private int end;
    private int englishStart;
    private int englishEnd;
    private int arabicStart;
    private int arabicEnd;

    public LetterSegmenter() {
        Arrays.sort(LETTER_LINK_SYMBOL);
        Arrays.sort(NUM_LINK_SYMBOL);
        this.start = -1;
        this.end = -1;
        this.englishStart = -1;
        this.englishEnd = -1;
        this.arabicStart = -1;
        this.arabicEnd = -1;
    }

    @Override
    public void analyze(AnalyzerContext context) {
        boolean isLock = false;
        // english letter
        isLock = processEnglishLetter(context) || isLock;
        // arabic letter
//        isLock =
        log.error("dd");
    }

    private boolean processEnglishLetter(AnalyzerContext context){
        boolean result = false;
        if(englishStart == -1){
            // init
            if(CharacterUtil.CHAR_ENGLISH == context.getCurrentCharType()){
                // mark start cursor
                englishStart = context.getCursor();
                englishEnd = englishStart;
            }
        }else{
            // dealing
            if(CharacterUtil.CHAR_ENGLISH == context.getCurrentCharType()){
                // mark end cursor
                englishEnd = context.getCursor();
            }
//            Lexeme newLexeme = new
        }
        return false;
    }
    @Override
    public void reset() {
        this.start = -1;
        this.end = -1;
        this.englishStart = -1;
        this.englishEnd = -1;
        this.arabicStart = -1;
        this.arabicEnd = -1;
    }
}
