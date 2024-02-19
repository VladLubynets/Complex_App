package api;

import TestData.TestData;
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
import org.junit.Assert;


import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.apache.http.HttpStatus.*;


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
    public void checkExistTokenInInvalidUser(String errorMessage, String login, String password) {
        JSONObject requestBody = new JSONObject();
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

        assert statusCode == SC_BAD_REQUEST : "Expected status code 400 but got: " + statusCode;
        assert actualErrorMessage.equals(errorMessage) : "Expected error message: " + errorMessage + ", but got: " + actualErrorMessage;
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

        Assert.assertEquals("Number of posts", 0, getPostsByUser(username).length);

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
        Assert.assertEquals("Message in response", "\"Success\"", actualMessage);

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
                .statusCode(SC_CREATED)
                .log().all()
                .extract().response().getBody().asString();
        Assert.assertEquals("Message in response", "\"Congrats. You created new user\"", actualMessage);
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
                .statusCode(SC_OK)
                .log().all()
                .extract().response().getBody();
        return responseBody.asString();
    }
    public String extractUserId(String userInfoResponse) {
        JSONObject jsonResponse = new JSONObject(userInfoResponse);
        return jsonResponse.getString("_id");
    }

    public void deleteUser( String token,String UserId) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("token", token);

        String actualMessage = given()
                .spec(requestSpecification)
                .body(requestBody.toMap())
                .when()
                .delete(EndPoints.DELETE_USER, UserId)
                .then()
                .statusCode(SC_OK)
                .log().all()
                .extract().response().getBody().asString();
        System.out.println(token);
        System.out.println(UserId);
        Assert.assertEquals("Message in response", "\"User with id " +UserId+ " was deletted \"", actualMessage); // TODO minor bug need deleted without double "t"
    }
}