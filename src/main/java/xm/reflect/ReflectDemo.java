package xm.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射演示
 * Created by xuming on 2016/7/21.
 */
public class ReflectDemo {
    /**
     * 通过反射拷贝对象
     *
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public Object copy(Object object) throws Exception {
        Class<?> classType = object.getClass();
        Object objectCopy = classType.getConstructor(new Class[]{}).newInstance(new Object[]{});
        //获取对象的所有成员变量
        Field[] fields = classType.getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();//获得成员变量的名称
            String firstLetter = name.substring(0, 1).toUpperCase();//将属性的首字母变为大写

            String getMethodName = "get" + firstLetter + name.substring(1);
            String setMethodeName = "set" + firstLetter + name.substring(1);

            Method getMethod = classType.getMethod(getMethodName, new Class[]{});
            Method setMethod = classType.getMethod(setMethodeName, new Class[]{field.getType()});

            Object value = getMethod.invoke(object, new Object[]{});
            setMethod.invoke(objectCopy, new Object[]{value});
        }
        return objectCopy;
    }

    public static void main(String[] args) throws Exception {
        Person p = new Person("lili", 23);
        p.setId(1L);
        ReflectDemo test = new ReflectDemo();
        Person person = (Person) test.copy(p);
        System.out.println(person);
        System.out.println("ID:" + person.getId() + ",name:" + person.getName() + ",age:" + person.getAge());
    }
}


