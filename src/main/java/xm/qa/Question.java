package xm.qa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xuming
 */
public class Question {

    private static final Logger LOG = LoggerFactory.getLogger(Question.class);
    private String question;
    private final List<Evidence> evidences = new ArrayList<>();
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<Evidence> getEvidences() {
        return evidences;
    }

    public void addEvidences(List<Evidence> evidences) {
        this.evidences.addAll(evidences);
    }

    public void addEvidence(Evidence evidence) {
        this.evidences.add(evidence);
    }

    public void removeEvidence(Evidence evidence) {
        this.evidences.remove(evidence);
    }
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("?. ").append(question).append("\n\n");
        for (Evidence evidence : evidences) {
            result.append("Title: ").append(evidence.getTitle()).append("\n");
            result.append("Snippet: ").append(evidence.getSnippet()).append("\n\n");
        }

        return result.toString();
    }

    public String toString(int index) {
        StringBuilder result = new StringBuilder();
        result.append("?").append(index).append(". ").append(question).append("\n\n");
        for (Evidence evidence : evidences) {
            result.append("Title: ").append(evidence.getTitle()).append("\n");
            result.append("Snippet: ").append(evidence.getSnippet()).append("\n\n");
        }

        return result.toString();
    }

}
