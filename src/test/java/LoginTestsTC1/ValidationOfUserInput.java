package LoginTestsTC1;

import BaseTest.BaseTest;
import TestData.TestData;
import org.junit.Test;

/**
 * Here we check valid login from pull test cases  "Validation of user input"
 * a link is provided in addition to the test case
 * <a href="https://docs.google.com/spreadsheets/d/1_xwqAD2uBCE5PedcIXOJinGt1QVhdZRI/edit?usp=sharing&ouid=111448493186981618140&rtpof=true&sd=true">
 */

public class ValidationOfUserInput extends BaseTest {

    @Test
    public void validLoginTC1() {
        pageProvider.LoginPage()
                .openLoginPage()
                .enterUsername(TestData.VALID_LOGIN)
                .enterPassword(TestData.VALID_PASSWORD)
                .clickOnLoginSignInButton()
                .checkIsSignInButtonNotVisible();
        pageProvider.Header()
                .checkIsAvatarVisibleAndCheckTextInAvatar(TestData.VALID_LOGIN)
                .checkIsSignOutButtonVisible();

    }


    @Test
    public void validLoginMinSymbolsTC2() {
        pageProvider.LoginPage()
                .openLoginPage()
                .enterUsername(TestData.VALID_LOGIN_MIN_SIZE)
                .enterPassword(TestData.VALID_PASSWORD_MIN_SIZE)
                .clickOnLoginSignInButton()
                .checkIsSignInButtonNotVisible();
        pageProvider.Header().checkIsAvatarVisibleAndCheckTextInAvatar(TestData.VALID_LOGIN_MIN_SIZE)
                .checkIsSignOutButtonVisible();

    }

    @Test
    public void validLoginMaxSymbolsTC3() {
        pageProvider.LoginPage()
                .openLoginPage()
                .enterUsername(TestData.VALID_LOGIN_MAX_SIZE)
                .enterPassword(TestData.VALID_PASSWORD_MAX_SIZE)
                .clickOnLoginSignInButton()
                .checkIsSignInButtonNotVisible();
        pageProvider.Header().checkIsAvatarVisibleAndCheckTextInAvatar(TestData.VALID_LOGIN_MAX_SIZE)
                .checkIsSignOutButtonVisible();

    }


    @Test
    public void validLoginWithKeyboardTC4() {
        pageProvider.LoginPage()
                .openLoginPage()
                .enterUsernameWithTab(TestData.VALID_LOGIN)
                .enterPasswordWithEnter(TestData.VALID_PASSWORD)
                .checkIsSignInButtonNotVisible();
        pageProvider.Header().checkIsAvatarVisibleAndCheckTextInAvatar(TestData.VALID_LOGIN)
                .checkIsSignOutButtonVisible();

    }

    @Test
    public void validLoginUpperCaseTC5() {
        pageProvider.LoginPage()
                .openLoginPage()
                .enterUsername(TestData.VALID_LOGIN_UPPER_CASE)
                .enterPassword(TestData.VALID_PASSWORD_UPPER_CASE)
                .clickOnLoginSignInButton();
        pageProvider.Header()
                .checkIsAvatarVisibleAndCheckTextInAvatar(TestData.VALID_LOGIN_UPPER_CASE)  // TODO bug here
                .checkIsSignOutButtonVisible();
        pageProvider.LoginPage().checkIsSignInButtonNotVisible();

    }

    @Test
    public void InvalidLoginTC6() {
        pageProvider.LoginPage()
                .openLoginPage()
                .enterUsername(TestData.INVALID_LOGIN)
                .enterPassword(TestData.INVALID_PASSWORD)
                .clickOnLoginSignInButton()
                .checkIsSignInButtonVisible()
                .checkIsAlertMessageVisible()
                .checkTextInAlertMessage("Invalid username / password"); // TODO bug here
        pageProvider.Header().checkIsSignOutButtonNotVisible();

    }

    @Test
    public void invalidLoginWithKeyboardTC7() {
        pageProvider.LoginPage()
                .openLoginPage()
                .enterUsernameWithTab(TestData.INVALID_LOGIN_REVERSE)
                .enterPasswordWithEnter(TestData.INVALID_PASSWORD_REVERSE)
                .checkIsSignInButtonVisible()
                .checkIsAlertMessageVisible()
                .checkTextInAlertMessage("Invalid username / password"); // TODO bug here
        pageProvider.Header().checkIsSignOutButtonNotVisible();

    }

    @Test
    public void invalidLoginWithEmptyFieldsTC8() {
        pageProvider.LoginPage()
                .openLoginPage()
                .enterUsername("")
                .enterPassword("")
                .clickOnLoginSignInButton()
                .checkIsSignInButtonVisible()
                .checkIsAlertMessageVisible()
                .checkTextInAlertMessage("Invalid username / password"); // TODO bug here
        pageProvider.Header().checkIsSignOutButtonNotVisible();

    }
}