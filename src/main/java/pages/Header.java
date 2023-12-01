package pages;


import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Header extends ActionWithElements {
    @FindBy(xpath = "//button[@class=\"btn btn-sm btn-secondary\" and contains(text(), 'Sign Out')]")
    private WebElement signOutButton;
    @FindBy(xpath = "//a[@href=\"/create-post\"]")
    private WebElement buttonCreatePost;
    @FindBy(xpath = "//a[contains(@href, '/profile/') and contains(@class, 'mr-2')]")
    private WebElement buttonProfile;
    @FindBy(xpath = "//span[contains( @class, 'text-white mr-2 header-chat-icon')]")
    private WebElement buttonChat;
    @FindBy(xpath = "//a[contains( @class, 'text-white mr-2 header-search-icon')]")
    private WebElement buttonSearch;
    @FindBy(xpath = "//a[contains(@class, 'text-white') and contains(text(), 'Complex app for testing - QA')]")
    private WebElement buttonLogo;
    @FindBy(xpath = "//img[@alt='My profile']")
    private WebElement buttonMyProfile;
    @FindBy(xpath = "//span[@class=\"text-white mr-2\"]")
    private WebElement avatar;

    public Header(WebDriver webDriver) {
        super(webDriver);
    }

    public Header checkIsButtonCreatePostVisible() {
        checkElementDisplayed(buttonCreatePost);
        return this;
    }

    public Header checkIsButtonCreatePostNotVisible() {
        checkElementNotDisplayed(buttonCreatePost);
        return this;
    }

    public Header checkIsButtonProfileVisible() {
        checkElementDisplayed(buttonProfile);
        return this;
    }

    public Header checkIsButtonProfileNotVisible() {
        checkElementNotDisplayed(buttonProfile);
        return this;
    }

    public Header checkIsButtonChatVisible() {
        checkElementDisplayed(buttonChat);
        return this;
    }

    public Header checkIsButtonChatNotVisible() {
        checkElementNotDisplayed(buttonChat);
        return this;
    }

    public Header checkIsButtonSearchVisible() {
        checkElementDisplayed(buttonSearch);
        return this;
    }

    public Header checkIsButtonSearchNotVisible() {
        checkElementNotDisplayed(buttonSearch);
        return this;
    }

    public Header checkIsSignOutButtonVisible() {
        checkElementDisplayed(signOutButton);
        return this;
    }

    public Header checkIsSignOutButtonNotVisible() {
        checkElementNotDisplayed(signOutButton);
        return this;
    }

    public Header checkIsAvatarVisibleAndCheckTextInAvatar(String enteredUsername) {
        String actualUsername = avatar.getText();
        Assert.assertEquals("Logged in username doesn't match displayed username", enteredUsername.toLowerCase(), actualUsername);
        checkElementDisplayed(avatar);
        return this;
    }
}