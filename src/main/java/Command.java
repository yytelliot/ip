public abstract class Command {


    public abstract String execute();

    public boolean isExit() {
        return false;
    }
}