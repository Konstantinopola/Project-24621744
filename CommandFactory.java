package bg.tu_varna.sit.f24621744.task;


import bg.tu_varna.sit.f24621744.task.objectInteract.*;
import bg.tu_varna.sit.f24621744.task.fileInteract.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * JSON editor application command factory.
 * <p>
 * Implements the <b>Factory</b> design pattern: creates and stores all available
 * commands as a {@code name → command} dictionary. Allows you to retrieve a desired command
 * by its string name and list all available commands for help display.
 * </p>
 * <p>
 * The command order is preserved using a {@link LinkedHashMap}, ensuring a stable
 * output order when calling the {@code help} command.
 * </p>
 */
public class CommandFactory {

    /** Dictionary of registered commands: key is the command name, value is its implementation. */
    private final Map<String, Command> commands = new LinkedHashMap<>();

    /**
     * Creates a factory and registers all available application commands.
     * <p>
     * List of registered commands:
     * <ul>
     * <li>{@code open} — open a JSON file</li>
     * <li>{@code close} — close the current file</li>
     * <li>{@code save} — save the current file</li>
     * <li>{@code save_as} — save to a new file</li>
     * <li>{@code create} — create a new element</li>
     * <li>{@code exit} — exit the program</li>
     * <li>{@code print} — print a JSON structure</li>
     * <li>{@code validate} — validate JSON</li>
     * <li>{@code search} — search by key</li>
     * <li>{@code set} — change the value by path</li>
     * <li>{@code delete} — delete an element by path</li>
     * </ul>
     * </p>
     */
    public CommandFactory() {
        commands.put("open", new Open());
        commands.put("close", new Close());
        commands.put("save", new Save());
        commands.put("save_as", new SaveAs());
        commands.put("create", new Create());
        commands.put("exit", new Exit());
        commands.put("print", new Print());
        commands.put("validate", new Validate());
        commands.put("search", new Search());
        commands.put("set", new Set());
        commands.put("delete", new Delete());
    }

    /**
     * Returns a command by its name.
     * <p>
     * Only the first word (the command name) is extracted from the passed string;
     * The remaining words are ignored. This allows you to pass either a bare
     * command name or a string with arguments.
     * </p>
     *
     * @param commandName is the command name (possibly with arguments)
     * @return the {@link Command} implementation corresponding to the name,
     * or {@code null} if the command is not found
     */
    public Command getCommand(String commandName) {
        String baseCommand = commandName.split("\\s+")[0];
        return commands.get(baseCommand);
    }

    /**
     * Returns all registered commands.
     * <p>
     * Used to display a full list of commands in the help ({@code help}).
     * </p>
     *
     * @return an iterable collection of all registered {@link Command}
     */
    public Iterable<Command> getAllCommands() {
        return commands.values();
    }
}