package esquie.commands;

import esquie.common.Messages;
import esquie.exceptions.EsquieException;
import esquie.storage.Storage;
import esquie.tasks.Task;
import esquie.tasks.TaskList;
import esquie.ui.Ui;

/**
 * Representation of a MarkCommand.
 */
public class MarkCommand extends Command {
    private final int taskNumber;
    private final boolean isMark;

    /**
     * Constructs a MarkCommand with the task to mark / unmark.
     * @param taskNumber is the task index to mark/unmark.
     * @param isMark is to indicate if we should mark or unmark the task.
     */
    public MarkCommand(int taskNumber, boolean isMark) {
        this.taskNumber = taskNumber;
        this.isMark = isMark;
    }

    /**
     * Mark or Unmark the current task.
     * @param taskList is the tasklist to read through.
     * @param ui is the Ui object for user interaction.
     * @param storage is the Storage object for storage interaction.
     * */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws EsquieException {
        // Error Checking
        if (taskNumber < 0 || taskNumber >= taskList.size()) {
            throw new EsquieException(Messages.ERR_TASK_NOT_EXIST);
        }

        Task currentTask = taskList.get(taskNumber);

        if (isMark) {
            currentTask.markComplete();
            ui.showMark(currentTask);
        } else {
            currentTask.markIncomplete();
            ui.showUnmark(currentTask);
        }

        storage.overwriteAll(taskList);
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
