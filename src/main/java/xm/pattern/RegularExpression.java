package xm.pattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xuming
 */
public class RegularExpression {
    public static void main(String[] args) {
        String text = "abcdebcadxbc";

        Pattern pattern = Pattern.compile(".bc");
        Matcher matcher = pattern.matcher(text);

        while(matcher.find()) {
            System.out.println(matcher.group());
        }
        System.out.println();

        String phones1 =
                "Justin 的手机号码：0939-100391\n" +
                        "momor 的手机号码：0939-666888\n"+
                        "momor1 的手机号码：0139-666888\n";

        Pattern pattern1 = Pattern.compile(".*0939-\\d{6}");
        Matcher matcher1 = pattern1.matcher(phones1);

        while(matcher1.find()) {
            System.out.println(matcher1.group());
        }




    }
}
