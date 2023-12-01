package pages;

import org.junit.Assert;
import org.openqa.selenium.Keys;
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

    public LoginPage enterLoginWithKey(String password, Keys keyToPress) {
        enterTextIntoInput(usernameInput, password);
        pressKey(usernameInput, keyToPress);
        return this;
    }

    public LoginPage enterPasswordWithKey(String password, Keys keyToPress) {
        enterTextIntoInput(passwordInput, password);
        pressKey(passwordInput, keyToPress);
        return this;
    }


    public LoginPage checkTextInAlertMessage(String text) {
        Assert.assertEquals("Alert message text doesn't match expected text", text, alertMessage.getText());
        return this;
    }

    public String getAlertMessageBackgroundColor() {
        return alertMessage.getCssValue("background-color");
    }


    public LoginPage checkBackgroundColorAlert(color backgroundColor) {
        String actualColor = getAlertMessageBackgroundColor();
        String expectedRGBColor = Color.fromString(backgroundColor.getColor()).asRgba();
        Assert.assertEquals("Alert message background color doesn't match expected color",
                expectedRGBColor, actualColor);
        return this;
    }

    public enum color {
        WHITE("#ffffff"),
        BLUE("#cce5ff"),
        PINK("#f8d7da");

        private String color;

        color(String color) {
            this.color = color;
        }

        public String getColor() {
            return color;
        }
    }
}