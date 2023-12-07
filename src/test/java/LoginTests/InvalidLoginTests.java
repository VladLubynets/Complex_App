package LoginTests;


import BaseTest.BaseTest;
import TestData.TestData;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.Keys;

import static TestData.ColorPalette.PINK;

/**
 * Here we check valid invalid login from pull test cases  "Invalid Login Tests"
 * a link is provided in addition to the test case
 * <a href="https://docs.google.com/spreadsheets/d/1QBGwOHKht1vgdbQR60V9qX-9vX_bKq65/edit#gid=1698997352">
 */

@RunWith(JUnitParamsRunner.class)

public class InvalidLoginTests extends BaseTest {

    @Test
    @Parameters(method = "parametersForInvalidLoginTest")
    public void TC3_invalidLogin(String userName, String password) {
        pageProvider.getLoginPage()
                .openLoginPage()
                .enterUsername(userName)
                .enterPassword(password)
                .clickOnLoginSignInButton()
                .checkIsAlertMessageVisible()
                .checkTextInAlertMessage("Invalid username/password.")
                .checkBackgroundColorAlert(PINK);
    }

    public Object[][] parametersForInvalidLoginTest() {
        return new Object[][]{
                {TestData.VALID_LOGIN, TestData.INVALID_PASSWORD},
                {TestData.INVALID_LOGIN, TestData.VALID_PASSWORD}};
    }

    @Test
    @Parameters(method = "parametersForInvalidLoginTestByKeyboardButtons")
    public void TC4_invalidLogin_withKeyboard(String userName, String password) {
        pageProvider.getLoginPage()
                .openLoginPage()
                .enterLoginWithKey(userName, Keys.TAB)
                .enterPasswordWithKey(password, Keys.ENTER)
                .checkIsAlertMessageVisible()
                .checkTextInAlertMessage("Invalid username/password.")
                .checkBackgroundColorAlert(PINK);
    }

    public Object[][] parametersForInvalidLoginTestByKeyboardButtons() {
        return new Object[][]{
                {TestData.INVALID_LOGIN_REVERSE, TestData.INVALID_PASSWORD_REVERSE},
                {TestData.INVALID_LOGIN_EMPTY, TestData.INVALID_PASSWORD_EMPTY}};
    }
}
