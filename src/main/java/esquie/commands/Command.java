package esquie.commands;

import esquie.tasks.TaskList;
import esquie.ui.Ui;
import esquie.storage.Storage;
import esquie.exceptions.EsquieException;

/**
 * Represents the valid commands that Esquie can execute.
 */
public abstract class Command {
    public abstract void execute(TaskList taskList, Ui ui, Storage storage) throws EsquieException;
    public abstract boolean isExit();
}
