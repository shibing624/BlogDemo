package xm.transientdemo;


import java.io.*;

/**
 * @author xuming
 */
public class UserTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        User user = new User("lili", "123321");
        System.out.println(user);
        // 序列化，transient的属性没有被序列化
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File("data/user.db")));
        objectOutputStream.writeObject(user);
        objectOutputStream.close();

        // 重新读取内容
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File("data/user.db")));
        User readUser = (User) in.readObject();
        // 读取后pwd的内容为null
        System.out.println(readUser.toString());
    }

}