package SuitOfTests;

import LoginTests.SaveDataTests.SaveDataTests;
import LoginTests.VisualElementsTests.VisualElementsTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        VisualElementsTests.class,
        SaveDataTests.class})
public class SmokeSuite {
}
