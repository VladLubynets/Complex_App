package pages;


import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * This class represents the header section of the site,
 * providing methods to interact with header elements.
 */
public class Header extends ActionWithElements {

    // Web elements representing different components of the header
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

    @FindBy(xpath = "//a[contains(@class, 'text-white') and contains(text(), 'Complex app for testing')]")
    private WebElement buttonLogo;

    @FindBy(xpath = "//img[@alt='My profile']")
    private WebElement buttonMyProfile;

    @FindBy(xpath = "//span[@class=\"text-white mr-2\"]")
    private WebElement avatar;

    /**
     * Constructor to initialize the Header object.
     */
    public Header(WebDriver webDriver) {
        super(webDriver);
    }

    /**
     * Checks if the Create Post button is visible.
     */
    public Header checkIsButtonCreatePostVisible() {
        checkElementDisplayed(buttonCreatePost);
        return this;
    }

    /**
     * Checks if the Create Post button is not visible.
     */
    public Header checkIsButtonCreatePostNotVisible() {
        checkElementNotDisplayed(buttonCreatePost);
        return this;
    }

    /**
     * Checks if the Profile button is visible.
     */
    public Header checkIsButtonProfileVisible() {
        checkElementDisplayed(buttonProfile);
        return this;
    }

    /**
     * Checks if the Profile button is not visible.
     */
    public Header checkIsButtonProfileNotVisible() {
        checkElementNotDisplayed(buttonProfile);
        return this;
    }

    /**
     * Checks if the Chat button is visible.
     */
    public Header checkIsButtonChatVisible() {
        checkElementDisplayed(buttonChat);
        return this;
    }

    /**
     * Checks if the Chat button is not visible.
     */
    public Header checkIsButtonChatNotVisible() {
        checkElementNotDisplayed(buttonChat);
        return this;
    }

    /**
     * Checks if the Search button is visible.
     */
    public Header checkIsButtonSearchVisible() {
        checkElementDisplayed(buttonSearch);
        return this;
    }

    /**
     * Checks if the Search button is not visible.
     */
    public Header checkIsButtonSearchNotVisible() {
        checkElementNotDisplayed(buttonSearch);
        return this;
    }

    /**
     * Checks if the Sign Out button is visible.
     */
    public Header checkIsSignOutButtonVisible() {
        checkElementDisplayed(signOutButton);
        return this;
    }

    /**
     * Clicks on the Sign Out button.
     */
    public Header clickOnButtonSignOut() {
        clickOnElement(signOutButton);
        return this;
    }

    /**
     * Checks if the Sign Out button is not visible.
     */
    public Header checkIsSignOutButtonNotVisible() {
        checkElementNotDisplayed(signOutButton);
        return this;
    }

    /**
     * Clicks on the My Profile button and navigates to the HomePage.
     */
    public HomePage clickOnMyProfileButton() {
        clickOnElement(buttonMyProfile);
        return new HomePage(webDriver);
    }

    /**
     * Checks if the My Profile button is visible.
     */
    public Header checkIsMyProfileButtonVisible() {
        checkElementDisplayed(buttonMyProfile);
        return this;
    }

    /**
     * Checks if the avatar is visible and verifies the text in the avatar matches the expected username.
     */
    public Header checkIsAvatarVisibleAndCheckTextInAvatar(String enteredUsername) {
        String actualUsername = avatar.getText();
        Assert.assertEquals("Logged in username doesn't match displayed username", enteredUsername.toLowerCase(), actualUsername);
        checkElementDisplayed(avatar);
        return this;
    }

    /**
     * Clicks on the Create Post button.
     */
    public void clickOnCreatePostButton() {
        clickOnElement(buttonCreatePost);
    }

    /**
     * Clicks on the Logo button.
     */
    public void clickOnLogoButton() {
        clickOnElement(buttonLogo);
    }
}