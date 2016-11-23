package xm.demo.transientdemo;

import java.io.Serializable;

/**
 * @author xuming
 */
public class User implements Serializable {
    private String name;
    private transient String pwd;
    public User(String name, String pwd) {
        this.name = name;
        this.pwd = pwd;
    }
    public String toString() {
        return "name=" + name + ",pwd=" + pwd;
    }
}
