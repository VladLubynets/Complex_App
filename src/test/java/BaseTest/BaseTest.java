package BaseTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import libs.ConfigProvider;
import libs.ScreenShot;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import pages.PageProvider;

import java.io.ByteArrayInputStream;
import java.time.Duration;
import java.util.ArrayList;

public class BaseTest {
    public  WebDriver webDriver;
    public PageProvider pageProvider;
    Logger logger = Logger.getLogger(getClass());
    protected ArrayList<ScreenShot> listOfScreenShots = new ArrayList<>();

    @Before
    public void setUp() {
        webDriver = initDriver();
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5)); // try to do every 0.5 sec  for 5 sec
        logger.info("Browser was opened");
        pageProvider = new PageProvider(webDriver);
    }


@Rule()
public final TestWatcher watchman = new TestWatcher() {
    @Override
    protected void failed(Throwable e, Description description) {
        screenshot();
    }

    public void saveScreenshot(ArrayList<ScreenShot> screenShots) {
        screenShots.forEach(screenShot -> Allure.addAttachment(screenShot.getName(),
                new ByteArrayInputStream(screenShot.getScreenShotImg())));
    }

    public void screenshot() {
        if (webDriver == null) {
            logger.info("Driver for screenshot not found");
            return;
        }
        byte[] screen = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);
        listOfScreenShots.add(new ScreenShot("Default screenShot after failed test", screen));
        saveScreenshot(listOfScreenShots);
    }

    @Override
    protected void finished(Description description) {
        logger.info(
                String.format("Finished test: %s::%s", description.getClassName(), description.getMethodName()));
        try {
            webDriver.quit();
            logger.info("Browser was closed");
        } catch (Exception e) {
            logger.error(e);
        }
    }

};

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
