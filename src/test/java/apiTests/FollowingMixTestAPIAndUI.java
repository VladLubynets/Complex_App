package apiTests;

import TestData.TestData;
import BaseTest.BaseTest;
import org.junit.Test;
import TestData.User;


public class FollowingMixTestAPIAndUI extends BaseTest {

    User loginFirst = new User(TestData.generateRandomString(10), TestData.generateRandomString(12), TestData.generateRandomEmail());
    User loginSecond = new User(TestData.generateRandomString(10), TestData.generateRandomString(12), TestData.generateRandomEmail());


    User followUser = new User(TestData.generateRandomString(10), TestData.generateRandomString(12), TestData.generateRandomEmail());
    User followingUser = new User(TestData.generateRandomString(10), TestData.generateRandomString(12), TestData.generateRandomEmail());


    @Test
    public void TC14_followingTestApiAndUi() {
        pageProvider.getLoginPage().registrationOfUser(loginFirst.getLogin(), loginFirst.getPassword(), loginFirst.getEmail());
        pageProvider.getLoginPage().registrationOfUser(loginSecond.getLogin(), loginSecond.getPassword(), loginSecond.getEmail());

        pageProvider.getLoginPage().loginWithValidCred(loginFirst.getLogin(), loginFirst.getPassword());
        pageProvider.getHomePage().openNewTabAndSwitchToIt(loginSecond.getUrl());
        pageProvider.getFollowingPage().clickOnButtonFollow();
        pageProvider.getFollowingPage().checkIsSuccessMessageVisible();
        pageProvider.getFollowingPage().checkTextInSuccessMessage("Successfully followed " + loginSecond.getLogin() + ".");
        pageProvider.getHomePage().refreshPage();


        pageProvider.getFollowingPage().checkIsButtonFollowersVisible();
        pageProvider.getFollowingPage().clickOnButtonFollowers();
        pageProvider.getFollowingPage().checkIsCountOfFollowers(1);

        pageProvider.getHomePage().openNewTabAndSwitchToIt(loginFirst.getUrl());
        pageProvider.getFollowingPage().clickOnButtonFollowing();
        pageProvider.getFollowingPage().checkIsCountOfFollowing(1);

        pageProvider.getHomePage().getHeader().clickOnButtonSignOut();


        pageProvider.getLoginPage().loginWithValidCred(loginSecond.getLogin(), loginSecond.getPassword());
        pageProvider.getHomePage().openNewTabAndSwitchToIt(loginFirst.getUrl());


        pageProvider.getFollowingPage().clickOnButtonFollowing();
        pageProvider.getFollowingPage().checkIsCountOfFollowing(1);

        pageProvider.getHomePage().getHeader().clickOnButtonSignOut();

        pageProvider.getLoginPage().loginWithValidCred(loginFirst.getLogin(), loginFirst.getPassword());
        pageProvider.getHomePage().openNewTabAndSwitchToIt(loginSecond.getUrl());
        pageProvider.getFollowingPage().clickOnButtonStopFollow();
        pageProvider.getFollowingPage().checkIsSuccessMessageVisible();
        pageProvider.getFollowingPage().checkTextInSuccessMessage("Successfully stopped following " + loginSecond.getLogin() + ".");

        pageProvider.getHomePage().refreshPage();
        pageProvider.getFollowingPage().clickOnButtonFollowers();
        pageProvider.getFollowingPage().checkIsCountOfFollowers(0);

        pageProvider.getHomePage().getHeader().clickOnButtonSignOut();

        pageProvider.getLoginPage().loginWithValidCred(loginSecond.getLogin(), loginSecond.getPassword());
        pageProvider.getHomePage().openNewTabAndSwitchToIt(loginFirst.getUrl());
        pageProvider.getFollowingPage().clickOnButtonFollowing();
        pageProvider.getFollowingPage().checkIsCountOfFollowing(0);
    }

    @Test
    public void TC15_InvalidFollowingTestByUI() {
        pageProvider.getLoginPage().registrationOfUser(followUser.getLogin(), followUser.getPassword(), followUser.getEmail());
        pageProvider.getLoginPage().registrationOfUser(followingUser.getLogin(), followingUser.getPassword(), followingUser.getEmail());
        pageProvider.getLoginPage().loginWithValidCred(followUser.getLogin(), followUser.getPassword());
        pageProvider.getHomePage().openNewTabAndSwitchToIt(followingUser.getUrl());
        pageProvider.getHomePage().openNewTabAndSwitchToIt(followingUser.getUrl());
        pageProvider.getHomePage().switchToTabByIndex(-1);
        pageProvider.getFollowingPage().clickOnButtonFollow();
        pageProvider.getFollowingPage().checkIsSuccessMessageVisible();
        pageProvider.getFollowingPage().checkTextInSuccessMessage("Successfully followed " + followingUser.getLogin() + ".");
        pageProvider.getHomePage().switchToTabByIndex(1);
        pageProvider.getFollowingPage().clickOnButtonFollow();
        pageProvider.getLoginPage().checkIsAlertMessageVisible();
        pageProvider.getLoginPage().checkTextInAlertMessage("You are already following this user.");
        pageProvider.getHomePage().getHeader().clickOnButtonSignOut();


        pageProvider.getLoginPage().loginWithValidCred(followUser.getLogin(), followUser.getPassword());
        pageProvider.getHomePage().openNewTabAndSwitchToIt(followingUser.getUrl());
        pageProvider.getHomePage().openNewTabAndSwitchToIt(followingUser.getUrl());
        pageProvider.getFollowingPage().clickOnButtonStopFollow();
        pageProvider.getFollowingPage().checkIsSuccessMessageVisible();
        pageProvider.getFollowingPage().checkTextInSuccessMessage("Successfully stopped following " + followingUser.getLogin() + ".");
        pageProvider.getHomePage().switchToTabByIndex(-1);
        pageProvider.getFollowingPage().clickOnButtonStopFollow();
        pageProvider.getLoginPage().checkIsAlertMessageVisible();
        pageProvider.getLoginPage().checkTextInAlertMessage("You cannot stop following someone you do not already follow..");
    }
}
