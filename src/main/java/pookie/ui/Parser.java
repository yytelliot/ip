package pookie.ui;

import pookie.command.AddCommand;
import pookie.command.Command;
import pookie.command.DeleteCommand;
import pookie.command.ExitCommand;
import pookie.command.FindCommand;
import pookie.command.HelpCommand;
import pookie.command.ListCommand;
import pookie.command.MarkCommand;
import pookie.command.UnmarkCommand;
import pookie.exception.PookieException;

/**
 * Parses user input into commands.
 */
public class Parser {

    /**
     * Parses user input into a Command.
     *
     * @param input the user input string
     * @return the corresponding Command object
     */
    public Command parse(String input) throws PookieException {
        assert input != null : "Input must not be null";
        input = input.trim();

        // single-word commands
        if (input.equals("bye") || input.equals("kthxbye")) {
            return new ExitCommand();
        }
        if (input.equals("list")) {
            return new ListCommand();
        }
        if (input.equals("help")) {
            return new HelpCommand();
        }

        // commands with single arguments
        if (input.startsWith("find ")) {
            return new FindCommand(input.substring(5).trim());
        }

        // commands with multiple arguments
        String[] parts = input.split("\\s+");
        String command = parts[0].trim();
        switch (command) {
            case "todo", "deadline", "event" -> {
                return new AddCommand(parts);
            }
            case "mark" -> {
                return new MarkCommand(parts);
            }
            case "unmark" -> {
                return new UnmarkCommand(parts);
            }
            case "delete" -> {
                return new DeleteCommand(parts);
            }
            default -> {
                throw new PookieException("Pookie doesn't know that command.");
            }
        }

    }
}
