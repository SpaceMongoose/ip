import java.util.Scanner;

/**
 * A Personal Assistant Chatbot that helps a person keep track of various things.
 */
public class Esquie {
    /** Array (with 100 limit) for storing tasks entered by the user. */
    private static Task[] taskList = new Task[100];

    /** Counter for the number of tasks added. */
    private static int numberOfTasks = 0;

    /** Constants used for standardized formatting. */
    private static final String BREAKLINE = "--------------------------------------";
    private static final String REPLYBREAKLINE = "    --------------------------------------";
    private static final String INDENTATION = "    ";

    public static void main(String[] args) {

        // Prints the Welcome Message
        printWelcome();

        // Loop to Scan for Inputs
        Scanner sc = new Scanner(System.in);
        while (true) {
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("bye")) {
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
    public static void printWelcome() {
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
    public static void printExit() {
        System.out.println(BREAKLINE);
        System.out.println("Bye mon ami! Hope to see you again soon!");
    }

    /**
     * Handles user input and either adds to task list, or display task list.
     *
     * @param input Either command, or task entered by user.
     */
    public static void inputHandler(String input) {
        System.out.println(INDENTATION + REPLYBREAKLINE);
        if (input.equalsIgnoreCase("list")) {
            for (int i = 0; i < numberOfTasks; i++) {
                System.out.println(INDENTATION + INDENTATION + (i + 1) + "." + taskList[i].toString());
            }
        } else {
            taskList[numberOfTasks++] = new Task(input);
            System.out.println(INDENTATION + INDENTATION + "added: " + input);
        }
        System.out.println(INDENTATION + REPLYBREAKLINE);
    }
}
