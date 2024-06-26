package pages;

import org.openqa.selenium.WebDriver;

public class PageProvider {
    // this class is a factory for all pages in the project
    // if we need to create a new page we need to add it here
    // And then we can use it in tests For example: pageProvider.HeaderLoginPage().openPage();
    // in general, it`s an entry point for all pages

    WebDriver webDriver;

    public PageProvider(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public LoginPage getLoginPage() {
        return new LoginPage(webDriver);
    }

    public HomePage getHomePage() {
        return new HomePage(webDriver);
    }

    public CreatePostPage getCreatePostPage() {
        return new CreatePostPage(webDriver);
    }

    public FollowingPage getFollowingPage() {
        return new FollowingPage(webDriver);
    }

    public PostPage getPostPage() {
        return new PostPage(webDriver);
    }

    public ApiUiPage getApiUiPage() {
        return new ApiUiPage(webDriver);
    }

    public MyProfilePage getMyProfilePage() {
        return new MyProfilePage(webDriver);
    }
}
