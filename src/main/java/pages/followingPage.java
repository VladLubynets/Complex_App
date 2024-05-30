package pages;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class followingPage extends ParentPage {

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


    public followingPage(WebDriver webDriver) {
        super(webDriver);

    }

    public followingPage checkIsSuccessMessageVisible() {
        checkElementDisplayed(successMessage);
        return this;
    }

    public followingPage checkTextInSuccessMessage(String text) {
        Assert.assertEquals("Alert message text doesn't match expected text", text, successMessage.getText());
        return this;
    }

    public followingPage checkIsButtonFollowersVisible() {
        checkElementDisplayed(followersButton);
        return this;
    }


    public followingPage clickOnButtonFollow() {
        clickOnElement(buttonStartFollow);
        return this;
    }



    public followingPage clickOnButtonStopFollow() {
        clickOnElement(buttonStopFollow);
        return this;
    }

    public followingPage checkIsCountOfFollowers(int CountOfFollowers) {
       String followersText = followersButton.getText();
     Assert.assertTrue("After subscribing or unsubscribing, the number of subscribers did not change", followersText.contains("Followers: " + CountOfFollowers));
        return this;

    }

    public followingPage checkIsCountOfFollowing(int CountOfFollowing) {
        String followingText = followingButton.getText();
        Assert.assertTrue("After subscribing or unsubscribing, the number of subscriptions did not change", followingText.contains("Following: " + CountOfFollowing));
        return this;
    }

    public followingPage clickOnButtonFollowers() {
        clickOnElement(followersButton);
        return this;
    }
    public followingPage clickOnButtonFollowing() {
        clickOnElement(followingButton);
        return this;
    }
    public followingPage checkIsButtonProfileFollowingVisible() {
        checkElementDisplayed(buttonProfileFollowing);
        return this;
    }
    public followingPage checkIsButtonProfilePostsVisible() {
       checkElementDisplayed(buttonProfilePosts);
        return this;
    }
    public followingPage checkIsButtonProfileFollowersVisible() {
        checkElementDisplayed(buttonProfileFollowers);
        return this;
    }
}
