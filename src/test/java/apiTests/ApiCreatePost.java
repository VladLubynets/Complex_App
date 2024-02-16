package apiTests;

import BaseTest.BaseApi;
import TestData.TestData;
import api.EndPoints;
import api.dto.requestDto.CreatePostDto;
import api.dto.responseDto.AuthorDto;
import api.dto.responseDto.PostDto;
import io.restassured.http.ContentType;
import junitparams.JUnitParamsRunner;
import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import junitparams.Parameters;

import static TestData.TestData.EMPTY_VALUE;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)

public class ApiCreatePost extends BaseApi {

    @Before
    public void deleteAllPostsByUser() {
        apiHelper.deletePostsTillPresent(); // before each test, delete all posts
    }

    String token = apiHelper.getToken(); // get new token before each test


    @Test
    public void TC3_createPostByApi() {

        CreatePostDto createPostBody = CreatePostDto.builder() // create post with valid data
                .title("Post from API")
                .body("Body Api")
                .select("One Person")
                .uniquePost("yes")
                .token(token)
                .build();

        String actualResponse = given()
                .contentType(ContentType.JSON)
                .log().all()
                .body(createPostBody)
                .when()
                .post(EndPoints.CREATE_POST)
                .then()
                .statusCode(200)
                .log().all()
                .extract().response().getBody().asString();

        assertEquals("Message", "\"Congrats.\"", actualResponse); // check if message is correct

        PostDto[] actualPostByUser = apiHelper.getPostsByUser(); // get all posts by user
        apiHelper.getAllPostsByUserSchema(); // check if  parameters are correct by schema

        assertEquals("Number of posts", 1, actualPostByUser.length); // check if number of posts is correct
        String postId = actualPostByUser[0].getId();
        System.out.println("Post id: " + postId);


        PostDto[] expectedPostDTO = {
                PostDto.builder()
                        .title(createPostBody.getTitle())
                        .body(createPostBody.getBody())
                        .select(createPostBody.getSelect())
                        .uniquePost(createPostBody.getUniquePost())
                        .isVisitorOwner(false)
                        .author(AuthorDto.builder().username(TestData.VALID_LOGIN_API_DEFAULT_10chars).build())
                        .build()
        };

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(actualPostByUser).usingRecursiveComparison().ignoringFields("id", "createdDate", "author.avatar")
                .isEqualTo(expectedPostDTO); // compare actual and expected posts
        softAssertions.assertAll();

        apiHelper.deletePostById(token, postId);  // delete post after test
        PostDto[] postsAfterDeletion = apiHelper.getPostsByUser();
        assertEquals("Number of posts after deletion", 0, postsAfterDeletion.length);

    }

    @Test
    @Parameters(method = "InValidParameters")
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

    public Object[] InValidParameters() {
        return new Object[][]{
                {null, "Body", "One Person", "yes", "[\"You must provide a title.\"]", 400, true},
                {"Valid Title", "", "One Person", "yes", "\"Sorry, you must provide a valid token.\"", 403, false},
                {EMPTY_VALUE, EMPTY_VALUE, EMPTY_VALUE, EMPTY_VALUE, "\"Sorry, you must provide a valid token.\"", 403, false}
        };
    }
}

