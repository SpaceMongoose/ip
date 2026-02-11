package esquie;

import esquie.commands.Command;
import esquie.common.Messages;
import esquie.exceptions.EsquieException;
import esquie.parser.Parser;
import esquie.storage.Storage;
import esquie.tasks.TaskList;
import esquie.ui.Ui;

import javafx.application.Platform;

import java.io.File;

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

    /**
     * Generates a response for the user's chat message.
     * @return A response on the UI for the user to read
     */
    public String getResponse(String input) {
        boolean isExit = false;
        try {
            // 1. Parse the user input into a Command
            Command command = Parser.parse(input);

            // 2. Execute the command
            // Call ui.showMessage, which now writes to our buffer instead of the console
            command.execute(taskList, ui, storage);
            isExit = command.isExit();
            if (isExit) {
                Platform.exit();
                System.exit(0);
            }

            // 3. Return the stored text from the buffer
            return ui.getResponse();

        } catch (EsquieException e) {
            // If an error happens, we return the error message directly
            return e.getMessage();
        }
    }

    /**
     * Updates the current Storage object based on the new file path
     * @param directoryPath is the file path to save/read esquie.txt from
     * */
    public void updateSaveLocation(String directoryPath) throws EsquieException {
        try {
            String fullPath = directoryPath + File.separator + "esquie.txt";

            // Load new file path and load tasks again
            Storage tempStorage = new Storage(fullPath);
            TaskList tempTaskList = new TaskList(tempStorage.loadTasks());

            // Update if reading and loading without error
            this.storage = tempStorage;
            this.taskList = tempTaskList;
            storage.checkSave();

        } catch (EsquieException e) {
            throw new EsquieException(Messages.ERR_STORAGE_IMPORT_FILE);
        }
    }
}
