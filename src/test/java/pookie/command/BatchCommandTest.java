package pookie.command;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pookie.exception.PookieException;
import pookie.storage.Storage;
import pookie.task.TaskList;
import pookie.task.TodoTask;

class BatchCommandTest {

    private TaskList taskList;
    private Storage storage;

    @BeforeEach
    void setUp() {
        taskList = new TaskList();
        storage = new Storage(Paths.get("test_data.txt"));

        // Add 5 test tasks
        taskList.addTask(new TodoTask("Task 1"));
        taskList.addTask(new TodoTask("Task 2"));
        taskList.addTask(new TodoTask("Task 3"));
        taskList.addTask(new TodoTask("Task 4"));
        taskList.addTask(new TodoTask("Task 5"));
    }

    // ========== MarkCommand Tests ==========

    @Test
    void markCommand_singleIndex_marksOneTask() throws PookieException {
        MarkCommand cmd = new MarkCommand(new String[]{"mark", "1"});
        cmd.execute(taskList, storage);

        assertTrue(taskList.getTask(0).isDone());
        assertFalse(taskList.getTask(1).isDone());
    }

    @Test
    void markCommand_multipleIndices_marksMultipleTasks() throws PookieException {
        MarkCommand cmd = new MarkCommand(new String[]{"mark", "1", "3", "5"});
        cmd.execute(taskList, storage);

        assertTrue(taskList.getTask(0).isDone());
        assertFalse(taskList.getTask(1).isDone());
        assertTrue(taskList.getTask(2).isDone());
        assertFalse(taskList.getTask(3).isDone());
        assertTrue(taskList.getTask(4).isDone());
    }

    @Test
    void markCommand_range_marksTasksInRange() throws PookieException {
        MarkCommand cmd = new MarkCommand(new String[]{"mark", "2-4"});
        cmd.execute(taskList, storage);

        assertFalse(taskList.getTask(0).isDone());
        assertTrue(taskList.getTask(1).isDone());
        assertTrue(taskList.getTask(2).isDone());
        assertTrue(taskList.getTask(3).isDone());
        assertFalse(taskList.getTask(4).isDone());
    }

    @Test
    void markCommand_mixedRangeAndSingle_marksCorrectTasks() throws PookieException {
        MarkCommand cmd = new MarkCommand(new String[]{"mark", "1", "3-4"});
        cmd.execute(taskList, storage);

        assertTrue(taskList.getTask(0).isDone());
        assertFalse(taskList.getTask(1).isDone());
        assertTrue(taskList.getTask(2).isDone());
        assertTrue(taskList.getTask(3).isDone());
        assertFalse(taskList.getTask(4).isDone());
    }

    @Test
    void markCommand_invalidIndex_throwsException() {
        MarkCommand cmd = new MarkCommand(new String[]{"mark", "10"});
        assertThrows(PookieException.class, () -> cmd.execute(taskList, storage));
    }

    @Test
    void markCommand_invalidRange_throwsException() {
        MarkCommand cmd = new MarkCommand(new String[]{"mark", "5-10"});
        assertThrows(PookieException.class, () -> cmd.execute(taskList, storage));
    }

    @Test
    void markCommand_invalidRangeFormat_throwsException() {
        MarkCommand cmd = new MarkCommand(new String[]{"mark", "1-"});
        assertThrows(PookieException.class, () -> cmd.execute(taskList, storage));
    }

    @Test
    void markCommand_reverseRange_throwsException() {
        MarkCommand cmd = new MarkCommand(new String[]{"mark", "4-2"});
        assertThrows(PookieException.class, () -> cmd.execute(taskList, storage));
    }

    // ========== UnmarkCommand Tests ==========

    @Test
    void unmarkCommand_singleIndex_unmarksOneTask() throws PookieException {
        // Mark all tasks first
        for (int i = 0; i < 5; i++) {
            taskList.getTask(i).markAsDone();
        }

        UnmarkCommand cmd = new UnmarkCommand(new String[]{"unmark", "2"});
        cmd.execute(taskList, storage);

        assertTrue(taskList.getTask(0).isDone());
        assertFalse(taskList.getTask(1).isDone());
        assertTrue(taskList.getTask(2).isDone());
    }

    @Test
    void unmarkCommand_range_unmarksTasksInRange() throws PookieException {
        // Mark all tasks first
        for (int i = 0; i < 5; i++) {
            taskList.getTask(i).markAsDone();
        }

        UnmarkCommand cmd = new UnmarkCommand(new String[]{"unmark", "2-4"});
        cmd.execute(taskList, storage);

        assertTrue(taskList.getTask(0).isDone());
        assertFalse(taskList.getTask(1).isDone());
        assertFalse(taskList.getTask(2).isDone());
        assertFalse(taskList.getTask(3).isDone());
        assertTrue(taskList.getTask(4).isDone());
    }

    // ========== DeleteCommand Tests ==========

    @Test
    void deleteCommand_singleIndex_deletesOneTask() throws PookieException {
        DeleteCommand cmd = new DeleteCommand(new String[]{"delete", "2"});
        cmd.execute(taskList, storage);

        assertEquals(4, taskList.getTaskCount());
        assertEquals("Task 1", taskList.getTask(0).getDescription());
        assertEquals("Task 3", taskList.getTask(1).getDescription());
    }

    @Test
    void deleteCommand_multipleIndices_deletesMultipleTasks() throws PookieException {
        DeleteCommand cmd = new DeleteCommand(new String[]{"delete", "1", "3", "5"});
        cmd.execute(taskList, storage);

        assertEquals(2, taskList.getTaskCount());
        assertEquals("Task 2", taskList.getTask(0).getDescription());
        assertEquals("Task 4", taskList.getTask(1).getDescription());
    }

    @Test
    void deleteCommand_range_deletesTasksInRange() throws PookieException {
        DeleteCommand cmd = new DeleteCommand(new String[]{"delete", "2-4"});
        cmd.execute(taskList, storage);

        assertEquals(2, taskList.getTaskCount());
        assertEquals("Task 1", taskList.getTask(0).getDescription());
        assertEquals("Task 5", taskList.getTask(1).getDescription());
    }

    @Test
    void deleteCommand_mixedRangeAndSingle_deletesCorrectTasks() throws PookieException {
        DeleteCommand cmd = new DeleteCommand(new String[]{"delete", "1", "3-4"});
        cmd.execute(taskList, storage);

        assertEquals(2, taskList.getTaskCount());
        assertEquals("Task 2", taskList.getTask(0).getDescription());
        assertEquals("Task 5", taskList.getTask(1).getDescription());
    }

    @Test
    void deleteCommand_invalidIndex_throwsException() {
        DeleteCommand cmd = new DeleteCommand(new String[]{"delete", "10"});
        assertThrows(PookieException.class, () -> cmd.execute(taskList, storage));
    }

    // ========== Edge Cases ==========

    @Test
    void markCommand_noArguments_throwsException() {
        MarkCommand cmd = new MarkCommand(new String[]{"mark"});
        assertThrows(PookieException.class, () -> cmd.execute(taskList, storage));
    }

    @Test
    void deleteCommand_noArguments_throwsException() {
        DeleteCommand cmd = new DeleteCommand(new String[]{"delete"});
        assertThrows(PookieException.class, () -> cmd.execute(taskList, storage));
    }

    @Test
    void markCommand_nonNumericIndex_throwsException() {
        MarkCommand cmd = new MarkCommand(new String[]{"mark", "abc"});
        assertThrows(PookieException.class, () -> cmd.execute(taskList, storage));
    }

    @Test
    void markCommand_zeroIndex_throwsException() {
        MarkCommand cmd = new MarkCommand(new String[]{"mark", "0"});
        assertThrows(PookieException.class, () -> cmd.execute(taskList, storage));
    }

    @Test
    void markCommand_negativeIndex_throwsException() {
        MarkCommand cmd = new MarkCommand(new String[]{"mark", "-1"});
        assertThrows(PookieException.class, () -> cmd.execute(taskList, storage));
    }

    @Test
    void deleteCommand_allTasksInRange_leavesEmptyList() throws PookieException {
        DeleteCommand cmd = new DeleteCommand(new String[]{"delete", "1-5"});
        cmd.execute(taskList, storage);

        assertEquals(0, taskList.getTaskCount());
    }
}
