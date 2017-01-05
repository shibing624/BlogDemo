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
        String str =
                "结婚和尚未结婚的lili。" +
//                "我在新东方学习中国英语课程 你喜欢什么样的人.有三个人买了4框梨子。" +
                "最新电脑是windows2017,这不是知名商标。";
        List<Lexeme> lexemeList = new Tokenizer().parse(str);
        System.out.println(str);
        lexemeList.forEach(System.out::println);
    }
}
