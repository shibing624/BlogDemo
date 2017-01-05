package xm.ik.segment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xm.ik.collection.Lexeme;
import xm.ik.util.CharacterUtil;

import java.util.Arrays;

/**
 * english letter and arabic num segmenter
 *
 * @author xuming
 */
public class LetterSegmenter implements ISegmenter {
    private static final Logger LOG = LoggerFactory.getLogger(LetterSegmenter.class);

    public static final String SEGMENTER_NAME = "LETTER_SEGMENTER";
    private static final char[] LETTER_LINK_SYMBOL = new char[]{'#', '&', '+', '-', '.',
            '@', '_'};
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
        isLock = processArabicLetter(context) || isLock;
        // english and arabic mixed, attention last process it
        isLock = processEnglishNumMixLetter(context) || isLock;

        // lock buffer
        if (isLock)
            context.lockBuffer(SEGMENTER_NAME);
        else
            context.unlockBuffer(SEGMENTER_NAME);
    }

    /**
     * pure english
     *
     * @param context
     * @return
     */
    private boolean processEnglishLetter(AnalyzerContext context) {
        boolean isLock;
        if (englishStart == -1) {
            // init
            if (CharacterUtil.CHAR_ENGLISH == context.getCurrentCharType()) {
                // mark start cursor
                englishStart = context.getCursor();
                englishEnd = englishStart;
            }
        } else {
            // dealing
            if (CharacterUtil.CHAR_ENGLISH == context.getCurrentCharType()) {
                // mark end cursor
                englishEnd = context.getCursor();
            } else {
                Lexeme newLexeme = new Lexeme(context.getBuffOffset(), englishStart,
                        englishEnd - englishStart + 1, Lexeme.TYPE_ENGLISH);
                context.addLexeme(newLexeme);
                englishStart = -1;
                englishEnd = -1;
            }
        }
        // buffer empty, output to lexeme
        if (context.isBufferConsumed()) {
            if (englishStart != -1 && englishEnd != -1) {
                Lexeme newLexeme = new Lexeme(context.getBuffOffset(), englishStart,
                        englishEnd - englishStart + 1, Lexeme.TYPE_ENGLISH);
                context.addLexeme(newLexeme);
                englishStart = -1;
                englishEnd = -1;
            }
        }
        // lock buff
        if (englishStart == -1 && englishEnd == -1) {
            isLock = false;
        } else {
            isLock = true;
        }
        return isLock;
    }

    /**
     * pure arabic letter
     *
     * @param context analyzer context
     * @return is buffer locked
     */
    private boolean processArabicLetter(AnalyzerContext context) {
        boolean isLock;
        if (arabicStart == -1) {
            if (CharacterUtil.CHAR_ARABIC == context.getCurrentCharType()) {
                arabicStart = context.getCursor();
                arabicEnd = arabicStart;
            }
        } else {
            if (CharacterUtil.CHAR_ARABIC == context.getCurrentCharType())
                arabicEnd = context.getCursor();
            else if (CharacterUtil.CHAR_USELESS == context.getCurrentCharType()
                    && isNumLinkSymbol(context.getCurrentChar())) {
                // do nothing
            } else {
                // not arabic char, output
                Lexeme newLexeme = new Lexeme(context.getBuffOffset(), arabicStart,
                        arabicEnd - arabicStart + 1, Lexeme.TYPE_ARABIC);
                context.addLexeme(newLexeme);
                arabicStart = -1;
                arabicEnd = -1;
            }
        }

        if (context.isBufferConsumed()) {
            if (arabicStart != -1 && arabicEnd != -1) {
                Lexeme newLexeme = new Lexeme(context.getBuffOffset(), arabicStart,
                        arabicEnd - arabicStart + 1, Lexeme.TYPE_ARABIC);
                context.addLexeme(newLexeme);
                arabicStart = -1;
                arabicEnd = -1;
            }
        }
        // buffer lock
        if (arabicStart == -1 && arabicEnd == -1)
            isLock = false;
        else
            isLock = true;
        return isLock;
    }

    /**
     * english and num mixed letter
     * eg, windows2000/p2p/c3p0
     *
     * @param context
     * @return
     */
    private boolean processEnglishNumMixLetter(AnalyzerContext context) {
        boolean isLock;
        if (start == -1) {
            if (CharacterUtil.CHAR_ARABIC == context.getCurrentCharType()
                    || CharacterUtil.CHAR_ENGLISH == context.getCurrentCharType()) {
                start = context.getCursor();
                end = start;
            }
        } else {
            if (CharacterUtil.CHAR_ARABIC == context.getCurrentCharType()
                    || CharacterUtil.CHAR_ENGLISH == context.getCurrentCharType())
                end = context.getCursor();
            else if (CharacterUtil.CHAR_USELESS == context.getCurrentCharType()
                    && isLetterLinkSymbol(context.getCurrentChar())) {
                // mark end
                end = context.getCursor();
            } else {
                // not letter char, output
                Lexeme newLexeme = new Lexeme(context.getBuffOffset(), start,
                        end - start + 1, Lexeme.TYPE_LETTER);
                context.addLexeme(newLexeme);
                arabicStart = -1;
                arabicEnd = -1;
            }
        }

        if (context.isBufferConsumed()) {
            if (start != -1 && end != -1) {
                Lexeme newLexeme = new Lexeme(context.getBuffOffset(), start,
                        end - start + 1, Lexeme.TYPE_LETTER);
                context.addLexeme(newLexeme);
                arabicStart = -1;
                arabicEnd = -1;
            }
        }
        // buffer lock
        if (start == -1 && end == -1)
            isLock = false;
        else
            isLock = true;
        return isLock;
    }

    private boolean isNumLinkSymbol(char c) {
        return Arrays.binarySearch(NUM_LINK_SYMBOL, c) >= 0;
    }

    private boolean isLetterLinkSymbol(char c) {
        return Arrays.binarySearch(LETTER_LINK_SYMBOL, c) >= 0;
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
