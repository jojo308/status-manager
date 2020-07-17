import javax.xml.bind.annotation.*;

@XmlRootElement(name = "task")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Task {

    private int id;
    private String name, status;

    /**
     * constructs an empty task. This constructor is not used but
     * is needed for JAXB to work properly
     */
    public Task() {
        name = null;
        status = null;
    }

    /**
     * constructs a task with name and status
     *
     * @param name the name of the task
     * @param status the status of the task
     */
    public Task(String name, String status) {
        this.name = name;
        this.status = status;
    }

    /**
     * constructs a task with id, name and status
     * @param id the id of the task
     * @param name the name of the task
     * @param status the status of the task
     */
    public Task(int id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    /**
     * returns the id
     *
     * @return the id
     */
    @XmlAttribute(name = "id")
    public int getId() {
        return id;
    }

    /**
     * sets the current id to a new id
     *
     * @param id the new id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * returns a string representation of name
     *
     * @return a string representation of name
     */
    @XmlElement(name = "name")
    public String getName() {
        assert (name != null);
        return name;
    }

    /**
     sets the current name to a new name
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * returns a string representation of status
     *
     * @return a string representation of status
     */
    @XmlElement(name = "status")
    public String getStatus() {
        return status;
    }

    /**
     * sets the current status to a new status
     *
     * @param status the new status
     */
    public void setStatus(String status) {
        assert (status != null);
        this.status = status;
    }

    /**
     * Returns a string representation of the object with
     * id, name and status.
     * @return Returns a string representation of the object.
     */
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}