package BaseTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import pages.PageProvider;

import java.time.Duration;

public class BaseTest {
    public  WebDriver webDriver;
    public PageProvider pageProvider;
    Logger logger = Logger.getLogger(getClass());

    @Before
    public void setUp() {
        webDriver = initDriver();
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5)); // try to do every 0.5 sec  for 5 sec
        logger.info("Browser was opened");
        pageProvider = new PageProvider(webDriver);
    }

    @After
    public void shutDown() {
        webDriver.quit();
        logger.info("Browser was closed");
    }

    private WebDriver initDriver() {
        String browser = System.getProperty("browser");
        if ((browser == null) || ("chrome".equals(browser.toLowerCase()))) { // default browser -Dbrowser=chrome
            WebDriverManager.chromedriver().setup();
            webDriver = new ChromeDriver();
        } else if ("firefox".equals(browser.toLowerCase())) { // -Dbrowser=firefox
            WebDriverManager.firefoxdriver().setup();
            webDriver = new FirefoxDriver();
        } else if ("iedriver".equals(browser.toLowerCase())) { // -Dbrowser=iedriver
            WebDriverManager.iedriver().setup(); // zoom 100%
            webDriver = new InternetExplorerDriver();//level of security - Medium (recommended)
        } else if ("edgedriver".equals(browser.toLowerCase())) { // -Dbrowser=edgedriver
            WebDriverManager.edgedriver().setup();
            webDriver = new EdgeDriver();
        } else if ("safari".equals(browser.toLowerCase())) { // -Dbrowser=safari
            WebDriverManager.getInstance(SafariDriver.class).setup();
            webDriver = new SafariDriver();
        } else {
            throw new IllegalArgumentException("Can't init driver for browser " + browser);
        }

        return webDriver;
    }


}
