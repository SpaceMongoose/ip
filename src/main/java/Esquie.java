/**
 * Esquie, a Personal Assistant Chatbot that helps a person keep track of various things
 * */
public class Esquie {
    /**
     * Prints the logo, welcome message and immediately exits
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
        String breakLine = "\n--------------------------------------";
        System.out.println(logo + breakLine);

        // Initial Conversation
        System.out.println("Bonjour mon ami! I'm Esquie\uD83D\uDE00" + "\nWhat can I do for you?");
        System.out.println(breakLine);
        System.out.println("Bye mon ami! Hope to see you again soon!");
    }
}
