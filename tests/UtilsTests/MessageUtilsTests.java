package UtilsTests;

import Core.Strings;
import Utils.MessageUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

/*
 * Created by Adrian Mora on 8/27/15.
 */

/*
 * Testing outline:
 *
 * ##################################################
 * public static void surroundWithNewlinesAndPrint(String message)
 *
 * message:
 *
 * - Empty String
 * - Message is not empty
 * - Message contains a newline at the beginning of the message
 * - Message contains a newline somewhere in the middle of the message
 * - Message contains a newline at the end of the message
 */
public class MessageUtilsTests {

    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final PrintStream defaultOut = System.out;

    @BeforeClass
    public static void oneTimeSetup() {
        // Set the output stream
        System.setOut(new PrintStream(outContent));
    }

    @Before
    public void setUp() {
        // Clear the output stream
        outContent.reset();
    }

    @AfterClass
    public static void oneTimeTearDown() {
        // Reset output stream to the default
        System.setOut(defaultOut);
    }

    @Test
    public void testSurroundWithNewlinesAndPrintEmptyString() {
        String message = "";
        MessageUtils.surroundWithNewlinesAndPrint(message);

        // Begin tests
        String output = outContent.toString();
        assertTrue(output.equals(Strings.NEWLINE + Strings.DOUBLE_NEWLINE));
    }

    @Test
    public void testSurroundWithNewlinesAndPrintMessageIsNotEmpty() {
        String message = "This is some test output";
        MessageUtils.surroundWithNewlinesAndPrint(message);

        // Begin tests
        String output = outContent.toString();
        assertTrue(output.equals(Strings.NEWLINE + "This is some test output" + Strings.DOUBLE_NEWLINE));
    }

    @Test
    public void testSurroundWithNewlinesAndPrintNewlineAtBeginningOfMessage() {
        String message = Strings.NEWLINE + "This is more test output";
        MessageUtils.surroundWithNewlinesAndPrint(message);

        // Begin tests
        String output = outContent.toString();
        assertTrue(output.equals(Strings.DOUBLE_NEWLINE + "This is more test output" +  Strings.DOUBLE_NEWLINE));
    }

    @Test
    public void testSurroundWithNewlinesAndPrintNewlineInMiddleOfMessage() {
        String message = "This is more" + Strings.NEWLINE +  "test output";
        MessageUtils.surroundWithNewlinesAndPrint(message);

        // Begin tests
        String output = outContent.toString();
        assertTrue(output.equals(
                Strings.NEWLINE + "This is more" + Strings.NEWLINE +  "test output" +  Strings.DOUBLE_NEWLINE));
    }

    @Test
    public void testSurroundWithNewlinesAndPrintNewlineAtEndOfMessage() {
        String message = "This is more test output" + Strings.NEWLINE;
        MessageUtils.surroundWithNewlinesAndPrint(message);

        // Begin tests
        String output = outContent.toString();
        assertTrue(output.equals(
                Strings.NEWLINE + "This is more test output" + Strings.NEWLINE + Strings.DOUBLE_NEWLINE));
    }
}
