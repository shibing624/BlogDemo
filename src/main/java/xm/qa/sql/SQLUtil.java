package xm.qa.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xm.qa.Evidence;
import xm.qa.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xuming
 */
public class SQLUtil {
    private static final Logger LOG = LoggerFactory.getLogger(SQLUtil.class);
    private static final String DRIVIER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/qa?useUnicode=true&characterEncoding=utf8";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    static {
        try{
            Class.forName(DRIVIER);
        }catch (ClassNotFoundException e){
            LOG.error(e.toString());
        }
    }
    private SQLUtil(){}

    public static Connection getConnection(){
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(URL,USER,PASSWORD);
        }catch (SQLException e){
            LOG.error("get mysql nosql connection error."+e);
        }
        return conn;
    }

    public static void close(Statement statement){
        close(null,statement,null);
    }
    public static void close(Statement statement,ResultSet resultSet){
        close(null,statement,resultSet);
    }
    public static void close(Connection conn, Statement st) {
        close(conn, st, null);
    }

    public static void close(Connection conn) {
        close(conn, null, null);
    }

    public static void close(Connection conn, Statement statement,ResultSet resultSet){
        try{
            if(resultSet!=null){
                resultSet.close();
            }
            if(statement !=null){
                statement .close();
            }
            if(conn!=null){
                conn.close();
            }
        }catch (SQLException e){
            LOG.error("close nosql error.",e);
        }
    }

    public static String getRewindEvidenceText(String question, String answer){
        String sql = "select text,id from rewind where question=?";
        Connection conn = getConnection();
        if(conn ==null){
            return null;
        }
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            // select
            pst = conn.prepareStatement(sql);
            pst.setString(1,question+answer);
            rs = pst.executeQuery();
            if(rs.next()){
                String text = rs.getString(1);
                return text;
            }
        }catch (SQLException e){
            LOG.error("select error.",e);
        }finally{
            close(conn,pst,rs);
        }
        return null;
    }

    public static void saveRewindEvidenceText(String question, String answer, String text) {
        String sql = "insert into rewind (question, text) values (?, ?)";
        Connection con = getConnection();
        if (con == null) {
            return;
        }
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, question + answer);
            pst.setString(2, text);
            ////1、保存回带文本
            int count = pst.executeUpdate();
            if (count == 1) {
                LOG.info("保存回带文本成功");
            } else {
                LOG.error("保存回带文本失败");
            }
        } catch (SQLException e) {
            LOG.debug("保存回带文本失败", e);
        } finally {
            close(con, pst, rs);
        }
    }

    public static List<Question> getHistoryQuestionsFromDatabase() {
        List<Question> questions = new ArrayList<>();
        String questionSql = "select question from question";
        Connection con = getConnection();
        if (con == null) {
            return questions;
        }
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            //查询问题
            pst = con.prepareStatement(questionSql);
            rs = pst.executeQuery();
            while (rs.next()) {
                String que = rs.getString(1);
                int index = que.indexOf(":");
                if (index > 0) {
                    que = que.substring(index + 1);
                }
                if (que == null || "".equals(que.trim())) {
                    continue;
                }
                Question question = new Question();
                question.setQuestion(que);
                questions.add(question);
            }
        } catch (SQLException e) {
            LOG.error("查询问题失败", e);
        } finally {
            close(con, pst, rs);
        }
        return questions;
    }
    public static Question getQuestionFromDatabase(String pre, String questionStr) {
        String questionSql = "select id,question from question where question=?";
        String evidenceSql = "select title,snippet from evidence where question=?";
        Connection con = getConnection();
        if (con == null) {
            return null;
        }
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            //1、查询问题
            pst = con.prepareStatement(questionSql);
            pst.setString(1, pre + questionStr.trim().replace("?", "").replace("？", ""));
            rs = pst.executeQuery();
            if (rs.next()) {
                int id = rs.getInt(1);
                //去掉前缀
                String que = rs.getString(2).replace(pre, "");
                Question question = new Question();
                question.setQuestion(que);
                close(pst, rs);
                //2、查询证据
                pst = con.prepareStatement(evidenceSql);
                pst.setInt(1, id);
                rs = pst.executeQuery();
                while (rs.next()) {
                    String title = rs.getString(1);
                    String snippet = rs.getString(2);
                }
                return question;
            } else {
                LOG.info("没有从数据库中查询到问题：" + questionStr);
            }
        } catch (SQLException e) {
            LOG.error("查询问题失败", e);
        } finally {
            close(con, pst, rs);
        }
        return null;
    }
    public static void saveQuestionToDatabase(String pre, Question question) {
        //保存
        String questionSql = "insert into question (question) values (?)";
        String evidenceSql = "insert into evidence (title, snippet, question) values (?, ?, ?)";
        Connection con = getConnection();
        if (con == null) {
            return;
        }
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = con.prepareStatement(questionSql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, pre + question.getQuestion().trim().replace("?", "").replace("？", ""));
            //1、保存问题
            int count = pst.executeUpdate();
            if (count == 1) {
                LOG.info("保存问题成功");
                //2、获取自动生成的主键值
                rs = pst.getGeneratedKeys();
                long primaryKey = 0;
                if (rs.next()) {
                    primaryKey = (Long) rs.getObject(1);
                }
                //关闭pst和rs
                close(pst, rs);
                if (primaryKey == 0) {
                    LOG.error("获取问题自动生成的主键失败");
                    return;
                }
                int i = 1;
                //3、保存证据
                for (Evidence evidence : question.getEvidences()) {
                    try {
                        pst = con.prepareStatement(evidenceSql);
                        pst.setString(1, evidence.getTitle());
                        pst.setString(2, evidence.getSnippet());
                        pst.setLong(3, primaryKey);
                        count = pst.executeUpdate();
                        if (count == 1) {
//                            LOG.info("保存证据 " + i + " 成功");
                        } else {
                            LOG.warn("保存证据 " + i + " 失败");
                        }
                        close(null, pst, null);
                    } catch (Exception e) {
                        LOG.error("保存证据 " + i + " 出错：", e);
                    }
                    i++;
                }

            } else {
                LOG.error("保存问题失败");
            }
        } catch (SQLException e) {
            LOG.error("保存问题失败", e);
        } finally {
            close(con, pst, rs);
        }
    }
    public static void main(String[] args) throws Exception {
        List<Question> text = getHistoryQuestionsFromDatabase();
        if (text != null) {
            for(Question q:text) System.out.println(q.getQuestion());
        } else {
            System.out.println("问题不在数据库中.");
        }
    }

}
