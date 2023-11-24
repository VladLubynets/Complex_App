package pages;

import org.openqa.selenium.WebDriver;

public class PageProvider {
    // this class is a factory for all pages in the project
    // if we need to create a new page we need to add it here
    // And then we can use it in tests For example: pageProvider.HeaderLoginPage().openPage();
    // in general, it`s an entry point for all pages

    WebDriver webDriver;

    public PageProvider(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public HeaderLoginPage HeaderLoginPage() {
        return new HeaderLoginPage(webDriver);
    }


}
