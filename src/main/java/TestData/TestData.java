package TestData;

import libs.ConfigProvider;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Locale;

public class TestData {
    public final static String VALID_LOGIN = System.getProperty("validLogin", ConfigProvider.configHiddenProperties.login_default());
    public final static String VALID_PASSWORD = System.getProperty("validPassword", ConfigProvider.configHiddenProperties.password_default());
    public final static String VALID_LOGIN_UPPER_CASE = VALID_LOGIN.toUpperCase(Locale.ROOT);

    public final static String VALID_LOGIN_MIN_SIZE = VALID_LOGIN.substring(0, 3); // at least 3 characters
    public final static String VALID_PASSWORD_MIN_SIZE = VALID_PASSWORD.substring(0, 12); // at least 12 characters

    public final static String VALID_LOGIN_MAX_SIZE = "a".repeat(30);   // at most 30 characters
    public final static String VALID_PASSWORD_MAX_SIZE = "a".repeat(30); // at most 30 characters

    public final static String INVALID_LOGIN = "aacomplex";
    public final static String INVALID_PASSWORD = "123";

    public final static String EMPTY_VALUE = "";
    public final static String INVALID_VALUE_1CHAR = "a";

    public final static String VALID_LOGIN_WITH_EXISTING_POSTS = System.getProperty("loginApiWithPosts", ConfigProvider.configHiddenProperties.login_api_with_posts());
    public final static String VALID_PASSWORD_WITH_EXISTING_POSTS = System.getProperty("passwordApiWithPosts", ConfigProvider.configHiddenProperties.password_api_with_posts());

    public final static String VALID_LOGIN_WITHOUT_POSTS = System.getProperty("loginApiWithoutPosts", ConfigProvider.configHiddenProperties.login_without_posts());
    public final static String VALID_PASSWORD_WITHOUT_POSTS = System.getProperty("passwordApiWithoutPosts", ConfigProvider.configHiddenProperties.password_without_posts());
    public final static String EXPECTED_TEXT_IN_HOME_PAGE = "Your feed displays the latest posts from the people you follow. If you don’t have any friends to follow that’s okay; you can use the “Search” feature in the top menu bar to find content written by people with similar interests and then follow them.";
    public final static String VALID_LOGIN_WITH_FOLLOWERS = System.getProperty("loginApiWithFollowers", ConfigProvider.configHiddenProperties.login_with_followers());
    public final static String VALID_PASSWORD_WITH_FOLLOWERS = System.getProperty("passwordApiWithFollowers", ConfigProvider.configHiddenProperties.password_with_followers());

    public static String generateRandomString(int length) {  // randomizer  value
        return RandomStringUtils.randomAlphanumeric(length).toLowerCase();
    }

    public static String generateRandomEmail() {  // create random email
        String randomPart = generateRandomString(10).toLowerCase();
        String domain = "gmail.com";
        return randomPart + "@" + domain;
    }

    public final static String VALID_LOGIN_API_DEFAULT_10chars = System.getProperty("loginApiDefault", ConfigProvider.configHiddenProperties.login_api_default());
    public final static String VALID_PASSWORD_API_DEFAULT_12chars = System.getProperty("passwordApiDefault", ConfigProvider.configHiddenProperties.password_api_default());

    public final static String API_URL = System.getProperty("apiUrl", ConfigProvider.configHiddenProperties.api_url());
    public final static String USER_URL = "https://qa-complexapp.onrender.com/profile/%s";

    public final static String GET_TOKEN = "Login: Get token POST /api/login Request Body { \"username\": \"your login\", \"password\": \"your password\" }  Status code : 200 ResponseBody example { \"token\" } Status code : 400 ResponseBody example { \"Sorry, your values are not correct.\" }";
    public final static String GET_ALL_POSTS = "Post: Get All Posts GET /api/postsByAuthor/{username} Status code : 200 ResponseBody example [ { \"_id\": \"656614a9436b730028056c91\", \"title\": \"test2\", \"body\": \"test body2\", \"select1\": \"All Users\", \"uniquePost\": \"no\", \"createdDate\": \"2023-11-28T16:26:17.845Z\", \"author\": { \"username\": \"autoapi\", \"avatar\": \"https://gravatar.com/avatar/{userId}?s=128\" }, \"isVisitorOwner\": false }, { \"_id\": \"656614a9436b730028056c90\", \"title\": \"test\", \"body\": \"test body\", \"select1\": \"All Users\", \"uniquePost\": \"no\", \"createdDate\": \"2023-11-28T16:26:17.293Z\", \"author\": { \"username\": \"autoapi\", \"avatar\": \"https://gravatar.com/avatar/{userId}?s=128\" }, \"isVisitorOwner\": false } ] Status code : 400 ResponseBody example { \"Sorry, invalid user requested. Wrong username - {username} or there is no posts. Exception is undefined\" }";
    public final static String CREATE_ONE_POST = "Post: Create one POST POST /api/create-post Request Body { \"select1\": \"One Person\", \"title\": \"New post from API\", \"body\": \"post body\", \"token\": \"your token\" }  Status code : 200 ResponseBody example { \"Congrats.\" } Status code : 403 ResponseBody example { \"Sorry, you must provide a valid token.\" }";
    public final static String DELETE_ONE_POST = "Post: Delete one POST DELETE /api/post/{postId} Request Body { \"token\": \"your token\" }  Status code : 200 ResponseBody example { \"Success\" } Status code : 403 ResponseBody example { \"Sorry, you must provide a valid token.\" }";
    public final static String CREATE_USER = "User: Create user PUT /api/user Request Body { \"username\": \"{username}\", \"email\": \"email@gmail.com\", \"password\": \"your password\", \"token\": \"your token\" }  Status code : 201 ResponseBody example { \"Congrats. You created new user\" } Status code : 403 ResponseBody example { \"Sorry, you must provide a valid token.\" } Status code : 404 ResponseBody example [ \"You must provide a username.\", \"This email is already being used.\" ]";
    public final static String GET_USER_INFO = "User: Get User Info POST api/user/info/{username} Request Body { \"token\": \"your token\" }  Status code : 200 ResponseBody example { \"_id\": \"656df7f0c7be6b427de778c2\", \"username\": \"{username}\", \"avatar\": \"https://gravatar.com/avatar/{userid}?s=128\", \"email\": \"test6@gmail.com\" } Status code : 403 ResponseBody example { \"Sorry, you must provide a valid token.\" }";
    public final static String DELETE_USER = "User: Delete User DELETE api/user/{userid} Request Body { \"token\": \"your token\" }  Status code : 200 ResponseBody example { \"User with id {userId} was deleted \" } Status code : 403 ResponseBody example { \"Sorry, you must provide a valid token.\" } Status code : 400 ResponseBody example { \"Number of posts of this user is {numberOfPosts}. We can not delete user with posts.\" } Status code : 400 ResponseBody example { \"Number of following of this user is {numberOfFollowing}. We can not delete user with 'following'.\" } Status code : 400 ResponseBody example { \"Number of followers of this user is {numberOfFollowes}. We can not delete user with 'followers'.\" } Status code : 400 ResponseBody example { \"User with ID '{notValidUserId}' was not found \" }";

}
