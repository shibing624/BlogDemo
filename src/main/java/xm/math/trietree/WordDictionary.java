package xm.math.trietree;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by xuming on 2016/6/24.
 */
public class WordDictionary {
    private static WordDictionary singleton;
    private static final String MAIN_DICT = "src/com/xm/math/trietree/dict.txt";

    public final Map<String, Double> freqs = new HashMap<String, Double>();
    public final Map<String, String> natures = new HashMap<String, String>();
    private Double minFreq = Double.MAX_VALUE;
    private Double total = 0.0;
    private DictSegment _dict;


    private WordDictionary() {
        this.loadDict();
    }

    public static WordDictionary getInstance() {
        if (singleton == null) {
            synchronized (WordDictionary.class) {
                if (singleton == null) {
                    singleton = new WordDictionary();
                    return singleton;
                }
            }
        }
        return singleton;
    }

    public void loadDict() {
        _dict = new DictSegment((char) 0);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(MAIN_DICT), Charset.forName("UTF-8")));

            long s = System.currentTimeMillis();
            while (br.ready()) {
                String line = br.readLine();
                String[] tokens = line.split("[\t ]+");

                if (tokens.length < 2)
                    continue;

                String word = tokens[0];
                double freq = Double.valueOf(tokens[1]);
                String nature = String.valueOf(tokens[2]);
                total += freq;
                word = addWord(word);
                freqs.put(word, freq);
                natures.put(word, nature);
            }
            // normalize
            for (Map.Entry<String, Double> entry : freqs.entrySet()) {
                entry.setValue((Math.log(entry.getValue() / total)));
                minFreq = Math.min(entry.getValue(), minFreq);
            }
            System.out.println(String.format(Locale.getDefault(), "main dict load finished, time elapsed %d ms",
                    System.currentTimeMillis() - s));
        } catch (IOException e) {
            System.err.println(String.format(Locale.getDefault(), "%s load failure!", MAIN_DICT));
        } finally {
            try {
                if (null != br)
                    br.close();
            } catch (IOException e) {
                System.err.println(String.format(Locale.getDefault(), "%s close failure!", MAIN_DICT));
            }
        }
    }


    private String addWord(String word) {
        if (null != word && !"".equals(word.trim())) {
            String key = word.trim().toLowerCase(Locale.getDefault());
            _dict.fillSegment(key.toCharArray());
//            System.out.println(key.toCharArray());
            return key;
        } else
            return null;
    }

    public DictSegment getTrie() {
        return this._dict;
    }

    public boolean containsWord(String word) {
        return freqs.containsKey(word);
    }


}
