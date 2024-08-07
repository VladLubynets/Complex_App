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
    public void TC601_NonExtencionOfActiveSessionForMyProfilePage() {
        LoginPage loginPage = pageProvider.getLoginPage();
        HomePage homePage = pageProvider.getHomePage();

        loginPage.openLoginPage().loginWithValidCred();
        homePage.getHeader().checkIsMyProfileButtonVisible();

        loginPage.waitForMinutes(15);

        homePage.getHeader().clickOnMyProfileButton();
        pageProvider.getFollowingPage().checkIsButtonProfileFollowersVisible()

                .checkIsButtonProfilePostsVisible()
                .checkIsButtonProfileFollowingVisible();

        loginPage.waitForMinutes(15);

        homePage.getHeader().clickOnMyProfileButton();

        loginPage.checkIsAlertMessageVisible().checkTextInAlertMessage("You must be logged in to perform that action.");
    }

    @Test
    public void TC602_ActiveSessionAfterLogin() {
        LoginPage loginPage = pageProvider.getLoginPage();
        HomePage homePage = pageProvider.getHomePage();

        loginPage.openLoginPage().loginWithValidCred();
        homePage.getHeader().checkIsMyProfileButtonVisible();

        loginPage.waitForMinutes(30);

        homePage.getHeader().clickOnMyProfileButton();
        loginPage.checkIsAlertMessageVisible()
                .checkTextInAlertMessage("You must be logged in to perform that action.");
    }

    @Test
    public void TC603_ActiveSessionInNewTab() {
        LoginPage loginPage = pageProvider.getLoginPage();

        loginPage.openLoginPage().loginWithValidCred()
                .openNewTabAndSwitchToIt(loginPage.BASE_URL);
        loginPage.checkIsSignInButtonNotVisible();

    }

    @Test
    public void TC604_ExtencionActiveSessionCreatePost() {
        LoginPage loginPage = pageProvider.getLoginPage();
        postPage = new PostPage(webDriver);
        title = "Session_Title";
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
        pageProvider.getMyProfilePage().checkNumberOfPostWithTitleIsPresent(title, 1);

        pageProvider.getHomePage()
                .getHeader().clickOnMyProfileButton();
        pageProvider.getPostPage().deletePostTillPresent(title);
    }
}
