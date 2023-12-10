package SuitOfTests;

import LoginTests.ActiveSessionTests.ActiveSessionTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ActiveSessionTests.class,})
public class SessionSuiteTests {

}
