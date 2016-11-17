package xm.math.trietree;

import java.util.Map;

/**
 * Created by xuming on 2016/6/24.
 */
public class Main {
    private static WordDictionary wordDict = WordDictionary.getInstance();
    public static void main(String[] args) {

        DictSegment trie = wordDict.getTrie();
        String sentence = "矿";

        char[] chars = sentence.toCharArray();
        int N = chars.length;
        int i = 0, j = 0;

        while (i < N) {
            Hit hit = trie.match(chars, i, j - i + 1);
            if (hit.isPrefix() || hit.isMatch()) {
                if (hit.isMatch()) {

                    DictSegment ds = trie.lookforSegment(chars[i], 0);
                    Map<Character, DictSegment> dsmap = ds.getChildMap();

                    for (Map.Entry<Character, DictSegment> entry : dsmap.entrySet()) {

                        System.out.println("Key = " + entry.getKey() + ", Value = " + sentence+ entry.getKey() );

                    }

                }
                j += 1;
                if (j >= N) {
                    i += 1;
                    j = i;
                }
            }
            else {
                i += 1;
                j = i;
            }
        }
    }
}
