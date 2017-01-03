package xm.ruleengine.selfengine;

import java.util.List;

/**
 * @author xuming
 */
public interface RuleEngine {
    void execute(Context context,String ruleSetName);
    void addRules(RuleSet ruleSet);
    void removeRules(RuleSet ruleSet);
    void addRuleExecutors(List<RuleExecutor> ruleExecutorList);
    void addRuleExecutor(RuleExecutor ruleExecutor);
    void setRuleExecutors(List<RuleExecutor> ruleExecutors);
}
