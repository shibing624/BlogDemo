package xm.ik.demo;

import org.junit.Test;
import xm.ik.analyzer.Tokenizer;
import xm.ik.collection.Lexeme;

import java.util.List;

/**
 * @author xuming
 */
public class AmbiguityDemo {
    @Test
    public void test1() {
        System.out.println("starting...");
        String str = "这是一个中文分词的例子，你可以直接运行它！i can analysis";
        List<Lexeme> lexemeList = new Tokenizer().parse(str);
        System.out.println(str + " size: "+lexemeList.size());
        lexemeList.forEach(System.out::println);
    }
}
