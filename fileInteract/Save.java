package bg.tu_varna.sit.f24621744.task.fileInteract;

import bg.tu_varna.sit.f24621744.task.Session;
import bg.tu_varna.sit.f24621744.task.jsonWork.CommandHandler;
import bg.tu_varna.sit.f24621744.task.jsonWork.JsonType;
import java.io.IOException;
import java.nio.file.Files;

public class Save implements Command {

     @Override
    public void execute(String arguments, Session session) {
        if (!session.isFileOpen()) {
            System.out.println("Error: No file is open to save.");
            return;
        }
        JsonType needToSave = session.getRootNode();

        if (arguments != null && !arguments.trim().isEmpty()) {
            String[] path = arguments.trim().split("\\s+");
            needToSave = CommandHandler.get(session.getRootNode(), path);
            if (needToSave == null) {
                System.out.println("Error: The specified path does not exist. Nothing to save.");
                return;
            }
        }

        try {
            String prettyContent = needToSave.toPrettyString(0);
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