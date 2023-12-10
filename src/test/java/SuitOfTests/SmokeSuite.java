package SuitOfTests;

import LoginTests.LoginTest.InvalidLoginTests;
import LoginTests.LoginTest.ValidLoginTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ValidLoginTests.class,
        InvalidLoginTests.class})
public class SmokeSuite {
}
