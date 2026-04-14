package bg.tu_varna.sit.f24621744.task;

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
            Files.writeString(Paths.get(arguments), session.getJsonInMemory());
            System.out.println("Successfully saved as " + arguments);
        } catch (IOException e) {
            System.out.println("Error while saving as: " + e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "save as <file> - saves the open file in a new location";
    }
}
