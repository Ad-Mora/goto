package CoreTests;

import Core.Main;
import Core.Strings;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/*
 * Created by Adrian Mora on 8/2/15.
 */

/*
 * Testing outline:
 *
 * ##################################################
 * General tests:
 *
 * - Check that the bookmark file path is of the form home directory/general project directory/
 * goto directory/bookmark file
 * - Check that the VALID_FLAGS constant does in fact contain all valid flags
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
 * is an unqualified URL, no other existing bookmarks
 * - First argument is the bookmark flag, second argument is not an alias or a flag, third argument is a qualified URL,
 * no other existing bookmarks
 * - First argument is the bookmark flag, second argument is not an alias or a flag, third argument is an unqualified
 * URL, file contains existing bookmarks
 * - First argument is the bookmark flag, second argument is not an alias or a flag, third argument is a qualified
 * URL, file contains existing bookmarks
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
 * - Consecutively bookmark two different alias URL paris
 * - Consecutively delete two different bookmarks
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
    // General tests

    @Test
    public void testMainBookmarkFilePathHasCorrectForm() {
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);
        File gotoDirectory = bookmarkFile.getParentFile();
        File projectDirectory = gotoDirectory.getParentFile();
        File homeDirectory = projectDirectory.getParentFile();

        assertTrue(bookmarkFile.exists());
        assertTrue(gotoDirectory.exists());
        assertTrue(projectDirectory.exists());
        assertTrue(homeDirectory.getAbsolutePath().equals(System.getProperty("user.home")));
    }

    @Test
    public void testMainValidFlagsConstantContainsAllFlags() {
        assertTrue(Main.VALID_FLAGS.contains(Main.BOOKMARK_FLAG));
        assertTrue(Main.VALID_FLAGS.contains(Main.DELETE_BOOKMARK_FLAG));
        assertTrue(Main.VALID_FLAGS.contains(Main.VIEW_BOOKMARKS_FLAG));
        assertTrue(Main.VALID_FLAGS.contains(Main.HELP_FLAG));
        assertTrue(Main.VALID_FLAGS.size() == 4);
    }

    @Test
    public void testMainGeneralProjectDirectoryDoesNotExist() throws IOException {
        String[] args = {};
        File generalProjectDirectory = new File(Main.GENERAL_PROJECT_DIR_PATH);
        File gotoDirectory = new File(Main.GOTO_DIR_PATH);
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);

        assertTrue(generalProjectDirectory.exists());
        assertTrue(gotoDirectory.exists());
        assertTrue(bookmarkFile.exists());
        FileUtils.deleteDirectory(generalProjectDirectory);

        assertTrue(!generalProjectDirectory.exists());
        assertTrue(!gotoDirectory.exists());
        assertTrue(!bookmarkFile.exists());
        Main.main(args);

        assertTrue(generalProjectDirectory.exists());
        assertTrue(gotoDirectory.exists());
        assertTrue(bookmarkFile.exists());
    }

    @Test
    public void testMainGeneralProjectDirectoryExistsGotoDirectoryDoesNotExist() throws IOException {
        String[] args = {};
        File generalProjectDirectory = new File(Main.GENERAL_PROJECT_DIR_PATH);
        File gotoDirectory = new File(Main.GOTO_DIR_PATH);
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);

        assertTrue(generalProjectDirectory.exists());
        assertTrue(gotoDirectory.exists());
        assertTrue(bookmarkFile.exists());
        FileUtils.deleteDirectory(gotoDirectory);

        assertTrue(generalProjectDirectory.exists());
        assertTrue(!gotoDirectory.exists());
        assertTrue(!bookmarkFile.exists());
        Main.main(args);

        assertTrue(generalProjectDirectory.exists());
        assertTrue(gotoDirectory.exists());
        assertTrue(bookmarkFile.exists());
    }

    @Test
    public void testMainGeneralProjectDirectoryExistsGotoDirectoryExistsFileDoesNotExist() {
        String[] args = {};
        File generalProjectDirectory = new File(Main.GENERAL_PROJECT_DIR_PATH);
        File gotoDirectory = new File(Main.GOTO_DIR_PATH);
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);

        assertTrue(generalProjectDirectory.exists());
        assertTrue(gotoDirectory.exists());
        assertTrue(bookmarkFile.exists());
        bookmarkFile.delete();

        assertTrue(generalProjectDirectory.exists());
        assertTrue(gotoDirectory.exists());
        assertTrue(!bookmarkFile.exists());
        Main.main(args);

        assertTrue(generalProjectDirectory.exists());
        assertTrue(gotoDirectory.exists());
        assertTrue(bookmarkFile.exists());
    }

    @Test
    public void testMainFileExistsInvalidlyFormattedData() throws IOException {
        String[] args = {};
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);

        String entry1 = "alias1 http://www.google.com thirdArg";
        String entry2 = "alias2 http://www.facebook." + Strings.NEWLINE + "com";
        String entry3 = "alias3 youtube";
        String entry4 = "alias4 http://www.apple.com";

        FileUtils.writeStringToFile(bookmarkFile, entry1 + System.lineSeparator(), true);
        FileUtils.writeStringToFile(bookmarkFile, entry2 + System.lineSeparator(), true);
        FileUtils.writeStringToFile(bookmarkFile, entry3 + System.lineSeparator(), true);
        FileUtils.writeStringToFile(bookmarkFile, entry4 + System.lineSeparator(), true);

        Main.main(args);

        // Begin tests
        List<String> bookmarks = FileUtils.readLines(bookmarkFile);
        String bookmarksString = FileUtils.readFileToString(bookmarkFile);

        assertTrue(bookmarksString.endsWith(Strings.NEWLINE));
        assertTrue(bookmarks.contains("alias4 http://www.apple.com"));
        assertTrue(bookmarks.size() == 1);
    }

    @Test
    public void testMainFileExistsValidlyFormattedData() throws IOException {
        String[] args = {};
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);

        String entry1 = "alias1 http://www.google.com";
        String entry2 = "alias2 http://www.facebook.com";
        String entry3 = "alias3 http://www.youtube.com";

        FileUtils.writeStringToFile(bookmarkFile, entry1 + System.lineSeparator(), true);
        FileUtils.writeStringToFile(bookmarkFile, entry2 + System.lineSeparator(), true);
        FileUtils.writeStringToFile(bookmarkFile, entry3 + System.lineSeparator(), true);

        Main.main(args);

        // Begin tests
        List<String> bookmarks = FileUtils.readLines(bookmarkFile);
        String bookmarksString = FileUtils.readFileToString(bookmarkFile);

        assertTrue(bookmarksString.endsWith(Strings.NEWLINE));
        assertTrue(bookmarks.contains("alias1 http://www.google.com"));
        assertTrue(bookmarks.contains("alias2 http://www.facebook.com"));
        assertTrue(bookmarks.contains("alias3 http://www.youtube.com"));
        assertTrue(bookmarks.size() == 3);
    }

    // ####################################################################################################
    // main(String[] args) tests

    @Test
    public void testMainEmptyArgsArray() {
        String[] args = {};
        Main.main(args);

        // Begin tests
        String output = outContent.toString();
        assertTrue(output.equals(Strings.NEWLINE + Strings.INVALID_ARGS + Strings.DOUBLE_NEWLINE));
    }

    @Test
    public void testMainFirstArgumentNotAnAlias() {
        String[] args = {"google"};
        Main.main(args);

        // Begin tests
        String output = outContent.toString();

        assertTrue(output.equals(Strings.OPEN_BROWSER_AT + "http://www.google.com" + Strings.NEWLINE));
    }

    @Test
    public void testMainFirstArgumentIsAnAlias() throws IOException {
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

        assertTrue(output.equals(Strings.OPEN_BROWSER_AT + "http://www.facebook.com" + Strings.NEWLINE));
    }

    @Test
    public void testMainFirstArgumentIsUnqualifiedURL() {
        String[] args = {"google"};
        Main.main(args);

        // Begin tests
        String output = outContent.toString();

        assertTrue(output.equals(Strings.OPEN_BROWSER_AT + "http://www.google.com" + Strings.NEWLINE));
    }

    @Test
    public void testMainFirstArgumentIsQualifiedURL() {
        String[] args = {"http://www.apple.com"};
        Main.main(args);

        // Begin tests
        String output = outContent.toString();

        assertTrue(output.equals(Strings.OPEN_BROWSER_AT + "http://www.apple.com" + Strings.NEWLINE));
    }

    @Test
    public void testMainFirstArgumentIsNotAValidFlagSecondArgumentIsNotAValidFlag() {
        String[] args = {"alias1", "secondArg"};
        Main.main(args);

        // Begin tests
        String output = outContent.toString();

        assertTrue(output.equals(Strings.NEWLINE + Strings.INVALID_ARGS + Strings.DOUBLE_NEWLINE));
    }

    @Test
    public void testMainFirstArgumentIsNotAValidFlagSecondArgumentIsAValidFlag() {
        String[] args = {"alias1", Main.HELP_FLAG};
        Main.main(args);

        // Begin tests
        String output = outContent.toString();

        assertTrue(output.equals(Strings.NEWLINE + Strings.INVALID_ARGS + Strings.DOUBLE_NEWLINE));
    }

    @Test
    public void testMainFirstArgumentIsNotAValidFlagFollowedByAtLeastTwoArguments() {
        String[] args = {"alias1", "secondArg", "thirdArg"};
        Main.main(args);

        // Begin tests
        String output = outContent.toString();

        assertTrue(output.equals(Strings.NEWLINE + Strings.INVALID_ARGS + Strings.DOUBLE_NEWLINE));
    }

    @Test
    public void testMainFirstArgumentIsAValidAliasSecondArgumentIsNotAValidFlag() throws IOException {
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

        assertTrue(output.equals(Strings.NEWLINE + Strings.INVALID_ARGS + Strings.DOUBLE_NEWLINE));
    }

    @Test
    public void testMainFirstArgumentIsAValidAliasSecondArgumentIsAValidFlag() throws IOException {
        String[] args = {"alias1", Main.HELP_FLAG};
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

        assertTrue(output.equals(Strings.NEWLINE + Strings.INVALID_ARGS + Strings.DOUBLE_NEWLINE));
    }

    @Test
    public void testMainFirstArgumentContainsNewlineCharacter() {
        String[] args = {"ali" + Strings.NEWLINE + "as1"};
        Main.main(args);

        // Begin tests
        String output = outContent.toString();

        assertTrue(output.equals(Strings.NEWLINE + Strings.INVALID_ARGS + Strings.DOUBLE_NEWLINE));
    }

    @Test
    public void testMainFirstArgumentIsHelpFlagNoFollowingArguments() {
        String[] args = {Main.HELP_FLAG};
        Main.main(args);

        // Begin tests
        String output = outContent.toString();

        assertTrue(output.equals(Strings.NEWLINE + Strings.INFO + Strings.DOUBLE_NEWLINE));
    }

    @Test
    public void testMainFirstArgumentIsHelpFlagSecondArgumentIsValidFlag() {
        String[] args = {Main.HELP_FLAG, Main.BOOKMARK_FLAG};
        Main.main(args);

        // Begin tests
        String output = outContent.toString();

        assertTrue(output.equals(Strings.NEWLINE + Strings.INVALID_ARGS + Strings.DOUBLE_NEWLINE));
    }

    @Test
    public void testMainFirstArgumentIsHelpFlagSecondArgumentIsNotAValidFlag() {
        String[] args = {Main.HELP_FLAG, "secondArg"};
        Main.main(args);

        // Begin tests
        String output = outContent.toString();

        assertTrue(output.equals(Strings.NEWLINE + Strings.INVALID_ARGS + Strings.DOUBLE_NEWLINE));
    }

    @Test
    public void testMainFirstArgumentIsHelpFlagSecondArgumentContainsANewlineCharacter() {
        String[] args = {Main.HELP_FLAG, "second" + Strings.NEWLINE + "Arg"};
        Main.main(args);

        // Begin tests
        String output = outContent.toString();

        assertTrue(output.equals(Strings.NEWLINE + Strings.INVALID_ARGS + Strings.DOUBLE_NEWLINE));
    }

    @Test
    public void testMainFirstArgumentIsViewBookmarksFlagNoFollowingArgumentsEmptyFile() {
        String[] args = {Main.VIEW_BOOKMARKS_FLAG};
        Main.main(args);

        // Begin tests
        String output = outContent.toString();

        assertTrue(output.equals(Strings.NEWLINE + Strings.NO_BOOKMARKS_SAVED + Strings.DOUBLE_NEWLINE));
    }

    @Test
    public void testMainFirstArgumentIsViewBookmarksFlagNoFollowingArgumentsFileContainsBookmarks() throws IOException {
        String[] args = {Main.VIEW_BOOKMARKS_FLAG};
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
        BufferedReader reader = new BufferedReader(new StringReader(output));
        List<String> bookmarks = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            bookmarks.add(line);
        }
        reader.close();

        assertTrue(output.startsWith(Strings.NEWLINE));
        assertTrue(output.endsWith(Strings.DOUBLE_NEWLINE));
        assertTrue(bookmarks.contains("alias1 - http://www.google.com"));
        assertTrue(bookmarks.contains("alias2 - http://www.facebook.com"));
        assertTrue(bookmarks.contains("alias3 - http://www.youtube.com"));
        assertTrue(bookmarks.size() == 5);
    }

    @Test
    public void testMainFirstArgumentIsViewBookmarksFlagSecondArgumentIsAnotherValidFlag() {
        String[] args = {Main.VIEW_BOOKMARKS_FLAG, Main.HELP_FLAG};
        Main.main(args);

        // Begin tests
        String output = outContent.toString();

        assertTrue(output.equals(Strings.NEWLINE + Strings.INVALID_ARGS + Strings.DOUBLE_NEWLINE));
    }

    @Test
    public void testMainFirstArgumentIsViewBookmarksFlagSecondArgumentIsNotValidFlag() {
        String[] args = {Main.VIEW_BOOKMARKS_FLAG, "secondArg"};
        Main.main(args);

        // Begin tests
        String output = outContent.toString();

        assertTrue(output.equals(Strings.NEWLINE + Strings.INVALID_ARGS + Strings.DOUBLE_NEWLINE));
    }

    @Test
    public void testMainFirstArgumentIsViewBookmarksFlagSecondArgumentContainsNewlineCharacter() {
        String[] args = {Main.VIEW_BOOKMARKS_FLAG, "second" + Strings.NEWLINE + "Arg"};
        Main.main(args);

        // Begin tests
        String output = outContent.toString();

        assertTrue(output.equals(Strings.NEWLINE + Strings.INVALID_ARGS + Strings.DOUBLE_NEWLINE));
    }

    @Test
    public void testMainFirstArgumentIsDeleteBookmarkFlagNoFollowingArguments() {
        String[] args = {Main.DELETE_BOOKMARK_FLAG};
        Main.main(args);

        // Begin tests
        String output = outContent.toString();

        assertTrue(output.equals(Strings.NEWLINE + Strings.INVALID_ARGS + Strings.DOUBLE_NEWLINE));
    }

    @Test
    public void testMainFirstArgumentIsDeleteBookmarkFlagSecondArgumentIsNotAValidFlagOrExistingAlias() {
        String[] args = {Main.DELETE_BOOKMARK_FLAG, "secondArg"};
        Main.main(args);

        // Begin tests
        String output = outContent.toString();

        assertTrue(output.equals(Strings.NEWLINE + Strings.ALIAS_DOES_NOT_EXIST + Strings.DOUBLE_NEWLINE));
    }

    @Test
    public void testMainFirstArgumentIsDeleteBookmarkFlagSecondArgumentIsAValidFlag() {
        String[] args = {Main.DELETE_BOOKMARK_FLAG, Main.BOOKMARK_FLAG};
        Main.main(args);

        // Begin tests
        String output = outContent.toString();

        assertTrue(output.equals(Strings.NEWLINE + Strings.ALIAS_DOES_NOT_EXIST + Strings.DOUBLE_NEWLINE));
    }

    @Test
    public void testMainFirstArgumentIsDeleteBookmarkFlagSecondArgumentIsAnExistingAlias() throws IOException {
        String[] args = {Main.DELETE_BOOKMARK_FLAG, "alias3"};
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);

        String entry1 = "alias1 http://www.google.com";
        String entry2 = "alias2 http://www.facebook.com";
        String entry3 = "alias3 http://www.youtube.com";

        FileUtils.writeStringToFile(bookmarkFile, entry1 + System.lineSeparator(), true);
        FileUtils.writeStringToFile(bookmarkFile, entry2 + System.lineSeparator(), true);
        FileUtils.writeStringToFile(bookmarkFile, entry3 + System.lineSeparator(), true);

        Main.main(args);

        // Begin tests
        List<String> bookmarks = FileUtils.readLines(bookmarkFile);
        String bookmarksString = FileUtils.readFileToString(bookmarkFile);

        assertTrue(bookmarksString.endsWith(Strings.NEWLINE));
        assertTrue(bookmarks.contains("alias1 http://www.google.com"));
        assertTrue(bookmarks.contains("alias2 http://www.facebook.com"));
        assertTrue(bookmarks.size() == 2);
    }

    @Test
    public void testMainFirstArgumentIsDeleteBookmarkFlagSecondArgumentIsNotAnExistingAliasThirdArgumentIsPresent() {
        String[] args = {Main.DELETE_BOOKMARK_FLAG, "secondArg", "thirdArg"};
        Main.main(args);

        // Begin tests
        String output = outContent.toString();

        assertTrue(output.equals(Strings.NEWLINE + Strings.INVALID_ARGS + Strings.DOUBLE_NEWLINE));
    }

    @Test
    public void testMainFirstArgumentIsDeleteBookmarkFlagSecondArgumentIsAnExistingAliasThirdArgumentIsPresent() throws IOException {
        String[] args = {Main.DELETE_BOOKMARK_FLAG, "alias2", "thirdArg"};
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

        assertTrue(output.equals(Strings.NEWLINE + Strings.INVALID_ARGS + Strings.DOUBLE_NEWLINE));
    }

    @Test
    public void testMainFirstArgumentIsDeleteBookmarkFlagSecondArgumentContainsANewlineCharacter() {
        String[] args = {Main.DELETE_BOOKMARK_FLAG, "secondA" + Strings.NEWLINE + "rg"};
        Main.main(args);

        // Begin tests
        String output = outContent.toString();

        assertTrue(output.equals(Strings.NEWLINE + Strings.INVALID_ARGS + Strings.DOUBLE_NEWLINE));
    }

    @Test
    public void testMainFirstArgumentIsBookmarkFlagNoFollowingArguments() {
        String[] args = {Main.BOOKMARK_FLAG};
        Main.main(args);

        // Begin tests
        String output = outContent.toString();

        assertTrue(output.equals(Strings.NEWLINE + Strings.INVALID_ARGS + Strings.DOUBLE_NEWLINE));
    }

    @Test
    public void testMainFirstArgumentIsBookmarkFlagSecondArgumentIsNotValidAliasOrFlag() {
        String[] args = {Main.BOOKMARK_FLAG, "secondArg"};
        Main.main(args);

        // Begin tests
        String output = outContent.toString();

        assertTrue(output.equals(Strings.NEWLINE + Strings.INVALID_ARGS + Strings.DOUBLE_NEWLINE));
    }

    @Test
    public void testMainFirstArgumentIsBookmarkFlagSecondArgumentIsAValidFlag() {
        String[] args = {Main.BOOKMARK_FLAG, Main.DELETE_BOOKMARK_FLAG};
        Main.main(args);

        // Begin tests
        String output = outContent.toString();

        assertTrue(output.equals(Strings.NEWLINE + Strings.INVALID_ARGS + Strings.DOUBLE_NEWLINE));
    }

    @Test
    public void testMainFirstArgumentIsBookmarkFlagSecondArgumentIsAValidAlias() throws IOException {
        String[] args = {Main.BOOKMARK_FLAG, "alias1"};
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
        List<String> bookmarks = FileUtils.readLines(bookmarkFile);
        String bookmarksString = FileUtils.readFileToString(bookmarkFile);

        assertTrue(bookmarksString.endsWith(Strings.NEWLINE));
        assertTrue(output.equals(Strings.NEWLINE + Strings.INVALID_ARGS + Strings.DOUBLE_NEWLINE));
        assertTrue(bookmarks.contains("alias1 http://www.google.com"));
        assertTrue(bookmarks.contains("alias2 http://www.facebook.com"));
        assertTrue(bookmarks.contains("alias3 http://www.youtube.com"));
        assertTrue(bookmarks.size() == 3);
    }

    @Test
    public void testMainFirstArgumentIsBookmarkFlagSecondArgumentIsNotValidAliasOrFlagThirdArgumentIsUnqualifiedURLNoExistingBookmarks() throws IOException {
        String[] args = {Main.BOOKMARK_FLAG, "apl", "apple"};
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);
        Main.main(args);

        // Begin tests
        List<String> bookmarks = FileUtils.readLines(bookmarkFile);
        String bookmarksString = FileUtils.readFileToString(bookmarkFile);

        assertTrue(bookmarksString.endsWith(Strings.NEWLINE));
        assertTrue(bookmarks.contains("apl http://www.apple.com"));
        assertTrue(bookmarks.size() == 1);
    }

    @Test
    public void testMainFirstArgumentIsBookmarkFlagSecondArgumentIsNotValidAliasOrFlagThirdArgumentIsQualifiedURLNoExistingBookmarks() throws IOException {
        String[] args = {Main.BOOKMARK_FLAG, "apl", "http://www.apple.com"};
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);
        Main.main(args);

        // Begin tests
        List<String> bookmarks = FileUtils.readLines(bookmarkFile);
        String bookmarksString = FileUtils.readFileToString(bookmarkFile);

        assertTrue(bookmarksString.endsWith(Strings.NEWLINE));
        assertTrue(bookmarks.contains("apl http://www.apple.com"));
        assertTrue(bookmarks.size() == 1);
    }

    @Test
    public void testMainFirstArgumentIsBookmarkFlagSecondArgumentIsNotValidAliasOrFlagThirdArgumentIsUnqualifiedURLExistingBookmarks() throws IOException {
        String[] args = {Main.BOOKMARK_FLAG, "testAlias", "microsoft.com"};
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);

        String entry1 = "alias1 http://www.google.com";
        String entry2 = "alias2 http://www.facebook.com";
        String entry3 = "alias3 http://www.youtube.com";

        FileUtils.writeStringToFile(bookmarkFile, entry1 + System.lineSeparator(), true);
        FileUtils.writeStringToFile(bookmarkFile, entry2 + System.lineSeparator(), true);
        FileUtils.writeStringToFile(bookmarkFile, entry3 + System.lineSeparator(), true);

        Main.main(args);

        // Begin tests
        List<String> bookmarks = FileUtils.readLines(bookmarkFile);
        String bookmarksString = FileUtils.readFileToString(bookmarkFile);

        assertTrue(bookmarksString.endsWith(Strings.NEWLINE));
        assertTrue(bookmarks.contains("alias1 http://www.google.com"));
        assertTrue(bookmarks.contains("alias2 http://www.facebook.com"));
        assertTrue(bookmarks.contains("alias3 http://www.youtube.com"));
        assertTrue(bookmarks.contains("testAlias http://www.microsoft.com"));
        assertTrue(bookmarks.size() == 4);
    }

    @Test
    public void testMainFirstArgumentIsBookmarkFlagSecondArgumentIsNotValidAliasOrFlagThirdArgumentIsQualifiedURLExistingBookmarks() throws IOException {
        String[] args = {Main.BOOKMARK_FLAG, "testAlias", "https://www.microsoft.com"};
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);

        String entry1 = "alias1 http://www.google.com";
        String entry2 = "alias2 http://www.facebook.com";
        String entry3 = "alias3 http://www.youtube.com";

        FileUtils.writeStringToFile(bookmarkFile, entry1 + System.lineSeparator(), true);
        FileUtils.writeStringToFile(bookmarkFile, entry2 + System.lineSeparator(), true);
        FileUtils.writeStringToFile(bookmarkFile, entry3 + System.lineSeparator(), true);

        Main.main(args);

        // Begin tests
        List<String> bookmarks = FileUtils.readLines(bookmarkFile);
        String bookmarksString = FileUtils.readFileToString(bookmarkFile);

        assertTrue(bookmarksString.endsWith(Strings.NEWLINE));
        assertTrue(bookmarks.contains("alias1 http://www.google.com"));
        assertTrue(bookmarks.contains("alias2 http://www.facebook.com"));
        assertTrue(bookmarks.contains("alias3 http://www.youtube.com"));
        assertTrue(bookmarks.contains("testAlias https://www.microsoft.com"));
        assertTrue(bookmarks.size() == 4);
    }

    @Test
    public void testMainFirstArgumentIsBookmarkFlagSecondArgumentIsValidAliasThirdArgumentIsUnqualifiedURL() throws IOException {
        String[] args = {Main.BOOKMARK_FLAG, "alias1", "https://www.apple"};
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);

        String entry1 = "alias1 http://www.google.com";
        String entry2 = "alias2 http://www.facebook.com";
        String entry3 = "alias3 http://www.youtube.com";

        FileUtils.writeStringToFile(bookmarkFile, entry1 + System.lineSeparator(), true);
        FileUtils.writeStringToFile(bookmarkFile, entry2 + System.lineSeparator(), true);
        FileUtils.writeStringToFile(bookmarkFile, entry3 + System.lineSeparator(), true);

        Main.main(args);

        // Begin tests
        List<String> bookmarks = FileUtils.readLines(bookmarkFile);
        String bookmarksString = FileUtils.readFileToString(bookmarkFile);

        assertTrue(bookmarksString.endsWith(Strings.NEWLINE));
        assertTrue(bookmarks.contains("alias1 https://www.apple.com"));
        assertTrue(bookmarks.contains("alias2 http://www.facebook.com"));
        assertTrue(bookmarks.contains("alias3 http://www.youtube.com"));
        assertTrue(bookmarks.size() == 3);
    }

    @Test
    public void testMainFirstArgumentIsBookmarkFlagSecondArgumentIsValidAliasThirdArgumentIsQualifiedURL() throws IOException {
        String[] args = {Main.BOOKMARK_FLAG, "alias3", "http://www.stackoverflow.com"};
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);

        String entry1 = "alias1 http://www.google.com";
        String entry2 = "alias2 http://www.facebook.com";
        String entry3 = "alias3 http://www.youtube.com";

        FileUtils.writeStringToFile(bookmarkFile, entry1 + System.lineSeparator(), true);
        FileUtils.writeStringToFile(bookmarkFile, entry2 + System.lineSeparator(), true);
        FileUtils.writeStringToFile(bookmarkFile, entry3 + System.lineSeparator(), true);

        Main.main(args);

        // Begin tests
        List<String> bookmarks = FileUtils.readLines(bookmarkFile);
        String bookmarksString = FileUtils.readFileToString(bookmarkFile);

        assertTrue(bookmarksString.endsWith(Strings.NEWLINE));
        assertTrue(bookmarks.contains("alias1 http://www.google.com"));
        assertTrue(bookmarks.contains("alias2 http://www.facebook.com"));
        assertTrue(bookmarks.contains("alias3 http://www.stackoverflow.com"));
        assertTrue(bookmarks.size() == 3);
    }

    @Test
    public void testMainFirstArgumentIsBookmarkFlagSecondArgumentIsValidFlagThirdArgumentIsUnqualifiedURL() throws IOException {
        String[] args = {Main.BOOKMARK_FLAG, Main.HELP_FLAG, "google"};
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);
        Main.main(args);

        // Begin tests
        String output = outContent.toString();
        List<String> bookmarks = FileUtils.readLines(bookmarkFile);

        assertTrue(output.equals(Strings.NEWLINE + Strings.INVALID_ARGS + Strings.DOUBLE_NEWLINE));
        assertTrue(bookmarks.size() == 0);
    }

    @Test
    public void testMainFirstArgumentIsBookmarkFlagSecondArgumentIsValidFlagThirdArgumentIsQualifiedURL() throws IOException {
        String[] args = {Main.BOOKMARK_FLAG, Main.HELP_FLAG, "http://www.google.com"};
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);
        Main.main(args);

        // Begin tests
        String output = outContent.toString();
        List<String> bookmarks = FileUtils.readLines(bookmarkFile);

        assertTrue(output.equals(Strings.NEWLINE + Strings.INVALID_ARGS + Strings.DOUBLE_NEWLINE));
        assertTrue(bookmarks.size() == 0);
    }

    @Test
    public void testMainFirstArgumentIsBookmarkFlagSecondArgumentNotValidAliasOrFlagThirdAndFourthArgumentsPresent() throws IOException {
        String[] args = {Main.BOOKMARK_FLAG, "secondArg", "thirdArg", "fourthArg"};
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);
        Main.main(args);

        // Begin tests
        String output = outContent.toString();
        List<String> bookmarks = FileUtils.readLines(bookmarkFile);

        assertTrue(output.equals(Strings.NEWLINE + Strings.INVALID_ARGS + Strings.DOUBLE_NEWLINE));
        assertTrue(bookmarks.size() == 0);
    }

    @Test
    public void testMainFirstArgumentIsBookmarkFlagSecondArgumentIsValidAliasThirdAndFourthArgumentsPresent() throws IOException {
        String[] args = {Main.BOOKMARK_FLAG, "alias2", "thirdArg", "fourthArg"};
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
        List<String> bookmarks = FileUtils.readLines(bookmarkFile);
        String bookmarksString = FileUtils.readFileToString(bookmarkFile);

        assertTrue(bookmarksString.endsWith(Strings.NEWLINE));
        assertTrue(output.equals(Strings.NEWLINE + Strings.INVALID_ARGS + Strings.DOUBLE_NEWLINE));
        assertTrue(bookmarks.contains("alias1 http://www.google.com"));
        assertTrue(bookmarks.contains("alias2 http://www.facebook.com"));
        assertTrue(bookmarks.contains("alias3 http://www.youtube.com"));
        assertTrue(bookmarks.size() == 3);
    }

    @Test
    public void testMainFirstArgumentIsBookmarkFlagSecondArgumentIsValidFlagThirdAndFourthArgumentsPresent() {
        String[] args = {Main.BOOKMARK_FLAG, Main.VIEW_BOOKMARKS_FLAG, "thirdArg", "fourthArg"};
        Main.main(args);

        // Begin tests
        String output = outContent.toString();

        assertTrue(output.equals(Strings.NEWLINE + Strings.INVALID_ARGS + Strings.DOUBLE_NEWLINE));
    }

    @Test
    public void testMainFirstArgumentIsBookmarkFlagSecondArgumentContainsNewlineCharacter() {
        String[] args = {Main.BOOKMARK_FLAG, "ali" + Strings.NEWLINE + "as1"};
        Main.main(args);

        // Begin tests
        String output = outContent.toString();

        assertTrue(output.equals(Strings.NEWLINE + Strings.INVALID_ARGS + Strings.DOUBLE_NEWLINE));
    }

    @Test
    public void testMainFirstArgumentIsBookmarkFlagSecondArgumentPresentThirdArgumentContainsNewlineCharacter() throws IOException {
        String[] args = {Main.BOOKMARK_FLAG, "alias1", "goo" + Strings.NEWLINE + "gle"};
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);
        Main.main(args);

        // Begin tests
        String output = outContent.toString();
        List<String> bookmarks = FileUtils.readLines(bookmarkFile);

        assertTrue(output.equals(Strings.NEWLINE + Strings.INVALID_ARGS + Strings.DOUBLE_NEWLINE));
        assertTrue(bookmarks.size() == 0);
    }

    @Test
    public void testMainBookmarkTwoAliasURLPairsInARow() throws IOException {
        String[] args1 = {Main.BOOKMARK_FLAG, "alias1", "google"};
        String[] args2 = {Main.BOOKMARK_FLAG, "alias2", "facebook"};
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);

        Main.main(args1);
        Main.main(args2);

        // Begin tests
        List<String> bookmarks = FileUtils.readLines(bookmarkFile);
        String bookmarksString = FileUtils.readFileToString(bookmarkFile);

        assertTrue(bookmarksString.endsWith(Strings.NEWLINE));
        assertTrue(bookmarks.contains("alias1 http://www.google.com"));
        assertTrue(bookmarks.contains("alias2 http://www.facebook.com"));
        assertTrue(bookmarks.size() == 2);
    }

    @Test
    public void testMainDeleteTwoBookmarksInARow() throws IOException {
        String[] args1 = {Main.DELETE_BOOKMARK_FLAG, "alias2"};
        String[] args2 = {Main.DELETE_BOOKMARK_FLAG, "alias1"};
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);

        String entry1 = "alias1 http://www.google.com";
        String entry2 = "alias2 http://www.facebook.com";
        String entry3 = "alias3 http://www.youtube.com";

        FileUtils.writeStringToFile(bookmarkFile, entry1 + System.lineSeparator(), true);
        FileUtils.writeStringToFile(bookmarkFile, entry2 + System.lineSeparator(), true);
        FileUtils.writeStringToFile(bookmarkFile, entry3 + System.lineSeparator(), true);

        Main.main(args1);
        Main.main(args2);

        // Begin tests
        List<String> bookmarks = FileUtils.readLines(bookmarkFile);
        String bookmarksString = FileUtils.readFileToString(bookmarkFile);

        assertTrue(bookmarksString.endsWith(Strings.NEWLINE));
        assertTrue(bookmarks.contains("alias3 http://www.youtube.com"));
        assertTrue(bookmarks.size() == 1);
    }
}
