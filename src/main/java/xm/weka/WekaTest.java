package xm.weka;

import org.junit.Test;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.CSVSaver;
import weka.core.converters.ConverterUtils;
import weka.core.converters.DatabaseLoader;
import weka.experiment.InstanceQuery;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import xm.qa.sql.SqlUtil;

import java.io.File;

/**
 * @author xuming
 */
public class WekaTest {
    @Test
    public void testInstanceFromARFF() throws Exception {
        Instances instances = ConverterUtils.DataSource.read("data/weka/weather.numeric.arff");
        System.out.println(instances);
    }

    @Test
    public void testInstanceFromMysql() throws Exception {
        InstanceQuery query = new InstanceQuery();
        Instances data = null;
        query.setDatabaseURL(SqlUtil.URL);
        query.setUsername(SqlUtil.USER);
        query.setPassword(SqlUtil.PASSWORD);
        query.setQuery("select question from question");
        data = query.retrieveInstances();
        if (data.classIndex() == -1)
            data.setClassIndex(data.numAttributes() - 1);
        System.out.println(data);

    }

    @Test
    public void testInstanceFromMysqlLoader() throws Exception {
        DatabaseLoader loader = new DatabaseLoader();
        loader.setUrl(SqlUtil.URL);
        loader.setUser(SqlUtil.USER);
        loader.setPassword(SqlUtil.PASSWORD);
        loader.setQuery("select question from question");
        Instances data1 = loader.getDataSet();
        if (data1.classIndex() == -1)
            data1.setClassIndex(data1.numAttributes() - 1);
        System.out.println(data1);

        ConverterUtils.DataSink.write("data/weka/baidubook.arff", data1);
        ConverterUtils.DataSink.write("data/weka/baidubook.csv", data1);
    }

    @Test
    public void testSaveCSV() throws Exception {
        DatabaseLoader loader = new DatabaseLoader();
        loader.setUrl(SqlUtil.URL);
        loader.setUser(SqlUtil.USER);
        loader.setPassword(SqlUtil.PASSWORD);
        loader.setQuery("select question from question");
        Instances data1 = loader.getDataSet();
        if (data1.classIndex() == -1)
            data1.setClassIndex(data1.numAttributes() - 1);
        System.out.println(data1);

        CSVSaver saver = new CSVSaver();
        saver.setInstances(data1);
        saver.setFile(new File("data/weka/baidubook-csvsaver.csv"));
        saver.writeBatch();

    }

    @Test
    public void testFilter() throws Exception {
        Instances instances = ConverterUtils.DataSource.read("data/weka/houses.arff");
        instances.setClassIndex(instances.numAttributes() - 1);
        System.out.println(instances);
        String[] options = new String[2];
        options[0]  = "-R";
        options[1] = "1";
        Remove remove = new Remove();
        remove.setOptions(options);
        remove.setInputFormat(instances);
        Instances newData = Filter.useFilter(instances,remove);
        System.out.println(newData);
    }

    @Test
    public void testFilterOnTheFly() throws Exception {
        Instances instances = ConverterUtils.DataSource.read("data/weka/weather.nominal.arff");
        instances.setClassIndex(instances.numAttributes() - 1);
        System.out.println(instances);
        Remove remove = new Remove();
        remove.setAttributeIndices("1");
        // classify
        J48 j48 = new J48();
        j48.setUnpruned(true);
        FilteredClassifier fc = new FilteredClassifier();
        fc.setFilter(remove);
        fc.setClassifier(j48);
        fc.buildClassifier(instances);
        System.out.println(fc);
        for(int i =0 ;i<instances.numInstances();i++){
            double pred = fc.classifyInstance(instances.instance(i));
            System.out.print(instances.classAttribute().value((int)instances.instance(i).classValue()));
            System.out.println(instances.classAttribute().value((int) pred));
        }

        remove.setInputFormat(instances);
        Instances newData = Filter.useFilter(instances,remove);
        System.out.println(newData);
    }
}
