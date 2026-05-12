package bg.tu_varna.sit.f24621744.task.fileInteract;


import bg.tu_varna.sit.f24621744.task.Session;

public class Exit implements Command {
    @Override
    public void execute(String arguments, Session session) {
        System.out.println("Exiting the program...");
        System.exit(0);
    }

    @Override
    public String getDescription() {
        return "exit - exists the program";
    }
}