package xm.math.matrix;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xuming
 */
public class Matrix {
    public int rows;
    public int cols;
    public int count;
    public List<Node> nodes = new ArrayList<>();

    public Matrix build() {
        Matrix matrix = new Matrix();
        matrix.nodes.add(new Node(0, 0, 8));
        matrix.nodes.add(new Node(1, 2, 1));
        matrix.nodes.add(new Node(2, 3, 6));
        matrix.nodes.add(new Node(3, 1, 4));

        matrix.rows = matrix.cols = 4;
        matrix.count = matrix.nodes.size();

        return matrix;
    }

    public void print(Matrix matrix) {
        if (matrix == null || matrix.nodes.size() == 0) return;
        for (Node i : matrix.nodes) {
            System.out.println(i.x + "\t" + i.y + "\t" + i.value);
        }
        System.out.println();
    }

    public Matrix convertMatrix(Matrix node) {
        Matrix matrix = new Matrix();
        matrix.rows = node.rows;
        matrix.cols = node.cols;
        matrix.count = node.count;
        for (int col = 0; col < node.cols; col++) {
            for (int triple = 0; triple < node.count; triple++) {
                Node t = node.nodes.get(triple);
                if (col == t.y) {
                    matrix.nodes.add(new Node(t.y, t.x, t.value));
                }
            }
        }
        return matrix;
    }

    public static void main(String[] args) {
        Matrix matrix = new Matrix();
        Matrix node = matrix.build();
        matrix.print(node);
        Matrix nodeNew = matrix.convertMatrix(node);
        matrix.print(nodeNew);
    }


}
