import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;

public class TasksTest {

    private File file;
    private ArrayList<Task> list;

    @Before
    public void init() {
        file = new File("test\\test.xml");
        list = getTestData();
    }

    private ArrayList<Task> getTestData() {
        ArrayList<Task> list = new ArrayList<>();
        list.add(new Task("company 1", "pending"));
        list.add(new Task("company 2", "denied"));
        list.add(new Task("company 3", "accepted"));
        return list;
    }

    @Test
    public void marshalTask() throws JAXBException {
        Tasks tasks = new Tasks();
        tasks.setTasks(list);

        JAXBContext context = JAXBContext.newInstance(Tasks.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(tasks, file);
    }

    @Test
    public void unmarshalTask() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Tasks.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Tasks tasks = (Tasks) unmarshaller.unmarshal(file);
        System.out.println(tasks);
    }
}