import javax.xml.bind.*;
import java.io.File;

/**
 * A utility class that uses JAXB to read and write to a XML file
 */
public class JAXBUtils {

    /**
     * The file where this class reads and writes from
     */
    private static File file;

    /**
     * the tasks that are written to the file
     */
    private static Tasks tasks;

    /**
     * reads a XML source, this methods must be executed
     * once before any other method is executed
     *
     * @param src the source of the file to be written to
     * @return the tasks in the XML file
     * @throws JAXBException if an error occurs while reading the file
     */
    public static Tasks read(String src) throws JAXBException {
        file = new File(src);
        return read();
    }

    /**
     * helper method for the read(String src) method
     *
     * @return the tasks in the XML file
     * @throws JAXBException if an error occurs while reading the file
     */
    private static Tasks read() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Tasks.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        try {
            tasks = (Tasks) unmarshaller.unmarshal(file);
        } catch (UnmarshalException e) {
            return new Tasks();
        }
        return tasks;
    }

    /**
     * writes the tasks list to the file
     *
     * @param tasks the tasks to be written to the file
     * @throws JAXBException if an error occurs while writing to the file
     */
    public static void write(Tasks tasks) throws JAXBException {
        read();
        JAXBContext context = JAXBContext.newInstance(Tasks.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        if (isDocEmpty()) {
            marshaller.marshal(new Tasks(), file);
        }
        marshaller.marshal(tasks, file);
    }

    /**
     * edit a specific task and overwrite it in the file
     *
     * @param task the task to be edited
     * @throws JAXBException if an error occurs while writing to the file
     */
    public static void edit(Task task) throws JAXBException {
        tasks = read();
        if (isDocEmpty()) {
            tasks = new Tasks();
        }
        tasks.set(task);
        marshal();
    }

    /**
     * deletes a specified task by id
     *
     * @param id the id of the task that needs to be deleted
     * @throws JAXBException if an error occurs while writing to the file
     */
    public static void delete(int id) throws JAXBException {
        tasks = read();
        if (isDocEmpty()) {
            tasks = new Tasks();
        }
        tasks.remove(id);
        marshal();
    }

    /**
     * marshals the tasks to the file
     *
     * @throws JAXBException if an error occurs while marshalling the file
     */
    private static void marshal() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Tasks.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(tasks, file);
    }

    /**
     * checks if the tasks list is empty
     *
     * @return true if the list is empty, false otherwise
     */
    public static boolean isDocEmpty() {
        return tasks == null || tasks.size() < 1;
    }
}