package pookie.command;
public class UnmarkCommand extends Command {
    private final String[] args;

    public UnmarkCommand(String[] args) {
        this.args = args;
    }

    @Override
    public String execute() {

        if (args.length < 2) {
            return "Please provide the index of the task to unmark! >w<";
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
        task.markAsUndone();
        return "OK, I've marked this task as not done yet ;w;\n  " + task.toString();
    }
}