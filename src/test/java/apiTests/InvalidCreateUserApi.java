package apiTests;

import BaseTest.BaseApi;
import api.EndPoints;
import api.dto.requestDto.CreateUserDto;
import io.restassured.http.ContentType;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static TestData.TestData.*;
import static TestData.TestData.generateRandomString;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)

public class InvalidCreateUserApi extends BaseApi {

    @Test
    @Parameters(method = "inValidParameters")
    public void TC207_invalidTestCreateUser(String username, String password, String email, String expectedErrorMessage, int expectedStatusCode, boolean includeToken) {
        String token = apiHelper.getToken();
        String tokenToUse;
        if (includeToken) {  // switch between including token and not including token
            tokenToUse = token;
        } else {
            tokenToUse = null;
        }
        CreateUserDto createUserDto = CreateUserDto.builder() // try to create user with invalid data
                .username(username)
                .email(email)
                .password(password)
                .token(tokenToUse)
                .build();

        String actualMessage = given() // send request
                .contentType(ContentType.JSON)
                .log().all()
                .body(createUserDto)
                .when()
                .put(EndPoints.CREATE_USER)
                .then()
                .statusCode(expectedStatusCode)
                .log().all()
                .extract().response().getBody().asString(); // get response

        assertEquals("Error message", expectedErrorMessage, actualMessage); // check if error message is correct
    }

    public Object[] inValidParameters() { // invalid parameters
        return new Object[][]{
                {generateRandomString(3), generateRandomString(12), generateRandomEmail(), "\"Sorry, you must provide a valid token.\"", SC_FORBIDDEN, false},// status code 403
                {"кирилицялогін", generateRandomString(13), generateRandomEmail(), "[\"Username can only contain letters and numbers.\"]", SC_NOT_FOUND, true}, // status code 404
                {generateRandomString(10), generateRandomString(12), "vladqaauto@gmail.com", "[\"This email is already being used.\"]", SC_NOT_FOUND, true},// status code 404
                {VALID_LOGIN_API_DEFAULT_10chars, generateRandomString(43), generateRandomEmail(), "[\"This username is already taken.\"]", SC_NOT_FOUND, true}, // status code 404
                {EMPTY_VALUE, EMPTY_VALUE, EMPTY_VALUE, "[\"You must provide a username.\",\"You must provide a valid email address.\",\"You must provide a password.\"]", SC_NOT_FOUND, true},// status code 404
                {INVALID_VALUE_1CHAR, INVALID_VALUE_1CHAR, INVALID_VALUE_1CHAR, "[\"You must provide a valid email address.\",\"Password must be at least 12 characters.\",\"Username must be at least 3 characters.\"]", SC_NOT_FOUND, true}, // status code 404
                {generateRandomString(31), generateRandomString(51), generateRandomEmail(), "[\"Password cannot exceed 50 characters.\",\"Username cannot exceed 30 characters.\"]", SC_NOT_FOUND, true}// status code 404
        };
    }
}