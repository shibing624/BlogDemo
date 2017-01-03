package xm.ruleengine.mvel;

import org.junit.Test;
import org.mvel2.MVEL;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xuming
 */
public class MVELTest {
    @Test
    public void test1(){
        String expression = "good>33";
        Map map = new HashMap();
        map.put("good", new Integer(333));
        Boolean result = (Boolean) MVEL.eval(expression, map);
        System.out.println(result);
    }
    @Test
    public void test2(){
        String expression = "good>33";
        Serializable compiled = MVEL.compileExpression(expression);
        Map map = new HashMap();
        map.put("good", new Integer(2));
        Boolean result = (Boolean) MVEL.executeExpression(compiled, map);
        System.out.println(result);
        int addresult =(int) MVEL.executeExpression(MVEL.compileExpression("good+321"),map);
        System.out.println(addresult);
    }
}
