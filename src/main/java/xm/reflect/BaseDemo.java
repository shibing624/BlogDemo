package xm.reflect;

public class BaseDemo {
    public static void main(String[] args) throws Exception {
        Person per = new Person(); // 正着操作
        System.out.println(per.getClass().getName()); // 反着来
    }
}
