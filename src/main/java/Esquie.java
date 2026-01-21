import java.util.Scanner;
import java.util.ArrayList;

/**
 * A Personal Assistant Chatbot that helps a person keep track of various things.
 */
public class Esquie {
    private ArrayList<Task> taskList;

    /** Constants used for standardized formatting. */
    private static final String BREAKLINE = "--------------------------------------";
    private static final String DOUBLEINDENTATION = "        ";

    /**
     * Initialize Esquie with empty task list (up to 100 items) and a counter for the number of tasks.
     */
    public Esquie() {
        this.taskList = new ArrayList<>();
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
            String[] input = sc.nextLine().trim().split(" ", 2);
            if (input[0].equalsIgnoreCase("bye")) {
                break;
            } else {
                try {
                    inputHandler(input);
                } catch (EsquieException e) {
                    System.out.println(e.getMessage());
                    System.out.println(DOUBLEINDENTATION + BREAKLINE);
                }
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
    private void inputHandler(String[] input) throws EsquieException {
        System.out.println(DOUBLEINDENTATION + BREAKLINE);
        try {
            Command cmd = Command.valueOf(input[0].toUpperCase());

            switch (cmd) {
                case LIST:
                    listHandler();
                    break;
                case MARK, UNMARK:
                    markHandler(input);
                    break;
                case TODO:
                    todoHandler(input);
                    break;
                case DEADLINE:
                    deadlineHandler(input);
                    break;
                case EVENT:
                    eventHandler(input);
                    break;
                case DELETE:
                    deleteHandler(input);
                    break;
            }
        } catch (IllegalArgumentException e) {
            throw new EsquieException(DOUBLEINDENTATION + "Esquie did not understand that!");
        }
        System.out.println(DOUBLEINDENTATION + BREAKLINE);
    }

    /**
     * Executes the mark command by either marking or unmarking a task.
     *
     * @param input A String array that is split from user input. Should contain command and task description.
     */
    private void markHandler(String[] input) throws EsquieException {
        // Error Checking
        // input length is minimally 2 i.e. command and taskNumber
        if (input.length < 2) {
            throw new EsquieException(DOUBLEINDENTATION
                    + "Whoopsie! command is missing an argument!"
                    + "\n" + DOUBLEINDENTATION + "Example Usage: mark 1 OR unmark 1");
        }

        try {
            // Checks if the 2nd number is Integer or not
            // Integer.parseInt returns NumberFormatException if fails
            int taskNumber = Integer.parseInt(input[1].trim()) - 1;
            boolean isMark = input[0].equalsIgnoreCase("mark");
            toggleMarkStatus(taskNumber, isMark);
        } catch (NumberFormatException e) {
            throw new EsquieException(DOUBLEINDENTATION
                    + "You didnt give me a number... Esquie is now sad :("
                    + "\n" + DOUBLEINDENTATION + "Example Usage: mark 1 OR unmark 1");

        }
    }

    /**
     * Based on User input, determines which task to mark or unmark.
     *
     * @param taskNumber the task to interact with in the taskList
     * @param isMark true = mark, false = unmark
     * */
    private void toggleMarkStatus(int taskNumber, boolean isMark) throws EsquieException {
        // Error Checking
        if (taskNumber < 0 || taskNumber >= taskList.size()) {
            throw new EsquieException(DOUBLEINDENTATION + "Whoopsie! This task does not exist");
        }

        Task currentTask = taskList.get(taskNumber);
        if (isMark) {
            currentTask.markComplete();
            System.out.println(DOUBLEINDENTATION + "WhooWhee! I've marked this task as done:");

        } else {
            currentTask.markIncomplete();
            System.out.println(DOUBLEINDENTATION  + "WhooWhee! I've marked this task as not done yet:");

        }
        System.out.println(DOUBLEINDENTATION + currentTask.toString());
    }

    /**
     * Method to List all current tasks in the Tasklist.
     *
     */
    private void listHandler() {
        System.out.println(DOUBLEINDENTATION + "Listing Current Tasks:");
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println(DOUBLEINDENTATION + (i + 1) + "." + taskList.get(i).toString());
        }
    }

    /**
     * Adds a task to taskList and prints success message.
     *
     * @param task The task object to be added.
     */
    private void taskHandler(Task task) throws EsquieException {
        if (taskList.size() >= 100) {
            throw new EsquieException(DOUBLEINDENTATION + "Whoopsie! Number of tasks is full!");
        }
        taskList.add(task);
        System.out.println(DOUBLEINDENTATION + "Got it. I've added this task:");
        System.out.println(DOUBLEINDENTATION + taskList.get(taskList.size() - 1).toString());
        System.out.println(DOUBLEINDENTATION + "Now you have " + taskList.size() + " tasks in the list.");
    }

    /**
     * Executes the todo command by adding a new todo task.
     *
     * @param input A String array that is split from user input. Should contain command and task description.
     */
    private void todoHandler(String[] input) throws EsquieException {
        if (input.length < 2 || input[1].trim().isEmpty()) {
            throw new EsquieException(DOUBLEINDENTATION
                    + "Whoopsie! Something is missing from the todo command!"
                    + "\n" + DOUBLEINDENTATION + "Example Usage: todo borrow book");
        }

        Task task = new Todo(input[1].trim());
        taskHandler(task);
    }

    /**
     * Executes the deadline command by adding a new deadline task.
     *
     * @param input A String array that is split from user input. Contains command, task description and deadline.
     */
    private void deadlineHandler(String[] input) throws EsquieException {
        if (input.length < 2) {
            throw new EsquieException(DOUBLEINDENTATION
                    + "Whoopsie! Something is missing from the deadline command!"
                    + "\n" + DOUBLEINDENTATION + "Example Usage: deadline return book /by Sunday");
        }

        // e.g. return book /by Sunday
        String[] byInput = input[1].split(" /by ", 2);

        if (byInput.length < 2 || byInput[0].trim().isEmpty() || byInput[1].trim().isEmpty()) {
            throw new EsquieException(DOUBLEINDENTATION
                    + "Whoopsie! Either your task or date is missing!"
                    + "\n" + DOUBLEINDENTATION + "Example Usage: deadline return book /by Sunday");
        }

        Task task = new Deadline(byInput[0].trim(), byInput[1].trim());
        taskHandler(task);
    }

    /**
     * Executes the event command by adding a new event task.
     *
     * @param input A String array that is split from user input. Contains command, task description and deadline.
     */
    private void eventHandler(String[] input) throws EsquieException {
        if (input.length < 2) {
            throw new EsquieException(DOUBLEINDENTATION
                    + "Whoopsie! Something is wrong with the event command!"
                    + "\n" + DOUBLEINDENTATION + "Example Usage: event project meeting /from Mon 2pm /to 4pm");
        }

        // e.g. project meeting /from Mon 2pm /to 4pm

        // This splits the description and time
        String[] splitFrom = input[1].split(" /from ", 2);
        if (splitFrom.length < 2 || splitFrom[0].trim().isEmpty() || splitFrom[1].trim().isEmpty()) {
            throw new EsquieException(DOUBLEINDENTATION
                    + "Whoopsie, something is wrong with the event command! \n"
                    + DOUBLEINDENTATION
                    + "Either a task description or time is missing!"
                    + "\n" + DOUBLEINDENTATION + "Example Usage: event project meeting /from Mon 2pm /to 4pm");
        }
        String description = splitFrom[0];
        String date = splitFrom[1]; // Mon 2pm /to 4pm

        // This further splits the time to obtain from and to
        String[] splitTo = date.split(" /to ", 2);
        if (splitTo.length < 2 || splitTo[0].trim().isEmpty() || splitTo[1].trim().isEmpty()) {
            throw new EsquieException(DOUBLEINDENTATION
                    + "Whoopsie, something is wrong with the event command!\n"
                    + DOUBLEINDENTATION
                    + "Either the from or to timing is missing from the event command!"
                    + "\n" + DOUBLEINDENTATION + "Example Usage: event project meeting /from Mon 2pm /to 4pm");
        }

        Task task = new Event(description.trim(), splitTo[0].trim(), splitTo[1].trim());
        taskHandler(task);
    }

    /**
     * Executes the delete command by deleting a task.
     *
     * @param input A String array that is split from user input.
     */
    private void deleteHandler(String[] input) throws EsquieException {
        if (input.length < 2) {
            throw new EsquieException(DOUBLEINDENTATION
                    + "Whoopsie! Something is wrong with the delete command!"
                    + "\n" + DOUBLEINDENTATION + "Example Usage: delete 3");
        }

        try {
            Task removedTask = taskList.remove(Integer.parseInt(input[1].trim()) - 1);
            System.out.println(DOUBLEINDENTATION + "Got it. I've removed this task:");
            System.out.println(DOUBLEINDENTATION + removedTask.toString());
            System.out.println(DOUBLEINDENTATION + "Now you have " + taskList.size() + " tasks in the list.");

        } catch (IndexOutOfBoundsException e) {
            throw new EsquieException(DOUBLEINDENTATION + "Whoopsie! Task does not exist");
        } catch (NumberFormatException e) {
            throw new EsquieException(DOUBLEINDENTATION
                    + "Whoopsie! You did not give me a proper number!"
                    + "\n" + DOUBLEINDENTATION + "Example Usage: delete 3");
        }
    }
}
