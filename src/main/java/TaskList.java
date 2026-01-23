import java.util.ArrayList;

/**
 *  TaskList class contains the task list and its methods (e.g. add, delete, get, size)
 */
public class TaskList {
    private ArrayList<Task> taskList;

    public TaskList() {
        this.taskList = new ArrayList<>();
    }

    /**
     * Constructs a TaskList with an existing list of tasks.
     * @param taskList The ArrayList loaded from save file.
     */
    public TaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    /**
     * Add task to taskList.
     * @param task The task to add to the taskList.
     */
    public void add(Task task) {
        taskList.add(task);
    }

    /**
     * Delete specified task from taskList.
     * @param index index of the task to delete.
     */
    public Task delete(int index) {
        return taskList.remove(index);
    }

    /**
     * Retrieve task at specified index.
     * @param index The task to add to the taskList.
     */
    public Task get(int index) {
        return taskList.get(index);
    }

    /**
     * Return the size of the taskList.
     */
    public int size() {
        return taskList.size();
    }
}
