package Core;

import Utils.ChromeDriverUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import Utils.ArgUtils;

/**
 * Created by AdrianM on 6/18/15.
 */
public class Main {

    public static final Set<String> VALID_FLAGS =
            new HashSet<String>(Arrays.asList("--max", "--front", "--help"));

    public static void main(String[] args) throws URISyntaxException, IOException {

        File chromedriver = new File("/Users/AdrianM/Google Drive/CodingProjects/JavaProjects/goto/chromedriver");
        System.setProperty("webdriver.chrome.driver", chromedriver.getAbsolutePath());

        String url;
        Set<String> flags;
        String invalidArgMessage = "Invalid argument(s). Use the '--help' flag for help.";

        if (args.length > 0) {

            url = ArgUtils.formatURL(args[0]);
            String[] flagsArr = Arrays.copyOfRange(args, 1, args.length);
            flags = new HashSet<String>(Arrays.asList(flagsArr));

            if (ArgUtils.validateFlags(flags)) {

                if (flags.contains("--help") || url.equals(ArgUtils.formatURL("--help"))) {
                    System.out.println(Help.getHelp());
                    return;
                }

                WebDriver driver = new ChromeDriver();
                for (String flag : flags) {

                    if (flag.equals("--max")) {
                        ChromeDriverUtils.maximizeChromeWindow(driver);
                    } else if (flag.equals("--front")) {
                        ChromeDriverUtils.bringWindowToFront(driver);
                    }
                }
                driver.get(url);
            } else {
                System.out.println(invalidArgMessage);
            }
        } else {
            System.out.println(invalidArgMessage);
        }




    }
}
