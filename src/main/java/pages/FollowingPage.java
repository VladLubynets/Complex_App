package pages;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


/**
 * Represents the following page functionality.
 */

public class FollowingPage extends ParentPage {

    @FindBy(xpath = "//button[contains(@class, 'btn') and contains(text(), 'Follow')]")
    private WebElement buttonStartFollow;

    @FindBy(xpath = "//div[@class=\"alert alert-success text-center\"]")
    private WebElement successMessage;

    @FindBy(xpath = "//a[contains(@class, 'profile-nav-link') and contains(text(), 'Following')]")
    private WebElement buttonProfileFollowing;

    @FindBy(xpath = "//button[@class=\"btn btn-danger btn-sm\"]")
    private WebElement buttonStopFollow;
    @FindBy(xpath = "//a[contains(@class, 'profile-nav-link') and contains(@class, 'nav-item') and contains(@class, 'nav-link') and contains(text(), 'Followers')]")
    private WebElement followersButton;
    @FindBy (xpath = "//a[contains(@class, 'profile-nav-link') and contains(@class, 'nav-item') and contains(@class, 'nav-link') and contains(text(), 'Following')]")
    private WebElement followingButton;
    @FindBy(xpath = "//a[@class='profile-nav-link nav-item nav-link active' and contains(text(), 'Posts')]")
    private WebElement buttonProfilePosts;
    @FindBy(xpath = "//a[contains(@class, 'profile-nav-link') and contains(text(), 'Followers')]")
    private WebElement buttonProfileFollowers;


    public FollowingPage(WebDriver webDriver) {
        super(webDriver);

    }
    /**
     * Verifies if the success message is visible.
     * Checks if the success message element is displayed on the page.
     */
    public FollowingPage checkIsSuccessMessageVisible() {
        checkElementDisplayed(successMessage);
        return this;
    }

    /**
     * Verifies if the text in the success message matches the expected text.
     * Compares the text in the success message element with the expected text.
     */
    public FollowingPage checkTextInSuccessMessage(String text) {
        Assert.assertEquals("Alert message text doesn't match expected text", text, successMessage.getText());
        return this;
    }

    /**
     * Verifies if the followers button is visible.
     * Checks if the followers button element is displayed on the page.
     */
    public FollowingPage checkIsButtonFollowersVisible() {
        checkElementDisplayed(followersButton);
        return this;
    }

    /**
     * Clicks on the follow button.
     * Performs a click action on the follow button element.
     */
    public FollowingPage clickOnButtonFollow() {
        clickOnElement(buttonStartFollow);
        return this;
    }

    /**
     * Clicks on the stop follow button.
     * Performs a click action on the stop follow button element.
     */
    public FollowingPage clickOnButtonStopFollow() {
        clickOnElement(buttonStopFollow);
        return this;
    }

    /**
     * Verifies if the count of followers matches the expected count.
     * Checks if the text of the followers button contains the expected count of followers.
     */
    public FollowingPage checkIsCountOfFollowers(int countOfFollowers) {
        String followersText = followersButton.getText();
        Assert.assertTrue("After subscribing or unsubscribing, the number of followers did not change", followersText.contains("Followers: " + countOfFollowers));
        return this;
    }

    /**
     * Verifies if the count of following matches the expected count.
     * Checks if the text of the following button contains the expected count of following.
     */
    public FollowingPage checkIsCountOfFollowing(int countOfFollowing) {
        String followingText = followingButton.getText();
        Assert.assertTrue("After subscribing or unsubscribing, the number of following did not change", followingText.contains("Following: " + countOfFollowing));
        return this;
    }

    /**
     * Clicks on the followers button.
     * Performs a click action on the followers button element.
     */
    public FollowingPage clickOnButtonFollowers() {
        clickOnElement(followersButton);
        return this;
    }

    /**
     * Clicks on the following button.
     * Performs a click action on the following button element.
     */
    public FollowingPage clickOnButtonFollowing() {
        clickOnElement(followingButton);
        return this;
    }

    /**
     * Verifies if the profile following button is visible.
     * Checks if the profile following button element is displayed on the page.
     */
    public FollowingPage checkIsButtonProfileFollowingVisible() {
        checkElementDisplayed(buttonProfileFollowing);
        return this;
    }

    /**
     * Verifies if the profile posts button is visible.
     * Checks if the profile posts button element is displayed on the page.
     */
    public FollowingPage checkIsButtonProfilePostsVisible() {
        checkElementDisplayed(buttonProfilePosts);
        return this;
    }

    /**
     * Verifies if the profile followers button is visible.
     * Checks if the profile followers button element is displayed on the page.
     */
    public FollowingPage checkIsButtonProfileFollowersVisible() {
        checkElementDisplayed(buttonProfileFollowers);
        return this;
    }
}