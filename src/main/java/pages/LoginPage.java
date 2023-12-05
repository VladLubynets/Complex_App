package pages;

import TestData.ColorPalette;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class LoginPage extends ParentPage {

    @FindBy(xpath = "//input[@placeholder='Username']")
    private WebElement usernameInput;

    @FindBy(xpath = "//input[@placeholder='Password']")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[@class='btn btn-primary btn-sm']")
    private WebElement loginSignInButton;
    @FindBy(id = "username-register")
    private WebElement inputUserNameRegistration;

    @FindBy(id = "email-register")
    private WebElement inputEmailRegistration;

    @FindBy(id = "password-register")
    private WebElement inputPasswordRegistration;

    @FindBy(xpath = "//button[@class=\"py-3 mt-4 btn btn-lg btn-success btn-block\" and contains(text(), \"Sign up for OurApp\")]")
    private WebElement buttonRegistration;


    @FindBy(xpath = "//div[@class=\"alert alert-danger text-center\"]")
    private WebElement alertMessage;

    final String listErrorsMessagesLocator = ".//*[@class='alert alert-danger small liveValidateMessage liveValidateMessage--visible']";
    PageProvider pageProvider = new PageProvider(webDriver);

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


    public LoginPage enterTextIntoInputUserNameRegistration(String userName) {
        enterTextIntoInput(inputUserNameRegistration, userName);
        return this;

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

    public LoginPage checkBackgroundColorAlert(ColorPalette colorEnum) {
        checkColorElement(alertMessage, colorEnum);
        return this;
    }

    public boolean checkErrorsMessages(String expectedMessages) {
        String[] errors = expectedMessages.split(";");
        webDriverWait10.until(ExpectedConditions.numberOfElementsToBe(By.xpath(listErrorsMessagesLocator), errors.length));
        ArrayList<String> actualTextFromErrors = new ArrayList<>(); // creating list  actual error messages and elements with errors
        List<WebElement> errorElements = getListOfErrors();

        for (int i = 0; i < errorElements.size(); i++) {
            actualTextFromErrors.add(errorElements.get(i).getText()); // adding text from error elements to list
        }

        SoftAssertions softAssertions = new SoftAssertions();  // Checking actual error messages against expected ones
        for (int i = 0; i < errors.length; i++) {
            String error = errors[i];
            String errorMessage = actualTextFromErrors.get(i);
            softAssertions.assertThat(error).as("Error " + i).isIn(errorMessage);
        }
        softAssertions.assertAll();

        return !errorElements.isEmpty();  // if list  not empty - true if list empty - false
    }

    public LoginPage checkValidationAlertMessageNotPresent() {
        List<WebElement> errorElements = getListOfErrors();
        Assert.assertTrue("Error messages should not be present", errorElements.isEmpty());
        return this;
    }

    public boolean isMessageWithTextDisplayed(String expectedText) {
        List<WebElement> errorElements = getListOfErrors();

        for (int i = 0; i < errorElements.size(); i++) {
            WebElement errorElement = errorElements.get(i);
            String errorMessage = errorElement.getText();
            if (errorMessage.contains(expectedText)) {
                return true;
            }
        }
        return false;
    }


    private List<WebElement> getListOfErrors() {
        return webDriver.findElements(By.xpath(listErrorsMessagesLocator));
    }

    public LoginPage checkColorPlaceholderUsername(ColorPalette colorEnum) {
        checkElementDisplayed(usernameInput);
        checkColorElement(usernameInput, colorEnum);
        return this;
    }

    public LoginPage checkColorPlaceholderPassword(ColorPalette colorEnum) {
        checkElementDisplayed(passwordInput);
        checkColorElement(passwordInput, colorEnum);
        return this;
    }

    public LoginPage checkColorBorderUsername(ColorPalette colorEnum) {
        checkElementDisplayed(usernameInput);
        checkColorBorderPlaceHolder(usernameInput, colorEnum);
        return this;

    }

    public LoginPage checkColorSignInButton(ColorPalette colorEnum) {
        checkElementDisplayed(loginSignInButton);
        checkColorElement(loginSignInButton, colorEnum);
        return this;
    }


    public LoginPage checkColorTextSignInButton(ColorPalette colorEnum) {
        checkTextColorElement(loginSignInButton, colorEnum);
        return this;
    }

    public void registrationUserIfNeeded(String userName, String password, String email) {
        boolean errorFound = false;
        try {
            errorFound = isMessageWithTextDisplayed("This username is already taken.");
        } catch (TimeoutException e) {

        }
        if (!errorFound) { // if error not found - do registration

            openLoginPage();
            enterTextIntoInputUserNameRegistration(userName);
            enterTextIntoInputEmailRegistration(email);
            enterTextIntoInputPasswordRegistration(password);
            checkValidationAlertMessageNotPresent();
            checkIsButtonRegistrationVisible();
            clickOnButtonRegistration();
            checkIsSignInButtonNotVisible();
            pageProvider.getHomePage().getHeader().clickOnButtonSignOut();

        }
    }
}