package xm.java8;

/**
 * Created by xuming on 2016/8/23.
 */
public class VariableParamter {
    public static void vartest(int... var) {
        for (int i = 0; i < var.length; i++) {
            System.out.println(var[i]);
        }
    }

    public static void main(String[] args) {
        VariableParamter.vartest(1, 2, 3);
    }
}
