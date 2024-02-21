package apiTests;

import BaseTest.BaseApi;
import api.EndPoints;
import api.dto.requestDto.CreatePostDto;
import io.restassured.http.ContentType;
import junitparams.JUnitParamsRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import junitparams.Parameters;

import static TestData.TestData.EMPTY_VALUE;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)

public class InvalidCreatePostByApi extends BaseApi {
    private String token;

    @Before
    public void getActualNewToken() {
        token = apiHelper.getToken(); // get new token before each test
    }

    @Test
    @Parameters(method = "inValidParameters")
    public void TC4_testNegativeApiCreatePost(String title, String body, String select, String uniquePost,
                                              String expectedErrorMessage, int expectedStatusCode, boolean includeToken) {
        String tokenToUse;
        if (includeToken) {  // switch between including token and not including token
            tokenToUse = token;
        } else {
            tokenToUse = null;
        }

        CreatePostDto createPostBody = CreatePostDto.builder()
                .title(title)
                .body(body)
                .select(select)
                .uniquePost(uniquePost)
                .token(tokenToUse)
                .build();

        String actualResponse = given()
                .contentType(ContentType.JSON)
                .log().all()
                .body(createPostBody)
                .when()
                .post(EndPoints.CREATE_POST)
                .then()
                .statusCode(expectedStatusCode)
                .log().all()
                .extract().response().getBody().asString();

        assertEquals("Error message", expectedErrorMessage, actualResponse);
    }

    public Object[] inValidParameters() {
        return new Object[][]{
                {null, "Body", "One Person", "yes", "[\"You must provide a title.\"]", SC_BAD_REQUEST, true}, // expected status code 400
                {"Valid Title", "", "One Person", "yes", "\"Sorry, you must provide a valid token.\"", SC_FORBIDDEN, false}, // expected status code 403
                {EMPTY_VALUE, EMPTY_VALUE, EMPTY_VALUE, EMPTY_VALUE, "\"Sorry, you must provide a valid token.\"", SC_FORBIDDEN, false} // expected status code 403
        };
    }
}