

public class Parser {

    public Command parse(String input) {
        input = input.trim();

        if (input.equals("bye") || input.equals("kthxbye")) {
            return new ExitCommand();
        }
        if (input.equals("list")) {
            return new ListCommand();
        }
 
        String[] parts = input.split(" ");
        String command = parts[0];
        switch (command) {
            default -> {
                return new AddCommand(parts);
            }
        }

    }

}