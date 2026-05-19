package bg.tu_varna.sit.f24621744.task.objectInteract;

import bg.tu_varna.sit.f24621744.task.Command;
import bg.tu_varna.sit.f24621744.task.Exception.JsonException;
import bg.tu_varna.sit.f24621744.task.Exception.JsonFileException;
import bg.tu_varna.sit.f24621744.task.Exception.JsonNavigationException;
import bg.tu_varna.sit.f24621744.task.Exception.JsonTypeException;
import bg.tu_varna.sit.f24621744.task.Session;

/**
 * The {@code delete} command: deletes an element from a JSON structure at the specified path.
 * <p>
 * The path is specified as a sequence of keys (for objects) or
 * numeric indices (for arrays), separated by spaces.
 * </p>
 *
 * <p><b>Syntax:</b> {@code delete <key1> [<key2> ...]}</p>
 *
 * <p><b>Examples:</b></p>
 * <pre>
 * delete name
 * delete users 0
 * delete address city
 * </pre>
 */
public class Delete implements Command {

    /**
     * Searches for an element by path and removes it if found.
     *
     * @param arguments path string (space-separated keys)
     * @param session current session
     * @throws JsonFileException if the path does not exist
     * @throws JsonNavigationException if {@code arguments} are empty
     */
    @Override
    public void execute(String arguments, Session session) {
        if (!session.isFileOpen()) {
            throw new JsonFileException("open a file first.");
        }

        arguments = arguments.trim();
        if (arguments.isEmpty()) {
            throw new JsonNavigationException("Usage: delete <path>");
        }

        String[] path = arguments.split("\\s+");

        try {
            boolean isDeleted = session.getRootNode().deleteByPath(path, 0);
            if (isDeleted) {
                System.out.println("Element deleted successfully.");
            }
        } catch (JsonException e) {
            throw new JsonTypeException("deleting error: " + e.getMessage());
        }
    }

    /**
     * Returns a short description of the command for reference.
     *
     * @return a string with the command's syntax and purpose
     */
    @Override
    public String getDescription() {
        return "delete <path> - remove the element at the specified path";
    }
}