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
 * public static void bookmark(File configFile, String alias, String url)
 *
 * configFile:
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
 * public static void deleteBookmark(File configFile, String alias)
 *
 * configFile:
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
 * public static void viewBookmarks(File configFile)
 *
 * configFile:
 *
 * - File is empty
 * - File contains existing bookmarks
 *
 * ##################################################
 * public static void createConfigFile(File configFile)
 *
 * - File does not exist
 * - .config folder does not exist
 * - .config folder exists, gotoconfig folder does not exist
 * - .config folder exists, gotoconfig folder exists, file does not exist
 * - File exists
 *
 * ##################################################
 * public static void cleanConfigFile(File configFile)
 *
 * configFile:
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
 * public static Map<String, String> getConfigFileData(File configFile)
 *
 * configFile:
 *
 * - File is empty
 * - File contains existing bookmarks
 *
 * ##################################################
 * public static void updateConfigFile(File configFile, Map<String, String> aliasesToURLs)
 *
 * - Update old alias with new value
 * - Delete old alias
 *
 * configFile:
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
 * public static String getURLFromAlias(File configFile, String alias)
 *
 * configFile:
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

    private static String originalFileContent = "";

    @BeforeClass
    public static void oneTimeSetup() throws IOException {
        File configFile = new File(Main.CONFIG_FILE_PATH);
        if (configFile.exists()) {
            originalFileContent = FileUtils.readFileToString(configFile);
        }
    }

    @Before
    public void setUp() throws IOException {
        FileUtils.deleteDirectory(new File(Main.CONFIG_FILE_PATH).getParentFile());
    }

    @AfterClass
    public static void oneTimeTearDown() throws IOException {
        File configFolder = new File(Main.GOTO_CONFIG_FOLDER_PATH);
        File configFile = new File(Main.CONFIG_FILE_PATH);

        FileUtils.deleteDirectory(configFolder);

        configFolder.mkdir();
        configFile.createNewFile();
        FileUtils.writeStringToFile(configFile, originalFileContent);
    }

    @Test
    public void test1() {
        System.out.println("testing1");
    }

    @Test
    public void test2() {
        System.out.println("testing2");
    }



}
