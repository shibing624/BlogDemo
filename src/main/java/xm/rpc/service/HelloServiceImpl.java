package xm.rpc.service;

import org.ansj.domain.Result;
import org.ansj.splitWord.analysis.ToAnalysis;
import xm.rpc.client.HelloService;

/**
 * 实现服务接口
 *
 * @author xuming
 */
@RpcService(HelloService.class)
public class HelloServiceImpl implements HelloService {
    public String hello(String name) {
        return "hello! " + name;
    }
    public String segment(String str){
        Result result = ToAnalysis.parse(str);
        return result.toString();
    }
}
