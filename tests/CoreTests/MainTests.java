package CoreTests;

import Core.Bookmark;
import Core.Help;
import Core.Main;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AdrianM on 8/2/15.
 */

/*
 * Testing outline:
 *
 * - Bookmark file path is of the form home directory/general project directory/
 * goto directory/bookmark file
 * - General project directory does not exist
 * - General project directory exists, goto directory does not exist
 * - General project directory exists, goto directory exists, file does not exist
 * - File exists, invalidly formatted data
 * - File exists, validly formatted data
 *
 * ##################################################
 * public static void main(String[] args)
 *
 * args:
 *
 * - Empty array
 * - First argument is not an alias
 * - First argument is an alias
 * - First argument is not a qualified URL
 * - First argument is a qualified URL
 * - First argument is not a flag, followed by another non-flag argument
 * - First argument is not a flag, followed by a valid flag
 * - First argument is a valid alias, second argument is present but not a flag
 * - First argument is a valid alias, second argument is a valid flag
 * - First argument is not a flag, followed by two or more arguments
 * - First argument contains a newline character
 *
 * - First argument is the help flag, no following arguments
 * - First argument is the help flag, second argument is another valid flag
 * - First argument is the help flag, second argument is not a valid flag
 * - First argument is the help flag, second argument contains a newline character
 *
 * - First argument is the view bookmarks flag, no following arguments, empty file
 * - First argument is the view bookmarks flag, no following arguments, file contains bookmarks
 * - First argument is the view bookmarks flag, second argument is another valid flag
 * - First argument is the view bookmarks flag, second argument is not a valid flag
 * - First argument is the view bookmarks flag, second argument contains a newline character
 *
 * - First argument is the delete bookmark flag, no following arguments
 * - First argument is the delete bookmark flag, second argument is not a flag or an existing alias
 * - First argument is the delete bookmark flag, second argument is a valid flag
 * - First argument is the delete bookmark flag, second argument is an existing alias
 * - First argument is the delete bookmark flag, second argument is not an existing alias, third argument is present
 * - First argument is the delete bookmark flag, second argument is an existing alias, third argument is present
 * - First argument is the delete bookmark flag, second argument contains a newline character
 *
 * - First argument is the bookmark flag, no following arguments
 * - First argument is the bookmark flag, second argument is not an alias or a flag
 * - First argument is the bookmark flag, second argument is a valid flag
 * - First argument is the bookmark flag, second argument is a valid alias
 * - First argument is the bookmark flag, second argument is not an alias or a flag, third argument
 * is an unqualified URL
 * - First argument is the bookmark flag, second argument is not an alias or a flag, third argument is a qualified URL
 * - First argument is the bookmark flag, second argument is a valid alias, third argument is an unqualified URL
 * - First argument is the bookmark flag, second argument is a valid alias, third argument is a qualified URL
 * - First argument is the bookmark flag, second argument is a valid flag, third argument is an unqualified URL
 * - First argument is the bookmark flag, second argument is a valid flag, third argument is a qualified URL
 * - First argument is the bookmark flag, second argument is not an alias or a flag, third and fourth arguments are
 * present
 * - First argument is the bookmark flag, second argument is a valid alias, third and fourth arguments are present
 * - First argument is the bookmark flag, second argument is a valid flag, third and fourth arguments are present
 * - First argument is the bookmark flag, second argument contains a newline character
 * - First argument is the bookmark flag, second argument is present, third argument contains a newline character
 *
 * - Consecutively bookmark two different alias URL paris // TODO
 * - Consecutively delete two different bookmarks // TODO
 *
 */

public class MainTests {

    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final PrintStream defaultOut = System.out;

    @BeforeClass
    public static void oneTimeSetup() throws IOException {
        // Copy the contents of the general project directory to a temp directory so that
        // the contents can be recopied back in later
        File generalProjectDir = new File(Main.GENERAL_PROJECT_DIR_PATH);
        File generalProjectDirTemp = new File(Main.GENERAL_PROJECT_DIR_PATH_TEMP);

        if (generalProjectDir.exists()) {
            FileUtils.copyDirectory(generalProjectDir, generalProjectDirTemp);
        }

        // Set the output stream
        System.setOut(new PrintStream(outContent));

        // Set DEBUG to true
        Main.DEBUG = true;
    }

    @Before
    public void setUp() throws IOException {
        // The goto directory in the general project directory is first deleted, then recreated with an empty
        // bookmark file in the goto directory before each test is run
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);
        File gotoDir = bookmarkFile.getParentFile();

        FileUtils.deleteDirectory(gotoDir);
        gotoDir.mkdirs();
        bookmarkFile.createNewFile();

        // Clear the output stream
        outContent.reset();
    }

    @AfterClass
    public static void oneTimeTearDown() throws IOException {
        // Restore the contents of the general project directory to what they were
        // before the tests began
        File generalProjectDir = new File(Main.GENERAL_PROJECT_DIR_PATH);
        File generalProjectDirTemp = new File(Main.GENERAL_PROJECT_DIR_PATH_TEMP);

        if (generalProjectDirTemp.exists()) {
            FileUtils.copyDirectory(generalProjectDirTemp, generalProjectDir);
            FileUtils.deleteDirectory(generalProjectDirTemp);
        }

        // Reset output stream to the default
        System.setOut(defaultOut);

        // Reset DEBUG to false
        Main.DEBUG = false;
    }

    // ####################################################################################################
    // main(String[] args) tests

    @Test
    public void testMainEmptyArgsArray() throws IOException, URISyntaxException {
        String[] args = {};
        Main.main(args);

        // Begin tests
        String output = outContent.toString();
        assertTrue(output.equals(Help.getInvalidArgMessage() + "\n"));
    }

    @Test
    public void testMainFirstArgumentNotAnAlias() throws IOException, URISyntaxException {
        String[] args = {"google"};
        Main.main(args);

        // Begin tests
        String output = outContent.toString();

        assertTrue(output.equals(Main.OPEN_BROWSER_AT_MESSAGE + "http://www.google.com\n"));
    }

    @Test
    public void testMainFirstArgumentIsAnAlias() throws IOException, URISyntaxException {
        String[] args = {"alias2"};

        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);
        String entry1 = "alias1 http://www.google.com";
        String entry2 = "alias2 http://www.facebook.com";
        String entry3 = "alias3 http://www.youtube.com";

        FileUtils.writeStringToFile(bookmarkFile, entry1 + System.lineSeparator(), true);
        FileUtils.writeStringToFile(bookmarkFile, entry2 + System.lineSeparator(), true);
        FileUtils.writeStringToFile(bookmarkFile, entry3 + System.lineSeparator(), true);

        Main.main(args);

        // Begin tests
        String output = outContent.toString();

        assertTrue(output.equals(Main.OPEN_BROWSER_AT_MESSAGE + "http://www.facebook.com\n"));
    }

    @Test
    public void testMainFirstArgumentIsUnqualifiedURL() throws IOException, URISyntaxException {
        String[] args = {"google"};
        Main.main(args);

        // Begin tests
        String output = outContent.toString();

        assertTrue(output.equals(Main.OPEN_BROWSER_AT_MESSAGE + "http://www.google.com\n"));
    }

    @Test
    public void testMainFirstArgumentIsQualifiedURL() throws IOException, URISyntaxException {
        String[] args = {"http://www.apple.com"};
        Main.main(args);

        // Begin tests
        String output = outContent.toString();

        assertTrue(output.equals(Main.OPEN_BROWSER_AT_MESSAGE + "http://www.apple.com\n"));
    }

    @Test
    public void testMainFirstArgumentIsNotAValidFlagSecondArgumentIsNotAValidFlag() throws IOException, URISyntaxException {
        String[] args = {"alias1", "secondArg"};
        Main.main(args);

        // Begin tests
        String output = outContent.toString();

        assertTrue(output.equals(Help.getInvalidArgMessage() + "\n"));
    }

    @Test
    public void testMainFirstArgumentIsNotAValidFlagSecondArgumentIsAValidFlag() throws IOException, URISyntaxException {
        String[] args = {"alias1", "--help"};
        Main.main(args);

        // Begin tests
        String output = outContent.toString();

        assertTrue(output.equals(Help.getInvalidArgMessage() + "\n"));
    }

    @Test
    public void testMainFirstArgumentIsNotAValidFlagFollowedByAtLeastTwoArguments() throws IOException, URISyntaxException {
        String[] args = {"alias1", "secondArg", "thirdArg"};
        Main.main(args);

        // Begin tests
        String output = outContent.toString();

        assertTrue(output.equals(Help.getInvalidArgMessage() + "\n"));
    }

    @Test
    public void testMainFirstArgumentIsAValidAliasSecondArgumentIsNotAValidFlag() throws IOException, URISyntaxException {
        String[] args = {"alias3", "secondArg"};
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);

        String entry1 = "alias1 http://www.google.com";
        String entry2 = "alias2 http://www.facebook.com";
        String entry3 = "alias3 http://www.youtube.com";

        FileUtils.writeStringToFile(bookmarkFile, entry1 + System.lineSeparator(), true);
        FileUtils.writeStringToFile(bookmarkFile, entry2 + System.lineSeparator(), true);
        FileUtils.writeStringToFile(bookmarkFile, entry3 + System.lineSeparator(), true);

        Main.main(args);

        // Begin tests
        String output = outContent.toString();

        assertTrue(output.equals(Help.getInvalidArgMessage() + "\n"));
    }

    @Test
    public void testMainFirstArgumentIsAValidAliasSecondArgumentIsAValidFlag() throws IOException, URISyntaxException {
        String[] args = {"alias1", "--help"};
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);

        String entry1 = "alias1 http://www.google.com";
        String entry2 = "alias2 http://www.facebook.com";
        String entry3 = "alias3 http://www.youtube.com";

        FileUtils.writeStringToFile(bookmarkFile, entry1 + System.lineSeparator(), true);
        FileUtils.writeStringToFile(bookmarkFile, entry2 + System.lineSeparator(), true);
        FileUtils.writeStringToFile(bookmarkFile, entry3 + System.lineSeparator(), true);

        Main.main(args);

        // Begin tests
        String output = outContent.toString();

        assertTrue(output.equals(Help.getInvalidArgMessage() + "\n"));
    }

    @Test
    public void testMainFirstArgumentContainsNewlineCharacter() throws IOException, URISyntaxException {
        String[] args = {"ali\nas1"};
        Main.main(args);

        // Begin tests
        String output = outContent.toString();

        assertTrue(output.equals(Help.getInvalidArgMessage() + "\n"));
    }

    @Test
    public void testMainFirstArgumentIsHelpFlagNoFollowingArguments() throws IOException, URISyntaxException {
        String[] args = {"--help"};
        Main.main(args);

        // Begin tests
        String output = outContent.toString();

        assertTrue(output.equals(Help.getHelp() + "\n"));
    }

    @Test
    public void testMainFirstArgumentIsHelpFlagSecondArgumentIsValidFlag() throws IOException, URISyntaxException {
        String[] args = {"--help", "--bookmark"};
        Main.main(args);

        // Begin tests
        String output = outContent.toString();

        assertTrue(output.equals(Help.getInvalidArgMessage() + "\n"));
    }

    @Test
    public void testMainFirstArgumentIsHelpFlagSecondArgumentIsNotAValidFlag() throws IOException, URISyntaxException {
        String[] args = {"--help", "secondArg"};
        Main.main(args);

        // Begin tests
        String output = outContent.toString();

        assertTrue(output.equals(Help.getInvalidArgMessage() + "\n"));
    }

    @Test
    public void testMainFirstArgumentIsHelpFlagSecondArgumentContainsANewlineCharacter() throws IOException, URISyntaxException {
        String[] args = {"--help", "second\nArg"};
        Main.main(args);

        // Begin tests
        String output = outContent.toString();

        assertTrue(output.equals(Help.getInvalidArgMessage() + "\n"));
    }

    @Test
    public void testMainFirstArgumentIsViewBookmarksFlagNoFollowingArgumentsEmptyFile() throws IOException, URISyntaxException {
        String[] args = {Main.VIEW_BOOKMARKS_FLAG};
        Main.main(args);

        // Begin tests
        String output = outContent.toString();

        assertTrue(output.equals("\n"));
    }

    @Test
    public void testMainFirstArgumentIsViewBookmarksFlagSecondArgumentIsAnotherValidFlag() {
    }

    @Test
    public void testMainFirstArgumentIsViewBookmarksFlagSecondArgumentIsNotValidFlag() {
    }

    @Test
    public void testMainFirstArgumentIsViewBookmarksFlagSecondArgumentContainsNewlineCharacter() {
    }

    @Test
    public void testMainFirstArgumentIsDeleteBookmarkFlagNoFollowingArguments() {
    }

    @Test
    public void testMainFirstArgumentIsDeleteBookmarkFlagSecondArgumentIsNotAValidFlagOrExistingAlias() {
    }

    @Test
    public void testMainFirstArgumentIsDeleteBookmarkFlagSecondArgumentIsAValidFlag() {
    }

    @Test
    public void testMainFirstArgumentIsDeleteBookmarkFlagSecondArgumentIsAnExistingAlias() {
    }

    @Test
    public void testMainFirstArgumentIsDeleteBookmarkFlagSecondArgumentIsNotAnExistingAliasThirdArgumentIsPresent() {
    }

    @Test
    public void testMainFirstArgumentIsDeleteBookmarkFlagSecondArgumentIsAnExistingAliasThirdArgumentIsPresent() {
    }

    @Test
    public void testMainFirstArgumentIsDeleteBookmarkFlagSecondArgumentContainsANewlineCharacter() {
    }

    @Test
    public void testMainFirstArgumentIsBookmarkFlagNoFollowingArguments() {
    }

    @Test
    public void testMainFirstArgumentIsBookmarkFlagSecondArgumentIsNotValidAliasOrFlag() {
    }

    @Test
    public void testMainFirstArgumentIsBookmarkFlagSecondArgumentIsAValidFlag() {
    }

    @Test
    public void testMainFirstArgumentIsBookmarkFlagSecondArgumentIsAValidAlias() {
    }

    @Test
    public void testMainFirstArgumentIsBookmarkFlagSecondArgumentIsNotValidAliasOrFlagThirdArgumentIsUnqualifiedURL() {
    }

    @Test
    public void testMainFirstArgumentIsBookmarkFlagSecondArgumentIsNotValidAliasOrFlagThirdArgumentIsQualifiedURL() {
    }

    @Test
    public void testMainFirstArgumentIsBookmarkFlagSecondArgumentIsValidAliasThirdArgumentIsUnqualifiedURL() {
    }

    @Test
    public void testMainFirstArgumentIsBookmarkFlagSecondArgumentIsValidAliasThirdArgumentIsQualifiedURL() {
    }

    @Test
    public void testMainFirstArgumentIsBookmarkFlagSecondArgumentIsValidFlagThirdArgumentIsUnqualifiedURL() {
    }

    @Test
    public void testMainFirstArgumentIsBookmarkFlagSecondArgumentIsValidFlagThirdArgumentIsQualifiedURL() {
    }

    @Test
    public void testMainFirstArgumentIsBookmarkFlagSecondArgumentNotValidAliasOrFlagThirdAndFourthArgumentsPresent() {
    }

    @Test
    public void testMainFirstArgumentIsBookmarkFlagSecondArgumentIsValidAliasThirdAndFourthArgumentsPresent() {
    }

    @Test
    public void testMainFirstArgumentIsBookmarkFlagSecondArgumentIsValidFlagThirdAndFourthArgumentsPresent() {
    }

    @Test
    public void testMainFirstArgumentIsBookmarkFlagSecondArgumentContainsNewlineCharacter() {
    }

    @Test
    public void testMainFirstArgumentIsBookmarkFlagSecondArgumentPresentThirdArgumentContainsNewlineCharacter() {
    }
}



























