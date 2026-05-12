package bg.tu_varna.sit.f24621744.task.objectInteract;

import bg.tu_varna.sit.f24621744.task.Session;
import bg.tu_varna.sit.f24621744.task.fileInteract.Command;
import bg.tu_varna.sit.f24621744.task.jsonWork.CommandHandler;
import bg.tu_varna.sit.f24621744.task.jsonWork.JsonType;
import bg.tu_varna.sit.f24621744.task.parser.*;

public class Set implements Command {

    @Override
    public void execute(String arguments, Session session) {
        if (!session.isFileOpen()) {
            System.out.println("Error: Open a file first!");
            return;
        }

        String[] parts = arguments.trim().split("\\s+");
        if (parts.length < 2) {
            System.out.println("Usage: set <path> <string>");
            return;
        }

        // last argument is a new JSON string.
        String jsonString = parts[parts.length - 1];
        // everything else - way
        String[] path = new String[parts.length - 1];
        System.arraycopy(parts, 0, path, 0, parts.length - 1);

        try {
            // parse a new string into an object
            JsonType newValue = JsonParser.parseString(jsonString);

            // Пытаемся заменить
            boolean success = CommandHandler.set(session.getRootNode(), path, newValue);

            if (success) {
                System.out.println("Value updated successfully.");
            } else {
                System.out.println("\"Error: Element at the specified path doesn`t exist.");
            }
        } catch (Exception e) {
            System.out.println("Error: Invalid JSON format for the new value.");
        }
    }

    @Override
    public String getDescription() {
        return "set <path> <string> - updates the value at the specified path.";
    }
}
