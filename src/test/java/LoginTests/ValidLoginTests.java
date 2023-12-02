package LoginTests;

import BaseTest.BaseTest;
import TestData.TestData;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.Keys;

/**
 * Here we check valid login from pull test cases  "Validation of user input"
 * a link is provided in addition to the test case
 * <a href="https://docs.google.com/spreadsheets/d/1QBGwOHKht1vgdbQR60V9qX-9vX_bKq65/edit#gid=1698997352">
 */

@RunWith(JUnitParamsRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // to run tests in order in JUnit4 we use @FixMethodOrder
//because in @Before we can't use @Parameters
public class ValidLoginTests extends BaseTest {

    @Test
    @Parameters(method = "parametersForLoginTest")
    public void step1_CheckValidRegistrationBefore(String userName, String password) {

        pageProvider.getLoginPage().openLoginPage().enterTextIntoInputUserNameRegistration(userName);
        if (pageProvider.getLoginPage().isAlertMessageUsernameIsAlreadyTakenVisible())
            return;
        {
            pageProvider.getLoginPage().openLoginPage()
                    .enterTextIntoInputUserNameRegistration(userName)
                    .enterTextIntoInputEmailRegistration(TestData.generateRandomEmail())
                    .enterTextIntoInputPasswordRegistration(password)
                    .checkAlertsInvalidRegistration()
                    .checkIsButtonRegistrationVisible()
                    .clickOnButtonRegistration();
            pageProvider.getHomePage().getHeader().clickOnButtonSignOut();


        }
    }

    @Test
    @Parameters(method = "parametersForLoginTest")
    public void step2_TC1_validLogin(String userName, String password) {
        pageProvider.getLoginPage()
                .openLoginPage()
                .enterUsername(userName)
                .enterPassword(password)
                .clickOnLoginSignInButton()
                .checkIsSignInButtonNotVisible();
        pageProvider.getHomePage().getHeader()
                .checkIsAvatarVisibleAndCheckTextInAvatar(userName)
                .checkIsSignOutButtonVisible();
    }

    public Object[][] parametersForLoginTest() {
        return new Object[][]{
                {"MassiveComplexLastTest222", "MassiveComplexLastTest2212"},
                {TestData.VALID_LOGIN, TestData.VALID_PASSWORD},
                {TestData.VALID_LOGIN_MIN_SIZE, TestData.VALID_PASSWORD_MIN_SIZE},
                {TestData.VALID_LOGIN_MAX_SIZE, TestData.VALID_PASSWORD_MAX_SIZE}};
    }

    @Test
    @Parameters(method = "parametersForLoginTestByKeyboardButtons")
    public void step3_TC2_validLogin_withKeyboard(String userName, String password) {
        pageProvider.getLoginPage()
                .openLoginPage()
                .enterLoginWithKey(userName, Keys.TAB)
                .enterPasswordWithKey(password, Keys.ENTER)
                .checkIsSignInButtonNotVisible();
        pageProvider.getHomePage().getHeader()
                .checkIsAvatarVisibleAndCheckTextInAvatar(userName)
                .checkIsSignOutButtonVisible();
    }

    public Object[][] parametersForLoginTestByKeyboardButtons() {
        return new Object[][]{

                {TestData.VALID_LOGIN, TestData.VALID_PASSWORD},
                {TestData.VALID_LOGIN_UPPER_CASE, TestData.VALID_PASSWORD_UPPER_CASE}}; // TODo bug in upper case denied entry
    }
}

