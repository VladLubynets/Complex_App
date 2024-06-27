package apiTests;

import BaseTest.BaseApi;
import org.junit.Before;
import org.junit.Test;

import static TestData.TestData.generateRandomEmail;
import static TestData.TestData.generateRandomString;

public class ValidCreateUserApi extends BaseApi {

    private String token;
    private String username;
    private String password;
    private String email;

    @Before
    public void getActualNewTokenAndCreateData() {
        token = apiHelper.getToken();
        username = generateRandomString(10);  // create random valid username
        password = generateRandomString(12); // create random valid password
        email = generateRandomEmail(); // create random valid email
    }


    @Test
    public void TC201_testCreateAndDeleteUser() {

        apiHelper.createUser(username, password, email, token); // create user
        String userInfoResponse = apiHelper.getUserInfo(username, token);  // get user info
        String userId = apiHelper.extractUserId(userInfoResponse); // extract user id
        System.out.println("User id: " + userId);


        apiHelper.deleteUser(token, userId, username, true); // delete user


        apiHelper.checkExistTokenInInvalidUser("\"Sorry, your values are not correct.\"", username, password); // check if user is deleted
    }
}