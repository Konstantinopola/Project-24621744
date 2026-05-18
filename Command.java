package bg.tu_varna.sit.f24621744.task;

/**
 * An interface representing a command in an application.
 * <p>
 * All commands (open, close, save, delete, etc.) implement this interface,
 * allowing them to be handled consistently via {@link CommandFactory}.
 * Uses the <b>Command</b> design pattern.
 * </p>
 */

public interface Command {

    /**
     * Executes the logic of the given command.
     *
     * @param arguments is a string of arguments passed by the user after the command name;
     * may be an empty string if no arguments are specified.
     * @param session is the current application session storing the state of the open file.
     */

    void execute(String arguments, Session session);

    /**
     * Returns a short description of the command for display in the help.
     *
     * @return a string describing the command's syntax and purpose
     */

    String getDescription();
}