package pookie.ui;

import pookie.command.AddCommand;
import pookie.command.Command;
import pookie.command.DeleteCommand;
import pookie.command.ExitCommand;
import pookie.command.FindCommand;
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
     * @throws PookieException if the command is unknown
     */
    public Command parse(String input) throws PookieException {
        input = input.trim();

        // single-word commands
        if (input.equals("bye") || input.equals("kthxbye")) {
            return new ExitCommand();
        }
        if (input.equals("list")) {
            return new ListCommand();
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
                throw new PookieException("Owo? I don't know that command! >w< "
                        + "Try: todo, deadline, event, mark, unmark, delete, list, find, or bye.");
            }
        }

    }
}
