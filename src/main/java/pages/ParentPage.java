package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;

public class ParentPage extends ActionWithElements {

    String env = System.getProperty("env", "qa"); // prepare URL for different environments
    public final String BASE_URL = String.format("https://%s-complexapp.onrender.com", env);

    public ParentPage(WebDriver webDriver) {
        super(webDriver);
    }

    public void resizeBrowserWindow(int width, int height) {
        Dimension dimension = new Dimension(width, height);
        webDriver.manage().window().setSize(dimension);
        logger.info("Browser window was resized to " + width + "x" + height);
    }

    public void minimizeBrowserWindow() {  // for Windows only
        webDriver.manage().window().setPosition(new Point(-2000, 0)); // move window out of screen
        logger.info("Browser window was minimized");
    }

    public void maximizeBrowserWindow() { // for Windows only
        webDriver.manage().window().maximize(); // move window out of screen
        logger.info("Browser window was maximized");
    }

    public void refreshPage() {
        webDriver.navigate().refresh();
        logger.info("Page was refreshed");
    }

    public void sendChordKeys(WebElement element, Keys key1, CharSequence key2) { // send chord keys to element
        Actions action = new Actions(webDriver);
        action.sendKeys(Keys.chord(key1, key2)).perform();
    }

    public void pressKey(WebElement element, Keys key) { // press key on element
        try {
            new Actions(webDriver).sendKeys(element, key).perform();
            logger.info("Key pressed");
        } catch (Exception e) {
            logger.error("Can not press key");
        }
    }

    public void openNewTabAndSwitchToIt(String url) {
        ((JavascriptExecutor) webDriver).executeScript("window.open()");
        ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
        webDriver.switchTo().window(tabs.get(tabs.size() - 1));
        webDriver.get(url);
    }

    public void switchToPreviousTab() {
        ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
        int currentIndex = tabs.indexOf(webDriver.getWindowHandle());
        int previousIndex = currentIndex - 1;
        if (previousIndex < 0) {
            previousIndex = tabs.size() - 1;
        }
        webDriver.switchTo().window(tabs.get(previousIndex));
    }

    public void switchToNextTab() {
        ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
        int currentIndex = tabs.indexOf(webDriver.getWindowHandle());
        int nextIndex = currentIndex + 1;
        if (nextIndex >= tabs.size()) {
            nextIndex = 0;
        }
        webDriver.switchTo().window(tabs.get(nextIndex));
    }

    public void waitForMinutes(int minutes) {
        try {
            int milliseconds = minutes * 60000;  // transform minutes to milliseconds
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}