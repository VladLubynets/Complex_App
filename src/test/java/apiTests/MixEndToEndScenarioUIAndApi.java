package apiTests;

import BaseTest.BaseTest;
import TestData.TestData;
import api.ApiHelper;
import org.junit.Before;
import org.junit.Test;
import pages.HomePage;
import pages.LoginPage;


public class MixEndToEndScenarioUIAndApi extends BaseTest {

    ApiHelper apiHelper = new ApiHelper();

    private String username;
    private String password;
    private String token;
    private String userId;


    @Before
    public void CreateDataIntoLogin() {
        LoginPage loginPage = pageProvider.getLoginPage();

        username = TestData.generateRandomString(10); // create random username
        password = TestData.generateRandomString(10) + "123qwerty";  // create random password
        String email = TestData.generateRandomEmail();  // create random email

        loginPage.openLoginPage().registrationOfUser(username, password, email); // registration of user
        token = apiHelper.getToken(username, password); // get token
        String userInfoResponse = apiHelper.getUserInfo(username, token);   // get user info (it`s need for take user id)
        userId = apiHelper.extractUserId(userInfoResponse); // get user id
        System.out.println("final info: " + username + " " + password + " " + email + " " + token + " " + userId);
    }

    @Test
    public void TC3_testUIAndApiLoginEndToEndScenario() {
        LoginPage loginPage = pageProvider.getLoginPage();
        HomePage homePage = pageProvider.getHomePage();

        loginPage.loginWithValidCred(username, password); // login with valid credentials in UI
        homePage.getHeader().checkIsSignOutButtonVisible();  // check is log in successful

        apiHelper.deleteUser(token, userId, username, true); // delete user by api

        loginPage.refreshPage(); // refresh page
        homePage.getHeader().clickOnCreatePostButton(); // click on create post button (it`s need to check  after click was logout or not)
        loginPage.checkIsAlertMessageVisible(); // check is alert message visible
        loginPage.checkTextInAlertMessage("You must be logged in to perform that action.");

        loginPage.checkIsSignInButtonVisible(); // check is sign in button visible and log out was successful
        homePage.getHeader().checkIsSignOutButtonNotVisible(); // TODO here BUG after delete user by api, user still can make some actions in UI
    }
}

