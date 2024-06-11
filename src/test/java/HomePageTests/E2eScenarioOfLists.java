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
    User username4 = new User(VALID_LOGIN_WITH_OLD_POSTS, VALID_PASSWORD, generateRandomEmail());

    @Before
    public void createUserAndCreatePostsByApi() {
        token = apiHelper.getToken();
        apiHelper.createUserByApiIfNeeded(username1.getLogin(), username1.getPassword(), username1.getEmail(), token); // create user if not exist
        apiHelper.createUserByApiIfNeeded(username2.getLogin(), username2.getPassword(), username2.getEmail(), token);
        apiHelper.createUserByApiIfNeeded(username3.getLogin(), username3.getPassword(), username3.getEmail(), token);

        tokenUser3 = apiHelper.getToken(username3.getLogin(), username3.getPassword());
        apiHelper.createMultiplePostsByApi("пост українською", 8, tokenUser3, username3.getLogin(), "One Person", "no"); // create Number of posts for user 3


        tokenUser2 = apiHelper.getToken(username2.getLogin(), username2.getPassword());
        apiHelper.createMultiplePostsByApi("Post", 8, tokenUser2, username2.getLogin(), "One Person", "no"); // create Number of posts for user 2

        apiHelper.createOnePostForFollowers("Оригінальний post for followers", "body of post for followers", "One Person", "yes", tokenUser2); // create post for followers

    }

    @Test
    public void TC1_e2eScenarioOfLists() {
        pageProvider.getLoginPage().openLoginPage().loginWithValidCred(username1.getLogin(), username1.getPassword()); // Login with user without posts
        pageProvider.getHomePage().getUserNameFromGreeting(); // Check username in greeting
        pageProvider.getHomePage().checkUserNameInHomePage(username1.getLogin()); // Check username in greeting
        pageProvider.getHomePage().checkTextInHomePageMenu(EXPECTED_TEXT_IN_HOME_PAGE); // Check text in home page menu
        pageProvider.getHomePage().getLatestPostsElement().checkIstLatestPostsListGroupVisible(); // Check is latest posts list group visible
        pageProvider.getHomePage().getLatestPostsFromFollowingList().checkIsFollowingPostsListGroupNonVisible(); // Check is latest posts from following list group not visible
        pageProvider.getHomePage().getLatestPostsElement().checkPostStructure(); // Check post structure in latest posts list
        pageProvider.getHomePage().getLatestPostsElement().checkNumberOfPosts(30); // Check number of posts in latest post
        pageProvider.getHomePage().getHeader().clickOnCreatePostButton(); // creating new post
        pageProvider.getCreatePostPage().enterTextIntoInputTitle("український пост")
                .enterTextIntoInputBody("тіло українського поста")
                .clickOnButtonSavePost();
        pageProvider.getPostPage().checkTextInSuccessMessage("New post successfully created.");
        pageProvider.getHomePage().getHeader().clickOnLogoButton();
        pageProvider.getHomePage().getLatestPostsElement().checkNumberOfPosts(30); // Check number of posts in latest post
        pageProvider.getHomePage().getLatestPostsElement().checkNumberOfPostWithTitleIsPresent("український пост", 1); // Check post with title present
        pageProvider.getHomePage().getLatestPostsElement().clickOnPostWithTitle("український пост");
        pageProvider.getPostPage().clickOnEditButton(); // Edit post
        pageProvider.getCreatePostPage().enterTextIntoInputTitle("український пост змінений");
        pageProvider.getPostPage().clickOnSaveUpdatesButton();
        pageProvider.getPostPage().checkTextInSuccessMessage("Post successfully updated.");
        pageProvider.getHomePage().getHeader().clickOnLogoButton();
        pageProvider.getHomePage().getLatestPostsElement().checkNumberOfPostWithTitleIsPresent("український пост змінений", 1); // Check post with title present
        pageProvider.getHomePage().getLatestPostsElement().verifyActualDateExistsInPost("український пост змінений"); // Verify actual date exists in post
        pageProvider.getHomePage().openNewTabAndSwitchToIt(username3.getUrl());
        pageProvider.getFollowingPage().clickOnButtonFollow(); // Follow user 3
        pageProvider.getFollowingPage().checkIsSuccessMessageVisible();
        pageProvider.getFollowingPage().checkTextInSuccessMessage("Successfully followed " + username3.getLogin() + "."); // Check success message
        pageProvider.getHomePage().openNewTabAndSwitchToIt(username2.getUrl());
        pageProvider.getFollowingPage().clickOnButtonFollow(); // Follow user 2
        pageProvider.getFollowingPage().checkIsSuccessMessageVisible();
        pageProvider.getFollowingPage().checkTextInSuccessMessage("Successfully followed " + username2.getLogin() + "."); // Check success message
        pageProvider.getHomePage().getHeader().clickOnLogoButton();
        pageProvider.getHomePage().getLatestPostsFromFollowingList().checkIstLatestPostsListGroupVisible();
        pageProvider.getHomePage().getLatestPostsElement().checkIstLatestPostsListGroupVisible();
        pageProvider.getHomePage().getLatestPostsFromFollowingList().checkNumberOfPosts(15); // Check number of posts in follow post
        pageProvider.getHomePage().getLatestPostsElement().checkNumberOfPosts(30);// Check number of posts in latest post
        pageProvider.getHomePage().openNewTabAndSwitchToIt(username2.getUrl());
        pageProvider.getFollowingPage().clickOnButtonStopFollow();// Stop follow user 2 if we don`t stop follow user  we can`t delete user
        pageProvider.getFollowingPage().checkIsSuccessMessageVisible();
        pageProvider.getFollowingPage().checkTextInSuccessMessage("Successfully stopped following " + username2.getLogin() + ".");
        pageProvider.getHomePage().openNewTabAndSwitchToIt(username3.getUrl());
        pageProvider.getFollowingPage().clickOnButtonStopFollow();// Stop follow user 3 if we don`t stop follow user  we can`t delete user
        pageProvider.getFollowingPage().checkIsSuccessMessageVisible();
        pageProvider.getFollowingPage().checkTextInSuccessMessage("Successfully stopped following " + username3.getLogin() + ".");
        pageProvider.getHomePage().getHeader().clickOnButtonSignOut();
    }


    @Test
    public void TC2_e2eScenarioOfLogicList() {
        pageProvider.getLoginPage().openLoginPage().loginWithValidCred(username1.getLogin(), username1.getPassword()); // Login with user without posts
        pageProvider.getHomePage().getUserNameFromGreeting(); // Check username in greeting
        pageProvider.getHomePage().checkUserNameInHomePage(username1.getLogin()); // Check username in greeting
        pageProvider.getHomePage().checkTextInHomePageMenu(EXPECTED_TEXT_IN_HOME_PAGE); // Check text in home page menu
        pageProvider.getHomePage().getLatestPostsElement().checkIstLatestPostsListGroupVisible(); // Check is latest posts list group visible
        // Check is the  latest posts from following list group not visible
        pageProvider.getHomePage().getLatestPostsFromFollowingList().checkIsFollowingPostsListGroupNonVisible();
        pageProvider.getHomePage().getLatestPostsElement().checkPostStructure(); // Check post structure in latest posts list
        pageProvider.getHomePage().getLatestPostsElement().checkNumberOfPosts(30); // Check number of posts in latest post
        pageProvider.getHomePage().getHeader().clickOnCreatePostButton(); // creating new post
        pageProvider.getCreatePostPage().createPost("український пост", "тіло українського поста");
        pageProvider.getPostPage().checkTextInSuccessMessage("New post successfully created.");
        pageProvider.getHomePage().getHeader().clickOnLogoButton();
        pageProvider.getHomePage().getLatestPostsElement().verifyActualDateExistsInPost("український пост"); // Verify actual date exists in post
        pageProvider.getHomePage().getLatestPostsElement().clickOnPostWithTitle("український пост");
        pageProvider.getPostPage().clickOnDeleteButton(); // delete post when check actual date
        pageProvider.getHomePage().getHeader().clickOnButtonSignOut();

        pageProvider.getLoginPage().openLoginPage().loginWithValidCred(username2.getLogin(), username2.getPassword());
        int currentPosition = pageProvider.getHomePage().getLatestPostsElement().getPostPositionWithTitle("Post 5");  // get post and save current position
        pageProvider.getHomePage().getLatestPostsElement().clickOnPostWithTitle("Post 5");
        pageProvider.getPostPage().clickOnEditButton();
        pageProvider.getCreatePostPage().setCheckBoxForFollowers(true); // set checkbox for followers
        pageProvider.getPostPage().clickOnSaveUpdatesButton();
        pageProvider.getPostPage().checkTextInSuccessMessage("Post successfully updated.");
        pageProvider.getHomePage().getHeader().clickOnLogoButton();
        pageProvider.getHomePage().getLatestPostsElement().verifyPostInLists("Post 5", false); // check post not present in latest post
        pageProvider.getHomePage().getHeader().clickOnMyProfileButton();
        pageProvider.getMyProfilePage().myProfileListClickOnPostWithTitle("Post 5");
        pageProvider.getPostPage().clickOnEditButton();
        pageProvider.getCreatePostPage().setCheckBoxForFollowers(false); // set checkbox for  all users
        pageProvider.getPostPage().clickOnSaveUpdatesButton();
        pageProvider.getPostPage().checkTextInSuccessMessage("Post successfully updated.");
        pageProvider.getHomePage().getHeader().clickOnLogoButton();
        // Check post with title present in right position located in all users list
        pageProvider.getHomePage().getLatestPostsElement().checkPostPositionWithTitle("Post 5", currentPosition);
        pageProvider.getHomePage().getLatestPostsElement().checkNumberOfPosts(30); // Check number of posts in latest post
        pageProvider.getHomePage().getLatestPostsElement().checkPostDatesInDescendingOrder(); // Verify post dates in descending order
        pageProvider.getHomePage().getHeader().clickOnButtonSignOut();


        pageProvider.getLoginPage().openLoginPage().loginWithValidCred(username1.getLogin(), username1.getPassword());
        pageProvider.getLoginPage().openNewTabAndSwitchToIt(username2.getUrl());
        pageProvider.getFollowingPage().clickOnButtonFollow(); // Follow user 2 with posts
        pageProvider.getFollowingPage().checkIsSuccessMessageVisible();
        pageProvider.getFollowingPage().checkTextInSuccessMessage("Successfully followed " + username2.getLogin() + ".");
        pageProvider.getHomePage().getHeader().clickOnLogoButton();
        // Verify top posts from user2 in descending order should be 8 on the top in following list
        pageProvider.getHomePage().getLatestPostsFromFollowingList().checkTopPostsFromUserInDescendingOrder(username2.getLogin(), 8);
        // Check is the latest posts from following list group visible
        pageProvider.getHomePage().getLatestPostsFromFollowingList().checkIstLatestPostsListGroupVisible();
        // check is following posts list group visible
        pageProvider.getHomePage().getLatestPostsFromFollowingList().checkIsFollowingPostsListGroupVisible();
        // check post present in following list
        pageProvider.getHomePage().getLatestPostsFromFollowingList().verifyPostInLists("Оригінальний post for followers", true);
        // check post not present in latest post
        pageProvider.getHomePage().getLatestPostsElement().verifyPostInLists("Оригінальний post for followers", false);
        pageProvider.getHomePage().getHeader().clickOnButtonSignOut();

        pageProvider.getLoginPage().loginWithValidCred(username2.getLogin(), username2.getPassword()); // Login with user with posts
        pageProvider.getHomePage().getHeader().clickOnMyProfileButton();
        pageProvider.getMyProfilePage().myProfileListClickOnPostWithTitle("Оригінальний post for followers");
        pageProvider.getPostPage().clickOnEditButton();
        // set checkbox for all users on post "Оригінальний post for followers"
        pageProvider.getCreatePostPage().setCheckBoxForFollowers(false);
        pageProvider.getPostPage().clickOnSaveUpdatesButton();
        pageProvider.getPostPage().checkTextInSuccessMessage("Post successfully updated.");
        pageProvider.getHomePage().getHeader().clickOnLogoButton();
        pageProvider.getHomePage().getLatestPostsElement().verifyPostInLists("Оригінальний post for followers", true); // check post present in latest post
        pageProvider.getHomePage().getHeader().clickOnButtonSignOut();

        pageProvider.getLoginPage().loginWithValidCred(username1.getLogin(), username1.getPassword());
        // check post present in following list
        pageProvider.getHomePage().getLatestPostsFromFollowingList().verifyPostInLists("Оригінальний post for followers", true);
        // if you don't use the checkbox but are subscribed, the post will be in both lists
        pageProvider.getHomePage().getLatestPostsElement().verifyPostInLists("Оригінальний post for followers", true);
        pageProvider.getHomePage().getHeader().clickOnButtonSignOut();

        pageProvider.getLoginPage().loginWithValidCred(username2.getLogin(), username2.getPassword()); // Login with user with posts
        pageProvider.getHomePage().getHeader().clickOnMyProfileButton();
        pageProvider.getMyProfilePage().myProfileListClickOnPostWithTitle("Оригінальний post for followers");
        pageProvider.getPostPage().clickOnDeleteButton(); // delete post "Оригінальний post for followers"
        pageProvider.getHomePage().getHeader().clickOnLogoButton();
        pageProvider.getHomePage().getLatestPostsElement().verifyPostInLists("Оригінальний post for followers", false);  // check post not present in latest post
        pageProvider.getHomePage().getHeader().clickOnButtonSignOut();

        pageProvider.getLoginPage().loginWithValidCred(username1.getLogin(), username1.getPassword()); // Login with user who follow user 2

        // check post "Оригінальний post for followers" not present in latest post
        pageProvider.getHomePage().getLatestPostsElement().verifyPostInLists("Оригінальний post for followers", false);
        // check post "Оригінальний post for followers"  not present in following list
        pageProvider.getHomePage().getLatestPostsFromFollowingList().verifyPostInLists("Оригінальний post for followers", false);

        pageProvider.getHomePage().openNewTabAndSwitchToIt(username3.getUrl());
        pageProvider.getFollowingPage().clickOnButtonFollow(); // Follow user 3 with posts
        pageProvider.getFollowingPage().checkIsSuccessMessageVisible();
        pageProvider.getFollowingPage().checkTextInSuccessMessage("Successfully followed " + username3.getLogin() + "."); // Check success message
        pageProvider.getHomePage().getHeader().clickOnLogoButton();
        pageProvider.getHomePage().getLatestPostsFromFollowingList().checkNumberOfPosts(15); // Check number of posts in follow post
        pageProvider.getHomePage().getLatestPostsElement().checkNumberOfPosts(30); // Check number of posts in latest post

        pageProvider.getHomePage().openNewTabAndSwitchToIt(username2.getUrl());
        // Stop follow user 2 if we don`t stop follow user  we can`t delete user and it`s need to run to next test
        pageProvider.getFollowingPage().clickOnButtonStopFollow();
        pageProvider.getFollowingPage().checkIsSuccessMessageVisible();
        pageProvider.getFollowingPage().checkTextInSuccessMessage("Successfully stopped following " + username2.getLogin() + ".");

        pageProvider.getHomePage().getHeader().clickOnLogoButton();


        pageProvider.getHomePage().openNewTabAndSwitchToIt(username4.getUrl());
        pageProvider.getFollowingPage().clickOnButtonFollow(); // Follow user 4 with old posts
        pageProvider.getFollowingPage().checkIsSuccessMessageVisible();
        pageProvider.getFollowingPage().checkTextInSuccessMessage("Successfully followed " + username4.getLogin() + ".");
        pageProvider.getHomePage().getHeader().clickOnLogoButton();
        // get post position and save it
        int expectedCountForFollowingPosts = pageProvider.getHomePage().getLatestPostsFromFollowingList().getPostPositionWithTitle("Test4");
        pageProvider.getHomePage().getHeader().clickOnButtonSignOut();

        pageProvider.getLoginPage().loginWithValidCred(username4.getLogin(), username4.getPassword()); // Login with user 4 with old posts
        pageProvider.getHomePage().getHeader().clickOnMyProfileButton();
        pageProvider.getMyProfilePage().myProfileListClickOnPostWithTitle("Test4");
        pageProvider.getPostPage().clickOnEditButton();
        pageProvider.getCreatePostPage().setCheckBoxForFollowers(false); // set checkbox for all users
        pageProvider.getPostPage().clickOnSaveUpdatesButton();
        pageProvider.getPostPage().checkTextInSuccessMessage("Post successfully updated.");
        pageProvider.getHomePage().getHeader().clickOnLogoButton();
        pageProvider.getHomePage().getLatestPostsElement().verifyPostInLists("Test4", true); // check post present in latest post
        pageProvider.getHomePage().getLatestPostsElement().clickOnPostWithTitle("Test4");
        pageProvider.getPostPage().clickOnEditButton();
        pageProvider.getCreatePostPage().setCheckBoxForFollowers(true); // set checkbox for followers
        pageProvider.getPostPage().clickOnSaveUpdatesButton();
        pageProvider.getPostPage().checkTextInSuccessMessage("Post successfully updated.");
        pageProvider.getHomePage().getHeader().clickOnLogoButton();
        pageProvider.getHomePage().getLatestPostsElement().verifyPostInLists("Test4", false); // check post not present in latest post
        pageProvider.getHomePage().getHeader().clickOnButtonSignOut();

        pageProvider.getLoginPage().loginWithValidCred(username1.getLogin(), username1.getPassword()); // Login with user who follow user 4
        pageProvider.getHomePage().getLatestPostsFromFollowingList().verifyPostInLists("Test4", true); // check post present in following list
        // check the position of the post  equal to the expected position
        pageProvider.getHomePage().getLatestPostsFromFollowingList().checkPostPositionWithTitle("Test4", expectedCountForFollowingPosts);
        pageProvider.getHomePage().getLatestPostsElement().checkPostDatesInDescendingOrder(); // Verify post dates in descending order


        pageProvider.getHomePage().openNewTabAndSwitchToIt(username4.getUrl());
        pageProvider.getFollowingPage().clickOnButtonStopFollow();// Stop follow user 4 if we don`t stop follow user  we can`t delete user
        pageProvider.getFollowingPage().checkIsSuccessMessageVisible();
        pageProvider.getFollowingPage().checkTextInSuccessMessage("Successfully stopped following " + username4.getLogin() + ".");


        pageProvider.getHomePage().openNewTabAndSwitchToIt(username3.getUrl());
        pageProvider.getFollowingPage().clickOnButtonStopFollow();// Stop follow user 3 if we don`t stop follow user  we can`t delete user
        pageProvider.getFollowingPage().checkIsSuccessMessageVisible();
        pageProvider.getFollowingPage().checkTextInSuccessMessage("Successfully stopped following " + username3.getLogin() + ".");
        pageProvider.getHomePage().getHeader().clickOnButtonSignOut();
    }

    @After
    public void cleanUp() {
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