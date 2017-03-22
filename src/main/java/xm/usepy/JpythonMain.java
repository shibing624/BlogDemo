package xm.usepy;

import org.python.util.PythonInterpreter;

/**
 * @author xuming
 */
public class JpythonMain {
    public static void main(String[] args) throws Exception {
        PythonInterpreter interpreter = new PythonInterpreter();
// PySystemState sys = Py.getSystemState();
        interpreter.exec("print 'hello'");
        interpreter.exec("import  sys");
        interpreter.exec("print sys.path");
        interpreter.exec("for x  in xrange(10):print x \n");
// interpreter.execfile("D:\\Users\\zhoumeixu204\\Desktop\\test.py");
    }
}
