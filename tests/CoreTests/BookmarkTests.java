package CoreTests;


import Core.Main;
import org.apache.commons.io.FileUtils;
import org.junit.*;

import java.io.File;
import java.io.IOException;

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
 * - Alias contains newline character
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
 * - General project folder does not exist
 * - General project folder exists, goto folder does not exist
 * - General project folder, goto folder exists, file does not exist
 * - File exists
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
 * - One of the URLs is not fully qualified
 * - File contains duplicate aliases
 * - Leading and trailing spaces around an entry
 * - Extra spaces between alias and URL
 * - Blank line between two entries
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

    @BeforeClass
    public static void oneTimeSetup() throws IOException {
        File generalProjectDir = new File(Main.GENERAL_PROJECT_DIR_PATH);
        File generalProjectDirTemp = new File(Main.GENERAL_PROJECT_DIR_PATH_TEMP);

        if (generalProjectDir.exists()) {
            FileUtils.copyDirectory(generalProjectDir, generalProjectDirTemp);
        }
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
    }

    @AfterClass
    public static void oneTimeTearDown() throws IOException {
        File generalProjectDir = new File(Main.GENERAL_PROJECT_DIR_PATH);
        File generalProjectDirTemp = new File(Main.GENERAL_PROJECT_DIR_PATH_TEMP);

        if (generalProjectDirTemp.exists()) {
            FileUtils.copyDirectory(generalProjectDirTemp, generalProjectDir);
        }
    }

    // ##################################################
    // bookmark(File bookmarkFile, String alias, String url) tests

    // bookmarkFile tests

    @Test
    public void testBookmarkEmptyFile() {
        File bookmarkFile = new File(Main.BOOKMARK_FILE_PATH);

    }





}
