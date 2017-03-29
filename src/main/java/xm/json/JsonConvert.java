package xm.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xuming
 */
public class JsonConvert {

    @Test
    public void testJson2Array() throws IOException {
        User user1 = new User();
        user1.setId(1);
        user1.setName("jad");
        user1.setRemark("man");
        user1.setDate(new Date());

        User user2 = new User();
        user2.setId(2);
        user2.setName("Lucyer");
        user2.setRemark("woman");
        user2.setDate(new Date());

        List<User> list = new ArrayList<>();
        list.add(user1);
        list.add(user2);

        JSONArray jsonArray = new JSONArray(list);
        System.out.println(jsonArray);


        ObjectMapper mapper = new ObjectMapper();
        // Convert object to JSON string
        String json1 = mapper.writeValueAsString(user1);
        System.out.println(json1);

        // Convert JSON string to Object
        User newUser = mapper.readValue(json1, User.class);
        System.out.println(newUser);

        Users users = new Users();
        users.name = "total";
        users.userList = list;

        // list convert to JSON string
        String json2 = mapper.writeValueAsString(users);
        System.out.println(json2);

        Users newUsers = mapper.readValue(json2, Users.class);
        System.out.println(newUsers);

        // parse json
        JSONObject jsonObject = new JSONObject(json2);
        String name = jsonObject.getString("name");
        JSONArray userListArray = jsonObject.getJSONArray("userList");

        for (int j = 0; j < userListArray.length(); j++) {
            JSONObject ob = userListArray.getJSONObject(j);
            System.out.println("id:" + ob.getInt("id") + "; name:" + ob.getString("name") + "; remark:" + ob.getString("remark"));

        }


    }
}
