package LoginTests;

import BaseTest.BaseTest;
import TestData.TestData;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.Keys;

/**
 * Here we check valid login from pull test cases  "Validation of user input"
 * a link is provided in addition to the test case
 * <a href="https://docs.google.com/spreadsheets/d/1QBGwOHKht1vgdbQR60V9qX-9vX_bKq65/edit#gid=1698997352">
 */

@RunWith(JUnitParamsRunner.class)

public class ValidLoginTests extends BaseTest {


    @Test
    @Parameters(method = "parametersForLoginTest")

    public void TC1_validLogin(String userName, String Password) {
        pageProvider.getLoginPage().openLoginPage().enterTextIntoInputUserNameRegistration(userName);
        if (pageProvider.getLoginPage().checkErrorsMessages("This username is already taken.")) {
            pageProvider.getLoginPage()
                    .openLoginPage()
                    .enterUsername(userName)
                    .enterPassword(Password)
                    .clickOnLoginSignInButton()
                    .checkIsSignInButtonNotVisible();
            pageProvider.getHomePage().getHeader()
                    .checkIsAvatarVisibleAndCheckTextInAvatar(userName)
                    .checkIsSignOutButtonVisible();
        } else {
            pageProvider.getLoginPage().openLoginPage()
                    .enterTextIntoInputUserNameRegistration(userName)
                    .enterTextIntoInputEmailRegistration(TestData.generateRandomEmail())
                    .enterTextIntoInputPasswordRegistration(Password)
                    .checkValidationAlertMessageNotPresent()
                    .checkIsButtonRegistrationVisible()
                    .clickOnButtonRegistration();
            pageProvider.getHomePage().getHeader().checkIsSignOutButtonVisible()
                    .clickOnButtonSignOut();
        }
    }


    public Object[][] parametersForLoginTest() {
        return new Object[][]{
                {TestData.VALID_LOGIN, TestData.VALID_PASSWORD},
                {TestData.VALID_LOGIN_MIN_SIZE, TestData.VALID_PASSWORD_MIN_SIZE},
                {TestData.VALID_LOGIN_MAX_SIZE, TestData.VALID_PASSWORD_MAX_SIZE}};
    }

    @Test
    @Ignore
    @Parameters(method = "parametersForLoginTestByKeyboardButtons")
    public void TC2_validLogin_withKeyboard(String userName, String password) {
        pageProvider.getLoginPage()
                .openLoginPage()
                .enterLoginWithKey(userName, Keys.TAB)
                .enterPasswordWithKey(password, Keys.ENTER)
                .checkIsSignInButtonNotVisible();
        pageProvider.getHomePage().getHeader()
                .checkIsAvatarVisibleAndCheckTextInAvatar(userName)
                .checkIsSignOutButtonVisible();
    }

    public Object[][] parametersForLoginTestByKeyboardButtons() {
        return new Object[][]{

                {TestData.VALID_LOGIN, TestData.VALID_PASSWORD},
                {TestData.VALID_LOGIN_UPPER_CASE, TestData.VALID_PASSWORD_UPPER_CASE}}; // TODo bug in upper case denied entry
    }
}

