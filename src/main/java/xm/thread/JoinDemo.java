package xm.thread;

/**
 * @author xuming
 */
public class JoinDemo implements Runnable{
    @Override
    public void run() {

        try {
            System.out.println(Thread.currentThread().getName() + " start-----");
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + " end------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i=0;i<5;i++) {
            Thread test = new Thread(new JoinDemo());
            test.start();
//            test.join();
        }


        System.out.println("Finished~~~");
    }
}
