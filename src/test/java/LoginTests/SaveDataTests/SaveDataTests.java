package LoginTests.SaveDataTests;

import BaseTest.BaseTest;

import static TestData.TestData.*;

import org.junit.Test;
import pages.LoginPage;

/**
 * Here we check Updating the page and cleaning the fields on pull test cases  "SaveDataTests"
 * a link is provided in addition to the test case
 * <a href="https://docs.google.com/spreadsheets/d/1QBGwOHKht1vgdbQR60V9qX-9vX_bKq65/edit#gid=1698997352">
 */

public class SaveDataTests extends BaseTest {

    @Test
    public void TC7_SaveDataWorkWithWindow() {
        LoginPage loginPage = pageProvider.getLoginPage();
        loginPage.fillingFormWithValidCredentials();
        loginPage.resizeBrowserWindow(500, 500); // 500, 500 - size of window
        loginPage.checkIsUsernameInputHasText(VALID_LOGIN);
        loginPage.minimizeBrowserWindow();
        loginPage.maximizeBrowserWindow();
        loginPage.clickOnLoginSignInButton();
        pageProvider.getHomePage().getHeader().checkIsSignOutButtonVisible();

    }

    @Test
    public void TC8_SaveDataRefreshPage() {
        LoginPage loginPage = pageProvider.getLoginPage();
        loginPage.openLoginPage()
                .fillingFormWithValidCredentials();
        loginPage.refreshPage();
        loginPage.checkIsUsernameInputEmpty()
                .checkIsPasswordInputEmpty();
    }
}
