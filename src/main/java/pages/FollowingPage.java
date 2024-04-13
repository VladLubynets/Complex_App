package pages;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FollowingPage extends ParentPage {

    @FindBy(xpath = "//button[contains(@class, 'btn') and contains(text(), 'Follow')]")
    private WebElement buttonStartFollow;

    @FindBy(xpath = "//div[@class=\"alert alert-success text-center\"]")
    private WebElement successMessage;

    @FindBy(xpath = "//button[@class=\"btn btn-danger btn-sm\"]")
    private WebElement buttonStopFollow;
    @FindBy(xpath = "//a[contains(@class, 'profile-nav-link') and contains(@class, 'nav-item') and contains(@class, 'nav-link') and contains(text(), 'Followers')]")
    private WebElement followersButton;
    @FindBy (xpath = "//a[contains(@class, 'profile-nav-link') and contains(@class, 'nav-item') and contains(@class, 'nav-link') and contains(text(), 'Following')]")
    private WebElement followingButton;


    public FollowingPage(WebDriver webDriver) {
        super(webDriver);

    }

    public FollowingPage checkIsSuccessMessageVisible() {
        checkElementDisplayed(successMessage);
        return this;
    }

    public FollowingPage checkTextInSuccessMessage(String text) {
        Assert.assertEquals("Alert message text doesn't match expected text", text, successMessage.getText());
        return this;
    }

    public FollowingPage checkIsButtonFollowersVisible() {
        checkElementDisplayed(followersButton);
        return this;
    }


    public FollowingPage clickOnButtonFollow() {
        clickOnElement(buttonStartFollow);
        return this;
    }



    public FollowingPage clickOnButtonStopFollow() {
        clickOnElement(buttonStopFollow);
        return this;
    }

    public FollowingPage checkIsCountOfFollowers( int CountOfFollowers) {
       String followersText = followersButton.getText();
     Assert.assertTrue("After subscribing or unsubscribing, the number of subscribers did not change", followersText.contains("Followers: " + CountOfFollowers));
        return this;

    }

    public FollowingPage checkIsCountOfFollowing( int CountOfFollowing) {
        String followingText = followingButton.getText();
        Assert.assertTrue("After subscribing or unsubscribing, the number of subscriptions did not change", followingText.contains("Following: " + CountOfFollowing));
        return this;
    }

    public FollowingPage clickOnButtonFollowers() {
        clickOnElement(followersButton);
        return this;
    }
    public FollowingPage clickOnButtonFollowing() {
        clickOnElement(followingButton);
        return this;
    }
}
