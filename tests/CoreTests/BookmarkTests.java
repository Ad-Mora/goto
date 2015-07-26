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
    public void setUp() {
        System.out.println("setup");
        FileUtils.deleteQuietly(new File(""));


    }

    @After
    public void tearDown() {
        System.out.println("tearDown");
    }

    @AfterClass
    public static void oneTimeTearDown() {
        System.out.println("oneTimeTearDown");
    }

    @Test
    public void test1() {
        System.out.println("testing");
        int testInt = 4/0;
    }





}
