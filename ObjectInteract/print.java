package bg.tu_varna.sit.f24621744.task.objectInteract;

import bg.tu_varna.sit.f24621744.task.Command;
import bg.tu_varna.sit.f24621744.task.Exception.JsonException;
import bg.tu_varna.sit.f24621744.task.Exception.JsonFileException;
import bg.tu_varna.sit.f24621744.task.Exception.JsonTypeException;
import bg.tu_varna.sit.f24621744.task.Session;

/**
 * The {@code print} command: prints the current JSON structure in a formatted format.
 * <p>
 * Prints the entire JSON tree of the currently open file to the console
 * in a readable (pretty-printed) format with indentations.
 * </p>
 *
 * <p><b>Syntax:</b> {@code print}</p>
 *
 * <p><b>Example output:</b></p>
 * <pre>{@code
 * {
 * "name": "Alice",
 * "age": 30,
 * "active": true
 * }
 * }</pre>
 */
public class Print implements Command {

    /**
     * Outputs the JSON structure of the currently open file to the console.
     * <p>
     * If the file is not open, displays an error message.
     * Formatting is performed using the {@code toPrettyString(0)} method
     * of the root node of the JSON tree.
     * </p>
     *
     * @param arguments command arguments (not used)
     * @param session current application session
     * @throws JsonFileException if the path does not exist
     */
    @Override
    public void execute(String arguments, Session session) {
        if (!session.isFileOpen()) {
            throw new JsonFileException("no file open.");
        }
        try {
            System.out.println(session.getRootNode().toPrettyString(0));
        } catch (JsonException e) {
            throw new JsonTypeException("formating error: " + e.getMessage());
        }
    }

    /**
     * Returns a short description of the command for reference.
     *
     * @return a string with the command's syntax and purpose
     */
    @Override
    public String getDescription() {
        return "print - displays the current JSON structure";
    }
}