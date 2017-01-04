package xm.ik.collection;


/**
 * lexeme sort fast result
 *
 * @author xuming
 */
public class QuickSortSet {
    private Cell head;
    private Cell tail;
    private int size;

    public QuickSortSet() {
        size = 0;
    }

    public boolean addLexeme(Lexeme lexeme) {
        Cell newCell = new Cell(lexeme);
        if (size == 0) {
            head = newCell;
            tail = newCell;
            size++;
            return true;
        }
        if (tail.compareTo(newCell) == 0) {
            return false;
        } else if (tail.compareTo(newCell) < 0) {
            tail.setNext(newCell);
            newCell.setPrev(tail);
            tail = newCell;
            size++;
            return true;
        } else if (head.compareTo(newCell) > 0) {
            head.setPrev(newCell);
            newCell.setNext(head);
            head = newCell;
            size++;
            return true;
        } else {
            Cell index = tail;
            while (index != null && index.compareTo(newCell) > 0) {
                index = index.getPrev();
            }
            if (index.compareTo(newCell) == 0) {
                return false;
            } else if (index.compareTo(newCell) < 0) {
                newCell.setPrev(index);
                newCell.setNext(index.getNext());
                index.getNext().setPrev(newCell);
                index.setNext(newCell);
                return true;
            }
        }
        return false;
    }

    public Lexeme peekFirst() {
        return (head != null) ? head.getLexeme() : null;
    }

    public Lexeme peekLast() {
        return (tail != null) ? tail.getLexeme() : null;
    }

    public Lexeme pollFirst() {
        if (size == 1) {
            Lexeme first = head.getLexeme();
            head = null;
            tail = null;
            size--;
            return first;
        } else if (size > 1) {
            Lexeme first = head.getLexeme();
            head = head.getNext();
            size--;
            return first;
        }
        return null;
    }

    public Lexeme pollLast() {
        if (size == 1) {
            Lexeme last = head.getLexeme();
            head = null;
            tail = null;
            size--;
            return last;
        } else if (size > 1) {
            Lexeme last = tail.getLexeme();
            tail = tail.getPrev();
            size--;
            return last;
        }
        return null;
    }

    public Cell getHead() {
        return head;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
