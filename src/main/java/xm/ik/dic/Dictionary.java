package xm.ik.dic;

import org.ansj.dic.DicReader;
import xm.ik.config.Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.List;

/**
 * dictionary manager, single
 *
 * @author xuming
 */
public class Dictionary {
    private static Dictionary singleton;
    private DictionarySegment mainDictionary;
    private DictionarySegment stopWordDictionary;
    private DictionarySegment quantifierDictionary;
    private Config config;

    private Dictionary(Config config) {
        this.config = config;
        this.loadMainDictionary();
        this.loadStopWordDictionary();
        this.loadQuantifierDictionary();
    }

    public static Dictionary init(Config config) {
        if (singleton == null) {
            synchronized (Dictionary.class) {
                if (singleton == null) {
                    singleton = new Dictionary(config);
                    return singleton;
                }
            }
        }
        return singleton;
    }

    public static Dictionary getSingleton() {
        if (singleton == null) {
            throw new IllegalStateException("dictionary not load.");
        }
        return singleton;
    }

    public void addWords(Collection<String> words) {
        if (words != null) {
            words.stream()
                    .filter(word -> word != null)
                    .forEach(word -> singleton.mainDictionary.fillSegment(word.trim().toLowerCase().toCharArray()));
        }
    }

    public void disableWords(Collection<String> words) {
        if (words != null) {
            words.stream()
                    .filter(word -> word != null)
                    .forEach(word -> singleton.mainDictionary.disableSegment(word.trim().toLowerCase().toCharArray()));
        }
    }

    public Hit matchInMainDictionary(char[] charArray) {
        return singleton.mainDictionary.match(charArray);
    }

    public Hit matchInMainDictionary(char[] charArray, int begin, int length) {
        return singleton.mainDictionary.match(charArray, begin, length);
    }

    public Hit matchInQuantifierDictionary(char[] charArray, int begin, int length) {
        return singleton.quantifierDictionary.match(charArray, begin, length);
    }

    public Hit matchWithHit(char[] charArray, int currentIndex, Hit matchedHit) {
        DictionarySegment ds = matchedHit.getMatchedDictionarySegment();
        return ds.match(charArray, currentIndex, 1, matchedHit);
    }

    public boolean isStopWord(char[] charArray, int begin, int length) {
        return singleton.stopWordDictionary.match(charArray, begin, length).isMatch();
    }

    private void loadMainDictionary() {
        mainDictionary = new DictionarySegment((char) 0);
        InputStream is = DicReader.getInputStream(config.getMainDictionary());
        if (is == null)
            throw new RuntimeException("main dictionary not found.");
        long count = 0;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"), 512);
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().equals("")) {
                    mainDictionary.fillSegment(line.trim().toLowerCase().toCharArray());
                    count++;
                }
            }
            System.out.println("main dictionary load finish: " + config.getMainDictionary() + "; count: " + count);
        } catch (IOException e) {
            System.err.printf("main dictionary loading exception." + e);
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        loadExtDictionary();
    }

    private void loadExtDictionary() {
        List<String> extDicts = config.getExtDictionarys();
        if (extDicts != null) {
            for (String extdict : extDicts) {
                InputStream is = DicReader.getInputStream(extdict);
                if (is == null)
                    continue;
                long count = 0;
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"), 512);
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (!line.trim().equals("")) {
                            mainDictionary.fillSegment(line.trim().toLowerCase().toCharArray());
                            count++;
                        }
                    }
                    System.out.println("extension dictionary load finish: " +
                            extdict + "; count: " + count);
                } catch (IOException e) {
                    System.err.printf("extension dictionary loading exception." + e);
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void loadStopWordDictionary() {
        stopWordDictionary = new DictionarySegment((char) 0);
        List<String> extStopWordDicts = config.getExtStopWordDictionarys();
        if (extStopWordDicts != null && extStopWordDicts.size() > 0) {
            for (String extStopWordDict : extStopWordDicts) {
                InputStream is = DicReader.getInputStream(extStopWordDict);
                if (is == null)
                    continue;
                long count = 0;
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"), 512);
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (!line.trim().equals("")) {
                            stopWordDictionary.fillSegment(line.trim().toLowerCase().toCharArray());
                            count++;
                        }
                    }
                    System.out.println("extension stop word dictionary load finish: " +
                            extStopWordDict + "; count: " + count);
                } catch (IOException e) {
                    System.err.printf("extension stop word dictionary loading exception." + e);
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void loadQuantifierDictionary() {
        quantifierDictionary = new DictionarySegment((char) 0);
        if (config.getQuantifierDictionary() != null) {
            InputStream is = DicReader.getInputStream(config.getQuantifierDictionary());
            if (is == null)
                throw new RuntimeException("Quantifier Dictionary not found.");
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"), 512);
                String line;
                while ((line = br.readLine()) != null) {
                    if (!line.trim().equals(""))
                        quantifierDictionary.fillSegment(line.trim().toLowerCase().toCharArray());
                }
            } catch (IOException e) {
                System.err.printf("quantifier dictionary loading exception." + e);
            } finally {
                try {
                    if (is != null)
                        is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
