package xm.ik.collection;

/**
 * lexeme path
 *
 * @author xuming
 */
public class LexemePath extends QuickSortSet implements Comparable<LexemePath> {
    private int begin;
    private int end;
    private int length;

    LexemePath() {
        this.begin = -1;
        this.end = -1;
        this.length = 0;
    }

    boolean addCrossLexeme(Lexeme lexeme) {
        if (isEmpty()) {
            addLexeme(lexeme);
            begin = lexeme.getBegin();
            end = lexeme.getBegin() + lexeme.getLength();
            length += lexeme.getLength();
            return true;
        } else if (checkCross(lexeme)) {
            addLexeme(lexeme);
            if (lexeme.getBegin() + lexeme.getLength() > end) {
                end = lexeme.getBegin() + lexeme.getLength();
            }
            length = end - begin;
            return true;
        }
        return false;
    }

    boolean addNotCorssLexeme(Lexeme lexeme) {
        if (isEmpty()) {
            addLexeme(lexeme);
            begin = lexeme.getBegin();
            end = lexeme.getBegin() + lexeme.getLength();
            length += lexeme.getLength();
            return true;
        } else if (checkCross(lexeme)) {
            return false;
        }
        addLexeme(lexeme);
        length += lexeme.getLength();
        Lexeme head = peekFirst();
        begin = head.getBegin();
        Lexeme tail = peekLast();
        end = tail.getBegin() + tail.getLength();
        return true;
    }

    Lexeme removeTail() {
        Lexeme tail = pollLast();
        if (isEmpty()) {
            begin = -1;
            end = -1;
            length = 0;
        } else {
            length -= tail.getLength();
            Lexeme newTail = peekLast();
            end = newTail.getBegin() + newTail.getLength();
        }
        return tail;
    }

    /**
     * ambigulity lexeme, check the position of split word
     *
     * @param lexeme
     * @return
     */
    public boolean checkCross(Lexeme lexeme) {
        return (lexeme.getBegin() >= begin && lexeme.getBegin() < end)
                || (begin >= lexeme.getBegin() && begin < lexeme.getBegin() + lexeme.getLength());
    }

    int getXWeight() {
        int product = 1;
        Cell c = getHead();
        while (c != null && c.getLexeme() != null) {
            product *= c.getLexeme().getLength();
            c = c.getNext();
        }
        return product;
    }

    int getPWeight() {
        int pW = 0;
        int p = 0;
        Cell c = getHead();
        while (c != null && c.getLexeme() != null) {
            p++;
            pW += p * c.getLexeme().getLength();
            c = c.getNext();
        }
        return pW;
    }

    LexemePath copy() {
        LexemePath copy = new LexemePath();
        copy.begin = begin;
        copy.end = end;
        copy.length = length;
        Cell c = getHead();
        while (c != null && c.getLexeme() != null) {
            copy.addLexeme(c.getLexeme());
            c = c.getNext();
        }
        return copy;
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

    public int getLength() {
        return length;
    }

    public int getPathLength() {
        return end - begin;
    }

    @Override
    public int compareTo(LexemePath o) {
        // compare length
        if (length > o.length) return -1;
        if (length < o.length) return 1;
        // compare size: less better
        if (size() < o.size()) return -1;
        if (size() > o.size()) return 1;
        // path length: more better
        if (getPathLength() > o.getPathLength()) return -1;
        if (getPathLength() < o.getPathLength()) return 1;
        // reverse split: later better
        if (end > o.end) return -1;
        if (end < o.end) return 1;
        // average length of word(XWeight): more better
        if (getXWeight() > o.getXWeight()) return -1;
        if (getXWeight() < o.getXWeight()) return 1;
        // position of lexeme: more better
        if (getPWeight() > o.getPWeight()) return -1;
        if (getPWeight() < o.getPWeight()) return 1;
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("begin: ").append(begin).append("\r\n");
        sb.append("end: ").append(end).append("\r\n");
        sb.append("length: ").append(length).append("\r\n");
        Cell head = getHead();
        while (head != null) {
            sb.append("lexeme: ").append(head.getLexeme()).append("\r\n");
            head = head.getNext();
        }
        return sb.toString();
    }
}
