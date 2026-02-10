package esquie.common;

/**
 * Class for user-visible messages.
 */
public class Messages {

    // ERR MESSAGE
    public static final String ERR_TASKLIST_FULL = "Whoopsie! Number of tasks is full!";
    public static final String ERR_TASK_NOT_EXIST = "Whoopsie! This task does not exist";
    public static final String ERR_TASK_NOT_FOUND = "Whoopsie! Nothing could be found";

    // SUCCESS MESSAGE
    public static final String MSG_TASK_ADD = "Got it. I've added this task:";
    public static final String MSG_TASK_DELETE = "Got it. I've removed this task:";
    public static final String MSG_TASK_LIST = "Listing Current Tasks:";
    public static final String MSG_TASK_FIND = "Here are the matching tasks in your list:";
    public static final String MSG_TASK_MARK = "WhooWhee! I've marked this task as done:";
    public static final String MSG_TASK_UNMARK = "WhooWhee! I've marked this task as not done yet:";

    private Messages() {};

    public static String getTaskCountMessage(int size) {
        return "Now you have " + size + " tasks in the list.";
    }
}
