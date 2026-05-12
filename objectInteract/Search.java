package bg.tu_varna.sit.f24621744.task.objectInteract;

import bg.tu_varna.sit.f24621744.task.fileInteract.Command;
import bg.tu_varna.sit.f24621744.task.Session;
import bg.tu_varna.sit.f24621744.task.jsonWork.CommandHandler;
import bg.tu_varna.sit.f24621744.task.jsonWork.JsonType;
import java.util.ArrayList;
import java.util.List;

public class Search implements Command {

    @Override
    public void execute(String arguments, Session session) {
        if (!session.isFileOpen()) {
            System.out.println("Error: Open a file first.");
            return;
        }

        String key = arguments.trim();
         if (key.isEmpty()) {
            System.out.println("Usage: search <key>");
            return;
        }

        List<JsonType> results = new ArrayList<>();
        CommandHandler.search(session.getRootNode(), key, results);

        if (results.isEmpty()) {
            System.out.println("Key '" + key + "' is not found");
        } else {
            for (JsonType res : results) {
                System.out.println(res.toPrettyString(0));
            }
        }
    }

    @Override
    public String getDescription() {
        return "search <key> - Search for a value by key in the entire JSON.";
    }
}