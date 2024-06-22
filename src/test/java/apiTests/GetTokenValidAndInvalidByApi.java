package apiTests;

import BaseTest.BaseApi;
import TestData.TestData;
import api.EndPoints;
import io.restassured.http.ContentType;
import io.restassured.response.ResponseBody;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import static TestData.TestData.*;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(JUnitParamsRunner.class)

public class GetTokenValidAndInvalidByApi extends BaseApi {
    String login = TestData.VALID_LOGIN_API_DEFAULT_10chars;
    String password = TestData.VALID_PASSWORD_API_DEFAULT_12chars;

    @Test
    public void TC209_ValidGetToken() {
        String firstToken = apiHelper.getToken(login, password);
        String secondToken = apiHelper.getToken(login, password);

        System.out.println("First Token: " + firstToken);
        System.out.println("Second Token: " + secondToken);

        assertNotEquals(firstToken, secondToken, "Tokens must be different");
    }

    @Test
    @Parameters(method = "invalidParameters")
    public void TC210_InvalidGetToken(String user, String pass, int expectedStatusCode, String expectedMessage) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("password", pass);
        requestBody.put("username", user);


        ResponseBody responseBody = given()
                .contentType(ContentType.JSON)
                .log().all()
                .body(requestBody.toMap())
                .when()
                .post(EndPoints.LOGIN)
                .then()
                .statusCode(expectedStatusCode)
                .log().all()
                .extract().response().getBody();
        String actualMessage = responseBody.asString();

        assertEquals("Error message", expectedMessage, actualMessage);

    }

    public Object[][] invalidParameters() {
        return new Object[][]{
                {INVALID_VALUE_1CHAR, VALID_PASSWORD_API_DEFAULT_12chars, SC_BAD_REQUEST, "\"Sorry, your values are not correct.\""}, // status code 400
                {VALID_LOGIN_API_DEFAULT_10chars, INVALID_VALUE_1CHAR, SC_BAD_REQUEST, "\"Sorry, your values are not correct.\""}, // status code 400
                {EMPTY_VALUE, EMPTY_VALUE, SC_BAD_REQUEST, "\"Sorry, your values are not correct.\""},// status code 400
        };
    }
}


