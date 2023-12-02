package pages;

import org.junit.Assert;
import org.openqa.selenium.*;
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
    @FindBy(id = "username-register")
    private WebElement inputUserNameRegistration;

    @FindBy(id = "email-register")
    private WebElement inputEmailRegistration;

    @FindBy(id = "password-register")
    private WebElement inputPasswordRegistration;

    @FindBy(xpath = "//button[@class=\"py-3 mt-4 btn btn-lg btn-success btn-block\" and contains(text(), \"Sign up for OurApp\")]")
    private WebElement buttonRegistration;

    @FindBy(xpath = "//div[@class=\"alert alert-danger small liveValidateMessage liveValidateMessage--visible\" and contains(text(), \"This username is already taken.\")]")
    private WebElement alertMessageUsernameIsAlreadyTaken;

    @FindBy(xpath = "//div[contains(@class, 'alert') and contains(text(), 'Username must be at least 3 characters.')]")
    private WebElement alertMessageUsernameMustBeAtLeast3Characters;
    @FindBy(xpath = "//div[contains(@class, 'alert') and contains(text(), 'You must provide a valid email address.')]")
    private WebElement alertMessageYouMustProvideAValidEmailAddress;
    @FindBy(xpath = "//div[contains(@class, 'alert') and contains(text(), 'Password must be at least 12 characters.')]")
    private WebElement alertMessagePasswordMustBeAtLeast12Characters;

    public LoginPage(WebDriver webDriver) {
        super(webDriver);
    }

    public LoginPage openLoginPage() {
        openPage(BASE_URL);
        return this;
    }

    public enum color {
        WHITE("#ffffff"),
        BLUE("#cce5ff"),
        PINK("#f8d7da"),
        GRAY("#4444"),
        LiGHT_GRAY("#888"),
        LIGHT_BLUE("#80bdff"),
        NAVY_BLUE("#007bff");


        private String color;

        color(String color) {
            this.color = color;
        }

        public String getColor() {
            return color;
        }
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

    public LoginPage enterTextIntoInputUserNameRegistration(String userName) {
        enterTextIntoInput(inputUserNameRegistration, userName);
        return this;

    }

    public boolean isAlertMessageUsernameIsAlreadyTakenVisible() {
        return isElementDisplayed(alertMessageUsernameIsAlreadyTaken);
    }

    public LoginPage enterTextIntoInputEmailRegistration(String email) {
        enterTextIntoInput(inputEmailRegistration, email);
        return this;

    }

    public LoginPage enterTextIntoInputPasswordRegistration(String password) {
        enterTextIntoInput(inputPasswordRegistration, password);
        return this;
    }

    public HomePage clickOnButtonRegistration() {
        clickOnElement(buttonRegistration);
        return new HomePage(webDriver);
    }

    public LoginPage checkIsButtonRegistrationVisible() {
        checkElementDisplayed(buttonRegistration);
        return this;
    }

    public LoginPage checkBackgroundColorAlert(color backgroundColor) {
        String actualColor = getAlertMessageBackgroundColor();
        String expectedRGBColor = Color.fromString(backgroundColor.getColor()).asRgba();
        Assert.assertEquals("Alert message background color doesn't match expected color",
                expectedRGBColor, actualColor);
        return this;
    }


    public LoginPage checkIsAlertMessageUsernameCharactersNotVisible() {
        checkElementNotDisplayed(alertMessageUsernameMustBeAtLeast3Characters);
        return this;
    }

    public LoginPage checkIsAlertMessageEmailCharactersNotVisible() {
        checkElementNotDisplayed(alertMessageYouMustProvideAValidEmailAddress);
        return this;
    }

    public LoginPage checkIsAlertMessagePasswordCharactersNotVisible() {
        checkElementNotDisplayed(alertMessagePasswordMustBeAtLeast12Characters);
        return this;
    }

    public LoginPage checkAlertsInvalidRegistration() {
        checkIsAlertMessageUsernameCharactersNotVisible();
        checkIsAlertMessageEmailCharactersNotVisible();
        checkIsAlertMessagePasswordCharactersNotVisible();
        return this;
    }

    public String getColorPlaceholderUsername() {
        return usernameInput.getCssValue("background-color");
    }

    public String getColorPlaceholderPassword() {
        return passwordInput.getCssValue("background-color");
    }


    public LoginPage checkColorPlaceholderUsername(String color) { // compare color with color from enum
        checkElementDisplayed(usernameInput);
        String actualColor = getColorPlaceholderUsername();
        String expectedRGBColor = Color.fromString(color).asRgba();
        Assert.assertEquals("Username Placeholder color doesn't match expected color",
                expectedRGBColor, actualColor);
        return this;
    }

    public LoginPage checkColorPlaceholderPassword(String color) {
        checkElementDisplayed(passwordInput);
        String actualColor = getColorPlaceholderPassword();
        String expectedRGBColor = Color.fromString(color).asRgba();
        Assert.assertEquals(" Password Placeholder color doesn't match expected color",
                expectedRGBColor, actualColor);
        return this;
    }

    public LoginPage checkColorBorderUsername(String expectedColor) {  // convert string to color + .asRgb() to compare
        checkElementDisplayed(usernameInput);
        String actualColorString = getColorBorderOnFocusAfterClick();
        Color actualBorderColor = Color.fromString(actualColorString);
        Color expectedBorderColor = Color.fromString(expectedColor);

        String actualRGBBorderColor = actualBorderColor.asRgb();
        String expectedRGBBorderColor = expectedBorderColor.asRgb();

        Assert.assertEquals(" Border Username Placeholder color doesn't match expected color", expectedRGBBorderColor, actualRGBBorderColor);
        return this;
    }

    public String getColorBorderOnFocusAfterClick() { // u need click on element before for check border color
        clickOnElement(usernameInput);
        return usernameInput.getCssValue("border-color");
    }

    public LoginPage checkColorSignInButton(String color) {
        checkElementDisplayed(loginSignInButton);
        String actualColor = getColorSignInButton();
        String expectedRGBColor = Color.fromString(color).asRgba();
        Assert.assertEquals(" Sign in Placeholder color doesn't match expected color",
                expectedRGBColor, actualColor);
        return this;
    }

    public String getColorSignInButton() {
        return loginSignInButton.getCssValue("background-color");
    }

    public String getTextColorSignInButton() {
        return loginSignInButton.getCssValue("color");
    }

    public LoginPage checkTextColorSignInButton(String color) {
        checkElementDisplayed(loginSignInButton);
        String actualColor = getTextColorSignInButton();
        String expectedRGBColor = Color.fromString(color).asRgba();
        Assert.assertEquals("Text in Sign in Placeholder color doesn't match expected color",
                expectedRGBColor, actualColor);
        return this;

    }
}