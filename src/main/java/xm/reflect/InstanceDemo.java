package xm.reflect;

/**
 * Created by xuming on 2016/7/21.
 */
public class InstanceDemo {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<?> cls = null; // 取得Class对象
        cls = Class.forName("com.xm.reflect.Person");
        System.out.println(cls.getName()); // 反着来

        Object obj = null; // 实例化对象，和使用关键字new一样
        obj = cls.newInstance();
        Person pers = (Person) obj; // 向下转型
        pers.setAge(33);
        System.out.println(pers + ";age:" + pers.getAge());
    }
}
