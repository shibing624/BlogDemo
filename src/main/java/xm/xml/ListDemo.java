package xm.xml;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.List;


public class ListDemo {

    public static void main(String[] args) throws Exception {
        org.junit.Assert.assertEquals(1,(2-1));
        SAXReader reader = new SAXReader();
        File file = new File("properties/books.xml");
        Document document = reader.read(file);
        Element root = document.getRootElement();
        List<Element> childElements = root.elements();
        for (Element child : childElements) {
            //未知属性名情况下
            List<Attribute> attributeList = child.attributes();
            for (Attribute attr : attributeList) {
                System.out.println(attr.getName() + ": " + attr.getValue());
            }

            //已知属性名情况下
                /*System.out.println("id: " + child.attributeValue("id"));*/

            //未知子元素名情况下
            List<Element> elementList = child.elements();
            for (Element ele : elementList) {
                System.out.println(ele.getName() + ": " + ele.getText());
            }
            System.out.println();

            //已知子元素名的情况下
                /*System.out.println("title" + child.elementText("title"));
                System.out.println("author" + child.elementText("author"));*/
            //格式化美观而存在
            System.out.println();
        }
    }

}