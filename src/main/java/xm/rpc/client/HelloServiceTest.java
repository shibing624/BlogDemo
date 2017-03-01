package xm.rpc.client;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author xuming
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:client/spring-zk-rpc-client.xml")
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