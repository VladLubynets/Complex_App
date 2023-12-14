package pages;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PostPage extends ParentPageWithHeader {
    @FindBy(xpath = ".//div[@class='alert alert-success text-center']")
    private WebElement successMessageElement;

    @FindBy(xpath = "//button[text()='Save New Post']")
    private WebElement buttonSave;

    @FindBy(xpath = ".//button[@class='delete-post-button text-danger']")
    private WebElement buttonDelete;

    @FindBy(xpath = "//button[text()='Save Post']")
    private WebElement buttonSavePost;

    @FindBy(xpath = "//a[@href and @class='text-primary mr-2']")
    private WebElement buttonEdit;


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
}