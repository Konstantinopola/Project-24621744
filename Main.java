package bg.tu_varna.sit.f24621744.task;

import bg.tu_varna.sit.f24621744.task.Exception.JsonException;
import bg.tu_varna.sit.f24621744.task.Exception.JsonFileException;
import bg.tu_varna.sit.f24621744.task.Exception.JsonNavigationException;

import java.util.Scanner;

/**
 * The main application class, implementing the main command processing loop.
 * <p>
 * Reads user input from the console, recognizes commands using
 * {@link CommandFactory}, and executes them, passing the current {@link Session}.
 * The loop runs until the user enters the command {@code exit}.
 * </p>
 */
public class Main {

    /** The current application session storing the state of the open file. */
    private final Session session = new Session();

    /** Factory that provides access to all registered commands. */
    CommandFactory factory = new CommandFactory();

    /**
     * Starts the application's main loop.
     * <p>
     * In the loop:
     * <ol>
     * <li>Reads a line of user input.</li>
     * <li>Splits it into the command name and arguments.</li>
     * <li>Searches for a command in {@link CommandFactory}.</li>
     * <li>Executes the found command or displays a help message.</li>
     * </ol>
     * The special command {@code help} displays a list of all available commands.
     * </p>
     * @throws JsonException from all program, and sent to user correct error without stopping programm
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Program started. Type \"help\" for a list of commands.");

        while (true) {
            System.out.print("> ");
            String userInput = scanner.nextLine().trim();
            if (userInput.isEmpty()) continue;

            String[] parts = userInput.split("\\s+", 2);
            String commandName = parts[0].toLowerCase();                // "ClOSe" = "close"
            String arguments = parts.length > 1 ? parts[1] : "";        // command without details - "" empty

            Command command = factory.getCommand(commandName);

            try {
                if (command != null) {
                    command.execute(arguments, session);
                } else if (commandName.equals("help")) {
                    printHelp();
                } else {
                    throw new JsonNavigationException("Unknown command. Type \"help\".");
                }

            } catch (JsonException e) {
                System.out.println(e.getMessage());

            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
        }
    }

    /**
     * Displays a list of all available commands and their descriptions in the console.
     * <p>
     * Called when the user enters the command {@code help}.
     * Iterates over all commands registered in {@link CommandFactory},
     * and for each, displays the string returned by {@link Command#getDescription()}.
     * </p>
     */
    private void printHelp() {
        System.out.println("Available commands:");
        for (Command cmd : factory.getAllCommands()) {
            System.out.println(cmd.getDescription());
        }
    }


}