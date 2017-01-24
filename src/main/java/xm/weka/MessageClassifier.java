package xm.weka;

import org.ansj.dic.DicReader;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.core.*;
import weka.core.converters.ConverterUtils;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static xm.weka.ClassifierTest.WEKA_PATH;

/**
 * 文本分类
 *
 * @author xuming
 */
public class MessageClassifier implements Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageClassifier.class);
    // train data
    private Instances data = null;
    // word count filter
    private StringToWordVector filter = new StringToWordVector();
    // classifier
    private Classifier classifier = new J48();
    // model is new
    private boolean upToDate;

    /**
     * build empty train set
     * @throws Exception
     */
    public MessageClassifier() throws Exception {
        String nameOfDataset = this.getClass().getName();
        List<Attribute> attributes = new ArrayList<>();
        // add text message attribute
        attributes.add(new Attribute("Message", (List<String>) null));
        // add class attribute
        List<String> classValues = new ArrayList<>();
        classValues.add("education");
        classValues.add("history");
        attributes.add(new Attribute("Class", classValues));
        // build instances of init is 100
        data = new Instances(nameOfDataset, (ArrayList<Attribute>) attributes, 100);
        data.setClassIndex(data.numAttributes() - 1);
    }

    /**
     * 使用给定的训练文本信息更新模型
     * @param message
     * @param classValue
     * @throws Exception
     */
    public void updateData(String message, String classValue) throws Exception {
        // 文本信息转为实例
        Instance instance = makeInstance(message, data);
        instance.setClassValue(classValue);
        // 添加实例到训练数据
        data.add(instance);
        upToDate = false;
        LOGGER.info("update model success!");
    }

    private Instance makeInstance(String message, Instances data) {
        Instance instance = new DenseInstance(2);
        Attribute messageAttr = data.attribute("Message");
        instance.setValue(messageAttr, messageAttr.addStringValue(message));
        instance.setDataset(data);
        return instance;
    }

    public void classifyMessage(String message) throws Exception {
        // 检查是否已构建分类器
        if (data.numInstances() == 0)
            throw new Exception("no classifier found.");
        // 检查分类器和过滤器是否最新
        if (!upToDate) {
            // 初始化过滤器，并告知输入格式
            filter.setInputFormat(data);
            // 从训练数据生成单词计数
            Instances filterdData = Filter.useFilter(data, filter);
            // 重建分类器
            classifier.buildClassifier(filterdData);
            upToDate = true;
        }
        // 形成单独小测试集
        Instances testSet = data.stringFreeStructure();
        // 文本信息成为测试实例
        Instance instance = makeInstance(message, testSet);
        filter.input(instance);
        Instance filteredInstance = filter.output();
        // 获取预测类别值的索引
        double predicted = classifier.classifyInstance(filteredInstance);
        // 输出类别值
        LOGGER.info("text info classify: " + data.classAttribute().value((int) predicted));
    }

    public static void main(String[] args) {
        try {
            String messageName = Utils.getOption('m', args);
            if (messageName.length() == 0) {
                throw new Exception("no text file name.");
            }
            BufferedReader m = DicReader.getReader(messageName);
            StringBuffer message = new StringBuffer();
            int l;
            while ((l = m.read()) != -1) {
                message.append((char) l);
            }
            m.close();

            boolean isEnglish = Utils.getFlag('E', args);
            StringBuffer text = new StringBuffer();
            if (!isEnglish) {
                List<Term> terms = ToAnalysis.parse(message.toString()).getTerms();
                for (Term i : terms) text.append(i.getName() + " ");
            }

            String classValue = Utils.getOption('c', args);
            String modelName = Utils.getOption('t', args);
            if (modelName.length() == 0)
                throw new Exception("must set model name.");
            MessageClassifier messageClassifier;
            try {
                ObjectInputStream modelInputFile = new ObjectInputStream(new FileInputStream(modelName));
                messageClassifier = (MessageClassifier) modelInputFile.readObject();
                modelInputFile.close();
            } catch (FileNotFoundException e) {
                messageClassifier = new MessageClassifier();
                LOGGER.error("model not found." + e);
            }

            if (classValue.length() != 0) {
                messageClassifier.updateData(text.toString(), classValue);
                ObjectOutputStream modelOutputFile = new ObjectOutputStream(new FileOutputStream(modelName));
                modelOutputFile.writeObject(messageClassifier);
                modelOutputFile.close();
                ConverterUtils.DataSink.write(WEKA_PATH + "textclassification.arff", messageClassifier.data);
            } else {
                messageClassifier.classifyMessage(text.toString());
            }
        } catch (Exception e) {
            LOGGER.error("err" + e);
        }
    }
}
