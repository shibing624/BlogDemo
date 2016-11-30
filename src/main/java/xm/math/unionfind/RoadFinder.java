package xm.math.unionfind;

import java.util.Scanner;

/**
 * 并查集的应用：道路畅通工程
 *
 * @author xuming
 */
public class RoadFinder {
    static int pre[] = new int[1000];

    //查找根节点
    static int find(int x) {
        int r = x;
        while (pre[r] != r)
            //返回根节点 r
            r = pre[r];
        int i = x;
        int j;
        //路径压缩
        while (i != r) {
            // 在改变上级之前用临时变量  j 记录下他的值
            j = pre[i];
            //把上级改为根节点
            pre[i] = r;
            i = j;
        }
        return r;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n, m, p1, p2, i, total, f1, f2;
        System.out.println("n:");
        n = scan.nextInt();
        //读入n，如果n为0，结束
        while (n != 0) {
            //刚开始的时候，有n个城镇，一条路都没有
            // 那么要修n-1条路才能把它们连起来
            total = n - 1;
            //每个点互相独立，自成一个集合，从1编号到n
            // 所以每个点的上级都是自己
            for (i = 1; i < n; i++) {
                pre[i] = i;
            }
            System.out.println("m:");
            m = scan.nextInt();
            //共有m条路
            while (m-- > 0) {
                //下面这段代码，其实就是join函数，只是稍作改动以适应题目要求
                System.out.println("p1:");
                p1 = scan.nextInt();
                System.out.println("p2:");
                p2 = scan.nextInt();
                //每读入一条路，看它的端点p1，p2是否已经在一个连通分支里了
                f1 = find(p1);
                f2 = find(p2);
                //如果是不连通的，那么把这两个分支连起来
                //分支的总数就减少了1，还需建的路也就减了1
                if (f1 != f2) {
                    pre[f2] = f1;
                    total--;
                }
                //如果两点已经连通了，那么这条路只是在图上增加了一个环 //对连通性没有任何影响，无视掉
            }
            //最后输出还要修的路条数
            System.out.println(total);
        }


    }
}
