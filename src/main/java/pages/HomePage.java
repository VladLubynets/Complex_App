package pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class HomePage extends ParentPageWithHeader {

    @FindBy(xpath = "//h2[contains(., 'Hello ') and contains(., ', your feed is empty.')]/strong")
    private WebElement userNameElement;
    @FindBy(xpath = "//p[@class='lead text-muted']")
    private WebElement feedMessageElement;


    private ListOfPostElement latestPostsElement;
    private ListOfPostElement latestPostsFromFollowingList;

    public HomePage(WebDriver webDriver) {
        super(webDriver);
    }

    public ListOfPostElement getLatestPostsElement() {
        if (latestPostsElement == null) {
            latestPostsElement = new ListOfPostElement(webDriver, NamesOfPostsList.THE_LATEST_POSTS);
        }
        return latestPostsElement;
    }

    public ListOfPostElement getLatestPostsFromFollowingList() {
        if (latestPostsFromFollowingList == null) {
            latestPostsFromFollowingList = new ListOfPostElement(webDriver, NamesOfPostsList.THE_LATEST_POSTS_FROM_THOSE_YOU_FOLLOW);
        }
        return latestPostsFromFollowingList;
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
    }
