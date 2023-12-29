package LoginTests.LoginTest;

import BaseTest.BaseTest;
import TestData.TestData;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.Keys;

import static TestData.TestData.*;


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
        pageProvider.getLoginPage().openLoginPage().enterTextIntoInputUserNameRegistration(userName)
                .registrationUserIfNeeded(userName, Password, TestData.generateRandomEmail()); // if user is not registered
        pageProvider.getLoginPage()
                .enterUsername(userName)
                .enterPassword(Password)
                .clickOnLoginSignInButton()
                .checkIsSignInButtonNotVisible();
        pageProvider.getHomePage().getHeader().checkIsSignOutButtonVisible();
    }

    public Object[][] parametersForLoginTest() {
        return new Object[][]{
                {VALID_LOGIN, VALID_PASSWORD},
                {VALID_LOGIN_MIN_SIZE, VALID_PASSWORD_MIN_SIZE},
                {VALID_LOGIN_MAX_SIZE, VALID_PASSWORD_MAX_SIZE}
                };
    }

    @Test
    @Parameters(method = "parametersForLoginTestByKeyboardButtons")
    public void TC2_validLogin_withKeyboard(String userName, String password) {
        pageProvider.getLoginPage()
                .openLoginPage()
                .enterLoginWithKey(userName, Keys.TAB)
                .enterPasswordWithKey(password, Keys.TAB)
                .pressEnterKeyOnFocusSignIn()
                .checkIsSignInButtonNotVisible();
        pageProvider.getHomePage().getHeader()
                .checkIsAvatarVisibleAndCheckTextInAvatar(userName)
                .checkIsSignOutButtonVisible();
    }

    public Object[][] parametersForLoginTestByKeyboardButtons() {
        return new Object[][]{

                {VALID_LOGIN, VALID_PASSWORD},
                {VALID_LOGIN_UPPER_CASE, VALID_PASSWORD}};

    }

    }
