package LoginTests.VisualElementsTests;

import BaseTest.BaseTest;


import org.junit.Test;

import static TestData.ColorPalette.*;
import static TestData.TestData.*;

/**
 * Here we check Visual elements of the login on pull test cases  "VisualElementsTests"
 * a link is provided in addition to the test case
 * <a href="https://docs.google.com/spreadsheets/d/1QBGwOHKht1vgdbQR60V9qX-9vX_bKq65/edit#gid=1698997352">
 */

public class VisualElementsTests extends BaseTest {
    @Test
    public void TC5_VisualElementsHeader() {

        pageProvider.getLoginPage().openLoginPage()
                .checkColorPlaceholderUsername(GRAY)
                .checkColorPlaceholderPassword(GRAY) //TODO  //  here old placeholder Password
                .checkColorBorderUsername(LIGHT_BLUE)
                .checkColorSignInButton(NAVY_BLUE)
                .checkColorTextSignInButton(WHITE);
    }

    @Test
    public void TC6_displayedPasswordElements() {
        pageProvider.getLoginPage().openLoginPage()
                .checkIsPasswordInputDisplayed()
                .enterPasswordAndCheckHidden(VALID_PASSWORD)
                .copyValueFromPasswordInput()
                .pasteValueToUsernameInput()
                .checkIsUsernameInputEmpty();
    }

}
