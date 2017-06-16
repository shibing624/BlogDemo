package xm.math.leetcode;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Reverse digits of an integer.
 * <p>
 * Example1: x = 123, return 321
 * Example2: x = -123, return -321
 *
 * @author XuMing
 */
public class ReverseInteger {
    public int reverse(int x) {
        int result = 0;
        while (x != 0) {
            int a = x % 10;
            int b = result * 10 + a;
            int c = (b - a) / 10;
            if (c != result) {
                return 0;
            }
            result = b;
            x = x / 10;
        }
        return result;
    }

    @Test
    public void test() {
        ReverseInteger reverseInteger = new ReverseInteger();
        System.out.println(reverseInteger.reverse(-123));
    }

    public void test2() {
        String q = "how  are   you";
        String[] temp = (String[]) q.split(" ");
        for (int i = temp.length; i > 0; i--) {
            System.out.print(temp[i - 1] + " ");
        }
    }

    @Test
    public void test1() {
        ReverseInteger reverseInteger = new ReverseInteger();
        reverseInteger.test2();
    }

    @Test
    public void test3() {
        ReverseInteger reverseInteger = new ReverseInteger();
        reverseInteger.test31();
    }

    public void test31() {
        String input = "how  are   you";
        String str = "how  are   you";
        String[] array = str.split(" ");
        int len = array.length;
        for (int i = 0; i < len / 2; i++) {
            String temp = array[i];
            array[i] = array[len - 1 - i];
            array[len - 1 - i] = temp;
        }
        String result = "";
        boolean isFirst = true;
        for (String s : array) {
            if (isFirst) {
                isFirst = false;
            } else {
                result += " ";
            }
            result += s;
        }
        System.out.println(result);
    }

    public void test4() {
        String q = "how  are   you";
        Map map = new HashMap<>();
        int count = 0;
        for (int i = 0; i < q.length(); i++) {
            if (q.indexOf("  ") > -1) {
                map.put("  ", i);
            }
            if (q.indexOf("   ") > -1) {
                map.put("   ", i);
            }
        }
        String[] temp = (String[]) q.split(" ");
        for (int i = temp.length; i > 0; i--) {
            if (temp[i - 1].indexOf(" ") > -1)
                System.out.print(temp[i - 1]);
        }

    }

    @Test
    public void test44() {
        ReverseInteger reverseInteger = new ReverseInteger();
        reverseInteger.test4();
    }
}


