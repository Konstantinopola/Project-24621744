package bg.tu_varna.sit.f24621744.task.objectInteract;

import bg.tu_varna.sit.f24621744.task.Session;
import bg.tu_varna.sit.f24621744.task.Command;
import bg.tu_varna.sit.f24621744.task.jsonWork.JsonType;

import java.util.ArrayList;
import java.util.List;

public class Search implements Command {
    @Override
    public void execute(String arguments, Session session) {
        if (!session.isFileOpen()) {
            System.out.println("Error: No file open.");
            return;
        }

        String key = arguments.trim();
        List<JsonType> results = new ArrayList<>();
        CommandHandler.search(session.getRootNode(), key, results);

        if (results.isEmpty()) {
            System.out.println("Key '" + key + "' is not found");
        } else {
            for (JsonType res : results) {
                System.out.println(res.toJsonString());
            }
        }
    }

    @Override
    public String getDescription() {
        return "search <key> - Search for a value by key in the entire JSON.";
    }
}