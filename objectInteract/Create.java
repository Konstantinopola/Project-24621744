package bg.tu_varna.sit.f24621744.task.objectInteract;
import bg.tu_varna.sit.f24621744.task.fileInteract.Command;
import bg.tu_varna.sit.f24621744.task.Session;
import bg.tu_varna.sit.f24621744.task.jsonWork.CommandHandler;
import bg.tu_varna.sit.f24621744.task.jsonWork.JsonObject;
import bg.tu_varna.sit.f24621744.task.jsonWork.JsonType;
import bg.tu_varna.sit.f24621744.task.parser.*;

public class Create implements Command {
    @Override
    public void execute(String arguments, Session session) {
        if (!session.isFileOpen()) {
            System.out.println("Error: Open a file first.");
            return;
        }

        String[] parts = arguments.split("\\s+");
        if (parts.length < 2) {
            System.out.println("Usage: create <key> <value>");
            return;
        }


        String rawValue = parts[parts.length - 1];
        String[] path = new String[parts.length - 1];
         System.arraycopy(parts, 0, path, 0, parts.length - 1);
        
         try {
            JsonType newValue = JsonParser.parseString(rawValue);
            JsonObject root = (JsonObject) session.getRootNode();
            CommandHandler.create(root, path, newValue);

            System.out.println("Element created successfully.");
        }

        catch (Exception e) {
            System.out.println("Error: incorrect type of JSON: " + e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "create <key> <value> - adds a new property to the JSON object";
    }
}