package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static TestData.TestData.*;

public class HomePage extends ParentPageWithHeader {
    @FindBy(xpath = "//a[@class='profile-nav-link nav-item nav-link active' and contains(text(), 'Posts')]")
    private WebElement buttonProfilePosts;
    @FindBy(xpath = "//a[contains(@class, 'profile-nav-link') and contains(text(), 'Followers')]")
    private WebElement buttonProfileFollowers;
    @FindBy(xpath = "//a[contains(@class, 'profile-nav-link') and contains(text(), 'Following')]")
    private WebElement buttonProfileFollowing;
    private ActionWithElements actionWithElements;
    private String postTitleLocator = ".//*[text()='%s']";
    @FindBy(xpath = "//table[@class='table-api table table-striped table-bordered ']")
    private WebElement tableWithApiValues;
    @FindBy(xpath = "//h2[contains(., 'Hello ') and contains(., ', your feed is empty.')]/strong")
    private WebElement userNameElement;
    @FindBy(xpath = "//p[@class='lead text-muted']")
    private WebElement feedMessageElement;
    @FindBy(xpath = "//h3[contains(text(), 'The latest posts')]")
    private WebElement latestPostsHeader;
    @FindBy(xpath = "//h3[contains(text(), 'The latest posts')]/following-sibling::div[@class='list-group']")
    private WebElement latestPostsList;
    @FindBy(xpath = "//h3[contains(text(), 'The Latest From Those You Follow')]/following-sibling::div[@class='list-group']")
    private WebElement latestPostsFromFollowingList;


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

    public List<WebElement> getPostList(String title) {

        return webDriver.findElements(By.xpath(
                String.format(postTitleLocator, title
                )));
    }

    public HomePage checkIsButtonProfileFollowingVisible() {
        actionWithElements.checkElementDisplayed(buttonProfileFollowing);
        return this;
    }

    public HomePage deletePostTillPresent(String title) {
        List<WebElement> postlist = getPostList(title);
        int counter = 0;
        while (!postlist.isEmpty() && counter < 100) {

            clickOnElement(postlist.get(0));
            new PostPage(webDriver).clickOnDeleteButton();

            logger.info("Post with title " + title + " was deleted");
            postlist = getPostList(title);

            counter++;
        }

        if (counter >= 100) {
            Assert.fail("There are more than 100 posts with title " + title + "or delete button does not work");
        }
        return this;
    }

    public HomePage checkPostWithTitleIsPresent(String title) {
        Assert.assertEquals(" Count of posts with title " + title, 1, getPostList(title).size());

        return this;
    }


    public List<String> extractTableInfo() {
        List<String> tableInfoList = new ArrayList<>();

        List<WebElement> rows = tableWithApiValues.findElements(By.xpath(".//tr"));


        tableInfoList.clear();

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.xpath(".//td"));
            StringBuilder rowInfo = new StringBuilder();
            for (WebElement cell : cells) {
                String cellText = cell.getText().replaceAll("\\s+", " ");
                rowInfo.append(cellText).append(" ");
            }
            String rowInfoString = rowInfo.toString().trim();
            tableInfoList.add(rowInfoString);
            System.out.println(rowInfoString);
        }

        return tableInfoList;
    }

    List<String> expectedTextList = Arrays.asList(
            GET_TOKEN,
            GET_ALL_POSTS,
            CREATE_ONE_POST,
            DELETE_ONE_POST,
            CREATE_USER,
            GET_USER_INFO,
            DELETE_USER);

    public void checkAPItextInTheTable() {
        List<String> tableInfoList = extractTableInfo();

        if (tableInfoList.isEmpty()) {
            Assert.fail("table is empty or not found");
        }

        for (String expectedText : expectedTextList) {
            boolean textFound = false;
            for (String rowInfoString : tableInfoList) {
                if (rowInfoString.contains(expectedText)) {
                    textFound = true;
                    break;
                }
            }
            Assert.assertTrue("text not found on table: " + expectedText, textFound);
        }
    }

    public String getUserNameFromGreeting() { // get the username from the greeting and in future we can use it for checking the username in the home page
        String userName = userNameElement.getText();
        System.out.println(userName);
        return userName;
    }

    public void checkUserNameInHomePage(String expectedUserName) { // check the username in the home page
        String actualUserName = getUserNameFromGreeting();
        assert actualUserName.equals(expectedUserName) : "Actual username '" + actualUserName + "' does not match expected username '" + expectedUserName + "'";
    }

    public void checkTextInHomePageMenu(String expectedText) { // check the text in the home page menu
        String actualText = feedMessageElement.getText();
        assert actualText.equals(expectedText) : "Actual username '" + actualText + "' does not match expected username '" + expectedText + "'";
    }

    public void checkIstLatestPostsListGroupVisible() {
        checkElementDisplayed(latestPostsList);
    }

    public void checkIstLatestPostsFromFollowingListGroupVisible() {
        checkElementDisplayed(latestPostsFromFollowingList);
    }

    public void checkIsLatestPostsFromFollowingListGroupNotVisible() {
        checkElementNotDisplayed(latestPostsFromFollowingList);
    }

    public void checkPostStructure() { // check the structure of the post in the latest posts list
        List<WebElement> postItems = latestPostsList.findElements(By.cssSelector("div.list-group a.list-group-item")); // Find all post items

        for (WebElement postItem : postItems) { // loop through all post items
            WebElement postTitleElement = postItem.findElement(By.tagName("strong")); // Find the post title element
            WebElement userInfoElement = postItem.findElement(By.xpath(".//div[@class='text-muted small']")); // Find the user info element

            String actualPostTitle = postTitleElement.getText().trim();
            String actualUserInfoText = userInfoElement.getText().trim();


            Assert.assertFalse("Post title is empty.", actualPostTitle.isEmpty()); // check if the post title is not empty


            Assert.assertTrue("User info does not contain 'by'.", actualUserInfoText.contains("by")); // check if the user info contains the word 'by'


            Assert.assertTrue("User info does not contain date in the format 'MM/dd/yyyy'.", actualUserInfoText.matches(".*\\d{1,2}/\\d{1,2}/\\d{4}.*"));
            // check if the user info contains the date in the format 'MM/dd/yyyy' When .*\\d{1,2}/\\d{1,2}/\\d{4}.*" is a regular expression that matches any text that contains a date in the format 'MM/dd/yyyy'
        }
    }

    public void checkNumberOfPostsInLatestPost(int expectedNumber) {
        List<WebElement> postItems = latestPostsList.findElements(By.cssSelector("div.list-group a.list-group-item"));
        Assert.assertEquals("The number of posts is not " + expectedNumber + ".", postItems.size(), expectedNumber);
    }

    public void checkNumberOfPostsInFollowPost(int expectedNumber) {
        List<WebElement> postItems = latestPostsFromFollowingList.findElements(By.cssSelector("div.list-group a.list-group-item"));
        Assert.assertEquals("The number of posts is not " + expectedNumber + ".", postItems.size(), expectedNumber);
    }

    public void assertPostWithTitlePresent(String title) { // check if the post with the specified title is present in the list
        List<WebElement> postItems = latestPostsList.findElements(By.cssSelector("div.list-group a.list-group-item"));

        boolean postFound = false;

        for (WebElement postItem : postItems) { // loop through all post items
            WebElement postTitleElement = postItem.findElement(By.tagName("strong"));
            String actualPostTitle = postTitleElement.getText().trim();

            if (actualPostTitle.equals(title)) {  // if the post title matches the expected title, stop the loop
                postFound = true;
                break;
            }
        }

        Assert.assertTrue("Post with title '" + title + "' is not present in the list.", postFound);
    }

    public void clickOnPostWithTitle(String title) { // find the post with the specified title and click on it
        List<WebElement> postItems = latestPostsList.findElements(By.cssSelector("div.list-group a.list-group-item"));

        boolean postFound = false;

        for (WebElement postItem : postItems) { // loop through all post items
            WebElement postTitleElement = postItem.findElement(By.tagName("strong"));
            String actualPostTitle = postTitleElement.getText().trim();

            if (actualPostTitle.equals(title)) { // if the post title matches the expected title, click on the post and stop the loop
                clickOnElement(postItem);
                postFound = true;
                break;
            }
        }

        Assert.assertTrue("Post with title '" + title + "' is not present in the list.", postFound);
    }

    public void verifyDateExistsInPost(String postTitle, String expectedDate) { // Verify that the post with the specified title contains the specified date in the user info
        List<WebElement> postItems = latestPostsList.findElements(By.cssSelector("div.list-group a.list-group-item")); // Find all post items

        boolean dateFound = false;

        for (WebElement postItem : postItems) { // Iterate through all post items
            WebElement postTitleElement = postItem.findElement(By.tagName("strong")); // Find the post title element
            if (postTitleElement.getText().trim().equals(postTitle)) {// Check if the post title matches the expected title
                WebElement userInfoElement = postItem.findElement(By.xpath(".//div[@class='text-muted small']"));
                String actualUserInfoText = userInfoElement.getText().trim();

                if (actualUserInfoText.contains(expectedDate)) { // Check if the user info contains the expected date if the post title matches stop the loop
                    dateFound = true;
                    break;
                }
            }
        }

        Assert.assertTrue("Date '" + expectedDate + "' is not found in the post with title '" + postTitle + "'.", dateFound);
    }
}