package pages;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends ParentPage {
    @FindBy(xpath = "//input[@placeholder='Username']")
    private WebElement usernameInput;
    @FindBy(xpath = "//input[@placeholder='Password']")
    private WebElement passwordInput;
    @FindBy(xpath = "//button[@class='btn btn-primary btn-sm']")
    private WebElement loginSignInButton;
    @FindBy(xpath = "//div[@class=\"alert alert-danger text-center\"]")
    private WebElement alertMessage;

    public LoginPage(WebDriver webDriver) {
        super(webDriver);
    }

    public LoginPage openLoginPage() {
        openPage(BASE_URL);
        return this;
    }

    public LoginPage enterUsername(String username) {
        enterTextIntoInput(usernameInput, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        enterTextIntoInput(passwordInput, password);
        return this;
    }

    public LoginPage clickOnLoginSignInButton() {
        clickOnElement(loginSignInButton);
        return this;
    }


    public LoginPage checkIsSignInButtonNotVisible() {
        checkElementNotDisplayed(loginSignInButton);
        return this;
    }

    public LoginPage checkIsSignInButtonVisible() {
        checkElementDisplayed(loginSignInButton);
        return this;
    }

    public LoginPage checkIsAlertMessageVisible() {
        checkElementDisplayed(alertMessage);
        return this;
    }

    public LoginPage enterUsernameWithTab(String username) {
        enterTextIntoInput(usernameInput, username);
        pressTabKey(usernameInput);
        return this;
    }

    public LoginPage enterPasswordWithEnter(String password) {
        enterTextIntoInput(passwordInput, password);
        pressEnterKey(passwordInput);
        return this;
    }


    public LoginPage checkTextInAlertMessage(String text) {
        Assert.assertEquals("Alert message text doesn't match expected text", text, alertMessage.getText());
        return this;
    }

    public String getAlertMessageBackgroundColor() {
        return alertMessage.getCssValue("background-color");
    }


    public LoginPage checkBackgroundColor(String expectedColor) {
        String actualColor = getAlertMessageBackgroundColor();
        String expectedRGBColor = Color.fromString(expectedColor).asRgba();
        Assert.assertEquals("Alert message background color doesn't match expected color",
                expectedRGBColor, actualColor);
        return this;
    }
}