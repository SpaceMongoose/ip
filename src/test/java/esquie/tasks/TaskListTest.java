package esquie.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import esquie.exceptions.EsquieException;

public class TaskListTest {

    @Test
    public void addTask_sizeIncrease() throws EsquieException {
        TaskList taskList = new TaskList();
        Task task = new Task("Read book");
        taskList.add(task);
        assertEquals(1, taskList.size());
    }

    @Test
    public void deleteTask_sizeDecrease() throws EsquieException {
        TaskList taskList = new TaskList();
        Task task = new Task("Read book");
        taskList.add(task);
        Task deletedTask = taskList.delete(0);
        assertEquals(0, taskList.size());
        assertEquals(task, deletedTask);
    }

    @Test
    public void getReturns_correctTask() throws EsquieException {
        TaskList taskList = new TaskList();
        Task taskOne = new Task("Read book");
        Task taskTwo = new Task("Watch TV");
        taskList.add(taskOne);
        taskList.add(taskTwo);

        Task getOne = taskList.get(0);
        Task getTwo = taskList.get(1);
        assertEquals(taskOne, getOne);
        assertEquals(taskTwo, getTwo);
    }
}
