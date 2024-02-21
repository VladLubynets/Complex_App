package apiTests;

import TestData.TestData;
import api.EndPoints;
import api.dto.requestDto.CreatePostDto;
import api.dto.responseDto.AuthorDto;
import api.dto.responseDto.PostDto;
import io.restassured.http.ContentType;
import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;


import static TestData.TestData.*;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;

import BaseTest.BaseApi;


public class ValidCreatePostByApi extends BaseApi {

    private String token;

    @Before
    public void getTokenAndDeletePosts() {
        token = apiHelper.getToken(); // get new token before each test (optional u can choose token for separate user)
        apiHelper.deletePostsTillPresent(); // before each test, delete all posts
    }

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
                .statusCode(SC_OK) // status code 200
                .log().all()
                .extract().response().getBody().asString();

        assertEquals("Message", "\"Congrats.\"", actualResponse); // check if message is correct

        PostDto[] actualPostByUser = apiHelper.getPostsByUser(); // get all posts by user
        apiHelper.getAllPostsByUserSchema(VALID_LOGIN_API_DEFAULT_10chars); // check if  parameters are correct by schema (need to choose username)

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
}