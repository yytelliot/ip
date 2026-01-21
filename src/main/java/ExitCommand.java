public class ExitCommand extends Command {

    @Override
    public String execute() {
        return "See you! TwT";
    }

    @Override
    public boolean isExit() {
        return true;
    }
}