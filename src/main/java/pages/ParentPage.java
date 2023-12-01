package pages;

import org.openqa.selenium.WebDriver;

public class ParentPage extends ActionWithElements {
    String env = System.getProperty("env", "qa"); // prepare URL for different environments
    final String BASE_URL = String.format("https://%s-complexapp.onrender.com", env);

    public ParentPage(WebDriver webDriver) {
        super(webDriver);
    }
}
