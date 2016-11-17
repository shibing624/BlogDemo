package xm.search;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuming
 */
public class SolrDemo1 {
    private static final String URL = "http://localhost:8080/solr/core1";
    private static HttpSolrClient solrClient = null;
    //public static String[] docs = {"阳光阳光高尔夫","高尔夫阳光阳光阳光","高尔夫阳阳光光阳光阳光","光大银行","光大银行阳光阳光阳光阳光阳光分行","阳光高尔夫",};
    public static String[] docs = {"solr in good 中海阳光1", "the bad num 阳光高尔夫2", "中海阳光阳光3", "光阳阳阳阳4", "高尔夫阳阳光光阳光阳光5", "光大银行6"};

    public static void main(String[] args) throws IOException {
        SolrDemo1 search = new SolrDemo1();
        search.init();
        search.createIndex();
        search.search();
        search.query();
    }


    private void init() {
        solrClient = new HttpSolrClient.Builder(URL).build();
        solrClient.setConnectionTimeout(3000);
    }

    public static void createIndex() {
        SolrDocumentList list = new SolrDocumentList();

        SolrDemo1 t = new SolrDemo1();
        SolrClient client = solrClient;
        int i = 0;
        List<SolrInputDocument> docList = new ArrayList<SolrInputDocument>();
        for (String str : docs) {
            SolrInputDocument doc = new SolrInputDocument();
            doc.addField("id", i++);
            doc.setDocumentBoost(1.0f);//set boost
            System.out.println(doc.getDocumentBoost());

            doc.addField("content", str);
            docList.add(doc);
        }
        try {
            client.add(docList);
            client.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // 删除索引
    // 据查询结果删除：
    public void DeleteByQuery() {
        //solrServer = createSolrServer();
        try {
            // 删除所有的索引
            solrClient.deleteByQuery("jobsName:高级技术支持");
            solrClient.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 根据索引号删除索引：
    public void DeleteByQueryJobsId() {

        try {
            solrClient.deleteById("1");
            solrClient.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void query(){
        SolrQuery query = new SolrQuery();
        query.set("q","*.*");
        query.setHighlight(true);
        query.addHighlightField("content");
        query.setHighlightSimplePre("<i>");
        query.setHighlightSimplePost("<h>");
        try {
            QueryResponse rsp = solrClient.query(query);


        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void search() {

        SolrQuery query = new SolrQuery();
        query.setQuery("content:阳光");
        query.setRows(10);
        query.setParam("defType", "complexphrase");

        QueryResponse response = null;
        try {
            response = solrClient.query(query);
            System.out.println(response.toString());
            System.out.println();
            SolrDocumentList docs = response.getResults();
            System.out.println("文档个数：" + docs.getNumFound());
            System.out.println("查询时间：" + response.getQTime());
            for (SolrDocument doc : docs) {

                System.out.println("id: " + doc.getFieldValue("id") + "      content: " + doc.getFieldValue("content"));
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 查询
    // SolrJ提供的查询功能比较强大，可以进行结果中查询、范围查询、排序等。
    // 补充一下范围查询的格式：[star t TO end]，start与end是相应数据格式的值的字符串形式，“TO” 一定要保持大写！
    /*
     * field 查询的字段名称数组 key 查询的字段名称对应的值 start 查询的起始位置 count 一次查询出来的数量 sortfield
     * 需要排序的字段数组 flag 需要排序的字段的排序方式如果为true 升序 如果为false 降序 hightlight 是否需要高亮显示
     */
    public QueryResponse Search(String[] field, String[] key, int start,
                                int count, String[] sortfield, Boolean[] flag, Boolean hightlight) {

        // 检测输入是否合法
        if (null == field || null == key || field.length != key.length) {
            return null;
        }
        if (null == sortfield || null == flag || sortfield.length != flag.length) {
            return null;
        }

        SolrQuery query = null;
        try {
            // 初始化查询对象
            query = new SolrQuery(field[0] + ":" + key[0]);
            for (int i = 0; i < field.length; i++) {
                query.addFilterQuery(field[i] + ":" + key[i]);
            }
            // 设置起始位置与返回结果数
            query.setStart(start);
            query.setRows(count);
            // 设置排序
            for (int i = 0; i < sortfield.length; i++) {
                if (flag[i]) {

                    query.addSort(sortfield[i], SolrQuery.ORDER.asc);
                } else {
                    query.addSort(sortfield[i], SolrQuery.ORDER.desc);
                }
            }
            // 设置高亮
            if (null != hightlight) {
                query.setHighlight(true); // 开启高亮组件
                query.addHighlightField("jobsName");// 高亮字段
                query.setHighlightSimplePre("<font color=\"red\">");// 标记
                query.setHighlightSimplePost("</font>");
                query.setHighlightSnippets(1);// 结果分片数，默认为1
                query.setHighlightFragsize(1000);// 每个分片的最大长度，默认为100

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        QueryResponse rsp = null;
        try {
            rsp = solrClient.query(query);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        // 返回查询结果
        return rsp;
    }

}
