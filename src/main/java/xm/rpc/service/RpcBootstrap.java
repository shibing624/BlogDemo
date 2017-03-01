package xm.rpc.service;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * RPC服务启动入口
 * 请先打开zookeeper，使用 ZooKeeper 客户端命令行创建/registry 永久节点（create /registry data），用于存放所有的服务临时节点。
 *
 * @author xuming
 */
public class RpcBootstrap {
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("service/spring-zk-rpc-server.xml");
    }
}
