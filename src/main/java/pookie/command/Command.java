package pookie.command;

import pookie.exception.PookieException;
import pookie.storage.Storage;

/**
 * Abstract class representing a command.
 */
public abstract class Command {

    protected static Storage storage;

    /**
     * Sets the storage instance for all commands.
     * 
     * @param storageInstance the storage instance to set
     */
    public static void setStorage(Storage storageInstance) {
        storage = storageInstance;
    }

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