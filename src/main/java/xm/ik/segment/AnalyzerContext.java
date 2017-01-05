package xm.ik.segment;

import xm.ik.collection.Lexeme;
import xm.ik.collection.LexemePath;
import xm.ik.collection.QuickSortSet;
import xm.ik.config.Config;
import xm.ik.util.CharacterUtil;

import java.io.IOException;
import java.io.Reader;
import java.util.*;

import static xm.ik.dic.Dictionary.getSingleton;

/**
 * analyzer context
 *
 * @author xuming
 */
public class AnalyzerContext {
    private static final int BUFF_SIZE = 4096;
    private static final int BUFF_EXHAUST_CRITICAL = 100;
    private char[] segmentBuff;
    private int[] charTypes;
    // offset from the start position of segmentBuff
    private int buffOffset;
    // buffer position cursor
    private int cursor;
    // available length
    private int available;
    private Set<String> buffLocker;
    // original analyzer result
    private QuickSortSet originalLexemes;
    // location index table of Lexeme path
    private Map<Integer, LexemePath> pathMap;
    private LinkedList<Lexeme> lexemeList;
    private Config config;

    public AnalyzerContext(Config config) {
        this.config = config;
        this.segmentBuff = new char[BUFF_SIZE];
        this.charTypes = new int[BUFF_SIZE];
        this.buffLocker = new HashSet<>();
        this.originalLexemes = new QuickSortSet();
        this.pathMap = new LinkedHashMap<>();
        this.lexemeList = new LinkedList<>();
    }

    public char[] getSegmentBuff() {
        return segmentBuff;
    }

    public int getBuffOffset() {
        return buffOffset;
    }

    public int getCursor() {
        return cursor;
    }

    public char getCurrentChar() {
        return segmentBuff[cursor];
    }

    public int getCurrentCharType() {
        return charTypes[cursor];
    }

    public LinkedList<Lexeme> getLexemeList() {
        return lexemeList;
    }

    public void setLexemeList(LinkedList<Lexeme> lexemeList) {
        this.lexemeList = lexemeList;
    }

    public int fillBuffer(Reader reader) throws IOException {
        int readCount = 0;
        if (buffOffset == 0)
            readCount = reader.read(segmentBuff);
        else {
            int offset = available - cursor;
            if (offset > 0) {
                // put string to head of segmentBuff
                System.arraycopy(segmentBuff, cursor, segmentBuff, 0, offset);
                readCount = offset;
            }
            // deal with segmentBuff, start with (read - analyzed)
            readCount += reader.read(segmentBuff, offset, BUFF_SIZE - offset);
        }
        available = readCount;
        cursor = 0;
        return readCount;
    }

    /**
     * init buff cursor, deal with first char
     */
    public void initCursor() {
        cursor = 0;
        segmentBuff[cursor] = CharacterUtil.regularize(segmentBuff[cursor]);
        charTypes[cursor] = CharacterUtil.identifyCharType(segmentBuff[cursor]);
    }

    public boolean moveCursor() {
        if (cursor < available - 1) {
            cursor++;
            segmentBuff[cursor] = CharacterUtil.regularize(segmentBuff[cursor]);
            charTypes[cursor] = CharacterUtil.identifyCharType(segmentBuff[cursor]);
            return true;
        }
        return false;
    }

    public void lockBuffer(String segmentName) {
        buffLocker.add(segmentName);
    }

    public void unlockBuffer(String segmentName) {
        buffLocker.remove(segmentName);
    }

    public boolean isBufferLocked() {
        return buffLocker.size() > 0;
    }

    public boolean isBufferConsumed() {
        return cursor == available - 1;
    }

    public boolean needRefillBuffer() {
        return available == BUFF_SIZE && cursor < available - 1
                && cursor > available - BUFF_EXHAUST_CRITICAL && !isBufferLocked();
    }

    public void markBufferOffset() {
        buffOffset += cursor;
    }

    public void addLexeme(Lexeme lexeme) {
        originalLexemes.addLexeme(lexeme);
    }

    public void addLexemePath(LexemePath path) {
        if (path != null) pathMap.put(path.getBegin(), path);
    }

    public QuickSortSet getOriginalLexemes() {
        return this.originalLexemes;
    }

    /**
     * output segmentation result to list
     */
    public void outputRawSegmentation() {
        int index=0;
        for (; index <= cursor; ) {
            // continue CJK
            if (CharacterUtil.CHAR_USELESS == charTypes[index]) {
                index++;
                continue;
            }
            LexemePath path = pathMap.get(index);
            if (path != null) {
                Lexeme lexeme = path.pollFirst();
                while (lexeme != null) {
                    lexeme.setLexemeText(String.valueOf(segmentBuff, lexeme.getBegin(), lexeme.getLength()));
                    lexemeList.add(lexeme);
                    // move index to end of lexeme
                    index = lexeme.getBegin() + lexeme.getLength();
                    lexeme = path.pollFirst();
                    if (lexeme != null) {
                        // push the single word of path
                        for (; index < lexeme.getBegin(); index++) {
                            outputSingleCJK(index);
                        }
                    }
                }
            } else {
                // not in pathMap
                outputSingleCJK(index);
                index++;
            }
        }
        pathMap.clear();
    }

    /**
     * output CJK single char
     *
     * @param index
     */
    private void outputSingleCJK(int index) {
        if (CharacterUtil.CHAR_CHINESE == charTypes[index]) {
            Lexeme singleCharLexeme = new Lexeme(buffOffset, index, 1, Lexeme.TYPE_CNCHAR);
            singleCharLexeme.setLexemeText(String.valueOf(segmentBuff, singleCharLexeme.getBegin(),
                    singleCharLexeme.getLength()));
            lexemeList.add(singleCharLexeme);
        }
        if (CharacterUtil.CHAR_OTHER_CJK == charTypes[index]) {
            Lexeme singleCharLexeme = new Lexeme(buffOffset, index, 1, Lexeme.TYPE_OTHER_CJK);
            singleCharLexeme.setLexemeText(String.valueOf(segmentBuff, singleCharLexeme.getBegin(),
                    singleCharLexeme.getLength()));
            lexemeList.add(singleCharLexeme);
        }
    }

    public Lexeme getNextLexeme() {
        // remove first lexeme
        Lexeme result = lexemeList.pollFirst();
        while (result != null) {
            // quantifier num word merge
            compound(result);
            if (getSingleton().isStopWord(segmentBuff, result.getBegin(), result.getLength())) {
                result = lexemeList.pollFirst();
            } else {
                result.setLexemeText(String.valueOf(segmentBuff, result.getBegin(), result.getLength()));
                break;
            }
        }
        return result;
    }

    private void compound(Lexeme lexeme) {
        if (!config.useSmart()) return;
        // quantifier num word merge
        if (!lexemeList.isEmpty()) {
            if (Lexeme.TYPE_ARABIC == lexeme.getLexemeType()) {
                Lexeme nextLexeme = lexemeList.peekFirst();
                boolean isAppend = false;
                if (Lexeme.TYPE_CNUM == nextLexeme.getLexemeType()) {
                    // merge english and CN quantifier word
                    isAppend = lexeme.isAppend(nextLexeme, Lexeme.TYPE_CNUM);
                } else if (Lexeme.TYPE_COUNT == nextLexeme.getLexemeType()) {
                    // merge english quantifier + CN num word
                    isAppend = lexeme.isAppend(nextLexeme, Lexeme.TYPE_CQUAN);
                }
                if (isAppend) lexemeList.pollFirst();
            }

            // second round
            if (Lexeme.TYPE_CNUM == lexeme.getLexemeType() && !lexemeList.isEmpty()) {
                Lexeme nextLexeme = lexemeList.peekFirst();
                boolean isAppend = false;
                if (Lexeme.TYPE_COUNT == nextLexeme.getLexemeType()) {
                    // merge CN quantifier + CN num
                    isAppend = lexeme.isAppend(nextLexeme, Lexeme.TYPE_CQUAN);
                }
                if (isAppend) lexemeList.pollFirst();
            }
        }
    }

    public void reset() {
        this.buffLocker.clear();
        this.originalLexemes = new QuickSortSet();
        this.available = 0;
        this.buffOffset = 0;
        this.charTypes = new int[BUFF_SIZE];
        this.cursor = 0;
        this.lexemeList.clear();
        this.segmentBuff = new char[BUFF_SIZE];
        this.pathMap.clear();
    }
}

