import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "tasks")
public class Tasks {

    private List<Task> tasks;

    // the no-arg constructor is needed for JAXB to work
    public Tasks() {
        tasks = new ArrayList<>();
    }

    public Tasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        if (tasks == null) {
            tasks = new ArrayList<>();
        }
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
     */
    public void add(Task task) {
        if (tasks != null) {
            tasks.add(task);
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
     * returns the next id if another task is added, returns 1 if the list is empty
     *
     * @return the next id
     */
    public int getNextId() {
        Task last = getLast();
        return (last == null) ? 1 : last.getId()+1;
    }

    /**
     * @return the last task in the list
     */
    public Task getLast() {
        if (tasks.isEmpty()) {
            return null;
        }

        return tasks.get(tasks.size() - 1);
    }

    public int size() {
        return (tasks == null) ? 0 : tasks.size();
    }

    @Override
    public String toString() {
        return (tasks.isEmpty() ? "empty list" : tasks.toString());
    }
}