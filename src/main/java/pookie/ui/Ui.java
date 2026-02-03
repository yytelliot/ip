package pookie.ui;

/**
 * Handles the user interface interactions.
 */
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
        showLine();
        System.out.println(response);
        showLine();
        showEmptyLine();
    }   

    public void showError(String errorMessage) {
        showLine();
        System.out.println(errorMessage);
        showLine();
        showEmptyLine();
    }

    
}
