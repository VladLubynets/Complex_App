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

    public void CheckAPItextInTheTable() {
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
}

