package xm.math.matrix;

/**
 * 三元组处理稀疏矩阵
 *
 * @author xuming
 */
public class Node {

    public int x;
    public int y;
    public double value;

    public Node(int r, int c, double v) {
        this.x = r;
        this.y = c;
        this.value = v;
    }

    public Node() {
        this(0, 0, 0.0);
    }
}
