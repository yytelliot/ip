package pookie.ui;

public class Ui {

    private static final String LINE = "==============================";

    /**
     * Prints the welcome message to the user.
     */
    public void showWelcomeMessage() {
        showLine();
        System.out.println("Hello! I'm Pookie :3");
        System.out.println("What can I do for you? OwO");
        showLine();
        showEmptyLine();
    }

    /**
     * Prints a line separator.
     */
    public void showLine() {
        System.out.println(LINE);
    }

    public void showEmptyLine() {
        System.out.println();
    }

    public void showResponse(String response) {
        System.out.println(response);
    }   

    public void showError(String errorMessage) {
        System.out.println("OwO! " + errorMessage);
    }

    
}
