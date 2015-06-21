package Core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Arrays;

/**
 * Created by AdrianM on 6/18/15.
 */
public class Main {

    public static final String[] VALID_FLAGS = {"--full", "--front", "--config"};

    public static void main(String[] args) {

        System.out.println("Made it here");

        String[] urlAndFlags = Utils.parseArgs(args);
        String url = urlAndFlags[0];
        String[] flags = Arrays.copyOfRange(urlAndFlags, 1, urlAndFlags.length);

        if (Utils.validateArgs(url, flags)) {
            WebDriver driver = new ChromeDriver();

        } else {
            System.out.println(invalidArgMesage);
        }


    }
}
