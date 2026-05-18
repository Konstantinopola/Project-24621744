package bg.tu_varna.sit.f24621744.task.fileInteract;

import bg.tu_varna.sit.f24621744.task.Session;
import bg.tu_varna.sit.f24621744.task.jsonWork.JsonObject;
import bg.tu_varna.sit.f24621744.task.jsonWork.JsonType;
import bg.tu_varna.sit.f24621744.task.Command;
import bg.tu_varna.sit.f24621744.task.parser.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * {@code open} command: Opens a JSON file or creates a new one.
 * <p>
 * If the file exists, reads its contents and parses them as JSON.
 * If the file doesn't exist, creates a new empty {@link JsonObject} structure
 * at the specified path (the file is only physically created upon saving).
 * </p>
 *
 * <p><b>Syntax:</b> {@code open <file path>}</p>
 *
 * <p><b>Examples:</b></p>
 * <pre>
 * open data.json
 * open /home/user/config.json
 * </pre>
 */
public class Open implements Command {

    /**
     * Opens a file at the specified path.
     * <p>
     * How it works:
     * <ol>
     * <li>Checks that the argument (path) is not empty.</li>
     * <li>If the file exists, reads and parses it as JSON.</li>
     * <li>If the file does not exist, creates an empty {@link JsonObject}.</li>
     * <li>In both cases, calls {@link Session#openFile(Path, JsonType)}.</li>
     * </ol>
     * </p>
     *
     * @param arguments path to the file to open
     * @param session current application session
     */
    @Override
    public void execute(String arguments, Session session) {
        if (arguments.isEmpty()) {
            System.out.println("Error: Please provide a path to a file (for example: open data.json)");
            return;
        }

        if (!arguments.toLowerCase().endsWith(".json")) {
            System.out.println("Error: Only .json files are supported.");
            return;
        }

        Path path = Paths.get(arguments);
        try {

            if (Files.exists(path)) {
                String content = Files.readString(path);
                try {
                    JsonType parsedJson = JsonParser.parseString(content);
                    session.openFile(path, parsedJson);
                    System.out.println("Successfully opened: " + path.getFileName());
                }
                catch (Exception e) {
                    System.out.println("Error: The file has invalid JSON format or broken, error: (" + e.getMessage() + ")");
                    System.out.println("File was NOT opened. Please fix the file or open a valid one.");
                }
            } else {
                System.out.println("File not found, creating a new JSON object.");
                session.openFile(path, new JsonObject());
                System.out.println("Successfully created: " + path.getFileName());
            }
        } catch (IOException e) {
            System.out.println("Error while opening file: " + e.getMessage());
        }
    }

    /**
     * Returns a short description of the command for reference.
     *
     * @return a string with the command's syntax and purpose
     */
    @Override
    public String getDescription() {
        return "open <file>";
    }
}