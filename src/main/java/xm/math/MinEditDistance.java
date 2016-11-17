package xm.math;

/**
 * Created by xuming on 2016/6/16.
 */
public class MinEditDistance {

    public static void main(String[] args) {
        String str1 = "dcga";
        String str2 = "edcb";
        int dis = getEditDistance(str1, str2);
        System.out.println("str1:" + str1 + ";str2:" + str2 + "; the distance is :" + dis);

    }

    public static int getEditDistance(String str1, String str2) {
        int[][] martix =new int[str1.length()+1][str2.length()+1];
        //init boundary = 0
        for (int i = 0; i <= str1.length(); i++) {
            martix[i][0] = i;
        }
        for (int j = 0; j <= str2.length(); j++) {
            martix[0][j] = j;
        }

        //martix x
        for (int i = 1; i <= str1.length(); i++) {
            //martix y
            for (int j = 1; j <= str2.length(); j++) {
                //equal
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    martix[i][j] = martix[i - 1][j - 1];
                } else {
                    //get min value:leftfront vs below
                    int temp = Math.min(martix[i - 1][j], martix[i][j - 1]);
                    //get min martix
                    martix[i][j] = Math.min(temp, martix[i - 1][j - 1]) + 1;

                }
            }
        }
        int result = martix[str1.length()][str2.length()];
        return result;


    }


}
