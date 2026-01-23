/**
 * Represents the valid commands that Esquie can execute.
 */
public abstract class Command {
    public abstract void execute(TaskList taskList, Ui ui, Storage storage) throws EsquieException;
    public abstract boolean isExit();
}
