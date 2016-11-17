package xm.math.slopeone;

import java.util.*;

/**
 * Created by xuming on 2016/6/21.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("****************");
        SlopeOne test = new SlopeOne();
        HashMap<Integer, List<Product>> userRating = new HashMap<Integer, List<Product>>();
        Product a = new Product() ;
        a.ProductID = 1;
        a.ProductName = "洗衣机";
        a.Score = 5;
        Product aa = new Product() ;
        aa.ProductID = 2;
        aa.ProductName = "电冰箱";
        aa.Score = 10;
        Product aaa = new Product() ;
        aaa.ProductID = 3;
        aaa.ProductName = "彩电";
        aaa.Score = 10;
        Product aaaa = new Product() ;
        aaaa.ProductID = 4;
        aaaa.ProductName = "空调";
        aaaa.Score = 5;
        //第一位用户
        List<Product> list = new ArrayList<Product>();
        list.add(a);
        list.add(aa);
        list.add(aaa);
        list.add(aaaa);
        userRating.clear();
        userRating.put(1000, list);
        test.AddUserRatings(userRating);
        //第二位用户
        List<Product> list2 = new ArrayList<Product>();
        Product a2 = new Product() ;
        a2.ProductID = 1;
        a2.ProductName = "洗衣机";
        a2.Score = 4;
        Product aa2 = new Product() ;
        aa2.ProductID = 2;
        aa2.ProductName = "电冰箱";
        aa2.Score = 5;
        Product aaa2 = new Product() ;
        aaa2.ProductID = 3;
        aaa2.ProductName = "彩电";
        aaa2.Score = 4;
        Product aaaa2 = new Product() ;
        aaaa2.ProductID = 4;
        aaaa2.ProductName = "空调";
        aaaa2.Score = 10;
        list2.add(a2);
        list2.add(aa2);
        list2.add(aaa2);
        list2.add(aaaa2);
        userRating.clear();
        userRating.put(2000, list2);
        test.AddUserRatings(userRating);

        //第三位用户
        List<Product> list3 = new ArrayList<Product>();
        Product a3 = new Product() ;
        a3.ProductID = 1;
        a3.ProductName = "洗衣机";
        a3.Score = 4;
        Product aa3 = new Product() ;
        aa3.ProductID = 2;
        aa3.ProductName = "电冰箱";
        aa3.Score = 10;
        Product aaaa3 = new Product() ;
        aaaa3.ProductID = 4;
        aaaa3.ProductName = "空调";
        aaaa3.Score = 5;
        list3.add(a3);
        list3.add(aa3);
        list3.add(aaaa3);
        userRating.clear();
        userRating.put(3000, list3);
        test.AddUserRatings(userRating);

        //那么我们预测userID=3000这个人对 “彩电” 的打分会是多少？
        Integer userID = 3000;
        List<Product> result = userRating.get(userID);
        HashMap<Integer, Float> predictions = test.Predict(result);
        for(Map.Entry<Integer, Float> user2 : predictions.entrySet()){
            Float value2 =  user2.getValue();
            Integer key2 = user2.getKey();
            System.out.println("ProductID= " + key2 + " Rating: " + value2);

        }
    }
}
