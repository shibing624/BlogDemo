package xm.ik.cfg;

import org.ansj.dic.DicReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Properties;

/**
 * config impl
 * @author xuming
 */
public class DefaultConfig implements Config {
    private static final String MAIN_DICT_PATH = "main.dic";
    private static final String QUANTIFIER_DICT_PATH = "quantifier.dic";
    private static final String FILE_NAME = "IKAnalyzer.cgf.xml";
    private static final String EXT_DICT = "ext_dict";
    private static final String EXT_STOP = "ext_stopwords";
    private Properties properties;
    private boolean useSmart;
    public static Config getInstance(){
        return new DefaultConfig();
    }
    private DefaultConfig(){
        properties = new Properties();
        InputStream inputStream = DicReader.getInputStream(FILE_NAME);
        if(inputStream!=null){
            try {
                properties.loadFromXML(inputStream);
            } catch (InvalidPropertiesFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public boolean useSmart() {
        return useSmart;
    }

    @Override
    public void setUseSmart(boolean useSmart) {
        this.useSmart = useSmart;
    }

    @Override
    public String getMainDictionary() {
        return MAIN_DICT_PATH;
    }

    @Override
    public String getQuantifierDictionary() {
        return QUANTIFIER_DICT_PATH;
    }

    @Override
    public List<String> getExtDictionarys() {
        List<String> list = new ArrayList<>(2);
        String dicStrs = properties.getProperty(EXT_DICT);
        if(dicStrs != null){
            String[] files = dicStrs.split(";");
            if(files !=null){
                for(String i:files)
                    if (i != null && !"".equals(i.trim())) {
                        list.add(i.trim());
                    }
            }
        }
        return list;
    }

    @Override
    public List<String> getExtStopWordDictionarys() {
        List<String> list = new ArrayList<>(2);
        String dicStrs = properties.getProperty(EXT_STOP);
        if(dicStrs != null){
            String[] files = dicStrs.split(";");
            if(files !=null){
                for(String i:files)
                    if (i != null && !"".equals(i.trim())) {
                        list.add(i.trim());
                    }
            }
        }
        return list;
    }
}
