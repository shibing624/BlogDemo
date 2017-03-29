package xm.json;

import java.util.List;

/**
 * @author xuming
 */
public class Users {
    public String name;
    public List<User> userList;

    @Override
    public String toString() {
        return name + "/" + userList.size() + "/" + userList.get(0);
    }
}
