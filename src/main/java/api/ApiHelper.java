package api;

import TestData.TestData;
import api.dto.requestDto.CreatePostDto;
import api.dto.requestDto.CreateUserDto;
import api.dto.responseDto.PostDto;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;


import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;


public class ApiHelper {

    Logger logger = Logger.getLogger(getClass());

    RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public String getToken() {
        return getToken(TestData.VALID_LOGIN_API_DEFAULT_10chars, TestData.VALID_PASSWORD_API_DEFAULT_12chars);
    }

    public String getToken(String login, String password) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("username", login);
        requestBody.put("password", password);

        ResponseBody responseBody = given()
                .spec(requestSpecification)
                .body(requestBody.toMap())
                .when()
                .post(EndPoints.LOGIN)
                .then()
                .statusCode(SC_OK) // status code 200
                .log().all()
                .extract().response().getBody();
        return responseBody.asString().replace("\"", "");
    }

    public void checkExistTokenInInvalidUser(String expectedErrorMessage, String login, String password) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("username", login);
        requestBody.put("password", password);

        Response response = given()
                .spec(requestSpecification)
                .body(requestBody.toMap())
                .when()
                .post(EndPoints.LOGIN)
                .then()
                .log().all()
                .extract().response();

        int statusCode = response.getStatusCode();
        String actualErrorMessage = response.getBody().asString();

        assertEquals("Status code", SC_BAD_REQUEST, statusCode);
        assertEquals("Error message", expectedErrorMessage, actualErrorMessage);
    }

    public PostDto[] getPostsByUser() {
        return getPostsByUser(TestData.VALID_LOGIN_API_DEFAULT_10chars);
    }

    public void getAllPostsByUserSchema(String username) {
        given().contentType(ContentType.JSON)
                .filter(new AllureRestAssured())
                .log().all()
                .when()
                .get(EndPoints.POST_BY_USER, username)
                .then()
                .statusCode(SC_OK) // status code 200
                .log().all()
                .assertThat().body(matchesJsonSchemaInClasspath("PostByUserSchema.json"));

    }

    public PostDto[] getPostsByUser(String username) {
        return given().spec(requestSpecification)
                .when()
                .get(EndPoints.POST_BY_USER, username)
                .then()
                .statusCode(SC_OK) // status code 200
                .log().all()
                .extract().response().getBody().as(PostDto[].class);
    }

    public void deletePostsTillPresent() {
        deletePostsTillPresent(TestData.VALID_LOGIN_API_DEFAULT_10chars, TestData.VALID_PASSWORD_API_DEFAULT_12chars);

    }

    public void deletePostsTillPresent(String username, String password) {
        PostDto[] listOfPosts = getPostsByUser(username);
        String token = getToken(username, password);

        for (int i = 0; i < listOfPosts.length; i++) {
            deletePostById(token, listOfPosts[i].getId());
            logger.info(String.format("Post with id %s and title '%s' was deleted",
                    listOfPosts[i].getId(), listOfPosts[i].getTitle()));
        }

        assertEquals("Number of posts", 0, getPostsByUser(username).length);

    }

    public void deletePostById(String token, String id) {

        JSONObject bodyParams = new JSONObject();
        bodyParams.put("token", token);

        String actualMessage = given()
                .spec(requestSpecification)
                .body(bodyParams.toMap())
                .when()
                .delete(EndPoints.DELETE_POST, id)
                .then()
                .statusCode(SC_OK) // status code 200
                .log().all().
                extract().response().getBody().asString();
        assertEquals("Message in response", "\"Success\"", actualMessage);

    }

    public void createUser(String username, String password, String email, String token) {
        CreateUserDto createUserDto = CreateUserDto.builder()
                .username(username)
                .email(email)
                .password(password)
                .token(token)
                .build();

        String actualMessage = given()
                .spec(requestSpecification)
                .body(createUserDto)
                .when()
                .put(EndPoints.CREATE_USER)
                .then()
                .statusCode(SC_CREATED) // status code 201
                .log().all()
                .extract().response().getBody().asString();
        assertEquals("Message in response", "\"Congrats. You created new user\"", actualMessage);
    }

    public String getUserInfo(String username, String token) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("token", token);

        ResponseBody responseBody = given()
                .spec(requestSpecification)
                .body(requestBody.toMap())
                .when()
                .post(EndPoints.GET_USER_INFO, username)
                .then()
                .statusCode(SC_OK) // status code 200
                .log().all()
                .extract().response().getBody();
        return responseBody.asString();
    }

    public String extractUserId(String userInfoResponse) {
        JSONObject jsonResponse = new JSONObject(userInfoResponse);
        return jsonResponse.getString("_id");
    }

    public void deleteUser(String token, String userId, String username, boolean expectSuccess) {
        PostDto[] posts = getPostsByUser(username); // get posts by user to check if user has posts
        int numberOfPosts = countPostsByTitle(posts); // count posts by title

        JSONObject requestBody = new JSONObject();
        requestBody.put("token", token);

        Response response = given()
                .spec(requestSpecification)
                .body(requestBody.toMap())
                .when()
                .delete(EndPoints.DELETE_USER, userId)
                .then()
                .log().all()
                .extract().response();

        if (expectSuccess) { // if user has no posts
            assertEquals("Status code", SC_OK, response.getStatusCode()); // status code 200
            assertEquals("Message in response", "\"User with id " + userId + " was deleted \"", response.getBody().asString());
        } else { // if user has posts
            assertEquals("Status code", SC_BAD_REQUEST, response.getStatusCode());
            String expectedErrorMessage = "\"Number of posts of this user is " + numberOfPosts + ". We can not delete user with posts.\"";
            assertEquals("Error message", expectedErrorMessage, response.getBody().asString());
        }
    }

    public void checkErrorTryToDeleteInvalidPost(String token, String postId) {

        JSONObject requestBody = new JSONObject();
        requestBody.put("token", token);

        Response response = given()
                .spec(requestSpecification)
                .body(requestBody.toMap())
                .when()
                .delete(EndPoints.DELETE_POST, postId)
                .then()
                .log().all()
                .extract().response();

        int statusCode = response.getStatusCode();

        assertEquals("Status code", SC_FORBIDDEN, statusCode); // status code 403

        String actualErrorMessage = response.getBody().asString();
        if (!actualErrorMessage.equals("\"You do not have permission to perform that action.\"")) {
            assertEquals("Error message", "\"Sorry, you must provide a valid token.\"", actualErrorMessage);
        } else {
            assertEquals("Error message", "\"You do not have permission to perform that action.\"", actualErrorMessage);
        }
    }

    public void checkErrorTryDeleteUserWithInvalidData(String token, String UserId) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("token", token);

        Response actualMessage = given()
                .spec(requestSpecification)
                .body(requestBody.toMap())
                .when()
                .delete(EndPoints.DELETE_USER, UserId)
                .then()
                .log().all()
                .extract().response();
        int statusCode = actualMessage.getStatusCode();
        String actualErrorMessage = actualMessage.getBody().asString();

        if (statusCode == SC_BAD_REQUEST || statusCode == SC_FORBIDDEN) {  // check status code 400 or 403

            assertTrue("Error message doesn't match expected", // check error message
                    actualErrorMessage.equals("\"Sorry, you must provide a valid token.\"") ||
                            actualErrorMessage.equals("\"User with ID '" + UserId + "' was not found \""));
        } else {
            assertEquals("Error message", "\"You do not have permission to perform that action.\"",
                    actualErrorMessage);
        }
    }

    public void checkErrorToGetPostByInvalidUser(String invalidValue) {
        Response response = given()
                .spec(requestSpecification)
                .when()
                .get(EndPoints.POST_BY_USER, invalidValue)
                .then()
                .log().all()
                .extract().response();

        int statusCode = response.getStatusCode();
        String actualErrorMessage = response.getBody().asString();

        assertEquals("Status code", SC_BAD_REQUEST, statusCode); // status code 400
        assertEquals("Error message", "\"Sorry, invalid user requested. Wrong username - " + invalidValue + " or there is no posts. Exception is undefined\"", actualErrorMessage);

    }

    public void checkErrorToGetInfoByInvalidUser(String token, String invalidValue) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("token", token);

        Response response = given()
                .spec(requestSpecification)
                .body(requestBody.toMap())
                .when()
                .post(EndPoints.GET_USER_INFO, invalidValue)
                .then()
                .log().all()
                .extract().response();

        int statusCode = response.getStatusCode();


        assertTrue("Status code is not 400 or 403",
                statusCode == SC_BAD_REQUEST || statusCode == SC_FORBIDDEN); // check status code 400 or 403


        if (statusCode == SC_BAD_REQUEST) { // if status code 400
            String actualErrorMessage = response.getBody().asString();
            assertEquals("Error message", "\"No user " + invalidValue + " was found\"", actualErrorMessage);
        } else if (statusCode == SC_FORBIDDEN) { // if status code 400
            String actualErrorMessage = response.getBody().asString();
            assertEquals("Error message", "\"Sorry, you must provide a valid token.\"", actualErrorMessage);
        }
    }

    public String extractPostIdFirstPost(PostDto[] posts) { // extract first post id
        if (posts != null) {
            if (posts.length > 0) {
                return posts[0].getId();
            }
        }
        return "";
    }

    public int countPostsByTitle(PostDto[] posts) { // count posts by title
        int count = 0;
        for (PostDto post : posts) {
            if (post.getTitle() != null) {
                count++;
            }
        }
        return count;
    }

    public void createUserByApiIfNeeded(String username, String password, String email, String token) { // create user by api if needed (if user doesn't exist)
        CreateUserDto createUserDto = CreateUserDto.builder()
                .username(username)
                .email(email)
                .password(password)
                .token(token)
                .build();

        try {
            given()
                    .spec(requestSpecification)
                    .body(createUserDto)
                    .when()
                    .put(EndPoints.CREATE_USER)
                    .then()
                    .assertThat().statusCode(anyOf(is(SC_CREATED), is(SC_NOT_FOUND)))
                    .log().all()
                    .extract().response().getBody().asString();
        } catch (Exception e) {
            String errorMessage = e.getMessage();

            if (errorMessage.contains("You must provide a username.") || // check if user already exists
                    errorMessage.contains("This email is already being used.")) {
                return;
            } else {

                throw e;
            }
        }
    }

    public void createMultiplePostsByApi(int numberOfPosts, String token, String username, String select, String uniquePost) { // create multiple posts by api (if needed)
        PostDto[] existingPosts = getPostsByUser(username);

        if (existingPosts.length >= numberOfPosts) { // check if there are already enough posts
            System.out.println("There are already enough posts. No new posts will be created.");
            return;
        }

        for (int i = 0; i < numberOfPosts; i++) {
            CreatePostDto createPostBody = CreatePostDto.builder()
                    .title("Post " + (i + 1))
                    .body("Body of Post " + (i + 1))
                    .select(select)
                    .uniquePost(uniquePost)
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
        }
    }

    public void createOnePostForFollowers(String title, String body, String select, String uniquePost, String token) {
        CreatePostDto createPostBody = CreatePostDto.builder()
                .title(title)
                .body(body)
                .select(select)
                .uniquePost(uniquePost)
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
    }
}