package xm.qa.place;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xm.qa.BaiduDataSource;
import xm.qa.DataSource;
import xm.qa.Evidence;
import xm.qa.Question;
import xm.qa.sql.SqlUtil;

import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/**
 * 百度百科
 *
 * @author xuming
 */
public class BaikeDataSource implements DataSource {
    private static final Logger LOG = LoggerFactory.getLogger(BaiduDataSource.class);

    private static final String ACCEPT = "text/html, */*; q=0.01";
    private static final String ENCODING = "utf-8";
    private static final String LANGUAGE = "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3";
    private static final String CONNECTION = "keep-alive";
    private static final String HOST = "www.baidu.com";
    private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:31.0) Gecko/20100101 Firefox/31.0";

    private static final int PAGE = 1;
    private static final int PAGESIZE = 10;
    private final List<String> files = new ArrayList<>();

    public BaikeDataSource() {

    }

    public BaikeDataSource(String file) {
        this.files.add(file);
    }

    public BaikeDataSource(List<String> files) {
        this.files.addAll(files);
    }

    @Override
    public List<Question> getQuestion() {
        return getAnswerByQuestion();
    }

    @Override
    public Question getQuestion(String questionStr) {
        return getAnswerByQuestion(questionStr);
    }

    @Override
    public Question getAnswerByQuestion(String questionStr) {
        // 1.select local nosql
        Question question = SqlUtil.getQuestionFromDatabase("baidu:", questionStr);
        if (question != null) {
            LOG.info("select question from nosql:" + question.getQuestion());
            return question;
        }
        // 2.search baidu
        question = new Question();
        question.setQuestion(questionStr);
        String query;
        try {
            query = URLEncoder.encode(question.getQuestion(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOG.error("url encoding error", e);
            return null;
        }
        String ref = "http://baike.baidu.com/item/";
        for (int i = 0; i < PAGE; i++) {
            query = "http://baike.baidu.com/item/" + query;
            LOG.debug(query);
            List<Evidence> evidences = searchBaike(query, ref);
            if (evidences != null && evidences.size() > 0) {
                question.addEvidences(evidences);
            } else {
                LOG.error("not found search result,page:" + (i + 1));
                break;
            }
        }
        LOG.info("question:" + question.getQuestion() + " search evidence, size: " + question.getEvidences().size() + ".");
        if (question.getEvidences().isEmpty()) {
            return null;
        }
        // 3.save search result to nosql
        if (question.getEvidences().size() > 0) {
            SqlUtil.saveQuestionToDatabase("baidu:", question);
        }
        return question;
    }

    @Override
    public List<Question> getAnswerByQuestion() {
        return null;
    }

    private List<Evidence> searchBaike(String url, String ref) {
        List<Evidence> evidences = new ArrayList<>();
        try {
            Document document = Jsoup.connect(url)
                    .header("Accept", ACCEPT)
                    .header("Accept-Encoding", ENCODING)
                    .header("Accept-Language", LANGUAGE)
                    .header("Connection", CONNECTION)
                    .header("User-Agent", USER_AGENT)
                    .header("Host", HOST)
                    .header("Referer", ref)
                    .get();
            String resultCssQuery = "html > body > div > div > div > div > div > h2";
            Elements elements = document.select(resultCssQuery);
            for (Element element : elements) {
                Elements subElements = element.select("[class=title-text]");
                if (subElements.size() == 0) {
                    LOG.debug("not found title");
                    continue;
                }
                String title = subElements.get(0).text();
                if (title == null || title.trim().isEmpty()) {
                    LOG.debug("title is null");
                    continue;
                }
                if (title.contains("区划") || title.contains("地理")) {
                    LOG.info("title is " + title);
                    Evidence evidence = new Evidence();
                    evidence.setTitle(title);
                    evidence.setSnippet(title);
                    evidences.add(evidence);
                    break;
                }

            }
        } catch (Exception e) {
            LOG.error("search error.", e);
        }
        return evidences;
    }

    public static void main(String[] args) {
        Map map = new LinkedHashMap<String, String>();

        try {
            File f = new File("data/fourthplace.txt");
            PrintStream ps = new PrintStream(new BufferedOutputStream(new FileOutputStream("data/fourthplace_validate.txt")));
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "utf-8"));
            String readline;
            while ((readline = br.readLine()) != null) {
                map.put(readline.trim(), "");
            }
            br.close();

            Iterator iter = map.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                String place = (String) entry.getKey();
                Question question = new BaikeDataSource().getQuestion(place);
                if (question != null) {
                    entry.setValue("Y");

                } else {
                    entry.setValue("N");
                }
                ps.println((String) (entry.getKey())+"\t"+(String) (entry.getValue()));
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
