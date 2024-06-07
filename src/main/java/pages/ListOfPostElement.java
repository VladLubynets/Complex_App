package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
        try {
            workingListOfPosts = new WebDriverWait(webDriver, Duration.ofSeconds(4)).until(
                    w -> webDriver.findElement(By.xpath("//h3[text()='" + nameOfPostsList.getName() + "']/following-sibling::div"))
            );
        } catch (TimeoutException e) {
            workingListOfPosts = null;
        }
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

    public void checkIsFollowingPostsListGroupVisible() {
        checkElementDisplayed(workingListOfPosts);
    }

    public void checkIsFollowingPostsListGroupNonVisible() {
        if (workingListOfPosts != null) {
            checkElementNotDisplayed(workingListOfPosts);
        }
    }

    public void checkNumberOfPosts(int expectedNumber) {
        Assert.assertEquals("The number of posts is not " + expectedNumber + ".", getPostItems().size(), expectedNumber);
    }

    public void checkPostStructure() {
        List<WebElement> postItems = getPostItems();
        for (int i = 0; i < postItems.size(); i++) {
            WebElement postItem = postItems.get(i);
            String postItemText = postItem.getText().trim();

            WebElement postTitleElement = postItem.findElement(inner_postTitleLocator);
            WebElement userInfoElement = postItem.findElement(inner_userInfoLocator);

            String actualPostTitle = postTitleElement.getText().trim();
            String actualUserInfoText = userInfoElement.getText().trim();


            Assert.assertFalse("Post title is empty.", actualPostTitle.isEmpty()); // check that post title is not empty
            Assert.assertTrue("User info does not contain 'by'.", actualUserInfoText.contains("by"));


            boolean containsDate = actualUserInfoText.matches(".*\\d{1,2}/\\d{1,2}/\\d{4}.*"); // check that user info contains date in the format 'MM/dd/yyyy'
            Assert.assertTrue("User info does not contain date in the format 'MM/dd/yyyy'.", containsDate);


            String expectedPostNumberPrefix = (i + 1) + "."; // check that post number starts with the correct number
            Assert.assertTrue("Post number does not start with the correct number: " + expectedPostNumberPrefix,
                    postItemText.startsWith(expectedPostNumberPrefix));
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


    public void verifyActualDateExistsInPost(String postTitle) {
        LocalDate currentDate = LocalDate.now();
        System.out.println(currentDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        System.out.println(formatter.format(currentDate));
        String expectedDate = currentDate.format(formatter);
        System.out.println(expectedDate);
        System.out.println(postTitle);

        boolean dateFound = false;

        for (WebElement postItem : getPostItems()) {
            WebElement postTitleElement = postItem.findElement(inner_postTitleLocator);
            if (postTitleElement.getText().trim().equals(postTitle)) {
                WebElement userInfoElement = postItem.findElement(inner_userInfoLocator);
                String actualUserInfoText = userInfoElement.getText().trim();

                if (actualUserInfoText.contains(expectedDate)) {
                    dateFound = true;
                    break;
                }
            }
        }
        Assert.assertTrue("Date '" + expectedDate + "' is not found in the post with title '" + postTitle + "'.", dateFound);
    }

    public void verifyPostInFollowingListNotInLatestPosts(String postTitle) {
        boolean isInFollowingList = isPostPresentInFollowingList(postTitle);
        boolean isInLatestPosts = isPostPresentInLatestPosts(postTitle);

        Assert.assertTrue("Post titled '" + postTitle + "' should be present in 'The Latest From Those You Follow'.", isInFollowingList);
        Assert.assertFalse("Post titled '" + postTitle + "' should not be present in 'The Latest Posts'.", isInLatestPosts);
    }

    public void checkPostExistInBothLists(String postTitle) {
        boolean isInFollowingList = isPostPresentInFollowingList(postTitle);
        boolean isInLatestPosts = isPostPresentInLatestPosts(postTitle);

        Assert.assertTrue("Post titled '" + postTitle + "' should be present in 'The Latest From Those You Follow'.", isInFollowingList);
        Assert.assertTrue("Post titled '" + postTitle + "' should be present in 'The Latest Posts'.", isInLatestPosts);
    }


    public void checkPostExistInFollowingList(String postTitle) {
        boolean isInFollowingList = isPostPresentInFollowingList(postTitle);
        Assert.assertTrue("Post titled '" + postTitle + "' should be present in 'The Latest From Those You Follow'.", isInFollowingList);
    }

    public void checkPostExistInLatestPosts(String postTitle) {
        boolean isInLatestPosts = isPostPresentInLatestPosts(postTitle);
        Assert.assertTrue("Post titled '" + postTitle + "' should be present in 'The Latest Posts'.", isInLatestPosts);
    }

    public void verifyPostNotPresentInFollowingList(String postTitle) {
        boolean isInFollowingList = !isPostPresentInFollowingList(postTitle);
        Assert.assertTrue("Post titled '" + postTitle + "' should not be present in 'The Latest From Those You Follow'.", isInFollowingList);
    }

    public void verifyPostNotPresentInLatestPostsElement(String postTitle) {
        boolean isInLatestPosts = !isPostPresentInLatestPosts(postTitle);
        Assert.assertTrue("Post titled '" + postTitle + "' should not be present in 'The Latest Posts'.", isInLatestPosts);
    }

    private boolean isPostPresentInFollowingList(String postTitle) {
        for (WebElement postItem : getPostItems()) {
            WebElement postTitleElement = postItem.findElement(inner_postTitleLocator);
            if (postTitleElement.getText().trim().equals(postTitle)) {
                return true;
            }
        }
        return false;
    }

    private boolean isPostPresentInLatestPosts(String postTitle) {
        for (WebElement postItem : getPostItems()) {
            WebElement postTitleElement = postItem.findElement(inner_postTitleLocator);
            if (postTitleElement.getText().trim().equals(postTitle)) {
                return true;
            }
        }
        return false;
    }
}