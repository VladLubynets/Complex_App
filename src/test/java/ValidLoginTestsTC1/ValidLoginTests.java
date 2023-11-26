package ValidLoginTestsTC1;

import BaseTest.BaseTest;
import TestData.TestData;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Here we check valid login from pull test cases  "Validation of user input"
 * a link is provided in addition to the test case
 * <a href="https://docs.google.com/spreadsheets/d/1QBGwOHKht1vgdbQR60V9qX-9vX_bKq65/edit#gid=1698997352">
 */

@RunWith(JUnitParamsRunner.class)

public class ValidLoginTests extends BaseTest {

    @Test
    @Parameters(method = "parametersForLoginTest")
    public void parametersForLoginTestTC1(String userName, String password) {
        pageProvider.LoginPage()
                .openLoginPage()
                .enterUsername(userName)
                .enterPassword(password)
                .clickOnLoginSignInButton()
                .checkIsSignInButtonNotVisible();
        pageProvider.Header()
                .checkIsAvatarVisibleAndCheckTextInAvatar(userName)
                .checkIsSignOutButtonVisible();
    }

    public Object[][] parametersForLoginTest() {
        return new Object[][]{
                {TestData.VALID_LOGIN, TestData.VALID_PASSWORD},
                {TestData.VALID_LOGIN_MIN_SIZE, TestData.VALID_PASSWORD_MIN_SIZE},
                {TestData.VALID_LOGIN_MAX_SIZE, TestData.VALID_PASSWORD_MAX_SIZE}};
    }

    @Test
    @Parameters(method = "parametersForLoginTestByKeyboardButtons")
    public void parametersForLoginTestByKeyboardButtonsTC2(String userName, String password) {
        pageProvider.LoginPage()
                .openLoginPage()
                .enterUsernameWithTab(userName)
                .enterPasswordWithEnter(password)
                .checkIsSignInButtonNotVisible();
        pageProvider.Header()
                .checkIsAvatarVisibleAndCheckTextInAvatar(userName)
                .checkIsSignOutButtonVisible();
    }

    public Object[][] parametersForLoginTestByKeyboardButtons() {
        return new Object[][]{
                {TestData.VALID_LOGIN, TestData.VALID_PASSWORD},
                {TestData.VALID_LOGIN_UPPER_CASE, TestData.VALID_PASSWORD_UPPER_CASE}}; // ToDo: Bug in Upper Case
    }
}

