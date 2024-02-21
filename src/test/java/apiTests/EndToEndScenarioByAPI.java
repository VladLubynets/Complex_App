package apiTests;

import BaseTest.BaseApi;
import api.EndPoints;
import api.dto.requestDto.CreatePostDto;
import api.dto.responseDto.PostDto;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;

import static TestData.TestData.*;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;

public class EndToEndScenarioByAPI extends BaseApi {
    String token;
    String newToken;
    String username;
    String password;
    String email;
    @Before
    public void getActualNewTokenAndCreateData() {
        token = apiHelper.getToken(VALID_LOGIN, VALID_PASSWORD);
        username = generateRandomString(10);  // create random valid username
        password = generateRandomString(12); // create random valid password
        email = generateRandomEmail(); // create random valid email
    }

    @Test

    public void TC6_endToEndScenario() {
        apiHelper.createUser(username, password, email, token); // create user

        apiHelper.checkErrorToGetInfoByInvalidUser(token, INVALID_VALUE_1CHAR); // check if message is correct if try to get info by invalid user
        apiHelper.checkErrorToGetInfoByInvalidUser(INVALID_VALUE_1CHAR, username); // check if message is correct if try to get info by invalid token

        String userInfoResponse = apiHelper.getUserInfo(username, token);  // get user info

        newToken=apiHelper.getToken(username, password);
        String userId = apiHelper.extractUserId(userInfoResponse);// extract user id
        CreatePostDto createPostBody = CreatePostDto.builder() // create post with valid data
                .title("Post from API")
                .body("Body Api")
                .select("One Person")
                .uniquePost("no")
                .token(newToken)
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

        apiHelper.checkErrorToGetPostByInvalidUser(INVALID_VALUE_1CHAR); // check if message is correct if try to get post by invalid user
        PostDto[] posts = apiHelper.getPostsByUser(username);

        String postId = apiHelper.extractPostIdFirstPost(posts);
        System.out.println("Post id: " + postId);

        apiHelper.checkErrorIfTryDeleteUserWithPosts(username,userId, token); // check if message is correct if try to delete user with posts

        apiHelper.checkErrorTryDeleteUserWithInvalidData(newToken,INVALID_VALUE_1CHAR); // check if message is correct if try to delete user with invalid token
        apiHelper.checkErrorTryDeleteUserWithInvalidData(INVALID_VALUE_1CHAR,userId); // check if message is correct if try to delete user with invalid user id


        apiHelper.checkErrorTryToDeleteInvalidPost(INVALID_VALUE_1CHAR, postId);  // check if message is correct if try to delete post with invalid token
        apiHelper.checkErrorTryToDeleteInvalidPost(newToken, INVALID_VALUE_1CHAR); // check if message is correct if try to delete post with invalid post id

        apiHelper.deletePostsTillPresent(username,password);// delete all posts by user
        apiHelper.deleteUser(newToken, userId); // delete user

        apiHelper.checkExistTokenInInvalidUser("\"Sorry, your values are not correct.\"", username, password); // check if user is deleted
    }
}