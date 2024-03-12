package apiUItableTests;

import BaseTest.BaseTest;
import org.junit.Test;

import static TestData.TestData.API_URL;

public class TableUiTests extends BaseTest {
    @Test
    public void TC7_displayAndComparisonAndActiveSession() {
        pageProvider.getLoginPage().loginWithValidCred()
                .openNewTabAndSwitchToIt(API_URL);
        pageProvider.getLoginPage().checkIsSignInButtonNotVisible();
        pageProvider.getHomePage().extractTableInfo();
        pageProvider.getHomePage().CheckAPItextInTheTable();
        pageProvider.getLoginPage().waitForMinutes(30);

        pageProvider.getHomePage().getHeader().clickOnCreatePostButton();
        pageProvider.getLoginPage().checkIsAlertMessageVisible()
                .checkTextInAlertMessage("You must be logged in to perform that action.");
        pageProvider.getLoginPage().checkIsSignInButtonVisible();

    }

    @Test
    public void TC8_ApiUIActiveSession() {
        pageProvider.getLoginPage().openLoginPage().checkIsUsernameInputEmpty()
                .checkIsPasswordInputEmpty().openNewTabAndSwitchToIt(API_URL);
        pageProvider.getLoginPage().checkIsAlertMessageVisible();
        pageProvider.getLoginPage().checkTextInAlertMessage("You must be logged in to perform that action.");
    }
}