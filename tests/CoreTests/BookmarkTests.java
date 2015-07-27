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
 * - File does not exist, parent directory exists
 * - File does not exist, parent directory does not exist
 * - File is empty
 * - File contains some existing bookmarks
 * - File contains invalidly formatted data
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
 *
 * ##################################################
 *
 * public static void cleanFile(File configFile)
 *
 * configFile:
 *
 * - File is empty
 * - File has validly formatted data
 * - File has only invalidly formatted data
 * - File has
 *
 *
 * public static void createOrUpdateBookmark(File configFile, String alias, String url)
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
 * - Alias contains a newline character
 *
 * url:
 *
 * - URL does not exist in file
 * - URL exists in file
 *
 *
 * ##################################################
 * public static Map<String, String> getFileData(File configFile)
 *
 * configFile:
 *
 * - File is empty
 * - File contains existing bookmarks
 *
 *
 * ##################################################
 * public static void updateBookmark(File configFile, Map<String, String> aliasesToURLs)
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
 *
 * ##################################################
 * public static void createBookmark(File configFile, String alias, String url)
 *
 * configFile:
 *
 * - File is empty
 * - File contains existing bookmarks
 * - File contains invalidly formatted data
 *
 *
 *
 * ##################################################
 * public static String getURLFromAlias(File configFile, String alias)
 *
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
        System.out.println("setup");
        FileUtils.deleteDirectory(new File(Main.GOTO_CONFIG_FOLDER_PATH));
    }

    @AfterClass
    public static void oneTimeTearDown() throws IOException {
        System.out.println("oneTimeTearDown");
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
