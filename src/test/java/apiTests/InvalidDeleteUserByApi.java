package apiTests;

import BaseTest.BaseApi;
import api.EndPoints;
import api.dto.responseDto.PostDto;
import io.restassured.http.ContentType;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import static TestData.TestData.*;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)

public class InvalidDeleteUserByApi extends BaseApi {


    @Test
    @Parameters(method = "invalidParameters")
    public void TC10_testDeleteUser(String userId, String token, int expectedStatusCode, String expectedMessage) {

        JSONObject requestBody = new JSONObject();
        requestBody.put("token", token);

        String actualMessage = given()
                .contentType(ContentType.JSON)
                .log().all()
                .body(requestBody.toMap())
                .when()
                .delete(EndPoints.DELETE_USER, userId)
                .then()
                .statusCode(expectedStatusCode)
                .log().all()
                .extract().response().getBody().asString();

        assertEquals("Message in response", expectedMessage, actualMessage);
    }

    public Object[] invalidParameters() {
        String token = apiHelper.getToken(VALID_LOGIN_WITH_EXISTING_POSTS, VALID_PASSWORD_WITH_EXISTING_POSTS); // get new token
        String userInfoResponse = apiHelper.getUserInfo(VALID_LOGIN_WITH_EXISTING_POSTS, token); // get user info
        String userId = apiHelper.extractUserId(userInfoResponse); // extract user id
        PostDto[] posts = apiHelper.getPostsByUser(VALID_LOGIN_WITH_EXISTING_POSTS); // get posts by user
        int numberOfPosts = apiHelper.countPostsByTitle(posts); // count number of posts by title

        return new Object[][]{

                {userId, INVALID_VALUE_1CHAR, SC_FORBIDDEN, "\"Sorry, you must provide a valid token.\""}, // status code 403 valid userId and invalid token
                {userId, token, SC_BAD_REQUEST, "\"Number of posts of this user is " + numberOfPosts + ". We can not delete user with posts.\""}, // status code 400 valid userId and valid token
                {INVALID_VALUE_1CHAR, INVALID_VALUE_1CHAR, SC_FORBIDDEN, "\"Sorry, you must provide a valid token.\""}, // status code 403 invalid userId and invalid token
                {INVALID_VALUE_1CHAR, token, SC_FORBIDDEN, "\"You do not have permission to perform that action.\""} // status code 403 invalid userId and valid token
        };
    }
}
