package LoginTests.LoginTest;


import BaseTest.BaseTest;
import TestData.TestData;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.Keys;

import static TestData.ColorPalette.PINK;
import static TestData.TestData.*;

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
                {VALID_LOGIN, INVALID_PASSWORD},
                {INVALID_LOGIN, VALID_PASSWORD}};
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
                {INVALID_LOGIN_REVERSE, INVALID_PASSWORD_REVERSE},
                {INVALID_LOGIN_EMPTY, INVALID_PASSWORD_EMPTY}};
    }
}