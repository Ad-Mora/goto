package CoreTests;


import Core.Main;
import org.apache.commons.io.FileUtils;
import org.junit.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by AdrianM on 7/19/15.
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
