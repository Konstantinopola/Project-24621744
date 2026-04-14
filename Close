package bg.tu_varna.sit.f24621744.task;

public class Close implements Command {

    @Override
    public void execute(String arguments, Session session) {
        if (!session.isFileOpen()) {
            System.out.println("There are no open files to close.");
            return;
        }

        session.closeFile();
        System.out.println("The file was closed successfully. Changes were discarded.");
    }

    @Override
    public String getDescription() {
        return "closed";
    }
}
