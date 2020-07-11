import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "tasks")
public class Tasks {

    private List<Task> tasks;

    public List<Task> getTasks() {
        return tasks;
    }

    @XmlElement(name = "task")
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * adds a task to the tasks list
     *
     * @param task the task to be added
     * @return true if the task can be added, false otherwise
     */
    public boolean add(Task task) {
        if (tasks == null) {
            return false;
        } else {
            tasks.add(task);
            return true;
        }
    }

    public Task get(int id) {
        return tasks.get(id);
    }

    public void set(Task updated) {
        for (Task t : tasks) {
            if (t.getId() == updated.getId()) {
                t.setName(updated.getName());
                t.setStatus(updated.getStatus());
            }
        }
    }

    public void remove(int id) {
        tasks.removeIf(t -> t.getId() == id);
    }

    /**
     * checks whether the tasks list is empty or not
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return tasks == null;
    }

    /**
     * returns the next id if another task is added, returns -1 if the list is empty
     *
     * @return the next id
     */
    public int getNextId() {
        return (tasks == null) ? -1 : getLast().getId() + 1;
    }

    /**
     * @return the last task in the list
     */
    public Task getLast() {
        return tasks.get(tasks.size() - 1);
    }

    @Override
    public String toString() {
        return (tasks.isEmpty() ? "empty list" : tasks.toString());
    }
}