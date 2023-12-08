package SaveDataTests;

import BaseTest.BaseTest;
import TestData.TestData;
import org.junit.Test;


public class SaveDataTests extends BaseTest {

    @Test
    public void TC7_SaveDataWorkWithWindow() {
        pageProvider.getLoginPage().openLoginPage()
                .enterUsername(TestData.VALID_LOGIN)
                .enterPassword(TestData.VALID_PASSWORD);
        BaseTest.resizeBrowserWindow(500, 500);
        pageProvider.getLoginPage()
                .checkIsUsernameInputHasText(TestData.VALID_LOGIN);
        BaseTest.minimizeBrowserWindow();
        BaseTest.maximizeBrowserWindow();
        pageProvider.getLoginPage()
                .clickOnLoginSignInButton();
        pageProvider.getHomePage().getHeader().checkIsSignOutButtonVisible();

    }

    @Test
    public void TC8_SaveDataRefreshPage() {
        pageProvider.getLoginPage().openLoginPage()
                .enterUsername(TestData.VALID_LOGIN)
                .enterPassword(TestData.VALID_PASSWORD);
        BaseTest.refreshPage();
        pageProvider.getLoginPage()
                .checkIsUsernameInputEmpty()
                .checkIsPasswordInputEmpty();
    }
}
