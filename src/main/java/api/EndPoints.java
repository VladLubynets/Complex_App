package api;

public interface EndPoints {

    final String BASE_URL = "https://qa-complexapp.onrender.com";
    final String POST_BY_USER = BASE_URL + "/api/postsByAuthor/{0}";
    final String LOGIN = BASE_URL + "/api/login";
    final String CREATE_POST = BASE_URL + "/api/create-post";
    final String DELETE_POST = BASE_URL + "/api/post/{0}";
    final String CREATE_USER = BASE_URL + "/api/user";
    final String GET_USER_INFO = BASE_URL + "/api/user/info/{username}";
    final String DELETE_USER = BASE_URL + "/api/user/{0}";
}