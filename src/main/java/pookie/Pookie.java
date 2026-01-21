import java.util.*;

import pookie.command.Command;
public class Pookie {
    private static final String LINE = "==============================";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Parser parser = new Parser();

        System.out.println(LINE);
        System.out.println("Hello! I'm Pookie :3");
        System.out.println("What can I do for you? OwO"); 
        System.out.println(LINE + "\n");

        while (true) {
            String userInput = sc.nextLine();
            Command command = parser.parse(userInput);
            String response = command.execute();

            System.out.println(LINE);
            System.out.println(response);
            System.out.println(LINE + "\n");

            if (command.isExit()) {
                break;
            }
        }
    }
}