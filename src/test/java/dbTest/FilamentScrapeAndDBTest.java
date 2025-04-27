package dbTest;

import BaseTest.BaseTest;
import libs.Database;
import libs.MySQL_Database;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.sql.SQLException;
import java.util.List;

public class FilamentScrapeAndDBTest extends BaseTest {
    private Database mysqlDB;
    private Logger logger = Logger.getLogger(getClass());

    @Before
    public void setUpDB() throws SQLException, ClassNotFoundException {
        mysqlDB = MySQL_Database.getDataBase();
    }

    @After
    public void tearDownDB() throws SQLException {
        if (mysqlDB != null) {
            mysqlDB.quit();
        }
    }

    @Test
    public void testFilamentbotProductsInDB() throws SQLException {
        pageProvider.getProductsPage().openFilamentBotPage();

        List<String[]> productData = pageProvider.getProductsPage().getAllProductData();
        logger.info("amount of products: " + productData.size());


        mysqlDB.executeUpdateQuery("CREATE TABLE IF NOT EXISTS filament_products (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(255)," +
                "price VARCHAR(50))");


        mysqlDB.executeUpdateQuery("DELETE FROM filament_products");


        for (String[] product : productData) {
            String name = product[0];
            String price = product[1];
            String query = String.format("INSERT INTO filament_products(name, price) VALUES('%s', '%s')", name, price);
            mysqlDB.executeUpdateQuery(query);
        }


        String countFromDB = mysqlDB.selectValue("SELECT COUNT(*) FROM filament_products");
        logger.info("amount of products in DB: " + countFromDB);


        mysqlDB.executeUpdateQuery("DELETE FROM filament_products");
        logger.info("All items was deleted by table filament_products");
    }
}
