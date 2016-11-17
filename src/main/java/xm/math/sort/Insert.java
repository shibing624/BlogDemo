package xm.math.sort;

/**
 * Created by xuming on 2016/6/30.
 * insert sort
 */
public class Insert {

    private static int[] insertSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return arr;
        }
        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j > 0; j--) {
                if (arr[j] < arr[j - 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;

                } else {
                    break;
                }
                mprint(arr, i, j);
            }
        }
        return arr;
    }


    public static void mprint(int a[], int n, int i) {
        for (int j = 0; j < 8; j++) {
            System.out.print(a[j] + " ");
        }
        System.out.println();
    }


    public static void main(String[] args) {
        System.out.println("----------------------\n insertSort: ");
        int[] arr = {2, 1, 8, 3, 4, 5, 6, 3};
        Insert insert = new Insert();
        insert.insertSort(arr);


    }


}
