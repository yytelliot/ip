package pookie.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class TaskListTest {

    @Test
    void getTaskCount_onEmptyList_returns0() {
        TaskList taskList = new TaskList();
        assertEquals(0, taskList.getTaskCount());
    }

    @Test
    void getTaskCount_afterAddingTasks_returnsCorrectCount() {
        TaskList taskList = new TaskList();
        taskList.addTask(new TodoTask("Test task"));
        taskList.addTask(new TodoTask("Another task"));
        assertEquals(2, taskList.getTaskCount());
    }

    @Test
    void getTaskCount_afterDeletingTask_decrementsCount() {
        TaskList taskList = new TaskList();
        taskList.addTask(new TodoTask("Test task"));
        taskList.addTask(new TodoTask("Another task"));

        taskList.deleteTask(0);

        assertEquals(1, taskList.getTaskCount());
    }

    @Test
    void addTask_increasesTaskCount() {
        TaskList taskList = new TaskList();
        Task task = new TodoTask("Test task");
        taskList.addTask(task);
        assertEquals(1, taskList.getTaskCount());
    }

    @Test
    void deleteTask_removesCorrectTask() {
        TaskList taskList = new TaskList();
        Task task1 = new TodoTask("Test task");
        Task task2 = new TodoTask("Another task");
        taskList.addTask(task1);
        taskList.addTask(task2);
        taskList.deleteTask(0);
        assertEquals(1, taskList.getTaskCount());
    }

    @Test
    void getTask_returnsCorrectTask() {
        TaskList taskList = new TaskList();
        Task task = new TodoTask("Test task");
        taskList.addTask(task);
        assertEquals(task, taskList.getTask(0));
    }

    @Test
    void clearTasks_emptiesTaskList() {
        TaskList taskList = new TaskList();
        Task task = new TodoTask("Test task");
        taskList.addTask(task);
        taskList.clearTasks();
        assertEquals(0, taskList.getTaskCount());
    }
}
