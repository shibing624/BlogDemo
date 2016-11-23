package xm.thread;

import java.util.concurrent.Callable;

/**
 * @author xuming
 */
public class MyCallable implements Callable {

    private String oid;

    MyCallable(String oid) {
        this.oid = oid;
    }

    @Override
    public Object call() throws Exception {
        System.out.println(Thread.currentThread().getName()+" start ...");
        Thread.sleep(1000);
        System.out.println(Thread.currentThread().getName() + " end ...");
        return oid+"任务返回的内容";
    }
}
