package xm.math.leetcode;

import org.junit.Test;

/**
 * Write a function to find the longest common prefix string amongst an array of strings.
 * input:["adsskl","adassl90","adssjlo8llj"]
 * output:ss
 *
 * @author XuMing
 */
public class LongestCommonPrefix {
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) return "";
        String result = strs[0];
        for (int i = 1; i < strs.length; i++) {
            while (strs[i].indexOf(result) != 0)
                result = result.substring(0, result.length() - 1);
        }
        return result;
    }

    @Test
    public void test() {
        LongestCommonPrefix demo = new LongestCommonPrefix();
        String[] strs = new String[]{
                "adsskl", "adassl90", "adssjlo8llj"
        };
        System.out.println(demo.longestCommonPrefix(strs));
    }
}
