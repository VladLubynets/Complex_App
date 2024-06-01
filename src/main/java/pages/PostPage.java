package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class PostPage extends ParentPageWithHeader {
    @FindBy(xpath = ".//div[@class='alert alert-success text-center']")
    private WebElement successMessageElement;

    @FindBy(xpath = "//button[text()='Save New Post']")
    private WebElement buttonSave;

    @FindBy(xpath = ".//button[@class='delete-post-button text-danger']")
    private WebElement buttonDelete;

    @FindBy(xpath = "//button[text()='Save Updates']")
    private WebElement buttonSaveUpdates;

    @FindBy(xpath = "//button[text()='Save Post']")
    private WebElement buttonSavePost;

    @FindBy(xpath = "//a[@href and @class='text-primary mr-2']")
    private WebElement buttonEdit;
    private String postTitleLocator = ".//*[text()='%s']";


    public PostPage(WebDriver webDriver) {
        super(webDriver);
    }


    public PostPage checkTextInSuccessMessage(String text) {
        Assert.assertEquals("Text in message", text, successMessageElement.getText());
        return this;
    }


    public HomePage clickOnDeleteButton() {
        clickOnElement(buttonDelete);
        return new HomePage(webDriver);
    }

    public PostPage clickOnEditButton() {
        clickOnElement(buttonEdit);
        return this;
    }
    public PostPage clickOnSaveUpdatesButton() {
        clickOnElement(buttonSaveUpdates);
        return this;
    }

    public List<WebElement> getPostList(String title) {
        return webDriver.findElements(By.xpath(
                String.format(postTitleLocator, title
                )));
    }

    public PostPage deletePostTillPresent(String title) {
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


}