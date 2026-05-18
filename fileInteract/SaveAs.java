package bg.tu_varna.sit.f24621744.task.fileInteract;

import bg.tu_varna.sit.f24621744.task.Session;
import bg.tu_varna.sit.f24621744.task.jsonWork.JsonType;
import bg.tu_varna.sit.f24621744.task.Command;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * The {@code save_as} command saves a JSON structure to a new file at the specified path.
 * <p>
 * Unlike {@link Save}, this command allows you to specify an arbitrary path for saving,
 * without changing the currently open session file.
 * Additionally, it supports saving a single sub-element of a JSON tree.
 * </p>
 *
 * <p><b>Syntax:</b></p>
 * <pre>
 * save_as output.json — save the entire tree to output.json
 * save_as output.json users — save the "users" element to output.json
 * save_as output.json users 0 name — save the element at the path users -> [0] -> name
 * </pre>
 */
public class SaveAs implements Command {

    /**
     * Saves a JSON structure to a new file.
     * <p>
     * Operation algorithm:
     * <ol>
     * <li>Checks that the file is open and the arguments are not empty.</li>
     * <li>The first argument is considered the path to the new file.</li>
     * <li>The remaining arguments (if any) are interpreted as the path
     * to the sub-element to be saved.</li>
     * <li>Writes formatted JSON to the specified file.</li>
     * </ol>
     * </p>
     *
     * @param arguments is the new file path and the optional path to the sub-element,
     * separated by spaces
     * @param session is the current application session
     */
    @Override
    public void execute(String arguments, Session session) {
        if (!session.isFileOpen()) {
            System.out.println("Error: No file is open to save.");
            return;
        }
        if (arguments.isEmpty()) {
            System.out.println("Error: Please provide a new file path.");
            return;
        }
        String[] parts = arguments.trim().split("\\s+");
        JsonType needToSave = session.getRootNode();
        String targetFilePath = parts[0];


        if (!targetFilePath.toLowerCase().endsWith(".json")) {
            System.out.println("Error: Target file must have a .json extension.");
            return;
        }

        if (parts.length > 1) {
            String[] path = new String[parts.length - 1];
            // Cutting what put user and using only path
            System.arraycopy(parts, 1, path, 0, parts.length - 1);

            needToSave = session.getRootNode().getByPath(path, 0);
            if (needToSave == null) {
                System.out.println("Error: The specified path does not exist. Nothing to save.");
                return;
            }
        }
        try {
            String prettyJson = needToSave.toPrettyString(0);
            Files.writeString(Paths.get(targetFilePath), prettyJson);

            System.out.println("Successfully saved as " + arguments);
        } catch (IOException e) {
            System.out.println("Error while saving as: " + e.getMessage());
        }
    }

    /**
     * Returns a short description of the command for reference.
     *
     * @return a string with the command's syntax and purpose
     */
    @Override
    public String getDescription() {
        return "save_as <file> - saves the current JSON structure in a new location";
    }
}