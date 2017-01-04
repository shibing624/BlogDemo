package xm.ik.collection;

/**
 * Cell unit for QuickSortSet
 *
 * @author xuming
 */
public class Cell implements Comparable<Cell> {
    private Cell prev;
    private Cell next;
    private Lexeme lexeme;

    Cell(Lexeme lexeme) {
        if (lexeme == null) {
            throw new IllegalArgumentException("lexeme not be null.");
        }
        this.lexeme = lexeme;
    }

    public Cell getPrev() {
        return prev;
    }

    public void setPrev(Cell prev) {
        this.prev = prev;
    }

    public Cell getNext() {
        return next;
    }

    public void setNext(Cell next) {
        this.next = next;
    }

    public Lexeme getLexeme() {
        return lexeme;
    }

    public void setLexeme(Lexeme lexeme) {
        this.lexeme = lexeme;
    }

    @Override
    public int compareTo(Cell o) {
        return this.lexeme.compareTo(o.lexeme);
    }
}
