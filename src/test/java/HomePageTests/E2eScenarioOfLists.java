package HomePageTests;

import org.junit.After;
import org.junit.Test;
import BaseTest.BaseTest;

import static TestData.TestData.*;

public class E2eScenarioOfLists extends BaseTest {


    @Test
    public void TC1_e2eScenarioOfLists() {
        pageProvider.getLoginPage().openLoginPage().loginWithValidCred(VALID_LOGIN_WITHOUT_POSTS, VALID_PASSWORD_WITHOUT_POSTS);//  Login with user without posts
        pageProvider.getHomePage().getUserNameFromGreeting(); // Check username in greeting
        pageProvider.getHomePage().checkUserNameInHomePage(VALID_LOGIN_WITHOUT_POSTS); // Check username in greeting
        pageProvider.getHomePage().checkTextInHomePageMenu(EXPECTED_TEXT_IN_HOME_PAGE); // Check text in home page menu
        pageProvider.getHomePage().checkIstLatestPostsListGroupVisible(); // Check is latest posts list group visible
        pageProvider.getHomePage().checkIsLatestPostsFromFollowingListGroupNotVisible(); // Check is latest posts from following list group not visible
        pageProvider.getHomePage().checkPostStructure();// Check post structure  in latest posts list
        pageProvider.getHomePage().checkNumberOfPostsInLatestPost(30); // Check number of posts in latest post
        pageProvider.getHomePage().getHeader().clickOnCreatePostButton(); // creating new post
        pageProvider.getCreatePostPage().enterTextIntoInputTitle("український пост")
                .enterTextIntoInputBody("тіло українського поста")
                .clickOnButtonSavePost();
        pageProvider.getPostPage().checkTextInSuccessMessage("New post successfully created.");
        pageProvider.getHomePage().getHeader().clickOnLogoButton();
        pageProvider.getHomePage().checkNumberOfPostsInLatestPost(30); // Check number of posts in latest post
        pageProvider.getHomePage().assertPostWithTitlePresent("український пост"); // Check post with title present
        pageProvider.getHomePage().clickOnPostWithTitle("український пост");
        pageProvider.getPostPage().clickOnEditButton(); // Edit post
        pageProvider.getCreatePostPage().enterTextIntoInputTitle("український пост змінений");
        pageProvider.getPostPage().clickOnSaveUpdatesButton();

        pageProvider.getPostPage().checkTextInSuccessMessage("Post successfully updated.");
        pageProvider.getHomePage().getHeader().clickOnLogoButton();
        pageProvider.getHomePage().assertPostWithTitlePresent("український пост змінений");
        pageProvider.getHomePage().verifyDateExistsInPost("український пост змінений", "5/22/2024"); // Verify actual date exists in post
        pageProvider.getHomePage().getHeader().clickOnButtonSignOut();
        pageProvider.getLoginPage().openLoginPage().loginWithValidCred(VALID_LOGIN_WITH_FOLLOWERS, VALID_PASSWORD_WITH_FOLLOWERS);
        pageProvider.getHomePage().checkIstLatestPostsFromFollowingListGroupVisible();
        pageProvider.getHomePage().checkIstLatestPostsListGroupVisible();
        pageProvider.getHomePage().checkNumberOfPostsInFollowPost(15);
        pageProvider.getHomePage().checkNumberOfPostsInLatestPost(30);
        pageProvider.getHomePage().getHeader().clickOnButtonSignOut();
    }


    @After
    public void DeletePost() { // delete post after test
        pageProvider.getLoginPage().openLoginPage().loginWithValidCred(VALID_LOGIN_WITHOUT_POSTS, VALID_PASSWORD_WITHOUT_POSTS);
        pageProvider.getHomePage().getHeader().clickOnLogoButton();
        pageProvider.getHomePage().clickOnPostWithTitle("український пост змінений");
        pageProvider.getPostPage().clickOnDeleteButton();
        pageProvider.getPostPage().checkTextInSuccessMessage("Post successfully deleted.");
    }
}