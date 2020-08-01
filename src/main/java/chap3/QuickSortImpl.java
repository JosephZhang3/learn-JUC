package chap3;

/**
 * 快速排序
 *
 * @author jianghao.zhang
 */
public class QuickSortImpl {

    public static void main(String[] args) {
        int[] array = {4, 6, 1, 3, 7, 5, 8, 2, 54, 372, 6456, 4745645, 654};
        QuickSort impl = new QuickSort();
        int[] a = impl.exec(array, 13);
        for (int i = 0; i < 13; i++)
            System.out.println(a[i]);
    }

}

class QuickSort {
    public int[] exec(int[] arr, int n) {
        sort(arr, 0, n - 1);
        return arr;
    }

    public void sort(int[] arr, int low, int high) {
        if (low < high) {
            int n;
            // 基准点
            n = partition(arr, low, high);
            // 向左
            sort(arr, low, n - 1);
            // 向右
            sort(arr, n + 1, high);
        }
    }

    public int partition(int[] arr, int low, int high) {
        int key = arr[low];
        while (low < high) {
            while (low < high && arr[high] >= key) {
                high--;
            }
            arr[low] = arr[high];
            while (low < high && arr[low] <= key) {
                low++;
            }
            arr[high] = arr[low];
        }
        arr[low] = key;
        return low;
    }
}