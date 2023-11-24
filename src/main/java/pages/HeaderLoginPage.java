package pages;

import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class HeaderLoginPage extends ParentPage {
    @FindBy(xpath = "//input[@placeholder='Username']")
    private WebElement usernameInput;
    @FindBy(xpath = "//input[@placeholder='Password']")
    private WebElement passwordInput;
    @FindBy(xpath = "//button[@class='btn btn-primary btn-sm']")
    private WebElement loginSignInButton;
    @FindBy(xpath = "//button[@class=\"btn btn-sm btn-secondary\" and contains(text(), 'Sign Out')]")
    private WebElement signOutButton;
    @FindBy(xpath = "//span[@class=\"text-white mr-2\"]")
    private WebElement avatar;
    @FindBy(xpath = "//div[@class=\"alert alert-danger text-center\"]")
    private WebElement alertMessage;

    public HeaderLoginPage(WebDriver webDriver) {
        super(webDriver);
    }

    public HeaderLoginPage openLoginPage() {
        openPage(BASE_URL);
        return this;
    }

    public HeaderLoginPage enterUsername(String username) {
        enterTextIntoInput(usernameInput, username);
        return this;
    }

    public HeaderLoginPage enterPassword(String password) {
        enterTextIntoInput(passwordInput, password);
        return this;
    }

    public HeaderLoginPage clickOnLoginSignInButton() {
        clickOnElement(loginSignInButton);
        return this;
    }

    public HeaderLoginPage checkIsSignOutButtonVisible() {
        checkElementDisplayed(signOutButton);
        return this;
    }

    public HeaderLoginPage checkIsSignOutButtonNotVisible() {
        checkElementNotDisplayed(signOutButton);
        return this;
    }

    public HeaderLoginPage checkIsSignInButtonNotVisible() {
        checkElementNotDisplayed(loginSignInButton);
        return this;
    }

    public HeaderLoginPage checkIsSignInButtonVisible() {
        checkElementDisplayed(loginSignInButton);
        return this;
    }

    public HeaderLoginPage checkIsAlertMessageVisible() {
        checkElementDisplayed(alertMessage);
        return this;
    }

    public HeaderLoginPage checkIsAvatarVisible(String enteredUsername) {  //  compare  text in input field with text in avatar
        String actualUsername = avatar.getText();
        Assert.assertEquals("Logged in username doesn't match displayed username", enteredUsername.toLowerCase(), actualUsername);
        checkElementDisplayed(avatar);
        return this;
    }

    public HeaderLoginPage pressTabKey() {
        new Actions(webDriver).sendKeys(Keys.TAB).perform();
        logger.info("Tab key pressed");
        return this;
    }

    public HeaderLoginPage pressEnterKey() {
        new Actions(webDriver).sendKeys(Keys.ENTER).perform();
        logger.info("Enter key pressed");
        return this;
    }

    public HeaderLoginPage checkTextInAlertMessage(String text) {
        Assert.assertEquals("Alert message text doesn't match expected text", text, alertMessage.getText());
        return this;
    }
}