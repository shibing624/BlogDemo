package xm.math.bit;

import java.util.Arrays;

/**
 * 树状数组
 * Created by xuming on 2016/8/5.
 */
public class BIT {

    static int[] sumArray = new int[8];

    static int[] arr = new int[8];

    public static void main(String[] args) {
        initBIT();

        System.out.println("A数组的值:" + Arrays.toString(arr));
        System.out.println("S数组的值:"+ Arrays.toString(sumArray));

        System.out.println("修改A[1]的值为3");
        Modify(1, 3);

        System.out.println("A数组的值:"+ Arrays.toString(arr));
        System.out.println("S数组的值:"+Arrays.toString( sumArray));
    }

    // 初始化两个数组
    public static void initBIT() {
        for (int i = 1; i <= 8; i++) {
            arr[i - 1] = i;
            //设置其实坐标：i=1开始
            int start = (i - Lowbit(i));
            int sum = 0;
            while (start < i) {
                sum += arr[start];
                start++;
            }
            sumArray[i - 1] = sum;
        }
    }

    public static void Modify(int x, int newValue) {
        //拿出原数组的值
        int oldValue = arr[x];
        arr[x] = newValue;
        for (int i = x; i < arr.length; i += Lowbit(i + 1)) {
            //减去老值，换一个新值
            sumArray[i] = sumArray[i] - oldValue + newValue;
        }
    }

    // 求前n项和
    public static int Sum(int x) {
        int ans = 0;
        int i = x;
        while (i > 0) {
            ans += sumArray[i - 1];
            //当前项的最大子树
            i -= Lowbit(i);
        }

        return ans;
    }

    // 当前的sum数列的起始下标
    public static int Lowbit(int i) {
        return i & -i;
    }

}
