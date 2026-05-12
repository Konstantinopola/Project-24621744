package fileInteract


import bg.tu_varna.sit.f24621744.task.Session;
import bg.tu_varna.sit.f24621744.task.jsonWork.JsonObject;
import bg.tu_varna.sit.f24621744.task.jsonWork.JsonType;
import bg.tu_varna.sit.f24621744.task.parser.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Open implements Command {

    @Override
    public void execute(String arguments, Session session) {
        if (arguments.isEmpty()) {
            System.out.println("Error: Please provide a path to a file (for example: open data.json)");
            return;
        }

        Path path = Paths.get(arguments);
        try {

            if (Files.exists(path)) {
                String content = Files.readString(path);
                JsonType parsedJson = JsonParser.parseString(content);
                session.openFile(path, parsedJson);
                System.out.println("Successfully opened: " + path.getFileName());
            } else {
                System.out.println("File not found, creating a new JSON object.");
                session.openFile(path, new JsonObject());
                System.out.println("Successfully created: " + path.getFileName());
            }


        } catch (IOException e) {
            System.out.println("Error while opening file: " + e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "open <file>";
    }
}