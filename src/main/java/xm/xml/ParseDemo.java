package xm.xml;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.List;

/**
 * Created by xuming on 2016/7/20.
 */
public class ParseDemo {
    public static void main(String[] args) throws Exception {
        ParseDemo parseDemo = new ParseDemo();
        parseDemo.parse();
    }

    public void parse() {
        try {
            //创建SAXReader读取器，专门用于读取xml
            SAXReader saxReader = new SAXReader();
            File file = new File("properties/demo.xml");
            Document document = saxReader.read(file);
            Element rootElement = document.getRootElement();
            if (rootElement.elements("module") != null) {
                //因为第一个module标签只有内容没有子节点，直接.iterator()就java.lang.NullPointerException了, 所以需要分开实现
                List<Element> elementList = rootElement.elements("module");
                for (Element element : elementList) {
                    if (!element.getTextTrim().equals("")) {
                        System.out.println("【1】" + element.getTextTrim());
                    } else {
                        Element nameElement = element.element("name");
                        System.out.println("   【2】" + nameElement.getName() + ":" + nameElement.getText());
                        Element valueElement = element.element("value");
                        System.out.println("   【2】" + valueElement.getName() + ":" + valueElement.getText());
                        Element descriptElement = element.element("descript");
                        System.out.println("   【2】" + descriptElement.getName() + ":" + descriptElement.getText());

                        List<Element> subElementList = element.elements("module");
                        for (Element subElement : subElementList) {
                            if (!subElement.getTextTrim().equals("")) {
                                System.out.println("      【3】" + subElement.getTextTrim());
                            } else {
                                Element subnameElement = subElement.element("name");
                                System.out.println("      【3】" + subnameElement.getName() + ":" + subnameElement.getText());
                                Element subvalueElement = subElement.element("value");
                                System.out.println("      【3】" + subvalueElement.getName() + ":" + subvalueElement.getText());
                                Element subdescriptElement = subElement.element("descript");
                                System.out.println("      【3】" + subdescriptElement.getName() + ":" + subdescriptElement.getText());
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
