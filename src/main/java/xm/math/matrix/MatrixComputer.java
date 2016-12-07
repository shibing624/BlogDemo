package xm.math.matrix;

import java.util.Arrays;

/**
 * @author xuming
 */
public class MatrixComputer {
    public static void main(String[] args) {
        MatrixComputer md = new MatrixComputer();
        Integer[][] M = new Integer[6][7];
        M[0][1] = 12; M[0][2] = 9;
        M[2][0] = -3; M[2][5] = 14;
        M[3][2] = 24; M[4][1] = 18;
        M[5][0] = 15; M[5][3] = -7;

        //md.compreMatrix(M);

        Integer[][] m = new Integer[3][4];
        m[0][0]=3;m[0][3]=5;
        m[1][1]=-1;m[2][0]=2;
        Integer[][] n = new Integer[4][2];
        n[0][0]=5;n[0][1]=2;  n[1][0]=1;
        n[2][0]=-2; n[2][1]=4;
        md.multiMatrix(m, n);
        System.out.println();
        md.multiMatrix(md.compreMatrix(m), md.compreMatrix(n));
    }


    /**
     * 把稀疏矩阵进行压缩
     */
    public Matrix compreMatrix(Integer[][] e){
        Matrix matrix = new Matrix();
        int mu = e.length;
        int nu = e[0].length;
        for(int i=0;i<mu;i++){
            for(int j=0;j<nu;j++){
                if(e[i][j] != null){    //压缩非空元素
                    Triple<Integer> triple = new Triple<Integer>(i,j,e[i][j]);
                    matrix.add(triple);
                }
            }
        }
        matrix.mu = mu;
        matrix.nu = nu;

        //下面是查看元素
        for(int i=0;i<matrix.tu;i++){
            Triple<Integer> t = matrix.data[i];
            System.out.print(t.i + " " + t.j + " " + t.integer);
            System.out.println();
        }
        System.out.println("----------");
//      transMatrix(matrix);
//      transMatrix2(matrix);
        return matrix;
    }

    /**
     * 压缩矩阵的转置，方法一
     * 原理：1.将矩阵的行列对应的值相互交换
     *       2.将矩阵的行列下标交换
     *       3.重新排列新矩阵的顺序(按行排，即行中非空元素出现的次序)
     */
    public Matrix transMatrix(Matrix m){
        Matrix t = new Matrix();    //转置后的矩阵
        t.mu = m.nu;
        t.nu = m.mu;

        if(m.tu == 0) return null;
        for(int col=0; col<m.nu;col++){  //根据m的列的顺序排列转换矩阵的顺序
            for(int p=0;p<m.tu;p++){
                if(m.data[p].j == col){
                    Triple<Integer> triple = new Triple<Integer>(m.data[p].j, m.data[p].i, (Integer)m.data[p].integer);
                    t.add(triple);
                }
            }
        }
        System.out.println("矩阵置换方法一");
        for(int i=0;i<t.tu;i++){
            Triple<Integer> tr = t.data[i];
            System.out.print(tr.i + " " + tr.j + " " + tr.integer);
            System.out.println();
        }
        return t;
    }

    /**
     * 压缩矩阵的转置，方法二
     * 原理：欲求得转置后的矩阵t的顺序，可以先求得m的每一列的第一个非空元素在
     * t中的索引cpot[col]，并确定m每一列中的非空元素的个数num[col]
     * 公式很容易得到：1.cpot[0] = 0;
     *                2.cpot[col] = cpot[col - 1]+ num[col-1] (1<col<m.nu)
     */
    public Matrix transMatrix2(Matrix m){
        Matrix t = new Matrix();    //转置后的矩阵
        t.mu = m.nu;
        t.nu = m.mu;

        //num和cpot数组的初始化
        int[] num = new int[m.nu];
        for(int ti=0;ti<m.tu;ti++){  //num初始化
            num[m.data[ti].j]++;
        }
        int[] cpot = new int[m.nu];
        cpot[0] = 0;
        //求第col列中第一个非空元素在t中的位置
        for(int col=1;col<m.nu;col++){   //cpot初始化
            cpot[col] = cpot[col-1] + num[col - 1];
        }
        //进行转换
        for(int p=0;p<m.tu;p++){
            int col = m.data[p].j;
            int q = cpot[col];
            Triple<Integer> triple = new Triple<Integer>(m.data[p].j, m.data[p].i, (Integer)m.data[p].integer);
            t.add(q, triple);
            cpot[col]++;    //这个位置的下一个位置存储此列的下一个元素
        }
        //查看
        System.out.println("矩阵置换方法二");
        for(int i=0;i<t.tu;i++){
            Triple<Integer> tr = t.data[i];
            System.out.print(tr.i + " " + tr.j + " " + tr.integer);
            System.out.println();
        }
        return t;
    }

    /**
     * 两稀疏矩阵相乘。
     * 前提：1.矩阵元素能够相乘
     *       2.一个矩阵的列等于另一个矩阵的行
     * 原理：假设两矩阵M与N相乘，前提M的列M.col要等于N的行N.row(反之亦可)
     * 得到结果矩阵Q, Q.row=M.row, Q.col = N.col
     * 而且Q[i][j] += M[i][k] * N[k][j]  0<i<M.row,0<j<N.col,0<k<M.col或N.row
     */
    public Integer[][] multiMatrix(Integer[][] m, Integer[][] n){
        Integer[][] q = new Integer[m.length][n[0].length];
        for(int i=0;i<m.length;i++){
            for(int j=0;j<n[0].length;j++){
                int num = 0;
                for(int k=0;k<n.length;k++){
                    num += (m[i][k]==null?0:m[i][k]) * (n[k][j]==null?0:n[k][j]);
                }
                q[i][j] = num;
            }
        }
        //打印结果
        for(int i=0;i<q.length;i++){
            for(int j=0;j<q[0].length;j++){
                System.out.print(q[i][j]+" ");
            }
            System.out.println();
        }
        return q;
    }

    /**
     * 压缩矩阵的乘法运算
     * 稀疏矩阵进行乘法运算时即使含有0元素也相乘了，
     * 为避免这种情况，进行矩阵压缩后再相乘
     * 压缩矩阵相乘原理：
     * 1.先把稀疏矩阵进行压缩
     * 2.求出m与n的每一行的第一个非0元素在m与n中的位置
     * 3.遍历m每行的非0元素，
     */
    public Matrix multiMatrix(Matrix m,Matrix n){
        Matrix q = new Matrix();
        q.mu = m.mu;
        q.nu = n.nu;
        //初始化各行第一个非0元素的位置表
        int[] mNum = new int[m.mu];
        for(int len=0;len<m.tu;len++){   //每行有多少个非0元素
            mNum[m.data[len].i]++;
        }
        m.rpos = new int[m.mu];
        m.rpos[0] = 0;
        for(int mRow=1;mRow<m.mu;mRow++){    //每行第一个非0元素在m中的位置
            m.rpos[mRow] = m.rpos[mRow-1] + mNum[mRow-1];
        }
        int[] nNum = new int[n.mu];
        for(int len=0;len<n.tu;len++){   //每行有多少个非0元素
            nNum[n.data[len].i]++;
        }
        n.rpos = new int[n.mu];
        n.rpos[0] = 0;
        for(int nRow=1;nRow<n.mu;nRow++){    //每行第一个非0元素在n中的位置
            n.rpos[nRow] = n.rpos[nRow-1] + nNum[nRow-1];
        }

        //初始化完毕，开始计算
        if(m.tu * n.tu !=0){
            for(int arow=0;arow<m.mu;arow++){  //一行一行处理
                int mlast=0;
                if(arow < m.mu-1)
                    mlast = m.rpos[arow+1];
                else
                    mlast = m.tu;
                for(int p=m.rpos[arow];p<mlast;p++){ //从这一行第一个非0索引到最后一个非0索引
                    int brow = m.data[p].j; //由于m.j=n.i，由此可以求出与此m元素相乘的n元素
                    int nlast=0;
                    if(brow < n.mu-1)
                        nlast = n.rpos[brow+1];
                    else
                        nlast = n.tu;
                    for(int w=n.rpos[brow];w<nlast;w++){
                        int ccol = n.data[w].j; //同一行的非0元素的列索引必然不相同
                        int sum = (Integer)m.data[p].integer * (Integer)n.data[w].integer;
                        if(sum!=0){     //除去0元素
                            Triple<Integer> triple = new Triple<Integer>(arow, ccol , sum);
                            q.add(triple);
                        }
                    }
                }
            }
        }
        //打印结果
        for(int i=0;i<q.tu;i++){
            Triple<Integer> tr = q.data[i];
            System.out.print(tr.i + " " + tr.j + " " + tr.integer);
            System.out.println();
        }
        return q;
    }

    /**
     * 非空元素用三元组表示
     */
    class Triple<Integer>{
        int i;  //元素的行下标
        int j;  //元素的列下标
        Integer integer;    //元素值
        Triple(int i, int j, Integer integer) {
            this.i = i;
            this.j = j;
            this.integer = integer;
        }
    }
    /**
     * 压缩矩阵
     */
    class Matrix{
        Triple[] data;   //存储三元组的数组
        int mu;     //矩阵的行数
        int nu;     //矩阵的列数
        int tu;     //非空个数
        int[] rpos;  //各行第一个非0元素的位置表
        public Matrix(){
            this(10);
        }

        public Matrix(int capacity) {
            data = new Triple[capacity];
        }

        /**
         * 是否需要扩充容量
         */
        public void ensureCapacity(int minCapacity) {
            int oldCapacity = data.length;
            if (minCapacity > oldCapacity) { //指定最小容量比原来容量大才扩充
                int newCapacity = (oldCapacity * 3) / 2 + 1;    //扩充原容量的1.5倍加1
                if (newCapacity < minCapacity)   //扩充后还是小于要求的最小容量，则扩充容量为最小容量
                    newCapacity = minCapacity;
                data = Arrays.copyOf(data, newCapacity);
            }
        }
        //添加元素到压缩矩阵
        public boolean add(Triple triple){
            ensureCapacity(tu + 1);
            data[tu++] = triple;    //size++
            return true;
        }

        //在指定位置添加元素（此添加非彼添加）
        public boolean add(int index,Triple triple){
            if (index >= tu || index < 0) //检查是否越界
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: "+ tu);
            ensureCapacity(tu + 1);
            data[index] = triple;
            tu++;
            return true;
        }
    }
}
