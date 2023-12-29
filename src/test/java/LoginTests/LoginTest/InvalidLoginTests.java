package LoginTests.LoginTest;


import BaseTest.BaseTest;
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
                .enterPasswordWithKey(password,  Keys.TAB)
                .pressEnterKeyOnFocusSignIn()
                .checkIsAlertMessageVisible()
                .checkTextInAlertMessage("Invalid username  pasword") // TODO Invalid username/password.
                .checkBackgroundColorAlert(PINK)
                .refreshPage();
        pageProvider.getLoginPage().checkIsAlertMessageNotVisible();
    }

    public Object[][] parametersForInvalidLoginTestByKeyboardButtons() {
        return new Object[][]{
                {VALID_PASSWORD, VALID_LOGIN},
                {EMPTY_VALUE, EMPTY_VALUE}};
    }
}
