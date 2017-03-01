package xm.rpc.client;

/**
 * 定义服务接口
 * @author xuming
 */
public interface HelloService {
    String hello(String name);
    String segment(String str);
}
