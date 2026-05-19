package bg.tu_varna.sit.f24621744.task.objectInteract;

import bg.tu_varna.sit.f24621744.task.Command;
import bg.tu_varna.sit.f24621744.task.Exception.JsonException;
import bg.tu_varna.sit.f24621744.task.Exception.JsonFileException;
import bg.tu_varna.sit.f24621744.task.Exception.JsonTypeException;
import bg.tu_varna.sit.f24621744.task.Session;
import bg.tu_varna.sit.f24621744.task.parser.JsonParser;

/**
 * The {@code validate} command: checks the validity of JSON data.
 * <p>
 * Works in two modes:
 * <ul>
 * <li><b>Without arguments</b> — checks the validity of the currently open file
 * (a file is considered valid if it was opened successfully).</li>
 * <li><b>With arguments</b> — attempts to parse the passed string as JSON
 * and reports whether it is valid JSON.</li>
 * </ul>
 * </p>
 *
 * <p><b>Syntax:</b></p>
 * <pre>
 * validate — check the currently open file
 * validate {"key": "value"} — check the passed string
 * validate [1, 2, 3] — check an array
 * </pre>
 */
public class Validate implements Command {

    /**
     * Performs JSON validation.
     * <p>
     * If no arguments are passed, checks whether the file is open in the current session.
     * (An open file is always valid JSON, since it was already successfully parsed when opened.
     * </p>
     * <p>
     * If arguments are passed, attempts to parse the string using
     * {@link JsonParser#parseString(String)} and displays the result.
     * </p>
     *
     * @param arguments - JSON string to validate, or an empty string
     * to validate the current file.
     * @param session - current application session.
     * @throws JsonTypeException if the passed string is not in valid JSON format
     */
    @Override
    public void execute(String arguments, Session session) {
        // If user just write command - checking file
        if (arguments.isEmpty()) {
            if (session.isFileOpen()) {
                System.out.println("Valid JSON (current file is valid).");
            } else {
                throw new JsonFileException("no file is open and no validation string was provided.");
            }
            return;
        }

        // If user write command with argument - check argument
        try {
            JsonParser.parseString(arguments);
            System.out.println("Valid JSON.");

        } catch (JsonException e) {
            throw new JsonTypeException("invalid JSON error: " + e.getMessage());
        }
    }

    /**
     * Returns a short description of the command for reference.
     *
     * @return a string with the command's syntax and purpose
     */
    @Override
    public String getDescription() {
        return "validate <string> - Checks if the passed string matches (or the current file) is valid JSON data.";
    }
}