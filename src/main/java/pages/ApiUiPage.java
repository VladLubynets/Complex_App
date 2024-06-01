package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static TestData.TestData.*;


public class ApiUiPage extends ParentPage {

    public ApiUiPage(WebDriver webDriver) {
        super(webDriver);
    }

    @FindBy(xpath = "//table[@class='table-api table table-striped table-bordered ']")
    private WebElement tableWithApiValues;

    public List<String> extractTableInfo() {
        List<String> tableInfoList = new ArrayList<>();
        List<WebElement> rows = tableWithApiValues.findElements(By.xpath(".//tr"));
        tableInfoList.clear();
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.xpath(".//td"));
            StringBuilder rowInfo = new StringBuilder();
            for (WebElement cell : cells) {
                String cellText = cell.getText().replaceAll("\\s+", " ");
                rowInfo.append(cellText).append(" ");
            }
            String rowInfoString = rowInfo.toString().trim();
            tableInfoList.add(rowInfoString);
            System.out.println(rowInfoString);
        }
        return tableInfoList;
    }

    List<String> expectedTextList = Arrays.asList(
            GET_TOKEN,
            GET_ALL_POSTS,
            CREATE_ONE_POST,
            DELETE_ONE_POST,
            CREATE_USER,
            GET_USER_INFO,
            DELETE_USER);

    public void checkAPItextInTheTable() {
        List<String> tableInfoList = extractTableInfo();
        if (tableInfoList.isEmpty()) {
            Assert.fail("table is empty or not found");
        }
        for (String expectedText : expectedTextList) {
            boolean textFound = false;
            for (String rowInfoString : tableInfoList) {
                if (rowInfoString.contains(expectedText)) {
                    textFound = true;
                    break;
                }
            }
            Assert.assertTrue("text not found on table: " + expectedText, textFound);
        }
    }

}