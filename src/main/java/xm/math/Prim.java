package xm.math;

import java.util.Vector;

/**
 * 贪心算法——最小生成树（Prim）
 */

public class Prim {
    public static Vector<Vector<Integer>> m_nvGraph;    // 无向连通图
    public static Vector<TreeNode> m_tnMSTree;    // 最小生成树
    static  int m_nNodeCount;

    public static class TreeNode {
        public int m_nVertexIndexA;
        public int m_nVertexIndexB;
        public int m_nWeight;

        public TreeNode(int nVertexIndexA, int nVertexIndexB, int nWeight) {
            this.m_nVertexIndexA = nVertexIndexA;
            this.m_nVertexIndexB = nVertexIndexB;
            this.m_nWeight = nWeight;
        }
    }


    public static class MST_Prim {
        public MST_Prim(Vector<Vector<Integer>> vnGraph) {
            m_nvGraph = vnGraph;
            m_nNodeCount =  m_nvGraph.size();
        }

        public void DoPrim() {
            // 是否被访问标志
            Vector<Boolean> bFlag =new Vector<>();

            int nMaxIndexA = 0;
            int nMaxIndexB = 0;
            int i1 = 0;
            while (i1 < m_nNodeCount - 1) {
                int nMaxWeight = Integer.MAX_VALUE;
                // 找到当前最短路径
                int i = 0;
                while (i < m_nNodeCount) {
                    if (!bFlag.get(i)) {
                        ++i;
                        continue;
                    }
                    for (i1 = 0; i1 < m_nNodeCount; ++i1) {
                        if (!bFlag.get(i1) && nMaxWeight > m_nvGraph.get(i).get(i1)) {
                            nMaxWeight =  m_nvGraph.get(i).get(i1);
                            nMaxIndexA = i;
                            nMaxIndexB = i1;
                        }
                    }
                    ++i;
                }
                bFlag.set(nMaxIndexB,true);
                TreeNode a =new TreeNode(nMaxIndexA, nMaxIndexB, nMaxWeight);
                m_tnMSTree .add(a);
                ++i1;
            }
            // 输出结果
            Vector<TreeNode> const_iterator =m_tnMSTree;

//            for (TreeNode ite = const_iterator.firstElement();ite != m_tnMSTree.lastElement();++ite ){
////                cout << ( * ite).m_nVertexIndexA << "->"
////                        << ( * ite).m_nVertexIndexB << " : "
////                        << ( * ite).m_nWeight << endl;
//            }
        }

    }

    public static void main(String[] args) {
        int cnNodeCount = 6;
        Vector<Vector<Integer>> graph =new Vector<>();
        for (int  i = 0; i < graph.size(); ++i) {
            graph.set(cnNodeCount,new Vector<>(Integer.MAX_VALUE));
        }
//        graph.set(0).set(1) = 6;
//        graph[0][2] = 1;
//        graph[0][3] = 5;
//        graph[1][2] = 5;
//        graph[1][4] = 3;
//        graph[2][3] = 5;
//        graph[2][4] = 6;
//        graph[2][5] = 4;
//        graph[3][5] = 2;
//        graph[4][5] = 6;
//
//        graph[1][0] = 6;
//        graph[2][0] = 1;
//        graph[3][0] = 5;
//        graph[2][1] = 5;
//        graph[4][1] = 3;
//        graph[3][2] = 5;
//        graph[4][2] = 6;
//        graph[5][2] = 4;
//        graph[5][3] = 2;
//        graph[5][4] = 6;
//
//        MST_Prim mstp=new MST_Prim(graph);
//        mstp.DoPrim();
    }


}
