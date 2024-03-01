package apiTests;

import BaseTest.BaseApi;
import api.EndPoints;
import api.dto.responseDto.PostDto;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
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
public class InvalidDeletePostByApi extends BaseApi {

    @Test
    @Parameters(method = "invalidParameters")
    public void testInvalidDeletePost(int expectedStatusCode, String expectedErrorMessage, String token, String postId) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("token", token);

        Response response = given()
                .contentType(ContentType.JSON)
                .log().all()
                .body(requestBody.toMap())
                .when()
                .delete(EndPoints.DELETE_POST, postId)
                .then()
                .statusCode(SC_FORBIDDEN) // status code 403
                .log().all()
                .extract().response();


        int actualStatusCode = response.getStatusCode();
        String actualErrorMessage = response.getBody().asString();

        assertEquals("Status code", expectedStatusCode, actualStatusCode);
        assertEquals("Error message", expectedErrorMessage, actualErrorMessage);
    }

    public Object[] invalidParameters() {
        String token = apiHelper.getToken(); // get token
        PostDto[] posts = apiHelper.getPostsByUser(VALID_LOGIN_WITH_EXISTING_POSTS); // get posts by user to check if user has posts
        String postId = apiHelper.extractPostIdFirstPost(posts); // extract post id


        return new Object[][]{

                {SC_FORBIDDEN, "\"Sorry, you must provide a valid token.\"", INVALID_VALUE_1CHAR, postId}, // status code 403
                {SC_FORBIDDEN, "\"You do not have permission to perform that action.\"", token, INVALID_VALUE_1CHAR} // status code 403
        };
    }
}

