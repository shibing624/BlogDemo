package xm.ruleengine.selfengine.config;

import xm.ruleengine.selfengine.Context;

/**
 * @author xuming
 */
public interface EL {
    String EL_BEAN = "el";

    <T> T execute(String expression, Context context);

}
