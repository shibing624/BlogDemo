package xm.rpc.client;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xuming
 */
@Service
public class HelloServiceTest {
    @Autowired
    private RpcProxy rpcProxy;

    @Test
    public void helloTest() {
        HelloService helloService = rpcProxy.create(HelloService.class);
        String result = helloService.hello("World");
        System.out.println(result + "2");
        Assert.assertEquals("hello! World", result);
    }

    @Test
    public void segmentTest() {
        HelloService helloService = rpcProxy.create(HelloService.class);
        String segmentResult = helloService.hello("我爱自然语言处理，结婚和尚未结婚的丽丽小姐姐");
        System.out.println(segmentResult);
    }

}