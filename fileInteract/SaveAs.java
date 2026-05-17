package bg.tu_varna.sit.f24621744.task.fileInteract;

import bg.tu_varna.sit.f24621744.task.Session;
import bg.tu_varna.sit.f24621744.task.jsonWork.CommandHandler;
import bg.tu_varna.sit.f24621744.task.jsonWork.JsonType;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import Command;

public class SaveAs implements Command {
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

        if (parts.length > 1) {
            String[] path = new String[parts.length - 1];
            // Cutting what put user and using only path
            System.arraycopy(parts, 1, path, 0, parts.length - 1);

            needToSave = CommandHandler.get(session.getRootNode(), path);

            if (needToSave == null) {
                System.out.println("Error: The specified path does not exist. Nothing to save.");
                return;
            }
        }
        try {
            String prettyJson = needToSave.toPrettyString(0);
            Files.writeString(Paths.get(parts[0]), prettyJson);

            System.out.println("Successfully saved as " + arguments);
        } catch (IOException e) {
            System.out.println("Error while saving as: " + e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "save_as <file> - saves the current JSON structure in a new location";
    }
}