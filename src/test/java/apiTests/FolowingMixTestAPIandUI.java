package apiTests;

import TestData.TestData;
import api.ApiHelper;
import BaseTest.BaseTest;
import org.junit.Test;

import static TestData.TestData.*;


public class FolowingMixTestAPIandUI extends BaseTest {
    ApiHelper apiHelper = new ApiHelper();
    String loginFirst = TestData.generateRandomString(10);
    String passwordFirst = TestData.generateRandomString(12);

    String loginSecond = TestData.generateRandomString(10);
    String passwordSecond = TestData.generateRandomString(12);

    String followLogin = TestData.generateRandomString(10);
    String followPassword = TestData.generateRandomString(12);

    String followingLogin = TestData.generateRandomString(10);
    String followingPassword = TestData.generateRandomString(12);

    String followLoginUrl = String.format(USER_URL, followLogin);
    String followingLoginUrl = String.format(USER_URL, followingLogin);

    String secondUserUrl = String.format(USER_URL, loginSecond);
    String firstUserUrl = String.format(USER_URL, loginFirst);


    @Test
    public void TC14_followingTestApiAndUi() {
        pageProvider.getLoginPage().registrationOfUser(loginFirst, passwordFirst, TestData.generateRandomEmail());
        pageProvider.getLoginPage().registrationOfUser(loginSecond, passwordSecond, TestData.generateRandomEmail());
        pageProvider.getLoginPage().loginWithValidCred(loginFirst, passwordFirst);
        pageProvider.getHomePage().openNewTabAndSwitchToIt(secondUserUrl);
        pageProvider.getFollowingPage().clickOnButtonFollow();
        pageProvider.getFollowingPage().checkIsSuccessMessageVisible();
        pageProvider.getFollowingPage().checkTextInSuccessMessage("Successfully followed " + loginSecond + ".");
        pageProvider.getHomePage().refreshPage();


        pageProvider.getFollowingPage().checkIsButtonFollowersVisible();
        pageProvider.getFollowingPage().clickOnButtonFollowers();
        pageProvider.getFollowingPage().checkIsCountOfFollowers(1);

        pageProvider.getHomePage().openNewTabAndSwitchToIt(firstUserUrl);
        pageProvider.getFollowingPage().clickOnButtonFollowing();
        pageProvider.getFollowingPage().checkIsCountOfFollowing(1);

        pageProvider.getHomePage().getHeader().clickOnButtonSignOut();


        pageProvider.getLoginPage().loginWithValidCred(loginSecond, passwordSecond);
        pageProvider.getHomePage().openNewTabAndSwitchToIt(firstUserUrl);


        pageProvider.getFollowingPage().clickOnButtonFollowing();
        pageProvider.getFollowingPage().checkIsCountOfFollowing(1);

        pageProvider.getHomePage().getHeader().clickOnButtonSignOut();

        pageProvider.getLoginPage().loginWithValidCred(loginFirst, passwordFirst);
        pageProvider.getHomePage().openNewTabAndSwitchToIt(secondUserUrl);
        pageProvider.getFollowingPage().clickOnButtonStopFollow();
        pageProvider.getFollowingPage().checkIsSuccessMessageVisible();
        pageProvider.getFollowingPage().checkTextInSuccessMessage("Successfully stopped following " + loginSecond + ".");

        pageProvider.getHomePage().refreshPage();
        pageProvider.getFollowingPage().clickOnButtonFollowers();
        pageProvider.getFollowingPage().checkIsCountOfFollowers(0);

        pageProvider.getHomePage().getHeader().clickOnButtonSignOut();

        pageProvider.getLoginPage().loginWithValidCred(loginSecond, passwordSecond);
        pageProvider.getHomePage().openNewTabAndSwitchToIt(firstUserUrl);
        pageProvider.getFollowingPage().clickOnButtonFollowing();
        pageProvider.getFollowingPage().checkIsCountOfFollowing(0);
    }

    @Test
    public void TC15_InvalidFollowingTestByUI() {
        pageProvider.getLoginPage().registrationOfUser(followLogin, followPassword, TestData.generateRandomEmail());
        pageProvider.getLoginPage().registrationOfUser(followingLogin, followingPassword, TestData.generateRandomEmail());
        pageProvider.getLoginPage().loginWithValidCred(followLogin, followPassword);
        pageProvider.getHomePage().openNewTabAndSwitchToIt(followingLoginUrl);
        pageProvider.getHomePage().openNewTabAndSwitchToIt(followingLoginUrl);
        pageProvider.getHomePage().switchToPreviousTab();
        pageProvider.getFollowingPage().clickOnButtonFollow();
        pageProvider.getFollowingPage().checkIsSuccessMessageVisible();
        pageProvider.getFollowingPage().checkTextInSuccessMessage("Successfully followed " + followingLogin + ".");
        pageProvider.getHomePage().switchToNextTab();
        pageProvider.getFollowingPage().clickOnButtonFollow();
        pageProvider.getLoginPage().checkIsAlertMessageVisible();
        pageProvider.getLoginPage().checkTextInAlertMessage("You are already following this user.");
        pageProvider.getHomePage().getHeader().clickOnButtonSignOut();


        pageProvider.getLoginPage().loginWithValidCred(followLogin, followPassword);
        pageProvider.getHomePage().openNewTabAndSwitchToIt(followingLoginUrl);
        pageProvider.getHomePage().openNewTabAndSwitchToIt(followingLoginUrl);
        pageProvider.getFollowingPage().clickOnButtonStopFollow();
        pageProvider.getFollowingPage().checkIsSuccessMessageVisible();
        pageProvider.getFollowingPage().checkTextInSuccessMessage("Successfully stopped following " + followingLogin + ".");
        pageProvider.getHomePage().switchToPreviousTab();
        pageProvider.getFollowingPage().clickOnButtonStopFollow();
        pageProvider.getLoginPage().checkIsAlertMessageVisible();
        pageProvider.getLoginPage().checkTextInAlertMessage("You cannot stop following someone you do not already follow..");
    }
}
