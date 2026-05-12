package fileInteract;

public interface Command {
    void execute(String arguments, Session session);

    String getDescription();
}
