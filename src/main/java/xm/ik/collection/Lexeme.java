package xm.ik.collection;

/**
 * @author xuming
 */
public class Lexeme implements Comparable<Lexeme> {
    // 未知
    public static final int TYPE_UNKNOWN = 0;
    // 英文
    public static final int TYPE_ENGLISH = 1;
    // 数字
    public static final int TYPE_ARABIC = 2;
    // 英文数字混合
    public static final int TYPE_LETTER = 3;
    // 中文词元
    public static final int TYPE_CNWORD = 4;
    // 中文单字
    public static final int TYPE_CNCHAR = 64;
    // 日韩文字
    public static final int TYPE_OTHER_CJK = 8;
    // 中文数词
    public static final int TYPE_CNUM = 16;
    // 中文量词
    public static final int TYPE_COUNT = 32;
    // 中文数量词
    public static final int TYPE_CQUAN = 48;
    // 词元的起始位移
    private int offset;
    // 词元的相对起始位置
    private int begin;
    // 词元的长度
    private int length;
    // 词元文本
    private String lexemeText;
    // 词元类型
    private int lexemeType;

    public Lexeme(int offset, int begin, int length, int lexemeType) {
        this.offset = offset;
        this.begin = begin;
        if (length < 0) {
            throw new IllegalArgumentException("length < 0");
        }
        this.length = length;
        this.lexemeType = lexemeType;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public int getBeginPosition() {
        return offset + begin;
    }

    public int getEndPosition() {
        return offset + begin + length;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getLexemeText() {
        if (lexemeText == null) return "";
        return lexemeText;
    }

    public void setLexemeText(String lexemeText) {
        if (lexemeText == null) {
            this.lexemeText = "";
            this.length = 0;
        }
        this.lexemeText = lexemeText;
        this.length = lexemeText.length();
    }

    public int getLexemeType() {
        return lexemeType;
    }

    public void setLexemeType(int lexemeType) {
        this.lexemeType = lexemeType;
    }

    public boolean equals(Object o) {
        if (o == null) return false;
        if (this == o) return true;
        if (o instanceof Lexeme) {
            Lexeme other = (Lexeme) o;
            if (this.offset == other.getOffset() && this.begin == other.getBegin() && this.length == other.getLength()) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int absBegin = getBeginPosition();
        int absEnd = getEndPosition();
        return (absBegin * 37) + (absEnd * 31) + ((absBegin * absEnd) % getLength()) * 11;
    }

    @Override
    public int compareTo(Lexeme o) {
        if (this.begin < o.getBegin()) {
            return -1;
        } else if (this.begin == o.getBegin()) {
            if (length > o.getLength()) {
                return -1;
            } else if (length == o.getLength()) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return 1;
        }
    }

    public boolean isAppend(Lexeme lexeme, int lexemeType) {
        if (lexeme != null && getEndPosition() == lexeme.getBeginPosition()) {
            this.length += lexeme.getLength();
            this.lexemeType = lexemeType;
            return true;
        }
        return false;
    }

    public String getLexemeTypeString() {
        switch (lexemeType) {
            case TYPE_ENGLISH:
                return "ENGLISH";
            case TYPE_ARABIC:
                return "ARABIC";
            case TYPE_LETTER:
                return "LETTER";
            case TYPE_CNWORD:
                return "CN_WORD";
            case TYPE_CNCHAR:
                return "CN_CHAR";
            case TYPE_OTHER_CJK:
                return "OTHER_CJK";
            case TYPE_COUNT:
                return "COUNT";
            case TYPE_CNUM:
                return "TYPE_CNUM";
            case TYPE_CQUAN:
                return "TYPE_CQUAN";
            case TYPE_UNKNOWN:
            default:
                return "UNKONW";
        }
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(getBeginPosition())
                .append("-")
                .append(getEndPosition())
                .append(": ")
                .append(lexemeText)
                .append("/")
                .append(getLexemeTypeString());
        return sb.toString();
    }
}
