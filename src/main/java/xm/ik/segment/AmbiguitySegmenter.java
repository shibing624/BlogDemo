package xm.ik.segment;

import xm.ik.collection.Cell;
import xm.ik.collection.Lexeme;
import xm.ik.collection.LexemePath;
import xm.ik.collection.QuickSortSet;

import java.util.Stack;
import java.util.TreeSet;

/**
 * deal with ambiguity conflict
 *
 * @author xuming
 */
public class AmbiguitySegmenter {
    /**
     * Ambiguity processing
     *
     * @param context
     * @param useSmart
     */
    public void process(AnalyzerContext context, boolean useSmart) {
        QuickSortSet originalLexemes = context.getOriginalLexemes();
        Lexeme originalLexeme = originalLexemes.pollFirst();
        LexemePath crossPath = new LexemePath();
        while (originalLexeme != null) {
            if (!crossPath.addCrossLexeme(originalLexeme)) {
                if (crossPath.size() == 1 || !useSmart) {
                    // not ambiguity word
                    context.addLexemePath(crossPath);
                } else {
                    // need ambiguity process
                    Cell headCell = crossPath.getHead();
                    LexemePath judgePath = judgePath(headCell);
                    // output judge path result
                    context.addLexemePath(judgePath);
                }
                // put original lexeme to new cross path
                crossPath = new LexemePath();
                crossPath.addCrossLexeme(originalLexeme);
            }
            originalLexeme = originalLexemes.pollFirst();
        }
        // the rest part
        if (crossPath.size() == 1 || !useSmart)
            context.addLexemePath(crossPath);
        else {
            Cell headCell = crossPath.getHead();
            LexemePath judgePath = judgePath(headCell);
            context.addLexemePath(judgePath);
        }
    }

    /**
     * recognize the ambiguity lexeme
     *
     * @param cell
     * @return
     */
    private LexemePath judgePath(Cell cell) {
        TreeSet<LexemePath> optionPaths = new TreeSet<>();
        LexemePath option = new LexemePath();
        Stack<Cell> lexemeStack = forwardPath(cell, option);
        // add option result to list
        optionPaths.add(option.copy());
        Cell c;
        while (!lexemeStack.isEmpty()) {
            c = lexemeStack.pop();
            // recall to the head
            backPath(c.getLexeme(), option);
            // from the ambiguity word start
            forwardPath(c, option);
            optionPaths.add(option.copy());
        }
        // get the best option
        return optionPaths.first();
    }

    /**
     * back to the place where contain the lexeme
     *
     * @param lexeme
     * @param option
     */
    private void backPath(Lexeme lexeme, LexemePath option) {
        while (option.checkCross(lexeme))
            option.removeTail();
    }

    /**
     * forward loop, delete ambiguity, add to path, create a new list
     *
     * @param cell
     * @param option
     * @return
     */
    private Stack<Cell> forwardPath(Cell cell, LexemePath option) {
        // conflict stack
        Stack<Cell> conflictStack = new Stack<>();
        Cell c = cell;
        // iter the lexeme list
        while (c != null && c.getLexeme() != null) {
            if (!option.addNotCrossLexeme(c.getLexeme())) {
                conflictStack.push(c);
            }
            c = c.getNext();
        }
        return conflictStack;
    }

}
