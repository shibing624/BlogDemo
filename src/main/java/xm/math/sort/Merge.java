package xm.math.sort;

/**
 * Created by xuming on 2016/7/6.
 */
public class Merge {

    //recursive
    static void merge_sort_recursive(int[] arr, int[] reg, int start, int end) {
        if (start >= end)
            return;
        int len = end - start, mid = (len >> 1) + start;
        int start1 = start, end1 = mid;
        int start2 = mid + 1, end2 = end;
        merge_sort_recursive(arr, reg, start1, end1);
        merge_sort_recursive(arr, reg, start2, end2);
        int k = start;
        while (start1 <= end1 && start2 <= end2)
            reg[k++] = arr[start1] < arr[start2] ? arr[start1++] : arr[start2++];
        while (start1 <= end1)
            reg[k++] = arr[start1++];
        while (start2 <= end2)
            reg[k++] = arr[start2++];
        for (k = start; k <= end; k++) {
            arr[k] = reg[k];
            System.out.println(" " + arr[k]);
        }


    }

    public static void merge_sort(int[] arr) {
        int len = arr.length;
        int[] reg = new int[len];
        merge_sort_recursive(arr, reg, 0, len - 1);
    }


    //verctor
    public static int[] mergesort(int[] arr) {
        int len = arr.length;
        int[] result = new int[len];
        int block, start;

        for (block = 1; block < len; block *= 2) {
            for (start = 0; start < len; start += 2 * block) {
                int low = start;
                int mid = (start + block) < len ? (start + block) : len;
                int high = (start + 2 * block) < len ? (start + 2 * block) : len;
                //两个块的起始下标及结束下标
                int start1 = low, end1 = mid;
                int start2 = mid, end2 = high;
                //开始对两个block进行归并排序
                while (start1 < end1 && start2 < end2) {
                    result[low++] = arr[start1] < arr[start2] ? arr[start1++] : arr[start2++];
                }
                while (start1 < end1) {
                    result[low++] = arr[start1++];
                }
                while (start2 < end2) {
                    result[low++] = arr[start2++];
                }
            }
            int[] temp = arr;
            arr = result;
            result = temp;
        }
        result = arr;
        return result;
    }


    public static void main(String[] args) {
        int a = 16;

        int b = a >> 1;
        System.out.println(b);
        System.out.println("----------------------\n merge Sort: ");
        int[] arr = {2, 1, 8, 3, 4, 5, 6, 3};
        merge_sort(arr);
        int[] result = mergesort(arr);
        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i]);
        }
    }
}
