import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "tasks")
public class Tasks {

    private List<Task> tasks;

    /**
     * constructs a new ArrayList.
     */
    public Tasks() {
        tasks = new ArrayList<>();
    }

    /**
     * constructs a new ArrayList with the given list
     *
     * @param tasks the list to be created
     */
    public Tasks(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    /**
     * returns a list representation of Tasks
     *
     * @return a list of tasks
     */
    public List<Task> getTasks() {
        if (tasks == null) {
            tasks = new ArrayList<>();
        }
        return tasks;
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

    /**
     * returns a task by id
     *
     * @param id the id to be queried
     * @return the task with the corresponding id
     */
    public Task get(int id) {
        return tasks.get(id);
    }

    /**
     * update the given task with the values from updated.
     * THe id stays the same.
     *
     * @param updated the new task
     */
    public void set(Task updated) {
        for (Task t : tasks) {
            if (t.getId() == updated.getId()) {
                t.setName(updated.getName());
                t.setStatus(updated.getStatus());
            }
        }
    }

    /**
     * removes a task by id
     *
     * @param id the id to be removed
     */
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
     * returns the last task in the list, or null if it's an empty list
     *
     * @return the last task in the list
     */
    public Task getLast() {
        if (tasks.isEmpty()) {
            return null;
        }

        return tasks.get(tasks.size() - 1);
    }

    /**
     * returns the size of the list.
     *
     * @return the number of tasks in the list
     */
    public int size() {
        return (tasks == null) ? 0 : tasks.size();
    }

    /**
     * Returns a string representation of all the tasks in the ArrayList
     * or "empty list" is the list is empty.
     *
     * @return Returns a string representation of the ArrayList.
     */
    @Override
    public String toString() {
        return (tasks.isEmpty() ? "empty list" : tasks.toString());
    }
}