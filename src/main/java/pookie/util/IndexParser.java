package pookie.util;

import java.util.ArrayList;
import java.util.List;

import pookie.exception.PookieException;
import pookie.task.TaskList;

/**
 * Handles parsing and validation of task indices from user input.
 */
public class IndexParser {

    /**
     * Parses task indices from user input, supporting both single indices and ranges.
     * Ranges are specified with hyphen notation (e.g., "1-3").
     *
     * @param taskList the task list for validation
     * @param indexStrs array of index strings to parse
     * @return list of 0-based indices
     * @throws PookieException if any index is invalid or out of bounds
     */
    public List<Integer> parseTaskIndices(TaskList taskList, String[] indexStrs) throws PookieException {
        List<Integer> indices = new ArrayList<>();
        for (String indexStr : indexStrs) {
            if (indexStr.contains("-")) {
                indices.addAll(parseRange(taskList, indexStr));
                continue;
            }
            indices.add(parseSingleIndex(taskList, indexStr));
        }
        return indices;
    }

    /**
     * Parses a range of task indices (e.g., "1-3").
     *
     * @param taskList the task list for validation
     * @param rangeStr the range string in format "start-end"
     * @return list of 0-based indices within the range (inclusive)
     * @throws PookieException if range format is invalid or indices are out of bounds
     */
    private List<Integer> parseRange(TaskList taskList, String rangeStr) throws PookieException {
        String[] parts = rangeStr.split("-", -1);
        List<Integer> indices = new ArrayList<>();
        if (parts.length != 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
            throw new PookieException("Owo? The index range " + rangeStr + " is invalid! >w<!");
        }

        int startIndex = parseSingleIndex(taskList, parts[0]);
        int endIndex = parseSingleIndex(taskList, parts[1]);
        if (startIndex > endIndex) {
            throw new PookieException("Owo? The index range " + rangeStr + " is invalid! >w<!");
        }
        for (int i = startIndex; i <= endIndex; i++) {
            indices.add(i);
        }
        return indices;
    }

    /**
     * Parses a single task index from user input (1-indexed) to internal format (0-indexed).
     *
     * @param taskList the task list for bounds validation
     * @param indexStr the string representation of the index
     * @return the 0-based index
     * @throws PookieException if the index is not a valid number or is out of bounds
     */
    private int parseSingleIndex(TaskList taskList, String indexStr) throws PookieException {
        try {
            int index = Integer.parseInt(indexStr) - 1;
            if (index < 0 || index >= taskList.getTaskCount()) {
                throw new IndexOutOfBoundsException();
            }
            return index;
        } catch (NumberFormatException e) {
            throw new PookieException("Owo? The index " + indexStr + " is not a number! >w<!");
        } catch (IndexOutOfBoundsException e) {
            throw new PookieException("Owo? The task index " + indexStr + " doesn't exist! >w<!");
        }
    }
}
