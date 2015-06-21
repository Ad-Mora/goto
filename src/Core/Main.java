package Core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.util.Arrays;

/**
 * Created by AdrianM on 6/18/15.
 */
public class Main {

    public static final String[] VALID_FLAGS = {"--full", "--front", "--config"}; // make set?

    public static void main(String[] args) {

        System.out.println("Made it here"); // marker; delete later

        File chromedriver = new File("/Users/AdrianM/Google Drive/CodingProjects/JavaProjects/goto/chromedriver");
        System.setProperty("webdriver.chrome.driver", chromedriver.getAbsolutePath());

        String url;
        String[] flags;
        String invalidArgMessage = "Invalid argument(s). Use the '--help' flag for help.";


        if (args.length > 0) {
            url = args[0];
            flags = Arrays.copyOfRange(args, 1, args.length);

            if (Utils.validateArgs(url, flags)) {
                WebDriver driver = new ChromeDriver();
                url = Utils.formatURL(url);
                driver.get(url); // find out what does and does not cause an error here

                for (String flag : flags) {
                }

            } else {
                System.out.println(invalidArgMessage);
            }
        } else {
            System.out.println(invalidArgMessage);
        }




    }
}
