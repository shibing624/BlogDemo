package xm.ik.util;

/**
 * util of character
 *
 * @author xuming
 */
public class CharacterUtil {
    public static final int CHAR_USELESS = 0;

    public static final int CHAR_ARABIC = 1;

    public static final int CHAR_ENGLISH = 2;

    public static final int CHAR_CHINESE = 4;

    public static final int CHAR_OTHER_CJK = 8;

    /**
     * 进行字符规格化（全角转半角，大写转小写处理）
     *
     * @param c char
     * @return char
     */
    public static char regularize(char c) {
        if (c == 12288) c = (char) 32;
        else if (c > 65280 && c < 65375) c = (char) (c - 65248);
        else if (c >= 'A' && c <= 'Z') c += 32;
        return c;
    }

    public static int identifyCharType(char c) {
        if (c >= '0' && c < '9') return CHAR_ARABIC;
        else if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) return CHAR_ENGLISH;
        else {
            Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
            // 目前已知的中文字符UTF-8集合
            if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                    || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                    || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A) {
                return CHAR_CHINESE;
            }
            else if (ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS // 全角数字字符和日韩字符
                    // 韩文字符集
                    || ub == Character.UnicodeBlock.HANGUL_SYLLABLES
                    || ub == Character.UnicodeBlock.HANGUL_JAMO
                    || ub == Character.UnicodeBlock.HANGUL_COMPATIBILITY_JAMO
                    // 日文字符集
                    || ub == Character.UnicodeBlock.HIRAGANA // 平假名
                    || ub == Character.UnicodeBlock.KATAKANA // 片假名
                    || ub == Character.UnicodeBlock.KATAKANA_PHONETIC_EXTENSIONS) {
                return CHAR_OTHER_CJK;
            }
        }
        // 其他的不做处理的字符
        return CHAR_USELESS;
    }
}
