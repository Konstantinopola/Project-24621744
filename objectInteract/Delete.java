package bg.tu_varna.sit.f24621744.task.objectInteract;

import bg.tu_varna.sit.f24621744.task.Command;
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
     */
    @Override
    public void execute(String arguments, Session session) {
        if (!session.isFileOpen()) {
            System.out.println("Error: Open a file first.");
            return;
        }

        arguments = arguments.trim();
        if (arguments.isEmpty()) {
            System.out.println("Usage: delete <path>");
            return;
        }

        String[] path = arguments.split("\\s+");

        boolean isDeleted = session.getRootNode().deleteByPath(path, 0);

        if (isDeleted) {
            System.out.println("Element deleted successfully.");
        } else {
            System.out.println("Error: Invalid path. Element doesn`t not exist.");
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