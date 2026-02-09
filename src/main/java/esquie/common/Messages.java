package esquie.common;

/**
 * Class for user-visible messages.
 */
public class Messages {

    // ERR MESSAGE
    public static final String ERR_TASKLIST_FULL = "Whoopsie! Number of tasks is full!";
    public static final String ERR_TASK_NOT_EXIST = "Whoopsie! This task does not exist";

    // SUCCESS MESSAGE
    public static final String MSG_TASK_ADD = "Got it. I've added this task:";
    public static final String MSG_TASK_DELETE = "Got it. I've removed this task:";

    private Messages() {};

    public static String getTaskCountMessage(int size) {
        return "Now you have " + size + " tasks in the list.";
    }
}
