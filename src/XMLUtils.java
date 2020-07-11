import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
from: https://stackoverflow.com/questions/42507819/how-to-create-xml-file-from-java/#answer-42510234
 */

public class XMLUtils {

    private static Document doc;

    // add List
    public static Element addRoot(Document doc, String rootName) {
        Element root = doc.createElement(rootName);
        doc.appendChild(root);
        return root;
    }

    public static boolean hasRoot(Document doc, String rootName) {
        try {
            return (doc.getElementsByTagName(rootName)
                    .item(0)
                    .getTextContent()) != null;
        } catch (NullPointerException e) {
            System.out.println("root " + rootName + " does not exist");
            return false;
        }
    }

    // add task -> list
    public static Element addTask(String rootName, Element root) {
        Element e = doc.createElement(rootName);
        root.appendChild(e);
        return e;
    }

    // add name, status -> task
    public static void addNode(Element e, String tagName, String content) {
        Element employ = doc.createElement(tagName);
        employ.appendChild(doc.createTextNode(content));
        e.appendChild(employ);
    }

    // Document
    public static void createDoc() throws ParserConfigurationException {
        DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = df.newDocumentBuilder();
        doc = db.newDocument();
    }

    // Save the xml file
    public static void saveFile(String filePath) throws TransformerException {
        TransformerFactory tF = TransformerFactory.newInstance();
        Transformer t = tF.newTransformer();
        DOMSource source = new DOMSource(doc);
        File xml = new File(filePath);
        StreamResult r = new StreamResult(xml);
        t.transform(source, r);
    }

    public static Document readDocument(String src) throws ParserConfigurationException, IOException, SAXException {
        File file = new File(src);

        if (!file.exists()) {
            createDoc();
        }

        DocumentBuilder db = DocumentBuilderFactory.
                newInstance().
                newDocumentBuilder();
        return db.parse(file);
    }

    public static List<Task> getList(Document document) {
        doc = document;
        List<Task> list = new ArrayList<>();
        int length = doc.getElementsByTagName("task").getLength();
        for (int i = 0; i < length; i++) {
            String name = doc.getElementsByTagName("name")
                    .item(i)
                    .getTextContent();
            String status = doc.getElementsByTagName("status")
                    .item(i)
                    .getTextContent();
            System.out.println("taskList: " + name + ": " + status);
            list.add(new Task(name, status));
        }
        return list;
    }


}