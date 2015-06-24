package Core;

import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by AdrianM on 6/18/15.
 */
public class Main {

    public static final Set<String> VALID_FLAGS = new HashSet<String>(Arrays.asList("--full", "--front", "--config"));

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

            if (Utils.validateFlags(flags)) {

                for (String flag : flags) {
                }

                ChromeOptions options = new ChromeOptions();
                options.addArguments("silent");
                WebDriver driver = new ChromeDriver(options);
                url = Utils.formatURL(url);
                driver.get(url);

            } else {
                System.out.println(invalidArgMessage);
            }
        } else {
            System.out.println(invalidArgMessage);
        }




    }
}
