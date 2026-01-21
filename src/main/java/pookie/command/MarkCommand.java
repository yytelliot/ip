package pookie.command;
public class MarkCommand extends Command {
    private final String[] args;

    public MarkCommand(String[] args) {
        this.args = args;
    }

    @Override
    public String execute() {

        if (args.length < 2) {
            return "Please provide the index of the task to mark! >w<";
        }

        Task task;
        try {
            int index = Integer.parseInt(args[1]) - 1;
            task = TaskList.getTask(index);
        } catch (NumberFormatException e) {
            return "Owo? The index provided is not a number! >w<";
        } catch (IndexOutOfBoundsException e) {
            return e.getMessage();
        }
        task.markAsDone();
        return "Nice! I've marked this task as done x3\n  " + task.toString();
    }
}