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
    private WebElement checkBoxForFollowers;


    public CreatePostPage(WebDriver webDriver) {
        super(webDriver);


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
        clickOnElement(buttonSave);
        return new PostPage(webDriver);
    }

    public void createPost(String title, String body) {
        enterTextIntoInputTitle(title);
        enterTextIntoInputBody(body);
        clickOnButtonSavePost();
    }

    public void setCheckBox(WebElement checkBox, boolean state) {
        if (checkBox.isSelected() != state) {
            clickOnElement(checkBox);
        }
    }

    public void setCheckBoxForFollowers(boolean state) {
        setCheckBox(checkBoxForFollowers, state);
    }
}