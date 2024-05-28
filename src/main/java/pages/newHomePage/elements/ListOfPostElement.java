package pages.newHomePage.elements;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.ActionWithElements;
import pages.newHomePage.NamesOfPostsList;

import java.time.Duration;
import java.util.List;

public class ListOfPostElement extends ActionWithElements {
    WebElement workingListOfPosts;
    String nameOfPostsList;

    private By inner_postTitleLocator = By.tagName("strong");
    private By inner_userInfoLocator = By.xpath(".//div[@class='text-muted small']");


    public ListOfPostElement(WebDriver webDriver, NamesOfPostsList nameOfPostsList) {
        super(webDriver);
        this.nameOfPostsList = nameOfPostsList.getName();
        workingListOfPosts = new WebDriverWait(webDriver, Duration.ofSeconds(4)).until(
                w -> webDriver.findElement(By.xpath("//h3[text()='" + nameOfPostsList.getName() + "']/following-sibling::div"))
        );

    }

    public WebElement getElementWithHeader() {
        return webDriver.findElement(By.xpath("//h2[text()='" + nameOfPostsList + "']"));
    }

    private List<WebElement> getPostItems() {
        return workingListOfPosts.findElements(By.cssSelector("div.list-group a.list-group-item"));
    }


    public void checkIstLatestPostsListGroupVisible() {
        checkElementDisplayed(workingListOfPosts);
    }

    public void checkIsLatestPostsFromFollowingListGroupNotVisible() {
        checkElementNotDisplayed(workingListOfPosts);
    }

    public void checkNumberOfPosts(int expectedNumber) {
        Assert.assertEquals("The number of posts is not " + expectedNumber + ".", getPostItems().size(), expectedNumber);
    }

    public void checkPostStructure() {
        List<WebElement> postItems = getPostItems();
        for (int i = 0; i < postItems.size(); i++) {
            WebElement postItem = postItems.get(i);
            WebElement postTitleElement = postItem.findElement(inner_postTitleLocator);
            WebElement userInfoElement = postItem.findElement(inner_userInfoLocator);

            String actualPostTitle = postTitleElement.getText().trim();
            String actualUserInfoText = userInfoElement.getText().trim();

            Assert.assertFalse("Post title is empty.", actualPostTitle.isEmpty());
            Assert.assertTrue("User info does not contain 'by'.", actualUserInfoText.contains("by"));

            boolean containsDate = actualUserInfoText.matches(".*\\d{1,2}/\\d{1,2}/\\d{4}.*");
            Assert.assertTrue("User info does not contain date in the format 'MM/dd/yyyy'.", containsDate);
        }
    }

    public List<WebElement> getPostListWithName(String title) {
        String postTitleLocator = String.format(".//*[text()='%s']", title);
        return workingListOfPosts.findElements(By.xpath(
                    String.format(postTitleLocator, title
                )));
    }

    public ListOfPostElement checkNumberOfPostWithTitleIsPresent(String title, int expectedCount) {
        Assert.assertEquals(" Count of posts with title " + title, expectedCount, getPostListWithName(title).size());
        return this;
    }

    public void clickOnPostWithTitle(String title) {
        clickOnElement(getPostListWithName(title).get(0));
    }

    public void verifyDateExistsInPost(String postTitle, String expectedDate) { // Verify that the post with the specified title contains the specified date in the user info
        List<WebElement> listPostItems = getPostListWithName(postTitle);
        Assert.assertTrue("Post with title '" + postTitle + "' is not found.", listPostItems.size() > 0);

        boolean dateFound = true;

        for (WebElement postItem : listPostItems) { // Iterate through all post items
               WebElement userInfoElement = postItem.findElement(inner_userInfoLocator);
                String actualUserInfoText = userInfoElement.getText().trim();

                if (!actualUserInfoText.contains(expectedDate)) { // Check if the user info contains the expected date if the post title matches stop the loop
                    dateFound = false;
                    break;
                }

        }

        Assert.assertTrue("Date '" + expectedDate + "' is not found in the post with title '" + postTitle + "'.", dateFound);
    }
}
