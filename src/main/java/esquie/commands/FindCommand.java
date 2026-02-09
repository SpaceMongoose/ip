package esquie.commands;

import esquie.exceptions.EsquieException;
import esquie.storage.Storage;
import esquie.tasks.TaskList;
import esquie.ui.Ui;

/**
 * Represents a FindCommand.
 */
public class FindCommand extends Command {
    private String itemToFind;

    /**
     * Constructs a FindCommand that finds related tasks based on a keyword.
     * @param itemToFind is the keyword to check for within the taskList
     */
    public FindCommand(String itemToFind) {
        this.itemToFind = itemToFind;
    }


    /**
     * Retrieves the tasks related to the keyword
     * @param taskList is the tasklist to read through.
     * @param ui is the Ui object for user interaction.
     * @param storage is the Storage object for storage interaction.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws EsquieException {
        TaskList findList = taskList.find(this.itemToFind);

        if (findList.size() == 0) {
            throw new EsquieException("Nothing could be found!");
        }

        list(findList, ui);
    }

    /**
     * Lists tasks related to the find keyword
     * @param taskList is the tasklist to read through.
     * @param ui is the Ui object for user interaction.
     */
    public void list(TaskList taskList, Ui ui) {
        ui.showMessage("Here are the matching tasks in your list:");
        for (int i = 0; i < taskList.size(); i++) {
            ui.showMessage((i + 1) + "." + taskList.get(i).toString());
        }
    }

    /**
     * Returns false.
     * @return returns true/false to indicate program exit.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
