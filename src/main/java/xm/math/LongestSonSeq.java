package xm.math;

/**
 * Created by xuming on 2016/6/14.
 */
public class LongestSonSeq {
    static int[][] martix;

    static String str1 = "cnblogs";
    static String str2 = "belong";

    public static void main(String[] arge) {
        martix = new int[str1.length() + 1][str2.length() + 1];
        LCS(str1, str2);
        //只要拿出矩阵最后一个位置的数字即可
        System.out.println("当前最大公共子序列的长度为:" + martix[str1.length()][str2.length()]);
    }

    static void LCS(String str1, String str2) {
        //初始化边界，过滤掉0的情况
        for (int i = 0; i <= str1.length(); i++)
            martix[i][0] = 0;

        for (int j = 0; j <= str2.length(); j++)
            martix[0][j] = 0;

        //填充矩阵
        for (int i = 1; i <= str1.length(); i++) {
            for (int j = 1; j <= str2.length(); j++) {
                //相等的情况
                char[] strchar1 = str1.toCharArray();
                char[] strchar2 = str2.toCharArray();
                if (strchar1[i - 1] == strchar2[j - 1]) {
                    martix[i][j] = martix[i - 1][j - 1] + 1;
                    System.out.println("martix[i][j]:" + String.valueOf(strchar1[i - 1]) + "    i:" + i + ";j:" + j);
                } else {
                    //比较“左边”和“上边“，根据其max来填充
                    if (martix[i - 1][j] >= martix[i][j - 1]) {
                        martix[i][j] = martix[i - 1][j];
                    } else {
                        martix[i][j] = martix[i][j - 1];
                    }
                }
            }
        }
    }
}

