package pookie;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;
import pookie.command.Command;
import pookie.exception.PookieException;
import pookie.storage.Storage;
import pookie.ui.Parser;

/**
 * Main class for the Pookie X3 application.
 */
public class Pookie {
    private static final String LINE = "==============================";

    private final Storage storage;
    private final Parser parser;

    public Pookie(String filePath) {
        this.storage = new Storage(Paths.get(filePath));
        this.parser = new Parser();
    }


    public void run() {
        // Implementation of run method if needed
        Scanner sc = new Scanner(System.in);
        Command.setStorage(storage);

        try {
            storage.loadIntoTaskList();
        } catch (IOException e) {
            System.out.println("Error loading data file: " + e.getMessage());
        }

        System.out.println(LINE);
        System.out.println("Hello! I'm Pookie :3");
        System.out.println("What can I do for you? OwO"); 
        System.out.println(LINE + "\n");
        try {

            // Main loop
            while (true) {

                // read user input
                String userInput = sc.nextLine();

                // parse and execute command
                Command command = parser.parse(userInput);

                try {
                    
                    // try to execute the command
                    String response = command.execute();

                    // print response
                    System.out.println(LINE);
                    System.out.println(response);
                    System.out.println(LINE + "\n");

                    // exit if command is exit command
                    if (command.isExit()) {
                        break;
                    }

                } catch (PookieException e) {
                    // print error message
                    System.out.println(LINE);
                    System.out.println(e.getMessage());
                    System.out.println(LINE + "\n");
                }
            } 
            
        } finally {
            sc.close();
        }
    }   
   

    public static void main(String[] args) {

        String filePath = "pookie_data.txt";
        if (args.length > 0) {
            filePath = args[0];
        }
        
        new Pookie(filePath).run();
 
    }

}