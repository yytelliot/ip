

public class Parser {

    public String parse(String input) {
        String message = "";
        switch (input) {
            case "bye" -> {
                message = "See you again soon! UwU";
            }
            case "" -> {
                message = "Didn't catch that, please say again?";
            }
            default -> message = input;
        }

        return message;
    }

}