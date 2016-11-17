package xm.thread;

/**
 * Created by xuming on 2016/7/8.
 */
public class JoinTest implements Runnable {

    public static int a = 0;

    public void run() {
        for (int k = 0; k < 5; k++) {
            a = a + 1;
        }
    }

    public static void main(String[] args) throws Exception {
//        base();
//        addjoin();
        addloop();

    }

    public static void base() {
        Runnable r = new JoinTest();
        Thread t = new Thread(r);
        t.start();
        System.out.println(a);
    }

    public static void addjoin() throws Exception {
        Runnable r = new JoinTest();
        Thread t = new Thread(r);
        t.start();
        t.join(); //加入join()
        System.out.println(a);
    }

    public static void addloop(){
        Runnable r = new JoinTest();
        Thread t = new Thread(r);
        t.start();
        //t.join(); //加入join()
            /*
             注意循环体内一定要有实际执行语句，否则编译器或JVM可能优化掉你的这段代码，视这段代
             码为无效。
            */
        for (int i=0; i<900; i++) {
            System.out.print(i+" ");
        }
        System.out.println();
        System.out.println(a);
    }
}