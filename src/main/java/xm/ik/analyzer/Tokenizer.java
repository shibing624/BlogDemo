package xm.ik.analyzer;

import xm.ik.collection.Lexeme;

import java.io.StringReader;
import java.util.List;

/**
 * chinese tokenizer
 *
 * @author xuming
 */
public class Tokenizer {
    public List<Lexeme> parse(String inputStr) {
        return new Analyzer(new StringReader(inputStr)).parse();
    }

}
