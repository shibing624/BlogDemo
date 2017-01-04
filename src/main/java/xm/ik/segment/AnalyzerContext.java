package xm.ik.segment;

import xm.ik.cfg.Config;
import xm.ik.collection.Lexeme;
import xm.ik.collection.LexemePath;
import xm.ik.collection.QuickSortSet;

import java.io.IOException;
import java.io.Reader;
import java.util.*;

/**
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
        this.pathMap = new HashMap<>();
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

    public char getCurrentChar(){
        return segmentBuff[cursor];
    }
    public int getCurrentCharType(){
        return charTypes[cursor];
    }
    public int fillBuffer(Reader reader)throws IOException{
        return 0;
    }
}
