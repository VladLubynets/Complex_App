package TestData;

import static TestData.TestData.USER_URL;

public class User {
    private String login;
    private String password;
    private String url;
    private String email;

    public User(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.url = String.format(USER_URL, login);
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getUrl() {
        return url;
    }
}