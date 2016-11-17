package xm.math.ac;

import java.util.HashSet;

/**
 * AC自动机
 * Created by xuming on 2016/8/4.
 */
public class AC {
    public static void main(String[] args) throws InterruptedException {
        ACTrie trie = new ACTrie();
        trie.AddTrieNode("say", 1);
        trie.AddTrieNode("she", 2);
        trie.AddTrieNode("shr", 3);
        trie.AddTrieNode("her", 4);
        trie.AddTrieNode("he", 5);
        trie.BuildFailNodeBFS();
        String s = "yasherhs";
        HashSet<Integer> hashSet = trie.SearchAC(s);
        StringBuffer sb = new StringBuffer();
        for (Integer i : hashSet) {
            sb.append(i.toString() + ",");
        }

        System.out.printf("在主串" + s + "中存在模式串的编号为:" + sb);


    }
}
