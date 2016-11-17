package xm.math;

import redis.clients.jedis.Jedis;


/**
 * Created by xuming on 2016/6/29.
 */
public class JedisDemo {

    public static void main(String[] args) {
//连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost");
        System.out.println("Connection to server sucessfully");
//        jedis.auth("foobared");

        jedis.set("foo", "bar");
        String value = jedis.get("foo");
        System.out.println("value:"+value);
        jedis.set("foo1", "bar1");
        String value1 = jedis.get("foo1");

        System.out.println("value:"+value1);
    }
}
