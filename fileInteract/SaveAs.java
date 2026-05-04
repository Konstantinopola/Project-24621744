package bg.tu_varna.sit.f24621744.task.fileInteract;

import bg.tu_varna.sit.f24621744.task.Command;
import bg.tu_varna.sit.f24621744.task.Session;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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

        try {
            String prettyJson = session.getRootNode().toPrettyString(0);
            Files.writeString(Paths.get(arguments), prettyJson);

            System.out.println("Successfully saved as " + arguments);
        } catch (IOException e) {
            System.out.println("Error while saving as: " + e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "save as <file> - saves the current JSON structure in a new location";
    }
}