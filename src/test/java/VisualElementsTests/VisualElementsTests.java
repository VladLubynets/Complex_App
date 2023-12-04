package VisualElementsTests;

import TestData.ColorPalette.color;
import BaseTest.BaseTest;

import org.junit.Test;

/**
 * Here we check Visual elements of the login on pull test cases  "VisualElementsTests"
 * a link is provided in addition to the test case
 * <a href="https://docs.google.com/spreadsheets/d/1QBGwOHKht1vgdbQR60V9qX-9vX_bKq65/edit#gid=1698997352">
 */

public class VisualElementsTests extends BaseTest {
    @Test
    public void TC5_VisualElementsHeader() {

        pageProvider.getLoginPage().openLoginPage()
                .checkColorPlaceholderUsername(color.GRAY)
                .checkColorPlaceholderPassword(color.GRAY)
                .checkColorBorderUsername(color.LIGHT_BLUE)
                .checkColorSignInButton(color.NAVY_BLUE)
                .checkColorTextSignInButton(color.WHITE);
    }

}
