package xm.ik.dic;

import java.util.HashMap;
import java.util.Map;

/**
 * the segment of dictionary
 *
 * @author xuming
 */
public class DictionarySegment implements Comparable<DictionarySegment> {
    private static final Map<Character, Character> charMap = new HashMap<>(16, 0.95f);
    private static final int ARRAY_LENGTH_LIMIT = 3;
    private Map<Character, DictionarySegment> childrenMap;
    private DictionarySegment[] childrenArray;
    private Character nodeChar;
    private int storeSize = 0;
    private int nodeState = 0;

    DictionarySegment(Character nodeChar) {
        if (nodeChar == null) {
            throw new IllegalArgumentException("parameters is null, character is null");
        }
        this.nodeChar = nodeChar;
    }

    Character getNodeChar() {
        return nodeChar;
    }

    boolean hasNextNode() {
        return this.storeSize > 0;
    }

    @Override
    public int compareTo(DictionarySegment o) {
        return 0;
    }
}
