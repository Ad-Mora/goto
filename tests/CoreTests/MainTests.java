package CoreTests;

import Core.Main;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

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
 * - First argument is a non-flag argument, followed by another non-flag argument
 * - First argument is a non-flag argument, followed by a valid flag
 * - First argument contains a newline character
 *
 * - First argument is the view bookmarks flag, no following arguments
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
    public void testMainEmptyArgsArray() {
    }

    @Test
    public void testMainFirstArgumentNotAnAlias() {
    }

    @Test
    public void testMainFirstArgumentIsAnAlias() {
    }

    @Test
    public void testMainFirstArgumentIsUnqualifiedURL() {
    }

    @Test
    public void testMainFirstArgumentIsQualifiedURL() {
    }

    @Test
    public void testMainFirstArgumentIsNotAValidFlagSecondArgumentIsNotAValidFlag() {
    }

    @Test
    public void testMainFirstArgumentIsNotAValidFlagSecondArgumentIsAValidFlag() {
    }

    @Test
    public void testMainFirstArgumentContainsNewlineCharacter() {
    }

    @Test
    public void testMainFirstArgumentIsViewBookmarksFlagNoFollowingArguments() {
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
