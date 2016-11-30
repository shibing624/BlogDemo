package xm.qa;

import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xuming
 */
public class Evidence {
    private String title;
    private String snippet;
    private double score = 1.0;

    public List<String> getTitleWords() {
        List<String> result = new ArrayList<>();
        Result lists = ToAnalysis.parse(snippet);
        result.addAll(lists.getTerms().stream().map(Term::getName).collect(Collectors.toList()));
        return result;
    }

    public List<String> getWords() {
        List<String> result = new ArrayList<>();
        Result lists = ToAnalysis.parse(title + this.snippet);
        result.addAll(lists.getTerms().stream().map(Term::getName).collect(Collectors.toList()));
        return result;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
