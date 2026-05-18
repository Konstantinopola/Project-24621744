package bg.tu_varna.sit.f24621744.task.fileInteract;

import bg.tu_varna.sit.f24621744.task.Session;
import bg.tu_varna.sit.f24621744.task.jsonWork.JsonType;
import bg.tu_varna.sit.f24621744.task.Command;

import java.io.IOException;
import java.nio.file.Files;

/**
 * The {@code save} command saves a JSON structure to the currently open file.
 * <p>
 * By default, the entire JSON tree is saved. If the argument is
 * a path to a sub-element, only that sub-element is saved.
 * </p>
 *
 * <p><b>Syntax:</b></p>
 * <pre>
 * save — save the entire tree
 * save users — save only the "users" element
 * save users 0 name — save the element at the path users -> [0] -> name
 * </pre>
 */
public class Save implements Command {

    /**
     * Saves a JSON structure to the current file.
     * <p>
     * How it works:
     * <ol>
     * <li>Checks that the file is open.</li>
     * <li>If no arguments are passed, saves the entire JSON tree.</li>
     * <li>If arguments are passed, parses them as a path and saves
     * only the specified sub-element.</li>
     * <li>Writes formatted JSON to a file using
     * {@link Files#writeString}.</li>
     * </ol>
     * </p>
     *
     * @param arguments is the path to the sub-element to save (space-separated),
     * or an empty string to save the entire tree.
     * @param session is the current application session.
     */
    @Override
    public void execute(String arguments, Session session) {
        if (!session.isFileOpen()) {
            System.out.println("Error: No file is open to save.");
            return;
        }
        JsonType needToSave = session.getRootNode();

        if (arguments != null && !arguments.trim().isEmpty()) {
            String[] path = arguments.trim().split("\\s+");
            needToSave = session.getRootNode().getByPath(path, 0);
            if (needToSave == null) {
                System.out.println("Error: The specified path does not exist. Nothing to save.");
                return;
            }
        }

        try {
            String prettyContent = needToSave.toPrettyString(0);
            Files.writeString(session.getCurrentFilePath(), prettyContent);
            System.out.println("Successfully saved " + session.getCurrentFilePath().getFileName());
        } catch (IOException e) {
            System.out.println("Error while saving file: " + e.getMessage());
        }
    }

    /**
     * Returns a short description of the command for reference.
     *
     * @return a string with the command's syntax and purpose
     */
    @Override
    public String getDescription() {
        return "save - saves the currently open file";
    }
}