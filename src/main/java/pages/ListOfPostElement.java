package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static pages.DateOfPostUtils.verifyDatesInDescendingOrder;

/**
 * Represents a page object handling a list of posts.
 */
public class ListOfPostElement extends ActionWithElements {

    WebElement workingListOfPosts;
    String nameOfPostsList;

    private By inner_postTitleLocator = By.tagName("strong");
    private By inner_userInfoLocator = By.xpath(".//div[@class='text-muted small']");

    /**
     * Constructor to initialize the list of posts.
     */
    public ListOfPostElement(WebDriver webDriver, NamesOfPostsList nameOfPostsList) {
        super(webDriver);
        this.nameOfPostsList = nameOfPostsList.getName();
        try {
            workingListOfPosts = new WebDriverWait(webDriver, Duration.ofSeconds(4)).until(
                    w -> webDriver.findElement(By.xpath("//h3[text()='" + nameOfPostsList.getName() + "']/following-sibling::div"))
            );
        } catch (TimeoutException e) {
            workingListOfPosts = null;
        }
    }

    /**
     * Retrieves the element with the header corresponding to the list of posts.
     */
    public WebElement getElementWithHeader() {
        return webDriver.findElement(By.xpath("//h2[text()='" + nameOfPostsList + "']"));
    }

    /**
     * Retrieves the list of post items from the current list of posts.
     */
    private List<WebElement> getPostItems() {
        return workingListOfPosts.findElements(By.cssSelector("div.list-group a.list-group-item"));
    }

    /**
     * Checks if the current list of latest posts is visible.
     */
    public void checkIsTheLatestPostsListGroupVisible() {
        checkElementDisplayed(workingListOfPosts);
    }

    /**
     * Checks if the current list of following posts is visible.
     */
    public void checkIsFollowingPostsListGroupVisible() {
        checkElementDisplayed(workingListOfPosts);
    }

    /**
     * Checks if the current list of following posts is not visible.
     */
    public void checkIsFollowingPostsListGroupNonVisible() {
        if (workingListOfPosts != null) {
            checkElementNotDisplayed(workingListOfPosts);
        }
    }

    /**
     * Verifies the number of posts matches the expected number.
     */
    public void checkNumberOfPosts(int expectedNumber) {
        Assert.assertEquals("The number of posts is not " + expectedNumber + ".", getPostItems().size(), expectedNumber);
    }

    /**
     * Checks the structure of each post item in the list.
     */
    public void checkPostStructure() {
        List<WebElement> postItems = getPostItems();
        for (int i = 0; i < postItems.size(); i++) {
            WebElement postItem = postItems.get(i);
            String postItemText = postItem.getText().trim();

            WebElement postTitleElement = postItem.findElement(inner_postTitleLocator);
            WebElement userInfoElement = postItem.findElement(inner_userInfoLocator);

            String actualPostTitle = postTitleElement.getText().trim();
            String actualUserInfoText = userInfoElement.getText().trim();

            Assert.assertFalse("Post title is empty.", actualPostTitle.isEmpty());
            Assert.assertTrue("User info does not contain 'by'.", actualUserInfoText.contains("by"));

            boolean containsDate = actualUserInfoText.matches(".*\\d{1,2}/\\d{1,2}/\\d{4}.*");
            Assert.assertTrue("User info does not contain date in the format 'MM/dd/yyyy'.", containsDate);

            String expectedPostNumberPrefix = (i + 1) + ".";
            Assert.assertTrue("Post number does not start with the correct number: " + expectedPostNumberPrefix,
                    postItemText.startsWith(expectedPostNumberPrefix));
        }
    }

    /**
     * Retrieves the list of post elements with a specific title.
     */
    public List<WebElement> getPostListWithName(String title) {
        String postTitleLocator = String.format(".//*[text()='%s']", title);
        return workingListOfPosts.findElements(By.xpath(String.format(postTitleLocator, title)));
    }

    /**
     * Verifies the number of posts with a specific title matches the expected count.
     */
    public ListOfPostElement checkNumberOfPostWithTitleIsPresent(String title, int expectedCount) {
        Assert.assertEquals("Count of posts with title " + title, expectedCount, getPostListWithName(title).size());
        return this;
    }

    /**
     * Clicks on the post with the specified title.
     */
    public void clickOnPostWithTitle(String title) {
        clickOnElement(getPostListWithName(title).get(0));
    }

    /**
     * Verifies if the post with the specified title exists in the list.
     */
    public void verifyPostInLists(String postTitle, boolean shouldBeInList) {
        boolean isPostPresent = isPostPresent(postTitle);

        if (shouldBeInList) {
            Assert.assertTrue("Post titled '" + postTitle + "' should be present in '" + nameOfPostsList + "'.", isPostPresent);
        } else {
            Assert.assertFalse("Post titled '" + postTitle + "' should not be present in '" + nameOfPostsList + "'.", isPostPresent);
        }
    }

    /**
     * Checks if a post with the specified title is present.
     */
    public boolean isPostPresent(String postTitle) {
        for (WebElement postItem : getPostItems()) {
            WebElement postTitleElement = postItem.findElement(inner_postTitleLocator);
            if (postTitleElement.getText().trim().equals(postTitle)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves the position of the post with the specified title in the list.
     */
    public int getPostPositionWithTitle(String title) {
        List<WebElement> postItems = getPostItems();
        for (int i = 0; i < postItems.size(); i++) {
            WebElement postItem = postItems.get(i);
            WebElement postTitleElement = postItem.findElement(inner_postTitleLocator);
            String actualPostTitle = postTitleElement.getText().trim();
            if (actualPostTitle.equals(title)) {
                int position = i + 1;
                System.out.println("Post with title '" + title + "' found at position: " + position);
                return position;
            }
        }
        System.out.println("Post with title '" + title + "' not found.");
        return -1;
    }

    /**
     * Verifies the position of the post with the specified title matches the expected position.
     */
    public void checkPostPositionWithTitle(String title, int expectedPosition) {
        int currentPosition = getPostPositionWithTitle(title);
        Assert.assertEquals("Position of post '" + title + "'", expectedPosition, currentPosition);
    }

    /**
     * Verifies if the actual date exists in the post with the specified title.
     */
    public void verifyActualDateExistsInPost(String postTitle) {
        List<WebElement> postItems = getPostItems();
        boolean dateExists = DateOfPostUtils.verifyActualDateExistsInPost(postItems, postTitle);
        Assert.assertTrue("Date is not found in the post with title '" + postTitle + "'.", dateExists);
    }

    /**
     * Checks the top posts from a specified user are in descending order by date.
     */
    public void checkTopPostsFromUserInDescendingOrder(String username, int Nposts) {
        List<WebElement> postItems = getPostItems();
        List<WebElement> topNPostItems = new ArrayList<>();
        int count = 0;


        for (WebElement postItem : postItems) {
            if (count >= Nposts) {
                break;
            }

            WebElement userInfoElement = postItem.findElement(inner_userInfoLocator);
            String actualUserInfoText = userInfoElement.getText().trim();


            if (actualUserInfoText.contains(username)) {
                topNPostItems.add(postItem);
                count++;
            }
        }


        Assert.assertEquals("Expected " + Nposts + " posts from user " + username + ", but found " + topNPostItems.size() + ".", Nposts, topNPostItems.size());


        boolean areDatesInDescendingOrder = verifyDatesInDescendingOrder(topNPostItems);
        Assert.assertTrue("Top " + Nposts + " post dates from user " + username + " are not in descending order.", areDatesInDescendingOrder);
    }

    /**
     * Checks if the dates of all posts are in descending order.
     */
    public void checkPostDatesInDescendingOrder() {
        List<WebElement> postItems = getPostItems();


        boolean areDatesInDescendingOrder = verifyDatesInDescendingOrder(postItems);
        Assert.assertTrue("Dates are not in descending order.", areDatesInDescendingOrder);
    }
}