package CoreTests;


import Core.Bookmark;
import Core.Main;
import org.apache.commons.io.FileUtils;
import org.junit.*;

import java.io.*;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by AdrianM on 7/19/15.
 */

/*
 * Testing outline:
 *
 * ##################################################
 * public static void bookmark(File bookmarkFile, String alias, String url)
 *
 * bookmarkFile:
 *
 * - File is empty
 * - File contains some existing bookmarks
 *
 * alias:
 *
 * - Alias does not exist in file
 * - Alias exists in file
 *
 * url:
 *
 * - URL does not exist in file
 * - URL exists in file
 *
 * ##################################################
 * public static void deleteBookmark(File bookmarkFile, String alias)
 *
 * bookmarkFile:
 *
 * - File is empty
 * - File contains existing bookmarks
 *
 * alias:
 *
 * - Alias does not exist in file
 * - Alias exists in file
 *
 * ##################################################
 * public static void viewBookmarks(File bookmarkFile)
 *
 * bookmarkFile:
 *
 * - File is empty
 * - File contains existing bookmarks
 *
 * ##################################################
 * public static void createBookmarkFile(File bookmarkFile)
 *
 * - General project directory does not exist
 * - General project directory exists, goto directory does not exist
 * - General project directory, goto directory exists, file does not exist
 * - File exists, empty file
 * - File exists, existing bookmarks
 *
 * ##################################################
 * public static void cleanBookmarkFile(File bookmarkFile)
 *
 * bookmarkFile:
 *
 * - File is empty
 * - File has validly formatted data
 * - File has only invalidly formatted data
 * - File has valid and invalid data
 * - Wrong number of items on a line
 * - One of the URLs is not fully qualified
 * - File contains duplicate aliases, both valid bookmarks
 * - File contains duplicate aliases, one bookmark valid, one invalid
 * - File contains duplicate aliases, both bookmarks invalid
 * - Leading and trailing spaces around an entry
 * - Extra spaces between alias and URL
 * - Leading and trailing spaces, and spaces in between alias and URL
 * - Single blank line between two entries
 * - Multiple blank lines between two entries
 * - Leading blank lines in a file
 * - Trailing blank lines in a file
 * - Leading and trailing blank lines in file
 * - Multiple types of invalid entries in file
 *
 * ##################################################
 * public static Map<String, String> getBookmarkFileData(File bookmarkFile)
 *
 * bookmarkFile:
 *
 * - File is empty
 * - File contains existing bookmarks
 *
 * ##################################################
 * public static void updateBookmarkFile(File bookmarkFile, Map<String, String> aliasesToURLs)
 *
 * - Update old alias with new value
 * - Delete old alias
 *
 * bookmarkFile:
 *
 * - File is empty
 * - File contains existing bookmarks
 * - File contains invalidly formatted data
 *
 * aliasesToURLs:
 *
 * - Empty dictionary
 * - One item in dictionary
 * - Multiple items in dictionary
 * - Duplicate URLs corresponding to different keys
 *
 * ##################################################
 * public static String getURLFromAlias(File bookmarkFile, String alias)
 *
 * bookmarkFile:
 *
 * - File is empty
 * - File contains existing bookmarks
 *
 * alias:
 *
 * - Alias does not exist
 * - Alias exists
 *
 * ##################################################
 * public static getLineEntry(String alias, String url)
 *
 * - Returns properly formatted entry
 *
 */
public class BookmarkTests {

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
    }

    // ####################################################################################################
    // bookmark(File bookmarkFile, String alias, String url) tests

    @Test
    public void testBookmarkEmptyFile() throws IOException {
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);
        String newAlias = "newAlias";
        String newUrl = "http://www.google.com";
        Bookmark.bookmark(bookmarkFile, newAlias, newUrl);

        // Begin tests
        List<String> bookmarks = FileUtils.readLines(bookmarkFile);

        assertTrue(bookmarks.contains("newAlias http://www.google.com"));
        assertTrue(bookmarks.size() == 1);
    }

    @Test
    public void testBookmarkExistingBookmarks() throws IOException {
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);
        String alias1 = "alias1";
        String url1 = "http://www.google.com";
        String alias2 = "alias2";
        String url2 = "http://www.facebook.com";
        String alias3 = "alias3";
        String url3 = "http://www.youtube.com";

        FileUtils.writeStringToFile(bookmarkFile, Bookmark.getLineEntry(alias1, url1), true);
        FileUtils.writeStringToFile(bookmarkFile, Bookmark.getLineEntry(alias2, url2), true);
        FileUtils.writeStringToFile(bookmarkFile, Bookmark.getLineEntry(alias3, url3), true);

        String newAlias = "newAlias";
        String newUrl = "http://www.stackoverflow.com";
        Bookmark.bookmark(bookmarkFile, newAlias, newUrl);

        // Begin tests
        List<String> bookmarks = FileUtils.readLines(bookmarkFile);

        assertTrue(bookmarks.contains("alias1 http://www.google.com"));
        assertTrue(bookmarks.contains("alias2 http://www.facebook.com"));
        assertTrue(bookmarks.contains("alias3 http://www.youtube.com"));
        assertTrue(bookmarks.contains("newAlias http://www.stackoverflow.com"));
        assertTrue(bookmarks.size() == 4);
    }

    @Test
    public void testBookmarkAliasDoesNotExist() throws IOException {
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);
        String alias1 = "alias1";
        String url1 = "http://www.google.com";
        String alias2 = "alias2";
        String url2 = "http://www.facebook.com";
        String alias3 = "alias3";
        String url3 = "http://www.youtube.com";

        FileUtils.writeStringToFile(bookmarkFile, Bookmark.getLineEntry(alias1, url1), true);
        FileUtils.writeStringToFile(bookmarkFile, Bookmark.getLineEntry(alias2, url2), true);
        FileUtils.writeStringToFile(bookmarkFile, Bookmark.getLineEntry(alias3, url3), true);

        String newAlias = "newAlias";
        String newUrl = "http://www.stackoverflow.com";
        Bookmark.bookmark(bookmarkFile, newAlias, newUrl);

        // Begin tests
        List<String> bookmarks = FileUtils.readLines(bookmarkFile);

        assertTrue(bookmarks.contains("alias1 http://www.google.com"));
        assertTrue(bookmarks.contains("alias2 http://www.facebook.com"));
        assertTrue(bookmarks.contains("alias3 http://www.youtube.com"));
        assertTrue(bookmarks.contains("newAlias http://www.stackoverflow.com"));
        assertTrue(bookmarks.size() == 4);
    }

    @Test
    public void testBookmarkAliasExists() throws IOException {
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);
        String alias1 = "alias1";
        String url1 = "http://www.google.com";
        String alias2 = "alias2";
        String url2 = "http://www.facebook.com";
        String alias3 = "alias3";
        String url3 = "http://www.youtube.com";

        FileUtils.writeStringToFile(bookmarkFile, Bookmark.getLineEntry(alias1, url1), true);
        FileUtils.writeStringToFile(bookmarkFile, Bookmark.getLineEntry(alias2, url2), true);
        FileUtils.writeStringToFile(bookmarkFile, Bookmark.getLineEntry(alias3, url3), true);

        String newAlias = "alias2";
        String newUrl = "http://www.stackoverflow.com";
        Bookmark.bookmark(bookmarkFile, newAlias, newUrl);

        // Begin tests
        List<String> bookmarks = FileUtils.readLines(bookmarkFile);

        assertTrue(bookmarks.contains("alias1 http://www.google.com"));
        assertTrue(bookmarks.contains("alias2 http://www.stackoverflow.com"));
        assertTrue(bookmarks.contains("alias3 http://www.youtube.com"));
        assertTrue(!bookmarks.contains("alias2 http://www.facebook.com"));
        assertTrue(bookmarks.size() == 3);
    }

    @Test
    public void testBookmarkURLDoesNotExist() throws IOException {
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);
        String alias1 = "alias1";
        String url1 = "http://www.google.com";
        String alias2 = "alias2";
        String url2 = "http://www.facebook.com";
        String alias3 = "alias3";
        String url3 = "http://www.youtube.com";

        FileUtils.writeStringToFile(bookmarkFile, Bookmark.getLineEntry(alias1, url1), true);
        FileUtils.writeStringToFile(bookmarkFile, Bookmark.getLineEntry(alias2, url2), true);
        FileUtils.writeStringToFile(bookmarkFile, Bookmark.getLineEntry(alias3, url3), true);

        String newAlias = "newAlias";
        String newUrl = "http://www.stackoverflow.com";
        Bookmark.bookmark(bookmarkFile, newAlias, newUrl);

        // Begin tests
        List<String> bookmarks = FileUtils.readLines(bookmarkFile);

        assertTrue(bookmarks.contains("alias1 http://www.google.com"));
        assertTrue(bookmarks.contains("alias2 http://www.facebook.com"));
        assertTrue(bookmarks.contains("alias3 http://www.youtube.com"));
        assertTrue(bookmarks.contains("newAlias http://www.stackoverflow.com"));
        assertTrue(bookmarks.size() == 4);
    }

    @Test
    public void testBookmarkURLExists() throws IOException {
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);
        String alias1 = "alias1";
        String url1 = "http://www.google.com";
        String alias2 = "alias2";
        String url2 = "http://www.facebook.com";
        String alias3 = "alias3";
        String url3 = "http://www.youtube.com";

        FileUtils.writeStringToFile(bookmarkFile, Bookmark.getLineEntry(alias1, url1), true);
        FileUtils.writeStringToFile(bookmarkFile, Bookmark.getLineEntry(alias2, url2), true);
        FileUtils.writeStringToFile(bookmarkFile, Bookmark.getLineEntry(alias3, url3), true);

        String newAlias = "newAlias";
        String newUrl = "http://www.google.com";
        Bookmark.bookmark(bookmarkFile, newAlias, newUrl);

        // Begin tests
        List<String> bookmarks = FileUtils.readLines(bookmarkFile);

        assertTrue(bookmarks.contains("alias1 http://www.google.com"));
        assertTrue(bookmarks.contains("alias2 http://www.facebook.com"));
        assertTrue(bookmarks.contains("alias3 http://www.youtube.com"));
        assertTrue(bookmarks.contains("newAlias http://www.google.com"));
        assertTrue(bookmarks.size() == 4);
    }

    // ####################################################################################################
    // public static void deleteBookmark(File bookmarkFile, String alias) tests

    @Test
    public void testDeleteBookmarkEmptyFile() throws IOException {
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);
        String aliasToDelete = "aliasToDelete";
        Bookmark.deleteBookmark(bookmarkFile, aliasToDelete);

        // Begin tests
        List<String> bookmarks = FileUtils.readLines(bookmarkFile);

        assertTrue(bookmarks.size() == 0);
    }

    @Test
    public void testDeleteBookmarkExistingBookmarks() throws IOException {
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);
        String alias1 = "alias1";
        String url1 = "http://www.google.com";
        String alias2 = "alias2";
        String url2 = "http://www.facebook.com";
        String alias3 = "alias3";
        String url3 = "http://www.youtube.com";

        FileUtils.writeStringToFile(bookmarkFile, Bookmark.getLineEntry(alias1, url1), true);
        FileUtils.writeStringToFile(bookmarkFile, Bookmark.getLineEntry(alias2, url2), true);
        FileUtils.writeStringToFile(bookmarkFile, Bookmark.getLineEntry(alias3, url3), true);

        String aliasToDelete = "aliasToDelete";
        Bookmark.deleteBookmark(bookmarkFile, aliasToDelete);

        // Begin tests
        List<String> bookmarks = FileUtils.readLines(bookmarkFile);

        assertTrue(bookmarks.contains("alias1 http://www.google.com"));
        assertTrue(bookmarks.contains("alias2 http://www.facebook.com"));
        assertTrue(bookmarks.contains("alias3 http://www.youtube.com"));
        assertTrue(bookmarks.size() == 3);
    }

    @Test
    public void testDeleteBookmarkAliasDoesNotExist() throws IOException {
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);
        String alias1 = "alias1";
        String url1 = "http://www.google.com";
        String alias2 = "alias2";
        String url2 = "http://www.facebook.com";
        String alias3 = "alias3";
        String url3 = "http://www.youtube.com";

        FileUtils.writeStringToFile(bookmarkFile, Bookmark.getLineEntry(alias1, url1), true);
        FileUtils.writeStringToFile(bookmarkFile, Bookmark.getLineEntry(alias2, url2), true);
        FileUtils.writeStringToFile(bookmarkFile, Bookmark.getLineEntry(alias3, url3), true);

        String aliasToDelete = "aliasToDelete";
        Bookmark.deleteBookmark(bookmarkFile, aliasToDelete);

        // Begin tests
        List<String> bookmarks = FileUtils.readLines(bookmarkFile);

        assertTrue(bookmarks.contains("alias1 http://www.google.com"));
        assertTrue(bookmarks.contains("alias2 http://www.facebook.com"));
        assertTrue(bookmarks.contains("alias3 http://www.youtube.com"));
        assertTrue(bookmarks.size() == 3);
    }

    @Test
    public void testDeleteBookmarkAliasExists() throws IOException {
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);
        String alias1 = "alias1";
        String url1 = "http://www.google.com";
        String alias2 = "alias2";
        String url2 = "http://www.facebook.com";
        String alias3 = "alias3";
        String url3 = "http://www.youtube.com";

        FileUtils.writeStringToFile(bookmarkFile, Bookmark.getLineEntry(alias1, url1), true);
        FileUtils.writeStringToFile(bookmarkFile, Bookmark.getLineEntry(alias2, url2), true);
        FileUtils.writeStringToFile(bookmarkFile, Bookmark.getLineEntry(alias3, url3), true);

        String aliasToDelete = "alias2";
        Bookmark.deleteBookmark(bookmarkFile, aliasToDelete);

        // Begin tests
        List<String> bookmarks = FileUtils.readLines(bookmarkFile);

        assertTrue(bookmarks.contains("alias1 http://www.google.com"));
        assertTrue(!bookmarks.contains("alias2 http://www.facebook.com"));
        assertTrue(bookmarks.contains("alias3 http://www.youtube.com"));
        assertTrue(bookmarks.size() == 2);
    }

    // ####################################################################################################
    // public static void viewBookmarks(File bookmarkFile) tests

    @Test
    public void testViewBookmarksEmptyFile() throws IOException {
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);
        Bookmark.viewBookmarks(bookmarkFile);

        assertTrue(outContent.toString().equals(""));
    }

    @Test
    public void testViewBookmarksExistingBookmarks() throws IOException {
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);
        String alias1 = "alias1";
        String url1 = "http://www.google.com";
        String alias2 = "alias2";
        String url2 = "http://www.facebook.com";
        String alias3 = "alias3";
        String url3 = "http://www.youtube.com";

        FileUtils.writeStringToFile(bookmarkFile, Bookmark.getLineEntry(alias1, url1), true);
        FileUtils.writeStringToFile(bookmarkFile, Bookmark.getLineEntry(alias2, url2), true);
        FileUtils.writeStringToFile(bookmarkFile, Bookmark.getLineEntry(alias3, url3), true);

        Bookmark.viewBookmarks(bookmarkFile);

        // Begin tests
        String output = outContent.toString();
        BufferedReader reader = new BufferedReader(new StringReader(output));
        List<String> bookmarks = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            bookmarks.add(line);
        }
        reader.close();

        assertTrue(bookmarks.contains("alias1 - http://www.google.com"));
        assertTrue(bookmarks.contains("alias2 - http://www.facebook.com"));
        assertTrue(bookmarks.contains("alias3 - http://www.youtube.com"));
        assertTrue(bookmarks.size() == 3);
    }

    // ####################################################################################################
    // public static void createBookmarkFile(File bookmarkFile) tests

    @Test
    public void testCreateBookmarkFileGeneralProjectDirDoesNotExist() throws IOException {
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
        Bookmark.createBookmarkFile(bookmarkFile);

        assertTrue(generalProjectDirectory.exists());
        assertTrue(gotoDirectory.exists());
        assertTrue(bookmarkFile.exists());
    }

    @Test
    public void testCreateBookmarkFileGotoDirDoesNotExist() throws IOException {
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
        Bookmark.createBookmarkFile(bookmarkFile);

        assertTrue(generalProjectDirectory.exists());
        assertTrue(gotoDirectory.exists());
        assertTrue(bookmarkFile.exists());
    }

    @Test
    public void testCreateBookmarkFileFileDoesNotExist() {
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
        Bookmark.createBookmarkFile(bookmarkFile);

        assertTrue(generalProjectDirectory.exists());
        assertTrue(gotoDirectory.exists());
        assertTrue(bookmarkFile.exists());
    }

    @Test
    public void testCreateBookmarkFileEmptyFile() {
        File generalProjectDirectory = new File(Main.GENERAL_PROJECT_DIR_PATH);
        File gotoDirectory = new File(Main.GOTO_DIR_PATH);
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);

        assertTrue(generalProjectDirectory.exists());
        assertTrue(gotoDirectory.exists());
        assertTrue(bookmarkFile.exists());

        Bookmark.createBookmarkFile(bookmarkFile);

        assertTrue(generalProjectDirectory.exists());
        assertTrue(gotoDirectory.exists());
        assertTrue(bookmarkFile.exists());
    }

    @Test
    public void testCreateBookmarkFileExistingBookmarks() throws IOException {
        File generalProjectDirectory = new File(Main.GENERAL_PROJECT_DIR_PATH);
        File gotoDirectory = new File(Main.GOTO_DIR_PATH);
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);

        assertTrue(generalProjectDirectory.exists());
        assertTrue(gotoDirectory.exists());
        assertTrue(bookmarkFile.exists());

        String alias1 = "alias1";
        String url1 = "http://www.google.com";
        String alias2 = "alias2";
        String url2 = "http://www.facebook.com";
        String alias3 = "alias3";
        String url3 = "http://www.youtube.com";

        FileUtils.writeStringToFile(bookmarkFile, Bookmark.getLineEntry(alias1, url1), true);
        FileUtils.writeStringToFile(bookmarkFile, Bookmark.getLineEntry(alias2, url2), true);
        FileUtils.writeStringToFile(bookmarkFile, Bookmark.getLineEntry(alias3, url3), true);

        Bookmark.createBookmarkFile(bookmarkFile);
        List<String> bookmarks = FileUtils.readLines(bookmarkFile);

        assertTrue(generalProjectDirectory.exists());
        assertTrue(gotoDirectory.exists());
        assertTrue(bookmarkFile.exists());
        assertTrue(bookmarks.contains("alias1 http://www.google.com"));
        assertTrue(bookmarks.contains("alias2 http://www.facebook.com"));
        assertTrue(bookmarks.contains("alias3 http://www.youtube.com"));
        assertTrue(bookmarks.size() == 3);
    }

    // ####################################################################################################
    // public static void cleanBookmarkFile(File bookmarkFile) tests

    @Test
    public void testCleanBookmarkFileEmptyFile() throws IOException {
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);
        Bookmark.cleanBookmarkFile(bookmarkFile);

        // Begin tests
        List<String> bookmarks = FileUtils.readLines(bookmarkFile);

        assertTrue(bookmarks.size() == 0);
    }

    @Test
    public void testCleanBookmarkFileOnlyValidData() throws IOException {
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);
        String alias1 = "alias1";
        String url1 = "http://www.google.com";
        String alias2 = "alias2";
        String url2 = "http://www.facebook.com";
        String alias3 = "alias3";
        String url3 = "http://www.youtube.com";

        FileUtils.writeStringToFile(bookmarkFile, Bookmark.getLineEntry(alias1, url1), true);
        FileUtils.writeStringToFile(bookmarkFile, Bookmark.getLineEntry(alias2, url2), true);
        FileUtils.writeStringToFile(bookmarkFile, Bookmark.getLineEntry(alias3, url3), true);

        Bookmark.cleanBookmarkFile(bookmarkFile);

        // Begin tests

        List<String> bookmarks = FileUtils.readLines(bookmarkFile);

        assertTrue(bookmarks.contains("alias1 http://www.google.com"));
        assertTrue(bookmarks.contains("alias2 http://www.facebook.com"));
        assertTrue(bookmarks.contains("alias3 http://www.youtube.com"));
        assertTrue(bookmarks.size() == 3);
    }

    @Test
    public void testCleanBookmarkFileOnlyInvalidData() throws IOException {
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);

        String entry1 = "alias1 google";
        String entry2 = "\n\n\ntestLine\n\n";
        String entry3 = "   alias2 http://www.facebook.com    ";
        String entry4 = "d";

        FileUtils.writeStringToFile(bookmarkFile, entry1, true);
        FileUtils.writeStringToFile(bookmarkFile, entry2, true);
        FileUtils.writeStringToFile(bookmarkFile, entry3, true);
        FileUtils.writeStringToFile(bookmarkFile, entry4, true);

        Bookmark.cleanBookmarkFile(bookmarkFile);

        // Begin tests

        List<String> bookmarks = FileUtils.readLines(bookmarkFile);
        assertTrue(bookmarks.size() == 0);
    }

    @Test
    public void testCleanBookmarkFileValidAndInvalidData() throws IOException {
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);
        String entry1 = "alias1 google";
        String entry2 = "alias2 http://www.facebook.com";
        String entry3 = "     alias3 http://www.youtube.com";
        String entry4 = "";

        FileUtils.writeStringToFile(bookmarkFile, entry1, true);
        FileUtils.writeStringToFile(bookmarkFile, entry2, true);
        FileUtils.writeStringToFile(bookmarkFile, entry3, true);
        FileUtils.writeStringToFile(bookmarkFile, entry4, true);

        Bookmark.cleanBookmarkFile(bookmarkFile);

        // Begin tests

        List<String> bookmarks = FileUtils.readLines(bookmarkFile);

        assertTrue(bookmarks.contains("alias2 http://www.facebook.com"));
        assertTrue(bookmarks.size() == 1);
    }

    @Test
    public void testCleanBookmarkFileWrongNumberOfItemsOnLine() throws IOException {
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);
        String entry1 = "a";
        String entry2 = "alias1 http://www.google.com extraArg";
        String entry3 = "alias2 extraArg http://www.facebook.com";
        String entry4 = "arg1 arg2 alias3 http://www.youtube.com arg3";
        String entry5 = "alias3 http://www.stackoverflow.com";

        FileUtils.writeStringToFile(bookmarkFile, entry1, true);
        FileUtils.writeStringToFile(bookmarkFile, entry2, true);
        FileUtils.writeStringToFile(bookmarkFile, entry3, true);
        FileUtils.writeStringToFile(bookmarkFile, entry4, true);
        FileUtils.writeStringToFile(bookmarkFile, entry5, true);

        Bookmark.cleanBookmarkFile(bookmarkFile);

        // Begin tests

        List<String> bookmarks = FileUtils.readLines(bookmarkFile);

        assertTrue(bookmarks.contains("alias3 http://www.stackoverflow.com"));
        assertTrue(bookmarks.size() == 1);
    }

    @Test
    public void testCleanBookmarkFileUnqualifiedURL() throws IOException {
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);
        String entry1 = "alias1 http://www.google.com";
        String entry2 = "alias2 facebook";
        String entry3 = "alias3 youtube";

        FileUtils.writeStringToFile(bookmarkFile, entry1, true);
        FileUtils.writeStringToFile(bookmarkFile, entry2, true);
        FileUtils.writeStringToFile(bookmarkFile, entry3, true);

        Bookmark.cleanBookmarkFile(bookmarkFile);

        // Begin tests

        List<String> bookmarks = FileUtils.readLines(bookmarkFile);

        assertTrue(bookmarks.contains("alias1 http://www.google.com"));
        assertTrue(bookmarks.size() == 1);
    }

    @Test
    public void testCleanBookmarkFileDuplicateAliasesBothValid() throws IOException {
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);
        String entry1 = "alias1 http://www.google.com";
        String entry2 = "alias1 http://www.youtube.com";
        String entry3 = "alias2 http://www.facebook.com";

        FileUtils.writeStringToFile(bookmarkFile, entry1, true);
        FileUtils.writeStringToFile(bookmarkFile, entry2, true);
        FileUtils.writeStringToFile(bookmarkFile, entry3, true);

        Bookmark.cleanBookmarkFile(bookmarkFile);

        // Begin tests

        List<String> bookmarks = FileUtils.readLines(bookmarkFile);

        assertTrue(bookmarks.contains("alias1 http://www.google.com")
                || bookmarks.contains("alias1 http://www.youtube.com"));
        assertTrue(bookmarks.contains("alias2 http://www.facebook.com"));
        assertTrue(bookmarks.size() == 2);
    }

    @Test
    public void testCleanBookmarkFileDuplicateAliasesOneValidOneInvalid() throws IOException {
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);
        String entry1 = "alias1 http://www.google.com";
        String entry2 = "alias1 youtube    ";
        String entry3 = "alias2 http://www.facebook.com";

        FileUtils.writeStringToFile(bookmarkFile, entry1, true);
        FileUtils.writeStringToFile(bookmarkFile, entry2, true);
        FileUtils.writeStringToFile(bookmarkFile, entry3, true);

        Bookmark.cleanBookmarkFile(bookmarkFile);

        // Begin tests

        List<String> bookmarks = FileUtils.readLines(bookmarkFile);

        assertTrue(bookmarks.contains("alias1 http://www.google.com"));
        assertTrue(bookmarks.contains("alias2 http://www.facebook.com"));
        assertTrue(bookmarks.size() == 2);

    }

    @Test
    public void testCleanBookmarkFileDuplicateAliasesBothInvalidBookmarks() throws IOException {
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);
        String entry1 = "alias1 http://www.google.com    ";
        String entry2 = "alias1 http://www.youtube.com extraArg";
        String entry3 = "alias2 http://www.facebook.com";

        FileUtils.writeStringToFile(bookmarkFile, entry1, true);
        FileUtils.writeStringToFile(bookmarkFile, entry2, true);
        FileUtils.writeStringToFile(bookmarkFile, entry3, true);

        Bookmark.cleanBookmarkFile(bookmarkFile);

        // Begin tests

        List<String> bookmarks = FileUtils.readLines(bookmarkFile);

        assertTrue(bookmarks.contains("alias2 http://www.facebook.com"));
        assertTrue(bookmarks.size() == 1);
    }

    @Test
    public void testCleanBookmarkFileLeadingAndTrailingSpaces() throws IOException {
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);
        String entry1 = "     alias1 http://www.google.com    ";
        String entry2 = "   alias2 http://www.facebook.com";
        String entry3 = "alias3 http://www.youtube.com       ";
        String entry4 = "alias4 http://www.stackoverflow.com";

        FileUtils.writeStringToFile(bookmarkFile, entry1, true);
        FileUtils.writeStringToFile(bookmarkFile, entry2, true);
        FileUtils.writeStringToFile(bookmarkFile, entry3, true);
        FileUtils.writeStringToFile(bookmarkFile, entry4, true);

        Bookmark.cleanBookmarkFile(bookmarkFile);

        // Begin tests

        List<String> bookmarks = FileUtils.readLines(bookmarkFile);

        assertTrue(bookmarks.contains("alias4 http://www.stackoverflow.com"));
        assertTrue(bookmarks.size() == 1);
    }

    @Test
    public void testCleanBookmarkFileSpacesBetweenAliasAndURL() throws IOException {
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);
        String entry1 = "alias1 http://www.google.com";
        String entry2 = "alias2       http://www.facebook.com";
        String entry3 = "alias3  http://www.youtube.com";

        FileUtils.writeStringToFile(bookmarkFile, entry1, true);
        FileUtils.writeStringToFile(bookmarkFile, entry2, true);
        FileUtils.writeStringToFile(bookmarkFile, entry3, true);

        Bookmark.cleanBookmarkFile(bookmarkFile);

        // Begin tests

        List<String> bookmarks = FileUtils.readLines(bookmarkFile);

        assertTrue(bookmarks.contains("alias1 http://www.google.com"));
        assertTrue(bookmarks.size() == 1);
    }

    @Test
    public void testCleanBookmarkFileSpacesLeadingTrailingAndInBetween() throws IOException {
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);
        String entry1 = "    alias1      http://www.google.com      ";
        String entry2 = "alias2 http://www.facebook.com";
        String entry3 = " alias3  http://www.youtube.com";

        FileUtils.writeStringToFile(bookmarkFile, entry1, true);
        FileUtils.writeStringToFile(bookmarkFile, entry2, true);
        FileUtils.writeStringToFile(bookmarkFile, entry3, true);

        Bookmark.cleanBookmarkFile(bookmarkFile);

        // Begin tests

        List<String> bookmarks = FileUtils.readLines(bookmarkFile);

        assertTrue(bookmarks.contains("alias2 http://www.facebook.com"));
        assertTrue(bookmarks.size() == 1);
    }

    @Test
    public void testCleanBookmarkFileSingleBlankLineBetweenEntries() throws IOException {
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);
        String entry1 = "alias1 http://www.google.com";
        String entry2 = "";
        String entry3 = "alias2 http://www.facebook.com";

        FileUtils.writeStringToFile(bookmarkFile, entry1, true);
        FileUtils.writeStringToFile(bookmarkFile, entry2, true);
        FileUtils.writeStringToFile(bookmarkFile, entry3, true);

        Bookmark.cleanBookmarkFile(bookmarkFile);

        // Begin tests

        List<String> bookmarks = FileUtils.readLines(bookmarkFile);

        assertTrue(bookmarks.contains("alias1 http://www.google.com"));
        assertTrue(bookmarks.contains("alias2 http://www.facebook.com"));
        assertTrue(bookmarks.size() == 2);
    }

    @Test
    public void testCleanBookmarkFileMultipleBlankLinesBetweenEntries() throws IOException {
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);
        String entry1 = "alias1 http://www.google.com";
        String entry2 = "\n\n\n\n\n\n\n\n\n\n";
        String entry3 = "alias2 http://www.facebook.com";
        String entry4 = "alias3 http://www.youtube.com";
        String entry5 = "\n\n";
        String entry6 = "alias4 http://www.stackoverflow.com";

        FileUtils.writeStringToFile(bookmarkFile, entry1, true);
        FileUtils.writeStringToFile(bookmarkFile, entry2, true);
        FileUtils.writeStringToFile(bookmarkFile, entry3, true);
        FileUtils.writeStringToFile(bookmarkFile, entry4, true);
        FileUtils.writeStringToFile(bookmarkFile, entry5, true);
        FileUtils.writeStringToFile(bookmarkFile, entry6, true);

        Bookmark.cleanBookmarkFile(bookmarkFile);

        // Begin tests

        List<String> bookmarks = FileUtils.readLines(bookmarkFile);

        assertTrue(bookmarks.contains("alias1 http://www.google.com"));
        assertTrue(bookmarks.contains("alias2 http://www.facebook.com"));
        assertTrue(bookmarks.contains("alias3 http://www.youtube.com"));
        assertTrue(bookmarks.contains("alias4 http://www.stackoverflow.com"));
        assertTrue(bookmarks.size() == 4);
    }

    @Test
    public void testCleanBookmarkFileLeadingBlankLinesInFile() throws IOException {
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);
        String entry1 = "\n\n\n\n\n\n\n\n\n\n";
        String entry2 = "alias1 http://www.google.com";
        String entry3 = "alias2 http://www.facebook.com";
        String entry4 = "alias3 http://www.youtube.com";

        FileUtils.writeStringToFile(bookmarkFile, entry1, true);
        FileUtils.writeStringToFile(bookmarkFile, entry2, true);
        FileUtils.writeStringToFile(bookmarkFile, entry3, true);
        FileUtils.writeStringToFile(bookmarkFile, entry4, true);

        Bookmark.cleanBookmarkFile(bookmarkFile);

        // Begin tests

        List<String> bookmarks = FileUtils.readLines(bookmarkFile);

        assertTrue(bookmarks.contains("alias1 http://www.google.com"));
        assertTrue(bookmarks.contains("alias2 http://www.facebook.com"));
        assertTrue(bookmarks.contains("alias3 http://www.youtube.com"));
        assertTrue(bookmarks.size() == 3);
    }

    @Test
    public void testCleanBookmarkFileTrailingBlankLinesInFile() throws IOException {
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);
        String entry1 = "alias3 http://www.youtube.com";
        String entry2 = "alias1 http://www.google.com";
        String entry3 = "alias2 http://www.facebook.com";
        String entry4 = "\n\n\n\n\n\n\n\n\n\n";

        FileUtils.writeStringToFile(bookmarkFile, entry1, true);
        FileUtils.writeStringToFile(bookmarkFile, entry2, true);
        FileUtils.writeStringToFile(bookmarkFile, entry3, true);
        FileUtils.writeStringToFile(bookmarkFile, entry4, true);

        Bookmark.cleanBookmarkFile(bookmarkFile);

        // Begin tests

        List<String> bookmarks = FileUtils.readLines(bookmarkFile);

        assertTrue(bookmarks.contains("alias1 http://www.google.com"));
        assertTrue(bookmarks.contains("alias2 http://www.facebook.com"));
        assertTrue(bookmarks.contains("alias3 http://www.youtube.com"));
        assertTrue(bookmarks.size() == 3);
    }

    @Test
    public void testCleanBookmarkFileLeadingAndTrailingBlankLinesInFile() throws IOException {
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);
        String entry1 = "\n\n\n\n\n\n\n\n\n\n";
        String entry2 = "alias1 http://www.google.com";
        String entry3 = "alias2 http://www.facebook.com";
        String entry4 = "alias3 http://www.youtube.com";
        String entry5 = "\n\n\n\n";

        FileUtils.writeStringToFile(bookmarkFile, entry1, true);
        FileUtils.writeStringToFile(bookmarkFile, entry2, true);
        FileUtils.writeStringToFile(bookmarkFile, entry3, true);
        FileUtils.writeStringToFile(bookmarkFile, entry4, true);
        FileUtils.writeStringToFile(bookmarkFile, entry5, true);

        Bookmark.cleanBookmarkFile(bookmarkFile);

        // Begin tests

        List<String> bookmarks = FileUtils.readLines(bookmarkFile);

        assertTrue(bookmarks.contains("alias1 http://www.google.com"));
        assertTrue(bookmarks.contains("alias2 http://www.facebook.com"));
        assertTrue(bookmarks.contains("alias3 http://www.youtube.com"));
        assertTrue(bookmarks.size() == 3);
    }

    @Test
    public void testCleanBookmarkFileMultipleTypesOfInvalidData() throws IOException {
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);
        String entry1 = "   alias1 http://www.google.com";
        String entry2 = "alias1 http://www.facebook.com";
        String entry3 = "alias2 facebook";
        String entry4 = "\narg1\n\n";
        String entry5 = "alias3    http://www.stackoverflow.com";
        String entry6 = "alias4 http://www.youtube.com";
        String entry7 = "alias1 http://www.apple.com";

        FileUtils.writeStringToFile(bookmarkFile, entry1, true);
        FileUtils.writeStringToFile(bookmarkFile, entry2, true);
        FileUtils.writeStringToFile(bookmarkFile, entry3, true);
        FileUtils.writeStringToFile(bookmarkFile, entry4, true);
        FileUtils.writeStringToFile(bookmarkFile, entry5, true);
        FileUtils.writeStringToFile(bookmarkFile, entry6, true);
        FileUtils.writeStringToFile(bookmarkFile, entry7, true);

        Bookmark.cleanBookmarkFile(bookmarkFile);

        // Begin tests

        List<String> bookmarks = FileUtils.readLines(bookmarkFile);

        assertTrue(bookmarks.contains("alias1 http://www.facebook.com")
                || bookmarks.contains("alias1 http://www.apple.com"));
        assertTrue(bookmarks.contains("alias4 http://www.youtube.com"));
        assertTrue(bookmarks.size() == 2);
    }

    // ####################################################################################################
    // public static void getBookmarkFileData(File bookmarkFile) tests

    @Test
    public void testGetBookmarkFileDataEmptyFile() throws IOException {
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);
        Map<String, String> aliasesToURLs = Bookmark.getBookmarkFileData(bookmarkFile);

        // Begin tests

        assertTrue(aliasesToURLs.size() == 0);
    }

    @Test
    public void testGetBookmarkFileDataExistingBookmarks() throws IOException {
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);
        String entry1 = "alias1 http://www.google.com";
        String entry2 = "alias2 http://www.facebook.com";
        String entry3 = "alias3 http://www.youtube.com";

        FileUtils.writeStringToFile(bookmarkFile, entry1, true);
        FileUtils.writeStringToFile(bookmarkFile, entry2, true);
        FileUtils.writeStringToFile(bookmarkFile, entry3, true);

        Map<String, String> aliasesToURLs = Bookmark.getBookmarkFileData(bookmarkFile);

        // Begin tests

        assertTrue(aliasesToURLs.get("alias1").equals("http://www.google.com"));
        assertTrue(aliasesToURLs.get("alias2").equals("http://www.facebook.com"));
        assertTrue(aliasesToURLs.get("alias3").equals("http://www.youtube.com"));
        assertTrue(aliasesToURLs.size() == 3);
    }

    // ####################################################################################################
    // public static void updateBookmarkFile(File bookmarkFile, Map<String, String> aliasesToURLs) tests















}
