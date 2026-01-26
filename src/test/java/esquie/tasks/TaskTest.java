package esquie.tasks;

import esquie.exceptions.EsquieException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TaskTest {

    @Test
    public void task_get_description_success() {
        Task task = new Task("Read book");
        assertEquals("Read book", task.getDescription());
    }

    @Test
    public void task_get_status_icon_success() {
        Task taskDone = new Task("Read book", true);
        Task taskNotDone = new Task("Read book", false);
        assertEquals("X", taskDone.getStatusIcon());
        assertEquals(" ", taskNotDone.getStatusIcon());
    }

    @Test
    public void task_mark_success() {
        Task task = new Task("Read book");
        task.markIncomplete();
        assertEquals(" ", task.getStatusIcon());
        task.markComplete();
        assertEquals("X", task.getStatusIcon());
    }

    @Test
    public void task_toString_success() {
        Task task = new Task("Read book");
        assertEquals("[ ] Read book", task.toString());

        task.markComplete();
        assertEquals("[X] Read book", task.toString());
    }

    @Test
    public void task_saveString_success() {
        Task task = new Task("Read book");
        assertEquals("0 | Read book", task.saveString());

        task.markComplete();
        assertEquals("1 | Read book", task.saveString());
    }
}
