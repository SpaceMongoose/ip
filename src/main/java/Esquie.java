import java.util.Scanner;

/**
 * A Personal Assistant Chatbot that helps a person keep track of various things.
 */
public class Esquie {
    /** Array for storing tring entered by the user. */
    private static String[] taskList = new String[100];

    /** Counter for the number of task added. */
    private static int numberOfTasks = 0;

    /**
     * Prints the logo, welcome message and immediately exits.
     *
     * @param args Command line arguments passed to the program (not used).
     */
    public static void main(String[] args) {
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
        String breakLine = "--------------------------------------";
        String replyBreakLine = "    --------------------------------------";
        String indentation = "    ";
        System.out.println(logo + "\n" + breakLine);

        // Initial Conversation
        System.out.println("Bonjour mon ami! I'm Esquie\uD83D\uDE00" + "\nWhat can I do for you?");
        System.out.println(breakLine);

        // Loop to Scan for Inputs
        Scanner sc = new Scanner(System.in);
        while (true) {
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("bye")) {
                break;
            } else {
                taskList[numberOfTasks++] = input;
                System.out.println(indentation + replyBreakLine);
                System.out.println(indentation + indentation + "added: " + input);
                System.out.println(indentation + replyBreakLine);
            }
        }
        System.out.println(breakLine);
        System.out.println("Bye mon ami! Hope to see you again soon!");
    }
}
