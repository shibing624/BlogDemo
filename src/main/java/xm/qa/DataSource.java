package xm.qa;

import java.util.List;

/**
 * @author xuming
 */
public interface DataSource {
    List<Question> getQuestion();
    Question getQuestion(String questionStr);
    Question getAnswerByQuestion(String questionStr);
    List<Question> getAnswerByQuestion();
}
