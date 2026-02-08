package pookie;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

import pookie.command.Command;
import pookie.exception.PookieException;
import pookie.storage.Storage;
import pookie.task.TaskList;
import pookie.ui.Parser;
import pookie.ui.Ui;

/**
 * Main class for the Pookie X3 application.
 */
public class Pookie {

    private final Storage storage;
    private final Parser parser;
    private final Ui ui;
    private final TaskList taskList;
    private boolean isExit = false;

    /**
     * Constructs a Pookie application with the specified storage file path.
     *
     * @param filePath The path to the storage file.
     */
    public Pookie(String filePath) {
        this.storage = new Storage(Paths.get(filePath));
        this.parser = new Parser();
        this.ui = new Ui();
        this.taskList = new TaskList();

        try {
            storage.loadIntoTaskList(taskList);
        } catch (IOException e) {
            ui.showError("UwU! There was an error loading the task list: " + e.getMessage());
        }

    }

    /**
     * Gets Pookie's response to the user input for JavaFX.
     *
     * @param userInput The input from the user.
     * @return Pookie's response.
     */
    public String getResponse(String userInput) {


        // parse and execute command
        Command command = parser.parse(userInput);

        try {

            // try to execute the command
            String response = command.execute(taskList, storage);
            if (command.isExit()) {
                isExit = true;
            }

            // print response
            return response;

        } catch (PookieException e) {
            // print error message
            return e.getMessage();
        }

    }

    /**
     * Returns whether the application should exit.
     *
     * @return true if the application should exit, false otherwise.
     */
    public boolean isExit() {
        return isExit;
    }

    /**
     * Runs the Pookie application.
     */
    public void run() {
        // Implementation of run method if needed
        Scanner sc = new Scanner(System.in);


        ui.showWelcomeMessage();

        try {

            // Main loop
            while (true) {

                // read user input
                String userInput = sc.nextLine();

                // parse and execute command
                Command command = parser.parse(userInput);

                try {

                    // try to execute the command
                    String response = command.execute(taskList, storage);

                    // print response
                    ui.showResponse(response);

                    // exit if command is exit command
                    if (command.isExit()) {
                        break;
                    }

                } catch (PookieException e) {
                    // print error message
                    ui.showError(e.getMessage());
                }
            }

        } finally {
            sc.close();
        }
    }

    public static void main(String[] args) {

        String filePath = "pookie_data.txt";
        if (args.length > 0) {
            filePath = args[0];
        }

        new Pookie(filePath).run();

    }

}
