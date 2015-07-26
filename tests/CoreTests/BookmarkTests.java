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
 * public static void bookmark(File configFile, String alias, String url)
 *
 * configFile:
 *
 * - configFile does not exist, parent directory exists
 * - configFile does not exist, parent directory does not exist
 * - configFile is empty
 * - configFile contains some existing bookmarks
 * - configFile contains invalidly formatted data
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
 * public static void createOrUpdateBookmark(File configFile, String alias, String url)
 *
 * -
 *
 * public static Map<String, String> getFileData(File configFile)
 *
 * public static void updateBookmark(File configFile, Map<String, String> aliasesToURLs)
 *
 * public static void createBookmark(File configFile, String alias, String url)
 *
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
