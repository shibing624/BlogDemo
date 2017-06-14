package xm.math.leetcode;

import org.junit.Test;

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
}
