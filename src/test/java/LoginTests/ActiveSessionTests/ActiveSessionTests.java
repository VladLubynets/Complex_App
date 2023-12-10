package LoginTests.ActiveSessionTests;

import BaseTest.BaseTest;
import org.junit.Test;
import pages.HomePage;
import pages.LoginPage;

/**
 * Here we check Working with a  active session on pull test cases  "ActiveSessionTests"
 * a link is provided in addition to the test case
 * <a href="https://docs.google.com/spreadsheets/d/1QBGwOHKht1vgdbQR60V9qX-9vX_bKq65/edit#gid=1698997352">
 */

public class ActiveSessionTests extends BaseTest {

    @Test
    public void TC9_ExtencionOfActiveSession() {
        LoginPage loginPage = pageProvider.getLoginPage();
        HomePage homePage = pageProvider.getHomePage();

        loginPage.openLoginPage().loginWithValidCred();
        homePage.getHeader().checkIsMyProfileButtonVisible();

        loginPage.WaitFor15Minutes();

        homePage.getHeader().clickOnMyProfileButton();
        homePage.checkIsButtonProfileFollowersVisible()
                .checkIsButtonProfilePostsVisible()
                .checkIsButtonProfileFollowingVisible();

        loginPage.WaitFor15Minutes();

        homePage.getHeader().clickOnMyProfileButton();

        loginPage.checkIsAlertMessageNotVisible(); //TODO Bug - Alert message is visible session is expired
    }

    @Test
    public void TC10_ActiveSessionAfterLogin() {
        LoginPage loginPage = pageProvider.getLoginPage();
        HomePage homePage = pageProvider.getHomePage();

        loginPage.openLoginPage().loginWithValidCred();
        homePage.getHeader().checkIsMyProfileButtonVisible();

        loginPage.WaitFor30Minutes();

        homePage.getHeader().clickOnMyProfileButton();
        loginPage.checkIsAlertMessageVisible()
                .checkTextInAlertMessage("You must be logged in to perform that action.");
    }

    @Test
    public void TC11_ActiveSessionInNewTab() {
        LoginPage loginPage = pageProvider.getLoginPage();

        loginPage.openLoginPage().loginWithValidCred()
                .openNewTabAndSwitchToIt();
        loginPage.checkIsSignInButtonNotVisible();


    }

}
