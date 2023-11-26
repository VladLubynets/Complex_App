package InvalidLoginTestsTC2;

import BaseTest.BaseTest;
import TestData.TestData;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Here we check valid invalid login from pull test cases  "Invalid Login Tests"
 * a link is provided in addition to the test case
 * <a href="https://docs.google.com/spreadsheets/d/1QBGwOHKht1vgdbQR60V9qX-9vX_bKq65/edit#gid=1698997352">
 */

@RunWith(JUnitParamsRunner.class)

public class InvalidLoginTests extends BaseTest {

    @Test
    @Parameters(method = "parametersForInvalidLoginTest")
    public void parametersForInvalidLoginTestTC3(String userName, String password) {
        pageProvider.LoginPage()
                .openLoginPage()
                .enterUsername(userName)
                .enterPassword(password)
                .clickOnLoginSignInButton()
                .checkIsAlertMessageVisible()
                .checkTextInAlertMessage("Invalid username / password") //ToDo: Bug in text
                .checkBackgroundColor("#f8d7da");
    }

    public Object[][] parametersForInvalidLoginTest() {
        return new Object[][]{
                {TestData.VALID_LOGIN, TestData.INVALID_PASSWORD},
                {TestData.INVALID_LOGIN, TestData.VALID_PASSWORD}};
    }

    @Test
    @Parameters(method = "parametersForInvalidLoginTestByKeyboardButtons")
    public void parametersForInvalidLoginTestByKeyboardButtonsTC4(String userName, String password) {
        pageProvider.LoginPage()
                .openLoginPage()
                .enterUsernameWithTab(userName)
                .enterPasswordWithEnter(password)
                .checkIsAlertMessageVisible()
                .checkTextInAlertMessage("Invalid username / password") //ToDo: Bug in text
                .checkBackgroundColor("#f8d7da");
    }

    public Object[][] parametersForInvalidLoginTestByKeyboardButtons() {
        return new Object[][]{
                {TestData.INVALID_LOGIN_REVERSE, TestData.INVALID_PASSWORD_REVERSE},
                {TestData.INVALID_LOGIN_EMPTY, TestData.INVALID_PASSWORD_EMPTY}};
    }
}
