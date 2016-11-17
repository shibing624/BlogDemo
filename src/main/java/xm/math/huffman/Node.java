package xm.math.huffman;

/**
 * @author xuming
 */
public class Node implements Comparable<Node> {
    public String chars = "";
    public int frequence = 0;
    public Node parent;
    public Node leftNode;
    public Node rightNode;

    @Override
    public int compareTo(Node n) {
        return frequence - n.frequence;
    }

    public boolean isLeaf() {
        return chars.length() == 1;
    }

    public boolean isRoot() {
        return parent == null;
    }

    public boolean isLeftChild() {
        return parent != null && this == parent.leftNode;
    }

    public String getChars() {
        return chars;
    }


    @Override
    public String toString() {
        if(parent!=null && leftNode!=null ){
            return "chars:" + this.chars + ";frequence:" + frequence + ";leftnode:" + leftNode
                    + ";rightNode:" + rightNode;
        }
        return "chars:" + this.chars + ";frequence:" + frequence ;
    }
}
