package xm.set;

import org.junit.Test;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * HashMap 其实是一个线性的数组实现的，那么如何实现键值对来存取数据？
 * 通过静态内部类Entry（key,value,next）实现，这个线性数组就是Entry[],map里面的内容保存在Entry[]里面
 *
 * @author xuming
 */
public class HashMapDemo {
    @Test
    public void test() {
        HashMap map = new HashMap();
        map.put("key1", "value1");
        map.put("key2", "value1");
        map.put("key3", "value1");
        map.put("key4", "value1");
        map.put("key3", "value2");
        map.put("key5", "value1");
        String value3 = (String) map.get("key3");
        System.out.println(value3);

        int i = 8 & (15 - 1);
        int b = 9 & (15 - 1);

    }

    @Test
    public void testIdentitymap() {
        IdentityHashMap<String, String> map = new IdentityHashMap<String, String>();
        map.put("key1", "value1");
        map.put("key2", "value1");
        map.put("key3", "value1");
        map.put("key3", "value2");
        map.put("key5", "value1");
        String value3 = (String) map.get("key3");
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            if (key.equals("key3"))
                System.out.println((String) entry.getValue());
        }

        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getKey().equals("key3"))
                System.out.println("new: " + entry.getValue());
        }

        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

    }

    @Test
    public void testIdentitySame() {
        IdentityHashMap<String, Object> map = new IdentityHashMap<String, Object>();
        String fsString = new String("xx");
        String secString = new String("xx");
        String tString = new String("key3");
        map.put(fsString, "first");
        map.put(secString, "second");
        map.put("key3", "value1");
        map.put(tString, "value2");
        map.put("key5", "value1");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "    " + entry.getValue());
        }
        System.out.println("idenMap=" + map.containsKey(fsString));
        System.out.println("idenMap=" + map.get(fsString));

        System.out.println("idenMap=" + map.containsKey(secString));
        System.out.println("idenMap=" + map.get(secString));
    }
}
