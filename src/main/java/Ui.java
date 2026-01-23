import java.util.Scanner;

public class Ui {
    private Scanner sc;
    /** Constants used for standardized formatting. */
    private static final String BREAKLINE = "--------------------------------------";
    private static final String DOUBLEINDENTATION = "        ";

    public Ui() {
        this.sc = new Scanner(System.in);
    }

    /**
     * Reads and returns input from the user
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
        System.out.println(BREAKLINE);
        System.out.println("Bye mon ami! Hope to see you again soon!");
    }

    /**
     * Prints an indented line that acts as a seperator
     */
    public void printLine() {
        System.out.println(DOUBLEINDENTATION + BREAKLINE);
    }

    /**
     * Prints a formatted error message
     */
    public void printError(String message) {
        System.out.println(DOUBLEINDENTATION + message);
    }

    /**
     * Returns a DOUBLEINDENTATION for formatting reasons
     */
    public String printIndent() {
        return DOUBLEINDENTATION;
    }

    /**
     * Prints a formatted message
     */
    public void showMessage(String message) {
        System.out.println(DOUBLEINDENTATION + message);
    }
}
