package xm.ruleengine.selfengine;


import xm.ruleengine.selfengine.config.Rule;

/**
 * @author xuming
 */
public class MvelRule extends Rule {
    /**
     * 匹配条件
     */
    private String condition;
    /**
     * 后续动作
     */
    private String action;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getType() {
        return "mvel";
    }

    public String toString() {
        return "mvelRule[condition=" + condition + ", action=" + action
                + ", type=" + getType() + ", id=" + getId() + ", priority=" + getPriority()
                + ", multipleTimes=" + isMultipleTimes() + ",exclusive=" + isExclusive() + "]";

    }

    /**
     * 验证mvel规则的合法性
     */
    public boolean isVaild() {
        if (getCondition() == null) {
            throw new RuntimeException(String.format("规则[%s]的匹配条件为空", getId()));
        }
        if (getCondition() == null) {
            throw new RuntimeException(String.format("规则[%s]的后续操作为空", getId()));
        }
        return true;
    }
}
