package pages;

import TestData.ColorPalette;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
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

    public void pressKey(WebElement element, Keys key) {
        try {
            new Actions(webDriver).sendKeys(element, key).perform();
            logger.info("Key pressed");
        } catch (Exception e) {
            printErrorAndStopTest(e);
        }
    }

    public String getColorElement(WebElement element) {
        return element.getCssValue("background-color");
    }

    public void checkColorElement(WebElement element, ColorPalette colorEnum) {
        String actualColor = getColorElement(element);
        String expectedRGBColor = Color.fromString(colorEnum.getColorCode()).asRgba();
        Assert.assertEquals("Element's color doesn't match expected Enum color",
                expectedRGBColor, actualColor);
    }

    public String getTextColorElement(WebElement element) {
        return element.getCssValue("color");
    }

    public void checkTextColorElement(WebElement element, ColorPalette colorEnum) {
        checkElementDisplayed(element);
        String actualColor = getTextColorElement(element);
        String expectedRGBColor = Color.fromString(colorEnum.getColorCode()).asRgba();
        Assert.assertEquals("Text in element`s color doesn't match expected Enum color",
                expectedRGBColor, actualColor);
    }

    public void checkColorBorderPlaceHolder(WebElement element, ColorPalette colorEnum) {
        checkElementDisplayed(element);

        String actualColorString = getColorBorderOnFocusAfterClick(element);
        Color actualBorderColor = Color.fromString(actualColorString);

        Color expectedBorderColor = Color.fromString(colorEnum.getColorCode());

        Assert.assertEquals("Border Username Placeholder color doesn't match expected color Enum",
                expectedBorderColor.asRgb(), actualBorderColor.asRgb());
    }

    public String getColorBorderOnFocusAfterClick(WebElement element) { // u need click on element before for check border color
        clickOnElement(element);
        return element.getCssValue("border-color");
    }
    public void copyValueFromElement(WebElement element){
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        element.sendKeys(Keys.chord(Keys.CONTROL, "c"));

    }
    public void pasteValueToElement(WebElement element){
        element.sendKeys(Keys.chord(Keys.CONTROL, "v"));
    }

    public void checkTextInElement(WebElement element, String expectedText) {
        checkElementDisplayed(element);
        String actualText = element.getText();
        Assert.assertEquals("Text in element doesn't match expected text",
                expectedText, actualText);
    }

    public void verifyHiddenElementWithNameIsVisibleAndHidden() {
        String name = "_csrf";
        WebElement element = webDriver.findElement(By.cssSelector("input[type='hidden'][name='" + name + "']"));

        boolean isHidden = !element.isDisplayed();
        boolean isHiddenType = "hidden".equalsIgnoreCase(element.getAttribute("type"));
        boolean isHiddenName = name.equalsIgnoreCase(element.getAttribute("name"));

        Assert.assertTrue("Element is not hidden", isHidden);
        Assert.assertTrue("Element type is not hidden", isHiddenType);
        Assert.assertTrue("Element name does not match", isHiddenName);
    }
}
