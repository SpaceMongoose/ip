import java.util.Scanner;

/**
 * A Personal Assistant Chatbot that helps a person keep track of various things.
 */
public class Esquie {
    private Task[] taskList;
    private int numberOfTasks;

    /** Constants used for standardized formatting. */
    private static final String BREAKLINE = "--------------------------------------";
    private static final String REPLYBREAKLINE = "    --------------------------------------";
    private static final String INDENTATION = "    ";

    /**
     * Initialize Esquie with empty task list (up to 100 items) and a counter for the number of tasks.
     */
    public Esquie() {
        this.taskList = new Task[100];
        this.numberOfTasks = 0;
    }

    public static void main(String[] args) {
        new Esquie().run();
    }

    /**
     * Starts the main execution of Esquie.
     * Displays welcome message and loops to read user input to call the correct methods.
     * Loop is continued until Esquie reads "bye".
     */
    public void run() {
        printWelcome();
        Scanner sc = new Scanner(System.in);
        while (true) {
            String[] input = sc.nextLine().split(" ", 2);
            if (input[0].equalsIgnoreCase("bye")) {
                break;
            } else {
                inputHandler(input);
            }
        }
        printExit();
    }

    /**
     * Prints the logo and welcome message to the User.
     *
     */
    private void printWelcome() {
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
    private void printExit() {
        System.out.println(BREAKLINE);
        System.out.println("Bye mon ami! Hope to see you again soon!");
    }


    /**
     * Handles user input and either adds to task list, display task list, mark and unmark task.
     *
     * @param input A String array that contains the commands and parameters specified by User
     */
    private void inputHandler(String[] input) {
        System.out.println(INDENTATION + REPLYBREAKLINE);
        if (input[0].equalsIgnoreCase("list")) {
            listHandler();
        } else if (input[0].equalsIgnoreCase("mark") || input[0].equalsIgnoreCase("unmark")) {
            markHandler(input);
        } else if (input[0].equalsIgnoreCase("todo")) {
            todoHandler(input);
        } else if (input[0].equalsIgnoreCase("deadline")) {
            deadlineHandler(input);
        } else if (input[0].equalsIgnoreCase("event")) {
            eventHandler(input);
        } else {
            System.out.println(INDENTATION + INDENTATION + "Esquie did not understand that!");
        }
        System.out.println(INDENTATION + REPLYBREAKLINE);
    }

    /**
     * Executes the mark command by either marking or unmarking a task.
     * Method does nothing if input is not minimally length 2.
     *
     * @param input A String array that is split from user input. Should contain command and task description.
     */
    private void markHandler(String[] input) {
        // Error Checking
        // input length is minimally 2 i.e. command and taskNumber
        if (input.length < 2) {
            System.out.println(INDENTATION + INDENTATION + "WhooWhee?? Please check the command!");
            System.out.println(INDENTATION + REPLYBREAKLINE);
            return;
        }

        try {
            // Checks if the 2nd number is Integer or not
            // Integer.parseInt returns NumberFormatException if fails
            int taskNumber = Integer.parseInt(input[1]) - 1;
            boolean isMark = input[0].equalsIgnoreCase("mark");
            toggleMarkStatus(taskNumber, isMark);
        } catch (NumberFormatException e) {
            System.out.print(INDENTATION + INDENTATION + "You didnt give me a number... Esquie is now sad\n");
        }
    }

    /**
     * Based on User input, determines which task to mark or unmark.
     *
     * @param taskNumber the task to interact with in the taskList
     * @param isMark true = mark, false = unmark
     * */
    private void toggleMarkStatus(int taskNumber, boolean isMark) {
        // Error Checking
        if (taskNumber < 0 || taskNumber >= numberOfTasks) {
            System.out.println(INDENTATION + INDENTATION + "I think you did an oopsie! That does not exist");
            return;
        }

        Task currentTask = taskList[taskNumber];
        if (isMark) {
            currentTask.markComplete();
            System.out.println(INDENTATION + INDENTATION + "WheeWhoo! I've marked this task as done:");

        } else {
            currentTask.markIncomplete();
            System.out.println(INDENTATION + INDENTATION + "WhooWhee! I've marked this task as not done yet:");

        }
        System.out.println(INDENTATION + INDENTATION + currentTask.toString());
    }

    /**
     * Method to List all current tasks in the Tasklist.
     *
     */
    private void listHandler() {
        System.out.println(INDENTATION + INDENTATION + "Listing Current Tasks:");
        for (int i = 0; i < numberOfTasks; i++) {
            System.out.println(INDENTATION + INDENTATION + (i + 1) + "." + taskList[i].toString());
        }
    }

    /**
     * Adds a task to taskList and prints success message.
     *
     * @param task The task object to be added.
     */
    private void taskHandler(Task task) {
        if (numberOfTasks >= 100) {
            System.out.println(INDENTATION + INDENTATION + "WhooWhee?? Number of tasks is full!");
            return;
        }
        taskList[numberOfTasks] = task;
        System.out.println(INDENTATION + INDENTATION + "Got it. I've added this task:");
        System.out.println(INDENTATION + INDENTATION + taskList[numberOfTasks].toString());
        numberOfTasks++;
        System.out.println(INDENTATION + INDENTATION + "Now you have " + numberOfTasks + " tasks in the list.");
    }

    /**
     * Executes the todo command by adding a new todo task.
     * Method does nothing if input is not minimally length 2.
     *
     * @param input A String array that is split from user input. Should contain command and task description.
     */
    private void todoHandler(String[] input) {
        if (input.length < 2 || input[1].trim().isEmpty()) {
            System.out.println(INDENTATION + INDENTATION + "WhooWhee?? Please check the command!");
            return;
        }

        Task task = new Todo(input[1].trim());
        taskHandler(task);
    }

    /**
     * Executes the deadline command by adding a new deadline task.
     * Method does nothing if input is not minimally length 2.
     *
     * @param input A String array that is split from user input. Contains command, task description and deadline.
     */
    private void deadlineHandler(String[] input) {
        if (input.length < 2) {
            System.out.println(INDENTATION + INDENTATION + "WhooWhee?? Please check the command!");
            return;
        }

        // e.g. return book /by Sunday
        String[] byInput = input[1].split(" /by ", 2);

        if (byInput.length < 2 || byInput[0].trim().isEmpty()) {
            System.out.println(INDENTATION + INDENTATION + "WhooWhee?? Please check the command!");
            return;
        }

        Task task = new Deadline(byInput[0].trim(), byInput[1].trim());
        taskHandler(task);
    }

    /**
     * Executes the event command by adding a new event task.
     * Method does nothing if input is not minimally length 2.
     *
     * @param input A String array that is split from user input. Contains command, task description and deadline.
     */
    private void eventHandler(String[] input) {
        if (input.length < 2) {
            System.out.println(INDENTATION + INDENTATION + "WhooWhee?? Please check the command!");
            return;
        }

        // e.g. project meeting /from Mon 2pm /to 4pm

        // This splits the description and time
        String[] splitFrom = input[1].split(" /from ", 2);
        if (splitFrom.length < 2) {
            System.out.println(INDENTATION + INDENTATION + "WhooWhee?? Please check the command!");
            return;
        }
        String description = splitFrom[0];
        String date = splitFrom[1]; // Mon 2pm /to 4pm

        // This further splits the time to obtain from and to
        String[] splitTo = date.split(" /to ", 2);
        if (splitTo.length < 2) {
            System.out.println(INDENTATION + INDENTATION + "WhooWhee?? Please check the command!");
            return;
        }

        Task task = new Event(description.trim(), splitTo[0].trim(), splitTo[1].trim());
        taskHandler(task);
    }
}
