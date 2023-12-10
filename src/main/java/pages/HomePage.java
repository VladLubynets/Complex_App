package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends ParentPageWithHeader {
    @FindBy(xpath = "//a[@class='profile-nav-link nav-item nav-link active' and contains(text(), 'Posts')]")
    private WebElement buttonProfilePosts;
    @FindBy(xpath = "//a[contains(@class, 'profile-nav-link') and contains(text(), 'Followers')]")
    private WebElement buttonProfileFollowers;
    @FindBy(xpath = "//a[contains(@class, 'profile-nav-link') and contains(text(), 'Following')]")
    private WebElement buttonProfileFollowing;
    private ActionWithElements actionWithElements;

    public HomePage(WebDriver webDriver) {
        super(webDriver);
        actionWithElements = new ActionWithElements(webDriver);
    }

    public HomePage checkIsButtonProfilePostsVisible() {
        actionWithElements.checkElementDisplayed(buttonProfilePosts);
        return this;
    }

    public HomePage checkIsButtonProfileFollowersVisible() {
        actionWithElements.checkElementDisplayed(buttonProfileFollowers);
        return this;
    }

    public HomePage checkIsButtonProfileFollowingVisible() {
        actionWithElements.checkElementDisplayed(buttonProfileFollowing);
        return this;
    }
}
