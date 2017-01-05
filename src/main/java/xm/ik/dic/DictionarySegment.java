package xm.ik.dic;

import java.util.Arrays;
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

    Hit match(char[] charArray) {
        return this.match(charArray, 0, charArray.length, null);
    }

    Hit match(char[] charArray, int begin, int length) {
        return this.match(charArray, begin, length, null);
    }

    Hit match(char[] charArray, int begin, int length, Hit searchHit) {
        if (searchHit == null) {
            searchHit = new Hit();
            searchHit.setBegin(begin);
        } else {
            searchHit.setUnmatch();
        }
        searchHit.setEnd(begin);
        Character keyChar = new Character(charArray[begin]);
        DictionarySegment ds = null;
        DictionarySegment[] segmentArray = this.childrenArray;
        Map<Character, DictionarySegment> segmentMap = this.childrenMap;
        // 1.find keyChar in DictionarySegment
        if (segmentArray != null) {
            DictionarySegment keySegment = new DictionarySegment(keyChar);
            int position = Arrays.binarySearch(segmentArray, 0, this.storeSize, keySegment);
            if (position >= 0) {
                ds = segmentArray[position];
            }
        } else if (segmentMap != null) {
            // get keyChar at map
            ds = segmentMap.get(keyChar);
        }
        // 2. find DictionarySegment, judge the state of Hited word
        if (ds != null) {
            if (length > 1)
                return ds.match(charArray, begin + 1, length - 1, searchHit);
            else if (length == 1) {
                if (ds.nodeState == 1)
                    searchHit.setMatch();
                if (ds.hasNextNode()) {
                    searchHit.setPrefix();
                    searchHit.setMatchedDictionarySegment(ds);
                }
                return searchHit;
            }
        }
        // 3. not find DictionarySegment, hit is unMatch
        return searchHit;
    }

    void fillSegment(char[] chars) {
        this.fillSegment(chars, 0, chars.length, 1);
    }

    void disableSegment(char[] chars) {
        this.fillSegment(chars, 0, chars.length, 0);
    }

    private synchronized void fillSegment(char[] charArray, int begin, int length, int enable) {
        Character beginChar = new Character(charArray[begin]);
        Character keyChar = charMap.get(beginChar);
        // add new char to dictionary
        if (keyChar == null) {
            charMap.put(beginChar, beginChar);
            keyChar = beginChar;
        }
        // find keyChar with keyChar, if no and create
        DictionarySegment ds = searchSegment(keyChar, enable);
        if (ds != null) {
            if (length > 1) {
                ds.fillSegment(charArray, begin + 1, length - 1, enable);
            } else if (length == 1) {
                //enable=1 has the word; enable0 is disable the word
                ds.nodeState = enable;
            }
        }
    }

    private DictionarySegment searchSegment(Character keyChar, int isCreate) {
        DictionarySegment ds = null;
        if (storeSize <= ARRAY_LENGTH_LIMIT) {
            DictionarySegment[] segmentArray = getChildrenArray();
            DictionarySegment keySegment = new DictionarySegment(keyChar);
            int position = Arrays.binarySearch(segmentArray, 0, storeSize, keySegment);
            if (position >= 0)
                ds = segmentArray[position];
            // not found
            if (ds == null && isCreate == 1) {
                ds = keySegment;
                if (storeSize < ARRAY_LENGTH_LIMIT) {
                    segmentArray[storeSize] = ds;
                    storeSize++;
                    Arrays.sort(segmentArray, 0, storeSize);
                } else {
                    // if array fill, change to map store
                    Map<Character, DictionarySegment> segmentMap = getChildrenMap();
                    // migrate array to map
                    migrate(segmentArray, segmentMap);
                    segmentMap.put(keyChar, ds);
                    storeSize++;
                    childrenArray = null;
                }
            }
        } else {
            Map<Character, DictionarySegment> segmentMap = getChildrenMap();
            // search map
            ds = segmentMap.get(keyChar);
            if (ds == null && isCreate == 1) {
                ds = new DictionarySegment(keyChar);
                segmentMap.put(keyChar, ds);
                storeSize++;
            }
        }
        return ds;
    }

    private DictionarySegment[] getChildrenArray() {
        if (childrenArray == null) {
            synchronized (this) {
                if (childrenArray == null)
                    childrenArray = new DictionarySegment[ARRAY_LENGTH_LIMIT];
            }
        }
        return childrenArray;
    }

    private Map<Character, DictionarySegment> getChildrenMap() {
        if (childrenMap == null) {
            synchronized (this) {
                if (childrenMap == null)
                    childrenMap = new HashMap<>(ARRAY_LENGTH_LIMIT * 2, 0.8f);
            }
        }
        return childrenMap;
    }

    private void migrate(DictionarySegment[] segmentArray, Map<Character, DictionarySegment> segmentMap) {
        for (DictionarySegment segment : segmentArray) {
            if (segment != null)
                segmentMap.put(segment.nodeChar, segment);
        }
    }

    @Override
    public int compareTo(DictionarySegment o) {
        return this.nodeChar.compareTo(o.nodeChar);
    }
}
