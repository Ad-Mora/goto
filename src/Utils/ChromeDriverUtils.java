package Utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.awt.*;

/**
 * Created by AdrianM on 6/28/15.
 */
public class ChromeDriverUtils {

    public static void maximizeChromeWindow(WebDriver driver) {

        java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)screenSize.getWidth();
        int height = (int)screenSize.getHeight();

        org.openqa.selenium.Dimension targetSize = new org.openqa.selenium.Dimension(width, height);
        org.openqa.selenium.Point targetPosition = new org.openqa.selenium.Point(0,0);

        driver.manage().window().setSize(targetSize);
        driver.manage().window().setPosition(targetPosition);
    }

    public static void bringWindowToFront(WebDriver driver) {

        ((JavascriptExecutor) driver).executeScript("window.focus();");
        ((JavascriptExecutor) driver).executeScript("alert('Test')");
        driver.switchTo().alert().accept();
    }
}
