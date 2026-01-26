package esquie.commands;

import esquie.tasks.TaskList;
import esquie.ui.Ui;
import esquie.storage.Storage;
import esquie.exceptions.EsquieException;

/**
 * Representation of a ExitCommand.
 */
public class ExitCommand extends Command {

    /**
     * Execute does not do anything for ExitCommand.
     * @param taskList is the tasklist object.
     * @param ui is the Ui object for user interaction.
     * @param storage is the Storage object for storage interaction.
     * */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws EsquieException {
        return;
    }

    /**
     * Returns true to exit the program.
     * @return returns true to indicate program exit.
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
