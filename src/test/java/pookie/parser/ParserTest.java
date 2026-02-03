package pookie.ui;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import pookie.command.AddCommand;
import pookie.command.Command;
import pookie.command.DeleteCommand;
import pookie.command.ExitCommand;
import pookie.command.ListCommand;
import pookie.command.MarkCommand;
import pookie.command.UnmarkCommand;

public class ParserTest {

    private final Parser parser = new Parser();

    // ---------- Single-word commands ----------

    @Test
    void parse_bye_returnsExitCommand() {
        Command cmd = parser.parse("bye");
        assertInstanceOf(ExitCommand.class, cmd);
    }

    @Test
    void parse_kthxbye_returnsExitCommand() {
        Command cmd = parser.parse("kthxbye");
        assertInstanceOf(ExitCommand.class, cmd);
    }

    @Test
    void parse_list_returnsListCommand() {
        Command cmd = parser.parse("list");
        assertInstanceOf(ListCommand.class, cmd);
    }

    // ---------- Commands with arguments ----------

    @Test
    void parse_todoWithDescription_returnsAddCommand() {
        Command cmd = parser.parse("todo read book");
        assertInstanceOf(AddCommand.class, cmd);
    }

    @Test
    void parse_deadlineWithDescription_returnsAddCommand() {
        Command cmd = parser.parse("deadline report /by 2026-02-10");
        assertInstanceOf(AddCommand.class, cmd);
    }

    @Test
    void parse_eventWithDescription_returnsAddCommand() {
        Command cmd = parser.parse("event meeting /from 2026-02-10 /to 2026-02-11");
        assertInstanceOf(AddCommand.class, cmd);
    }

    @Test
    void parse_markWithIndex_returnsMarkCommand() {
        Command cmd = parser.parse("mark 1");
        assertInstanceOf(MarkCommand.class, cmd);
    }

    @Test
    void parse_unmarkWithIndex_returnsUnmarkCommand() {
        Command cmd = parser.parse("unmark 1");
        assertInstanceOf(UnmarkCommand.class, cmd);
    }

    @Test
    void parse_deleteWithIndex_returnsDeleteCommand() {
        Command cmd = parser.parse("delete 2");
        assertInstanceOf(DeleteCommand.class, cmd);
    }

    // ---------- Current behavior: parser does not validate ----------

    @Test
    void parse_unknownCommand_returnsAddCommandByDefault() {
        Command cmd = parser.parse("fly 123");
        assertInstanceOf(AddCommand.class, cmd);
    }

    @Test
    void parse_deleteMissingIndex_stillReturnsDeleteCommand() {
        Command cmd = parser.parse("delete");
        assertInstanceOf(DeleteCommand.class, cmd);
    }

    @Test
    void parse_markMissingIndex_stillReturnsMarkCommand() {
        Command cmd = parser.parse("mark");
        assertInstanceOf(MarkCommand.class, cmd);
    }

    @Test
    void parse_todoMissingDescription_stillReturnsAddCommand() {
        Command cmd = parser.parse("todo");
        assertInstanceOf(AddCommand.class, cmd);
    }

    @Test
    void parse_deleteNonNumericIndex_stillReturnsDeleteCommand() {
        Command cmd = parser.parse("delete two");
        assertInstanceOf(DeleteCommand.class, cmd);
    }

    // ---------- Whitespace trimming (your parser trims input) ----------

    @Test
    void parse_trimsLeadingTrailingSpaces() {
        Command cmd = parser.parse("   list   ");
        assertInstanceOf(ListCommand.class, cmd);
    }

    @Test
    void parse_multipleSpacesBetweenTokens_currentlyMayFail() {
        Command cmd = parser.parse("delete     3");
        assertTrue(cmd instanceof DeleteCommand);
    }
}
