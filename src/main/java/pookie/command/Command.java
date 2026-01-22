package pookie.command;

import pookie.exception.PookieException;

/**
 * Abstract class representing a command.
 */
public abstract class Command {

    /**
     * Executes the command.
     * 
     * @return the message to print after execution
     */
    public abstract String execute() throws PookieException;

    /**
     * Indicates whether this command should close the application.
     * 
     * @return true if the command is an exit command, false otherwise
     */
    public boolean isExit() {
        return false;
    }
}