package esquie.commands;

import esquie.exceptions.EsquieException;
import esquie.storage.Storage;
import esquie.tasks.TaskList;
import esquie.ui.Ui;

/**
 * Representation of a ListCommand.
 */
public class ListCommand extends Command {
    /**
     * Lists the current tasks in the tasklist.
     * @param taskList is the tasklist to read through.
     * @param ui is the Ui object for user interaction.
     * @param storage is the Storage object for storage interaction.
     * */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws EsquieException {
        ui.showMessage("Listing Current Tasks:");
        for (int i = 0; i < taskList.size(); i++) {
            ui.showMessage((i + 1) + "." + taskList.get(i).toString());
        }
    }

    /**
     * Returns false for this specific command.
     * @return returns true/false to indicate program exit.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
