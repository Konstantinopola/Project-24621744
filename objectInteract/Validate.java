package bg.tu_varna.sit.f24621744.task.objectInteract;

import bg.tu_varna.sit.f24621744.task.fileInteract.Command;
import bg.tu_varna.sit.f24621744.task.Session;
import bg.tu_varna.sit.f24621744.task.parser.JsonParser;


public class Validate implements Command {

    @Override
    public void execute(String arguments, Session session) {
        // If user just write command - checking file
        if (arguments.isEmpty()) {
            if (session.isFileOpen()) {
                System.out.println("Valid JSON (current file is valid).");
            } else {
                System.out.println("Error: No file is open and no validation string was provided.");
            }
            return;
        }

        // If user write command with argument - check argument
        try {
            JsonParser.parseString(arguments);
            System.out.println("Valid JSON.");

        } catch (Exception e) {
            System.out.println("Invalid JSON: " + e.getMessage());
        }
    }

    @Override
    public String getDescription() {
   return "validate <string> - Checks if the passed string matches (or the current file) is valid JSON data.";
    }
}