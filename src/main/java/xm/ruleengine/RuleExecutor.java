package xm.ruleengine;

import xm.ruleengine.config.Rule;

/**
 * @author xuming
 */
public interface RuleExecutor<T extends Rule> {
    String getType();
    boolean execute(Context context,T rule);
}
