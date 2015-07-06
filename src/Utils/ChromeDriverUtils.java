package Utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.awt.*;

/**
 * Created by AdrianM on 6/28/15.
 */

/**
 * This class contains helper functions that modify the driver's characteristics, such as the size of the browser,
 * or whether or not the browser is brought to the front of the screen as the active window.
 */
public class ChromeDriverUtils {

    /**
     * This function takes in a driver and maximizes its corresponding browser window to fit the dimensions
     * of the current screen.
     *
     * @param driver An instance of a Selenium WebDriver.
     */
    public static void maximizeChromeWindow(WebDriver driver) {

        java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)screenSize.getWidth();
        int height = (int)screenSize.getHeight();

        org.openqa.selenium.Dimension targetSize = new org.openqa.selenium.Dimension(width, height);
        org.openqa.selenium.Point targetPosition = new org.openqa.selenium.Point(0,0);

        driver.manage().window().setSize(targetSize);
        driver.manage().window().setPosition(targetPosition);
    }

    /**
     * This function takes in a driver and brings its browser to the front of the screen, making it the active window.
     *
     * @param driver An instance of a Selenium WebDriver.
     */
    public static void bringWindowToFront(WebDriver driver) {

        ((JavascriptExecutor) driver).executeScript("window.focus();");
        ((JavascriptExecutor) driver).executeScript("alert('Test')");
        driver.switchTo().alert().accept();
    }
}
