package xm.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by xuming on 2016/7/21.
 */
public class ConstructDemo {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class<?> cls = Class.forName("com.xm.reflect.Person"); // 取得Class对象
        Constructor<?> cons[] = cls.getConstructors(); // 取得全部构造
        for (int x = 0; x < cons.length; x++) {
            System.out.println(cons[x]);
        }

        Object obj = cls.newInstance(); // 实例化对象
        System.out.println(obj);

        Constructor<?> cons1 = cls.getConstructor(String.class,int.class) ;
        Object obj1 = cons1.newInstance("张三", 20); // 为构造方法传递参数
        System.out.println(obj1);

    }
}
