package xm.ik.dic;

/**
 * the word hit dictionary
 * @author xuming
 */
public class Hit {
    private static final int UNMATCH = 0;
    private static final int PREFIX = 2;
    private static final int MATCH = 1;
    private int hitState = UNMATCH;
    private DictionarySegment matchedDictionarySegment;
    private  int begin;
    private  int end;
    public boolean isMatch(){
        return (this.hitState&MATCH)>0;
    }
    public void setMatch(){
        this.hitState = this.hitState|MATCH;
    }
    public boolean isPrefix(){
        return (this.hitState&PREFIX)>0;
    }
    public void setPrefix(){
        this.hitState = this.hitState|PREFIX;
    }
    public boolean isUnmatch(){
        return this.hitState == UNMATCH;
    }
    public void setUnmatch(){
        this.hitState = UNMATCH;
    }

    public DictionarySegment getMatchedDictionarySegment() {
        return matchedDictionarySegment;
    }

    public void setMatchedDictionarySegment(DictionarySegment matchedDictionarySegment) {
        this.matchedDictionarySegment = matchedDictionarySegment;
    }

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}
