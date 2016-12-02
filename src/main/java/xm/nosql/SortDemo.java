package xm.nosql;

import redis.clients.jedis.Jedis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author xuming
 */
public class SortDemo {

    public static void main(String[] args) {
        Jedis jedis;
        jedis = new Jedis("127.0.0.1", 6379);
        jedis.flushDB();
        //jedis 排序
        //注意，此处的rpush和lpush是List的操作。是一个双向链表（但从表现来看的）
        jedis.del("b");//先清除数据，再加入数据进行测试
        jedis.rpush("b", "1");
        jedis.lpush("b", "6");
        jedis.lpush("b", "3");
        jedis.lpush("b", "9");
        try {
            File f = new File("data/test_sort.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "utf-8"));
            String readline;
            while ((readline = br.readLine()) != null) {
                jedis.lpush("b", readline.trim());
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(jedis.sort("b")); //[1, 3, 6, 9]  //输入排序后结果

        List<String> set = jedis.sort("b");
        set.forEach(System.out::println);
    }

}
