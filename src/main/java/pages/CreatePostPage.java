package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreatePostPage extends ParentPageWithHeader {

    @FindBy(id = "post-title")
    private WebElement inputTitle;
    @FindBy(name = "body")
    private WebElement inputBody;
    @FindBy(xpath = "//button[text()='Save New Post']")
    private WebElement buttonSave;
    @FindBy(xpath = "//input[@type='checkbox' and @name='uniquePost']")

    private ActionWithElements actionWithElements;

    public CreatePostPage(WebDriver webDriver) {
        super(webDriver);
        actionWithElements = new ActionWithElements(webDriver);


    }

    public CreatePostPage enterTextIntoInputTitle(String title) {
        enterTextIntoInput(inputTitle, title);
        return this;
    }

    public CreatePostPage enterTextIntoInputBody(String body) {
        enterTextIntoInput(inputBody, body);
        return this;
    }


    public PostPage clickOnButtonSavePost() {
        actionWithElements.clickOnElement(buttonSave);
        return new PostPage(webDriver);
    }
}