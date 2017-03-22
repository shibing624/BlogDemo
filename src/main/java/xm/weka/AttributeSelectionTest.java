package xm.weka;

import org.junit.Test;
import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.GreedyStepwise;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ConverterUtils;

import static xm.weka.ClassifierTest.WEATHER_NOMINAL_PATH;
import static xm.weka.ClassifierTest.pln;

/**
 * @author xuming
 */
public class AttributeSelectionTest {

    // 底层API属性选择
    @Test
    public void testUseLowApi() throws Exception {
        ConverterUtils.DataSource source = new ConverterUtils.DataSource(WEATHER_NOMINAL_PATH);
        Instances data = source.getDataSet();
        if(data.classIndex() == -1)
            data.setClassIndex(data.numAttributes() -1);
        AttributeSelection attributeSelection = new AttributeSelection();
        CfsSubsetEval eval = new CfsSubsetEval();
        GreedyStepwise search = new GreedyStepwise();
        search.setSearchBackwards(true);
        attributeSelection.setEvaluator(eval);
        attributeSelection.setSearch(search);
        attributeSelection.SelectAttributes(data);
        int[] indices = attributeSelection.selectedAttributes();
        pln(Utils.arrayToString(indices));

    }
}
