package xm.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by xuming on 2016/7/21.
 */
public class MethodDemo {
    public static void main(String[] args) throws Exception {
        Class<?> cls = Class.forName("com.xm.reflect.Person"); // 取得Class对象
        Method met[] = cls.getMethods(); // 取得全部方法
        for (int x = 0; x < met.length; x++) {
            System.out.println(met[x]);
        }

        Object obj = cls.newInstance(); // 实例化对象，没有向Person转型
        String attribute = "name"; // 要调用类之中的属性
        Method setMet = cls.getMethod("set"+ initcap(attribute), String.class);// setName()
        Method getMet = cls.getMethod("get"+ initcap(attribute));// getName()
        setMet.invoke(obj, "张三") ; // 等价于：Person对象.setName("张三")
        System.out.println(getMet.invoke(obj));// 等价于：Person对象.getName()

        Field nameField = cls.getDeclaredField("name") ; // 找到name属性
        nameField.setAccessible(true) ; // 解除封装了
        nameField.set(obj, "张三22") ; // Person对象.name = "张三22"
        System.out.println(nameField.get(obj)); // Person对象.name
    }
    public static String initcap(String str) {
        return str.substring(0,1).toUpperCase().concat(str.substring(1)) ;
    }

}
