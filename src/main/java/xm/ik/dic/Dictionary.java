package xm.ik.dic;

import xm.ik.cfg.Config;

/**
 * dictionary manager, single
 * @author xuming
 */
public class Dictionary {
    private static Dictionary singleton;
    private DictionarySegment mainDictionary;
    private DictionarySegment stopWordDictionary;
    private DictionarySegment quantifierDictionary;
    private Config config;
    private Dictionary(Config config){
        this.config = config;
//        this.loadMainDictionary();
//        this.loadStopWordDictionary();
//        this.loadQuantifierDictionary();
    }
}
