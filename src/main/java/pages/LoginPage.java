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

    public LoginPage checkBackgroundColorAlert(ColorPalette.color colorEnum) {
        checkColorElement(alertMessage, colorEnum);
        return this;
    }

    public boolean checkErrorsMessages(String expectedMessages) {
        String[] errors = expectedMessages.split(";");

        List<String> actualTextFromErrors = new ArrayList<>(); //  // creating list to store actual error messages and elements with errors
        List<WebElement> errorElements;

        try {       // waiting for the expected number of error elements
            webDriverWait10.until(ExpectedConditions.numberOfElementsToBe(By.xpath(listErrorsMessagesLocator), errors.length));
            errorElements = getListOfErrors();
        } catch (TimeoutException e) {
            errorElements = new ArrayList<>();  // initializing  empty list if  we didn`t find errors
        }

        if (errorElements.isEmpty()) {
            // returning false if didn`t find errors
            return false;
        } else {
            // if we  found error collecting the text of each error
            for (int i = 0; i < errorElements.size(); i++) {
                actualTextFromErrors.add(errorElements.get(i).getText());
            }

            // Checking if we  found errors match the expected ones
            SoftAssertions softAssertions = new SoftAssertions();
            for (int i = 0; i < errors.length; i++) {
                String error = errors[i];
                String errorMessage = actualTextFromErrors.get(i);
                softAssertions.assertThat(error).as("Error " + i).isIn(errorMessage);
            }
            softAssertions.assertAll();

            return true; // return true if errors founded
        }
    }

    public LoginPage checkValidationAlertMessageNotPresent() {
        List<WebElement> errorElements = getListOfErrors();
        Assert.assertTrue("Error messages should not be present", errorElements.isEmpty());
        return this;
    }


    private List<WebElement> getListOfErrors() {
        return webDriver.findElements(By.xpath(listErrorsMessagesLocator));
    }

    public LoginPage checkColorPlaceholderUsername(ColorPalette.color colorEnum) {
        checkElementDisplayed(usernameInput);
        checkColorElement(usernameInput, colorEnum);
        return this;
    }

    public LoginPage checkColorPlaceholderPassword(ColorPalette.color colorEnum) {
        checkElementDisplayed(passwordInput);
        checkColorElement(passwordInput, colorEnum);
        return this;
    }

    public LoginPage checkColorBorderUsername(ColorPalette.color colorEnum) {
        checkElementDisplayed(usernameInput);
        checkColorBorderPlaceHolder(usernameInput, colorEnum);
        return this;

    }

    public LoginPage checkColorSignInButton(ColorPalette.color colorEnum) {
        checkElementDisplayed(loginSignInButton);
        checkColorElement(loginSignInButton, colorEnum);
        return this;
    }


    public LoginPage checkColorTextSignInButton(ColorPalette.color colorEnum) {
        checkTextColorElement(loginSignInButton, colorEnum);
        return this;
    }
}