package xm.weka;

import org.junit.Test;
import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.GreedyStepwise;
import weka.classifiers.Evaluation;
import weka.classifiers.meta.AttributeSelectedClassifier;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ConverterUtils;

import java.util.Random;

import static xm.weka.ClassifierTest.WEATHER_NOMINAL_PATH;
import static xm.weka.ClassifierTest.WEATHER_NUMERIC_PATH;
import static xm.weka.ClassifierTest.pln;

/**
 * @author xuming
 */
public class AttributeSelectionTest {
    // 元分类器
    @Test
    public void testMetaClassifier() throws Exception {
        Instances data = ConverterUtils.DataSource.read(WEATHER_NUMERIC_PATH);
        if (data.classIndex() == -1)
            data.setClassIndex(data.numAttributes() - 1);

        AttributeSelectedClassifier classifier = new AttributeSelectedClassifier();
        CfsSubsetEval eval = new CfsSubsetEval();
        GreedyStepwise stepwise = new GreedyStepwise();
        stepwise.setSearchBackwards(true);
        J48 base = new J48();
        classifier.setClassifier(base);
        classifier.setEvaluator(eval);
        classifier.setSearch(stepwise);
        Evaluation evaluation = new Evaluation(data);
        evaluation.crossValidateModel(classifier, data, 10, new Random(1234));
        pln(evaluation.toSummaryString());
    }

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
