import org.w3c.dom.Document;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;

public class JAXBUtils {

    private static Tasks tasks;
    private static File file;

    // reads a XML source, this methods must be executed before any other method in this class
    public static Tasks read(String src) throws JAXBException, NullPointerException {
        file = new File(src);
        return read();
    }

    // read helper method
    private static Tasks read() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Tasks.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        tasks = (Tasks) unmarshaller.unmarshal(file);
        return tasks;
    }

    // writes the task to the XML file
    public static void write(Tasks tasks) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Tasks.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(tasks, file);
    }

    // edits a specific task in the XML file
    public static void edit(Task task) throws JAXBException {
        tasks = read();
        tasks.set(task);
        marshal();
    }

    // deletes a specific task from the XML file
    public static void delete(int id) throws JAXBException {
        tasks = read();
        tasks.remove(id);
        marshal();
    }

    private static void marshal() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Tasks.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(tasks, file);
    }

    public static Task getElementAt(int row) {
        return tasks.get(row);
    }

    public static boolean hasRoot() throws ParserConfigurationException {
        DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = df.newDocumentBuilder();
        Document doc = db.newDocument();
        return doc.getElementsByTagName("task").getLength() > 0;
    }

    public static boolean isDocEmpty() {
        return tasks == null;
    }
}