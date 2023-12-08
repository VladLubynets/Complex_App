package LoginTests.SaveDataTests;

import BaseTest.BaseTest;

import static TestData.TestData.*;

import org.junit.Test;
import pages.LoginPage;


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
