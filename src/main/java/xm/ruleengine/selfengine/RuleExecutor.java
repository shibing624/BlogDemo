package xm.ruleengine.selfengine;

import xm.ruleengine.selfengine.config.Rule;

/**
 * @author xuming
 */
public interface RuleExecutor<T extends Rule> {
    String getType();
    boolean execute(Context context,T rule);
}
