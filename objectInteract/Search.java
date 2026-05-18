package bg.tu_varna.sit.f24621744.task.objectInteract;

import bg.tu_varna.sit.f24621744.task.Command;
import bg.tu_varna.sit.f24621744.task.Session;
import bg.tu_varna.sit.f24621744.task.jsonWork.JsonType;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code search} command: performs a recursive search for a key in the entire JSON tree.
 * <p>
 * Searches for all elements matching the given key, at any nesting level.
 * Displays all found values in format.
 * If no matches are found, displays a corresponding message.
 * </p>
 *
 * <p><b>Syntax:</b> {@code search <key>}</p>
 *
 * <p><b>Examples:</b></p>
 * <pre>
 * search name
 * search city
 * </pre>
 */
public class Search implements Command {

    /**
     * Performs a search by key in a JSON tree and displays the results.
     * <p>
     * Operation:
     * <ol>
     * <li>Checks that the file is open and the key is not empty.</li>
     * <li>Creates an empty list of results.</li>
     * <li>Calls {@link JsonType result = session.getRootNode().getByPath(path, 0);}
     * to recursively traverse the tree.</li>
     * <li>Displays all found values or a message indicating no results.</li>
     * </ol>
     * </p>
     *
     * <p><b>Examples:</b></p>
     *  <pre>
     *  search name
     *  search users
     *  </pre>
     * @param arguments key to search for
     * @param session current application session
     */
    @Override
    public void execute(String arguments, Session session) {
        if (!session.isFileOpen()) {
            System.out.println("Error: Open a file first!");
            return;
        }

        String key = arguments.trim();
        if (key.isEmpty()) {
            System.out.println("Usage: search <key>");
            return;
        }

        String[] path = key.split("\\s+");

        // Start the traversal from the initial index 0
        JsonType result = session.getRootNode().getByPath(path, 0);

        if (result == null) {
            System.out.println("Key '" + key + "' not found.");
        } else {
            System.out.println(result.toPrettyString(0));

        }
    }

    /**
     * Returns a short description of the command for reference.
     *
     * @return a string with the command's syntax and purpose
     */
    @Override
    public String getDescription() {
        return "search <key> - Search for a value by key in the entire JSON.";
    }
}