public class AddCommand extends Command {

    private final String[] args;

    public AddCommand(String[] args) {
        this.args = args;
    }

    @Override
    public String execute() {

        if (args.length < 1) {
            return "Please provide a task to add! >w<";
        }

        String task = String.join(" ", args);

        // add task to task list
        try {
            TaskList.addTask(task);
        } catch (IllegalStateException e) {
            return e.getMessage();
        }
        
        // print message
        return "Added task: " + task + " ^w^";
    }
}
