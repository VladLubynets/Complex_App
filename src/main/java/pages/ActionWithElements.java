package pages;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ActionWithElements {
    public WebDriver webDriver;
    public WebDriverWait webDriverWait10;
    Logger logger = Logger.getLogger(getClass());

    public ActionWithElements(WebDriver webDriver) { // constructor
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this); // init web-drivers elements For example: @FindBy

        webDriverWait10 = new WebDriverWait(webDriver, Duration.ofSeconds(10)); //init waiter for 10 seconds
    }

    public void openPage(String url) {
        try {
            webDriver.get(url);
            logger.info("Page was opened " + url);
        } catch (Exception e) {
            logger.info("Can not open " + url);
            Assert.fail("Can not open " + url);

        }
    }

    public void clickOnElement(WebElement element) { // click on web-element
        waitUntilElementIsVisible(element);
        try {
            element.click();
            logger.info("Element was clicked");
        } catch (Exception e) {
            printErrorAndStopTest(e);
        }
    }

    public void waitUntilElementIsVisible(WebElement element) { // wait until element is visible
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void checkElementNotDisplayed(WebElement element) { // check if web-element is not displayed
        Assert.assertFalse("Element is displayed", isElementDisplayed(element));
    }


    public boolean isElementDisplayed(WebElement element) {  // true or false if false - element is not displayed
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void checkElementDisplayed(WebElement element) { // check if web-element is displayed
        webDriverWait10.until(ExpectedConditions.visibilityOf(element));
        Assert.assertTrue("Element is not displayed", isElementDisplayed(element));

    }

    private void printErrorAndStopTest(Exception e) { // if something wrong - print error and stop test
        System.out.println("Can not work with element" + e);
        Assert.fail("Can not work with element" + e);
    }

    public void enterTextIntoInput(WebElement input, String text) { // enter text into web-element(input)
        try {
            webDriverWait10.until(ExpectedConditions.visibilityOf(input));
            input.clear();
            input.sendKeys(text);
        } catch (Exception e) {
            printErrorAndStopTest(e);

        }
    }

    public void pressTabKey(WebElement element) {
        try {
            new Actions(webDriver).sendKeys(element, Keys.TAB).perform();
            logger.info("Tab key pressed");
        } catch (Exception e) {
            printErrorAndStopTest(e);
        }


    }

    public void pressEnterKey(WebElement element) {
        try {
            new Actions(webDriver).sendKeys(element, Keys.ENTER).perform();
            logger.info("Enter key pressed");
        } catch (Exception e) {
            printErrorAndStopTest(e);
        }
    }
}
