package SuitOfTests;

import LoginTests.LoginTest.InvalidLoginTests;
import LoginTests.LoginTest.ValidLoginTests;
import LoginTests.SaveDataTests.SaveDataTests;
import LoginTests.VisualElementsTests.VisualElementsTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ValidLoginTests.class,
        InvalidLoginTests.class,
        SaveDataTests.class,
        VisualElementsTests.class
})
public class RegressionSuite {
}
