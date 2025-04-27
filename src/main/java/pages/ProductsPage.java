package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class ProductsPage extends ParentPage {

    @FindBy(xpath = "//tbody//tr[contains(@class, 'v-data-table__tr')]")
    private List<WebElement> productRows;

    @FindBy(xpath = "//tbody//tr[contains(@class, 'v-data-table__tr')]//td[4]//a")
    private List<WebElement> productNames;

    @FindBy(xpath = "//tbody//tr[contains(@class, 'v-data-table__tr')]//td[5]")
    private List<WebElement> productPrices;

    public ProductsPage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    public List<String[]> getAllProductData() {
        List<String[]> products = new ArrayList<>();
        for (int i = 0; i < productNames.size(); i++) {
            String name = productNames.get(i).getText().replace("'", "");
            String price = productPrices.get(i).getText().replace("'", "");
            products.add(new String[]{name, price});
        }
        return products;
    }

    public void openFilamentBotPage() {
        webDriver.get("https://www.filamentbot.org.ua/");
        logger.info("Filament Bot page was opened");

      webDriverWait10 .until(ExpectedConditions.visibilityOf(productRows.get(0)));
        logger.info("Product rows are visible");
    }


}