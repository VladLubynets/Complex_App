package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.NoSuchElementException;

public class MyProfilePage extends ParentPageWithHeader{

    @FindBy(xpath = "//a[@class='list-group-item list-group-item-action']")
    private List<WebElement> profilePosts;

    public MyProfilePage(WebDriver webDriver) {
        super(webDriver);
    }

    public void myProfileListClickOnPostWithTitle(String title) {
        WebElement post = getPostWithTitle(title);
        if (post != null) {
            clickOnElement(post);
        } else {
            throw new NoSuchElementException("Post with title '" + title + "' not found.");
        }
    }

    private WebElement getPostWithTitle(String title) {
        for (WebElement post : profilePosts) {
            String postTitle = post.findElement(By.tagName("strong")).getText().trim();
            if (postTitle.equals(title)) {
                return post;
            }
        }
        return null;
    }
    public void checkNumberOfPostWithTitleIsPresent(String title, int expectedCount) {
        int actualCount = 0;
        for (WebElement post : profilePosts) {
            String postTitle = post.findElement(By.tagName("strong")).getText().trim();
            if (postTitle.equals(title)) {
                actualCount++;
            }
        }
        if (actualCount != expectedCount) {
            throw new AssertionError("The number of posts with title '" + title + "' is not as expected. Expected: " + expectedCount + ", Actual: " + actualCount);
        }
    }
}