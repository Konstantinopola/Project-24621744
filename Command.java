package bg.tu_varna.sit.f24621744.task;

public interface Command {
    void execute(String arguments, Session session);

    String getDescription();
}
