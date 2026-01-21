import java.util.*;
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
            String response = parser.parse(userInput);

            System.out.println(LINE);
            System.out.println(response);
            System.out.println(LINE + "\n");

            if (userInput.equals("bye")) {
                break;
            }
        }
    }
}