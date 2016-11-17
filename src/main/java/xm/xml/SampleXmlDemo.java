package xm.xml;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


/**
 * Created by xuming on 2016/7/7.
 */
public class SampleXmlDemo {

    public static void main(String[] args) {
        readxml4j();
        writexml4j();
    }

    public static void readxml4j() {
        SAXReader reader = new SAXReader();
        File file = new File("properties/student.xml");
        try {
            Document doc = reader.read(file);
            doc.accept(new MyVistor());
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public static class MyVistor extends VisitorSupport {
        public void visit(Attribute node) {
            System.out.println("Attibute:---" + node.getName() + "=" + node.getValue());
        }

        public void visit(Element node) {
            if (node.isTextOnly()) {
                System.out.println("Element:---" + node.getName() + "="
                        + node.getText());
            } else {
                System.out.println("--------" + node.getName() + "-------");
            }
        }

        @Override
        public void visit(ProcessingInstruction node) {
            System.out.println("PI:" + node.getTarget() + " " + node.getText());
        }
    }


    public static void writexml4j() {
        try {
            XMLWriter writer = new XMLWriter(new FileWriter("properties/author.xml"));
            Document doc = createDoc();
            writer.write(doc);
            writer.close();

            // Pretty print the document to System.out
            // 设置了打印的格式,将读出到控制台的格式进行美化
            OutputFormat format = OutputFormat.createPrettyPrint();
            writer = new XMLWriter(System.out, format);
            writer.write(doc);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Document createDoc() {
        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("root");
        Element author1 = root.addElement("author").addAttribute("name",
                "Kree").addAttribute("location", "UK")
                .addText("Kree Strachan");
        Element author2 = root.addElement("author").addAttribute("name", "King")
                .addAttribute("location", "US").addText("King McWrirter");
        return doc;
    }

}