package bg.tu_varna.sit.f24621744.task.objectInteract;

import bg.tu_varna.sit.f24621744.task.Command;
import bg.tu_varna.sit.f24621744.task.Session;
import jsonWork.CommandHandler;

public class Delete implements Command {

    @Override
    public void execute(String arguments, Session session) {
        if (!session.isFileOpen()) {
            System.out.println("Error: Open a file first.");
            return;
        }

        arguments = arguments.trim();
        if (arguments.isEmpty()) {
            System.out.println("Usage: delete <path>");
            return;
        }

        String[] path = arguments.split("\\s+");

        boolean isDeleted = CommandHandler.delete(session.getRootNode(), path);

        if (isDeleted) {
            System.out.println("Element deleted successfully.");
        } else {
            System.out.println("Error: Invalid path. Element doesn`t not exist.");
        }
    }

 @Override
    public String getDescription() {
        return "delete <path> - remove the element at the specified path";
    }
}
