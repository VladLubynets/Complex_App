package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends ParentPageWithHeader {
    @FindBy(xpath = "//a[@href=\"/profile/aqacomplex\" and contains(@class, 'profile-nav-link')]")
    private WebElement buttonProfilePosts;
    @FindBy(xpath = "//a[@href=\"/profile/aqacomplex/followers\" and contains(@class, 'profile-nav-link')]")
    private WebElement buttonProfileFollowers;
    @FindBy(xpath = "//a[@href=\"/profile/aqacomplex/following\" and contains(@class, 'profile-nav-link')]")
    private WebElement buttonProfileFollowing;

    public HomePage(WebDriver webDriver) {
        super(webDriver);
    }
}
