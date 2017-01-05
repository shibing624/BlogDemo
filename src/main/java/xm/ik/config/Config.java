package xm.ik.config;

import java.util.List;

/**
 * configuration
 *
 * @author xuming
 */
public interface Config {
    boolean useSmart();

    void setUseSmart(boolean useSmart);

    String getMainDictionary();

    String getQuantifierDictionary();

    List<String> getExtDictionarys();

    List<String> getExtStopWordDictionarys();
}
