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

    /**
     * Displays the response message to the user.
     *
     * @param response The response message to display.
     */
    public void showResponse(String response) {
        showLine();
        System.out.println(response);
        showLine();
        showEmptyLine();
    }

    /**
     * Displays an error message to the user.
     *
     * @param errorMessage The error message to display.
     */
    public void showError(String errorMessage) {
        showLine();
        System.out.println(errorMessage);
        showLine();
        showEmptyLine();
    }

}
