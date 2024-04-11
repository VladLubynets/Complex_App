package SuitOfTests;

import apiTests.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ // ApiSuiteEnvQA
        EndToEndScenarioByAPI.class,
        GetTokenValidAndInvalidByApi.class,
        InvalidCreatePostByApi.class,
        InvalidCreateUserApi.class,
        InvalidDeleteUserByApi.class,
        InvalidGetUserInfoByApi.class,
        InvalidDeletePostByApi.class,
        ValidCreatePostByApi.class,
        ValidCreateUserApi.class,
        MixEndToEndScenarioUIAndApi.class
})
public class ApiSuite {

}

