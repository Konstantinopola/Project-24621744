package bg.tu_varna.sit.f24621744.task; 


import bg.tu_varna.sit.f24621744.task.objectInteract.*;
import bg.tu_varna.sit.f24621744.task.fileInteract.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class CommandFactory {
    private final Map<String, Command> commands = new LinkedHashMap<>();

    // Separate creation of all commands to a factory class for better organization
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

    public Command getCommand(String commandName) {
        String baseCommand = commandName.split("\\s+")[0];
        return commands.get(baseCommand);
    }

    public Iterable<Command> getAllCommands() {
        return commands.values();
    }
}