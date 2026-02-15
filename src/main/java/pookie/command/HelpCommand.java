package pookie.command;

import pookie.exception.PookieException;
import pookie.storage.Storage;
import pookie.task.TaskList;

/**
 * Command to display help information for all available commands.
 */
public class HelpCommand extends Command {

    @Override
    public String execute(TaskList taskList, Storage storage) throws PookieException {
        return getHelpText();
    }

    private String getHelpText() {
        return """
                Pookie's Task Management Guide ~(˘⌣˘)~
                ═══════════════════════════════════════

                Task Management:
                  todo <description>
                    Add a todo task. Example: todo read book

                  deadline <description> /by <date>
                    Add a deadline task. Example: deadline report /by 2026-02-15

                  event <description> /from <date> /to <date>
                    Add an event task. Example: event meeting /from 2026-02-10 /to 2026-02-11

                Viewing Tasks:
                  list
                    Show all tasks in your task list. Time to see what you've got!

                  find <keyword>
                    Search for tasks containing the keyword. Example: find book

                Marking Tasks (supports single index, multiple indices, or ranges):
                  mark <index|range ...>
                    Mark task(s) as done. ^w^ ✓
                    Examples: mark 1
                             mark 1 3 5
                             mark 2-4
                             mark 1 3-5 7

                  unmark <index|range ...>
                    Unmark task(s) as not done. x3
                    Examples: unmark 1
                             unmark 2-4

                Deleting Tasks (supports single index, multiple indices, or ranges):
                  delete <index|range ...>
                    Delete task(s) from your list. Into Pookie's hole! >:3
                    Examples: delete 1
                             delete 1 3 5
                             delete 2-4
                             delete 1 3-5 7

                Exit:
                  bye / kthxbye
                    Exit the application. ;w;

                ═══════════════════════════════════════
                Pro Tip: Use batch operations for 'mark', 'unmark', and 'delete'
                with multiple indices or ranges! Examples:
                  mark 1-5        (marks tasks 1 through 5)
                  delete 1 3-5 7  (deletes tasks 1, 3, 4, 5, and 7)

                UwU! Let Pookie help you!"""
                .stripIndent();
    }
}
