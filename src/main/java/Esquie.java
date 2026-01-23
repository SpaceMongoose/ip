import java.time.format.DateTimeParseException;

/**
 * A Personal Assistant Chatbot that helps a person keep track of various things.
 */
public class Esquie {
    private TaskList taskList;
    private Ui ui;
    private Storage storage;

    /**
     * Initialize Esquie with empty task list (up to 100 items) and a counter for the number of tasks.
     * Check if save file exists.
     */
    public Esquie(String filePath) throws EsquieException {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        try {
            this.taskList = new TaskList(storage.loadTasks());
            storage.checkSave();

        } catch (EsquieException e) {
            ui.printError(e.getMessage());
            this.taskList = new TaskList(); // Start with empty list if something went wrong
        }
    }

    public static void main(String[] args) {
        try {
            new Esquie("./data/esquie.txt").run();
        } catch (EsquieException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Starts the main execution of Esquie.
     * Displays welcome message and loops to read user input to call the correct methods.
     * Loop is continued until Esquie reads "bye".
     */
    public void run() {
        ui.printWelcome();
        while (true) {
            String[] input = ui.readCommand().split(" ", 2);
            if (input[0].equalsIgnoreCase("bye")) {
                break;
            } else {
                try {
                    inputHandler(input);
                } catch (EsquieException e) {
                    ui.printError(e.getMessage());
                    ui.printLine();
                }
            }
        }
        ui.printExit();
    }

    /**
     * Handles user input and either adds to task list, display task list, mark and unmark task.
     *
     * @param input A String array that contains the commands and parameters specified by User
     */
    private void inputHandler(String[] input) throws EsquieException {
        ui.printLine();
        try {
            Command cmd = Command.valueOf(input[0].toUpperCase());

            switch (cmd) {
            case LIST -> listHandler();
            case MARK, UNMARK -> markHandler(input);
            case TODO -> todoHandler(input);
            case DEADLINE -> deadlineHandler(input);
            case EVENT -> eventHandler(input);
            case DELETE -> deleteHandler(input);
            }
        } catch (IllegalArgumentException e) {
            throw new EsquieException("Esquie did not understand that!");
        }
        ui.printLine();
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
            throw new EsquieException("Whoopsie! command is missing an argument!"
                    + "\n" + ui.printIndent() + "Example Usage: mark 1 OR unmark 1");
        }

        try {
            // Checks if the 2nd number is Integer or not
            // Integer.parseInt returns NumberFormatException if fails
            int taskNumber = Integer.parseInt(input[1].trim()) - 1;
            boolean isMark = input[0].equalsIgnoreCase("mark");
            toggleMarkStatus(taskNumber, isMark);
        } catch (NumberFormatException e) {
            throw new EsquieException("You didnt give me a number... Esquie is now sad :("
                    + "\n" + ui.printIndent() + "Example Usage: mark 1 OR unmark 1");

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
            throw new EsquieException("Whoopsie! This task does not exist");
        }

        Task currentTask = taskList.get(taskNumber);
        if (isMark) {
            currentTask.markComplete();
            storage.overwriteAll(taskList);
            ui.showMessage("WhooWhee! I've marked this task as done:");

        } else {
            currentTask.markIncomplete();
            storage.overwriteAll(taskList);
            ui.showMessage("WhooWhee! I've marked this task as not done yet:");

        }
        ui.showMessage(currentTask.toString());
    }

    /**
     * List all current tasks in the Tasklist.
     *
     */
    private void listHandler() {
        ui.showMessage("Listing Current Tasks:");
        for (int i = 0; i < taskList.size(); i++) {
            ui.showMessage((i + 1) + "." + taskList.get(i).toString());
        }
    }

    /**
     * Adds a task to taskList and prints success message.
     *
     * @param task The task object to be added.
     */
    private void taskHandler(Task task) throws EsquieException {
        if (taskList.size() >= 100) {
            throw new EsquieException("Whoopsie! Number of tasks is full!");
        }
        taskList.add(task);
        storage.writeTask(task);
        ui.showMessage("Got it. I've added this task:");
        ui.showMessage(taskList.get(taskList.size() - 1).toString());
        ui.showMessage("Now you have " + taskList.size() + " tasks in the list.");
    }

    /**
     * Executes the todo command by adding a new todo task.
     *
     * @param input A String array that is split from user input. Should contain command and task description.
     */
    private void todoHandler(String[] input) throws EsquieException {
        if (input.length < 2 || input[1].trim().isEmpty()) {
            throw new EsquieException("Whoopsie! Something is missing from the todo command!"
                    + "\n" + ui.printIndent() + "Example Usage: todo borrow book");
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
            throw new EsquieException("Whoopsie! Something is missing from the deadline command!"
                    + "\n" + ui.printIndent() + "Example Usage: deadline Play E33 /by 2026-01-25 1750"
                    + "\n" + ui.printIndent() + "Example Usage: deadline Play E33 /by 2026-01-25");
        }

        // e.g. return book /by Sunday
        String[] byInput = input[1].split(" /by ", 2);

        if (byInput.length < 2 || byInput[0].trim().isEmpty() || byInput[1].trim().isEmpty()) {
            throw new EsquieException("Whoopsie! Either your task or date is missing!"
                    + "\n" + ui.printIndent() + "Example Usage: deadline Play E33 /by 2026-01-25 1750");
        }
        try {
            Task task = new Deadline(byInput[0].trim(), byInput[1].trim());
            taskHandler(task);
        } catch (DateTimeParseException e) {
            throw new EsquieException("Oopsie! Please enter the date in yyyy-MM-dd or yyyy-MM-dd HHmm format!"
                    + "\n" + ui.printIndent()
                    + "Example Usage: /by 2026-01-25 OR /by 2026-01-25 1750");
        }
    }

    /**
     * Executes the event command by adding a new event task.
     *
     * @param input A String array that is split from user input. Contains command, task description and deadline.
     */
    private void eventHandler(String[] input) throws EsquieException {
        if (input.length < 2) {
            throw new EsquieException("Whoopsie! Something is wrong with the event command!"
                    + "\n" + ui.printIndent()
                    + "Example Usage: event Play E33 /from 2026-01-25 1300 /to 2026-01-25 1800"
                    + "\n" + ui.printIndent()
                    + "Example Usage: event Play E33 /from 2026-01-25 /to 2026-01-25");
        }

        // e.g. project meeting /from Mon 2pm /to 4pm

        // This splits the description and time
        String[] splitFrom = input[1].split(" /from ", 2);
        if (splitFrom.length < 2 || splitFrom[0].trim().isEmpty() || splitFrom[1].trim().isEmpty()) {
            throw new EsquieException("Whoopsie, something is wrong with the event command! \n"
                    + ui.printIndent()
                    + "Either a task description or time is missing!"
                    + "\n" + ui.printIndent()
                    + "Example Usage: event Play E33 /from 2026-01-25 1300 /to 2026-01-25 1800"
                    + "\n" + ui.printIndent()
                    + "Example Usage: event Play E33 /from 2026-01-25 /to 2026-01-25");
        }
        String description = splitFrom[0];
        String date = splitFrom[1];

        // This further splits the time to obtain from and to
        String[] splitTo = date.split(" /to ", 2);
        if (splitTo.length < 2 || splitTo[0].trim().isEmpty() || splitTo[1].trim().isEmpty()) {
            throw new EsquieException("Whoopsie, something is wrong with the event command!\n"
                    + ui.printIndent()
                    + "Either the from or to timing is missing from the event command!"
                    + "\n" + ui.printIndent()
                    + "Example Usage: event Play E33 /from 2026-01-25 1300 /to 2026-01-25 1800"
                    + "\n" + ui.printIndent()
                    + "Example Usage: event Play E33 /from 2026-01-25 /to 2026-01-25");
        }

        try {
            Task task = new Event(description.trim(), splitTo[0].trim(), splitTo[1].trim());
            taskHandler(task);
        } catch (DateTimeParseException e) {
            throw new EsquieException("Oopsie! Please enter the date in yyyy-MM-dd or yyyy-MM-dd HHmm format!"
                    + "\n" + ui.printIndent()
                    + "Example Usage: event Play E33 /from 2026-01-25 1300 /to 2026-01-25 1800"
                    + "\n" + ui.printIndent()
                    + "Example Usage: event Play E33 /from 2026-01-25 /to 2026-01-25");
        }
    }

    /**
     * Executes the delete command by deleting a task.
     *
     * @param input A String array that is split from user input.
     */
    private void deleteHandler(String[] input) throws EsquieException {
        if (input.length < 2) {
            throw new EsquieException("Whoopsie! Something is wrong with the delete command!"
                    + "\n" + ui.printIndent() + "Example Usage: delete 3");
        }

        try {
            Task removedTask = taskList.delete(Integer.parseInt(input[1].trim()) - 1);
            storage.overwriteAll(taskList);
            ui.showMessage("Got it. I've removed this task:");
            ui.showMessage(removedTask.toString());
            ui.showMessage("Now you have " + taskList.size() + " tasks in the list.");

        } catch (IndexOutOfBoundsException e) {
            throw new EsquieException("Whoopsie! Task does not exist");
        } catch (NumberFormatException e) {
            throw new EsquieException("Whoopsie! You did not give me a proper number!"
                    + "\n" + ui.printIndent() + "Example Usage: delete 3");
        }
    }


}
