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
    String SecondUserUrl = String.format(USER_URL, loginSecond);
    String FirstUserUrl = String.format(USER_URL, loginFirst);


    @Test
    public void TC14_followingTestApiAndUi() {
        pageProvider.getLoginPage().registrationOfUser(loginFirst, passwordFirst, TestData.generateRandomEmail());
        pageProvider.getLoginPage().registrationOfUser(loginSecond, passwordSecond, TestData.generateRandomEmail());
        pageProvider.getLoginPage().loginWithValidCred(loginFirst, passwordFirst);
        pageProvider.getHomePage().openNewTabAndSwitchToIt(SecondUserUrl);
        pageProvider.getFollowingPage().clickOnButtonFollow();
        pageProvider.getFollowingPage().checkIsSuccessMessageVisible();
        pageProvider.getFollowingPage().checkTextInSuccessMessage("Successfully followed " + loginSecond + ".");
        pageProvider.getHomePage().refreshPage();


        pageProvider.getFollowingPage().checkIsButtonFollowersVisible();
        pageProvider.getFollowingPage().clickOnButtonFollowers();
        pageProvider.getFollowingPage().checkIsCountOfFollowers(1);
        pageProvider.getHomePage().getHeader().clickOnButtonSignOut();


        pageProvider.getLoginPage().loginWithValidCred(loginSecond, passwordSecond);
        pageProvider.getHomePage().openNewTabAndSwitchToIt(FirstUserUrl);


        pageProvider.getFollowingPage().clickOnButtonFollowing();
        pageProvider.getFollowingPage().checkIsCountOfFollowing(1);

        pageProvider.getHomePage().getHeader().clickOnButtonSignOut();

        pageProvider.getLoginPage().loginWithValidCred(loginFirst, passwordFirst);
        pageProvider.getHomePage().openNewTabAndSwitchToIt(SecondUserUrl);
        pageProvider.getFollowingPage().clickOnButtonStopFollow();
        pageProvider.getFollowingPage().checkIsSuccessMessageVisible();
        pageProvider.getFollowingPage().checkTextInSuccessMessage("Successfully stopped following " + loginSecond + ".");

        pageProvider.getHomePage().refreshPage();
        pageProvider.getFollowingPage().clickOnButtonFollowers();
        pageProvider.getFollowingPage().checkIsCountOfFollowers(0);

        pageProvider.getHomePage().getHeader().clickOnButtonSignOut();

        pageProvider.getLoginPage().loginWithValidCred(loginSecond, passwordSecond);
        pageProvider.getHomePage().openNewTabAndSwitchToIt(FirstUserUrl);
        pageProvider.getFollowingPage().clickOnButtonFollowing();
        pageProvider.getFollowingPage().checkIsCountOfFollowing(0);
    }
}
