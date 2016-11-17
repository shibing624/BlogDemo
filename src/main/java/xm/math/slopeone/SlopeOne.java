

package xm.math.slopeone;

import java.util.*;

/**
 * Created by xuming on 2016/6/20.
 * Slope One 算法
 */
public class SlopeOne {
    /// <summary>
    /// 评分系统
    /// </summary>
    public static Map<Integer, Product> dicRatingSystem = new HashMap<>();
    public Map<String, Rating> dic_Martix = new HashMap<>();
    public HashSet<Integer> hash_items = new HashSet<Integer>();
    /// <summary>
    /// 接收一个用户的打分记录
    /// </summary>
    /// <param name="userRatings"></param>
    public void AddUserRatings(HashMap<Integer, List<Product>> userRatings) {
        for (Map.Entry<Integer, List<Product>> user1 : userRatings.entrySet()) {
            List<Product> value = user1.getValue();
            for (Product item1 : value) {
                //该产品的编号（具有唯一性）
                int item1Id = item1.ProductID;
                //该项目的评分
                float item1Rating = item1.Score;
                //将产品编号字存放在hash表中
                hash_items.add(item1.ProductID);
                for (Map.Entry<Integer, List<Product>> user2 : userRatings.entrySet()) {
                    List<Product> value2 = user2.getValue();
                    for (Product item2 : value2) {
                        //过滤掉同名的项目
                        if (item2.ProductID <= item1Id)
                            continue;
                        //该产品的名字
                        int item2Id = item2.ProductID;
                        //该项目的评分
                        float item2Rating = item2.Score;
                        Rating ratingDiff;
                        //用表的形式构建矩阵
                        String key = Tools.GetKey(item1Id, item2Id);
                        //将俩俩 Item 的差值 存放到 Rating 中
                        if (dic_Martix.keySet().contains(key))
                            ratingDiff = dic_Martix.get(key);
                        else {
                            ratingDiff = new Rating();
                            dic_Martix.put(key, ratingDiff);
                        }
                        //方便以后以后userrating的编辑操作，（add)
                        if (!ratingDiff.hash_user.contains(user1.getKey())) {
                            //value保存差值
                            ratingDiff.Value += item1Rating - item2Rating;

                            //说明计算过一次
                            ratingDiff.Freq += 1;
                        }
                        //记录操作人的ID，方便以后再次添加评分
                        ratingDiff.hash_user.add(user1.getKey());
                    }
                }
            }
        }
    }

    /// <summary>
    /// 根据矩阵的值，预测出该Rating中的值
    /// </summary>
    /// <param name="userRatings"></param>
    /// <returns></returns>
    public HashMap<Integer, Float> Predict(List<Product> userRatings) {
        HashMap<Integer, Float> predictions = new HashMap<Integer, Float>();
        List<Integer> productIDs = new ArrayList<Integer>();
        userRatings.forEach(i -> productIDs.add(i.ProductID));//lambda
        //循环遍历_Items中所有的Items
        for (Integer itemId : this.hash_items) {
            //过滤掉不需要计算的产品编号
            if (productIDs.contains(itemId))
                continue;
            Rating itemRating = new Rating();
            // 内层遍历userRatings
            for (Product userRating : userRatings) {
                if (userRating.ProductID == itemId)
                    continue;
                int inputItemId = userRating.ProductID;
                //获取该key对应项目的两组AVG的值
                String key = Tools.GetKey(itemId, inputItemId);
                if (dic_Martix.keySet().contains(key)) {
                    Rating diff = dic_Martix.get(key);
                    //关键点：运用公式求解（这边为了节省空间，对角线两侧的值呈现奇函数的特性）
                    itemRating.Value += diff.Freq * (userRating.Score + diff.AverageValue * ((itemId < inputItemId) ? 1 : -1));
                    //关键点：运用公式求解 累计每两组的人数
                    itemRating.Freq += diff.Freq;
                }
            }
            predictions.put(itemId, itemRating.AverageValue=(itemRating.Value/itemRating.Freq));
        }
        return predictions;
    }
}





