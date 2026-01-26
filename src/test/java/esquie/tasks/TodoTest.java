package esquie.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TodoTest {

    @Test
    public void todo_add_success() {
        Todo todo = new Todo("Read book");
        assertEquals("[T][ ] Read book", todo.toString());
    }

    @Test
    public void todo_add_saveString() {
        Todo todo = new Todo("Read book");
        assertEquals("T | 0 | Read book", todo.saveString());
    }

    @Test
    public void todo_manual_mark() {
        Todo todoMarked = new Todo("Read book" , true);
        Todo todoUnmarked = new Todo("Read book" , false);

        //  the print and save string for a marked task
        assertEquals("[T][X] Read book", todoMarked.toString());
        assertEquals("T | 1 | Read book", todoMarked.saveString());

        //  the print and save string for a unmarked task
        assertEquals("[T][ ] Read book", todoUnmarked.toString());
        assertEquals("T | 0 | Read book", todoUnmarked.saveString());
    }
}
