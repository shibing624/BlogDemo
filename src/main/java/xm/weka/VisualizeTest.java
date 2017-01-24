package xm.weka;

import org.junit.Test;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import javax.swing.*;

import static xm.weka.ClassifierTest.WEATHER_NOMINAL_PATH;
import static xm.weka.ClassifierTest.pln;

/**
 * @author xuming
 */
public class VisualizeTest {
    @Test
    public void testTree() throws Exception {
        // 很慢
        J48 j48 = new J48();
        Instances data = ConverterUtils.DataSource.read(WEATHER_NOMINAL_PATH);
        data.setClassIndex(data.numAttributes() -1);
        j48.buildClassifier(data);
        pln(data.toString());


        // tree
//        TreeVisualizer tv = new TreeVisualizer(null,j48.graph(),new PlaceNode2());
        JFrame jf = new JFrame("J48 tree");
//        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        jf.setSize(600,500);
//        jf.getContentPane().setLayout(new BorderLayout());
//        jf.getContentPane().add(tv,BorderLayout.CENTER);
        jf.setVisible(true);
//        tv.fitToScreen();
    }
}
