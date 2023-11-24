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
    public void validLogin() { // test case 1
        pageProvider.HeaderLoginPage()
                .openLoginPage()
                .enterUsername(TestData.VALID_LOGIN)
                .enterPassword(TestData.VALID_PASSWORD)
                .clickOnLoginSignInButton()
                .checkIsAvatarVisible(TestData.VALID_LOGIN)
                .checkIsSignOutButtonVisible()
                .checkIsSignInButtonNotVisible();
    }


    @Test
    public void validLoginMinSymbols() { // test case 2
        pageProvider.HeaderLoginPage()
                .openLoginPage()
                .enterUsername(TestData.VALID_LOGIN_MIN_SIZE)
                .enterPassword(TestData.VALID_PASSWORD_MIN_SIZE)
                .clickOnLoginSignInButton()
                .checkIsAvatarVisible(TestData.VALID_LOGIN_MIN_SIZE)
                .checkIsSignOutButtonVisible()
                .checkIsSignInButtonNotVisible();
    }

    @Test
    public void validLoginMaxSymbols() { // test case 3
        pageProvider.HeaderLoginPage()
                .openLoginPage()
                .enterUsername(TestData.VALID_LOGIN_MAX_SIZE)
                .enterPassword(TestData.VALID_PASSWORD_MAX_SIZE)
                .clickOnLoginSignInButton()
                .checkIsAvatarVisible(TestData.VALID_LOGIN_MAX_SIZE)
                .checkIsSignOutButtonVisible()
                .checkIsSignInButtonNotVisible();
    }


    @Test
    public void validLoginWithKeyboard() {  // test case 4
        pageProvider.HeaderLoginPage()
                .openLoginPage()
                .enterUsername(TestData.VALID_LOGIN)
                .pressTabKey()
                .enterPassword(TestData.VALID_PASSWORD)
                .pressTabKey()
                .pressEnterKey()
                .checkIsAvatarVisible(TestData.VALID_LOGIN)
                .checkIsSignOutButtonVisible()
                .checkIsSignInButtonNotVisible();
    }

    @Test
    public void validLoginUpperCase() { // test case 5
        pageProvider.HeaderLoginPage()
                .openLoginPage()
                .enterUsername(TestData.VALID_LOGIN_UPPER_CASE)
                .enterPassword(TestData.VALID_PASSWORD_UPPER_CASE)
                .clickOnLoginSignInButton()
                .checkIsAvatarVisible(TestData.VALID_LOGIN_UPPER_CASE) // TODO bug here
                .checkIsSignOutButtonVisible()
                .checkIsSignInButtonNotVisible();
    }

    @Test
    public void InvalidLogin() {   // test case 6
        pageProvider.HeaderLoginPage()
                .openLoginPage()
                .enterUsername(TestData.INVALID_LOGIN)
                .enterPassword(TestData.INVALID_PASSWORD)
                .clickOnLoginSignInButton()
                .checkIsSignOutButtonNotVisible()
                .checkIsSignInButtonVisible()
                .checkIsAlertMessageVisible()
                .checkTextInAlertMessage("Invalid username / password"); // TODO bug here
    }

    @Test
    public void invalidLoginWithKeyboard() { // test case 7
        pageProvider.HeaderLoginPage()
                .openLoginPage()
                .enterUsername(TestData.INVALID_LOGIN_REVERSE)
                .pressTabKey()
                .enterPassword(TestData.INVALID_PASSWORD_REVERSE)
                .pressTabKey()
                .pressEnterKey()
                .checkIsSignOutButtonNotVisible()
                .checkIsSignInButtonVisible()
                .checkIsAlertMessageVisible()
                .checkTextInAlertMessage("Invalid username / password"); // TODO bug here
    }

    @Test
    public void invalidLoginWithEmptyFields() { // test case 8
        pageProvider.HeaderLoginPage()
                .openLoginPage()
                .enterUsername("")
                .enterPassword("")
                .clickOnLoginSignInButton()
                .checkIsSignOutButtonNotVisible()
                .checkIsSignInButtonVisible()
                .checkIsAlertMessageVisible()
                .checkTextInAlertMessage("Invalid username / password"); // TODO bug here
    }
}