package bg.tu_varna.sit.f24621744.task.fileInteract;

import bg.tu_varna.sit.f24621744.task.Session;
import bg.tu_varna.sit.f24621744.task.Command;

/**
 * The {@code exit} command terminates the application.
 * <p>
 * Displays a farewell message and calls {@link System#exit(int)} with code {@code 0},
 * which indicates normal program termination.
 * </p>
 * <p>
 * <b>Warning:</b> This command does not check for unsaved changes.
 * All unsaved data will be lost upon exit.
 * </p>
 *
 * <p><b>Syntax:</b> {@code exit}</p>
 */
public class Exit implements Command {

    /**
     * Terminates the application.
     * <p>
     * Displays a termination message and calls {@link System#exit(int)}
     * with code {@code 0} (successful completion).
     * </p>
     *
     * @param arguments command arguments (not used)
     * @param session current application session (not used)
     */
    @Override
    public void execute(String arguments, Session session) {
        System.out.println("Exiting the program...");
        System.exit(0);
    }

    /**
     * Returns a short description of the command for reference.
     *
     * @return a string with the command's syntax and purpose
     */
    @Override
    public String getDescription() {
        return "exit - exists the program";
    }
}