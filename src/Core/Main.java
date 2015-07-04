package Core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.util.*;

import Utils.ChromeDriverUtils;
import Utils.StringUtils;

/**
 * Created by AdrianM on 6/18/15.
 */
public class Main {

    public static final Set<String> VALID_FLAGS = new HashSet<String>(Arrays.asList("--max,", "--full", "--front", "--help"));

    public static void main(String[] args) {

        File chromedriver = new File("/Users/AdrianM/Google Drive/CodingProjects/JavaProjects/goto/chromedriver");
        System.setProperty("webdriver.chrome.driver", chromedriver.getAbsolutePath());

        String url;
        Set<String> flags;
        String invalidArgMessage = "Invalid argument(s). Use the '--help' flag for help.";

        if (args.length > 0) {

            url = StringUtils.formatURL(args[0]);

            String[] flagsArr = Arrays.copyOfRange(args, 1, args.length);
            flags = new HashSet<String>(Arrays.asList(flagsArr));

            if (StringUtils.validateFlags(flags)) {

                if (flags.contains("--help")) {
                    System.out.println(Help.getHelp());
                    return;
                }

                for (String flag : flags) {

                    // maximized window
                    if (flag.equals("--max")) {

                    } else if (flag.equals("--full")) {

                    } else if (flag.equals("--front")) {

                    } else if (flag.equals("--help")) {

                    }
                }

                System.out.println("Valid"); // marker; delete later

                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-maximized");
                options.addArguments("user-data-dir=/Users/AdrianM/Library/Application Support/Google/Chrome/");

                WebDriver driver = new ChromeDriver(options);

            } else {
                System.out.println(invalidArgMessage);
            }
        } else {
            System.out.println(invalidArgMessage);
        }




    }
}
