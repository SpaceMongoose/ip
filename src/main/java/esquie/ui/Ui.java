package esquie.ui;

import java.util.Scanner;

import esquie.common.Messages;
import esquie.tasks.Task;
import esquie.tasks.TaskList;

/**
 * Ui deals with interactions with the user, focusing on input and output.
 */
public class Ui {
    /** Constants used for standardized formatting. */
    private static final String BREAKLINE = "--------------------------------------";
    private static final String DOUBLEINDENTATION = "        ";
    private StringBuilder response = new StringBuilder();
    private Scanner sc;

    public Ui() {
        this.sc = new Scanner(System.in);
    }

    /**
     * Prints a formatted error message.
     */
    public void printError(String message) {
        response.append(message).append("\n");
    }

    /**
     * Prints a formatted message.
     */
    public void showMessage(String message) {
        response.append(message).append("\n");
    }

    /**
     * Returns the current command response and resets the response
     */
    public String getResponse() {
        String currentResponse = response.toString();
        response.setLength(0);
        return currentResponse;
    }

    /**
     * Returns the success message when a task is added
     */
    public void showTaskAdded(Task task, int size) {
        showMessage(Messages.MSG_TASK_ADD);
        showMessage(task.toString());
        showMessage(Messages.getTaskCountMessage(size));
    }

    /**
     * Returns the success message when a task is deleted
     */
    public void showTaskDelete(Task task, int size) {
        showMessage(Messages.MSG_TASK_DELETE);
        showMessage(task.toString());
        showMessage(Messages.getTaskCountMessage(size));
    }

    /**
     * Returns the current task(s) in taskList
     */
    public void showTaskList(TaskList taskList) {
        for (int i = 0; i < taskList.size(); i++) {
            showMessage((i + 1) + "." + taskList.get(i).toString());
        }
    }

    /**
     * Returns a string representation of a marked task
     */
    public void showMark(Task task) {
        showMessage(Messages.MSG_TASK_MARK);
        showMessage(task.toString());
    }

    /**
     * Returns a string representation of a unmarked task
     */
    public void showUnmark(Task task) {
        showMessage(Messages.MSG_TASK_UNMARK);
        showMessage(task.toString());
    }
}
