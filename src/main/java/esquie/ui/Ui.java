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
     * Reads and returns input from the user.
     */
    public String readCommand() {
        return sc.nextLine().trim();
    }

    /**
     * Prints the logo and welcome message to the User.
     *
     */
    public void printWelcome() {
        String logo = """
                  ______                 _     \s
                 |  ____|               (_)    \s
                 | |__   ___  __ _ _   _ _  ___\s
                 |  __| / __|/ _` | | | | |/ _ \\
                 | |____\\__ \\ (_| | |_| | |  __/
                 |______|___/\\__, |\\__,_|_|\\___|
                                | |            \s
                                |_|             \
                """;

        System.out.println(logo + "\n" + BREAKLINE);

        // Initial Conversation
        System.out.println("Bonjour mon ami! I'm Esquie\uD83D\uDE00" + "\nWhat can I do for you?");
        System.out.println(BREAKLINE);
    }

    /**
     * Prints the exit message.
     *
     */
    public void printExit() {
        System.out.println(DOUBLEINDENTATION + "Bye mon ami! Hope to see you again soon!");
    }

    /**
     * Prints an indented line that acts as a separator.
     */
    public void printLine() {
        System.out.println(DOUBLEINDENTATION + BREAKLINE);
    }

    /**
     * Prints a formatted error message.
     */
    public void printError(String message) {
        // System.out.println(DOUBLEINDENTATION + message);
        response.append(message).append("\n");
    }

    /**
     * Returns a DOUBLEINDENTATION for formatting reasons.
     */
    public String printIndent() {
        return DOUBLEINDENTATION;
    }

    /**
     * Prints a formatted message.
     */
    public void showMessage(String message) {
        // System.out.println(DOUBLEINDENTATION + message);
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
}
