public class ListCommand extends Command {

    @Override
    public String execute() {
        StringBuilder sb = new StringBuilder("Here are the tasks in your list :3\n");
        for (int i = 0; i < TaskList.getTaskCount(); i++) {
            sb.append((i + 1)).append(". ")
            .append(TaskList.getTask(i))
            .append("\n");
        }
        return sb.toString().trim();
    }

}