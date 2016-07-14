package lambdas;

import org.junit.Test;

import java.io.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class NumberValidatorTest {

    private static final String MENU_PROMPT = "Please choose:\n(1) Validate number is between 0 and 100\n(2) Exit\n";
    private static final String NOT_A_VALID_MENU_OPTION = "Not a valid menu option!\n";
    private static final String NUMBER_PROMPT = "Please enter a number:\n";
    private static final String NOT_A_NUMBER = "Not a number!\n";

    @Test
    public void promptsForInput() {
        Reader reader = new StringReader("1\n");
        Writer writer = new StringWriter();
        NumberValidator numberValidator = new NumberValidator(reader, writer);

        int userInput = numberValidator.promptUntilValidNumberEntered();

        assertThat(userInput, is(1));
    }

    @Test
    public void readsInputAsInteger() {
        Reader reader = new StringReader("1\n");
        Writer writer = new StringWriter();
        NumberValidator numberValidator = new NumberValidator(reader, writer);

        numberValidator.promptUntilValidNumberEntered();

        assertThat(writer.toString(), is(NUMBER_PROMPT));
    }

    @Test
    public void readsUntilNumericInputProvided() {
        Reader reader = new StringReader("A\n3\n");
        Writer writer = new StringWriter();
        NumberValidator numberValidator = new NumberValidator(reader, writer);

        int userInput = numberValidator.promptUntilValidNumberEntered();

        assertThat(userInput, is(3));
    }

    @Test
    public void readsUntilNumberInRangeIsProvided() {
        Reader reader = new StringReader("180\n3\n");
        Writer writer = new StringWriter();
        NumberValidator numberValidator = new NumberValidator(reader, writer);

        int userInput = numberValidator.promptUntilValidNumberEntered();

        assertThat(userInput, is(3));
    }


    @Test
    public void displaysErrorMessageBeforeReprompt() {
        Reader reader = new StringReader("A\n3\n");
        Writer writer = new StringWriter();
        NumberValidator numberValidator = new NumberValidator(reader, writer);

        numberValidator.promptUntilValidNumberEntered();
        assertTrue(writer.toString().equals(NUMBER_PROMPT + NOT_A_NUMBER + NUMBER_PROMPT));
    }

    @Test
    public void promptsForMenuChoice() {
        Reader reader = new StringReader("1\n");
        Writer writer = new StringWriter();
        NumberValidator numberValidator = new NumberValidator(reader, writer);

        int userInput = numberValidator.promptUntilValidMenuChoice();

        assertThat(userInput, is(1));
    }

    @Test
    public void displaysErrorMessageForInvalidMenuChoice() {
        Reader reader = new StringReader("3\n1\n");
        Writer writer = new StringWriter();
        NumberValidator numberValidator = new NumberValidator(reader, writer);

        numberValidator.promptUntilValidMenuChoice();
        assertTrue(writer.toString().equals(MENU_PROMPT + NOT_A_VALID_MENU_OPTION + MENU_PROMPT));
    }

    @Test
    public void printsUserPromptsForValidInput() throws Exception {
        Reader reader = new StringReader("1\n1\n");
        Writer writer = new StringWriter();
        NumberValidator numberValidator = new NumberValidator(reader, writer);

        numberValidator.runApp(numberValidator);
        assertTrue(writer.toString().equals(MENU_PROMPT + NUMBER_PROMPT));
    }

    @Test(expected = RuntimeException.class)
    public void exceptionWhenWritingMenuPrompt() {
        Reader reader = new StringReader("1\n");
        Writer writer = new WriterThatThrowsException();
        NumberValidator numberValidator = new NumberValidator(reader, writer);

        numberValidator.promptUntilValidMenuChoice();
    }

    @Test(expected = RuntimeException.class)
    public void exceptionWhenWritingNumberPrompt() {
        Reader reader = new StringReader("1\n");
        Writer writer = new WriterThatThrowsException();
        NumberValidator numberValidator = new NumberValidator(reader, writer);

        numberValidator.promptUntilValidNumberEntered();
    }

    @Test(expected = RuntimeException.class)
    public void exceptionsWhenReadingInput() {
        Reader reader = new ReaderThatThrowsException();
        Writer writer = new StringWriter();
        NumberValidator numberValidator = new NumberValidator(reader, writer);

        numberValidator.promptUntilValidNumberEntered();
    }
}

class ReaderThatThrowsException extends Reader {
    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        throw new IOException("Error for testing");
    }

    @Override
    public void close() throws IOException {

    }
}

class WriterThatThrowsException extends Writer {
    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        throw new IOException("Error for testing");
    }

    @Override
    public void flush() throws IOException {

    }

    @Override
    public void close() throws IOException {

    }
}
