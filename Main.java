package bg.tu_varna.sit.f24621744.task;

import bg.tu_varna.sit.f24621744.task.fileInteract.*;

import java.util.Scanner;

public class Main {
    private final Session session = new Session();
    CommandFactory factory = new CommandFactory();


    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Program started. Type \"help\" for a list of commands.");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) continue;

            String[] parts = input.split("\\s+", 2);
            String commandName = parts[0].toLowerCase();                // "ClOSe" = "close"
            String arguments = parts.length > 1 ? parts[1] : "";        // command without details - "" empty

            Command command = factory.getCommand(userInput);

            if (command != null) {
                command.execute(arguments, session);
            } else if (commandName.equals("help")) {
                printHelp();
            } else {
                System.out.println("Unknown command. Type \"help\".");
            }
        }
    }

    private void printHelp() {
        System.out.println("Available commands:");
        for (Command cmd : factory.getAllCommands()) {
            System.out.println(cmd.getDescription());
        }
    }


}