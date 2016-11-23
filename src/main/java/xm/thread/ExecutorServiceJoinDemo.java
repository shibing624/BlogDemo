package xm.thread;

import java.util.concurrent.*;

/**
 * @author xuming
 */
public class ExecutorServiceJoinDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int threads = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(threads);
        for (int i = 0; i < 5; i++) {
            Callable c = new MyCallable(String.valueOf(i));
            Future f = executorService.submit(c);
            System.out.println("f " + f.get());
        }

        executorService.shutdown();
        System.out.println("finish.");
    }


}
