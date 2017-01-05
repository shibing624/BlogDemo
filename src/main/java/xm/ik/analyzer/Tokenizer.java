package xm.ik.analyzer;

import xm.ik.collection.Lexeme;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * chinese tokenizer
 * @author xuming
 */
public class Tokenizer {
    private Analyzer analyzer;
    private int endPosition;
    public Tokenizer(){
    }
    public List<Lexeme> parse(String inputStr) {
        List<Lexeme> result = new ArrayList<>();
        Reader inputReader = new StringReader(inputStr);
        analyzer = new Analyzer(inputReader);
        Lexeme nextLexeme = analyzer.next();
        if (nextLexeme != null) {
            result.add(nextLexeme);
            endPosition = nextLexeme.getEndPosition();
        }
        return result;
    }

}
