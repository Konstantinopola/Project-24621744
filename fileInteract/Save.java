package bg.tu_varna.sit.f24621744.task.fileInteract;

import bg.tu_varna.sit.f24621744.task.Command;
import bg.tu_varna.sit.f24621744.task.Session;

import java.io.IOException;
import java.nio.file.Files;

public class Save implements Command {

    @Override
    public void execute(String arguments, Session session) {
        if (!session.isFileOpen()) {
            System.out.println("Error: No file is open to save.");
            return;
        }

        try {

            String prettyContent = session.getRootNode().toPrettyString(0);
            Files.writeString(session.getCurrentFilePath(), prettyContent);
            System.out.println("Successfully saved " + session.getCurrentFilePath().getFileName());
        } catch (IOException e) {
            System.out.println("Error while saving file: " + e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "save - saves the currently open file";
    }
}