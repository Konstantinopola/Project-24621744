package bg.tu_varna.sit.f24621744.task.ObjectInteract;

import bg.tu_varna.sit.f24621744.task.Command;
import bg.tu_varna.sit.f24621744.task.Session;

public class Print implements Command {
    @Override
    public void execute(String arguments, Session session) {
        if (!session.isFileOpen()) {
            System.out.println("Error: No file open.");
            return;
        }
        System.out.println(session.getRootNode().toJsonString());
    }

    @Override
    public String getDescription() {
        return "print - displays the current JSON structure";
    }
}
