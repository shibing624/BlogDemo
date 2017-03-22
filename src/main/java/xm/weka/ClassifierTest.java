package xm.weka;

import org.junit.Test;
import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.GreedyStepwise;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.meta.AttributeSelectedClassifier;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.*;
import weka.core.converters.ArffLoader;
import weka.core.converters.ConverterUtils;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AddClassification;

import java.io.File;
import java.util.Random;

/**
 * @author xuming
 */
public class ClassifierTest {
    public static final String WEKA_PATH = "data/weka/";
    public static final String WEATHER_NOMINAL_PATH = "data/weka/weather.nominal.arff";
    public static final String WEATHER_NUMERIC_PATH = "data/weka/weather.numeric.arff";
    public static final String SEGMENT_CHALLENGE_PATH = "data/weka/segment-challenge.arff";
    public static final String SEGMENT_TEST_PATH = "data/weka/segment-test.arff";
    public static final String IONOSPHERE_PATH = "data/weka/ionosphere.arff";

    public static void pln(String str) {
        System.out.println(str);
    }

    @Test
    public void testLinearRegression() throws Exception {
        Instances dataset = ConverterUtils.DataSource.read(WEKA_PATH + "houses.arff");
        dataset.setClassIndex(dataset.numAttributes() - 1);
        LinearRegression linearRegression = new LinearRegression();
        try {
            linearRegression.buildClassifier(dataset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        double[] coef = linearRegression.coefficients();
        double myHouseValue = (coef[0] * 3198) +
                (coef[1] * 9669) +
                (coef[2] * 5) +
                (coef[3] * 3) +
                (coef[4] * 1) +
                coef[6];

        System.out.println(myHouseValue);
    }

    @Test
    public void testNBIncrementalClassifier() throws Exception {
        ArffLoader loader = new ArffLoader();
        loader.setFile(new File(WEATHER_NOMINAL_PATH));
        Instances instances = loader.getStructure();
        instances.setClassIndex(instances.numAttributes() - 1);
        System.out.println(instances);
        System.out.println("------------");

        NaiveBayesUpdateable nb = new NaiveBayesUpdateable();
        nb.buildClassifier(instances);
        Instance instance;
        while ((instance = loader.getNextInstance(instances)) != null) {
            nb.updateClassifier(instance);
        }
        System.out.println(nb);

    }

    @Test
    public void testBatchClassifier() throws Exception {
        ArffLoader loader = new ArffLoader();
        loader.setFile(new File(WEATHER_NOMINAL_PATH));
        Instances instances = loader.getDataSet();
        instances.setClassIndex(instances.numAttributes() - 1);
        System.out.println(instances);
        System.out.println("------------");

        J48 tree = new J48();
        tree.buildClassifier(instances);
        System.out.println(tree);
    }

    @Test
    public void testRandomForestClassifier() throws Exception {
        ArffLoader loader = new ArffLoader();
        loader.setFile(new File(WEKA_PATH + "segment-challenge.arff"));
        Instances instances = loader.getDataSet();
        instances.setClassIndex(instances.numAttributes() - 1);
        System.out.println(instances);
        System.out.println("------------");

        RandomForest rf = new RandomForest();
        rf.buildClassifier(instances);
        System.out.println(rf);
    }

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

    /**
     * 利用训练集预测测试集的分类，批量处理
     */
    @Test
    public void testOutputClassDistribution() throws Exception {
        ArffLoader loader = new ArffLoader();
        loader.setFile(new File(SEGMENT_CHALLENGE_PATH));
        Instances train = loader.getDataSet();
        train.setClassIndex(train.numAttributes() - 1);

        ArffLoader loader1 = new ArffLoader();
        loader1.setFile(new File(SEGMENT_TEST_PATH));
        Instances test = loader1.getDataSet();
        test.setClassIndex(test.numAttributes() - 1);

        J48 classifier = new J48();
        classifier.buildClassifier(train);
        System.out.println("num\t-\tfact\t-\tpred\t-\terr\t-\tdistribution");
        for (int i = 0; i < test.numInstances(); i++) {
            double pred = classifier.classifyInstance(test.instance(i));
            double[] dist = classifier.distributionForInstance(test.instance(i));
            StringBuilder sb = new StringBuilder();
            sb.append(i + 1)
                    .append(" - ")
                    .append(test.instance(i).toString(test.classIndex()))
                    .append(" - ")
                    .append(test.classAttribute().value((int) pred))
                    .append(" - ");
            if (pred != test.instance(i).classValue())
                sb.append("yes");
            else
                sb.append("no");
            sb.append(" - ");
            sb.append(Utils.arrayToString(dist));
            System.out.println(sb.toString());
        }
    }

    // 单次运行十折交叉验证
    @Test
    public void testOnceCV() throws Exception {
        Instances data = ConverterUtils.DataSource.read(IONOSPHERE_PATH);
        data.setClassIndex(data.numAttributes() - 1);
        /*
        String[] options = new String[2];
        options[0] = "-C";
        options[1] = "0.25";

        String classname = "weka.classifiers.trees.J48";
        Classifier classifier = (Classifier) Utils.forName(Classifier.class, classname, options);
        */

        Classifier classifier = new J48();

        int seed = 1234;
        int folds = 10;

        Debug.Random random = new Debug.Random(seed);
        Instances newData = new Instances(data);
        newData.randomize(random);
        if (newData.classAttribute().isNominal())
            newData.stratify(folds);

        Evaluation eval = new Evaluation(newData);
        for (int i = 0; i < folds; i++) {
            Instances train = newData.trainCV(folds, i);
            Instances test = newData.testCV(folds, i);
            Classifier clsCopy = AbstractClassifier.makeCopy(classifier);
            clsCopy.buildClassifier(train);
            eval.evaluateModel(clsCopy, test);
        }
        pln("classifier:" + Utils.toCommandLine(classifier));
        pln("data:" + data.relationName());
        pln("seed:" + seed);
        pln(eval.toSummaryString("=== " + folds + " test ===", false));
    }

    // 交叉验证并预测
    @Test
    public void testOnceCVAndPrediction() throws Exception {
        Instances data = ConverterUtils.DataSource.read(IONOSPHERE_PATH);
        data.setClassIndex(data.numAttributes() - 1);
        Classifier classifier = new J48();
        int seed = 1234;
        int folds = 10;

        Debug.Random random = new Debug.Random(seed);
        Instances newData = new Instances(data);
        newData.randomize(random);
        if (newData.classAttribute().isNominal())
            newData.stratify(folds);

        // 执行交叉验证，并添加预测
        Instances predictedData = null;
        Evaluation eval = new Evaluation(newData);
        for (int i = 0; i < folds; i++) {
            Instances train = newData.trainCV(folds, i);
            Instances test = newData.testCV(folds, i);
            Classifier clsCopy = AbstractClassifier.makeCopy(classifier);
            clsCopy.buildClassifier(train);
            eval.evaluateModel(clsCopy, test);

            // add prediction
            AddClassification filter = new AddClassification();
            filter.setClassifier(classifier);
            filter.setOutputClassification(true);
            filter.setOutputDistribution(true);
            filter.setOutputErrorFlag(true);
            filter.setInputFormat(train);
            Filter.useFilter(train, filter);
            Instances pred = Filter.useFilter(test, filter);
            if (predictedData == null)
                predictedData = new Instances(pred, 0);
            for (int j = 0; j < pred.numInstances(); j++)
                predictedData.add(pred.instance(j));
        }
        pln("classifier:" + classifier.getClass().getName() + " " + Utils.joinOptions(((OptionHandler) classifier).getOptions()));
        pln("data:" + data.relationName());
        pln("seed:" + seed);
        pln(eval.toSummaryString("=== " + folds + " test ===", false));
        // write data
        ConverterUtils.DataSink.write(WEKA_PATH + "predictions.arff", predictedData);
    }

    // 10次交叉验证
    @Test
    public void test10CV() throws Exception {
        Instances data = ConverterUtils.DataSource.read(WEKA_PATH + "labor.arff");
        data.setClassIndex(data.numAttributes() - 1);
        Classifier classifier = new J48();

        int runs = 10;
        int folds = 10;

        for (int k = 0; k < runs; k++) {
            // 10次十折交叉验证，主要区别就是随机种子数
            int seed = k + 1234;
            Debug.Random random = new Debug.Random(seed);
            Instances newData = new Instances(data);
            newData.randomize(random);
            // 如果类别是标称型，则根据其类别值分层
            if (newData.classAttribute().isNominal())
                newData.stratify(folds);

            // 执行交叉验证
            Evaluation eval = new Evaluation(newData);
            for (int i = 0; i < folds; i++) {
                Instances train = newData.trainCV(folds, i);
                Instances test = newData.testCV(folds, i);
                Classifier clsCopy = AbstractClassifier.makeCopy(classifier);
                clsCopy.buildClassifier(train);
                eval.evaluateModel(clsCopy, test);
            }
            pln("classifier:" + Utils.toCommandLine(classifier));
            pln("data:" + data.relationName());
            pln("seed:" + seed);
            pln(eval.toSummaryString("=== " + folds + " test, run: " + (k + 1) + " time  ===", false));
        }
    }


}
