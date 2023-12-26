package LoginTests.ActiveSessionTests;

import BaseTest.BaseTest;
import org.junit.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.PostPage;

/**
 * Here we check Working with an  active session on pull test cases  "ActiveSessionTests"
 * a link is provided in addition to the test case
 * <a href="https://docs.google.com/spreadsheets/d/1QBGwOHKht1vgdbQR60V9qX-9vX_bKq65/edit#gid=1698997352">
 */

public class ActiveSessionTests extends BaseTest {
    private String title;
    private String body;
    private PostPage postPage;

    @Test
    public void TC9_ExtencionOfActiveSession() {
        LoginPage loginPage = pageProvider.getLoginPage();
        HomePage homePage = pageProvider.getHomePage();

        loginPage.openLoginPage().loginWithValidCred();
        homePage.getHeader().checkIsMyProfileButtonVisible();

        loginPage.waitForMinutes(15);

        homePage.getHeader().clickOnMyProfileButton();
        homePage.checkIsButtonProfileFollowersVisible()
                .checkIsButtonProfilePostsVisible()
                .checkIsButtonProfileFollowingVisible();

        loginPage.waitForMinutes(15);

        homePage.getHeader().clickOnMyProfileButton();

        loginPage.checkIsAlertMessageVisible().checkTextInAlertMessage("You must be logged in to perform that action.");
    }

    @Test
    public void TC10_ActiveSessionAfterLogin() {
        LoginPage loginPage = pageProvider.getLoginPage();
        HomePage homePage = pageProvider.getHomePage();

        loginPage.openLoginPage().loginWithValidCred();
        homePage.getHeader().checkIsMyProfileButtonVisible();

        loginPage.waitForMinutes(30);

        homePage.getHeader().clickOnMyProfileButton();
        loginPage.checkIsAlertMessageVisible()
                .checkTextInAlertMessage("You must be logget in to perform that action."); //TODO You must be logged in to perform that action.
    }

    @Test
    public void TC11_ActiveSessionInNewTab() {
        LoginPage loginPage = pageProvider.getLoginPage();

        loginPage.openLoginPage().loginWithValidCred()
                .openNewTabAndSwitchToIt(loginPage.BASE_URL);
        loginPage.checkIsSignInButtonNotVisible();

    }

    @Test
    public void TC12_ExtencionActiveSessionCreatePost() {
        LoginPage loginPage = pageProvider.getLoginPage();
        postPage = new PostPage(webDriver);
        title = "Test_Title";
        body = "Test_Body";

        loginPage.openLoginPage().loginWithValidCred();

       loginPage.waitForMinutes(15);

        pageProvider.getHomePage().getHeader().clickOnCreatePostButton();
        pageProvider.getCreatePostPage().enterTextIntoInputTitle(title);
        pageProvider.getCreatePostPage().enterTextIntoInputBody(body);
        pageProvider.getCreatePostPage().clickOnButtonSavePost();
        postPage.checkTextInSuccessMessage("New post successfully created.");
        loginPage.waitForMinutes(15);



        pageProvider.getHomePage().getHeader().clickOnMyProfileButton();
        pageProvider.getHomePage().checkPostWithTitleIsPresent(title);

        pageProvider.getHomePage()
                .getHeader().clickOnMyProfileButton()
                .deletePostTillPresent(title);
    }
}
