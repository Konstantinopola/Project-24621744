package bg.tu_varna.sit.f24621744.task.objectInteract;

import bg.tu_varna.sit.f24621744.task.Command;
import bg.tu_varna.sit.f24621744.task.Session;
import bg.tu_varna.sit.f24621744.task.jsonWork.JsonObject;
import bg.tu_varna.sit.f24621744.task.jsonWork.JsonType;
import bg.tu_varna.sit.f24621744.task.parser.*;

/**
 * The {@code create} command: Creates a new element in a JSON structure at the given path.
 * <p>
 * The path is specified as a sequence of keys separated by spaces.
 * The last argument is a JSON value.
 * Intermediate path nodes are automatically created as {@link JsonObject},
 * if they do not exist.
 * </p>
 *
 * <p><b>Syntax:</b> {@code create <key1> [<key2> ...] <value>}</p>
 *
 * <p><b>Examples:</b></p>
 * <pre>
 * create name "Alice"
 * create address city "Sofia"
 * create scores 95
 * create active true
 * </pre>
 */
public class Create implements Command {

    /**
     * Performs a node creation operation at the given path.
     * Checks the file session activity status and splits the passed tokens.
     *
     * @param arguments is a parameter string (path keys and the target value at the end)
     * @param session is the current working session of the application
     */
    @Override
    public void execute(String arguments, Session session) {
        if (!session.isFileOpen()) {
            System.out.println("Error: Open a file first.");
            return;
        }

        String[] parts = arguments.split("\\s+");
        if (parts.length < 2) {
            System.out.println("Usage: create <key> <value>");
            return;
        }

        String rawValue = parts[parts.length - 1];
        String[] path = new String[parts.length - 1];
        System.arraycopy(parts, 0, path, 0, parts.length - 1);

        try {
            JsonType newValue = JsonParser.parseString(rawValue);

            boolean success = session.getRootNode().createPath(path, 0, newValue);

            if (success) {
                System.out.println("Element created successfully.");
            } else {
                System.out.println("Error: Could not create element at the specified path.");
            }
        }

        catch (Exception e) {
            System.out.println("Error: problem in JSON is: " + e.getMessage());
        }
    }

    /**
     * Returns a short description of the command for reference.
     *
     * @return a string with the command's syntax and purpose
     */
    @Override
    public String getDescription() {
        return "create <key> <value> - adds a new property to the JSON object";
    }
}