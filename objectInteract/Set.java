package bg.tu_varna.sit.f24621744.task.objectInteract;

import bg.tu_varna.sit.f24621744.task.Command;
import bg.tu_varna.sit.f24621744.task.Exception.JsonException;
import bg.tu_varna.sit.f24621744.task.Exception.JsonFileException;
import bg.tu_varna.sit.f24621744.task.Exception.JsonNavigationException;
import bg.tu_varna.sit.f24621744.task.Exception.JsonTypeException;
import bg.tu_varna.sit.f24621744.task.Session;
import bg.tu_varna.sit.f24621744.task.jsonWork.JsonType;
import bg.tu_varna.sit.f24621744.task.parser.*;

/**
 * The {@code set} command: Replaces the value of an existing element at the specified path.
 * <p>
 * Unlike {@link Create}, the {@code set} command only works with existing
 * elements—it does not create new nodes in the tree.
 * </p>
 *
 * <p><b>Syntax:</b> {@code set <key1> [<key2> ...] <new value>}</p>
 *
 * <p><b>Examples:</b></p>
 * <pre>
 * set name "Bob"
 * set age 25
 * set address city "Plovdiv"
 * set active false
 * </pre>
 */
public class Set implements Command {

    /**
     * Replaces the value of an element at the specified path.
     * <p>
     * Operation algorithm:
     * <ol>
     * <li>Checks that the file is open and at least two arguments are passed.</li>
     * <li>The last argument is parsed as a new JSON value.</li>
     * <li>The remaining arguments form the path to the element being replaced.</li>
     *
     * </ol>
     * </p>
     *
     * @param arguments is a string of arguments: the path and the new value, separated by spaces.
     * @param session is the current application session.
     * @throws JsonException if the value is not in the correct format or the element at the given path does not exist
     */
    @Override
    public void execute(String arguments, Session session) {
        if (!session.isFileOpen()) {
            throw new JsonFileException("open a file first!");
        }

        String[] parts = arguments.trim().split("\\s+");
        if (parts.length < 2) {
            throw new JsonNavigationException("Usage: set <path> <string>");
        }

        // last argument is a new JSON string.
        String jsonString = parts[parts.length - 1];
        // everything else - way
        String[] path = new String[parts.length - 1];
        System.arraycopy(parts, 0, path, 0, parts.length - 1);

        try {
            // parse a new string into an object
            JsonType newValue = JsonParser.parseString(jsonString);

            boolean success = session.getRootNode().setByPath(path, 0, newValue);

            if (success) {
                System.out.println("Value updated successfully.");
            }
        } catch (JsonException e) {
            throw new JsonTypeException("changing data error: " + e.getMessage());
        }
    }

    /**
     * Returns a short description of the command for reference.
     *
     * @return a string with the command's syntax and purpose
     */
    @Override
    public String getDescription() {
        return "set <path> <string> - updates the value at the specified path.";
    }
}