package SuitOfTests;

import apiTests.*;
import apiUItableTests.TableUiTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ // ApiSuite+UI EnvQA
        MixEndToEndScenarioUIAndApi.class,
        TableUiTests.class

})
public class ApiAndUISuite {
}
