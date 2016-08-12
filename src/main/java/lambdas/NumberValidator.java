package lambdas;

import java.io.*;

import static java.lang.Integer.parseInt;

public class NumberValidator {
    private BufferedReader reader;
    private final Writer writer;

    public NumberValidator(Reader reader, Writer writer) {
        this.reader = new BufferedReader(reader);
        this.writer = writer;
    }

    public static void main(String... args) {
        NumberValidator numberValidator = new NumberValidator(new InputStreamReader(System.in),
                new OutputStreamWriter(System.out));
        runApp(numberValidator);
    }

    public static void runApp(NumberValidator numberValidator) {
        numberValidator.promptUntilValidMenuChoice();
        numberValidator.promptUntilValidNumberEntered();
    }

    public int promptUntilValidMenuChoice() {
        promptForMenuChoice();
        String input = readInput();

        while (invalidMenuChoice(input)) {
            promptForMenuChoice();
            input = readInput();
        }
        return parseToInt(input);
    }

    public int promptUntilValidNumberEntered() {
        promptForNumber();
        String input = readInput();

        while (invalid(input)) {
            promptForNumber();
            input = readInput();
        }
        return parseToInt(input);
    }

    private void promptForMenuChoice() {
        write("Please choose:\n(1) Validate number\n(2) Exit");
    }

    private void write(String message) {
        try {
            writer.write(message + "\n");
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException("Error when writing message");
        }
    }

    private String readInput() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Error reading input");
        }
    }

    private boolean invalidMenuChoice(String input) {
        if (!validMenuChoice(input)) {
            write("Not a valid menu option!");
            return true;
        }
        return false;
    }

    private boolean validMenuChoice(String input) {
        return input.equals("1") || input.equals("2");
    }

    private int parseToInt(String input) {
        return parseInt(input);
    }

    private void promptForNumber() {
        write("Please enter a number:");
    }

    private boolean invalid(String input) {
        if (!isNumber(input)) {
            write("Not a number!");
            return true;
        }

        if (!isInRange(input)) {
            write("Not in range!");
            return true;
        }
        return !isNumber(input);
    }

    private boolean isInRange(String input) {
        int number = parseToInt(input);
        return number >= 0 && number <= 100;
    }

    private boolean isNumber(String input) {
        try {
            parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
