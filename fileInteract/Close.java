package bg.tu_varna.sit.f24621744.task.fileInteract;

import bg.tu_varna.sit.f24621744.task.Session;
import bg.tu_varna.sit.f24621744.task.Command;

/**
 * {@code close} command: Closes the currently open file without saving.
 * <p>
 * Resets the session state: the file path and JSON tree
 * are set to {@code null}. All unsaved changes will be lost.
 * </p>
 *
 * <p><b>Syntax:</b> {@code close}</p>
 */
public class Close implements Command {

    /**
     * Closes the current file.
     * <p>
     * If the file is not open, displays a corresponding message.
     * If the file is open, calls {@link Session#closeFile()} and reports
     * a successful close. Changes are <b>not saved</b>.
     * </p>
     *
     * @param arguments command arguments (not used)
     * @param session current application session
     */
    @Override
    public void execute(String arguments, Session session) {
        if (!session.isFileOpen()) {
            System.out.println("There are no open files to close.");
            return;
        }

        session.closeFile();
        System.out.println("The file was closed successfully. Changes were discarded.");
    }

    /**
     * Returns a short description of the command for reference.
     *
     * @return a string with the command's syntax and purpose
     */
    @Override
    public String getDescription() {
        return "close - close the file";
    }
}