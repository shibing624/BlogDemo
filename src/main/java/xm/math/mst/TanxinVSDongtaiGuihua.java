package xm.math.mst;


public class TanxinVSDongtaiGuihua {
    /*********************************************************
     *问  题：有最小面额为 11 5 1的三种人民币，用最少的张数找钱
     *描  述：动态规划与贪心算法 解决问题 比较
     */
    static int N = 4;
    static int VALUE1 = 11;                  //面额为 11元的人民币 （可以修改）
    static int VALUE2 = 5;                   //面额为  5元的人民币 （可以修改）
    static int VALUE3 = 1;                   //面额为  1元的人民币 （不要修改，不然会有找不开的情况）
    static int MAX_MONEY = 1000;            //能找开的钱的上限

//**************************动态规划法********************************
//    static int Num[1000];                  //Num[i]保存要找开 i 元钱，需要的最小人民币张数
//    int Num_Value[N][MAX_MONEY];         //Num_Value[i][j] 表示 要找 j元钱，需要面额 VALUEi 的人民币张数
//    Num[i]=i;          0<=i <=4
//    Num[i]=min(Num[i-VALUE1]+1, Num[i-VALUE2]+1, Num[i-VALUE3]+1)

    //-------------------------求最小值---------------------------------
    static int min(int a, int b, int c) {
        return a < b ? (a < c ? a : c) : (b < c ? b : c);
    }

    //-------------------------求最优值---------------------------------
    static int DP_Money(int money, int Num[]) {
        //获得要找开money元钱，需要的人民币总张数
        int i;
        for (i = 0; i <= VALUE2; i++) {                               //0~4 全用 1元
            Num[i] = i;
        }
        for (i = VALUE2; i <= money; i++) {                           //从5元开始 凑钱
            if (i - VALUE1 >= 0) {                                //如果比 11 元大，说明多了一种用11元面额人民币的可能
                //从用 11元、5元、1元中 选择一个张数小的
                Num[i] = min(Num[i - VALUE1] + 1, Num[i - VALUE2] + 1, Num[i - VALUE3] + 1);
            } else {                                            //从5元、1元中 选择一个张数小的
                Num[i] = Math.min(Num[i - VALUE2] + 1, Num[i - VALUE3] + 1);
            }
        }
        return Num[money];
    }

    //-------------------------求最优解---------------------------------
    static void BestChoice(int money, int Num[], int Num_Value[][]) {
        //要找 money 元钱，总人民币张数放在Num[money]中
        //Num[1~3][money]分别保存三种面额的张数
        int i;
        for (i = 0; i < VALUE2; i++) {
            Num_Value[1][i] = 0;
            Num_Value[2][i] = 0;
            Num_Value[3][i] = i;
        }
        for (i = VALUE2; i <= money; i++) {
            if ((i >= VALUE1) && (Num[i] == (Num[i - VALUE1] + 1))) {   //i 是由 i-11+11 构成  即i元是由 i-11元 加上一张面额11元的人民币构成
                Num_Value[1][i] = Num_Value[1][i - VALUE1] + 1;     //多一张 11元面额人民币
                Num_Value[2][i] = Num_Value[2][i - VALUE1];       // 5元面额人民币 张数一样多
                Num_Value[3][i] = Num_Value[3][i - VALUE1];       // 1元面额人民币 张数一样多
            } else if (Num[i] == (Num[i - VALUE2] + 1)) {               //i 是由 i-5+5 构成
                Num_Value[1][i] = Num_Value[1][i - VALUE2];       //11元面额人民币 张数一样多
                Num_Value[2][i] = Num_Value[2][i - VALUE2] + 1;     //多一张 5元面额人民币
                Num_Value[3][i] = Num_Value[3][i - VALUE2];       // 1元面额人民币 张数一样多
            } else if (Num[i] == (Num[i - VALUE3] + 1)) {               //i 是由 i-1+1 构成
                Num_Value[1][i] = Num_Value[1][i - VALUE3];       //11元面额人民币 张数一样多
                Num_Value[2][i] = Num_Value[2][i - VALUE3];       // 5元面额人民币 张数一样多
                Num_Value[3][i] = Num_Value[3][i - VALUE3] + 1;     //多一张 1元面额人民币
            }
        }
    }

    /***************************贪心算法********************************
     *方法：
     *     Num_Value[i]表示 面额为VALUEi 的人民币用的张数
     *     能用大面额的人民币，就尽量用大面额
     */
    static int Greed(int money, int Num_Value[]) {
        //要找开 money元人民币，Num_Value[1~3]保存 三种面额人民币的张数
        int total = 0;                                            //总张数，返回值也即是总张数。
        Num_Value[1] = 0;
        Num_Value[2] = 0;
        Num_Value[3] = 0;
        for (int i = money; i >= 1; ) {
            if (i >= VALUE1) {
                Num_Value[1]++;
                i -= VALUE1;
                total++;
            } else if (i >= VALUE2) {
                Num_Value[2]++;
                i -= VALUE2;
                total++;
            } else if (i >= VALUE3) {
                Num_Value[3]++;
                i -= VALUE3;
                total++;
            } else {
            }
        }
        return total;
    }

    public static void main(String[] args) {
        int money = 15;
        int Num[] = new int[1000];                  //Num[i]保存要找开 i 元钱，需要的最小人民币张数
        int Num_Value[][] = new int[4][1000];         //Num_Value[i][j] 表示 要找 j元钱，需要面额 VALUEi 的人民币张数
        System.out.println("-------------------------------------------取15块钱人民币，最优解的总张数:");
        System.out.println(DP_Money(money, Num));
        BestChoice(money, Num, Num_Value);
        System.out.println("-------------------------------------------");
        for (int i = 0; i <= money; i++) {
            System.out.printf("Num[%d]=%4d, %3d, %3d, %3d\n", i, Num[i], Num_Value[1][i], Num_Value[2][i], Num_Value[3][i]);
        }


        System.out.println("-------------------------------------------测试 贪心算法");
        //测试 贪心算法
        int Num_Value_Greed[] = new int[4];
        for (int j = 0; j <= 40; j++) {                 //从0 元到 40 元的每一个找钱方式
            Greed(j, Num_Value_Greed);
            System.out.printf("%d---->>> %d,%d,%d\n", j, Num_Value_Greed[1], Num_Value_Greed[2], Num_Value_Greed[3]);
        }


        System.out.println("-------------------------------------------比较两个算法");
        //比较两个算法
        int dp, grd;                         //分别保存动态规划法和贪心算法得到的人民币总张数
        int money1;                          //要找的钱
        int Num1[] = new int[1000];                 //Num[i]保存要找i花费的银币的数目
        int Num_Value1[][] = new int[4][1000];       //Num_Value[i][j] 表示 要找 j 花费的 面值为 VALUEi 的硬币 的数目
        int Num_Value_Greed1[] = new int[4];             //Num_Value_Greed[i] 表示 面值为VALUEi 的人民币 数目
        money1 = 15;                         //可以为任意非负整型值（15 元是一个比较典型的可以区分两种算法的值）
        dp = DP_Money(money1, Num1);          //动态规划法
        BestChoice(money1, Num1, Num_Value1);
        grd = Greed(money1, Num_Value_Greed1); //贪心算法
        System.out.printf("要找的钱 为：%d\n\n", money1);
        System.out.printf("  算法    张数  11元  5元  1元\n");
        System.out.printf("动态规划  %-4d  %-4d  %-3d  %-3d\n", dp, Num_Value1[1][money1], Num_Value1[2][money1], Num_Value1[3][money1]);
        System.out.printf("贪心算法  %-4d  %-4d  %-3d  %-3d\n", grd, Num_Value_Greed1[1], Num_Value_Greed1[2], Num_Value_Greed1[3]);
    }
}
