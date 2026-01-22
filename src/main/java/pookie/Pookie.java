package pookie;

import java.util.Scanner;
import pookie.command.Command;
import pookie.exception.PookieException;

/**
 * Main class for the Pookie X3 application.
 */
public class Pookie {
    private static final String LINE = "==============================";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Parser parser = new Parser();

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
}