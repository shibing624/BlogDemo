package xm.reflect;

/**
 * 反射取class演示
 * Created by xuming on 2016/7/21.
 */
public class ClassDemo {
    public static void main(String[] args) throws Exception {
        Person per = new Person(); // 正着操作
        Class<?> cls = per.getClass(); // 取得Class对象
        System.out.println(cls.getName() + ";toString:" + cls.toString()); // 反着来

        Class<?> clss = Person.class; // 取得Class对象
        System.out.println(clss.getName()); // 反着来

        Class<?> cls1 = Class.forName("com.xm.reflect.Person"); // 取得Class对象
        System.out.println(cls1.getName()); // 反着来


        Object obj = cls.newInstance(); // 实例化对象，和使用关键字new一样
        Person pers = (Person) obj; // 向下转型
        System.out.println(pers);

    }
}
