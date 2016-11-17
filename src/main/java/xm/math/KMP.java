package xm.math;

/**
 * Created by xuming on 2016/6/29.
 */
public class KMP {
    public static void main(String[] args) {
        String s = "ababcabababdc";
        int s1 = s.indexOf("babdc");
        System.out.println(s1 + "：jdk的实现方法");
        int index = BF("ababcabababdc.".toCharArray(), "babdc.".toCharArray(), 0);
        System.out.println(index + "：wiki的BF算法实现。");
        int index1 = BFmath("ababcabababdc", "babdc");
        System.out.println(index1 + "：自写的BF算法");
        int kmp = KMPStrMatch("ababcabababdc", "babdc");
        System.out.println(kmp + "：KMP算法实现。");
    }

    //wiki的BF
    public static int BF(char S[], char T[], int pos) {//c从第pos位开始搜索匹配
        int i = pos, j = 0;
        while (S[i + j] != '.' && T[j] != '.') {
            if (S[i + j] == T[j])
                j++;
            else {
                i++;
                j = 0;
            }
        }
        if (T[j] == '.')
            return i + 1;
        else
            return -1;
    }

    //自己写BF
    public static int BFmath(String T, String P) {
        int t = 0, p = 0;
        int tLen = T.length();
        int pLen = P.length();
        if (tLen < pLen)
            return -1;
        while (p < pLen && t < tLen) {
            if (T.charAt(t) == P.charAt(p)) {
                p++;
                t++;
            } else {
                t = t - p + 1;
                p = 0;
            }
        }
        if (p == pLen)
            return t - pLen + 1;
        else
            return -1;
    }


    //计算字符串特征向量
    public static int[] findNext(String P) {
        int i = 0;
        int k = -1;                         //前缀串起始位置("-1"是方便计算）
        int m = P.length();                   //m为字符串P的长度
        assert (m > 0);                          // 若m=0,退出
        int[] next = new int[m];            // 动态存储区开辟整数数组

        next[0] = -1;
        while (i < m) {                          //计算i=1...m-1的next值
            while (k >= 0 && P.charAt(i) != P.charAt(k)) {
                 k = next[k];
            }

            i++;
            k++;
            if (i == m) break;
            if (P.charAt(i) == P.charAt(k)) {
                next[i] = next[k];         //P[i]和P[k]相等，优化
            } else
                next[i] = k;               //不需要优化，就是位置i的首尾子串的长度
        }
        return next;
    }

    //计算字符串特征向量（优化版）
    public static int[] getNext(String P) {
        int i = 0;
        int k = -1;                         //前缀串起始位置("-1"是方便计算）
        int[] next = new int[P.length()];            // 动态存储区开辟整数数组
        next[0] = -1;
        while (i < P.length()-1) {                          //计算i=1...m-1的next值
            if(k==-1 || P.charAt(k) == P.charAt(i)){
                next[++i] = ++k;    //pk=pi的情况: next[i+1]=k+1 => next[i+1]=next[i]+1
            }else {
                k = next[k];            //pk != pi 的情况:我们递推 k=next[k];要么找到，要么k=-1中止
            }
        }
        return next;
    }

    //KMP模式匹配算法的实现
    public static int KMPStrMatch(String T, String P) {
        int[] next = getNext(P);           //计算前缀串 和 后缀串的next
        int t = 0,p = 0;                           //模式的下标变量
        int pLen = P.length();              //模式的长度
        int tLen = T.length();              //目标的长度
        if (tLen < pLen)                    //如果目标比模式短，匹配无法成功
            return -1;
        while (p < pLen && t < tLen) {           //反复比较对应字符来开始匹配
            if (p == -1 || T.charAt(t) == P.charAt(p)) {
                p++;
                t++;
            } else {
                p = next[p];
            }
        }
        if (p == pLen)
            return t - pLen + 1;
        else
            return -1;
    }



    /**
     * Code shared by String and StringBuffer to do searches. The
     * source is the character array being searched, and the target
     * is the string being searched for.
     *
     * @param   source       the characters being searched.
     * @param   sourceOffset offset of the source string.
     * @param   sourceCount  count of the source string.
     * @param   target       the characters being searched for.
     * @param   targetOffset offset of the target string.
     * @param   targetCount  count of the target string.
     * @param   fromIndex    the index to begin searching from.
     */
    static int indexOf(char[] source, int sourceOffset, int sourceCount,
                       char[] target, int targetOffset, int targetCount,
                       int fromIndex) {
        if (fromIndex >= sourceCount) {
            return (targetCount == 0 ? sourceCount : -1);
        }
        if (fromIndex < 0) {
            fromIndex = 0;
        }
        if (targetCount == 0) {
            return fromIndex;
        }

        char first = target[targetOffset];
        int max = sourceOffset + (sourceCount - targetCount);

        for (int i = sourceOffset + fromIndex; i <= max; i++) {
            /* Look for first character. */
            if (source[i] != first) {
                while (++i <= max && source[i] != first);
            }

            /* Found first character, now look at the rest of v2 */
            if (i <= max) {
                int j = i + 1;
                int end = j + targetCount - 1;
                for (int k = targetOffset + 1; j < end && source[j]
                        == target[k]; j++, k++);

                if (j == end) {
                    /* Found whole string. */
                    return i - sourceOffset;
                }
            }
        }
        return -1;
    }

}
