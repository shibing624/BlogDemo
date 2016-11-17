package xm.math.huffman;

import java.util.*;

/**
 * @author xuming
 */
public class Huffman {

    public static Map<Character, Integer> statistics(char[] charArray) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c : charArray) {
            Character character = new Character(c);
            if (map.containsKey(character)) {
                map.put(character, map.get(character) + 1);
            } else {
                map.put(character, 1);
            }
        }

        return map;
    }

    private static Tree buildTree(Map<Character, Integer> statistics, List<Node> leafs) {
        Character[] keys = statistics.keySet().toArray(new Character[0]);

        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        for (Character character : keys) {
            Node node = new Node();
            node.chars = character.toString();
            node.frequence = statistics.get(character);
            priorityQueue.add(node);
            leafs.add(node);
        }

        int size = priorityQueue.size();
        for (int i = 1; i <= size - 1; i++) {
            Node node1 = priorityQueue.poll();
            Node node2 = priorityQueue.poll();

            Node sumNode = new Node();
            sumNode.chars = node1.chars + node2.chars;
            sumNode.frequence = node1.frequence + node2.frequence;

            sumNode.leftNode = node1;
            sumNode.rightNode = node2;

            node1.parent = sumNode;
            node2.parent = sumNode;

            priorityQueue.add(sumNode);
            System.out.println("sumNode:" + sumNode + "  leftNode:" + node1 + "  rightNode:" + node2);
        }

        Tree tree = new Tree();
        tree.root = priorityQueue.poll();
        return tree;
    }

    public static String encode(String originalStr, Map<Character, Integer> statistics) {
        if (originalStr == null || originalStr.equals("")) {
            return "";
        }

        char[] charArray = originalStr.toCharArray();
        List<Node> leafNodes = new ArrayList<Node>();
        buildTree(statistics, leafNodes);
        Map<Character, String> encodInfo = buildEncodingInfo(leafNodes);

        StringBuffer buffer = new StringBuffer();
        for (char c : charArray) {
            Character character = new Character(c);
            buffer.append(encodInfo.get(character));
        }

        return buffer.toString();
    }

    private static Map<Character, String> buildEncodingInfo(List<Node> leafNodes) {
        Map<Character, String> codewords = new HashMap<>();
        for (Node leafNode : leafNodes) {
            Character character = new Character(leafNode.getChars().charAt(0));
            String codeword = "";
            Node currentNode = leafNode;
            try {
                do {
                    if (currentNode.isLeftChild()) {
                        codeword = "0" + codeword;
                    } else {
                        codeword = "1" + codeword;
                    }
                    currentNode = currentNode.parent;
                } while (currentNode.parent != null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            codewords.put(character, codeword);
        }
        return codewords;
    }

    public static String decode(String binaryStr, Map<Character, Integer> statistics) {
        if (binaryStr == null || binaryStr.equals("")) {
            return "";
        }
        char[] binaryCharArray = binaryStr.toCharArray();
        LinkedList<Character> binaryList = new LinkedList<>();
        int size = binaryCharArray.length;
        for (int i = 0; i < size; i++) {
            binaryList.addLast(new Character(binaryCharArray[i]));
        }

        List<Node> leafNodes = new ArrayList<>();
        Tree tree = buildTree(statistics, leafNodes);
        StringBuffer buffer = new StringBuffer();
        while (binaryList.size() > 0) {
            Node node = tree.root;
            do {
                Character c = binaryList.removeFirst();
                if (c.charValue() == '0') {
                    node = node.leftNode;
                } else {
                    node = node.rightNode;
                }
            } while (!node.isLeaf());

            buffer.append(node.chars);
        }
        return buffer.toString();
    }

}
