import javax.xml.bind.annotation.*;

@XmlRootElement(name = "task")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Task {

    private int id;
    private String name, status;

    // the no-arg constructor is needed for JAXB to work
    public Task() {
        name = null;
        status = null;
    }

    public Task(String name, String status) {
        this.name = name;
        this.status = status;
    }

    public Task(int id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    @XmlAttribute(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlElement(name = "name")
    public String getName() {
        assert (name != null);
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        assert (status != null);
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}