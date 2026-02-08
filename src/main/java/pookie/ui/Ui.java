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
        System.out.println(LINE);
        System.out.println(getWelcomeMessage());
        System.out.println(LINE);
        System.out.println();
    }

    public String getWelcomeMessage() {
        StringBuilder sb = new StringBuilder();

        sb.append("Hello! I'm Pookie :3").append("\n");
        sb.append("What can I do for you? OwO").append("\n");

        return sb.toString();
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
