package xm.math.huffman;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * @author xuming
 */
public class HuffmanTest {
    public static void main(String[] args) {
        String oriStr = "i love you,and you?";
        Map<Character, Integer> statistics = Huffman.statistics(oriStr.toCharArray());
        String encodedBinariStr =  Huffman.encode(oriStr, statistics);
        String decodedStr =  Huffman.decode(encodedBinariStr, statistics);

        System.out.println("Original string: " + oriStr);
        System.out.println("Huffman encoded binary string: " + encodedBinariStr);
        System.out.println("decoded string from binary string: " + decodedStr);

        System.out.println("binary string of UTF-8: " + getStringOfByte(oriStr, Charset.forName("UTF-8")));
        System.out.println("binary string of UTF-16: "
                + getStringOfByte(oriStr, Charset.forName("UTF-16")));
        System.out.println("binary string of US-ASCII: "
                + getStringOfByte(oriStr, Charset.forName("US-ASCII")));
        System.out.println("binary string of GB2312: "
                + getStringOfByte(oriStr, Charset.forName("GB2312")));
    }

    public static String getStringOfByte(String str, Charset charset) {
        if (str == null || str.equals("")) {
            return "";
        }

        byte[] byteArray = str.getBytes(charset);
        int size = byteArray.length;
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < size; i++) {
            byte temp = byteArray[i];
            buffer.append(getStringOfByte(temp));
        }

        return buffer.toString();
    }

    public static String getStringOfByte(byte b) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 7; i >= 0; i--) {
            byte temp = (byte) ((b >> i) & 0x1);
            buffer.append(String.valueOf(temp));
        }

        return buffer.toString();
    }

}