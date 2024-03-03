package apiTests;

import BaseTest.BaseApi;
import api.EndPoints;
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

public class InvalidGetUserInfoByApi extends BaseApi {

    @Test
    @Parameters(method = "invalidParameters")
    public void testInvalidGetUserInfo(String username, String token, String expectedErrorMessage, int expectedStatusCode) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("token", token);

        Response response = given()
                .contentType(ContentType.JSON)
                .log().all()
                .body(requestBody.toMap())
                .when()
                .post(EndPoints.GET_USER_INFO, username)
                .then()
                .statusCode(expectedStatusCode)
                .log().all()
                .extract().response();

        String actualResponse = response.getBody().asString();

        assertEquals("Error message", expectedErrorMessage, actualResponse);
    }

    public Object[] invalidParameters() {
        String token = apiHelper.getToken();
        return new Object[][]{
                {VALID_LOGIN_API_DEFAULT_10chars, INVALID_VALUE_1CHAR, "\"Sorry, you must provide a valid token.\"", SC_FORBIDDEN}, // status code 403
                {INVALID_VALUE_1CHAR, token, "\"No user " + INVALID_VALUE_1CHAR + " was found\"", SC_BAD_REQUEST}, // status code 400
                {INVALID_VALUE_1CHAR, INVALID_VALUE_1CHAR, "\"Sorry, you must provide a valid token.\"", SC_FORBIDDEN}, // status code 403
        };
    }
}