package xm.ruleengine.selfengine;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;


/**
 * @author xuming
 */
public class RuleEngineDefaultTest {
    @Test
    public void execute() throws Exception {
        String feerulexml = "data/feerule.xml";

        RuleEngine ruleEngine = new RuleEngineDefault();
        Context context = new MvelContext();
        context.put("fee", 0.0);

        context.put("salary", 1000);
        ruleEngine.execute(context, "feerule");
        assertEquals(0, (int)context.get("fee"));

        context.put("salary", 4000);
        ruleEngine.execute(context, "feerule");
        assertEquals(15.0, context.get("fee"));

        context.put("salary", 7000);
        ruleEngine.execute(context, "feerule");
        assertEquals(245.0, context.get("fee"));

        context.put("salary", 21000);
        ruleEngine.execute(context, "feerule");
        assertEquals(3370.0, context.get("fee"));

        context.put("salary", 40005);
        ruleEngine.execute(context, "feerule");
        assertEquals(8196.50, context.get("fee"));

        context.put("salary", 70005);
        ruleEngine.execute(context, "feerule");
        assertEquals(17771.75, context.get("fee"));

        context.put("salary", 100000);
        ruleEngine.execute(context, "feerule");
        assertEquals(29920.00, context.get("fee"));
    }

}