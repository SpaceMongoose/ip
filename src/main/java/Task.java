/**
 * Task class that contains a description and a completion status.
 *
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Initializes new Task with given description.
     * The initialized task is not done by default (i.e. isDone = false).
     *
     * @param description Text describing the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the current status of completion in String.
     * "X" indicates completion, " " indicates not complete.
     *
     * @return icon "X" or " ".
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Marks the task as complete.
     */
    public void markComplete() {
        this.isDone = true;
    }

    /**
     * Marks the task as incomplete.
     */
    public void markIncomplete() {
        this.isDone = false;
    }

    /**
    * Returns the task description.
    *
    * @return The description of the task.
    */
    public String getDescription() {
        return this.description;
    }


    /**
     * Returns a complete string with both current task status and task description.
     *
     * @return A formatted string (e.g. [] read book or [X] burn book).
     *
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + getDescription();
    }
}
