package xm.demo;

/**
 * 字符串处理示例
 * Created by xuming on 2016/8/24.
 */
public class StringDemo {
    public static void main(String[] args) throws Exception {
        String str = "nihao,。";
        char[] syschars = new char[65286];
        syschars['％'] = '%';
        char s = '.';
        char n ;
        char mm = '%';
        char jj='％';
        syschars[0] = s;
        syschars.hashCode();

        System.out.println(syschars.length);

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            syschars[i] = c;

        }
        syschars['a'] = '8';
        // char 的对应的数字是ASCII，可以把这个值设定为index

        char k = syschars[0];
        if (s > 0) {
            System.out.print(s);
        }
    }

}
