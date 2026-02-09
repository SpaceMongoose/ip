package esquie.tasks;

import java.util.ArrayList;
import java.util.List;

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
     * @return Returns a task object that is removed
     */
    public Task delete(int index) {
        return taskList.remove(index);
    }

    /**
     * Retrieve task at specified index.
     * @param index The task to add to the taskList.
     * @return Returns a task object
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

    /**
     * Filter the current tasks and extracts out only tasks with matching keywords
     *
     * @param keyword is the filter keyword to apply on tasks
     */
    public TaskList find(String keyword) {
        String searchKey = keyword.toLowerCase();
        List<Task> findList = this.taskList.stream()
                .filter(currentTask -> currentTask.toString().toLowerCase().contains(searchKey))
                .toList();

        return new TaskList(new ArrayList<>(findList));
    }
}
