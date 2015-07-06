package Core;

import Utils.ChromeDriverUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

import Utils.ArgUtils;

/**
 * Created by AdrianM on 6/18/15.
 */

/**
 * This class and enclosed main method control the main flow of the program, and contain the overall architecture.
 * The WebDriver is launched from here. Arguments are also passed in through here, and are parsed using helper classes.
 *
 * It is assumed that every element in the args array is an individual argument, rather than there being only one item
 * in the args array containing a single String of all the arguments.
 *
 * The very first argument passed in is treated as the URL argument, and every subsequent space separated argument is
 * treated as a flag.
 *
 * The browser that is opened is configured accordingly with the arguments passed in. The passing in of one or more
 * invalid arguments disallows the launching of the browser, and an invalid input message is output.
 * The optional flags are also set here, as constants.
 */
public class Main {

    // Flags
    public static final String MAX_FLAG = "--max";
    public static final String FRONT_FLAG = "--front";
    public static final String HELP_FLAG = "--help";
    public static final Set<String> VALID_FLAGS = new HashSet<String>(Arrays.asList(MAX_FLAG, FRONT_FLAG, HELP_FLAG));

    public static void main(String[] args) throws URISyntaxException, IOException {

        // Necessary setup for ChromeDriver to function
        File chromedriver = new File("/Users/AdrianM/Google Drive/CodingProjects/JavaProjects/goto/chromedriver");
        System.setProperty("webdriver.chrome.driver", chromedriver.getAbsolutePath());

        String url;
        Set<String> flags;

        if (args.length > 0) {
            url = ArgUtils.formatURL(args[0]);
            String[] flagsArr = Arrays.copyOfRange(args, 1, args.length);
            flags = new HashSet<String>(Arrays.asList(flagsArr));

            if (ArgUtils.validateFlags(flags)) {
                // If the help flag is given at any point, do not start the driver and instead show the help message
                if (flags.contains(HELP_FLAG) || url.equals(ArgUtils.formatURL(HELP_FLAG))) {
                    System.out.println(Help.getHelp());
                    return;
                }

                // The browser is actually started upon initialization
                WebDriver driver = new ChromeDriver();
                for (String flag : flags) {

                    if (flag.equals(MAX_FLAG)) {
                        ChromeDriverUtils.maximizeChromeWindow(driver);
                    } else if (flag.equals(FRONT_FLAG)) {
                        ChromeDriverUtils.bringWindowToFront(driver);
                    }
                }
                driver.get(url);
            } else {
                System.out.println(Help.getInvalidArgMessage());
            }
        } else {
            System.out.println(Help.getInvalidArgMessage());
        }
    }
}
