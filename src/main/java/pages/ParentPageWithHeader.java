package pages;

import org.openqa.selenium.WebDriver;

abstract public class ParentPageWithHeader extends ParentPage {

    public ParentPageWithHeader(WebDriver webDriver) {
        super(webDriver);
    }

    public Header getHeader() {
        return new Header(webDriver);
    }
}