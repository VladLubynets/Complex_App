package HomePageTests;

import TestData.User;
import api.ApiHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import BaseTest.BaseTest;

import static TestData.TestData.*;

public class E2eScenarioOfLists extends BaseTest {
    ApiHelper apiHelper = new ApiHelper();
    private String token;
    private String tokenUser3;
    private String tokenUser2;

    User username1 = new User(generateRandomString(3), VALID_PASSWORD, generateRandomEmail());
    User username2 = new User(generateRandomString(3), VALID_PASSWORD, generateRandomEmail());
    User username3 = new User(generateRandomString(3), VALID_PASSWORD, generateRandomEmail());

    @Before
    public void createUserAndCreatePostsByApi() {
        token = apiHelper.getToken();
        apiHelper.createUserByApiIfNeeded(username1.getLogin(), username1.getPassword(), username1.getEmail(), token); // create user if not exist
        apiHelper.createUserByApiIfNeeded(username2.getLogin(), username2.getPassword(), username2.getEmail(), token);
        apiHelper.createUserByApiIfNeeded(username3.getLogin(), username3.getPassword(), username3.getEmail(), token);

        tokenUser3 = apiHelper.getToken(username3.getLogin(), username3.getPassword());
        apiHelper.createMultiplePostsByApi(8, tokenUser3, username3.getLogin()); // create Number of posts for user 3

        tokenUser2 = apiHelper.getToken(username2.getLogin(), username2.getPassword());
        apiHelper.createMultiplePostsByApi(8, tokenUser2, username2.getLogin()); // create Number of posts for user 2
    }

    @Test
    public void TC1_e2eScenarioOfLists() {
        System.out.println("Test started");

        pageProvider.getLoginPage().loginWithValidCred(username1.getLogin(), username1.getPassword()); // Login with user without posts
//        pageProvider.getHomePageNew().getUserNameFromGreeting(); // Check username in greeting -- не робить перевірки !!!
        pageProvider.getHomePageNew().checkUserNameInHomePage(username1.getLogin()); // Check username in greeting
        pageProvider.getHomePageNew().checkTextInHomePageMenu(EXPECTED_TEXT_IN_HOME_PAGE); // Check text in home page menu
        pageProvider.getHomePageNew().getLatestPostsElement().checkIstLatestPostsListGroupVisible(); // Check is latest posts list group visible
//        pageProvider.getHomePageNew().getLatestPostsFromFollowingList().checkIsLatestPostsFromFollowingListGroupNotVisible(); // Check is latest posts from following list group not visible
        pageProvider.getHomePageNew().getLatestPostsElement().checkPostStructure(); // Check post structure in latest posts list
        pageProvider.getHomePageNew().getLatestPostsElement().checkNumberOfPosts(30); // Check number of posts in latest post
        pageProvider.getHomePageNew().getHeader().clickOnCreatePostButton(); // creating new post
        pageProvider.getCreatePostPage().enterTextIntoInputTitle("український пост")
                .enterTextIntoInputBody("тіло українського поста")
                .clickOnButtonSavePost();
        pageProvider.getPostPage().checkTextInSuccessMessage("New post successfully created.");
        pageProvider.getHomePageNew().getHeader().clickOnLogoButton();
        pageProvider.getHomePageNew().getLatestPostsElement().checkNumberOfPosts(30); // Check number of posts in latest post
        pageProvider.getHomePageNew().getLatestPostsElement().checkNumberOfPostWithTitleIsPresent("український пост", 1); // Check post with title present
        pageProvider.getHomePageNew().getLatestPostsElement().clickOnPostWithTitle("український пост");
        pageProvider.getPostPage().clickOnEditButton(); // Edit post
        pageProvider.getCreatePostPage().enterTextIntoInputTitle("український пост змінений");
        pageProvider.getPostPage().clickOnSaveUpdatesButton();
        pageProvider.getPostPage().checkTextInSuccessMessage("Post successfully updated.");
        pageProvider.getHomePageNew().getHeader().clickOnLogoButton();
//        pageProvider.getHomePageNew().checkPostWithTitlePresent("український пост змінений");
//        pageProvider.getHomePageNew().verifyDateExistsInPost("український пост змінений", "5/25/2024"); // Verify actual date exists in post
//        pageProvider.getHomePageNew().openNewTabAndSwitchToIt(username3.getUrl());
//        pageProvider.getFollowingPage().clickOnButtonFollow(); // Follow user 3
//        pageProvider.getFollowingPage().checkIsSuccessMessageVisible();
//        pageProvider.getFollowingPage().checkTextInSuccessMessage("Successfully followed " + username3.getLogin() + "."); // Check success message
//        pageProvider.getHomePageNew().openNewTabAndSwitchToIt(username2.getUrl());
//        pageProvider.getFollowingPage().clickOnButtonFollow(); // Follow user 2
//        pageProvider.getFollowingPage().checkIsSuccessMessageVisible();
//        pageProvider.getFollowingPage().checkTextInSuccessMessage("Successfully followed " + username2.getLogin() + "."); // Check success message
//        pageProvider.getHomePageNew().getHeader().clickOnLogoButton();
//        pageProvider.getHomePageNew().checkIstLatestPostsFromFollowingListGroupVisible();
//        pageProvider.getHomePageNew().checkIstLatestPostsListGroupVisible();
//        pageProvider.getHomePageNew().checkNumberOfPostsInFollowPost(15); // Check number of posts in follow post
//        pageProvider.getHomePageNew().checkNumberOfPostsInLatestPost(30);// Check number of posts in latest post
//        pageProvider.getHomePageNew().openNewTabAndSwitchToIt(username2.getUrl());
//        pageProvider.getFollowingPage().clickOnButtonStopFollow();// Stop follow user 2 if we don`t stop follow user  we can`t delete user
//        pageProvider.getFollowingPage().checkIsSuccessMessageVisible();
//        pageProvider.getFollowingPage().checkTextInSuccessMessage("Successfully stopped following " + username2.getLogin() + ".");
//        pageProvider.getHomePageNew().openNewTabAndSwitchToIt(username3.getUrl());
//        pageProvider.getFollowingPage().clickOnButtonStopFollow();// Stop follow user 3 if we don`t stop follow user  we can`t delete user
//        pageProvider.getFollowingPage().checkIsSuccessMessageVisible();
//        pageProvider.getFollowingPage().checkTextInSuccessMessage("Successfully stopped following " + username3.getLogin() + ".");
//        pageProvider.getHomePageNew().getHeader().clickOnButtonSignOut();
    }

    @After
    public void cleanUp() {
        System.out.println("After started");

        apiHelper.deletePostsTillPresent(username3.getLogin(), username3.getPassword()); // delete all posts by user 3
        apiHelper.deletePostsTillPresent(username2.getLogin(), username2.getPassword()); // delete all posts by user 2
        apiHelper.deletePostsTillPresent(username1.getLogin(), username1.getPassword()); // delete all posts by user 1

        String userInfoResponse1 = apiHelper.getUserInfo(username1.getLogin(), token);  // get user1 info
        String userId1 = apiHelper.extractUserId(userInfoResponse1); // extract user id

        String userInfoResponse2 = apiHelper.getUserInfo(username2.getLogin(), token);  // get user2 info
        String userId2 = apiHelper.extractUserId(userInfoResponse2); // extract user id

        String userInfoResponse3 = apiHelper.getUserInfo(username3.getLogin(), token);  // get user3 info
        String userId3 = apiHelper.extractUserId(userInfoResponse3); // extract user id

        apiHelper.deleteUser(token, userId1, username1.getLogin(), true); // delete user 1
        apiHelper.deleteUser(token, userId2, username2.getLogin(), true);// delete user 2
        apiHelper.deleteUser(token, userId3, username3.getLogin(), true);// delete user 3
    }
}