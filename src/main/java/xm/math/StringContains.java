package xm.math;

import java.util.Arrays;

/**
 * 字符串包含问题
 * Created by xuming on 2016/6/22.
 */
public class StringContains {

    public static void main(String[] arg){

        if(StringContain1("good","oog")){
            System.out.println("1");
        }else{
            System.out.println("0");
        }
        if(StringContain2("good","ood")){
            System.out.println("true");
        }else{
            System.out.println("false");
        }
    }
    public static boolean StringContain1(String a, String b) {
        for (int i = 0; i < b.length(); ++i) {
            int j;
            for (j = 0; (j < a.length()) && (a.charAt(j) != b.charAt(i)); ++j);
            if (j >= a.length()) {
                return false;
            }
        }
        return true;
    }

    //注意A B中可能包含重复字符，所以注意A下标不要轻易移动。这种方法改变了字符串。如不想改变请自己复制
    public static boolean StringContain2(String a,String b)
    {
        int subvalue = a.compareTo(b);

        char[] a_chars = a.toCharArray();
        Arrays.sort(a_chars);
        char[] b_chars = b.toCharArray();
        Arrays.sort(b_chars);
        System.out.println(a.chars().sorted().collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString());

//        Sort(a.begin(),a.end());
//        sort(b.begin(),b.end());
        for (int pa = 0, pb = 0; pb < b.length();)
        {
            while ((pa < a.length()) && (a.charAt(pa) < b.charAt(pb)))
            {
                ++pa;
            }
            if ((pa >= a.length()) || (a.charAt(pa) > b.charAt(pb)))
            {
                return false;
            }
            //a[pa] == b[pb]
            ++pb;
        }
        return true;
    }
}
