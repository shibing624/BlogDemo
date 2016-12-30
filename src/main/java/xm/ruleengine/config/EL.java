package xm.ruleengine.config;

import xm.ruleengine.Context;

/**
 * @author xuming
 */
public interface EL {
    String EL_BEAN = "el";

    <T> T execute(String expression, Context context);

}
