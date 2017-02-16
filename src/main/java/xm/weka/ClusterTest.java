package xm.weka;

import org.junit.Test;
import weka.clusterers.ClusterEvaluation;
import weka.clusterers.Clusterer;
import weka.clusterers.DensityBasedClusterer;
import weka.clusterers.EM;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ConverterUtils;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

import static xm.weka.ClassifierTest.*;

/**
 * @author xuming
 */
public class ClusterTest {
    @Test
    public void testEM() throws Exception {
        Instances instances = ConverterUtils.DataSource.read(WEKA_PATH + "contact-lenses.arff");
        EM cluster = new EM();
        cluster.setOptions(new String[]{"-I", "100"});
        cluster.buildClusterer(instances);
        pln(cluster.toString());
    }

    // 评估聚类器的方式 3种
    @Test
    public void testEvaluation() throws Exception {
        String filePath = WEKA_PATH + "contact-lenses.arff";
        Instances instances = ConverterUtils.DataSource.read(filePath);
        // 第1种
        String[] options = new String[]{"-t", filePath};
        String output = ClusterEvaluation.evaluateClusterer(new EM(), options);
        pln(output);

        // 第2种
        DensityBasedClusterer dbc = new EM();
        dbc.buildClusterer(instances);
        ClusterEvaluation clusterEvaluation = new ClusterEvaluation();
        clusterEvaluation.setClusterer(dbc);
        clusterEvaluation.evaluateClusterer(new Instances(instances));
        pln(clusterEvaluation.clusterResultsToString());

        // 第3种
        // 基于密度的聚类器交叉验证
        DensityBasedClusterer newdbc = new EM();
        double logLikelyhood = ClusterEvaluation.crossValidateModel(newdbc, instances, 10, instances.getRandomNumberGenerator(1234));
        pln("logLikelyhood: " + logLikelyhood);
    }

    @Test
    public void testClassesToClusters() throws Exception {
        String filePath = WEKA_PATH + "contact-lenses.arff";
        Instances data = ConverterUtils.DataSource.read(filePath);
        data.setClassIndex(data.numAttributes() - 1);
        Remove remove = new Remove();
        remove.setAttributeIndices("" + (data.classIndex() + 1));
        remove.setInputFormat(data);
        Instances dataCluster = Filter.useFilter(data, remove);

        Clusterer cluster = new EM();
        cluster.buildClusterer(dataCluster);

        ClusterEvaluation eval = new ClusterEvaluation();
        eval.setClusterer(cluster);
        eval.evaluateClusterer(data);

        pln(eval.clusterResultsToString());
    }

    @Test
    public void testOutputClusterDistribution() throws Exception {
        Instances train = ConverterUtils.DataSource.read(SEGMENT_CHALLENGE_PATH);
        Instances test = ConverterUtils.DataSource.read(SEGMENT_TEST_PATH);
        if (!train.equalHeaders(test))
            throw new Exception("train data and test data not the same headers.");

        EM clusterer = new EM();
        clusterer.buildClusterer(train);
        pln("id - cluster - distribution");
        for (int i = 0; i < test.numInstances(); i++) {
            int cluster = clusterer.clusterInstance(test.instance(i));
            double[] dists = clusterer.distributionForInstance(test.instance(i));
            StringBuilder sb = new StringBuilder();
            sb.append(i + 1).append(" - ").append(cluster).append(" - ").append(Utils.arrayToString(dists));
            pln(sb.toString());
        }
    }
}
