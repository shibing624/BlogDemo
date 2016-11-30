package xm.qa;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xm.qa.sql.SQLUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xuming
 */
public class BaiduDataSource implements DataSource {
    private static final Logger LOG = LoggerFactory.getLogger(BaiduDataSource.class);

    private static final String ACCEPT = "text/html, */*; q=0.01";
    private static final String ENCODING = "gzip, deflate";
    private static final String LANGUAGE = "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3";
    private static final String CONNECTION = "keep-alive";
    private static final String HOST = "www.baidu.com";
    private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:31.0) Gecko/20100101 Firefox/31.0";

    private static final int PAGE = 1;
    private static final int PAGESIZE = 10;
    private final List<String> files = new ArrayList<>();

    public BaiduDataSource() {

    }

    public BaiduDataSource(String file) {
        this.files.add(file);
    }

    public BaiduDataSource(List<String> files) {
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
        // 1.select local db
        Question question = SQLUtil.getQuestionFromDatabase("baidu:", questionStr);
        if (question != null) {
            LOG.info("select question from db:" + question.getQuestion());
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
        String ref = "http://www.baidu.com/";
        for (int i = 0; i < PAGE; i++) {
            query = "http://www.baidu.com/s?tn=monline_5_dg&ie=utf-8&wd=" + query + "&oq=" + query + "&usm=3&f=8&bs=" + query + "&rsv_bp=1&rsv_sug3=1&rsv_sug4=141&rsv_sug1=1&rsv_sug=1&pn=" + i * PAGESIZE;
            LOG.debug(query);
            List<Evidence> evidences = searchBaidu(query, ref);
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
        // 3.save search result to db
        if (question.getEvidences().size() > 7) {
            SQLUtil.saveQuestionToDatabase("baidu:", question);
        }
        return question;
    }

    @Override
    public List<Question> getAnswerByQuestion() {
        return null;
    }

    private List<Evidence> searchBaidu(String url, String ref) {
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
            String resultCssQuery = "html > body > div > div > div > div > div";
            Elements elements = document.select(resultCssQuery);
            for (Element element : elements) {
                Elements subElements = element.select("h3 > a");
                if (subElements.size() != 1) {
                    LOG.debug("not found title");
                    continue;
                }
                String title = subElements.get(0).text();
                if (title == null || title.trim().isEmpty()) {
                    LOG.debug("title is null");
                    continue;
                }
                subElements = element.select("div.c-abstract");
                if (subElements.size() != 1) {
                    continue;
                }
                String snippet = subElements.get(0).text();
                if (snippet == null || "".equals(snippet.trim())) {
                    continue;
                }
                Evidence evidence = new Evidence();
                evidence.setTitle(title);
                evidence.setSnippet(snippet);
                evidences.add(evidence);
            }
        } catch (Exception e) {
            LOG.error("search error.", e);
        }
        return evidences;
    }

    public static void main(String[] args) {
        Question question = new BaiduDataSource().getQuestion("江阴是在哪个省");
        System.out.println(question.toString());

    }
}
