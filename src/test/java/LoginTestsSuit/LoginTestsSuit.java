package LoginTestsSuit;

import LoginTests.InvalidLoginTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        LoginTests.ValidLoginTests.class,
        InvalidLoginTests.class})
public class LoginTestsSuit {
}