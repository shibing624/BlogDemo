package xm.thread;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuming on 2016/7/8.
 */
class CustomThread extends Thread {
    CustomThread1 t1;

    public CustomThread(CustomThread1 t1) {
        this.t1 = t1;
    }

    public void run() {
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + " start.");
        try {
            for (int i = 0; i < 3; i++) {
                System.out.println(threadName + " loop at " + i);
                JoinTestDemo.numlist.add(i);
                Thread.sleep(1000);
            }
            t1.join();
            System.out.println(threadName + " end.");
        } catch (Exception e) {
            System.out.println("Exception from " + threadName + ".run");
        }
    }
}


class CustomThread1 extends Thread {

    public void run() {
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + " start.");
        try {
            for (int i = 0; i < 5; i++) {
                System.out.println(threadName + " loop at " + i);
                Thread.sleep(1000);
                JoinTestDemo.numlist.add(i);
            }
            System.out.println(threadName + " end.");
        } catch (Exception e) {
            System.out.println("Exception from " + threadName + ".run");
        }
    }
}

class JoinTestDemo {
    public static List<Integer> numlist = new ArrayList<>();
    public static void main(String[] args) {
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + " start.");
        CustomThread1 t1 = new CustomThread1();
        CustomThread t = new CustomThread(t1);
        try {
            t1.start();
            Thread.sleep(2000);
            t.start();
            t1.join();
            t.join(); //在代碼2里，將此處注釋掉
        } catch (Exception e) {
            System.out.println("Exception from main");
        }
        System.out.println(numlist.size() + " :size");
        System.out.println(threadName + " end!");
    }
}