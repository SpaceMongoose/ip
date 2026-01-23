package esquie;

import esquie.tasks.TaskList;
import esquie.ui.Ui;
import esquie.storage.Storage;
import esquie.parser.Parser;
import esquie.commands.Command;
import esquie.exceptions.EsquieException;


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
     * Loop is continued until Esquie reads BYE command is executed
     */
    public void run() {
        ui.printWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.printLine();
                Command command = Parser.parse(fullCommand);
                command.execute(taskList, ui, storage);
                isExit = command.isExit();
                if (isExit) {ui.printExit();}
            } catch (EsquieException e) {
                ui.printError(e.getMessage());

            } finally {
                ui.printLine();
            }
        }
    }
}