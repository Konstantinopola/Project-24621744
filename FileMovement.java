package tu_varna.sit.f24621744;


import tu_varna.sit.f24621744.FileActions.FileAction;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileMovement implements FileAction {

    private String fileName;
    Scanner scanner = new Scanner(System.in);

    public FileMovement() {

    }

    @Override
    public void OpenFile() {

        String fileName = scanner.nextLine();
        File file = new File(fileName);

        java.io.File myObj = new java.io.File(fileName);

        try (Scanner myReader = new Scanner(myObj)) {
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
    }
}
